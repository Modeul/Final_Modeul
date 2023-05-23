<template>
	<div class="wrap">
		<header class="d-fl al-cen header">
			<router-link to="/login" class="icon icon-back">Back</router-link>
			<div class="signup-title">
				회원가입
			</div>
		</header>
		<!-- ----------------------------------------------------------- -->
		<main class="m-t-31px">
			<!-- flex 시작 -->
			<div class="sign-up-container">
				<form @submit.prevent="submit" id="signup-vue" action="signup" method="post">
					<div @input="checkUidDupl" class="input-field-2">
						<label for="uid" class="uid-label signup-label">
							<span class="d-none">uid</span>
							<input type="text" id="uid" name="uid" class="input-text-2" placeholder="아이디를 입력해주세요."
								v-model="member.uid" />
							<div v-if="uidbtn == ''" class="btn-null"></div>
							<div v-if="uidbtn == true" class="btn-check"></div>
							<div v-if="uidbtn == false" class="btn-x"></div>
						</label>
						<div class="error-font">{{ this.uidError }}</div>
					</div>
					<div @input="isValidPwd" class="input-field-2">
						<label for="password" class="password-label signup-label">
							<span class="d-none">pw</span>
							<input type="password" id="pwd" name="pwd" class="input-text-2" placeholder="비밀번호를 입력해주세요."
								v-model="member.pwd" />
							<div v-if="pwdbtn == ''" class="btn-null"></div>
							<div v-if="pwdbtn == true" class="btn-check"></div>
							<div v-if="pwdbtn == false" class="btn-x"></div>
						</label>
						<div class="error-font">{{ this.pwdError }}</div>
					</div>
					<div @input="isValidPwdConfirm" class="input-field-2">
						<label for="password-confirm" class="password-confirm-label signup-label">
							<span class="d-none">pw-confirm</span>
							<input type="password" id="password-confirm" name="repassword" class="input-text-2"
								placeholder="비밀번호를 다시 입력해주세요." v-model="member.pwdConfirm" />
							<div v-if="pwdbtn2 == ''" class="btn-null"></div>
							<div v-if="pwdbtn2 == true" class="btn-check"></div>
							<div v-if="pwdbtn2 == false" class="btn-x"></div>
						</label>
						<div class="error-font">{{ this.pwdCheckError }}</div>
					</div>

					<div @input="isValidName" class="input-field-2">
						<label for="name" class="name-label signup-label">
							<span class="d-none">name</span>
							<input type="text" id="name" name="name" class="input-text-2" placeholder="이름을 입력해주세요."
								v-model="member.name" />
							<div v-if="namebtn == ''" class="btn-null"></div>
							<div v-if="namebtn == true" class="btn-check"></div>
							<div v-if="namebtn == false" class="btn-x"></div>
						</label>
						<div class="error-font">{{ this.nameError }}</div>
					</div>

					<div @change="checkNicknameDupl" class="input-field-2">
						<label for="name" class="name-label signup-label">
							<span class="d-none">nickname</span>
							<input type="text" id="nickname" name="nickname" class="input-text-2" placeholder="닉네임을 입력해주세요."
								v-model="member.nickname" />
							<div v-if="nicknamebtn == ''" class="btn-null"></div>
							<div v-if="nicknamebtn == true" class="btn-check"></div>
							<div v-if="nicknamebtn == false" class="btn-x"></div>
						</label>
						<div class="error-font">{{ this.nicknameError }}</div>
					</div>

					<div @click.prevent="postCode" class="input-field-2">
						<label for="address" class="address-label signup-label">
							<span class="d-none">addr</span>
							<input type="text" id="address" name="address" class="input-text-2" v-model="member.address" hidden />
							<div class="input-text-3" v-text="member.address"></div>
						</label>
					</div>
					<div class="input-field-2 m-t-18">
						<label for="addr2" class="address-label signup-label">
							<input type="text" id="addr2" name="addr2" class="input-text-2" v-model="addr2"
								placeholder="상세 주소를 입력해주세요." />
						</label>
					</div>

					<div @input="checkEmailDupl" class="input-field-2">
						<label for="email" class="email-label signup-label">
							<span class="d-none">email</span>
							<input type="text" id="email" name="email" class="input-text-2" placeholder="이메일을 입력해주세요."
								v-model="member.email" />
							<div v-if="emailbtn == ''" class="btn-null"></div>
							<div v-if="emailbtn == true" class="btn-check"></div>
							<div v-if="emailbtn == false" class="btn-x"></div>
						</label>
						<div class="error-font">{{ this.emailError }}</div>
						<div class="error-font">{{ this.emailCheckError }}</div>
					</div>

					<div class="input-field-2" @input="checkEmailConfirm">
						<label for="email" class="email-confirm-label signup-label">
							<span class="d-none">email-confirm</span>
							<input type="text" id="email-confirm" name="reemail" class="input-text-2" placeholder="인증번호를 입력해주세요."
								v-model="emailconfirm" />
							<!-- 이메일이 중복이 아닐 때 전송버튼 표시(emailDupl==true) -->
							<input @click.prevent="checkEmail" v-if="emailDupl && !emailconfirmbtn" class="btn-post" id="btn-post"
								type="button" value="전송" />
							<div v-if="emailConfirmChk" class="btn-check"></div>
							<!-- <div v-if="!emailConfirmChk" class="btn-x"></div>  -->
						</label>
						<div class="error-font">{{ this.emailcodeError }} </div>
					</div>

					<div class="d-fl-jf m-t-28px">
						<input class="btn-4" type="submit" value="가입하기" @click.prevent="[submit()]" />
					</div>
				</form>

				<!-- 에러메시지 모달창 -->
				<div v-if="openModal == true" class="black-bg">
					<div class="error-box">{{ ErrorMsg }}
						<div @click="toggleModal" class="error-close"></div>
					</div>
				</div>

				<div v-if="openModal2" class="black-bg">
					<div class="findpwd-modal-box">
						<div class="modal-txt">가입 완료되었습니다!</div>
						<button @click.prevent="move" class="modal-btn">확인</button>
					</div>
				</div>

			</div>
			<!-- flex 끝 -->
		</main>
	</div>
