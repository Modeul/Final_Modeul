import * as types from './mutation_types';

export default{
    [types.LOADING_STATUS](state, loadingStatus){
        state.loadingStatus = loadingStatus
		},
		initHost: function(state){
			let hostName = 'http://' + document.location.host;
			if(hostName.indexOf("localhost") !== -1){
				hostName = "http://localhost:8080";
			}
			return state.host = hostName
		}
}