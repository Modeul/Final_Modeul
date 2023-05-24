<template>
	<div class="mypage">
		<div v-if="openModal" class="black-bg">
			<div class="delete-box">
				<div class="delete-box-1">프로필 사진이 변경되었습니다</div>
				<div class="delete-box-2">
					<div @click="modalHandler" class="delete-box-4">확인</div>
				</div>
			</div>
		</div>

		<div v-if="openModal2" class="black-bg">
			<div class="delete-box">
				<div class="delete-box-1">저장되었습니다</div>
				<div class="delete-box-2">
					<div @click="modalHandler2" class="delete-box-4">확인</div>
				</div>
			</div>
		</div>

		<div class="header">
			<router-link to="/member/mypage" class="back"></router-link>
			<div class="title">프로필 수정</div>
		</div>
		<div class="profile-pic">
			<div class="profile-img">
				<img class="profile-img" :src="'/images/member/' + loginInfo.image" />
			</div>
			<form method="post" enctype="multipart/form-data" ref="form">
				<label for="file">
					<div class="edit-btn2">
						<input type="file" class="d-none" id="file" name="imgs" @change.prevent="uploadImg" />
						<img src="/images/member/stuff/mypageEditIcon.svg" />
					</div>
				</label>
			</form>
		</div>
		<div class="mypage-input">
			<div class="input-field">
				<div class="uid-icon"></div>
				<span class="text">{{ loginInfo.uid }}</span>
			</div>
			<div class="input-field">
				<div class="email-icon"></div>
				<span class="text">{{ loginInfo.email }}</span>
			</div>
			<div>
				<div v-if="!this.nicknamebtn" class="input-field">
					<div class="nickname-icon"></div>
					<div class="text" type="text">
						{{ loginInfo.nickname }}
						<input @click.prevent="active" class="btn-change" id="btn-auth" type="button" value="변경" />
					</div>
				</div>
				<div v-if="this.nicknamebtn" class="input-field">
					<div class="nickname-icon"></div>
					<input class="text" type="text" v-model="loginInfo.nickname" />
					<input @click.prevent="checkNicknameDupl" class="btn-change" id="btn-auth" type="button" value="확인" />
				</div>
				<span class="error-txt">{{ this.ErrorMsg }}</span>
			</div>
		</div>
		<div v-if="this.nicknamebtn" @click.prevent="submit" class="btn-save">
			저장하기
		</div>
	</div>
</template>
<script>
import { useUserDetailsStore } from '../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../stores/useDefaultStore';

export default {
	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			ErrorMsg: "",
			nicknameDupl: "",
			nicknamebtn: false,
			loginInfo: "",
			file: [],
			openModal: false,
			openModal2: false,
		};
	},
	methods: {
		submit() {
			this.ErrorMsg = "";
			if (!this.loginInfo.nickname) {
				this.ErrorMsg = "닉네임은 필수 입력사항입니다.";
				this.nicknamebtn = false;
			} else if (!this.nicknameDupl) {
				this.ErrorMsg = "중복된 닉네임입니다.";
			} else if (this.loginInfo.nickname.length < 2 || this.loginInfo.nickname.length > 20) {
				this.ErrorMsg = "닉네임을 2글자 이상 입력해주세요.";
			}
			if (this.ErrorMsg) {
				console.log("에러메시지 존재");
				return false;
			}
			if (!this.ErrorMsg) {

				var myHeaders = new Headers();
				myHeaders.append("Content-Type", "application/json");

				var raw = JSON.stringify({
					"id": this.loginInfo.id,
					"nickname": this.loginInfo.nickname
				});

				var requestOptions = {
					method: 'PUT',
					headers: myHeaders,
					body: raw,
					redirect: 'follow'
				};

				fetch(`${this.defaultStore.host}/api/member/update`, requestOptions)
					.then(response => response.text())
					.then(result => console.log(result))
					.catch(error => console.log('error', error));
				// this.$router.replace('/member/mypage');
				this.openModal2 = true;
			}
		},
		active() {
			this.nicknamebtn = !this.nicknamebtn;
		},
		// 닉네임 중복 검사
		checkNicknameDupl() {
			this.nicknameDupl = "";
			this.ErrorMsg = "";
			fetch(
				`${this.defaultStore.host}/api/signup/checkNickname?nickname=${this.loginInfo.nickname}`
			)
				.then((response) => response.text())
				.then((result) => {
					console.log(result);
					if (result == "false") this.nicknameDupl = false;
					else this.nicknameDupl = true;

					if (!this.nicknameDupl) {
						this.nicknamebtn = false;
						this.ErrorMsg = "중복된 닉네임 입니다.";
					} else {
						this.nicknamebtn = true;
					}
				})
				.catch((error) => console.log("error", error));
		},
		async uploadImg(e) {
			this.file = e.target.files;
			console.log(e.target.files);
			this.loginInfo.image = this.file[0].name;

			var formData = new FormData(this.$refs.form);
			formData.append('id', this.userDetails.id);

			var requestOptions = {
				method: 'POST',
				body: formData,
				redirect: 'follow'
			};
			this.defaultStore.loadingStatus = true;
			await fetch(`${this.defaultStore.host}/api/member/updateImage`, requestOptions)
				.then(response => response.text())
				.then(result => {
					setTimeout(() => {
						this.load();
						this.defaultStore.loadingStatus = false;
						this.openModal = true;
					}, 500);
				})
				.catch(error => console.log('error', error));
		},
		modalHandler() {
			this.openModal = !this.openModal;
		},
		modalHandler2() {
			this.openModal2 = !this.openModal2;
		},
		async load() {
			await fetch(`${this.defaultStore.host}/api/member/${this.userDetails.id}`)
				.then(response => response.json())
				.then(data => {
					this.loginInfo = data;
					console.log(data);
				})
		}
	},
	mounted() {
		this.load();
	}
}


