<template>
	<div class="wrap">
		<header class="header">
			<router-link to="/member/mypage" class="back"></router-link>
			<div class="changpwd-title">개인정보 변경</div>
		</header>
		<section class="input-sec">
			<!-- 현재 비밀번호 -->
			<div class="input-box">
				<div class="input-field">
					<div class="pwd-icon"></div>
					<input class="txt" type="password" autofocus placeholder="현재 비밀번호를 입력해주세요." v-model="pwd">
					<div v-if="this.pwdbtn == ''" class="btn-null"></div>
					<div v-if="this.pwdbtn == true" class="btn-check"></div>
					<div v-if="this.pwdbtn == false" class="btn-x"></div>
				</div>
				<div class="error-txt">{{ this.pwdError }}</div>
			</div>
			<!-- 새로운 비밀번호 -->
			<div class="input-box" @input="isValidPwd">
				<div class="input-field">
					<div class="pwd-icon"></div>
					<input class="txt" type="password" placeholder="새로운 비밀번호를 입력해주세요." v-model="newPwd">
					<div v-if="pwdbtn2 == ''" class="btn-null"></div>
					<div v-if="pwdbtn2 == true" class="btn-check"></div>
					<div v-if="pwdbtn2 == false" class="btn-x"></div>
				</div>
				<div class="error-txt">{{ this.newPwdError }}</div>
			</div>
			<!-- 새로운 비밀번호 검증 -->
			<div class="input-box" @input="isValidPwdConfirm">
				<div class="input-field">
					<div class="pwd-check-icon"></div>
					<input class="txt" type="password" placeholder="비밀번호를 다시 입력해주세요." v-model="newPwdConfirm">
					<div v-if="pwdbtn3 == ''" class="btn-null"></div>
					<div v-if="pwdbtn3 == true" class="btn-check"></div>
					<div v-if="pwdbtn3 == false" class="btn-x"></div>
				</div>
				<div class="error-txt">{{ this.newPwdConfirmError }}</div>
			</div>

			<!-- 주소 -->
			<div class="input-box" @click.prevent="postCode">
				<div class="input-field">
					<div class="address-icon"></div>
					<input type="text" id="address" class="txt input-addr" v-model="addr" hidden />
					<div class="txt" v-text="addr"></div>
				</div>
			</div>
			<div class="input-box">
				<div class="input-field">
					<div class="address-icon"></div>
					<input type="text" class="txt input-addr2" v-model="addr2" placeholder="상세 주소" />
				</div>
			</div>



		</section>
		<div @click.prevent="submit" class="btn-save">저장하기</div>
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
			// myMemberId: '110',
			loginInfo: '',

			pwd: "",
			newPwd: "",
			newPwdConfirm: "",

			pwdbtn: null,
			pwdbtn2: null,
			pwdbtn3: null,

			pwdError: "",
			newPwdError: "",
			newPwdConfirmError: "",

			address: "",
			newAddress: "",
			addr: "",
			addr2: "",
			coordX: "",
			coordY: "",
			chkPwd: "",
		}
	},
	methods: {
		async checkPwd() {
			this.pwdbtn = null;
			if (!this.pwd) {
				this.pwdbtn = false;
				this.pwdError = "비밀번호를 확인하세요."
				this.chkPwd = false;
				return;
			}

			var myHeaders = new Headers();
			myHeaders.append("Content-Type", "application/json");

			var raw = JSON.stringify({
				"uid": this.loginInfo.uid,
				"pwd": this.pwd
			});

			var requestOptions = {
				method: 'POST',
				headers: myHeaders,
				body: raw,
				redirect: 'follow'
			};

			await fetch(`${this.defaultStore.host}/api/member/checkpwd`, requestOptions)
				.then(response => response.json())
				.then((result) => {
					if (result) {
						console.log("true");
						this.pwdbtn = true;
						this.pwdError = ""
						this.chkPwd = true;
						// return true;
					}
					else {
						console.log("false");
						this.pwdbtn = false;
						this.pwdError = "비밀번호를 확인하세요."
						this.chkPwd = false;
						// return false;
					}
				})
				.catch(error => console.log('error', error));
		},
		// pwd 검사
		isValidPwd() {
			this.pwdbtn2 = null;
			// 비밀번호는 8자 이상이어야 합니다.

			if (!this.newPwd && this.newAddress) {
	
				this.newPwdError = "";
				return true;
			}
			if (this.newPwd.length < 8) {
				this.pwdbtn2 = false;
				this.newPwdError = "올바른 비밀번호를 입력해주세요.(8자 이상 영어+숫자)";
				return false;
			}

			if (this.newPwd == this.pwd) {
				this.pwdbtn2 = false;
				this.newPwdError = "새로운 비밀번호를 입력해주세요.";
				return false;
			}

			// 비밀번호는 영문자와 숫자를 반드시 포함해야 합니다.
			const hasLetter = /[a-zA-Z]/.test(this.newPwd);
			const hasNumber = /\d/.test(this.newPwd);
			if (!hasLetter || !hasNumber) {
				this.pwdbtn2 = false;
				return false;
			}

			// 모든 검증을 통과한 경우 유효한 비밀번호입니다.
			this.pwdbtn2 = true;
			this.newPwdError = "";
			return true;
		},
		// pwd 일치 검사
		isValidPwdConfirm() {
			this.pwdbtn3 = null;

			if (!this.newPwd && !this.newPwdConfirm && this.newAddress) {
				this.newPwdConfirmError = "";
				return true
			}

			if (this.newPwd !== this.newPwdConfirm) {
				this.pwdbtn3 = false;
				this.newPwdConfirmError = "비밀번호가 일치하지 않습니다.";
				return false
			}
			this.pwdbtn3 = true;
			this.newPwdConfirmError = "";
			return true
		},

		addrStatus() {
			this.newAddress = this.addr + "," + this.addr2;
			return this.address === this.newAddress ? false : true;
		},

		postCode() {
			const geocoder = new daum.maps.services.Geocoder();
			new daum.Postcode({
				oncomplete: (data) => {

					this.addr = data.address;
					// this.dongCode = data.bcode;
					geocoder.addressSearch(data.address, (results, status) => {

						if (status === daum.maps.services.Status.OK) {

							let result = results[0];
							this.coordX = result.x;
							this.coordY = result.y;
							// this.addrError = false;
							document.querySelector(".input-addr2").focus();
						}
					});

				}
			}).open();
		},

		async submit() {

			let check = null;
			if (!this.addrStatus()) {
				this.newAddress = null;
				this.coordX = "";
				this.coordY = "";
			}

			if (this.pwd && this.newPwd && this.newPwdConfirm) {
				check = true;
			}
			else if (this.pwd && !this.newPwd && !this.newPwdConfirm && this.newAddress) {
				check = true;
			} else {
				check = false;
				console.log("폴스");
			}

			await this.checkPwd();

			if (this.chkPwd &&
				this.isValidPwd() &&
				this.isValidPwdConfirm() &&
				check) {
				var myHeaders = new Headers();
				myHeaders.append("Content-Type", "application/json");

				var raw = JSON.stringify({
					"id": this.loginInfo.id,
					"pwd": this.newPwd,
					"address": this.newAddress,
					"coordX": this.coordX,
					"coordY": this.coordY,
				});

				var requestOptions = {
					method: 'PUT',
					headers: myHeaders,
					body: raw,
					redirect: 'follow'
				};
				fetch(`${this.defaultStore.host}/api/member/update`, requestOptions)
					.then(response => response.text())
					.catch(error => console.log('error', error));
				this.$router.push('/member/mypage');

			} else{
			}


		}
	},
	mounted() {
		fetch(`${this.defaultStore.host}/api/member/${this.userDetails.id}`)
			.then(response => response.json())
			.then(data => {
				this.loginInfo = data;
				this.address = data.address;
				[this.addr, this.addr2] = data.address.split(',');
			});


	}
}

