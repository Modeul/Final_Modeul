<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko';
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../../stores/useDefaultStore';

import PcHeader from './PcHeader.vue'

export default {
	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			showMap: true,
			mapStatus: true,
			memberId: '',
			stuffId: '',
			openModal: false,
			openModal2: false,
			openModal3: false,
			openModal4: false,
			stuff: {},
			stuffPrice: 0,
			category: {},
			imageList: '',
			participantList: [],
			memberCount: '',
			isCheckParticipation: '',
			dialog: false,
			participantInfo: {},
			memberInfo: '',
			stuffAuthority: false,
			stuffView: '',
			Report: {
				detail: '',
			},
			favoriteList: [],
			favoriteInfo: {},
			//heartStuffId: '',
			isfavorite: false,
			list: [],
			zzimModalMsg: "",
			favorOpenModal: false,
			stuffUser: false,
			isCheckNumPeople: true,
			isDutchComplete: ''
		};
	},
	components: {
		PcHeader
	},
	methods: {
		/* 뒤로가기 : 이전페이지로 이동 */
		goback() {
			this.$router.push('list');
		},
		/* 모달 이벤트 */
		modalHandler() {
			this.openModal = !this.openModal;
		},
		modalHandler2() {
			this.openModal2 = !this.openModal2;
		},
		modalHandler3() {
			this.openModal3 = !this.openModal3;
		},
		modalHandler4() {
			this.openModal4 = !this.openModal4;
		},

		imageZoomInHandler() {
			// console.log("zoom-in");
		},
		imageZoomOutHandler() {
			// console.log("zoom-out");
		},
		formatImgUrl(imgDir){
			if(!imgDir)
				return imgDir;
			if(imgDir.substr(0, 4) == 'http')
				return imgDir
			else
				return '/images/member/stuff/' + imgDir
		},
		formatDateStuff() {
			const deadlineObj = dayjs(this.stuff.deadline).locale('ko');
			this.stuff.deadline = deadlineObj.format("M월 D일 (dd) HH시까지");

			this.stuff.dDay = dayjs().diff(deadlineObj, 'day');
			if (parseInt(this.stuff.dDay) < 0) {
				this.stuff.dDay = 'D' + this.stuff.dDay;
				this.stuff.deadlineState = 1;
			}
			else if (parseInt(this.stuff.dDay) == 0) {
				this.stuff.dDay = deadlineObj.diff(dayjs(), 'hours')
				if (parseInt(this.stuff.dDay) > 0) {
					this.stuff.dDay = '마감 ' + deadlineObj.diff(dayjs(), 'hours') + '시간 전'
					this.stuff.deadlineState = 2;
				}
				else if (parseInt(this.stuff.dDay) == 0) {
					this.stuff.dDay = '1시간 내 마감';
					this.stuff.deadlineState = 3;
				}
				else {
					this.stuff.dDay = '마감';
					this.stuff.deadlineState = 0;
				}
			}
			else {
				this.stuff.dDay = '마감';
				this.stuff.deadlineState = 0;
			}
		},
		/* 글 내용 br */
		getContent(content) {
			return (content || "").split('\n').join('<br>');
		},
		deleteStuff() {
			var requestOptions = {
				method: 'DELETE',
				redirect: 'follow'
			};
			this.defaultStore.loadingStatus = true;

			fetch(`${this.defaultStore.host}/api/stuff/${this.$route.params.id}`, requestOptions)
				.then(response => response.text())
				.then(result => {
					this.defaultStore.loadingStatus = false
					this.$router.push("/member/stuff/list");
				})
				.catch(error => console.log('error', error));
		},

		reportStuff() {
			var requestOptions = {
				method: 'POST',
				redirect: 'follow',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({
					stuffId: this.$route.params.id,
					memberId: this.userDetails.id,
					detail: this.Report.detail,
				})
			};

			fetch(`${this.defaultStore.host}/api/reports/stuff`, requestOptions)
				.then(response => response.text())
				.then(result => {
					if (result === 'OK') {
						this.modalHandler3();
						this.modalHandler4();
					}
				})
				.catch(error => console.log('error', error));
		},

		/* 공구상품 글에 참여!! */
		participationHandler() {
			var myHeaders = new Headers();
			myHeaders.append("Content-Type", "application/json");

			var raw = JSON.stringify({
				memberId: this.userDetails.id,
				stuffId: this.stuff.id,
			});

			var requestOptions = {
				method: 'POST',
				headers: myHeaders,
				body: raw,
				redirect: 'follow'
			};

			fetch(`${this.defaultStore.host}/api/participation`, requestOptions)
				.then(response => response.text())
				.then(result => {
					this.loadParticipationList();
					this.isCheckParticipation = !this.isCheckParticipation;
					this.dialog = true;
				})
				.catch(error => console.log('error', error));
		},
		/* 참여 인원 추가하면 참여 멤버 실시간 업데이트하기 */
		loadParticipationList() {
			var requestOptions = {
				method: 'GET',
				redirect: 'follow'
			};
			fetch(`${this.defaultStore.host}/api/participation/stuff/${this.$route.params.id}`, requestOptions)
				.then(response => response.json())
				.then(data => {
					this.participantList = data.list;
					this.memberCount = data.memberCount;
				})
				.catch(error => console.log('error', error));
		},
		loadParticipantInfo() {
			fetch(`${this.defaultStore.host}/api/chat/${this.$route.params.id}/${this.userDetails.id}`)
				.then(response => response.json())
				.then(data => {
					this.participantInfo = data.memberInfo;
				})
				.catch(error => console.log('error', error));
		},
		// 참여버튼 참여한지에 따라 초기값 설정
		checkParticipation() {
			if (this.participantInfo)
				this.isCheckParticipation = true;
			else
				this.isCheckParticipation = false;
		},
		checkStuffLeader() {
			if (this.stuffView.memberId === this.memberInfo.id) {
				this.stuffAuthority = !this.stuffAuthority;
			}
		},
		/* 공구상품 항목의 참여 취소 요청 */
		cancelParticipationHandler() {
			var requestOptions = {
				method: 'DELETE',
				redirect: 'follow'
			};

			fetch(`${this.defaultStore.host}/api/participation/${this.$route.params.id}/${this.userDetails.id}`, requestOptions)
				.then(response => response.text())
				.then(result => {
					this.loadParticipationList();
					this.isCheckParticipation = !this.isCheckParticipation;
					this.dialog = true;
				})
				.catch(error => console.log('error', error));
		},
		drawMap() {
			const mapContainer = document.getElementById('map');

			let coords = new daum.maps.LatLng(this.stuff.coordY, this.stuff.coordX);
			let mapOption = {
				center: coords,
				level: 5
			};

			let map = new daum.maps.Map(mapContainer, mapOption);

			let content = `<div class="map-content">
				<div class="map-content-items">${this.stuff.place}</div>
				<div class="map-content-items">
					<a href="https://map.kakao.com/link/map/${this.stuff.place},${this.stuff.coordY},${this.stuff.coordX}"target="_blank">큰 지도</a>
				</div>
				<div class="map-content-items">
				<a href="https://map.kakao.com/link/to/${this.stuff.place},${this.stuff.coordY},${this.stuff.coordX}"target="_blank">길 찾기</a>
			</div>
			</div>`;


			var customOverlay = new kakao.maps.CustomOverlay({
				position: coords,
				content: content
			});


			map.relayout();
			map.setCenter(coords);
			customOverlay.setMap(map);
		},

		toggleMap() {
			let map = document.querySelector("#map");
			if (this.showMap) {
				map.style.height = '300px';
				this.showMap = !this.showMap;

				if (this.mapStatus) {
					setTimeout(() => {
						this.drawMap();
						this.mapStatus = false;
					}, 500);
				}


			} else {
				map.style.height = 0;
				this.showMap = !this.showMap;
			}

		},
		async loadParticipant() {
			const response = await fetch(`${this.defaultStore.host}/api/member/${this.userDetails.id}`);
			const data = await response.json();
			this.memberInfo = data;
		},

		loadFavoriteInfo() {
			fetch(`${this.defaultStore.host}/api/favorite/${this.$route.params.id}/${this.userDetails.id}`)
				.then(response => response.json())
				.then(data => {
					this.favoriteInfo = data.favoriteInfo;
				})
				.catch(error => console.log('error', error));
		},

		toggleFavorite() {
			if (this.isfavorite) {
				// 이미 등록된 경우, 삭제 요청 수행
				var myHeaders = new Headers();
				myHeaders.append("Content-Type", "application/json");

				var raw = JSON.stringify({

					stuffId: this.stuff.id,
					memberId: this.userDetails.id,
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
						// "관심삭제"
						this.isfavorite = !this.isfavorite;
					})
					.catch(error => console.log('error', error));
			} else {
				// 등록되지 않은 경우, 등록 요청 수행
				var myHeaders = new Headers();
				myHeaders.append("Content-Type", "application/json");

				var raw = JSON.stringify({
					stuffId: this.stuff.id,
					memberId: this.userDetails.id,
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
						this.isfavorite = !this.isfavorite;
						// "관심등록"
					})
					.catch(error => console.log('error', error));
				this.zzimModalMsg = "  관심목록에 추가되었습니다."
				this.favorOpenModal = true;
			}
		},
		checkFavoriteList() {
			if (this.favoriteInfo)
				this.isfavorite = true;
			else
				this.isfavorite = false;
		},
		aniEndHandler() {
			this.favorOpenModal = false;
		},
		checkStuffUser() {
			this.stuffUser = this.userDetails.id !== this.stuff.memberId ? false : true;
		},
		checkNumPeople(){
			if(this.memberCount < parseInt(this.stuff.numPeople)){
				this.isCheckNumPeople = true;
			}
			else{
				this.isCheckNumPeople = false;
			}
		},
		async checkDutchComplete() {
			const response = await fetch(`${this.defaultStore.host}/api/dutch/${this.$route.params.id}`)
			const dataList = await response.json();
			if(dataList.list.length > 0)
				this.isDutchComplete = true;
			else
				this.isDutchComplete = false;
		},
		urlHandler(url) {
			// const httpPattern = /^http(s):\/\//;
			// const wwwPattern = /^www\./;
			if(url.startsWith('w')){
				url = 'https://' + url;
			}
			// let output = false;
			// if (wwwPattern.test(url))
				// output = 'http://' + url;
			// else if (httpPattern.test(url))
			// 	output = url;
			// return output;
			return url;
		}
	},
	computed: {

	},
	created() {
		this.loadParticipant();
		this.loadParticipationList();
		this.loadParticipantInfo();
		this.loadFavoriteInfo();
	},
	async mounted() {
		this.defaultStore.loadingStatus = true;
		await fetch(`${this.defaultStore.host}/api/stuff/${this.$route.params.id}`)
			.then((response) => response.json())
			.then((data) => {
				this.stuff = data.stuff;
				this.stuffPrice = Number(data.stuff.price).toLocaleString();
				this.category = data.category;
				this.imageList = data.imageList;
				this.participantList = data.participantList;
				this.memberCount = data.memberCount;
				this.formatDateStuff();
				this.stuffView = data.stuffView;
				this.favoriteList = data.favoriteView;
				this.defaultStore.loadingStatus = false;
				this.checkNumPeople();
			})
			.catch((error) => console.log("error", error));

		this.checkParticipation();
		this.checkStuffLeader();
		this.checkFavoriteList();
		this.checkStuffUser();
		this.checkDutchComplete()
	},
};
</script>

