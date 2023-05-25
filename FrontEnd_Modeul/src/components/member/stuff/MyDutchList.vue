<script setup>
import dayjs from 'dayjs';
import 'dayjs/locale/ko';
import { nextTick, onMounted, reactive, ref } from 'vue';
import { useRoute } from 'vue-router';
import { useDefaultStore } from '../../../stores/useDefaultStore';
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';

// Vue3 Composition API으로 작성해보기**

const route = useRoute();
const todayMonth = new dayjs().locale('ko').format("M");

let page = ref(1);
let memberId = ref();
let month = ref(todayMonth);
let calDrawer = ref(false);
let stuffId = ref();
let sumDutch = ref();
let userDetails = useUserDetailsStore();
let defaultStore = useDefaultStore();
let copyModal = ref(false);
let listCount = ref();
let selectBank = ref();
let accountNumber = ref();
let stuffLeaderName = ref();

let model = reactive({
	list: [],
	months: [],
	memberList: [],
});


async function addListHandler() {
	page.value++;
	// 쿼리스트링을 Composition API에서는 어떻게 SpringBoot로 비동기 처리하여 요청하지? 
	// ** 컴포지션 API에서 넘기는 방법? 방법이 body 밖에 없는데? body는 POST요청에서만 이용
	// **결론, 'value' 이용!
	// vuex를 composition API에서 이용하기 위해서는 this와 $를 없애자(원래는 'this.$state.host') 
	const response = await fetch(`${defaultStore.host}/api/dutchs?p=${page.value}&memberId=${memberId.value}&m=${month.value}`, {
		// body:`p=${page}&memberId=${memberId}`
	});

	const dataList = await response.json(); // 여기 await 중요!
	model.list = formatDateList(dataList.list);
	model.months = dataList.months;
	listCount = ref(dataList.listCount);
}

async function selectMonthList() {
	page = ref(1);
	// 쿼리스트링을 Composition API에서는 어떻게 SpringBoot로 비동기 처리하여 요청하지? 
	// ** 컴포지션 API에서 넘기는 방법? 방법이 body 밖에 없는데? body는 POST요청에서만 이용
	// **결론, 'value' 이용!
	// vuex를 composition API에서 이용하기 위해서는 this와 $를 없애자(원래는 'this.$state.host') 
	const response = await fetch(`${defaultStore.host}/api/dutchs?p=${page.value}&memberId=${memberId.value}&m=${month.value}`, {
		// body:`p=${page}&memberId=${memberId}`
	});

	const dataList = await response.json(); // 여기 await 중요!
	model.list = formatDateList(dataList.list);
	model.months = dataList.months;
	listCount = ref(dataList.listCount);
}

async function loadDutchMemberList() {
	const response = await fetch(`${defaultStore.host}/api/dutch/${stuffId.value}`);
	const dataList = await response.json();
	model.memberList = dataList.list;
	sumDutchHandler();
}

async function showAccount() {

	let response = await fetch(`${defaultStore.host}/api/account/${stuffId.value}`);
	response = await response.json();
	selectBank = ref(response.bankName + " ");
	accountNumber = ref(response.number);
	stuffLeaderName = ref(response.memberName);
}

function formatDateList(list) {
	if (list == null)
		return;
	let resultList = [];
	for (let item of list) {
		if (item.stuffDeadline == null)
			continue;
		const today = new dayjs().format('YYYY-MM-DD');

		const deadlineObj = dayjs(item.stuffDeadline).locale('ko');
		const isToday = (deadlineObj.format('YYYY-MM-DD') == today) ? '오늘, ' : ''
		item.stuffDeadline = isToday + deadlineObj.format("M월 D일 (dd) HH시까지");
		// item.month = new dayjs(item.date).locale('ko').format("M");

		item.dDay = dayjs().diff(deadlineObj, 'day');
		if (parseInt(item.dDay) < 0) {
			item.dDay = 'D' + item.dDay;
			item.deadlineState = 1;
		}
		else if (parseInt(item.dDay) == 0) {
			item.dDay = deadlineObj.diff(dayjs(), 'hours')
			if (parseInt(item.dDay) > 0) {
				item.dDay = '마감 ' + deadlineObj.diff(dayjs(), 'hours') + '시간 전'
				item.deadlineState = 2;
			}
			else if (parseInt(item.dDay) == 0) {
				item.dDay = '1시간 내 마감';
				item.deadlineState = 3;
			}
			else {
				item.dDay = '마감';
				item.deadlineState = 0;
			}
		}
		else {
			item.dDay = '마감';
			item.deadlineState = 0;
		}
		resultList.push(item);
	}
	return resultList;
}