</script>

<style scoped>
@import url(/css/component/component.css);
@import url(/css/button.css);

.wrap {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 0;

	position: relative;
	width: 360px;

	background: #FFFFFF;
	margin: 0 auto;
}

.header {
	display: flex;
	flex-direction: row;
	align-items: center;
	padding: 0px;
	gap: 10px;

	width: 312px;
	margin-top: 25px;
}

.back {
	background-image: url("data:image/svg+xml,%3Csvg width='16' height='16' viewBox='0 0 16 16' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M16 7H3.83L9.42 1.41L8 0L0 8L8 16L9.41 14.59L3.83 9H16V7Z' fill='black'/%3E%3C/svg%3E%0A");
	width: 23.04px;
	height: 24px;
	margin-top: 9px;
}

.changpwd-title {
	margin-left: 86px;
	/* font-size: 14px; */
	color: #333;
}

.input-sec {
	margin-top: 120px;
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
	margin: 0 auto;
	margin-top: 60px;
}

.input-box {
	margin-bottom: 10px;
	height: 63px;
}

.input-sec .input-field {
	display: flex;
	position: relative;
	top: 0;
	right: 0;
	align-items: center;
	width: 300px;
	height: 45px;
	border: 1px solid #D5D5D5;
	border-radius: 10px;
	padding: 0 12px;
	box-sizing: border-box;
}

