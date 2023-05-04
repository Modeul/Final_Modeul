<template>
    <div class="favorite">
        <div class="header">
            <router-link to="/member/mypage" class="back"></router-link>
            <div class="title">관심 목록</div>
        </div>

        <div class="stuff-list" v-for="(f) in list" >
            <router-link :to="'/member/stuff/' + f.stuffId">
                <div class="d-gr li-gr m-t-13px list-cl">
					<!-- 나중에 전체를 div로 묶어서 main으로 크게 묶기 -->
					<div class="li-pic b-rad-1">
						<img v-if="f.imageName != null" class="listview-image" :src="'/images/member/stuff/' + f.imageName" alt="img">
						<img v-else-if="f.categoryId == '1'" class="listview-image" src="/images/member/stuff/category1.svg" alt="img">
						<img v-else-if="f.categoryId == '2'" class="listview-image" src="/images/member/stuff/category2.svg" alt="img">
						<img v-else-if="f.categoryId == '3'" class="listview-image" src="/images/member/stuff/category3.svg" alt="img">
						<img v-else class="listview-image" src="/images/member/stuff/member.png" alt="img">
					</div>
					<div class="li-categ-place">
					
						<span class="li-categ-place-p">
							{{ f.stuffPlace }}
						</span>
					</div>
					<div class="li-dday"
						:class="(f.deadlineState == 0)? 'expired' : 
						(f.deadlineState == 1)? 'day-left' : 
						(f.deadlineState == 2)? 'hour-left' : 'minute-left' ">{{ f.dDay }}</div>
					<div class="li-subj">{{ f.stuffTitle }}</div>
			
					<!-- <div @click.prevent="addToFavorite" v-if="!this.isFavorite" class="icon-empty-heart"></div>
					<div @click.prevent="addToFavorite" v-if="this.isFavorite" class="icon-full-heart" :value="f.stuffId"></div> -->


					<div :class=" f.isFavorite? 'filled-heart' : 'empty-heart'"
						@click.prevent="toggleFavorite()">
						<img v-if="!f.isFavorite" src="/images/member/stuff/empty-heart.png" alt="img">
						<img v-else src="/images/member/stuff/filled-heart.png" alt="img">					
					</div>
                </div>
                
            </router-link>
		</div>
    </div>
</template>


<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko'


export default {
	data() {
		return {
			memberId:2,
			page: '',
			list: [],
			categoryList: [],
			categoryId:'',
            isFavorite: false,
			
		};
	},
	computed: {
		
	},
	methods: {
		toggleFavorite() {
      		this.isFavorite = !this.isFavorite;
			console.log(this.isFavorite)
   		 },
        // addToFavorite(e) {
        // this.isFavorite = !this.isFavorite;
        // console.log(this.isFavorite)
		// e.target.value
        // },
		
		addListHandler() {

			this.$store.commit('LOADING_STATUS', true); // 해당 함수 true/false 로 어디서나 추가 가능
			// setTimeout(() => { this.$store.commit('LOADING_STATUS', false); }, 400); //settimout은 지워도 됨

			this.page++;
			fetch(`${this.$store.state.host}/api/favorites?memberId=${this.memberId}`)
				.then(response => response.json())
				.then(dataList => {
					this.list = this.formatDateList(dataList.list);
					// this.categoryList = dataList.categoryList;
					//console.log(`categoryList:${this.list}`);
						this.$store.commit('LOADING_STATUS', false);
				})
				.catch(error => console.log('error', error));
				
		},
		formatDateList(list) {
			if (list == null)
				return;
			let resultList = [];
			for (let item of list) {
				if (item.stuffDeadline == null)
					continue;
				const today = new dayjs().format('YYYY-MM-DD');

				const deadlineObj = dayjs(item.stuffDeadline).locale('ko');
				const isToday = (deadlineObj.format('YYYY-MM-DD') == today) ? '오늘, ' : ''
				item.stuffDeadline = isToday + deadlineObj.format("M월 D일 (dd) HH시까지");

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
@import "/css/component/member/stuff/component-list.css";
@import "/css/button.css";
@import "/css/style.css";


.favorite{
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 0;
        position: relative;
        width: 360px;
        height: 740px;
        background: #FFFFFF;
    }
    .favorite .header{
        display: flex;
        flex-direction: row;
        align-items: center;
        padding: 0px;
        gap: 10px;
        width: 312px;
        height: 88px;
        border-bottom: 0.3px #d5d5d5 solid;
    }
    .favorite .header .back{
        background-image: url("data:image/svg+xml,%3Csvg width='16' height='16' viewBox='0 0 16 16' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M16 7H3.83L9.42 1.41L8 0L0 8L8 16L9.41 14.59L3.83 9H16V7Z' fill='black'/%3E%3C/svg%3E%0A");
        width: 23.04px;
        height: 24px;
        margin-top: 9px;
    }
    .favorite .header .title{
        margin-left: 86px;
    }

    .stuff-list{

    padding: 12px 0px 16px 0px;
    border-bottom: 0.3px #d5d5d5 solid;
    box-sizing: border-box;
    }
    .stuff-list:hover {
	transition: 0.3s;
	background-color: #fbfbfb;
	border-radius: 5px;
}
    .li-pic {
	grid-area: pic;
	width: 68px;
	height: 68px;
	display: inline-block;
	overflow: hidden;
	/* text-indent: -999px; */
	justify-self: center;
	align-self: center;
}

.li-gr {
	grid-template-columns: 68px 10px 100px 74px 8px 70px;
	grid-template-rows: 36px 12px 12px;
	grid-template-areas:
		"pic . subj subj . dday"
		"pic . . . . ."
		"pic . ct ct . heart ";

	align-items: center;
	/* grid-row-gap: 1%; */
	/* grid-column-gap: 10px; */
}
.icon-full-heart {
    grid-area: heart;
	justify-self: right;
	align-self: center;
	width: 20px;
	height: 20px;
	background-size: 100%;
}
.icon-empty-heart{
    grid-area: heart;
	/* background-repeat: no-repeat;
	background-position: center;
	width: 19px;
	height: 19px;
	display: inline-block;
	text-indent: -9999px; */
    justify-self: right;
	align-self: center;
	width: 20px;
	height: 20px;
	background-size: 100%;
}

.filled-heart{
	grid-area: heart;
	justify-self: right;
	align-self: center;
	width: 20px;
	height: 20px;
	background-size: 100%;
}

.empty-heart{
	grid-area: heart;
	justify-self: right;
	align-self: center;
	width: 20px;
	height: 20px;
	background-size: 100%;
}


</style>