<script>
import Loader from './components/common/loader.vue';
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';

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
		},
		stompClient(){
			this.$store.commit('stompClient');
		},
		connect() {
			// const serverURL = `http://192.168.0.8:8080/ws-stomp`
			const serverURL = `http://localhost:8080/ws-stomp`
			let socket = new SockJS(serverURL);
			this.stompClient = Stomp.over(socket);
			let reconnect = 0;
			console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
			this.stompClient.connect(
				{},
				frame => {
					// 소켓 연결 성공!
					this.connected = true;
					console.log('소켓 연결 성공', frame);
					// 서버의 메시지 전송 endpoint를 구독합니다.
					// 이런형태를 pub sub 구조라고 합니다.
				},
				error => {
					// 소켓 연결 실패..
					console.log('소켓 연결 실패', error);

					// 소켓 연결시, 재연결
					if(reconnect++ <= 5) {
						setTimeout(function() {
							console.log("connection reconnect");
							socket = new SockJS(`http://localhost:8080/ws-stomp`);
							this.stompClient = Stomp.over(socket);
							this.stompClient.connect();
							console.log("connection complete");
						},5*1000);
					}

					this.connected = false;
				}
		)},     
	},
	created() {
    	// App.vue가 생성되면 소켓 연결을 시도합니다.
		this.stompClient();
		this.connect();
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