async function sumDutchHandler() {
	let sum = 0;

	for (let m of model.memberList) {
		sum += parseInt(m.price);
	}
	sumDutch = ref(sum);
	return sumDutch.value;
}

function selectMonthHandler() {
	selectMonthList();
}

function showCalcResultHandler(d) {
	
	stuffId = ref(d.stuffId);
	calDrawer.value = !calDrawer.value;
	
	loadDutchMemberList();
	showAccount();
}

function copyHandler() {
	navigator.clipboard.writeText(selectBank.value + accountNumber.value)
		.then(() => {
			copyModal.value = false;

			nextTick(() => {
				copyModal.value = true;
			});
		},
			() => {

			});
}

function formatPrice(price) {
	return Number(price).toLocaleString();
}

onMounted(() => {
	page = ref(0);
	memberId.value = userDetails.id;
	addListHandler();
})


</script>

<template>
	<!-- Vue3 composition API에서 SpringBoot DB 데이터를 받아와서 이용하는 방법 -->
	<div class="dutch canvas">

		<header class="header">
			<div>
				<router-link to="/member/mypage" class="back"></router-link>
			</div>
			<div class="title">정산 내역</div>
		</header>

		<div class="selectbox">
			<select class="select-arrow" v-model="month" @change="selectMonthHandler">
				<option v-for="m in model.months" :value="m.month">{{ m.month }}월</option>
			</select>
		</div>


		<div class="stuff-list" v-for="d in model.list">
			<div @click="showCalcResultHandler(d)">
				<div class="d-gr li-gr m-t-13px list-cl">

					<div class="li-pic b-rad-1">
						<img :src="'/images/member/' + d.stuffManagerImage" />
					</div>

					<div class="li-manager-nic">
						<span class="li-manager-icon">
						</span>
						<div class="li-manager-name">
							{{ d.stuffManagerNickname }}
						</div>
					</div>

					<div class="li-dday" :class="(d.deadlineState == 0) ? 'expired' :
						(d.deadlineState == 1) ? 'day-left' :
							(d.deadlineState == 2) ? 'hour-left' : 'minute-left'">{{ d.dDay }}</div>

					<div class="li-subj">{{ d.stuffTitle }}</div>

					<div class="li-price">
						{{ formatPrice(d.price) }} 원
					</div>
				</div>

			</div>
		</div>
		<button class="btn-next more-list" @click="addListHandler()"> 더보기 <span> +{{ listCount }}</span></button>
		<!-- ** 정산 결과 모달 ** -->
		<v-navigation-drawer style="height: 630px; border-radius: 30px 30px 0px 0px;" v-model="calDrawer" location="bottom"
		temporary>
			<section class="calc-result-default">
				<h1 class="d-none">calculate</h1>

				<section class="cal-result-main">


					<header class="cal-result-header">
						<h1 class="d-none">title</h1>
						<div class="cal-result-title">정산결과</div>
					</header>

					<main class="cal-result-user-list">
						<h1 class="d-none">main</h1>
						<!-- <div class="cal-user" v-for="m in dutchMemberList"> -->
							<div class="cal-user" v-for="m in model.memberList">
							<div class="cal-user-img">
								<img :src="'/images/member/' + m.memberImage" alt="사용자1">
							</div>
							<div class="cal-user-name">
								{{ m.memberNickname }}
							</div>
							<div class="cal-user-self-result">
								{{ formatPrice(m.price) }}원
							</div>
						</div>
					</main>

					<section class="cal-result-sum">
						<h1 class="d-none">sum</h1>
						<div>
							합계
						</div>
						<div>
							{{ formatPrice(sumDutch) }}원
						</div>
					</section>

					<section class="cal-result-account-form">
						<h1 class="d-none">account</h1>

						<div class="cal-result-account-all">
							<a class="icon-bank-security"></a>
							<div class="cal-leader-account">
								<span>{{ selectBank }} </span>
								<span>{{ accountNumber }}</span>
							</div>
							<a class="icon-account-paste" @click.prevent="copyHandler">복사하기</a>
						</div>

						<div class="cal-leader-name">
							{{ stuffLeaderName }}
						</div>

					</section>

					<section class="cal-result-check-form">
						<h1 class="d-none">check</h1>
						<button class="cal-result-check-btn" @click="calDrawer = !calDrawer">확인</button>
					</section>

				</section>
			</section>
		</v-navigation-drawer>
	</div>

	<div v-if="copyModal" class="error-box ani">
		복사되었습니다.
	</div>
