<script>
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../../stores/useDefaultStore';


export default {
	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			page: '',
			list: [],
			category: [],
			categoryId:'',
			queryList:[],
			categoryList:[]
		};
	},
	computed: {
	},
	methods: {
		regbuttonHandler(e){
			console.log(e.target.value);

		},
		searchInput(e){
			this.page = 1;
			e.preventDefault();
            this.query = e.target.value;
			console.log(this.query);
			fetch(`${this.defaultStore.host}/api/stuff/recommends?q=${this.query}&p=${this.page}&c=${this.categoryId}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = dataList.queryList;
					this.listCount = dataList.listCount;
					console.log(this.list)
				}).catch(error => console.log('error', error));
        },
		goback() {
			this.$router.go(-1);
		},
		categoryHandler(e){
			this.page=1;
			this.categoryId = e.target.value;
			console.log(this.categoryId);
			fetch(`${this.defaultStore.host}/api/stuff/recommends?q=${this.query}&p=${this.page}&c=${this.categoryId}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = dataList.categoryList;
					this.listCount = dataList.listCount;
					this.category = dataList.category;
					// console.log(this.list)
				}).catch(error => console.log('error', error));
		},
		async addListHandler() {

			this.defaultStore.loadingStatus = true; 
			console.log(this.categoryId);
			console.log(this.query);
			this.page++;
			await fetch(`${this.defaultStore.host}/api/stuff/recommends?q=${this.query}&p=${this.page}&c=${this.categoryId}`)
				.then(response => response.json())
				.then(dataList => {
					if (this.query == null)
						this.list = dataList.crawlingList;
					else this.list = dataList.queryList;
					if (this.categoryId == 1 || this.categoryId == 2 || this.categoryId == 3 ||this.categoryId == 4)
						this.list = dataList.categoryList;
					this.listCount = dataList.listCount;
					this.category = dataList.category;
					this.defaultStore.loadingStatus = false;
					console.log(this.list);
				})
				.catch(error => console.log('error', error));
				
		},
	},
	mounted() {
		this.page = 0;
		this.addListHandler();

	}
}
</script>

<style scoped>
@import url(/css/component/member/stuff/component-crawlinglist.css);
@import url(/css/component/member/stuff/component-list-search.css);
</style>
<template>
	<div>
        <router-link to="/member/stuff/list" class="icon icon-back" @click="goback">뒤로가기</router-link>
    </div>
	<section class="canvas p-rel b-rad-2">
		        <!-- 검색창 들어가는 부분 -->
        <header class="d-fl-al">
                <div class="search-container">
                    <div class="d-fl d-b-none search-form">
                        <h1 class="icon search-dodbogi m-l-6px">돋보기</h1>
                        <input id="search-bar" class="search-input m-l-6px" name="q" @keyup.enter="searchInput" placeholder="검색어 입력">
                    </div>
				</div>
		</header>

		<nav>
			<div class="header-categ-box">
				<div>
					<button class="header-categ" @click="categoryHandler">전체</button>
				</div>
				<div v-for="c in category">
					<button class="header-categ" @click="categoryHandler" name="c" :value="c.categoryId">{{ c.categoryName }}</button>
				</div>
			</div>
		</nav>

		<main>
			<div class="stuff-list" v-for="stuff in list">
				<a :href="stuff.contenturl" target="_blank">
					<div class="d-gr li-gr m-t-13px list-cl">
						<div class="li-pic b-rad-1">
							<img class="listview-image" :src="stuff.imgurl" alt="img">
						</div>
						<div class="li-categ-place">
							<span class="li-categ-place-categoryName">{{stuff.categoryName}}</span>
						</div>
						<router-link :to="{ path : '/member/stuff/crawlingreg/' + stuff.id}">
							<button class="icon-write" v-on:click="regbuttonHandler" name="id" :value="stuff.id"></button>
						</router-link>
						<div class="li-subj">{{ stuff.title }}</div>
						<div class="li-member">
							<span class="li-member-limit"> {{ stuff.price }} ₩</span>
						</div>
					</div>
				</a>
			</div>

			<button class="btn-next more-list" @click="addListHandler"> 더보기 </button>
			<nav class="navi-bar d-fl-jf">
				<div class="navi-icon">
					<router-link to="/member/stuff/list" class="icon icon-home">home</router-link>
				</div>
				<div class="navi-icon">
					<router-link to="/member/stuff/listsearch" class="icon icon-search">search</router-link>
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


