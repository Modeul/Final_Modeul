<script>
import Loader from './components/common/loader.vue';
import { useDefaultStore } from './stores/useDefaultStore';

export default {
	data() {
		return {
			massageView: [],
			defaultStore: useDefaultStore()
		}
	},
	components: {
		Loader
	},
	methods: {
	},
	created() {
 	},
	mounted() {
		this.defaultStore.initHost();
	},
}
</script>
<template>
	<v-app>
		<router-view></router-view>
		<Loader :loading="defaultStore.loadingStatus"></Loader>
	</v-app>
</template>

<!-- $store.commit -->