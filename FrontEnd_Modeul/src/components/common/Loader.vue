<template>
	<div class="loader-warp" v-if="defaultStore.loadingStatus">
		<div class="loading">
			<span></span>
			<span></span>
			<span></span>
		</div>
	</div>
</template>

<script>
import { useDefaultStore } from '../../stores/useDefaultStore.js'

export default {

	data() {
		return {
			defaultStore: useDefaultStore()
		}
	}
};
</script>

<style scoped>
.loader-warp{
  position: fixed;
  width : 100vw;
  height : 100vh;
  top: 0;
  left: 0;
  background-color: rgb(255, 255, 255);
  opacity: 80%;
  z-index: 80;
	display: flex;
}

.loading {
	display: flex;
	justify-content: center;	
	/* margin-top: 57px; */
	margin: auto;
  z-index: 99;
}

.loading span {
	display: inline-block;
	width: 12px;
	height: 12px;
	border-radius: 50%;
	margin-right: 10px;
	animation: loading 1s 0s linear infinite;
}

.loading span:nth-child(1) {
	animation-delay: 0s;
	background-color: #125ECF;
}

.loading span:nth-child(2) {
	animation-delay: 0.2s;
	background-color: #3E77CD;
}

.loading span:nth-child(3) {
	animation-delay: 0.4s;
	background-color: #8FB9F8;
	margin-right: 0;
}

@keyframes loading {

	0%,
	100% {
		opacity: 0;
		transform: scale(0.5);
	}

	50% {
		opacity: 1;
		transform: scale(1);
	}
}</style>


