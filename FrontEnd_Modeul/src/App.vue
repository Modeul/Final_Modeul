<script>
import Loader from './components/common/loader.vue';

export default {
	components: {
		Loader
	},
	methods: {
		initHost(){
			this.$store.commit('initHost');
		}
	},
	mounted() {
		this.initHost();
	}
}
</script>
<template>
	<v-app>
		<router-view></router-view>
		<Loader :loading="this.$store.state.loadingStatus"></Loader>
	</v-app>
</template>