.input-sec .input-field:last-child {
	margin-bottom: 0;
}

.input-sec .btn-post {
	position: absolute;
	top: 50%;
	right: -3%;
	width: 40px;
	height: 25px;
	border: solid 1px #253232;
	border-radius: 5px;
	background-color: #FFFFFF;
	font-size: 10px;
	line-height: 13px;
	color: rgba(0, 0, 0, 0.7);
	cursor: pointer;

	transform: translate(-50%, -50%);
}

.pwd-icon {
	width: 25px;
	height: 25px;
	background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_663' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_663' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAnFJREFUaEPtl+ExBEEQhd9FQAaIABEgAkSACBABIkAEiAARIAJEgAyIgPqupq/6tuZuxnXVnVPTVfdjZ7t7+73X3bvX05xbb87rVwMwawWbAk2BIAOthYIEhsObAmEKgwmaAkECw+H/UoE1SeeSDiS9J4pOJX1LupT0WaDN4m8kXSffI0nbko4lvRTilyWdSP3vtP3ku5lqIv7Rx+cUoOil5LgliYRvKehMEmDGGUXvJaAryZH4RUmAsqJG5QAsBGLrCfCDJEC8SoKggeUA7Ei6TR4AOJTE2UcCU+pbCoWEBUkAxmD0K8WXFMTfkwjoK1dPUQF8cdpILFIQtivprlR9uo9KFO2tRj3zh21YxwBMDfeJyKGkXgH/QNrGS02SCxf51O3F5E/rmXVbzV+jps2HLxrSzGglI48z/G0mue6r6wEwpLWWY9NUq8kBAbDsLafauFz92j0A31sgX3XR9K/fHrDRZRCF/IB5NklF0WbkgmFvKO5VJxdzZMYA+/npEzDqPZBjg43iJRzHDoXY4Jkfa7kLelQOin/u3GSFd0FnAXTXJsXQ2wwwg1wy1LO1yeBhvANgDxJqtpCtTZsVm09bq4MacgqwQm1twgRS+bU6tMYyaGglVi/m3wNc12wirx6E8Txazr+bxgKgfZCKn0lOEpThrLRKAQ8IYm3zcM15TTyE4Y9SNuiA4ozf0HYbNQMU6/u9e13TRvhYu9g6rGkfy11Vw7/8mCux+6fuNwVmLUdToCkQZKC1UJDAcHhOgd/8LwgXMEGCoZobgAkYjIYUFYg+YKrxbQtNle7Mw5oCTYEgA62FggSGw5sCYQqDCX4ADxZ1McMpYs0AAAAASUVORK5CYII='/%3E%3C/defs%3E%3C/svg%3E%0A");
}

.pwd-check-icon {
	width: 25px;
	height: 25px;
	background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_664' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_664' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAbBJREFUaEPtmOFNxDAMhd9twCbABMAmMAFsALcBTABMAhsAm7AByFIjWaFp+ly7uFL75053sfM+20mdHLDx57Bx/dgB/juDWTJwDeACwA0bkAwAIv55EP7CQmQA+ARwqiJPQWQAOAHwboXIACDBN0NkATBDMAD37A5hGC+ZuKvsJtcEA/BjEORl0oTYCsArANlu/zxWgKNXaCs/UkK31W9N8TLOCsDYzWUV8W8AzpTBpPhMACbxWQDM4rMAfLBlo2uSqWW9jTJ2vTWgm7luzdfOGCFRAKJJIC5bW+VUBLIA9LLU/J8BeFBe9Hfz5B6GDIDHfO4+dgD3kJIO9wyQAesN12eOWQ1jtgzQ7xorQOnN5aDh+awCoF/9chHlCREOoMVL5L+qRmxpNkIBxsRL//K9VLWyDwNYQ7xwhACsJT4EoBYvkzw6l42uQN0kztohpwaNiXcs966rHaCclMrdfQnZU2AJtcpp0YGmLiW5z79aCcKnzoazqs5EGohZC2UIQ8pMMABja0Iycd7Nc+AAFqCG8G7maFQLQIGQT89OlBYvBlYA02QRRjtARFQZn5vPwC9zdFMxvDYwdQAAAABJRU5ErkJggg=='/%3E%3C/defs%3E%3C/svg%3E%0A");
}

.address-icon::before {
	content: "\e88a";
	font-family: 'Material Icons';
	font-size: 25px;
	/* margin-right: 8px; */
}

.txt {
	width: 70%;
	line-height: 45px;
	font-size: 12px;
	margin-left: 10px;
}

