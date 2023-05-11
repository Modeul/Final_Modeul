<script>
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../../stores/useDefaultStore';

export default {
	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			query: ''
		};
	},
	methods: {
		queryHandler(e) {
			this.$emit('query', e.target.value);
		}
	}
}
</script>

<template>
	<div class="pc-header-wrap">
		<div class="header-menu">
			<div v-if="!userDetails.isAuthenticated" class="signup"><router-link to="/signup">회원가입</router-link></div>
			<div v-if="!userDetails.isAuthenticated" class="login"><router-link to="/login">로그인</router-link></div>
			<div v-else @click.prevent="userDetails.logout" class="login">로그아웃</div>
		</div>
		<div class="pc-header">
			<div class="logo-moduel header-logo">
				<router-link to="/member/stuff/list"></router-link>
			</div>
			<div class="search-container" v-if="$route.path == '/member/stuff/list'">
				<div class="d-fl d-b-none search-form">
					<input id="search-bar" class="search-input m-l-6px" placeholder="검색어를 입력해주세요." @keyup.enter="queryHandler">
					<h1 class="icon search-dodbogi">돋보기</h1>
				</div>
			</div>
			<div class="btnbox">
				<div class="btn-heart"></div>
				<div class="btn-location"></div>
			</div>
		</div>
	</div>
</template>

<style scoped>
@import "/css/component/admin/member/list-responsive.css";
</style>
