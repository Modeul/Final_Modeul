<script>
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../../stores/useDefaultStore';

export default {
	// props: ["dongName"],
	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			query: '',
			loginMember: '',
			upHere1:false,
			upHere2:false,
			upHere3:false,
			upHere4:false
		};
	},
	methods: {
		handleSelectChange(e) {
			this.handleChange(e);
			this.$emit('change', e);
		},
		handleChange(e) {
			let selectedOption = e.target.value;
			if (selectedOption === "main") {
				this.$router.push("/member/stuff/list"); // "/list"는 실제 경로에 맞게 수정해야 합니다.
			}
		},
		queryHandler(e) {
			this.$emit('queryEmit', e.target.value);
		}
	},
	async mounted() {
		const response = await fetch(`${this.defaultStore.host}/api/member/${this.userDetails.id}`);
		const result = await response.json();
		this.loginMember = result;
	}
}
</script>

<template>
	<div class="pc-header-wrap">
		<div class="header-menu">
			<div v-if="!userDetails.isAuthenticated" class="signup"><router-link to="/signup">회원가입</router-link></div>
			<div v-else class="info">
				<p class="text">{{ loginMember.nickname }}</p> &nbsp;님 안녕하세요.&nbsp;&nbsp;
			</div>
			<div v-if="!userDetails.isAuthenticated" class="login"><router-link to="/login">로그인</router-link></div>
			<div v-else @click.prevent="userDetails.logout" class="logout"><router-link to="/login"></router-link></div>
		</div>
		<div class="pc-header">
			<div class="logo-moduel header-logo">
				<router-link to="/member/stuff/list"></router-link>
			</div>

			<div class="filter" v-if="$route.path == '/member/stuff/recommends'">
				<div class="search-container">
					<div class="d-fl d-b-none search-form">
						<div class="icon icon-crawling"></div>
						<select class="selectbox-set" @change="handleSelectChange">
							<option value="crwaling" default>추천상품</option>
							<option value="main">메인화면</option>
						</select>
						<div class="vertical"></div>
						<input id="search-bar" class="search-input" placeholder="검색어를 입력해주세요." @keyup.enter="queryHandler">
						<h1 class="icon search-dodbogi">돋보기</h1>
					</div>
				</div>
			</div>
			<div class="btn-wrap" v-if="userDetails.hasRole('ADMIN')">
				<div class="btnbox">
					<router-link @mouseover="upHere1 = true" @mouseleave="upHere1 = false" class="btn-setting"
						to="/admin/stuff/list"></router-link>
					<router-link @mouseover="upHere2 = true" @mouseleave="upHere2 = false" class="btn-crawling"
						to="/member/stuff/recommends"></router-link>
					<router-link @mouseover="upHere3 = true" @mouseleave="upHere3 = false" class="btn-heart"
						to="/member/mypage/favorite"></router-link>
					<router-link @mouseover="upHere4 = true" @mouseleave="upHere4 = false" class="btn-mypage"
						to="/member/mypage"></router-link>
				</div>
				<div class="set-txt" v-show="upHere1">
					<img src="/images/member/setting.svg" alt="">
				</div>
				<div class="crawling-txt" v-show="upHere2">
					<img src="/images/member/crawling.svg" alt="">
				</div>
				<div class="heart-txt" v-show="upHere3">
					<img src="/images/member/heart.svg" alt="">
				</div>
				<div class="mypage-txt" v-show="upHere4">
					<img src="/images/member/mypage.svg" alt="">
				</div>
			</div>
			<div class="btn-wrap" v-else>
				<div class="btnbox">
					<router-link @mouseover="upHere1 = true" @mouseleave="upHere1 = false" class="btn-setting d-none"
						to="/admin/stuff/list"></router-link>
					<router-link @mouseover="upHere2 = true" @mouseleave="upHere2 = false" class="btn-crawling"
						to="/member/stuff/recommends"></router-link>
					<router-link @mouseover="upHere3 = true" @mouseleave="upHere3 = false" class="btn-heart"
						to="/member/mypage/favorite"></router-link>
					<router-link @mouseover="upHere4 = true" @mouseleave="upHere4 = false" class="btn-mypage"
						to="/member/mypage"></router-link>
				</div>
				<div>
					<div class="set-txt" v-show="upHere1">
						<img src="/images/member/setting.svg" alt="">
					</div>
					<div class="crawling-txt2" v-show="upHere2">
						<img src="/images/member/crawling.svg" alt="">
					</div>
					<div class="heart-txt2" v-show="upHere3">
						<img src="/images/member/heart.svg" alt="">
					</div>
					<div class="mypage-txt" v-show="upHere4">
						<img src="/images/member/mypage.svg" alt="">
					</div>
				</div>
			</div>

		</div>
	</div>
</template>

<style scoped>
@import "/css/component/admin/member/list-responsive.css";

.icon-crawling {
	width: 24px;
	height: 24px;
	margin: 1%;
}
.btn-wrap{
	position: relative;
	top: 0;
	right:0;
}
.btn-txt-box{
	display: flex;
	position: absolute;
	width: 100%;
    height: 100%;
}
.set-txt{
	position: absolute;
	left:-13.57%;
}
.crawling-txt{
	position: absolute;
	left:12%;
}
.heart-txt{
	position: absolute;
	left:39%;
}
.mypage-txt{
	position: absolute;
	right:-14%;
}
.crawling-txt2{
	position: absolute;
	left:-13%;
}
.heart-txt2{
	position: absolute;
	left:26%;
}
</style>
