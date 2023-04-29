
<template>
	<!-- 추방 재확인 모달 -->
	<div v-if="openModal" class="black-bg">
		<div class="delete-box">
			<div class="delete-box-1">정말로 추방하시겠습니까?</div>
			<div class="delete-box-2">
				<div @click="banishUserHandler" class="delete-box-3">추방</div>
				<div @click="modalBanishCloseHandler" class="delete-box-4">취소</div>
			</div>
		</div>
	</div>

	<div v-if="openLeaveModal" class="black-bg">
		<div class="delete-box">
			<div class="delete-box-1">정말로 나가시겠습니까?</div>
			<div class="delete-box-2">
				<div @click="exitchatRoom" class="delete-box-3">나가기</div>
				<div @click="modalLeaveCloseHandler" class="delete-box-4">취소</div>
			</div>
		</div>
	</div>

	<v-dialog
		v-model="dialog"
		width="auto"
	>
		<v-card>
			<v-card-text>
				추방되었습니다.
			</v-card-text>
			<v-card-actions>
			<v-btn color="#63A0C2" block @click="dialog = false">확인</v-btn>
			</v-card-actions>
		</v-card>
	</v-dialog>

	<div class="canvas">
		<v-navigation-drawer v-model="drawer" temporary location="right" width="268" style="z-index: 1006;">
			<div class="chat-side">
				<div class="chat-side-top">
					<div class="chat-side-top-left">
						<div class="chat-side-title">{{ chat.title }}</div>
						<div class="chat-side-people">{{ chat.participantCount }}명 참여중</div>
						<div class="chat-side-date">개설일 {{ chat.regDate }}</div>
					</div>
					<div class="chat-side-top-right">
						<div class="chat-side-top-icon" v-if="banishAuthority" @click="showBanishHandler"></div>
					</div>
				</div>
				<div class="chat-side-list-wrap">
					<!-- 유저 1명 -->
					<div v-for="user in participantList" class="chat-side-list-user">
						<div class="chat-side-list-user-info">
							<div class="chat-user-img"><img class="chat-user-img" :src="'/images/member/' + user.memberImage"></div>
							<div class="chat-user-nickname">{{ user.memberNickname }}</div>
						</div>
						<div class="chat-side-list-user-icon"> <img @click="modalBanishHandler(user)" :class="{'d-none':!showBanish}"
							src="../../public/images/member/stuff/chatpeopleout.svg" alt="추방버튼"></div>
					</div>
				</div>
				<div class="chat-side-bottom">
					<div class="chat-side-bottom-icon" @click="modalLeaveHandler()"></div>
				</div>
			</div>


		</v-navigation-drawer>

		<v-app-bar height="80" density="compact" flat absolute>

			<template v-slot:prepend>
				<v-btn icon="mdi-arrow-left" @click="goback"></v-btn>
			</template>

			<p class="chat-title">{{ chat.title }}</p>
			<p class="chat-participant-count">{{ chat.participantCount }}</p>

			<template v-slot:append>
				<v-btn icon="mdi-menu" @click.stop="drawer = !drawer"></v-btn>
			</template>
		</v-app-bar>

		<div class="chat-canvas">

			<div v-for="m in messageView">
				<div class="chat-line-wrap" v-if="!(m.type == 'ENTER' || m.type == 'LEAVE')" :class="(myUserId == m.memberId) ? 'mine' : 'others'">
					<img v-if="!(myUserId == m.memberId)" class="user-profile" :src="'/images/member/' + m.memberImage">
					<div class="chat-box">
						<p v-if="!(myUserId == m.memberId)" class="chat-nickname">{{ m.sender }}</p>
						<div class="chat-content-wrap">
							<p class="chat-content">{{ m.content }}</p>
							<p class="chat-time">{{ m.sendDate }}</p>
						</div>
					</div>
				</div>
			</div>

			<div class="chat-input-wrap">
				<div class="cal-btn"><img src="../../images/member/stuff/cal-btn.svg"></div>
				<div class="chat-input-box">
					<input class="chat-input" placeholder="메시지를 입력해주세요." v-model="message" @keypress="sendMessage">
					<div class="submit-btn"><img src="../../images/member/stuff/chat-submit-btn.svg"></div>
				</div>
			</div>

		</div>
	</div>
