
<template>
	<div class="canvas">

		<v-navigation-drawer v-model="drawer" temporary location="right" width="268">
			<v-list-item prepend-avatar="https://randomuser.me/api/portraits/men/78.jpg">a</v-list-item>
			<v-list-item prepend-avatar="https://randomuser.me/api/portraits/men/79.jpg">b</v-list-item>
			<v-list-item prepend-avatar="https://randomuser.me/api/portraits/men/80.jpg">c</v-list-item>
			<v-list-item prepend-avatar="https://randomuser.me/api/portraits/men/81.jpg">d</v-list-item>
			<v-list-item prepend-avatar="https://randomuser.me/api/portraits/men/82.jpg">e</v-list-item>

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
			drawer: null,
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
	min-width: 360px;
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
</style>