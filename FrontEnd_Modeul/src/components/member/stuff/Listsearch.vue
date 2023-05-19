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
			categoryId: '',
			query: '',
			queryisVal: true,
			listCount: '',
			dongCode: ''
		}
	},
	methods: {
		searchInput(e) {
			this.page = 1;
			e.preventDefault();
			this.query = e.target.value;

			fetch(`${this.defaultStore.host}/api/stuffs?p=${this.page}&q=${this.query}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = this.formatDateList(dataList.list);

				}).catch(error => console.log('error', error));
		},

		async addListHandler() {
			this.defaultStore.loadingStatus = true; // 해당 함수 true/false 로 어디서나 추가 가능
			// setTimeout(() => { this.defaultStore.loadingStatus = false; }, 400); //settimout은 지워도 됨

			this.page++;
			await fetch(`${this.defaultStore.host}/api/stuffs?p=${this.page}&c=${this.categoryId}&dc=${this.dongCode}&q=${this.query}`)
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
		this.page = 0;
		this.addListHandler();
	}
}
</script>
<style scoped>
@import "/css/component/member/stuff/component-list.css";
@import "/css/component/member/stuff/component-list-search.css";

.canvas {
		max-width: 600px;
		padding: 0 20px;
		margin: 0 auto;
	}
.search-header{
	display: flex;
	align-items: center;
	justify-content: flex-end;
}
</style>
<template>
	<section class="canvas b-rad-2">

		<!-- 검색창 들어가는 부분 -->
		<header class="search-header">
			<div>
				<router-link to="/member/stuff/list" class="icon icon-back">뒤로가기</router-link>
			</div>

			<div class="search-container">
				<div class="d-fl d-b-none search-form">
					<h1 class="icon search-dodbogi m-l-6px">돋보기</h1>
					<input id="search-bar" class="search-input m-l-6px" name="q" @keyup.enter="searchInput" placeholder="검색어 입력">
				</div>
			</div>
		</header>

		<main>
			<div class="stuff-list" v-for="stuff in list">
				<router-link :to="'./' + stuff.id">
					<div class="d-gr li-gr m-t-13px list-cl">
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
							<span class="li-member-limit"> 1</span>
							/ {{ stuff.numPeople }} 명
						</div>

					</div>
				</router-link>
			</div>


			<button class="btn-next more-list" @click="addListHandler"> 더보기 </button>
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
		</main>
	</section>
</template>