</template>

<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko'
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';

export default {
	data() {
		return {
			userName: "",
			message: "",
			recvList: [],
			myUserId: this.$route.params.memberId,
			// stuffId: 449,
			stuffId: '',
			drawer: null,
			openModal: false,
			stompClient: '',

			participantList: '',
			chat: {
				title: "여러가지 나눔",
				participantCount: "12",
				regDate: ''
			},
			memberInfo:'',
			messageView: [],
			dialog:false,
			banishUser:{
				id:'',
				nickname:'',
				image:''
			},
			openLeaveModal:false,
			banishAuthority:false,
			showBanish:false,
		}
	},
	computed: {
	},
	methods: {
		sendMessage(e) {
			if (e.keyCode === 13 && this.message != '' && this.message.trim() != '') {
				console.log("send");
				this.send()
				this.message = ''
			}
		},
		send() {
			if (this.stompClient && this.stompClient.connected) {
				const date = new dayjs().locale('ko').format("A hh:mm");

				// 여기에 entity값에 맞게 DB에서 값을 가져와서 심어주기만 하면 된다.
				// 로그인을 통해 얻은 정보로 대체
				const chatMessage = {
					type: 'TALK',
					stuffId: this.$route.params.stuffId,
					memberId: this.memberInfo.id,
					sender: this.memberInfo.nickname,
					memberImage: this.memberInfo.image,
					sendDate: date,
					content: this.message,
				};

				this.myUserId = this.memberInfo.id;

				this.stompClient.send("/pub/chat/message", JSON.stringify(chatMessage));
			}
		},
		stompConnect() {
			const serverURL = `${this.$store.state.host}/ws-stomp`
			let socket = new SockJS(serverURL);
			this.stompClient = Stomp.over(socket);
		},
		connect() {
			this.stompClient.connect(
				{},
				async frame => {
					// 소켓 연결 성공!
					this.connected = true;

					await fetch(`${this.$store.state.host}/api/chatlog?
					stuffId=${this.$route.params.stuffId}&
					memberId=${this.$route.params.memberId}`)
						.then(response => response.text())
						.then(result => {
							this.messageView = JSON.parse(result)
						})
						.catch(error => console.log('error', error));

					// 1. 소켓 연결 성공하면 바로 구독하기! Topic 연결(방에 들어가면 등장 메세지 보내주기!)
					this.stompClient.subscribe(`/sub/chat/room/${this.$route.params.stuffId}`, res => {

						// 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
						this.messageView.push(JSON.parse(res.body));
					});

					// 2. 초기 설정 메세지 바로 보내준다. 위의 send 이벤트에 의해서 사용자 메세지가 전송된다,
					// 로그인 구현 이후 로그인으로 얻은 정보로 대체
					this.stompClient.send('/pub/chat/enterUser',
						JSON.stringify({
							type: 'ENTER',
							stuffId: this.$route.params.stuffId,
							memberId: this.memberInfo.id,
							sender: this.memberInfo.nickname,
							memberImage: this.memberInfo.image,
						})
					)
				});
		},
        exitchatRoom(){     
            var requestOptions = {
                method: 'DELETE',
                redirect: 'follow'
            };

            fetch(`${this.$store.state.host}/api/participation/${this.$route.params.stuffId}/${this.$route.params.memberId}`, requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(result);

                this.stompClient.send('/pub/chat/exitUser',
                    JSON.stringify({
                        type: 'LEAVE',
                        stuffId: this.$route.params.stuffId,
                        memberId: this.memberInfo.id,
                        sender: this.memberInfo.nickname,
                        memberImage: this.memberInfo.image,
                    })
                )

                // ** 소켓 끊어주기 추가
                this.stompClient.disconnect((frame) => {

                        this.stompClient.unsubscribe(`/sub/chat/room/${this.$route.params.stuffId}`);

                        // 소켓 연결 끊기 성공!
                        this.connected = false;
                        this.$router.go(-1);
                        console.log('소켓 연결 끊기 성공!', frame);
                });
                
            })
            .catch(error => console.log('error', error));
        },
		goback() {
			this.$router.go(-1);
		},
		loadParticipationList() {
			fetch(`${this.$store.state.host}/api/chat/${this.$route.params.stuffId}`)
				.then(response => response.json())
				.then(dataList => {
					this.participantList = dataList.memberList;
				})
				.catch(error => console.log('error', error));
		},
		async loadParticipant() {
			const response = await fetch(`${this.$store.state.host}/api/member/${this.$route.params.memberId}`);
			const data = await response.json();
			this.memberInfo = data;
			console.log("this.memberInfo:"+ this.memberInfo.id);
		},
		checkStuffLeader(){
			// 방장에게 추방 권한
			if(this.chat.memberId === this.memberInfo.id){
				this.banishAuthority = !this.banishAuthority;
			}
		},
        banishUserHandler() {
            
            var requestOptions = {
                method: 'DELETE',
                redirect: 'follow'
            };

            fetch(`${this.$store.state.host}/api/participation/${this.$route.params.stuffId}/${this.banishUser.id}`, requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(result);
                this.openModal = !this.openModal;
                this.loadParticipationList();
                this.dialog=true;

           		// ** 소켓 끊어주기 추가

                // 퇴장시켰는데 퇴장ID가 그게 본인ID이랑 같으면, 연결 끊어주기
                // 불린 값 1개 추가해줘서 그 값을 true로 인식하면, 
                
                // if(this.banishUser.id === this.$route.params.memberId){
                //  this.$router.go(0);
                //  this.stompClient.disconnect((frame) => {
                //          this.stompClient.unsubscribe(`/sub/chat/room/${this.$route.params.stuffId}`);
                            
                //          // 소켓 연결 끊기 성공!
                //          this.connected = false;
                //          console.log('소켓 연결 끊기 성공!', frame);

                //          // 강퇴된 그 사람이 뒤로가기 되기
                            
                //  });
                //  this.$router.go(-1);
                // }
            
            })
            .catch(error => console.log('error', error));
        },
		modalBanishHandler(user) {
			this.openModal = !this.openModal;
			this.banishUser.id = user.memberId;
			this.banishUser.nickname = user.memberNickname;
			this.banishUser.image = user.memberImage;
			console.log("banishUserId:" + this.banishUser.id); 
			console.log("banishUserNickName:" + this.banishUser.nickname); 
		},
		modalBanishCloseHandler() {
			this.openModal = !this.openModal;
		},
		modalLeaveHandler() {
			this.openLeaveModal = !this.openLeaveModal;
		},
		modalLeaveCloseHandler() {
			this.openLeaveModal = !this.openLeaveModal;
		},
		unLoadEvent() {
			this.stompClient.send('/pub/chat/exitUser',
				JSON.stringify({
					type: 'LEAVE',
					stuffId: this.$route.params.stuffId,
					memberId: this.memberInfo.id,
					sender: this.memberInfo.nickname,
					memberImage: this.memberInfo.image
				})
			);
		},
		formatChatRegDate() {
			const chatRegDateObj = dayjs(this.chat.regDate).locale('ko');
			this.chat.regDate = chatRegDateObj.format("YYYY. M. D.");
		},
		showBanishHandler(){
			this.showBanish=!this.showBanish;
		}
	},
	beforeRouteLeave() {
		this.unLoadEvent()
	},
	created() {
		this.stompConnect();
		this.connect();
		this.loadParticipant();
	},
	updated() {
	},
	async mounted() {
		await fetch(`${this.$store.state.host}/api/chat/${this.$route.params.stuffId}`)
				.then(response => response.json())
				.then(dataList => {
					this.participantList = dataList.memberList;
					this.chat = dataList.stuffView;
					this.formatChatRegDate();
					console.log(this.participantList);
					console.log("this.participantList.memberId: "+this.participantList[0].memberId);
					console.log("this.chat.memberId:"+ this.chat.memberId);
				})
				.catch(error => console.log('error', error));

		window.addEventListener('beforeunload', this.unLoadEvent);
		setTimeout(() => {
			window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' });
		}, 100);

		this.checkStuffLeader();
	},
	beforeUnmount() {
		window.removeEventListener('beforeunload', this.unLoadEvent);
	},
	computed: {
		chatLength: function () {
			return this.messageView.length;
		}
	},
	watch: {
		chatLength: function () {
			setTimeout(() => {
				window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' });
			}, 50);
		}
	},
}
</script>
<style scoped>
.canvas .v-app-bar {
	min-width: 320px;
}

