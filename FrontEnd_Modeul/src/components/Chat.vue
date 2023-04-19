
<template>
	<div class="canvas">

		<v-navigation-drawer v-model="drawer" temporary location="right" width="268">
			<!-- <v-list-item prepend-avatar="https://randomuser.me/api/portraits/men/78.jpg">a</v-list-item>
			<v-list-item prepend-avatar="https://randomuser.me/api/portraits/men/79.jpg">b</v-list-item>
			<v-list-item prepend-avatar="https://randomuser.me/api/portraits/men/80.jpg">c</v-list-item>
			<v-list-item prepend-avatar="https://randomuser.me/api/portraits/men/81.jpg">d</v-list-item>
			<v-list-item prepend-avatar="https://randomuser.me/api/portraits/men/82.jpg">e</v-list-item> -->

		<div class="chat-side">
			<div class="chat-side-top">
				<div class="chat-side-top-left">
					<div class="chat-side-title">여러가지 나눔</div>
					<div class="chat-side-people">11명 참여중</div>
					<div class="chat-side-date">개설일 2023. 04. 18</div>
				</div>
				<div class="chat-side-top-right">
					<div class="chat-side-top-icon"></div>
				</div>
			</div>
			<div class="chat-side-list-wrap">
				<!-- 유저 1명 -->
				<div class="chat-side-list-user">
					<div class="chat-side-list-user-info">
						<div class="chat-user-img"><img src="../../public/images/member/stuff/chatid110.svg" alt=""></div>
						<div class="chat-user-nickname">감자탕</div>
					</div>
					<div class="chat-side-list-user-icon"> <img src="../../public/images/member/stuff/chatpeopleout.svg" alt="추방버튼"></div>
				</div>
			</div>
			<div class="chat-side-bottom">
				<div class="chat-side-bottom-icon"></div>
			</div>
		</div>


		</v-navigation-drawer>

		<v-app-bar height="80" density="compact" flat absolute>

			<template v-slot:prepend>
				<v-btn icon="mdi-arrow-left"></v-btn>
			</template>

			<p class="chat-title">{{ chat.title }}</p>
			<p class="chat-participant-count">{{ chat.participantCount }}</p>

			<template v-slot:append>
				<v-btn icon="mdi-menu" @click.stop="drawer = !drawer"></v-btn>
			</template>
		</v-app-bar>

		<div class="chat-canvas">


			<div class="chat-line-wrap" v-for="m in massageView" :class="(myUserId == m.user.userId)? 'mine':'others'">
				<img v-if="!(myUserId == m.user.userId)" class="user-profile" :src="m.user.userImg">
				<div class="chat-box">
					<p v-if="!(myUserId == m.user.userId)" class="chat-nickname">{{ m.user.userName }}</p>
					<div class="chat-content-wrap">
						<p class="chat-content">{{ m.massage.contents }}</p>
						<p class="chat-time">{{m.massage.time}}</p>
					</div>
				</div>
			</div>

		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			myUserId: 1,
			stuffId: 2,
			drawer: null,

			participant: {
				user: {
						userId: 1,
						userName: '감자',
						userImg: 'https://randomuser.me/api/portraits/men/78.jpg'
					}
			},
			chat: {
				title: "여러가지 나눔",
				participantCount: "12"
			},
			massageView: [
				{
					user: {
						userId: 1,
						userName: '감자',
						userImg: 'https://randomuser.me/api/portraits/men/78.jpg'
					},
					massage: {
						contents: '안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요',
						time: '오후 11:00'
					}
				},
				{
					user: {
						userId: 2,
						userName: '고구마',
						userImg: 'https://randomuser.me/api/portraits/men/79.jpg'
					},
					massage: {
						contents: 'aaaa',
						time: '오후 11:02'
					}
				},
				{
					user: {
						userId: 2,
						userName: '고구마',
						userImg: 'https://randomuser.me/api/portraits/men/79.jpg'
					},
					massage: {
						contents: '333333333333333333333333333333333333333333333333333333333',
						time: '오후 11:05'
					}
				},
				{
					user: {
						userId: 1,
						userName: '감자',
						userImg: 'https://randomuser.me/api/portraits/men/78.jpg'
					},
					massage: {
						contents: '444',
						time: '오후 11:10'
					}
				},
				{
					user: {
						userId: 2,
						userName: '고구마',
						userImg: 'https://randomuser.me/api/portraits/men/79.jpg'
					},
					massage: {
						contents: 'aaaa',
						time: '오후 11:02'
					}
				},
			],
		}
	},
	computed: {
	}
}
</script>
<style scoped>
.canvas, .v-app-bar {
	min-width: 320px;
}

