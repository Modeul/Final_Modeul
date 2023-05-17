<script>
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../../stores/useDefaultStore';

export default {
	props: ["dongName"],
	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			query: '',
			loginMember: ''
		};
	},
	methods: {
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

			<div class="filter" v-if="$route.path == '/member/stuff/list'">
				<div class="search-container">
					<div class="d-fl d-b-none search-form">
						<div class="gps-box">
							<div class="icon icon-location"></div>
							<!-- <select class="selectbox-set" @change="onChange($event)"> -->
							<select class="selectbox-set" @change="this.$emit('change', $event)">
								<option value="" default>전체</option>
								<option value="my">{{ dongName }}</option>
								<option value="cur">현재위치</option>
							</select>
						</div>
						<div class="vertical"></div>
						<input id="search-bar" class="search-input" placeholder="검색어를 입력해주세요." @keyup.enter="queryHandler">
						<h1 class="icon search-dodbogi">돋보기</h1>
					</div>
				</div>
			</div>
			<div class="btnbox">
				<div class="btn-crawling">
					<router-link to="/member/stuff/recommends"></router-link>
				</div>
				<div class="btn-setting" v-if="userDetails.hasRole('ADMIN')">
					<router-link to="/admin/stuff/list"></router-link>
				</div>
				<div class="btn-heart">
					<router-link to="/member/mypage/favorite"></router-link>
				</div>
				<div class="btn-mypage">
					<router-link to="/member/mypage"></router-link>
				</div>
			</div>
		</div>
	</div>
</template>

<style scoped>
@import "/css/component/admin/member/list-responsive.css";
</style>
