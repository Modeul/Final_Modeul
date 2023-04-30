<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko'


export default {
	data() {
		return {
			page: '',
			list: [],
			categoryList: [],
			categoryId:'',
			listCount:'',
			myMemberId:'2',
		};
	},
	computed: {
	},
	methods: {
		categoryHandler(e){	
			this.page=1;
			this.categoryId = e.target.value;
			console.log(this.categoryId);
			fetch(`${this.$store.state.host}/api/stuffs?p=${this.page}&c=${this.categoryId}&id=${this.myMemberId}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = this.formatDateList(dataList.list);
					this.listCount = dataList.listCount;
					this.categoryList = dataList.categoryList;
					console.log(this.list)
				}).catch(error => console.log('error', error));
		},
		async addListHandler() {

			this.$store.commit('LOADING_STATUS', true); // 해당 함수 true/false 로 어디서나 추가 가능
			// setTimeout(() => { this.$store.commit('LOADING_STATUS', false); }, 400); //settimout은 지워도 됨

			this.page++;
			await fetch(`${this.$store.state.host}/api/stuffs?p=${this.page}&c=${this.categoryId}&id=${this.myMemberId}`)
				// .then(response => {
				// 	console.log(response)
				// 	return response.json()})
				.then(response => response.json())
				.then(dataList => {
					this.list = this.formatDateList(dataList.list);
					this.listCount = dataList.listCount;
					this.categoryList = dataList.categoryList;
					console.log(dataList);
						this.$store.commit('LOADING_STATUS', false);
				})
				.catch(error => console.log('error', error));
				
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
				if (parseInt(item.dDay) < 0){
					item.dDay = 'D' + item.dDay;
					item.deadlineState = 1;
				}
				else if (parseInt(item.dDay) == 0) {
					item.dDay = deadlineObj.diff(dayjs(), 'hours')
					if (parseInt(item.dDay) > 0){
						item.dDay = '마감 ' + deadlineObj.diff(dayjs(), 'hours') + '시간 전'
						item.deadlineState = 2;
					}
					else if (parseInt(item.dDay) == 0){
						item.dDay = '1시간 내 마감';
						item.deadlineState = 3;
					}
					else{
						item.dDay = '마감';
						item.deadlineState = 0;
					}
				}
				else{
					item.dDay = '마감';
					item.deadlineState = 0;
				}
				resultList.push(item);
			}
			return resultList;
		},
	},
	mounted() {
		this.page = 0;
		this.addListHandler();

	}
}
</script>

<style scoped>
@import url(/css/component/member/stuff/component-list.css);
</style>
<template>
	<section class="canvas p-rel b-rad-2">
		<header class="d-fl-al header-jc">
			내가 쓴 게시글

		<div>
				<!-- <a class="icon icon-menu">메뉴</a> -->
				<a class="icon icon-alarm">알림</a>
				<a class="icon">
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
								<router-link to="/member/stuff/crawlinglist">추천상품</router-link>
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
				</a>
			</div>
		</header>

		<nav>
			<div class="header-categ-box">
				<div>
					<button class="header-categ" @click="categoryHandler" name="c">전체</button>
				</div>

				<div v-for="c in categoryList">
					<button class="header-categ" @click="categoryHandler" name="c" :value="c.id">{{ c.name }}</button>
				</div>
			</div>
		</nav>

		<!-- 나중에 onclick 이벤트 하트 부분만 빼고 넣기 -->
		<main>
			<div class="stuff-list" v-for="stuff in list">
				<router-link :to="'./' + stuff.id">
						<div class="d-gr li-gr m-t-13px list-cl">
							<!-- 나중에 전체를 div로 묶어서 main으로 크게 묶기 -->
							<div class="li-pic b-rad-1">
								<img v-if="stuff.imageName != null" class="listview-image" :src="'/images/member/stuff/' + stuff.imageName" alt="img">
								<img v-else-if="stuff.categoryId == '1'" class="listview-image" src="/images/member/stuff/category1.svg" alt="img">
								<img v-else-if="stuff.categoryId == '2'" class="listview-image" src="/images/member/stuff/category2.svg" alt="img">
								<img v-else-if="stuff.categoryId == '3'" class="listview-image" src="/images/member/stuff/category3.svg" alt="img">
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
							<div class="li-dday"
							:class="(stuff.deadlineState == 0)? 'expired' : 
							(stuff.deadlineState == 1)? 'day-left' : 
							(stuff.deadlineState == 2)? 'hour-left' : 'minute-left' ">{{ stuff.dDay }}</div>
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
@import "/css/component/member/stuff/component-list.css";
@import "/css/button.css";
</style>