<template>
	<PcHeader></PcHeader>

	<!-- detail : flex-container -->
	<div class="detail">


		<!-- detail - item1  -->
		<main>
			<!-- detail-main : flex-container -->
			<header class="detail-header">
				<router-link to="list" class="icon icon-back" @click.prevent="goback">뒤로가기</router-link>

				<!-- 수정/삭제 모달 버튼 -->

				<i @click="modalHandler" class="icon-edit"></i>
				<!-- 모달 배경 -->
				<div v-if="openModal">
					<div class="icon-edit2" v-if="this.stuffUser">
						<div class="box">
							<router-link :to="'./edit/' + stuff.id">
								<div class="icon-edit3">
									<div class="icon"></div>수정 하기
								</div>
							</router-link>
							<div @click="modalHandler2" class="icon-edit4">
								<div class="icon"></div>삭제 하기
							</div>
						</div>
					</div>
					<div class="icon-report" v-else @click="modalHandler3">
						<div class="icon"></div>신고 하기
					</div>
					<!-- 취소 확인 모달 -->
					<div v-if="openModal2" class="black-bg">
						<div class="delete-box">
							<div class="delete-box-1">정말로 삭제하시겠습니까?</div>
							<div class="delete-box-2">
								<div @click="deleteStuff" class="delete-box-3">삭제</div>
								<div @click="modalHandler2" class="delete-box-4">취소</div>
							</div>
						</div>
					</div>
				</div>

				<div v-if="openModal3" class="black-bg">
					<div class="report-box">
						<div class="delete-box-1">신고 하시겠습니까?</div>
						<div>사유</div>
						<textarea maxlength="100" placeholder="100자 이하" v-model="Report.detail"></textarea>
						<div class="delete-box-2">
							<div @click="reportStuff" class="delete-box-3">신고</div>
							<div @click="modalHandler3" class="delete-box-4">취소</div>
						</div>
					</div>
				</div>

				<div v-if="openModal4" class="black-bg">
					<div class="delete-box">
						<div class="delete-box-1">신고 완료</div>
						<div class="delete-box-2">
							<div @click="modalHandler4" class="delete-box-3">닫기</div>
						</div>
					</div>
				</div>
			</header>
			<div class="detail-main">

				<!-- detail-img : detail-main - item1 -->

				<div class="detail-img">
					<v-carousel v-if="imageList.length != 0" hide-delimiters show-arrows="hover" height="100%">
						<v-carousel-item v-for="img in imageList" :src="formatImgUrl(img.name)"></v-carousel-item>
					</v-carousel>
					<div v-else class="noImg"></div>
				</div>
				<div class="detail-content-wrap">
					<!-- detail-heading : detail-main - item2 -->
					<section class="canvas detail-heading">
						<h1 class="d-none">heading</h1>
						<div class="d-fl detail-edit">
							<div class="detail-top">
								<div class="detail-category" :value="stuff.categoryId">{{ category.name }}</div>
								<div class="detail-status" :class="(stuff.deadlineState == 0) ? 'expired' :
									(stuff.deadlineState == 1) ? 'day-left' :
										(stuff.deadlineState == 2) ? 'hour-left' : 'minute-left'">{{ stuff.dDay }}</div>
							</div>

							<div :class="isfavorite ? 'filledHeart' : 'emptyHeart'" @click.prevent="toggleFavorite"></div>

						</div>
						<p class="detail-heading-title">{{ stuff.title }}</p>
						<!-- <div class="d-fl">
							<div class="ed-text"><router-link :to="'./'+stuff.id+'/edit/'">수정</router-link></div>
							<div class="ed-text" @click="deleteStuff">삭제</div>
						</div> -->
						<div class="detail-price">{{ stuffPrice }}원</div>

					</section>
					<!-- detail-info : detail-main - item3 -->
					<section class="canvas detail-info">
						<h1 class="d-none">info</h1>
						<div class="detail-in">
							<div class="detail-info-title">인원</div>
							<div class="detail-info-txt">{{ memberCount }} / {{ stuff.numPeople }} 명</div>
						</div>
						<div class="detail-in">
							<div class="detail-info-title">기한</div>
							<div class="detail-info-txt">{{ stuff.deadline }}</div>
						</div>
						<div class="detail-in">
							<div class="detail-info-title">장소</div>
							<div class="detail-info-txt">{{ stuff.place }}</div>
						</div>
						<div class="detail-in">
							<div class="detail-info-title">링크</div>
							<div v-if="stuff.url" class="detail-info-txt"><a class="detail-info-txt-a" :href="urlHandler(stuff.url)" target="_blank">{{ stuff.url }}</a></div>
							<!-- <div v-if="stuff.url" class="detail-info-txt"><a class="detail-info-txt-a" :href="stuff.url" target="_blank">{{stuff.url}}</a></div> -->
							<div v-else class="detail-info-txt"> 게시된 링크가 없습니다. </div>
						</div>

					</section>
					<section class="canvas map">
						<div class="map-txt" @click="toggleMap" v-if="showMap">지도 열기</div>
						<div class="map-txt" @click="toggleMap" v-else>지도 닫기</div>
						<div id="map"></div>
					</section>
					<!-- detail-writing : detail-main - item4 -->
					<section class="canvas detail-writing">
						<h1 class="d-none">writing</h1>
						<!-- <p class="detail-paragraph">
									            {{ stuff.content }}
									          </p> -->
						<p v-html="getContent(stuff.content)" class="detail-paragraph"></p>
					</section>
				</div>
			</div>
		</main>
		<div class="favorModal" @animationend="aniEndHandler">
			<div v-if="favorOpenModal == true">
				<div class="error-box">{{ zzimModalMsg }}</div>
				<router-link to="/member/mypage/favorite">
					<div class="error-gotofavor">관심목록 보기</div>
				</router-link>
			</div>
		</div>

		<!-- detail-join : detail - itme2  -->

		<section class="canvas detail-join">
			<div class="detail-join-title">참여중인 사람</div>
			<div class="detail-join-wrap">
				<v-sheet>
					<v-slide-group>
						<v-slide-group-item v-for="m in participantList" :key="m" v-slot="{ isSelected, toggle }">
							<button>
								<img :src="'/images/member/' + m.memberImage">
							</button>
						</v-slide-group-item>
					</v-slide-group>

				</v-sheet>

				<!-- ** vuex와 store를 이용해서 참여 중이면 취소 버튼 보이게 상태 유지 값 만들기 -->
				<div class="detail-join-button-wrap">
					<button class="detail-join-button" v-if="!isCheckParticipation && isCheckNumPeople && !isDutchComplete" @click="participationHandler">
						참여하기
					</button>
					<button class="detail-join-end-button" v-else-if="!isCheckParticipation && (!isCheckNumPeople || isDutchComplete)" @click="">
						참여마감
					</button>
					<div class="join-button-wrap" v-else-if="isCheckParticipation">
						<router-link :to="'../../chat/' + stuff.id" class="detail-chat-button">채팅하기</router-link>
						<button class="detail-cancel-button" @click="cancelParticipationHandler" v-if="!stuffAuthority && !isDutchComplete">
							참여취소
						</button>
					</div>
				</div>


				<v-dialog v-model="dialog" width="auto" v-if="isCheckParticipation">
					<v-card>
						<v-card-text class="participationcard">
							참여되었습니다.
						</v-card-text>
						<v-card-actions>
							<v-btn color="#63A0C2" block @click="dialog = false">확인</v-btn>
						</v-card-actions>
					</v-card>
				</v-dialog>

				<v-dialog v-model="dialog" width="auto" v-if="!isCheckParticipation">
					<v-card>
						<v-card-text>
							취소되었습니다.
						</v-card-text>
						<v-card-actions>
							<v-btn color="#63A0C2" block @click="dialog = false">닫기</v-btn>
						</v-card-actions>
					</v-card>
				</v-dialog>
			</div>
		</section>
	</div>
