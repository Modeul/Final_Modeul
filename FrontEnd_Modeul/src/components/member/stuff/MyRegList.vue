<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko'
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../../stores/useDefaultStore';

export default {

	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			page: '',
			list: [],
			categoryList: [],
			categoryId: '',
			listCount: '',
			myMemberId: '',
		};
	},
	computed: {
	},
	methods: {
		categoryHandler(e) {
			this.page = 1;
			this.categoryId = e.target.value;
			fetch(`${this.defaultStore.host}/api/stuffs?p=${this.page}&c=${this.categoryId}&id=${this.myMemberId}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = this.formatDateList(dataList.list);
					this.listCount = dataList.listCount;
					this.categoryList = dataList.categoryList;
				}).catch(error => console.log('error', error));
		},
		async addListHandler() {

			this.defaultStore.loadingStatus = true; // 해당 함수 true/false 로 어디서나 추가 가능
			// setTimeout(() => { this.defaultStore.loadingStatus = false; }, 400); //settimout은 지워도 됨

			this.page++;
			await fetch(`${this.defaultStore.host}/api/stuffs?p=${this.page}&c=${this.categoryId}&id=${this.myMemberId}`)
				// .then(response => {
				// 	console.log(response)
				// 	return response.json()})
				.then(response => response.json())
				.then(dataList => {
					this.list = this.formatDateList(dataList.list);
					this.listCount = dataList.listCount;
					this.categoryList = dataList.categoryList;
					this.defaultStore.loadingStatus = false;
				})
				.catch(error => console.log('error', error));

		},
		formatDateList(list) {
			if (list == null)
				return;
			let resultList = [];
			for (let item of list) {
				if (item.deadline == null)
					continue;
				const today = new dayjs().format('YYYY-MM-DD');

				const deadlineObj = dayjs(item.deadline).locale('ko');
				const isToday = (deadlineObj.format('YYYY-MM-DD') == today) ? '오늘, ' : ''
				item.deadline = isToday + deadlineObj.format("M월 D일 (dd) HH시까지");

				// deadlineState
				// 0: 마감 -> (마감) // 회색
				// 1: 1일 이상 남음 -> (D-n) // 파랑? 초록?
				// 2: 당일 마감 -> (마감 n 시간 전) // 초록? 주황? 
				// 3: 1시간 내 마감 -> (1시간 내 마감)  // 빨강?

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
		},
		formatImgUrl(imgDir){
			if(!imgDir)
				return imgDir;
			if(imgDir.substr(0, 4) == 'http')
				return imgDir
			else
				return '/images/member/stuff/' + imgDir
		}
	},
	mounted() {
		this.myMemberId = this.userDetails.id;
		this.page = 0;
		this.addListHandler();

	}
}
</script>

<template>
	<section class="canvas p-rel b-rad-2">
		<header class="header">
			<div>
				<router-link to="/member/mypage" class="back"></router-link>
			</div>
			<div class="title">내가 쓴 게시글</div>
		</header>

		<nav>
			<div class="header-categ-box">
				<div>
					<button class="header-categ" @click="categoryHandler" name="c" :class="(this.categoryId != '')?'header-categ':'default'">전체</button>
				</div>

				<div v-for="c in categoryList">
					<button  @click="categoryHandler" name="c" :value="c.id" :class="(this.categoryId == c.id)?'selected':'header-categ'" >{{ c.name }}</button>
				</div>
			</div>
		</nav>

		<!-- 나중에 onclick 이벤트 하트 부분만 빼고 넣기 -->
		<main>
			<div class="stuff-list" v-for="stuff in list">
				<router-link :to="'../stuff/' + stuff.id">
					<div class="d-gr li-gr m-t-13px list-cl">
						<!-- 나중에 전체를 div로 묶어서 main으로 크게 묶기 -->
						<div class="li-pic b-rad-1">
							<img v-if="stuff.imageName != null" class="listview-image" :src="formatImgUrl(stuff.imageName)"
								alt="img">
							<img v-else-if="stuff.categoryId == '1'" class="listview-image" src="/images/member/stuff/category1.svg"
								alt="img">
							<img v-else-if="stuff.categoryId == '2'" class="listview-image" src="/images/member/stuff/category2.svg"
								alt="img">
							<img v-else-if="stuff.categoryId == '3'" class="listview-image" src="/images/member/stuff/category3.svg"
								alt="img">
							<img v-else class="listview-image" src="/images/member/stuff/member.png" alt="img">
						</div>
						<div class="li-categ-place">
							<span class="li-categ-place-categoryName">
								{{ stuff.categoryName }}
							</span>
							<span class="li-categ-place-p">
								{{ stuff.place }}
							</span>
						</div>
						<div class="li-dday" :class="(stuff.deadlineState == 0) ? 'expired' :
							(stuff.deadlineState == 1) ? 'day-left' :
								(stuff.deadlineState == 2) ? 'hour-left' : 'minute-left'">{{ stuff.dDay }}</div>
						<div class="li-subj">{{ stuff.title }}</div>
						<div class="li-member">
							<span class="li-member-limit"> {{ stuff.participantCount }} </span>
							/ {{ stuff.numPeople }} 명
						</div>
						<!-- <div class="li-place">{{ stuff.place }}</div> -->
						<!-- <div class="li-date">{{ stuff.deadline }} | {{'D' + stuff.dDay }}</div> -->

						<!-- <div class="li-date">{{'D' + stuff.dDay }}</div> -->
					</div>
				</router-link>
			</div>

			<button class="btn-next more-list" @click="addListHandler"> 더보기 <span> + {{ listCount }}</span></button>
		</main>
	</section>
</template>


<style scoped>
@import "/css/component/member/stuff/component-list.css";
@import "/css/button.css";

.header {
	display: flex;
	flex-direction: row;
	align-items: center;
	padding: 0px;
	gap: 10px;
	width: 100%;
	margin-top: 25px;
}

.back {
	background-image: url("data:image/svg+xml,%3Csvg width='16' height='16' viewBox='0 0 16 16' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M16 7H3.83L9.42 1.41L8 0L0 8L8 16L9.41 14.59L3.83 9H16V7Z' fill='black'/%3E%3C/svg%3E%0A");
	width: 23.04px;
	height: 24px;
	margin-top: 9px;
}

.title {
	color: #333;
	margin: 0 auto;
	padding-right: 23px;
}

.canvas {
	max-width: 600px;
	padding: 0 20px;
	margin: 0 auto;
	min-width: 360px
}
</style>