.chat-canvas {
	margin-top: 64px;
	padding-bottom: 78px;
}

.v-app-bar .chat-title {
	font-size: 14px;
	font-weight: 700;
}

.v-app-bar .chat-participant-count {
	font-size: 14px;
	color: #9F9F9F;
	margin-left: 4px;
}

.chat-line-wrap {
	width: 100%;
	display: flex;
	margin-top: 18px;
}

.chat-line-wrap.mine {
	flex-direction: row-reverse;
}

.chat-line-wrap .chat-box {
	margin-left: 8px;
}

.chat-line-wrap .user-profile {
	width: 38px;
	height: 38px;
	border-radius: 50%;
}

.chat-line-wrap .chat-nickname {
	color: #222222;
	font-size: 10px;
}

.chat-line-wrap .chat-content-wrap {
	display: flex;
	align-items: flex-end;
}

.chat-line-wrap.mine .chat-content-wrap {
	flex-direction: row-reverse;
}

.chat-line-wrap .chat-content {
	font-size: 14px;
	max-width: 220px;
	padding: 6px 12px;
	columns: #1A1A1A;
	word-break: break-all;
}

.chat-line-wrap.others .chat-content {
	margin-top: 6px;
	border: solid #63A0C2 1px;
	border-radius: 0 12px 12px 12px;
}

