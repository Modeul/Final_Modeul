<template>
	<div class="canvas">
		<section class="favorite">
			<header class="header">
				<div class="back" @click="goback"></div>
				<div class="title">관심 목록</div>
			</header>

			<div class="stuff-list" v-for="f in list" :key="f.stuffId">
				<router-link :to="'/member/stuff/' + f.stuffId">
					<div class="d-gr li-gr m-t-13px list-cl">
						<!-- 나중에 전체를 div로 묶어서 main으로 크게 묶기 -->
						<div class="li-pic b-rad-1">
							<img v-if="f.imageName != null" class="listview-image" :src="formatImgUrl(f.imageName)" alt="img">
							<img v-else-if="f.categoryId == '1'" class="listview-image" src="/public/images/member/stuff/category1.svg"
								alt="img">
							<img v-else-if="f.categoryId == '2'" class="listview-image" src="/public/images/member/stuff/category2.svg"
								alt="img">
							<img v-else-if="f.categoryId == '3'" class="listview-image" src="/public/images/member/stuff/category3.svg"
								alt="img">
							<img v-else class="listview-image" src="/images/member/stuff/member.png" alt="img">
						</div>
						<div class="li-categ-place">

							<span class="li-categ-place-p">
								{{ f.stuffPlace }}
							</span>
						</div>
						<div class="li-dday" :class="(f.deadlineState == 0) ? 'expired' :
							(f.deadlineState == 1) ? 'day-left' :
								(f.deadlineState == 2) ? 'hour-left' : 'minute-left'">{{ f.dDay }}</div>
						<div class="li-subj">{{ f.stuffTitle }}</div>


						<div :class="isfavorite[f.stuffId] ? 'empty-heart' : 'filled-heart'" @click.prevent="toggleFavorite(f.stuffId)">
							<!-- <input type="image" v-model=heartId[f.stuffId] v-if="!heartId[f.stuffId]" src="/images/member/stuff/empty-heart.png" alt="img">
							<input type="image" v-model=heartId[f.stuffId] v-else src="/images/member/stuff/filled-heart.png" alt="img">	  -->

						</div>
					</div>
				</router-link>
			</div>
			<button class="btn-next more-list" @click="addListHandler()"> 더보기 <span> +{{ listCount }}</span></button>

		</section>
	</div>
</template>


<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko'
import { vModelCheckbox } from 'vue';
import { useUserDetailsStore } from '../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../stores/useDefaultStore';