</template>

<style scoped>
@import "/css/component/member/stuff/component-list.css";
@import "/css/button.css";


.canvas {
	max-width: 600px;
	padding: 0 20px;
	margin: 0 auto;
	min-width: 360px
}

.dutch .header {
	display: flex;
	flex-direction: row;
	align-items: center;
	padding: 0px;
	width: 100%;
	margin-top: 25px;
}

.back-arrow-box {
	margin-top: 16px;
	margin-left: 20px;
	width: 46px;
}

.header .back {
	background-image: url("data:image/svg+xml,%3Csvg width='16' height='16' viewBox='0 0 16 16' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M16 7H3.83L9.42 1.41L8 0L0 8L8 16L9.41 14.59L3.83 9H16V7Z' fill='black'/%3E%3C/svg%3E%0A");
	width: 23.04px;
	height: 24px;
	margin-top: 9px;
}

.header .title {
	color: #333;
	margin: 0 auto;
	padding-right: 23px;
}

.dutch .selectbox {
	margin: 12px 0;
	display: flex;
	font-size: 15px;
	font-weight: 400;
}

select {
	text-align-last: center;
}

select:focus {
	outline: none;
}

.select-arrow {
	width: 55px;
	background: url("data:image/svg+xml,%3Csvg width='17' height='21' viewBox='0 0 17 21' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath fill-rule='evenodd' clip-rule='evenodd' d='M11.7512 7.23657L8.5 11.095L5.24875 7.23657L4.25 8.42442L8.5 13.4791L12.75 8.42442L11.7512 7.23657Z' fill='black' fill-opacity='0.87'/%3E%3C/svg%3E%0A") no-repeat right -2px center;
}

.dutch .header .content {
	display: flex;
	flex-direction: column;
	/* justify-content: center; */
	height: 121px;
	width: 250px;
	margin-top: 32px;
}

.stuff-list {
	border-bottom: 0.3px #d5d5d5 solid;
	box-sizing: border-box;
}

.stuff-list:hover {
	transition: 0.3s;
	background-color: #fbfbfb;
	border-radius: 5px;
}


.li-gr {
	grid-template-columns: 60px 8px minmax(174px, auto) 8px 75px;
	grid-template-rows: 36px 12px 12px;
	grid-template-areas:
		"pic . subj . dday"
		"pic . . . ."
		"pic . nic price price";

	align-items: center;
	/* grid-row-gap: 1%; */
	/* grid-column-gap: 10px; */
}

.li-pic {
	grid-area: pic;
	width: 60px;
	height: 60px;
	display: inline-block;
	overflow: hidden;
	/* text-indent: -999px; */
	justify-self: center;
	align-self: center;
	border-radius: 50%;
}

.li-manager-nic {
	grid-area: nic;
	font-size: 10px;
	color: #888585;
	display: flex;
	justify-self: flex-start;
	align-self: center;
	/* padding-left: 13px; */
}

