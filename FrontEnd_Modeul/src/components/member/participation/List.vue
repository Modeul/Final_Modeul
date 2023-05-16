<template>
	<section class="canvas p-rel">

		<header class="header">
			<div>
				<router-link to="/member/mypage" class="back"></router-link>
			</div>
			<div class="title">참여 목록</div>
		</header>

		<nav>
			<h1 class="m-t-2 f-size-2 f-weight">공동구매에<br><span class="f-color-2">{{ stuffCount }}건</span> 참여하고 있어요</h1>

			<div class="header-categ-box">
				<div>
					<button class="header-categ" @click="categoryHandler" name="c" :class="(this.categoryId != '')?'header-categ':'default'">전체</button>
				</div>

				<div v-for="c in categoryList">
					<button  @click="categoryHandler" name="c" :value="c.id" :class="(this.categoryId == c.id)?'selected':'header-categ'" >{{ c.name }}</button>
				</div>
			</div>
		</nav>

		<main>
			<div class="stuff-list" v-for="p in participationList">
				<router-link :to="'../stuff/' + p.stuffId">
					<div class="d-gr li-gr m-t-13px list-cl">

						<div class="li-pic b-rad-1">
							<img v-if="p.imageName != null" class="listview-image" :src="'/images/member/stuff/' + p.imageName"
								alt="img">
							<img v-else-if="p.categoryId == '1'" class="listview-image" src="/images/member/stuff/category1.svg"
								alt="img">
							<img v-else-if="p.categoryId == '2'" class="listview-image" src="/images/member/stuff/category2.svg"
								alt="img">
							<img v-else-if="p.categoryId == '3'" class="listview-image" src="/images/member/stuff/category3.svg"
								alt="img">
							<img v-else class="listview-image" src="/images/member/stuff/member.png" alt="img">
						</div>
						<div class="li-categ-place">
							<span class="li-categ-place-categoryName">
								{{ p.categoryName }}
							</span>
							<span class="li-categ-place-p">
								{{ p.stuffPlace }}
							</span>
						</div>
						<div class="li-dday" :class="(p.deadlineState == 0) ? 'expired' :
							(p.deadlineState == 1) ? 'day-left' :
								(p.deadlineState == 2) ? 'hour-left' : 'minute-left'">{{ p.dDay }}</div>
						<div class="li-subj">{{ p.stuffTitle }}</div>
						<div class="li-member">
							<span class="li-member-limit"> {{ p.participantCount }}</span>
							/ {{ p.stuffNumPeople }} 명
						</div>
					</div>
				</router-link>
			</div>

			<button class="btn-next more-list" @click="addListHandler"> 더보기 <span> {{ listCount }}</span></button>
		</main>
		<nav class="navi-bar d-fl-jf">
			<div class="navi-icon">
				<router-link to="/member/stuff/list" class="icon icon-home">home</router-link>
			</div>
			<div class="navi-icon">
				<router-link to="/member/stuff/recommends" class="icon icon-crawling">search</router-link>
			</div>
			<div>
				<router-link to="/member/stuff/reg" class="reg-stuff"></router-link>
			</div>
			<div class="navi-icon">
				<router-link to="/member/participation/list" class="icon icon-chat">chat</router-link>
			</div>
			<div class="navi-icon">
				<router-link to="/member/mypage" class="icon icon-info">mypage</router-link>
			</div>
		</nav>
	</section>
</template>

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
			memberId: '',
			page: '',
			categoryList: [],
			participationList: [],
			categoryId: '',
			stuffCount: '',
			memberCount: '',
			//listCount:''
		}
	},
	methods: {
		goback() {
			this.$router.go(-1);
		},
		categoryHandler(e) {
			this.page = 1;
			this.categoryId = e.target.value;
			console.log(this.categoryId);
			fetch(`${this.defaultStore.host}/api/participations/${this.memberId}?p=${this.page}&c=${this.categoryId}`)
				.then(response => response.json())
				.then(dataList => {
					this.participationList = this.formatDateList(dataList.list);
					this.categoryList = dataList.categoryList;
					// this.listCount = dataList.listCount;    // 이거 API 하나 더 추가
					console.log(this.list)
				}).catch(error => console.log('error', error));
		},
		async addListHandler() {
			this.defaultStore.loadingStatus = true; // 해당 함수 true/false 로 어디서나 추가 가능
			// setTimeout(() => { this.defaultStore.loadingStatus = false; }, 400); //settimout은 지워도 됨

			this.page++;
			await fetch(`${this.defaultStore.host}/api/participations/${this.memberId}?p=${this.page}&c=${this.categoryId}`)
				.then(response => response.json())
				.then(dataList => {
					console.log(dataList);
					this.participationList = this.formatDateList(dataList.list);
					this.categoryList = dataList.categoryList;
					this.stuffCount = dataList.stuffCount;
					// this.listCount = dataList.listCount;
					console.log(this.participationList);
					this.defaultStore.loadingStatus = false;
				})
				.catch(error => console.log('error', error));

		},
		formatDateList(participationList) {
			if (participationList == null)
				return;
			let resultList = [];
			for (let item of participationList) {
				if (item.stuffDeadline == null)
					continue;
				const today = new dayjs().format('YYYY-MM-DD');

				const deadlineObj = dayjs(item.stuffDeadline).locale('ko');
				const isToday = (deadlineObj.format('YYYY-MM-DD') == today) ? '오늘, ' : ''
				item.stuffDeadline = isToday + deadlineObj.format("M월 D일 (dd) HH시까지");

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
	},
	mounted() {
		this.memberId = this.userDetails.id;
		this.page = 0;
		this.addListHandler();
	},
}
</script>

<style scoped>
@import "/css/component/member/stuff/component-list.css";
@import "/css/component/member/participation/component-parti-list.css";

.canvas {
	max-width: 600px;
	padding: 0 20px;
	margin: 0 auto;
	min-width: 360px
}

.li-gr {
    grid-template-columns: 70px 8px minmax(174px, auto) 0px 70px;
}

.back {
	background-image: url("data:image/svg+xml,%3Csvg width='16' height='16' viewBox='0 0 16 16' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M16 7H3.83L9.42 1.41L8 0L0 8L8 16L9.41 14.59L3.83 9H16V7Z' fill='black'/%3E%3C/svg%3E%0A");
	width: 23.04px;
	height: 24px;
	margin-top: 9px;
}

.canvas .header {
	display: flex;
	flex-direction: row;
	align-items: center;
	padding: 0px;
	gap: 10px;
	width: 100%;
	margin-top: 25px;
}

.header .title {
	margin: 0 auto;
	padding-right: 23px;
}
.f-weight{
	font-weight: 500;
}
</style>


