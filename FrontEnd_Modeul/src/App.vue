<script>
import Loader from './components/common/loader.vue';

export default {
	data() {
		return {
			massageView: []
		}
	},
	components: {
		Loader
	},
	methods: {
		initHost(){
			this.$store.commit('initHost');
		}
	},
	created() {
 	},
	mounted() {
		this.initHost();
	},
}
</script>
<template>
	<v-app>
		<router-view></router-view>
		<Loader :loading="this.$store.state.loadingStatus"></Loader>
	</v-app>
</template>

