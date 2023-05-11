<script>
import Loader from './components/common/Loader.vue';
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
		<Loader></Loader>
	</v-app>
</template>

<!-- $store.commit -->