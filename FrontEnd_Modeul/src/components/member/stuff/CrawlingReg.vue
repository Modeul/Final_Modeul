<template>
	<!-- =================== reg1 ======================= -->
	<section class="reg1-form" :class="{ 'd-none': isNext }">
		<h1 class="d-none">reg1</h1>

		<section class="canvas-1 d-fl fl-dir-col">
			<!-- =================== reg1 : header ===================== -->
			<header class="d-fl">
				<div>
					<router-link to="/member/stuff/recommends" class="icon icon-back" @click="goback">뒤로가기</router-link>
				</div>

				<div class="hd-title-box">
					<h1 class="hd-title">카테고리 설정</h1>
				</div>
			</header>

			<!-- =================== reg1 : main ===================== -->
			<main>
				<div class="reg1-main-content">
					<h1>지역 주민들과 어떤 물건을 같이 살까요?</h1>
				</div>

				<div class="category-select">
					<ul class="select-box-list">
						<li v-for="category in categoryList">
							<button @click.prevent="categorySelectHandler(category.id)" :id="category.id" class="select-box"
								:class="{ 'categ-eff': categorySelected === category.id }">{{ category.name }}</button>
						</li>
					</ul>
					<button class="btn-next m-t-button" @click.prevent="dnoneHandler"> 다음 </button>
				</div>
			</main>
		</section>
	</section>

	<!-- =================== reg2 ======================= -->
	<section class="reg2-form" :class="{ 'd-none': isNext === false }">
		<h1 class="d-none">reg2</h1>

		<section class="canvas-1 d-fl fl-dir-col">

			<h1 class="d-none">reg2</h1>

			<!-- =================== reg2 : header ===================== -->
			<header class="">

				<div class="reg2-back">
					<a class="icon icon-back" @click.prevent="dnoneHandler">뒤로가기</a>
				</div>

				<div class="hd-title-box">
					<h1 class="hd-title">글 올리기</h1>
				</div>
			</header>

			<!-- =================== reg2 : main ===================== -->
			<main class="d-fl-jf m-b">

				<form @submit.prevent="upload" method="post" enctype="multipart/form-data" ref="form">
					<div>
						<input type="submit" class="reg2-post" value="올리기">
					</div>

					<!-- 이미지 업로드  -->
					<div class="file-box">
						<label for="file">
								<img class="uploaded-crawlingimg" :src="this.stuff.imageList.name" />
						</label>
					</div>

					<!-- 에러메시지 모달창 -->
					<div v-if="openModal == true" class="black-bg">
						<div class="error-box">{{ valiError }}
							<div @click="toggleModal" class="error-close"></div>
						</div>
					</div>

					<!-- 카테고리 목록 선택 -->


					<select class="category-box" name="categoryId">
						<!-- <option class="d-none" value="null">{{ stuff.categoryId }}</option> -->

						<option v-for="c in categoryList" v-bind:selected="c.id == stuff.categoryId" :value=c.id
							v-text="c.name">
						</option>

					</select>


					<div class="select-box">
						<label for="title" class="input-field-txt">제목</label>
						<input type="text" class="input-field" id="title" name="title" v-model="stuff.title">
					</div>

					<!-- 인원수 조절 -->
					<div class="select-box2 d-fl">
						<label for="" class="input-field-txt">인원</label>
						<div class="people-count-box">
							<input class="btn-minus" id="people-count" type="button" value=""
								@click.prevent="numPeopleMinusHandler">

							<input type="text" class="people-count-num" name="numPeople" id="result"
								v-model="stuff.numPeople">

							<input class="btn-plus" id="people-count" type="button" value=""
								@click.prevent="numPeoplePlusHandler">
						</div>
					</div>

					<!-- 마감일 설정 -->
					<div id="btn-date" class="select-box d-fl jf-sb">
						<label for="datetime-local" class="input-field-txt">마감시간</label>
						<input class="date-pic" type="datetime-local" data-placeholder="날짜를 선택해주세요." required
							aria-required="true" name="deadline" v-model="stuff.deadline">

					</div>

					<div class="select-box">
						<label for="price" class="input-field-txt">가격</label>
						<input type="text" class="input-field" name="price" id="price" v-model="stuff.price">
					</div>

					<div class="select-box" @click.prevent="postCode" >
						<label for="place" class="input-field-txt">장소</label>
						<input type="text" class="input-field" name="place" id="place" v-model="stuff.place" hidden>
						<div  class="input-field">{{ stuff.place }}</div>
					</div>
					<div class="select-box toggle-map" v-if="mapNav">
						<div  v-if="showMap" @click="toggleMap">지도 열기</div>
						<div  v-else @click="toggleMap">지도 닫기</div>
					</div>
					<div id="map"></div>
					<input type="text" class="input-field" name="coordX" v-show="false" v-model="stuff.coordX">
					<input type="text" class="input-field" name="coordY" v-show="false" v-model="stuff.coordY">
					<input type="text" class="input-field" name="dongCode" v-show="false" v-model="stuff.dongCode">

					<div class="select-box">
						<label for="url" class="input-field-txt">링크</label>
						<input type="text" class="input-field" name="url" id="url" v-model="stuff.url">
					</div>

					<div class="select-box select-content d-f fl-dir-col">
						<label for="content" class="input-field-txt2">내용</label>
						<textarea class="input-field input-content" name="content" id="content" cols="30" rows="10"
							v-model="stuff.content"></textarea>
					</div>
				</form>
			</main>

		</section>
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
			categorySelected: null,
			isNext: false,
			categoryList: [],
			files: [],
			imageList: [],
			showMap: false,
			mapStatus: false,
			mapNav: false,
			crawlingData : [],
			id:'',

			croodX: 0,
			croodY: 0,
			stuff: {
				title: '',
				place: '',
				numPeople: '2',
				categoryId: 1,
				deadline: '',
				price: '',
				url: '',
				content: '',
				dongCode:'',
				imageList: [
					{
						"id": '',
						"name": '',
						"stuffId": ''
					}
				]
			},
			// 에러 메시지
			valiError: "",
			openModal: false,

		}
	},
	methods: {
		goback() {
			this.$router.go(-1);
		},
		/* reg1 <-> reg2 이동 이벤트 */
		dnoneHandler() {
			this.isNext = !this.isNext;
		},

		categorySelectHandler(categoryId) {
			this.categorySelected = categoryId
			this.stuff.categoryId = event.currentTarget.id;
		},

		/* 인원 수 증감 이벤트 */
		numPeoplePlusHandler(stuff) {
			if (this.stuff.numPeople >= 1 && this.stuff.numPeople < 16)
				this.stuff.numPeople++;
		},
		numPeopleMinusHandler(stuff) {
			if (this.stuff.numPeople >= 2 && this.stuff.numPeople <= 16)
				this.stuff.numPeople--;
		},

		// 선택한 데이터 받아오기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		loadCrawlingData() {
			this.id = this.$route.params.id;
			var requestOptions = {
				method: 'GET',
				redirect: 'follow'
			};
			fetch(`${this.defaultStore.host}/api/stuff/crawling/${this.id}`, requestOptions)
				.then(response => response.json())
				.then(data => {
					this.crawlingData = data;
					// this.crawlingData Proxy객체로 반환됨.
					// Proxy를 Object로 변환
      				// this.crawlingData = JSON.parse(JSON.stringify(this.crawlingData));
					this.stuff.title = this.crawlingData.crawlingData.title || '';
					this.stuff.price = this.crawlingData.crawlingData.price.replace(/,/g, '') || '';
					this.stuff.price = this.stuff.price.replace(/원/g, '') || '';
					this.stuff.url = this.crawlingData.crawlingData.contenturl || '';
					this.stuff.imageList.name = this.crawlingData.crawlingData.imgurl || '';
				})
				.catch(error => console.log('error', error));
		},
		/* selectbox에 카테고리 목록 불러오기 */
		loadCategory() {
			var requestOptions = {
				method: 'GET',
				redirect: 'follow'
			};

			fetch(`${this.defaultStore.host}/api/stuff/categories`, requestOptions)
				.then(response => response.json())
				.then(categoryList => {
					this.categoryList = categoryList;
				})
				.catch(error => console.log('error', error));
		},

		// 파일 업로드시, 이벤트 처리
		async upload() {


			this.valiError = "";

			// 제목 체크 (글자 수)
			if (!this.stuff.title) {
				this.valiError = "제목을 입력하세요.";
				this.openModal = true;
				return;
			} else if (!this.isValidTitle(this.stuff.title)) {
				this.valiError = "제목을 100자 이하로 입력해주세요.";
				this.openModal = true;
				return;
			}
			// 날짜 체크 (현재 시점 이전 선택 불가)
			if (!this.isValidDeadline()) {
				this.valiError = "마감시간을 확인하세요";
				this.openModal = true;
				return;
			}
			// 가격 체크
			if (!this.stuff.price) {
				this.valiError = "가격을 입력하세요.";
				this.openModal = true;
				return;
			} else if (!this.isValidPrice(this.stuff.price.length)) {
				this.openModal = true;
				return;
			}
			// 장소, 카테고리, 날짜, 내용 체크
			if (!this.stuff.place) {
				this.valiError = "장소를 입력하세요.";
				this.openModal = true;
				return;
			} else if (!this.stuff.categoryId) {
				this.valiError = "카테고리를 선택하세요.";
				this.openModal = true;
				return;
			} else if (!this.stuff.deadline) {
				this.valiError = "날짜를 입력하세요.";
				this.openModal = true;
				return;
			} else if (!this.stuff.content) {
				this.valiError = "내용을 입력하세요.";
				this.openModal = true;
				return;
			}

			if (!this.valiError) {
				var formData = new FormData(this.$refs.form);
				formData.append('imgurl' , this.stuff.imageList.name);
				formData.append("memberId",this.userDetails.id);

				var requestOptions = {
					method: 'POST',
					body: formData,
					redirect: 'follow'
				};

				await fetch(`${this.defaultStore.host}/api/stuff/recommend`, requestOptions)
					.then(response => response.text())
					.then(result => result)
					.catch(error => console.log('error', error));

				this.$router.replace('/member/stuff/list')
			}
		},

		// 제목 체크
		isValidTitle() {
			if (this.stuff.title.length > 100) {
				return false;
			}
			return true;
		},
		// 가격 체크
		isValidPrice() {
			// 가격제한
			if (this.stuff.price.length > 8) {
				this.valiError = "너무 비싸요.";
				return false;
			}
			// 가격은 숫자만 입력 가능
			const priceRegex = /^[0-9]+$/.test(this.stuff.price);
			if (!priceRegex) {
				this.valiError = "가격은 숫자만 입력 가능합니다.";
				return false;
			}
			// 모든 검증을 통과한 경우
			return true;
		},
		isValidDeadline() {
			const deadlineObj = new dayjs(this.stuff.deadline)
			if (deadlineObj.diff(dayjs(), 'minute') <= 0)
				return false;
			else
				return true;
		},
		toggleModal() {
			this.openModal = !this.openModal;
		},

		postCode() {


			const geocoder = new daum.maps.services.Geocoder();
			new daum.Postcode({
				oncomplete: (data) => {

					this.stuff.place = data.address;
					this.stuff.dongCode = data.bcode;

					geocoder.addressSearch(data.address, (results, status) => {

						if (status === daum.maps.services.Status.OK) {

							let result = results[0];
							this.stuff.coordX = result.x;
							this.stuff.coordY = result.y;
							this.showMap = true;
							this.mapStatus = true;
							this.mapNav = true;
							document.querySelector("#content").focus();

						}
					});

				}
			}).open();
		},

		drawMap() {
			const mapContainer = document.getElementById('map');
			let coords = new daum.maps.LatLng(this.stuff.coordY, this.stuff.coordX);
			let mapOption = {
				center: coords,
				level: 5
			};


			let map = new daum.maps.Map(mapContainer, mapOption);


			let marker = new daum.maps.Marker({
				position: coords,
				map: map
			});

			map.relayout();
			map.setCenter(coords);
			marker.setPosition(coords);
		},
		toggleMap() {

			let map = document.querySelector("#map");
			if (this.showMap) {
				map.style.height = '300px';
				this.showMap = !this.showMap;

				if (this.mapStatus) {
					setTimeout(() => {
						this.mapStatus = false;
						this.drawMap();
					}, 500);
				}


			} else {
				map.style.height = 0;
				this.showMap = !this.showMap;
			}

		}
	},
	mounted() {
		this.loadCategory();
		this.loadCrawlingData();

	},
}
</script>

<style scoped>
@import "/css/component/member/stuff/component-reg.css";



select {
	-webkit-appearance: none;
	/* 크롬 화살표 없애기 */
	-moz-appearance: none;
	/* 파이어폭스 화살표 없애기 */
	appearance: none
		/* 화살표 없애기 */

}
</style>