.li-manager-icon::before {
	content: "";
	background-image: url("data:image/svg+xml,%3Csvg width='10' height='12' viewBox='0 0 10 12' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M4.99992 5.3999C6.36222 5.3999 7.46655 4.29553 7.46655 2.93323C7.46655 1.57093 6.36222 0.466553 4.99992 0.466553C3.63762 0.466553 2.5332 1.57093 2.5332 2.93323C2.5332 4.29553 3.63762 5.3999 4.99992 5.3999Z' fill='%23929090'/%3E%3Cpath d='M7.86713 11.4667C8.80046 11.4667 9.46716 10.4667 9.06716 9.6C8.33383 8.06666 6.80051 7 5.00051 7C3.20051 7 1.60053 8.06666 0.933858 9.6C0.533858 10.4667 1.20048 11.4667 2.13381 11.4667H7.86713Z' fill='%23929090'/%3E%3C/svg%3E%0A");
	margin-left: 3px;

	display: inline-block;

	height: 12px;
	width: 10px;

	position: relative;
	top: 2px;

	/* background-image: url("data:image/svg+xml,%3Csvg width='10' height='12' viewBox='0 0 10 12' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M4.99992 5.3999C6.36222 5.3999 7.46655 4.29553 7.46655 2.93323C7.46655 1.57093 6.36222 0.466553 4.99992 0.466553C3.63762 0.466553 2.5332 1.57093 2.5332 2.93323C2.5332 4.29553 3.63762 5.3999 4.99992 5.3999Z' fill='%23929090'/%3E%3Cpath d='M7.86713 11.4667C8.80046 11.4667 9.46716 10.4667 9.06716 9.6C8.33383 8.06666 6.80051 7 5.00051 7C3.20051 7 1.60053 8.06666 0.933858 9.6C0.533858 10.4667 1.20048 11.4667 2.13381 11.4667H7.86713Z' fill='%23929090'/%3E%3C/svg%3E%0A"); */
}

.li-manager-name {
	margin-left: 5px;
}

.li-dday {
	grid-area: dday;
	text-align: center;
	border-radius: 5px;
	font-size: 0.5rem;
	/* width: 64px; */
	height: 15px;
	line-height: 15px;
	margin-top: 0px;
	/* margin-left: 35px; */
	font-weight: 600;
}

.li-dday .expired {
	background-color: #19191982;
	width: 35px;
	/* margin-left: 35px; */
	color: #FFFFFF;
}

.li-dday .day-left {
	background-color: #b8ccff82;
	width: 30px;
	/* margin-left: 40px; */
	color: #56A4FF;
}

.li-dday .hour-left {
	background-color: #FAEEF0;
	width: 70px;
	/* margin-left: 34px; */
	color: #FF687B;
}

.li-dday .minute-left {
	background-color: #FAEEF0;
	width: 70px;
	/* margin-left: 34px; */
	color: #FF687B;
}


.li-subj {
	grid-area: subj;
	font-size: 12px;
	justify-self: flex-start;
	align-self: flex-start;
	padding: 7px 0px 0px 0px;
}

.li-price {
	grid-area: price;
	font-size: 12px;
	font-weight: 700;
	/* margin-left: 15px; */
	justify-self: flex-end;
	align-self: center;
	margin-right: 2px;
}

.calc-result-default {
	display: flex;
	flex-direction: column;


	position: relative;
	width: 100%;
	background: #f5f1f1;
	border-radius: 30px 30px 0px 0px;
	border-radius: 30px 30px 0px 0px;
}

.cal-result-main {
	display: flex;
	flex-direction: column;
	align-self: center;
	padding: 0px 24px 0px;
	overflow: auto;

	height: 629px;
	background: #fff;
	border-radius: 30px 30px 10px 10px;
	flex: none;
	order: 1;
	flex-grow: 1;
}

.cal-result-header {
	display: flex;
	flex-direction: column;
	align-items: center;
	/* justify-content: center; */
	/* justify-content: center; */

	width: 327px;
	height: 74px;

}