</template>

<style scoped>
@import "/css/component/member/stuff/component-detail.css";

@media (min-width: 768px) {
	.detail-main {
		display: flex;
		flex-direction: row;
	}

	main {
		max-width: 1050px;
		margin: 0 auto;
	}

	.detail-img {
		width: 50%;
		max-height: 525px;
	}

	.detail-content-wrap {
		width: 50%;
		margin: auto 0;
	}

	.icon.icon-back {
		display: none;
	}

	#map {
		width: 470px;
		border-radius: 10px;
	}
}

.v-slide-group button {
	box-sizing: border-box;
	padding: 16px;
	width: 52px;
}

.v-slide-group img {
	width: 36px;
	height: 36px;
	border-radius: 50%;
}

.participation {
	display: flex;
	justify-content: space-around;
}

.v-img__img--contain {
	object-fit: cover;
}

/* Vuetify css 변경하는 v-deep 이용하는 방법!! : 
개발자모드에서 보여지는 css 계층의 값 변경 가능*/

.v-dialog :deep {
	font-size: 14px;
}

.filledHeart {
	background-image: url("/images/member/stuff/filled-heart.png");
	background-repeat: no-repeat;
	background-position: center;
	background-size: 100%;
	width: 19px;
	height: 19px;
	display: inline-block;
	text-indent: -9999px;
}

