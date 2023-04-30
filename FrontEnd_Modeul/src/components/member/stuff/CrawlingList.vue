<script>


export default {
	data() {
		return {
			page: '',
			list: [],
			categoryList: [],
			categoryId:'',
			listCount:''
		};
	},
	computed: {
	},
	methods: {
		goback() {
			this.$router.go(-1);
		},
		categoryHandler(e){	
			this.page=1;
			this.categoryId = e.target.value;
			console.log(this.categoryId);
			fetch(`${this.$store.state.host}/api/stuff/crawlinglist?p=${this.page}&c=${this.categoryId}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = dataList.crawlingList;
					this.listCount = dataList.listCount;
					this.categoryList = dataList.categoryList;
					// console.log(this.list)
				}).catch(error => console.log('error', error));
		},
		async addListHandler() {

			this.$store.commit('LOADING_STATUS', true); // 해당 함수 true/false 로 어디서나 추가 가능
			// setTimeout(() => { this.$store.commit('LOADING_STATUS', false); }, 400); //settimout은 지워도 됨
			console.log(this.page , this.categoryId);
			this.page++;
			await fetch(`${this.$store.state.host}/api/stuff/crawlinglist?p=${this.page}&c=${this.categoryId}`)
				// .then(response => {
				// 	console.log(response)
				// 	return response.json()})
				.then(response => response.json())
				.then(dataList => {
					this.list = dataList.crawlingList;
					this.listCount = dataList.listCount;
					this.categoryList = dataList.categoryList;
					console.log(dataList);
						this.$store.commit('LOADING_STATUS', false);
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
					<!-- <button class="header-categ" @click="categoryHandler" name="c">전체</button> -->
					<button class="header-categ">전체</button>
				</div>

				<!-- <div v-for="c in categoryList">
					<button class="header-categ" @click="categoryHandler" name="c" :value="c.id">{{ c.name }}</button>
				</div> -->
				<div>
					<button class="header-categ" > 쿠팡 </button>
				</div>
			</div>
		</nav>

		<!-- 나중에 onclick 이벤트 하트 부분만 빼고 넣기 -->
		<main>
			<div class="stuff-list" v-for="stuff in list">
				<a :href="stuff.contenturl">
						<div class="d-gr li-gr m-t-13px list-cl">
							<!-- 나중에 전체를 div로 묶어서 main으로 크게 묶기 -->
							<div class="li-pic b-rad-1">
								<img class="listview-image" :src="stuff.imgurl" alt="img">
							</div>
							<div class="li-categ-place">
								<span class="li-categ-place-categoryName">
									쿠팡
								</span>
								<!-- <span class="li-categ-place-p">
									{{ stuff.place }}
								</span> -->
							</div>
							<!-- <div class="li-dday">{{ stuff.price }}</div> -->
							<div class="li-subj">{{ stuff.title }}</div>
							<div class="li-member">
								<span class="li-member-limit"> {{ stuff.price }}₩</span>
							</div>
							<!-- <div class="li-place">{{ stuff.place }}</div> -->
							<!-- <div class="li-date">{{ stuff.deadline }} | {{'D' + stuff.dDay }}</div> -->
							<!-- <div class="li-date">{{'D' + stuff.dDay }}</div> -->
						</div>
					</a>
			</div>

			<button class="btn-next more-list" @click="addListHandler"> 더보기 <span> + {{ listCount }}</span></button>
			<router-link to="/member/stuff/reg">
				<div class="reg-stuff"></div>
			</router-link>
		</main>

		<nav class="navi-bar d-fl-jf" style="display: none;">
			<div>
				<router-link to="/member/stuff/list" class="icon icon-home m-notop">home</router-link>
			</div>
			<div>
				<router-link to="/member/stuff/listsearch" class="icon icon-search m-notop">search</router-link>
			</div>
			<div>
				<router-link to="/member/stuff/reg" class="icon icon-post m-notop">post+</router-link>
			</div>
			<div>
				<router-link to="/member/participation/list" class="icon icon-chat m-notop">chat</router-link>
			</div>
			<div>
				<a class="icon icon-info m-notop">mypage</a>
			</div>
		</nav>
	</section>
</template>


<style scoped>
@import "/css/button.css";
</style>