</script>
<style>
.mypage {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 0;
	margin: 0 auto;
	position: relative;
	width: 360px;
	height: 740px;

	background: #ffffff;
}

.mypage .header {
	display: flex;
	flex-direction: row;
	align-items: center;
	padding: 0px;
	gap: 10px;

	width: 312px;
	height: 88px;
}

.mypage .header .back {
	background-image: url("data:image/svg+xml,%3Csvg width='16' height='16' viewBox='0 0 16 16' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M16 7H3.83L9.42 1.41L8 0L0 8L8 16L9.41 14.59L3.83 9H16V7Z' fill='black'/%3E%3C/svg%3E%0A");
	width: 23.04px;
	height: 24px;
	margin-top: 9px;
}

.mypage .header .title {
	margin-left: 86px;
}

.profile-pic {
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	padding: 10px;
	gap: 10px;

	width: 312px;
	height: 130px;
}

.profile-img {
	width: 108px;
	height: 108px;
	border-radius: 50%;
}

.edit-btn2 {
	width: 36px;
	height: 36px;
	border-radius: 50%;
	position: absolute;
	right: 167px;
	top: 186px;
	cursor: pointer;
}

.edit-btn:hover {
	transform: scale(1.1);
	transition: 0.3s;
}

.mypage-input {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 0px 10px 10px;
	gap: 20px;
	margin-top: 52px;

	width: 312px;
	height: 225px;
}

.mypage-input .input-field {
	display: flex;
	position: relative;
	align-items: center;
	width: 300px;
	height: 45px;
	border: 1px solid #d5d5d5;
	border-radius: 10px;
}

.mypage-input .input-field-2 {
	display: flex;
	align-items: center;
	width: 300px;
	height: 45px;
	border: 1px solid #7299be;
	border-radius: 10px;
}

.mypage-input .uid-icon {
	width: 25px;
	height: 25px;
	left: 10px;
	top: 7px;
	margin-left: 12px;
	display: inline-block;
	background-size: contain;
	background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_649' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_649' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAj5JREFUaEPtmIExRDEQhv+rgA7oABWgAlSAClABOqACVIAKUAE6oAMqYD6TJZdzL3nvJY+7SWbe3LxLsvn/3X83ezfSjI/RjONXJfDXEawRqBHo6YG5ltBHT+c0bX+QtJHDflMEKoEGDw8SgRwRLm5jrpO4uPdyHFAjkMOLfWw0ReCuj2Fv77Okw8DWrqRtSavu+ydJN5Ku2p45xD2wJgmAjEVJ1w2X2L2kHUlvqURKE7h1njY8RDV2A0NiMweB2EEpZ7xI4mHsSbrwNh052djcsTe3L+ky5YAhqxCA0D7jVNJJAJB3I0EuQDg6hiTw6CWtnxcGkoRmDYOcYU10DEkAba87RGicd38gWat8yb3SkARiEjqTdOAYZZGQn1TRUAYLAGDJa1PUfUqoDQBT+xnM+XcFpdTmGs8uVUZ/kwhAALUV8UZYev8VAS4ypDSNBOCpPlkusrDMtZERIEMJ+fuRDEnrtxIkdZJsfENDJnEbBySvrQSSXfWzkDxYca/WrtidQOearH9sDBUBgNJG8LkcIc0tDKHzSB59mSlNANAUgxjoaZwgQ9MX3trf60sRoLrQeVqVCQGaVAwYkfGlFa5nHUTsd0VRAn5PYwe9uxJJmQTMNJ1Dgv2UWetczQZ7uCDHSJSIgN91ApyWgadVcrqI0F7wLDgWEz92ShCwvyRfnYTaAg/lQ1Tw+pKbGMNckkCHCpu0pRKIuankv9oTpb+EhGIEs85XAlnd2cFYjUAHp2XdUiOQ1Z0djM18BD4B8lljMc3J4QIAAAAASUVORK5CYII='/%3E%3C/defs%3E%3C/svg%3E%0A");
}