.btn-check2 {
	width: 12px;
	height: 8.8px;
	background-image: url("data:image/svg+xml,%3Csvg width='12' height='9' viewBox='0 0 12 9' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath fill-rule='evenodd' clip-rule='evenodd' d='M11.6475 0.350826C12.1177 0.816093 12.1177 1.57653 11.6475 2.0468L5.24881 8.45046C4.78354 8.92073 4.0231 8.92073 3.55283 8.45046L0.351001 5.24863C-0.119268 4.78336 -0.114265 4.02293 0.351001 3.55266C0.816267 3.08239 1.5767 3.08239 2.04697 3.55266L4.39832 5.90401L9.9515 0.350826C10.4168 -0.119443 11.1772 -0.11444 11.6475 0.350826Z' fill='%233BA739'/%3E%3Cpath fill-rule='evenodd' clip-rule='evenodd' d='M11.6475 0.350826C12.1177 0.816093 12.1177 1.57653 11.6475 2.0468L5.24881 8.45046C4.78354 8.92073 4.0231 8.92073 3.55283 8.45046L0.351001 5.24863C-0.119268 4.78336 -0.114265 4.02293 0.351001 3.55266C0.816267 3.08239 1.5767 3.08239 2.04697 3.55266L4.39832 5.90401L9.9515 0.350826C10.4168 -0.119443 11.1772 -0.11444 11.6475 0.350826Z' fill='%233BA739'/%3E%3Cpath fill-rule='evenodd' clip-rule='evenodd' d='M11.6475 0.350826C12.1177 0.816093 12.1177 1.57653 11.6475 2.0468L5.24881 8.45046C4.78354 8.92073 4.0231 8.92073 3.55283 8.45046L0.351001 5.24863C-0.119268 4.78336 -0.114265 4.02293 0.351001 3.55266C0.816267 3.08239 1.5767 3.08239 2.04697 3.55266L4.39832 5.90401L9.9515 0.350826C10.4168 -0.119443 11.1772 -0.11444 11.6475 0.350826Z' fill='%233BA739'/%3E%3Cpath fill-rule='evenodd' clip-rule='evenodd' d='M11.6475 0.350826C12.1177 0.816093 12.1177 1.57653 11.6475 2.0468L5.24881 8.45046C4.78354 8.92073 4.0231 8.92073 3.55283 8.45046L0.351001 5.24863C-0.119268 4.78336 -0.114265 4.02293 0.351001 3.55266C0.816267 3.08239 1.5767 3.08239 2.04697 3.55266L4.39832 5.90401L9.9515 0.350826C10.4168 -0.119443 11.1772 -0.11444 11.6475 0.350826Z' fill='%2380C954'/%3E%3C/svg%3E%0A");
	background-repeat: no-repeat;
	background-size: cover;
	position: absolute;
	right: 12px;
	top: 50%;
	transform: translate(-50%, -50%);
}

.btn-x2 {
	background-image: url("data:image/svg+xml,%3Csvg width='9' height='9' viewBox='0 0 9 9' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath fill-rule='evenodd' clip-rule='evenodd' d='M0.360716 2.09522C-0.120239 1.61939 -0.120239 0.841672 0.360716 0.360716C0.836555 -0.120239 1.61427 -0.120239 2.09522 0.360716L4.5 2.76549L6.90478 0.360716C7.38062 -0.120239 8.15833 -0.115122 8.63929 0.360716C9.12024 0.836555 9.12024 1.61427 8.63929 2.09522L6.23451 4.5L8.63929 6.90478C9.12024 7.38062 9.11512 8.15833 8.63929 8.63929C8.16345 9.12024 7.38573 9.12024 6.90478 8.63929L4.5 6.23451L2.09522 8.63929C1.61939 9.12024 0.841672 9.11512 0.360716 8.63929C-0.120239 8.16345 -0.120239 7.38573 0.360716 6.90478L2.76549 4.5L0.360716 2.09522Z' fill='%23E4674B'/%3E%3C/svg%3E%0A");
	width: 9px;
	height: 9px;
	background-repeat: no-repeat;
	background-size: cover;
	position: absolute;
	right: 12px;
	top: 50%;
	transform: translate(-50%, -50%);
}

.error-txt {
	font-size: 12px;
	color: red;
	margin-left: 8px;
}

/***** modal *****/
.findpwd-modal-box {
	width: 253px;
	height: 113px;
	background: #FFFFFF;
	border-radius: 10px;
	color: #000000;
	font-weight: 400;
	font-size: 12px;
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;
	position: relative;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

.modal-txt {
	font-size: 12px;
	text-align: center;
}

.modal-btn {
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
	cursor: pointer;
	margin-top: 18px;
	transition: 0.2s;
}

.modal-btn:hover {
	background-color: #d5d5d566;
}

.btn-save:hover {
	background: #7299BE;
}
</style>