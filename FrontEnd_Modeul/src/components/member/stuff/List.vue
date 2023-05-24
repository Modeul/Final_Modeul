<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko'
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../../stores/useDefaultStore';

import PcHeader from './PcHeader.vue'

export default {
	data() {
		return {
			page: '',
			dongCode: '',
			serchDong: '',
			dongName: '',
			myDongCode: '',
			myDongName: '',
			myCoordX: '',
			myCoordY: '',
			list: [],
			categoryList: [],
			categoryId: '',
			listCount: '',
			query: '',
			searchToggle: false,
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore()
		};
	},
	components: {
		PcHeader
	},
	computed: {
	},
	methods: {
		searchInput(queryEmit) {
			if (!this.searchToggle)
				if (this.query == queryEmit.trim())
					return;
			this.query = queryEmit.trim();
			this.page = 1;
			this.defaultStore.loadingStatus = true;
			fetch(`${this.defaultStore.host}/api/stuffs?p=${this.page}&c=${this.categoryId}&dc=${this.serchDong}&q=${this.query}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = this.formatDateList(dataList.list);
					this.listCount = dataList.listCount;
					this.defaultStore.loadingStatus = false;
				}).catch(error => console.log('error', error));
		},
		categoryHandler(e) {
			this.page = 1;
			this.categoryId = e.target.value;
			this.addListHandler()
			fetch(`${this.defaultStore.host}/api/stuffs?p=${this.page}&c=${this.categoryId}&dc=${this.serchDong}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = this.formatDateList(dataList.list);
					this.listCount = dataList.listCount;
					this.categoryList = dataList.categoryList;
				}).catch(error => console.log('error', error));
		},
		async addListHandler(c) {
			this.defaultStore.loadingStatus = true; // 해당 함수 true/false 로 어디서나 추가 가능
			// setTimeout(() => { this.defaultStore.loadingStatus = false; }, 400); //settimout은 지워도 됨

			this.page++;
			await fetch(`${this.defaultStore.host}/api/stuffs?p=${this.page}&c=${this.categoryId}&dc=${c}&q=${this.query}`)
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
		scrollCheck() {
			if (window.innerHeight >= 718) {
				this.addListHandler(this.serchDong);
			}
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
		formatImgUrl(imgDir) {
			if (!imgDir)
				return imgDir;
			if (imgDir.substr(0, 4) == 'http')
				return imgDir
			else
				return '/images/member/stuff/' + imgDir
		},
		onChange(v) {
			this.page = 1;
			if (v.target.value === 'cur') {
				this.getDongInfo(null, null);
			} else if (v.target.value === 'my') {
				this.serchDong = this.myDongCode;
				this.dongName = this.myDongName;
				this.addListHandler(this.serchDong);
			} else {
				this.serchDong = '';
				this.dongName = '';
				this.addListHandler(this.serchDong);
			}
		},
		getDongInfo(x, y) {

			const geocoder = new kakao.maps.services.Geocoder();

			if (x == null) {
				const watchID = navigator.geolocation.getCurrentPosition((position) => {
					let lat = position.coords.latitude;
					let lng = position.coords.longitude;

					geocoder.coord2Address(lng, lat, (result, status) => {
						if (status === kakao.maps.services.Status.OK) {

							this.dongName = result[0].address.region_3depth_name;
						}
					});
					geocoder.coord2RegionCode(lng, lat, (result, status) => {
						if (status === kakao.maps.services.Status.OK) {

							this.dongCode = result[0].code;
							this.serchDong = this.dongCode;
							this.addListHandler(this.serchDong);
						}
					});

				}, () => { alert("죄송합니다. 위치 정보를 사용할 수 없습니다.") });

			} else {

				geocoder.coord2Address(x, y, (result, status) => {
					if (status === kakao.maps.services.Status.OK) {

						this.myDongName = result[0].address.region_3depth_name;
					}
				});
				geocoder.coord2RegionCode(x, y, (result, status) => {
					if (status === kakao.maps.services.Status.OK) {

						this.myDongCode = result[0].code;

					}
				});


			}


		},
		getMemberCoordInfo() {
			fetch(`${this.defaultStore.host}/api/member/${this.userDetails.id}`)
				.then(response => response.json())
				.then(dataList => {
					this.myCoordX = dataList.coordX;
					this.myCoordY = dataList.coordY;
					this.getDongInfo(this.myCoordX, this.myCoordY);
				}).catch(error => console.log('error', error));
		},
		scrollHandler() {
			window.scrollTo({ top: 0, behavior: 'smooth' });
		},
		scroll() {
			if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 10) {
				if (this.listCount !== 0) {
					this.addListHandler(this.serchDong);
				}
			}
		}
	},
	mounted() {
		this.target = document.querySelector('.top-btn');
		this.target.addEventListener('scroll', this.handleScroll);
		this.page = 0;
		this.addListHandler('');
		this.scrollCheck();
		this.getMemberCoordInfo();

		document.addEventListener("scroll", this.scroll)

	},
	beforeUnmount() {
		document.removeEventListener("scroll", this.scroll)
	}
}
</script>

