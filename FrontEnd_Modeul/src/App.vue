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
		<Transition name="fade">
		<Loader></Loader>
	</Transition>
	</v-app>
</template>

<style scoped>

.fade-leave-active {
	transition: opacity 0.4s ease;
}
.fade-leave-to {
	opacity: 0;
}
</style>

<!-- $store.commit -->