.chat-line-wrap.mine .chat-content {
	background-color: rgba(99, 160, 194, 0.4);
	border-radius: 12px 12px 0 12px;
}

.chat-content-wrap .chat-time {
	margin: 0 4px;
	color: #353535;
	font-size: 8px;
}

/* 인풋 */
.chat-input-wrap {
	display: flex;
	flex-direction: row;
	align-items: center;
	gap: 8px;
	margin: 0 auto;
	padding: 0 20px 20px 20px;
	box-sizing: border-box;
	position: fixed;
	right: 0;
	bottom: 0;
	width: 100%;
	height: 66px;

	background: #FFFFFF;
}

.cal-btn {
	width: 40px;
	height: 40px;
	cursor: pointer;
}

.chat-input-box {
	box-sizing: border-box;
	display: flex;
	flex-direction: row;
	align-items: center;

	width: 100%;
	height: 44px;

	background: #FFFFFF;
	border: 1px solid #333333;
	border-radius: 30px;

}

.chat-input {
	width: 90%;
	margin-left: 14px;
	font-size: 14px;
}

.submit-btn {
	width: 24px;
	height: 24px;
	margin-right: 15px;
	cursor: pointer;
}

/* 사이드바 */
.chat-side {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 0px 10px 10px;

	position: relative;
	width: 268px;
	height: 640px;

	background: #FFFFFF;

	z-index: 9;
}

.chat-side-top {
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	padding: 10px 8px;
	gap: 32px;
	width: 271px;
	height: 113px;
	background: #FFFFFF;
}

.chat-side-top-left {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: flex-start;
	padding: 14px 0px;
	gap: 10px;

	width: 120px;
	height: 113px;

	background: #ffffff;
}