.mypage-input .email-icon {
	width: 25px;
	height: 25px;
	left: 10px;
	top: 7px;
	margin-left: 12px;
	display: inline-block;
	background-size: contain;
	background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_666' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_666' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAf9JREFUaEPtme01xEAUht+tgA7QgQ7ogA5QAR2gAlSADuiADnSwVIAKOI8z15ldid2bmUw2e+b+yTnJzJ37fsxHkolGHpOR168KYGgFqwJVgUQG1tZC55LOJG0mEpSr+4eka0mX8wmbFLiQBIBVDABQ3280AXhfIebnSXyVtLMIwFfU4EXSiSSuQ8SupFtJXC1mSG9SIAZgnZDtRhJeLBXYeMYuYWAXgE9JG6Ej8qHGU88I9iVdRazHNTC0CwB+u5O0FxVtq0FuNVjxbPWz4Z4lHUuadrWQoWVJRc6+1IB1vL4dCoV1xoMsIra1S4G4MclJeBCx8RBs1VUNWMcusGzxGPYgLGuRBYAlOwy2MjUonrkBGE+QB9Zts4R1gDTlyQqAIhmUudFFDfpSOABi1im+TcnsAP5Tg/ly3yLFUbDhMqzHKXoD0KYGSy22Mh8zf2Cdybos68UAxGowybfCDexgGxFXY/0teN2zn/SqQMwURVLsaYuF2M157l21igGwurEKk9zU6MJ6cQvNk25qcL8L64MD8OwJi9oWt9CigrzPKwAvY7nbVwVyM+rNVxXwMpa7fVUgN6PefFUBL2O527sU4Khr77u5C0nNx6nWvlz85FrLj7t2/OUl287zqcyl9od53i/+fGpc2x8cqYwV618VKEZ1y0BVgapAIgOjt9A34VGAMWTd1zgAAAAASUVORK5CYII='/%3E%3C/defs%3E%3C/svg%3E%0A");
}

.mypage-input .nickname-icon {
	width: 25px;
	height: 25px;
	left: 10px;
	top: 7px;
	margin-left: 12px;
	display: inline-block;
	background-size: contain;
	background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_665' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_665' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAiZJREFUaEPtmY0xBEEQhd9FgAgQASJABkRABogAESADIiADZCADRIAIqK9qV82N2Z3ZuZ49WzVddXV7d/PTr1//zdxME5fZxPVXBbBsBq0ZWJV0LulYEs+ufEp6kHQmiWcTsQZwL+kgotmTpH0T7SXTGMDiH4mKrVmxYMnAnqTHBsCzJD67guV3my9ggM8LSwXgmLAykONP1YWqC+X4jTOny4WopqeBapq6XSyNpq7TjqNyX0u69CeGAFw07cDQTdzx1gDatQGAfr8SAkA19fuYoWBKAXiTtBkD8J3gYiFAJVuJTp1CDOQCAJTbLnSxFmInxvBoAGCBYKMjXfG0+mraaZLD0HZ6NAAxS+b+XgHkWs5qXmWgz5IE7FYTmHcJAcp5+ShCjX/kLMYAGeXKUeZF0k5EuZRK76f3YgBeJW14CseOizDAq0/842gxAORzP98fNvneKoBZpxgAitaJo+m7pO2EOBgKrhgAFMGnoZxGi2fe+wSXW4+Mod1wpSiAodb8V0HMPQ8u07bfZCGs19frLB0ACnNiw226zg3cgd50XF7hQn7m8ln0L73MXAiFSZ2pBx4OH7GYSHFBMwBUXS5wU+XPETB1YqkgTilC7t63kngtKmYMLKpI7vwKINdyVvMqA1aWzF1nEAOhDjN3Y+t5NItzRbDU1aK14u16SVeLDKZfIefHusZSivrrYnnqydy9KIMs/+AYC8zcPhXAUszubDp5Bn4AITeSMeOKBiYAAAAASUVORK5CYII='/%3E%3C/defs%3E%3C/svg%3E%0A");
}

.mypage-input .input-field .text {
	display: inline-block;
	font-size: 14px;
	margin-left: 8px;
}

.btn-save {
	width: 280px;
	height: 40px;
	background: #222222;
	border-radius: 8px;
	font-weight: 700;
	font-size: 14px;
	line-height: 40px;
	color: white;
	text-align: center;
	cursor: pointer;
	transition: 0.3s;
}

.btn-save:hover {
	background: #7299be;
}

.btn-change {
	position: absolute;
	top: 50%;
	right: -3%;
	width: 40px;
	height: 25px;
	border: solid 1px #d5d5d5;
	border-radius: 5px;
	background-color: #ffffff;
	font-size: 10px;
	line-height: 13px;
	color: rgba(0, 0, 0, 0.7);
	cursor: pointer;

	transform: translate(-50%, -50%);
}

.btn-change:active {
	background: #7299be;
	transition: 0.3s;
}

.error-txt {
	font-size: 12px;
	color: red;
	margin-left: 8px;
}

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
	background: #ffffff;
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
	background: #ffffff;
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
	background: #ffffff;
	border-radius: 5px;
	border: 0.5px solid #e01616;
	color: #e01616;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 26px;
	cursor: pointer;
}

.delete-box-4 {
	width: 65px;
	height: 26px;
	background: #ffffff;
	border-radius: 5px;
	border: 0.5px solid #6a6a6a;
	color: #6a6a6a;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 26px;
	cursor: pointer;
}
</style>
