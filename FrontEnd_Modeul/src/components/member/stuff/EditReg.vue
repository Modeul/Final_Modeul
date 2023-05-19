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
			showMap: true,
			mapStatus: true,
			isNext: false,
			categoryList: [],
			files: [],
			url: '',
			stuffView: '',
			stuff: '',
			category: '',
			image: '',
			imageList: '',

			valiError: "",
			changed: false,
			openModal: false,

		}
	},
	methods: {
		/* reg1 <-> reg2 이동 이벤트 */
		dnoneHandler() {
			this.isNext = !this.isNext;
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

		async update() {
			// var myHeaders = new Headers();
			// myHeaders.append("Content-Type", "multipart/form-data");

			this.valiError = "";

			// 제목 체크 (글자 수)
			if (!this.stuff.title) {
				this.valiError = "제목을 입력하세요.";
				this.openModal = true;
				return;
			} else if (!this.isValidTitle(this.stuff.title)) {
				this.valiError = "제목을 40자 이하로 입력해주세요.";
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
			} else if (this.stuff.url) {
				this.stuff.url = this.stuff.url.toLowerCase();
				// "소문자 변환"
				if (this.stuff.url.startsWith('h')) {
					let regex = /^http(s)?:\/\/(www\.)?[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$/;
					if (!regex.test(this.stuff.url)) {
						this.valiError = "올바른 형식의 주소를 입력하세요.";
						this.openModal = true;
						// "h 필터"
						return;
					}
				} else if (this.stuff.url.startsWith('w')) {
					let regex = /^[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b(?:[-a-zA-Z0-9()@:%_\+.~#?&//=]*)$/;
					if (!regex.test(this.stuff.url)) {
						this.valiError = "올바른 형식의 주소를 입력하세요.";
						this.openModal = true;
						// "w 필터"
						return;
					}
				} else {
					// "나머지"
					this.valiError = "올바른 형식의 주소를 입력하세요.";
					this.openModal = true;
					return;
				}
			}else if (!this.stuff.content) {
				this.valiError = "내용을 입력하세요.";
				this.openModal = true;
				return;
			}

			if (!this.valiError) {
				var formData = new FormData(this.$refs.form);

				var requestOptions = {
					method: 'PUT',
					// headers: myHeaders,
					body: formData,
					redirect: 'follow'
				};
				// this.defaultStore.loadingStatus = true;
				await fetch(`${this.defaultStore.host}/api/stuff/update/${this.$route.params.id}`, requestOptions)
					.then(response => response.text())
					.then(result => {
						// this.defaultStore.loadingStatus = false;
						this.$router.replace('/member/stuff/' + this.stuff.id);
					})
					.catch(error => console.log('error', error));

			}
		},

		// 썸네일 조작
		uploadImage(e) {
			this.files = e.target.files;

			if (this.files.length > 6) {
				this.valiError = "이미지는 최대 6개까지 선택할 수 있습니다.";
				this.openModal = true;
				return;
			}

			// 취소 버튼을 눌렀을 때 이미지 초기화 방지
			if (this.files.length <= 0) {
				return;
			}

			this.changed = true;
			this.imageList = [];

			for (let file of this.files) {
				this.imageList.push(URL.createObjectURL(file));
			}
		},

		load() {
			fetch(`${this.defaultStore.host}/api/stuff/${this.$route.params.id}`)
				.then(response => response.json())
				.then(stuffView => {
					this.stuffView = stuffView;
					this.stuff = stuffView.stuff;
					this.category = stuffView.category;
					this.imageList = stuffView.imageList;
				})
				.catch(error => console.log('error', error));
		},

		// 제목 체크
		isValidTitle() {
			if (this.stuff.title.length > 40) {
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

					geocoder.addressSearch(data.address, (results, status) => {

						if (status === daum.maps.services.Status.OK) {

							let result = results[0];
							this.stuff.coordX = result.x;
							this.stuff.coordY = result.y;
							this.showMap = true;
							this.mapStatus = true;
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
						this.drawMap();
						this.mapStatus = false;
					}, 500);
				}


			} else {
				map.style.height = 0;
				this.showMap = !this.showMap;
			}

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
		this.numPeoplePlusHandler();
		this.numPeopleMinusHandler();

		this.loadCategory();
		this.load();
	},
	updated() {

	}
}
</script>

<style scoped>
@import "/css/component/member/stuff/component-reg.css";
</style>
<template>
	<!-- =================== reg2 ======================= -->
	<section class="reg2-form">
		<h1 class="d-none">reg2</h1>

		<section class="canvas-1 d-fl fl-dir-col">

			<h1 class="d-none">reg2</h1>

			<!-- =================== reg2 : header ===================== -->
			<header class="">

				<router-link :to="'/member/stuff/' + stuff.id">
					<div class="reg2-back">
						<a class="icon icon-back">뒤로가기</a>
					</div>
				</router-link>
				<div class="hd-title-box">
					<h1 class="hd-title">글 수정하기</h1>
				</div>
			</header>

			<!-- =================== reg2 : main ===================== -->
			<main class="d-fl-jf m-b">

				<form @submit.prevent="update" enctype="multipart/form-data" ref="form">
					<div>
						<input type="submit" class="reg2-post" value="저장">
					</div>

					<!-- 이미지 업로드  -->
					<div class="file-box">
						<label for="file">
							<div class="btn-file">{{ imageList.length }}/6</div>
							<div class="btn-uploaded-files" v-for="img in imageList">
								<img class="uploaded-files" :src="changed ? img : formatImgUrl(img.name)">
							</div>
						</label>
						<input type="file" class="d-none" id="file" name="imgs" multiple accept="image/*"
							@change="uploadImage">
					</div>

					<!-- 에러메시지 모달창 -->
					<div v-if="openModal == true" class="black-bg">
						<div class="error-box">{{ valiError }}
							<div @click="toggleModal" class="error-close"></div>
						</div>
					</div>
					<div class="form-text">
						<!-- 카테고리 목록 선택 -->
						<select class="category-box" name="categoryId" v-model="category.id">
							<option v-for="c in categoryList" :value="c.id" class="" name="categoryId" v-text="c.name">
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

						<div class="select-box" @click.prevent="postCode">
							<label for="place" class="input-field-txt">장소</label>
							<input type="text" class="input-field" name="place" id="place" v-model="stuff.place" hidden>
							<div class="input-field">{{ stuff.place }}</div>
						</div>
						<div class="select-box toggle-map" v-if="showMap" @click="toggleMap">지도 열기</div>
						<div class="select-box toggle-map" v-else @click="toggleMap">지도 닫기</div>
						<div id="map"></div>
						<input type="text" class="input-field" name="coordX" v-show="false" v-model="stuff.coordX">
						<input type="text" class="input-field" name="coordY" v-show="false" v-model="stuff.coordY">

						<div class="select-box">
							<label for="url" class="input-field-txt">링크</label>
							<input type="text" class="input-field" name="url" id="url" v-model="stuff.url">
						</div>

						<div class="select-box select-content d-f fl-dir-col">
							<label for="content" class="input-field-txt2">내용</label>
							<textarea class="input-field input-content" name="content" id="content" cols="30" rows="10"
								v-model="stuff.content"></textarea>
						</div>
					</div>
				</form>

			</main>

		</section>
	</section>
</template>
<style scoped>
.form-text {
	display: flex;
	flex-wrap: nowrap;
	flex-direction: column;
	align-items: center;
}
</style>