.chat-canvas {
	margin-top: 64px;
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

.chat-line-wrap.mine .chat-content-wrap
{
	flex-direction: row-reverse;
}

.chat-line-wrap .chat-content{
	font-size: 14px;
	max-width: 240px;
	padding: 6px 12px;
	columns: #1A1A1A;
	word-break: break-all;
}

.chat-line-wrap.others .chat-content
{
	margin-top: 6px;
	border: solid #63A0C2 1px;
	border-radius: 0 12px 12px 12px;
}

.chat-line-wrap.mine .chat-content
{
	background-color: rgba(99, 160, 194, 0.4);
	border-radius: 12px 12px 0 12px;
}

.chat-content-wrap .chat-time
{
	margin: 0 4px;
	color: #353535;
	font-size: 8px;
}

.chat-side{
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 0px 10px 10px;
	
	position: relative;
	width: 268px;
	height: 640px;
	
	background: #FFFFFF;

}
.chat-side-top{
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
.chat-side-top-left{
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
.chat-side-title{
	font-size: 16px;
	line-height: 23px;
	align-items: center;
	color: #222222;
}
.chat-side-people{
	font-size: 12px;
	line-height: 17px;
	align-items: center;
	color: #222222;
}
.chat-side-date{
	font-size: 10px;
	line-height: 14px;
	align-items: center;
	color: #A4A4A4;
}

.chat-side-top-right{
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
.chat-side-top-icon{
	width: 20.17px;
	height: 14.67px;
	background-image: url("data:image/svg+xml,%3Csvg width='21' height='15' viewBox='0 0 21 15' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M13.6663 9.50004C11.2188 9.50004 6.33301 10.7192 6.33301 13.1667V15H20.9997V13.1667C20.9997 10.7192 16.1138 9.50004 13.6663 9.50004ZM0.833008 5.83337V7.66671H8.16634V5.83337M13.6663 7.66671C14.6388 7.66671 15.5714 7.2804 16.2591 6.59277C16.9467 5.90513 17.333 4.9725 17.333 4.00004C17.333 3.02758 16.9467 2.09495 16.2591 1.40732C15.5714 0.719682 14.6388 0.333374 13.6663 0.333374C12.6939 0.333374 11.7613 0.719682 11.0736 1.40732C10.386 2.09495 9.99968 3.02758 9.99968 4.00004C9.99968 4.9725 10.386 5.90513 11.0736 6.59277C11.7613 7.2804 12.6939 7.66671 13.6663 7.66671Z' fill='black'/%3E%3C/svg%3E%0A");
}
.chat-side-list-wrap{
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
.chat-side-list-user{
	display: flex;
	flex-direction: row;
	align-items: center;
	padding: 10px;
	gap: 4px;

	width: 268px;
	height: 60px;

	background: #FFFFFF;
}
.chat-side-list-user:hover{
	background-color: #f2f2f2;
}
.chat-side-list-user-info{
	box-sizing: border-box;
	display: flex;
	flex-direction: row;
	align-items: center;
	padding: 10px;
	gap: 8px;

	width: 210px;
	height: 50px;
}

.chat-user-img{
	width: 38px;
	height: 38px;
}
.chat-user-nickname{
	font-size: 14px;
	line-height: 20px;
	align-items: center;
	color: #222222;
}

.chat-side-list-user-icon{
	box-sizing: border-box;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	padding: 10px;
	gap: 10px;

	width: 30px;
	height: 50px;
}
.chat-side-bottom{
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
.chat-side-bottom-icon{
	width: 20px;
	height: 20px;
	background-image: url("data:image/svg+xml,%3Csvg width='20' height='20' viewBox='0 0 20 20' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M12.6659 20C12.8427 20 13.0123 19.9247 13.1373 19.7908C13.2623 19.6568 13.3325 19.4752 13.3325 19.2857C13.3325 19.0963 13.2623 18.9146 13.1373 18.7806C13.0123 18.6467 12.8427 18.5714 12.6659 18.5714H3.99976C3.29256 18.5714 2.61432 18.2704 2.11426 17.7346C1.61419 17.1988 1.33325 16.472 1.33325 15.7143V4.28571C1.33325 3.52795 1.61419 2.80123 2.11426 2.26541C2.61432 1.72959 3.29256 1.42857 3.99976 1.42857H12.6659C12.8427 1.42857 13.0123 1.35332 13.1373 1.21936C13.2623 1.08541 13.3325 0.903726 13.3325 0.714286C13.3325 0.524845 13.2623 0.343164 13.1373 0.20921C13.0123 0.075255 12.8427 0 12.6659 0H3.99976C2.93896 0 1.9216 0.451529 1.1715 1.25526C0.421402 2.05898 0 3.14907 0 4.28571V15.7143C0 16.8509 0.421402 17.941 1.1715 18.7447C1.9216 19.5485 2.93896 20 3.99976 20H12.6659ZM14.1938 4.49429C14.2557 4.42777 14.3293 4.37499 14.4103 4.33898C14.4913 4.30297 14.5781 4.28444 14.6658 4.28444C14.7535 4.28444 14.8403 4.30297 14.9213 4.33898C15.0023 4.37499 15.0758 4.42777 15.1378 4.49429L19.8042 9.49429C19.8662 9.56064 19.9155 9.63946 19.9491 9.72624C19.9827 9.81302 20 9.90605 20 10C20 10.094 19.9827 10.187 19.9491 10.2738C19.9155 10.3605 19.8662 10.4394 19.8042 10.5057L15.1378 15.5057C15.0758 15.5721 15.0022 15.6248 14.9212 15.6607C14.8402 15.6967 14.7534 15.7152 14.6658 15.7152C14.5781 15.7152 14.4913 15.6967 14.4104 15.6607C14.3294 15.6248 14.2558 15.5721 14.1938 15.5057C14.1318 15.4393 14.0827 15.3605 14.0491 15.2737C14.0156 15.1869 13.9983 15.0939 13.9983 15C13.9983 14.9061 14.0156 14.8131 14.0491 14.7263C14.0827 14.6395 14.1318 14.5607 14.1938 14.4943L17.7229 10.7143H5.99964C5.82284 10.7143 5.65328 10.639 5.52827 10.5051C5.40325 10.3711 5.33302 10.1894 5.33302 10C5.33302 9.81056 5.40325 9.62888 5.52827 9.49492C5.65328 9.36097 5.82284 9.28571 5.99964 9.28571H17.7229L14.1938 5.50571C14.1317 5.43936 14.0825 5.36054 14.0489 5.27376C14.0153 5.18698 13.998 5.09395 13.998 5C13.998 4.90605 14.0153 4.81302 14.0489 4.72624C14.0825 4.63946 14.1317 4.56064 14.1938 4.49429Z' fill='black'/%3E%3C/svg%3E%0A");
}
</style>