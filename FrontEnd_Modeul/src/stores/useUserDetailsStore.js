import { defineStore } from "pinia";

export const useUserDetailsStore = defineStore("userDetails", {
	state: () => ({
		id: 0,
		uid: null,
		roles: []
	}),
	getters: {
		isAuthenticated: (state) => state.uid ? true : false
	},
	actions: {
		logout() {
			this.id = 0;
			this.uid = null;
			this.roles = [];
		},
		// role을 받아서 true,false를 반환해주자.
		hasRole(role) {

			let result = this.roles.indexOf(role) < 0 ? false : true; // 있는 곳의 인덱스 값이 나온다. == 0 , 찾지 못하면 -1

			return result;
		}
	},
	persist: {
		enabled: true
	}
});