import { createStore } from "vuex";
import mutations from "./mutations";

const store = createStore({
	state: {
		loadingStatus: false,
		host : '',
		// host : 'http://' + window.location.host
		// stompClient: ''
	},
	mutations
})

export default store;
