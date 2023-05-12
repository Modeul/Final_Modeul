<template>
	<aside class="admin-sidebar">
		<h1 class="d-none">사이드바</h1>
		<div class="admin-logo-moduel">
			<router-link to="/member/stuff/list"></router-link>
		</div>
		<div class="side-menu-wrap">
			<!-- 관리자 홈 버튼 숨김 -->
			<router-link hidden class="admin-side-wrap" to="/admin/index">
				<div class="iconbox-admin">
					<div class="icon-admin1 icon-home">홈</div>
					<h1 class="label-admin">홈</h1>
				</div>
			</router-link>
			<router-link class="admin-side-wrap" to="/admin/member/list">
				<div class="iconbox-admin m-t-35px">
					<!-- <a href="../member/list.html" class="icon-admin1 icon-membermanage">회원 관리</a> -->
					<div class="icon-admin1 icon-membermanage">회원 관리</div>
					<h1 class="label-admin">회원 관리</h1>
				</div>
			</router-link>
			<router-link class="admin-side-wrap" to="/admin/stuff/list">
				<div class="iconbox-admin m-t-35px">
					<!-- <a href="../stuff/list.html" class="icon-admin1 icon-stuffmanage">게시글 관리</a> -->
					<div class="icon-admin1 icon-stuffmanage">게시글 관리</div>
					<h1 class="label-admin">게시글 관리</h1>
				</div>
			</router-link>
			<router-link class="admin-side-wrap" to="/admin/category/list">
				<div class="iconbox-admin m-t-35px">
					<!-- <a href="../category/list.html" class="icon-admin1 icon-category">카테고리 관리</a> -->
					<div div class="icon-admin1 icon-category">카테고리 관리</div>
					<h1 class="label-admin">카테고리 관리</h1>
				</div>
			</router-link>
			<router-link class="admin-side-wrap" to="/admin/report/list">
				<div class="iconbox-admin m-t-35px">
					<!-- <a href="../category/list.html" class="icon-admin1 icon-category">카테고리 관리</a> -->
					<div div class="icon-admin1 icon-report">신고 관리</div>
					<h1 class="label-admin">신고 관리</h1>
				</div>
			</router-link>
			<!-- <div class="iconbox-admin m-t-35px">
			<a href="../analytics/list.html" class="icon-admin1 icon-analytics">통계</a>
			<h1 class="label-admin">통계</h1> -->
		</div>
	</aside>
</template>

<script>
export default {
	data() {
		return {
		}
	},
	methods: {
		// openAside() {
		// this.isCheck = !this.isCheck;
		// }
	},
}
</script>

<style scoped>
@import "/css/component/component.css";
@import "/css/component/admin/component-admin.css";

a.admin-side-wrap {
	height: 30px;
	color: #333;
}
</style>