.emptyHeart {
	background-image: url("/images/member/stuff/empty-heart.png");
	background-repeat: no-repeat;
	background-position: center;
	background-size: 100%;
	width: 19px;
	height: 19px;
	display: inline-block;
	text-indent: -9999px;
}


.error-box {
	position: absolute;
	background-color: white;
	width: 70%;
	height: 40px;
	text-align: center;
	top: 5%;
	left: 50%;
	transform: translate(-50%, -50%);
	display: flex;
	align-items: center;
	padding: 0 26px;
	box-sizing: border-box;
	border-radius: 5px;
	font-size: 12px;
	font-weight: 500;
	animation-timing-function: ease-in-out;
	animation: fadeout 3s;
	animation-fill-mode: forwards;

}

.error-gotofavor {
	position: absolute;
	text-align: center;
	top: 5%;
	right: 15%;
	transform: translate(-50%, -50%);
	display: flex;
	font-weight: 600;
	font-size: 10px;
	animation-timing-function: ease-in-out;
	animation: fadeout 3s;
	animation-fill-mode: forwards;
}

.map-txt {
	cursor: pointer;
	color: #727272;
	font-size: 12px;
	font-weight: 500;
	margin-top: 18px;
	display: flex;
	align-items: center;
	justify-content: center;
}

.map-txt::before {
	content: "\e55b";
	font-family: 'Material Icons';
	font-size: 16px;
	margin-right: 2px;
}
</style>

<style>
@import "/css/component/member/stuff/map-content.css";

@keyframes fadeout {
	from {
		opacity: 1;
	}

	50% {
		opacity: 1;
	}

	to {
		opacity: 0;
	}
}
</style>