.cal-result-title {
	color: #222222;
	font-weight: 700;
	font-size: 18px;
	padding-top: 28px;
	padding-top: 28px;
}

.cal-result-del {
	width: 327px;

	display: flex;
	justify-content: flex-end;
	align-items: flex-end;

	padding: 7px 8px 0px 0px;

	padding: 7px 8px 0px 0px;

	font-size: 12px;
	color: #8A8787;

}

.cal-result-del span {
	cursor: pointer;
}

.cal-result-user-list {
	width: 327px;
	height: 210px;
	height: 210px;

	display: flex;
	flex-direction: column;
	align-items: center;

	border-image: url("data:image/svg+xml,%3Csvg width='335' height='1' viewBox='0 0 335 1' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Crect x='0.25' y='0.25' width='334.5' height='0.5' stroke='black' stroke-width='0.5' stroke-dasharray='3 3'/%3E%3C/svg%3E%0A");
	border-image-slice: 200 100;
	border-image-width: 1px;
	border-image-repeat: repeat;

	margin-top: 11px;
	padding-bottom: 9px;
	overflow: auto;
	overscroll-behavior-y: none;
	scroll-behavior: smooth;
	-ms-overflow-style: none;
	/* 익스플로러, 앳지 */
	scrollbar-width: none;
	/* 파이어폭스 */

}

.cal-result-user-list::-webkit-scrollbar {
	display: none;
	/* 크롬, 사파리, 오페라 */
}

.cal-user {
	display: flex;
	justify-content: center;
	margin-top: 9px;
	margin-top: 9px;

}

.cal-user-img {
	width: 24px;
	height: 24px;
	margin-left: 5px;
	margin-left: 5px;
}

.cal-user img {
	width: 100%;
	height: 100%;
	object-fit: cover;
	border-radius: 50%;
	overflow: hidden;
	object-fit: cover;
	border-radius: 50%;
	overflow: hidden;
	display: inline-block;
	text-indent: -999px;
}

.cal-user-name {
	width: 120px;
	height: 24px;
	font-size: 14px;
	color: #333333;
	margin-left: 11px;
}

.cal-user-self-result {
	width: 111px;
	font-size: 14px;
	color: #333333;
	margin-left: 56px;
	text-align: right;
	padding-right: 7px;
	padding-right: 7px;
}


.cal-result-sum {
	width: 327px;
	height: 48px;

	display: flex;
	justify-content: space-between;

	margin-top: 18px;
	padding: 0px 7px 18px 4px;

	border-image: url("data:image/svg+xml,%3Csvg width='335' height='1' viewBox='0 0 335 1' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Crect x='0.25' y='0.25' width='334.5' height='0.5' stroke='black' stroke-width='0.5' stroke-dasharray='3 3'/%3E%3C/svg%3E%0A");
	border-image-slice: 0 0 200 0;
	border-image-width: 1px;
	border-image-repeat: repeat;

	font-size: 14px;
	color: #222222;
	font-weight: bold;
}

.cal-result-account-form {
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-top: 73px;
}

.cal-result-account-all {
	display: flex;
	justify-content: center;
	align-items: center;

}