.chat-side-title {
	font-size: 16px;
	line-height: 23px;
	align-items: center;
	color: #222222;
	width: 160px;
	overflow:hidden; 	
	white-space:nowrap;
	text-overflow:ellipsis; 
}

.chat-side-people {
	font-size: 12px;
	line-height: 17px;
	align-items: center;
	color: #222222;
}

.chat-side-date {
	font-size: 10px;
	line-height: 14px;
	align-items: center;
	color: #A4A4A4;
}

.chat-side-top-right {
	display: flex;
	flex-direction: column;
	justify-content: flex-end;
	align-items: flex-end;
	padding: 10px;
	gap: 10px;

	width: 98px;
	height: 113px;

	background: #ffffff;
}

.chat-side-top-icon {
	cursor: pointer;
	width: 21px;
	height: 14.67px;
	background-image: url("data:image/svg+xml,%3Csvg width='21' height='15' viewBox='0 0 21 15' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M13.6663 9.50004C11.2188 9.50004 6.33301 10.7192 6.33301 13.1667V15H20.9997V13.1667C20.9997 10.7192 16.1138 9.50004 13.6663 9.50004ZM0.833008 5.83337V7.66671H8.16634V5.83337M13.6663 7.66671C14.6388 7.66671 15.5714 7.2804 16.2591 6.59277C16.9467 5.90513 17.333 4.9725 17.333 4.00004C17.333 3.02758 16.9467 2.09495 16.2591 1.40732C15.5714 0.719682 14.6388 0.333374 13.6663 0.333374C12.6939 0.333374 11.7613 0.719682 11.0736 1.40732C10.386 2.09495 9.99968 3.02758 9.99968 4.00004C9.99968 4.9725 10.386 5.90513 11.0736 6.59277C11.7613 7.2804 12.6939 7.66671 13.6663 7.66671Z' fill='black'/%3E%3C/svg%3E%0A");
}

.chat-side-list-wrap {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 10px;
	gap: 10px;
	width: 268px;
	height: 453px;
	border-top: 1px solid #DEDEDE;
	background: #ffffff;
}

.chat-side-list-user {
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: space-between;
	padding: 10px;
	gap: 4px;

	width: 268px;
	height: 60px;

	background: #FFFFFF;
}

.chat-side-list-user:hover {
	background-color: #f2f2f2;
	transition: 0.3s;
}

.chat-side-list-user-info {
	box-sizing: border-box;
	display: flex;
	flex-direction: row;
	align-items: center;
	padding: 10px;
	gap: 8px;

	width: 210px;
	height: 50px;
}

.chat-user-img {
	width: 38px;
	height: 38px;
	object-fit: cover;
	border-radius: 50%;
}

.chat-user-nickname {
	font-size: 14px;
	line-height: 20px;
	align-items: center;
	color: #222222;
}

.chat-side-list-user-icon {
	box-sizing: border-box;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	cursor: pointer;
	margin-right: 10px;
}

.chat-side-bottom {
	display: flex;
	flex-direction: row;
	align-items: flex-start;
	position: fixed;
	bottom: 0;
	padding: 14px 10px 10px 20px;
	gap: 10px;
	width: 268px;
	height: 75px;
	border-top: 1px solid #DEDEDE;
	background: #F6F6F6;
}

