<script>
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../../stores/useDefaultStore';
import PcHeader from './PcHeaderCrawling.vue'



export default {
	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			// dongCode: '',
			// serchDong: '',
			// dongName: '',
			// myDongCode: '',
			// myDongName: '',
			// myCoordX: '',
			// myCoordY: '',
			page: '',
			query:'',
			searchToggle: false,
			list: [],
			category: [],
			categoryId:'',
		};
	},
	components: {
		PcHeader
	},
	computed: {
	},
	methods: {
		searchInput(queryEmit){
			if (!this.searchToggle)
				if (this.query == queryEmit.trim())
					return;
			this.query = queryEmit.trim();
			this.page = 1;

			fetch(`${this.defaultStore.host}/api/stuff/recommends?q=${this.query}&p=${this.page}&c=${this.categoryId}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = dataList.list;
					this.listCount = dataList.listCount;
				}).catch(error => console.log('error', error));
        },
		goback() {
			this.$router.go(-1);
		},
		categoryHandler(e){
			this.page=1;
			this.categoryId = e.target.value;
			this.addListHandler()
			fetch(`${this.defaultStore.host}/api/stuff/recommends?q=${this.query}&p=${this.page}&c=${this.categoryId}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = dataList.list;
					this.listCount = dataList.listCount;
					this.category = dataList.category;

				}).catch(error => console.log('error', error));
		},
		async addListHandler() {

			this.defaultStore.loadingStatus = true; 
			this.page++;
			await fetch(`${this.defaultStore.host}/api/stuff/recommends?q=${this.query}&p=${this.page}&c=${this.categoryId}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = dataList.list;
					this.listCount = dataList.listCount;
					this.category = dataList.category;
					this.defaultStore.loadingStatus = false;

				})
				.catch(error => console.log('error', error));
				
		},
		scrollCheck() {
			if (window.innerHeight >= 718) {
				this.addListHandler();
			}
		},
		scrollHandler(){
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
		this.page = 0;
		this.addListHandler();
		this.scrollCheck();
		document.addEventListener("scroll", this.scroll)

	},
	beforeUnmount() {
		document.removeEventListener("scroll", this.scroll)
	}
}
</script>

<style scoped>
@import url(/css/component/member/stuff/component-crawlinglist.css);
@import url(/css/component/admin/member/crawlinglist-responsive.css);


.fade-enter-active,
.fade-leave-active {
	transition: opacity 0.4s ease;
}

.fade-enter-from,
.fade-leave-to {
	opacity: 0;
}
.d-fl-al{
justify-content: end;
}



</style>
<template>
	<PcHeader @queryEmit="searchInput"></PcHeader>

	<div class="pc-carousel">
		<v-carousel cycle interval="6000" height="370" hide-delimiter-background :show-arrows="false" color="white">
			<v-carousel-item src="/images/member/banner1.png"></v-carousel-item>
			<v-carousel-item src="/images/member/banner2.png"></v-carousel-item>
			<v-carousel-item src="/images/member/banner3.png"></v-carousel-item>
		</v-carousel>
	</div>

	<section class="canvas">
		<header class="d-fl-al header-jc">
			<Transition name="fade">
				<div v-if="searchToggle" class="d-fl d-b-none search-form">
					<input id="search-bar" class="search-input m-l-6px" name="q" placeholder="검색어를 입력해주세요." v-model="query"
						@keyup.enter="searchInput(query)">
					<h1 class="icon search-dodbogi">돋보기</h1>
				</div>
			</Transition>
			<div>
				<div v-if="!searchToggle" class="icon icon-search" @click="searchToggle = !searchToggle"></div>
				<div v-else class="icon icon-search-cancel" @click="searchToggle = !searchToggle"></div>
			</div>
		</header>

		<nav>
			<div class="header-categ-box">
				<div>
					<button class="header-categ" @click="categoryHandler" name="c" :class="(this.categoryId != '')?'header-categ':'default'">전체</button>
				</div>
				<div v-for="c in category">
					<button  @click="categoryHandler" name="c" :value="c.categoryId" :class="(this.categoryId == c.categoryId)?'selected':'header-categ'" >{{ c.categoryName }}</button>
				</div>
			</div>
		</nav>

		<main>
			<div class="list-wrap">
				<div class="stuff-list" v-for="stuff in list">
					<a :href="stuff.contenturl" target="_blank">
						<div class="d-gr li-gr m-t-13px list-cl">
							<div class="li-pic b-rad-1">
								<img class="listview-image" :src="stuff.imgurl" alt="img">
							</div>
							<div class="li-categ-place">
								<span class="li-categ-place-categoryName">{{stuff.categoryName}}</span>
							</div>
							<router-link class="li-write-icon" :to="{ path : '/member/stuff/recommend/' + stuff.id}" >
								<div class="icon-write" name="id" :value="stuff.id"></div>
							</router-link>
							<div class="li-subj">{{ stuff.title }}</div>
							<div class="li-member">
								<span class="li-member-limit"> {{ stuff.price }} ₩</span>
							</div>
						</div>
					</a>
				</div>
			</div>

			<!-- <button class="btn-next more-list" @click="addListHandler"> 더보기 </button> -->
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
<div class="top-btn" @click="scrollHandler"></div>
</template>


