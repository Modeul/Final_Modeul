<script>
import Header from './Header.vue';
import Aside from './Aside.vue';

export default {
	components: {
		Header,
		Aside
	},
	data() {
		return {
		}
	}
}
</script>

<template>
	<!-- <section class="canvas-2 d-fl fl-dir-col"> -->
	<section>
		<Header />
		<!-- --------------------------------------- -->
		<Aside />
		<!-- --------------------------------------- -->
		<div class="admin-main-wrap">
			<router-view></router-view>
		</div>
	</section>
</template>

<style scoped>
	section{
		display: flex;
		height: 100%;
	}
	.admin-main-wrap{
		width: 100%;
	}
</style>