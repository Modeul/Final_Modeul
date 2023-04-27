<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko';

export default {
	data() {
		return {
			memberId:2,
			stuffId:1,
			openModal: false,
			openModal2: false,
			stuff: {},
			category: {},
			imageList: '',
			participantList:[],
			memberCount:'',
			isParticipated:'',
			isParticipationChecked:'',
			dialog: false
		};
	},
	methods: {
		/* 뒤로가기 : 이전페이지로 이동 */
		goback(){
          this.$router.go(-1);    
      	},
		/* 모달 이벤트 */
		modalHandler() {
			this.openModal = !this.openModal;
		},
		modalHandler2() {
			this.openModal2 = !this.openModal2;
		},
		imageZoomInHandler() {
			console.log("zoom-in");
		},
		imageZoomOutHandler() {
			console.log("zoom-out");
		},
		formatDateStuff() {
			const deadlineObj = dayjs(this.stuff.deadline).locale('ko');
			this.stuff.deadline = deadlineObj.format("M월 D일 (dd) HH시까지");
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
			this.$router.push("/member/stuff/list");

			fetch(`${this.$store.state.host}/api/stuff/${this.$route.params.id}`, requestOptions)
				.then(response => response.text())
				.then(result => console.log(result))
				.catch(error => console.log('error', error));
		},

		/* 공구상품 글에 참여!! */
		participationHandler() {
			var myHeaders = new Headers();
			myHeaders.append("Content-Type", "application/json");

			var raw = JSON.stringify({
				// memberid:this.stuff.memberId,
				memberId:this.memberId,
				stuffId:this.stuff.id,
				// stuffId:this.stuffId
			});

			var requestOptions = {
				method: 'POST',
				headers: myHeaders,
				body: raw,
				redirect: 'follow'
			};

			fetch(`${this.$store.state.host}/api/participation`, requestOptions)
				.then(response => response.text())
				.then(result => {
					console.log(result);
					this.loadParticipationList();
					this.isParticipated = !this.isParticipated;
					this.dialog=true;
				})
				.catch(error => console.log('error', error));
		},
		/* 참여 인원 추가하면 참여 멤버 실시간 업데이트하기 */
		loadParticipationList(){
			var requestOptions = {
				method: 'GET',
				redirect: 'follow'
			};
			fetch(`${this.$store.state.host}/api/participation/stuff/${this.$route.params.id}`, requestOptions)
			.then(response => response.json())
			.then(data => {
				this.participantList = data.list;
				this.memberCount = data.memberCount;
				console.log(this.participantList);
			})
			.catch(error => console.log('error', error));
		},
		/* 공구상품 항목의 참여 취소 요청 */
		cancelParticipationHandler(){
			var requestOptions = {
				method: 'DELETE',
				redirect: 'follow'
			};

			fetch(`${this.$store.state.host}/api/participation/${this.$route.params.id}/${this.memberId}`, requestOptions)
			.then(response => response.text())
			.then(result => {
				console.log(result);
				this.loadParticipationList();
				this.isParticipated = !this.isParticipated;
				this.dialog=true;
			})
			.catch(error => console.log('error', error));
		}
	},
	computed: {

	},
	async mounted() {
		this.$store.commit('LOADING_STATUS', true);
		await fetch(`${this.$store.state.host}/api/stuff/${this.$route.params.id}`)
			.then((response) => response.json())
			.then((data) => {
				this.stuff = data.stuff;
				this.category = data.category;
				this.imageList = data.imageList;
				this.participantList = data.participantList;
				this.memberCount = data.memberCount;
				this.formatDateStuff();
				this.$store.commit('LOADING_STATUS', false);
			})
			.catch((error) => console.log("error", error));
		this.$store.commit('LOADING_STATUS', false);
		
		this.loadParticipationList();
	},
};
</script>

<template>
	<!-- detail : flex-container -->