export default {
	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			// memberId: '',
			page: '',
			list: [],
			listCount: '',
			categoryList: [],
			categoryId: '',
			isfavorite: {},
			stuffId: '',
			valiError: "",
			openModal: false,
		};
	},
	computed: {},
	methods: {
		goback() {
			this.$router.go(-1);
		},
		toggleModal() {
			this.openModal = !this.openModal;
		},
		toggleFavorite(stuffId) {
			if (this.isfavorite[stuffId]) {
				// 등록되지 않은 경우, 등록 요청 수행
				var myHeaders = new Headers();
				myHeaders.append("Content-Type", "application/json");

				var raw = JSON.stringify({
					stuffId: stuffId,
					memberId: this.memberId,
				});

				var requestOptions = {
					method: 'POST',
					headers: myHeaders,
					body: raw,
					redirect: 'follow'
				};
				fetch(`${this.defaultStore.host}/api/favorite`, requestOptions)
					.then(response => response.text())
					.then(result => {
						this.isfavorite[stuffId] = !this.isfavorite[stuffId];

					})
					.catch(error => console.log('error', error));
			} else {
				// 이미 등록된 경우, 삭제 요청 수행
				var myHeaders = new Headers();
				myHeaders.append("Content-Type", "application/json");

				var raw = JSON.stringify({

					stuffId: stuffId,
					memberId: this.memberId,
				});
				var requestOptions = {
					method: 'DELETE',
					headers: myHeaders,
					body: raw,
					redirect: 'follow'
				};
				fetch(`${this.defaultStore.host}/api/favorite`, requestOptions)
					.then(response => {
						response.text();
					})
					.then(result => {
						this.isfavorite[stuffId] = !this.isfavorite[stuffId];
					})
					.catch(error => console.log('error', error));
			}
		},

		async addListHandler() {
			this.defaultStore.loadingStatus = true; // 해당 함수 true/false 로 어디서나 추가 가능
			this.page++;
			await fetch(`${this.defaultStore.host}/api/favorites?memberId=${this.memberId}&p=${this.page}&c=${this.categoryId}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = this.formatDateList(dataList.list);
					this.listCount = dataList.listCount;
					this.categoryList = dataList.categoryList;
					this.defaultStore.loadingStatus = false;
				})
				.catch(error => console.log("error", error));
		},
		formatDateList(list) {
			if (list == null)
				return;
			let resultList = [];
			for (let item of list) {
				if (item.stuffDeadline == null)
					continue;
				const today = new dayjs().format("YYYY-MM-DD");
				const deadlineObj = dayjs(item.stuffDeadline).locale("ko");
				const isToday = (deadlineObj.format("YYYY-MM-DD") == today) ? "오늘, " : "";
				item.stuffDeadline = isToday + deadlineObj.format("M월 D일 (dd) HH시까지");
				item.dDay = dayjs().diff(deadlineObj, "day");
				if (parseInt(item.dDay) < 0) {
					item.dDay = "D" + item.dDay;
					item.deadlineState = 1;
				}
				else if (parseInt(item.dDay) == 0) {
					item.dDay = deadlineObj.diff(dayjs(), "hours");
					if (parseInt(item.dDay) > 0) {
						item.dDay = "마감 " + deadlineObj.diff(dayjs(), "hours") + "시간 전";
						item.deadlineState = 2;
					}
					else if (parseInt(item.dDay) == 0) {
						item.dDay = "1시간 내 마감";
						item.deadlineState = 3;
					}
					else {
						item.dDay = "마감";
						item.deadlineState = 0;
					}
				}
				else {
					item.dDay = "마감";
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
		},
	},
	mounted() {
		this.page = 0;
		this.memberId = this.userDetails.id;
		this.addListHandler();
	},
	components: { vModelCheckbox }
}
</script>

<style scoped>
@import "/css/component/member/stuff/component-list.css";
@import url(/css/button.css);

.canvas {
	max-width: 600px;
	padding: 0 20px;
	margin: 0 auto;
	min-width: 360px;
}
/* .favorite {
	display: flex;
	flex-direction: column;
	padding: 0;
	position: relative;
	
	height: 740px;
	background: #FFFFFF;
	margin: 0 auto;
} */

.header {
	display: flex;
	flex-direction: row;
	align-items: center;
	padding: 0px;
	gap: 10px;
	width: 100%;
	margin-top: 25px;
	margin-bottom: 12px;
}

.back {
	background-image: url("data:image/svg+xml,%3Csvg width='16' height='16' viewBox='0 0 16 16' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M16 7H3.83L9.42 1.41L8 0L0 8L8 16L9.41 14.59L3.83 9H16V7Z' fill='black'/%3E%3C/svg%3E%0A");
	width: 23.04px;
	height: 24px;
	margin-top: 9px;
}

.favorite .header .title {
	margin: 0 auto;
	padding-right: 23px;
}

.stuff-list {

	padding: 12px 0px 16px 0px;
	border-bottom: 0.3px #d5d5d5 solid;
	box-sizing: border-box;
}

.stuff-list:hover {
	transition: 0.3s;
	background-color: #fbfbfb;
	border-radius: 5px;
}

.li-pic {
	grid-area: pic;
	width: 68px;
	height: 68px;
	display: inline-block;
	overflow: hidden;
	/* text-indent: -999px; */
	justify-self: center;
	align-self: center;
}

.li-gr {
	
	grid-template-columns: 68px 8px minmax(174px, auto) 0px 70px;
	grid-template-rows: 36px 12px 12px;
	grid-template-areas:
		"pic . subj . dday"
		"pic . . . ."
		"pic . ct . heart ";

	align-items: center;
	
}

.filled-heart {
	grid-area: heart;
	background-image: url("/images/member/stuff/filled-heart.png");
	background-repeat: no-repeat;
	background-position: center;
	background-size: 100%;
	position: relative;
	right: -45px;
	width: 24px;
	height: 24px;
	display: inline-block;
	text-indent: -9999px;
}

.empty-heart {
	grid-area: heart;
	background-image: url("/images/member/stuff/empty-heart.png");
	background-repeat: no-repeat;
	background-position: center;
	background-size: 100%;
	position: relative;
	right: -45px;
	width: 24px;
	height: 24px;
	display: inline-block;
	text-indent: -9999px;
}</style>