.icon-bank-security {
	background-repeat: no-repeat;
	background-position: center;
	background-position: center;
	background-size: cover;
	background-image: url("data:image/svg+xml,%3Csvg width='12' height='14' viewBox='0 0 12 14' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M10.6 1.75C10.6 1.75 9.8 1.75 8.5 1.25C7.1 0.75 6.25 0.2 6.25 0.2L5.85 0L5.5 0.2C5.5 0.2 4.6 0.75 3.25 1.25C1.9 1.7 1.15 1.75 1.15 1.75L0.5 1.8V8.7C0.5 11.25 5.25 14 5.85 14C6.4 14 11.2 11.25 11.2 8.7V1.8L10.6 1.75ZM5.5 9.7L3 7.4L3.75 6.45L5.35 7.9L8.35 4.15L9.25 5L5.5 9.7Z' fill='%23008EFF'/%3E%3C/svg%3E%0A");

	width: 12px;
	height: 14px;

	display: inline-block;
	overflow: hidden;
	text-indent: -999px;
	margin-right: 5px;
	background-image: url("data:image/svg+xml,%3Csvg width='12' height='14' viewBox='0 0 12 14' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M10.6 1.75C10.6 1.75 9.8 1.75 8.5 1.25C7.1 0.75 6.25 0.2 6.25 0.2L5.85 0L5.5 0.2C5.5 0.2 4.6 0.75 3.25 1.25C1.9 1.7 1.15 1.75 1.15 1.75L0.5 1.8V8.7C0.5 11.25 5.25 14 5.85 14C6.4 14 11.2 11.25 11.2 8.7V1.8L10.6 1.75ZM5.5 9.7L3 7.4L3.75 6.45L5.35 7.9L8.35 4.15L9.25 5L5.5 9.7Z' fill='%23008EFF'/%3E%3C/svg%3E%0A");

	width: 12px;
	height: 14px;

	display: inline-block;
	overflow: hidden;
	text-indent: -999px;
	margin-right: 5px;
}

.icon-account-paste {
	background-repeat: no-repeat;
	background-position: center;
	background-size: cover;
	background-image: url("data:image/svg+xml,%3Csvg width='14' height='18' viewBox='0 0 14 18' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M4 2.6V12.2C4 12.6243 4.15804 13.0313 4.43934 13.3314C4.72064 13.6314 5.10218 13.8 5.5 13.8H11.5C11.8978 13.8 12.2794 13.6314 12.5607 13.3314C12.842 13.0313 13 12.6243 13 12.2V5.1936C13 4.98045 12.96 4.76945 12.8825 4.573C12.805 4.37654 12.6914 4.19859 12.5485 4.0496L10.0623 1.456C9.78202 1.16373 9.40568 1.00005 9.01375 1H5.5C5.10218 1 4.72064 1.16857 4.43934 1.46863C4.15804 1.76869 4 2.17565 4 2.6Z' stroke='%2363A0C2' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'/%3E%3Cpath d='M10 13.8V15.4C10 15.8243 9.84196 16.2313 9.56066 16.5314C9.27936 16.8314 8.89782 17 8.5 17H2.5C2.10218 17 1.72064 16.8314 1.43934 16.5314C1.15804 16.2313 1 15.8243 1 15.4V6.6C1 6.17565 1.15804 5.76869 1.43934 5.46863C1.72064 5.16857 2.10218 5 2.5 5H4' stroke='%2363A0C2' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'/%3E%3C/svg%3E%0A");
	width: 12px;
	height: 16px;

	display: inline-block;
	overflow: hidden;
	text-indent: -999px;

	cursor: pointer;
	margin-left: 5px;
	display: inline-block;
	overflow: hidden;
	text-indent: -999px;

	cursor: pointer;
	margin-left: 5px;
}

.cal-leader-account {
	font-size: 16px;
}

.cal-leader-name {
	margin-top: 21px;
	font-size: 16px;
}

.cal-result-check-form {
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-top: 60px;
}

.cal-result-check-btn {
	background-color: #63A0C2;
	width: 136px;
	height: 45px;
	border-radius: 5px;
	color: #FFFFFF;
	font-size: 14px;
	font-weight: bold;
}

.error-box {
	position: fixed;
	background-color: #FFF;
	width: 80%;
	height: 40px;
	text-align: center;
	top: 15%;
	padding: auto 0;
	line-height: 40px;
	/* left: 50%; */
	/* transform: translate(-50%, -50%); */
	/* display: flex; */
	/* align-items: center; */
	/* padding: 0 12px; */
	/* box-sizing: border-box; */
	/* border-radius: 5px; */
	/* font-size: 12px; */
	font-weight: bold;
	z-index: 9999;
}

.ani {
	animation-timing-function: ease-in;
	animation: fadeout 5s;
	animation-fill-mode: forwards;
}

@keyframes fadeout {
	from {
		opacity: 1;
	}

	to {
		opacity: 0;
	}
}
</style>