<template>
	<PcHeader @queryEmit="searchInput" :dongName="myDongName" @change="onChange"></PcHeader>

	<div class="pc-carousel">
		<v-carousel cycle interval="6000" height="370" hide-delimiter-background :show-arrows="false" color="white">
			<v-carousel-item src="/images/member/banner1.png"></v-carousel-item>
			<v-carousel-item src="/images/member/banner2.png"></v-carousel-item>
			<v-carousel-item src="/images/member/banner3.png"></v-carousel-item>
		</v-carousel>
	</div>

	<section class="canvas">
		<header class="d-fl-al header-jc">
			<div class="gps-box">
				<div class="icon icon-location"></div>
				<select class="selectbox-set" @change="onChange($event)">
					<option value="" default>전체</option>
					<option value="my">{{ myDongName }}</option>
					<option value="cur">현재위치</option>
				</select>
				<div class="select__arrow"></div>
			</div>
			<Transition name="fade">
				<div v-if="searchToggle" class="d-fl d-b-none search-form">
					<input id="search-bar" class="search-input m-l-6px" placeholder="검색어를 입력해주세요." v-model="query"
						@keyup.enter="searchInput(query)">
					<h1 class="icon search-dodbogi">돋보기</h1>
				</div>
				<!-- <div v-else-if="dongName"> {{ dongName }}</div> -->
			</Transition>

			<div>
				<div v-if="!searchToggle" class="icon icon-search" @click="searchToggle = !searchToggle"></div>
				<div v-else class="icon icon-search-cancel" @click="searchToggle = !searchToggle"></div>
				<!-- <a class="icon">
					<input type="checkbox" id="menuicon">
					<label for="menuicon">
						<span></span>
						<span></span>
						<span></span>
					</label>
					<div class="sidebar">
						<div class="welcome f-weight">
							<span class="f-color-2">뉴렉님</span><br>환영합니다.
						</div>
						<div class="side-menu">
							<div></div>

							<span class="sidebar-padding">
								<router-link to="/member/stuff/list">HOME</router-link>
							</span>
							<span class="sidebar-padding">
								<router-link to="/member/stuff/recommends">추천상품</router-link>
							</span>
							<span class="sidebar-padding">
								<router-link to="/member/stuff/listsearch">검색하기</router-link>
							</span>
							<span class="sidebar-padding">
								<router-link to="/member/stuff/reg">글 등록하기</router-link>
							</span>
							<span class="sidebar-padding">
								<router-link to="/member/participation/list">채팅하기</router-link>
							</span>
							<span class="sidebar-padding">
								<router-link to="/member/mypage">마이페이지</router-link>
							</span>
						</div>
					</div>
				</a> -->
			</div>
		</header>

		<nav>
			<div class="header-categ-box">
				<div>
					<button class="header-categ" @click="categoryHandler" name="c"
						:class="(this.categoryId != '') ? 'header-categ' : 'default'">전체</button>
				</div>

				<div v-for="c in categoryList">
					<button @click="categoryHandler" name="c" :value="c.id"
						:class="(this.categoryId == c.id) ? 'selected' : 'header-categ'">{{ c.name }}</button>
				</div>
			</div>
		</nav>

		<!-- 나중에 onclick 이벤트 하트 부분만 빼고 넣기 -->
		<main>

			<div class="list-wrap">

				<div class="stuff-list" v-for="stuff in list">
					<router-link :to="'./' + stuff.id">
						<div class="d-gr li-gr m-t-13px list-cl">
							<!-- 나중에 전체를 div로 묶어서 main으로 크게 묶기 -->
							<div class="li-pic b-rad-1">
								<img v-if="stuff.imageName != null" class="listview-image"
									:src="formatImgUrl(stuff.imageName)" alt="img">
								<img v-else-if="stuff.categoryId == '1'" class="listview-image"
									src="/images/member/stuff/category1.svg" alt="img">
								<img v-else-if="stuff.categoryId == '2'" class="listview-image"
									src="/images/member/stuff/category2.svg" alt="img">
								<img v-else-if="stuff.categoryId == '3'" class="listview-image"
									src="/images/member/stuff/category3.svg" alt="img">
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
			</div>
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

	<div>
		<router-link to="/member/stuff/reg" class="pc-reg-stuff"></router-link>
	</div>
	<div class="top-btn" @click="scrollHandler"></div>
</template>


<style scoped>
@import "/css/component/member/stuff/component-list.css";
@import "/css/component/admin/member/list-responsive.css";
@import "/css/button.css";

.fade-enter-active,
.fade-leave-active {
	transition: opacity 0.4s ease;
}

.fade-enter-from,
.fade-leave-to {
	opacity: 0;
}
</style>