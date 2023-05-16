import { defineStore } from "pinia";

export const useDefaultStore = defineStore("default", {
  state: () => ({
    loadingStatus: false,
    host: ""
  }),
  getters: {
    isAuthenticated: (state) => (state.uid ? true : false)
  },
  actions: {
    initHost() {
      let hostName = "http://" + document.location.host;
      if (hostName.indexOf("localhost") !== -1) {
        hostName = "http://localhost:8080";
      }
      this.host = hostName;
    }
  },
  persist: {
    enabled: true
  }
});
