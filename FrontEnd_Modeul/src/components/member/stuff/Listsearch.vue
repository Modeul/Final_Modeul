<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko'


export default {
	data() {
		return {
			page: '',
			list: [],
			queryList:[],
			categoryId:'',
			query:'',
			queryisVal:true,
			// listCount: ''
		}
	},
	methods: {
		goback() {
			this.$router.go(-1);
		},
        searchInput(e){
			this.page = 1;
			e.preventDefault();
            this.query = e.target.value;
	
			console.log(this.query);
			fetch(`${this.$store.state.host}/api/stuffs?p=${this.page}&q=${this.query}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = this.formatDateList(dataList.queryList);
				
				}).catch(error => console.log('error', error));
        },
		addListHandler() {
			this.page++;
			fetch(`${this.$store.state.host}/api/stuffs?q=${this.query}&p=${this.page}&c=${this.categoryId}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = this.formatDateList(dataList.queryList);
					console.log(this.list);
				})
				.catch(error => console.log('error', error));
		},
        formatDateList(list) {
			if (list.length == 0)
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
        }
	},
	mounted() {
		this.page = 0;
		this.addListHandler();
	}
}
</script>
<style scoped>
@import url(/css/component/member/stuff/component-list.css);
@import url(/css/component/member/stuff/component-list-search.css);
</style>
<template>
    <section class="canvas b-rad-2">

        <!-- 검색창 들어가는 부분 -->
        <header class="d-fl-al">
                <div>
                    <router-link to="/member/stuff/list" class="icon icon-back" @click="goback">뒤로가기</router-link>
                </div>

                <div class="search-container">
                    <div class="d-fl d-b-none search-form">
                        <h1 class="icon search-dodbogi m-l-6px">돋보기</h1>
                        <input id="search-bar" class="search-input m-l-6px" name="q" @keyup.enter="searchInput" placeholder="검색어 입력">
                    </div>
                </div>
        </header>

       <main>
        
			<div class="stuff-list" v-for="stuff in list">
				<router-link :to="'./' + stuff.id">
						<div class="d-gr li-gr m-t-13px list-cl">
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
								<span class="li-member-limit"> 1</span>
								/ {{ stuff.numPeople }} 명
							</div>
							
						</div>
					</router-link>
			</div>
		

			<button class="btn-next more-list" @click="addListHandler"> 더보기 </button>
			<router-link to="/member/stuff/reg">
				<div class="reg-stuff">
				</div>
			</router-link>
		</main>
    </section>
</template>