<div class="detail">
		<header class="detail-header">
			<router-link to="list" class="icon icon-back" @click.prevent="goback">뒤로가기</router-link>

			<!-- 수정/삭제 모달 버튼 -->
			<i @click="modalHandler" class="icon-edit"></i>
			<!-- 모달 배경 -->
			<div v-if="openModal">
				<div class="icon-edit2">
				<div class="d-fl-al fl-dir-col">
					<router-link :to="'./edit/' + stuff.id">
						<div class="icon-edit3"></div>
					</router-link>
					<div @click="modalHandler2" class="icon-edit4">

					</div>
				</div>
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



		</header>

		<!-- detail - item1  -->
		<main>
			<!-- detail-main : flex-container -->
			<div class="detail-main">
				<!-- detail-img : detail-main - item1 -->

				<div class="detail-img">
					<v-carousel v-if="imageList.length != 0" hide-delimiters show-arrows="hover" height="100%" >
						<v-carousel-item v-for="img in imageList"
							:src="'/images/member/stuff/' + img.name"></v-carousel-item>
					</v-carousel>
					<div v-else class="noImg"></div>
				</div>
				<!-- detail-heading : detail-main - item2 -->
				<section class="canvas detail-heading">
					<h1 class="d-none">heading</h1>
					<div class="d-fl detail-edit">
						<div class="detail-top">
							<div class="detail-category" :value="stuff.categoryId">{{ category.name }}</div>
							<div class="detail-status">모집중</div>
						</div>

						<div class="icon-heart">하트</div>

					</div>
					<p class="detail-heading-title">{{ stuff.title }}</p>
					<!-- <div class="d-fl">
						<div class="ed-text"><router-link :to="'./'+stuff.id+'/edit/'">수정</router-link></div>
						<div class="ed-text" @click="deleteStuff">삭제</div>
					</div> -->
					<div class="detail-price">{{ stuff.price }}원</div>

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
		</main>

		<!-- detail-join : detail - itme2  -->

		<section class="canvas detail-join">
			<div class="detail-join-title">참여중인 사람</div>
			<div class="detail-join-wrap">
				<v-sheet max-width="240">
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
					<button
						class="detail-join-button"
						v-if="!isParticipated"
						@click="participationHandler"
					>
					<!-- @click="[dialog=true, participationHandler()]" -->
					참여하기
					</button>

					<div class="join-button-wrap">
						<router-link :to="'../../chat/' + stuff.id + '/' + this.memberId" class="detail-chat-button" v-if="isParticipated">채팅하기</router-link>
						<button
							class="detail-cancel-button"
							v-if="isParticipated"
							@click="cancelParticipationHandler"
						>
						참여취소
						</button>
					</div>
				</div>
				

				<v-dialog
					v-model="dialog"
					width="auto"
					v-if="isParticipated"
				>
					<v-card>
						<v-card-text class="participationcard">
							참여되었습니다.
						</v-card-text>
						<v-card-actions>
						<v-btn color="#63A0C2" block @click="dialog = false">확인</v-btn>
						</v-card-actions>
					</v-card>
				</v-dialog>

				<v-dialog
					v-model="dialog"
					width="auto"
					v-if="!isParticipated"
				>
					<v-card>
						<v-card-text>
							취소되었습니다.
						</v-card-text>
						<v-card-actions>
						<v-btn color="#63A0C2" block @click="dialog = false">확인</v-btn>
						</v-card-actions>
					</v-card>
				</v-dialog>
			</div>
		</section>
	</div>
</template>

<style scoped>
@import "/css/component/member/stuff/component-detail.css";

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

.participation{
	display: flex;
	justify-content:space-around;
}
.v-img__img--contain{
	object-fit: cover;
}

/* Vuetify css 변경하는 v-deep 이용하는 방법!! : 
개발자모드에서 보여지는 css 계층의 값 변경 가능*/

.v-dialog::v-deep{
	font-size: 14px;
}
</style>