</template>

<script>
import { useUserDetailsStore } from '../stores/useUserDetailsStore';
import { useDefaultStore } from '../stores/useDefaultStore';



export default {
	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			// 회원가입 정보
			member: {
				uid: "",
				pwd: "",
				pwdConfirm: "",
				name: "",
				nickname: "",
				email: "",
				address: "주소를 입력해주세요",
				coordX: "",
				coordY: "",
			},
			addr2: "",
			// 에러메시지
			addrError: true,
			uidError: "",
			nameError: "",
			nicknameError: "",
			pwdError: "",
			pwdCheckError: "",
			emailError: "",
			emailCheckError: "",
			emailcodeError: "",
			ErrorMsg: "",
			// 이메일 인증 코드
			emailconfirm: "",
			emailcode: "",
			emailConfirmChk: false,
			// 중복체크
			uidDupl: "",
			nicknameDupl: "",
			emailDupl: "",
			// 체크버튼
			uidbtn: null,
			pwdbtn: null,
			pwdbtn2: null,
			namebtn: null,
			nicknamebtn: null,
			emailbtn: null,
			emailconfirmbtn: false,
			// 모달
			openModal: false,
			openModal2: false,
		};
	},
	methods: {
		async submit() {
			this.ErrorMsg = "";

			// email 체크
			if (!this.member.email) {
				this.ErrorMsg = "이메일 주소는 필수 입력사항입니다.";
				this.emailbtn = false;
			} else if (!this.emailDupl) {
				this.ErrorMsg = "중복 된 이메일 주소입니다.";
			} else if (!this.checkEmailDupl()) {
				this.ErrorMsg = "올바른 이메일 주소를 입력해주세요.";
			} else if (!this.emailConfirmChk) {
				this.ErrorMsg = "이메일 인증번호를 확인해주세요.";
			}

			if (this.addrError) {
				this.ErrorMsg = "주소는 필수 입력사항입니다.";
			}
			// nickname 체크
			if (!this.member.nickname) {
				this.ErrorMsg = "닉네임은 필수 입력사항입니다.";
				this.nicknamebtn = false;
			} else if (!this.nicknameDupl) {
				this.ErrorMsg = "중복 된 닉네임입니다.";
			}
			// name 체크
			if (!this.member.name) {
				this.ErrorMsg = "이름은 필수 입력사항입니다.";
				this.namebtn = false;
			}
			// pwd 체크
			if (!this.member.pwd) {
				this.pwdbtn = false;
				this.ErrorMsg = "비밀번호는 필수 입력사항입니다.";
			} else if (this.member.pwd !== this.member.pwdConfirm) {
				this.ErrorMsg = "비밀번호를 확인해주세요.";
			} else if (!this.isValidPwd(this.member.pwd)) {
				this.ErrorMsg = "비밀번호를 확인해주세요. (8자리 이상 영문+숫자)";
			}
			// uid 체크
			if (!this.member.uid) {
				this.ErrorMsg = "아이디는 필수 입력사항입니다.";
				this.uidbtn = false;
			} else if (!this.uidDupl) {
				this.ErrorMsg = "아이디를 확인해주세요.";
			}
			// 만약 ErrorMsg 가 있다면 모달창 띄우기
			if (this.ErrorMsg) {
				this.openModal = true;
				return false;
			}
			// 전체 체크
			if (
				this.uidDupl &&
				!this.pwdError &&
				this.namebtn &&
				this.nicknameDupl &&
				!this.emailError &&
				!this.addrError &&
				this.emailConfirmChk
			) {
				var myHeaders = new Headers();
				myHeaders.append("Content-Type", "application/json");

				this.member.address = this.member.address + ',' + this.addr2;
				var raw = JSON.stringify(this.member);

				var requestOptions = {
					method: "POST",
					headers: myHeaders,
					body: raw,
					redirect: "follow",
				};
				fetch(`${this.defaultStore.host}/api/signup`, requestOptions)
					.then((response) => response.text())
					.then((result) => console.log(result))
					.catch((error) => console.log("error", error));
				this.toggleModal2();
			}
		},
		// pwd 검사
		isValidPwd() {
			// 비밀번호는 8자 이상이어야 합니다.
			if (this.member.pwd.length < 8) {
				this.pwdbtn = false;
				this.pwdError = "영어,숫자 포함 8자 이상 입력해주세요.";
				return false;
			}

			// 비밀번호는 영문자와 숫자를 반드시 포함해야 합니다.
			const hasLetter = /[a-zA-Z]/.test(this.member.pwd);
			const hasNumber = /\d/.test(this.member.pwd);
			if (!hasLetter || !hasNumber) {
				this.pwdbtn = false;

				return false;
			}

			// 모든 검증을 통과한 경우 유효한 비밀번호입니다.
			this.pwdbtn = true;
			this.pwdError = "";
			return true;
		},
		// pwd 일치 검사
		isValidPwdConfirm() {
			if (this.member.pwd !== this.member.pwdConfirm) {
				this.pwdbtn2 = false;
				this.pwdCheckError = "비밀번호가 일치하지 않습니다.";
				return false
			}
			this.pwdbtn2 = true;
			this.pwdCheckError = "";
			return true
		},
		// name 검사
		isValidName() {
			const hasName = /^[ㄱ-ㅎ|가-힣]+$/.test(this.member.name);
			if (!hasName) {
				this.nameError = "이름은 한글로 입력해주세요.";
				this.namebtn = false;
				return false;
			}
			if (this.member.name.length > 10) {
				this.nameError = "이름은 10글자 이하로 입력해주세요.";
				this.namebtn = false;
				return false;
			}
			if (!this.member.name) {
				this.namebtn = false;
				return false;
			}

			this.nameError = "";
			this.namebtn = true;
			return true;
		},
		isValidEmail() {
			const hasEmail = /^[A-Za-z0-9]+$/.test(this.member.email);
			if (!hasEmail) {
				this.emailError = "이메일은 영문만 가능합니다.";
				this.emailbtn = false;
				return false;
			}
		},
		// 이메일 인증번호 발송
		async checkEmail() {
			var myHeaders = new Headers();
			myHeaders.append("Content-Type", "application/json");

			var requestOptions = {
				method: "POST",
				headers: myHeaders,
				redirect: "follow",
			};
			fetch(
				`${this.defaultStore.host}/api/signup/mailConfirm?email=${this.member.email}`,
				requestOptions
			)
				.then((response) => response.text())
				.then((code) => {
					this.emailcode = code;
					console.log("emailcode : " + this.emailcode);
				})
				.catch((error) => console.log("error", error));

			this.emailconfirmbtn = true;
		},
		// 이메일 인증번호 확인
		checkEmailConfirm() {
			if (this.emailconfirm == this.emailcode) {
				this.emailConfirmChk = true;
				this.emailcodeError = "";
				// this.emailcodeError = "인증번호 확인 완료";
			} else {
				this.emailConfirmChk = false;
				this.emailcodeError = "인증번호가 잘못되었습니다.";
			}
		},

		// 아이디 중복검사
		async checkUidDupl() {
			this.uidError = ""; // 에러메시지
			this.uidDupl = ""; // true/false
			this.uidbtn = "";


			const hasLetter = /^[a-zA-Z0-9]*$/g.test(this.member.uid);
			if (!hasLetter) {
				this.uidbtn = false;
				this.uidError = "한글은 입력할 수 없습니다.";
				this.ErrorMsg = "아이디를 확인해주세요.";
				return false;
			}
			if (this.member.uid.length < 6) {
				this.uidbtn = false;
				this.uidError = "6자 이상 입력해주세요.";
				this.ErrorMsg = "아이디를 확인해주세요.";
				return false;
			}
			if (this.member.uid.length > 16) {
				this.uidbtn = false;
				this.uidError = "16자 이하 입력해주세요.";
				this.ErrorMsg = "아이디를 확인해주세요.";
				return false;
			}

			await fetch(
				`${this.defaultStore.host}/api/signup/checkUid?uid=${this.member.uid}`
			)
				.then((response) => response.text())
				.then((result) => {
					if (result == "false")
						this.uidDupl = false;
					else this.uidDupl = true;

					if (!this.uidDupl) {
						this.uidError = "중복된 아이디 입니다.";
						this.uidbtn = false;
					} else {
						this.uidError = "";
						this.uidbtn = true;
					}
				})
				.catch((error) => console.log("error", error));
		},
		// 닉네임 중복 검사
		checkNicknameDupl() {
			this.nicknameDupl = "";
			this.nicknameError = "";
			this.emailCheckError = "";
			fetch(
				`${this.defaultStore.host}/api/signup/checkNickname?nickname=${this.member.nickname}`
			)
				.then((response) => response.text())
				.then((result) => {
					if (result == "false") this.nicknameDupl = false;
					else this.nicknameDupl = true;

					if (!this.nicknameDupl) {
						this.nicknamebtn = false;
						this.nicknameError = "중복된 닉네임 입니다.";
					} else {
						this.nicknamebtn = true;
					}
				})
				.catch((error) => console.log("error", error));
		},
		// 이메일 중복 검사
		async checkEmailDupl() {
			this.isValidEmail();
			this.emailDupl = "";
			this.emailError = "";
			this.emailCheckError = "";
			await fetch(
				`${this.defaultStore.host}/api/signup/checkEmail?email=${this.member.email}`
			)
				.then((response) => response.text())
				.then((result) => {
					if (result == "false") this.emailDupl = false;
					else this.emailDupl = true;
					const emailRegex = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/.test(this.member.email);
					if (!emailRegex) {
						this.emailbtn = false;
						this.emailCheckError = "올바른 이메일 형식으로 입력해주세요.";
					}
					else if (!this.emailDupl) {
						this.emailError = "이미 가입된 이메일 입니다.";
						this.emailbtn = false;
						return false;
					}
					else {
						this.emailError = "";
						this.emailbtn = true;
						this.emailDupl = true;
						return true;
					}
				})
				.catch((error) => console.log("error", error));
		},
		toggleModal() {
			this.openModal = !this.openModal;
		},
		toggleModal2() {
			this.openModal2 = !this.openModal2;
		},
		move() {
			this.$router.push('/login');
		},
		postCode() {
			const geocoder = new daum.maps.services.Geocoder();
			new daum.Postcode({
				oncomplete: (data) => {

					this.member.address = data.address;
					// this.dongCode = data.bcode;
					geocoder.addressSearch(data.address, (results, status) => {

						if (status === daum.maps.services.Status.OK) {

							let result = results[0];
							this.member.coordX = result.x;
							this.member.coordY = result.y;
							this.addrError = false;
							document.querySelector("#addr2").focus();
						}
					});

				}
			}).open();
		}
	},
};
</script>
<style scoped>
@import url(/css/component/component.css);
@import url(/css/component/component-sign-up.css);
@import url(/css/button.css);

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
</style>
