<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko';

export default {
	data() {
		return {
			openModal: false,
			openModal2: false,
			stuff: {},
			category: {},
			imageList: '',
		};
	},
	methods: {
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
				this.formatDateStuff();
				this.$store.commit('LOADING_STATUS', false);
			})
			.catch((error) => console.log("error", error));
		this.$store.commit('LOADING_STATUS', false);
	},
};
</script>

<style scoped>
@import "/css/component/member/stuff/component-detail.css";
</style>

<template>
	<!-- detail : flex-container -->
	<div class="detail">
		<header class="detail-header">
			<router-link to="list" class="icon icon-back">뒤로가기</router-link>

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
					<v-carousel v-if="imageList.length != 0" hide-delimiters show-arrows="hover" height="100%">
							<v-carousel-item v-for="img in imageList" :src="'/images/member/stuff/' + img.name"></v-carousel-item>
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
						<div class="detail-info-txt">2 / {{ stuff.numPeople }} 명</div>
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
			<h1 class="d-none">join</h1>
			<h2 class="detail-join-title">참여중인 사람</h2>
			<div class="detail-join-wrap">
				<div class="detail-join-members">
					<a class="icon-member">멤버a</a>
					<a class="icon-member">멤버b</a>
					<a class="icon-member">멤버c</a>
					<a class="icon-member">멤버d</a>
					<a class="icon-member">멤버e</a>
				</div>
				<button class="detail-join-button" onclick="location.href='list.html'">
					참여하기
				</button>
			</div>

		</section>
	</div>
</template>