.chat-side-bottom-icon {
	width: 20px;
	height: 20px;
	background-image: url("data:image/svg+xml,%3Csvg width='20' height='20' viewBox='0 0 20 20' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M12.6659 20C12.8427 20 13.0123 19.9247 13.1373 19.7908C13.2623 19.6568 13.3325 19.4752 13.3325 19.2857C13.3325 19.0963 13.2623 18.9146 13.1373 18.7806C13.0123 18.6467 12.8427 18.5714 12.6659 18.5714H3.99976C3.29256 18.5714 2.61432 18.2704 2.11426 17.7346C1.61419 17.1988 1.33325 16.472 1.33325 15.7143V4.28571C1.33325 3.52795 1.61419 2.80123 2.11426 2.26541C2.61432 1.72959 3.29256 1.42857 3.99976 1.42857H12.6659C12.8427 1.42857 13.0123 1.35332 13.1373 1.21936C13.2623 1.08541 13.3325 0.903726 13.3325 0.714286C13.3325 0.524845 13.2623 0.343164 13.1373 0.20921C13.0123 0.075255 12.8427 0 12.6659 0H3.99976C2.93896 0 1.9216 0.451529 1.1715 1.25526C0.421402 2.05898 0 3.14907 0 4.28571V15.7143C0 16.8509 0.421402 17.941 1.1715 18.7447C1.9216 19.5485 2.93896 20 3.99976 20H12.6659ZM14.1938 4.49429C14.2557 4.42777 14.3293 4.37499 14.4103 4.33898C14.4913 4.30297 14.5781 4.28444 14.6658 4.28444C14.7535 4.28444 14.8403 4.30297 14.9213 4.33898C15.0023 4.37499 15.0758 4.42777 15.1378 4.49429L19.8042 9.49429C19.8662 9.56064 19.9155 9.63946 19.9491 9.72624C19.9827 9.81302 20 9.90605 20 10C20 10.094 19.9827 10.187 19.9491 10.2738C19.9155 10.3605 19.8662 10.4394 19.8042 10.5057L15.1378 15.5057C15.0758 15.5721 15.0022 15.6248 14.9212 15.6607C14.8402 15.6967 14.7534 15.7152 14.6658 15.7152C14.5781 15.7152 14.4913 15.6967 14.4104 15.6607C14.3294 15.6248 14.2558 15.5721 14.1938 15.5057C14.1318 15.4393 14.0827 15.3605 14.0491 15.2737C14.0156 15.1869 13.9983 15.0939 13.9983 15C13.9983 14.9061 14.0156 14.8131 14.0491 14.7263C14.0827 14.6395 14.1318 14.5607 14.1938 14.4943L17.7229 10.7143H5.99964C5.82284 10.7143 5.65328 10.639 5.52827 10.5051C5.40325 10.3711 5.33302 10.1894 5.33302 10C5.33302 9.81056 5.40325 9.62888 5.52827 9.49492C5.65328 9.36097 5.82284 9.28571 5.99964 9.28571H17.7229L14.1938 5.50571C14.1317 5.43936 14.0825 5.36054 14.0489 5.27376C14.0153 5.18698 13.998 5.09395 13.998 5C13.998 4.90605 14.0153 4.81302 14.0489 4.72624C14.0825 4.63946 14.1317 4.56064 14.1938 4.49429Z' fill='black'/%3E%3C/svg%3E%0A");
	cursor: pointer;
}

/* 추방 확인 모달 */
.black-bg {
	position: fixed;
	background: rgba(0, 0, 0, 0.7);
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 1007;
}

.delete-box {
	width: 253px;
	height: 113px;
	background: #FFFFFF;
	border-radius: 10px;
	color: #000000;
	font-weight: 400;
	font-size: 12px;
	display: flex;
	align-items: center;
	flex-direction: column;
	position: relative;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	z-index: 1007;

}

.delete-box-1 {
	width: 135px;
	height: 12px;
	background: #FFFFFF;
	border-radius: 5px;
	color: #000000;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 12px;
	margin-top: 28px;
}

.delete-box-2 {
	width: 180px;
	height: 26px;
	margin-top: 23px;
	display: flex;
	justify-content: center;
}

.delete-box-3 {
	width: 65px;
	height: 26px;
	background: #FFFFFF;
	border-radius: 5px;
	border: 0.5px solid #E01616;
	color: #E01616;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 26px;
	cursor: pointer;
}

.delete-box-4 {
	width: 65px;
	height: 26px;
	background: #FFFFFF;
	border-radius: 5px;
	border: 0.5px solid #6A6A6A;
	color: #6A6A6A;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 26px;
	margin-left: 25px;
	cursor: pointer;
}

.v-dialog:deep{
	font-size: 14px;
}
</style>