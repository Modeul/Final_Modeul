import * as types from './mutation_types';
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';

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
		},
		stompClient: function(state){
			// const serverURL = 'http://192.168.0.8:8080/ws-stomp'
			const serverURL = 'http://localhost:8080/ws-stomp'
			let socket = new SockJS(serverURL);
			let stompClient = Stomp.over(socket);

			return state.stompClient = stompClient
		}
}