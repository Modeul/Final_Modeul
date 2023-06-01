<template>
	<div class="canvas f-family">

		<header>
			<div class="header-container">
				<h1 class="logo-main logo-moduel logo-size1 m-t-80px">mo_deul</h1>
			</div>
		</header>

		<main>
			<div class="input-container">

				<form>

					<div class="input-field-1">
						<label for="uid" class="uid-label login-label">
							<span class="d-none">uid</span>
						</label>
						<input type="text" class="input-text" placeholder="아이디를 입력해주세요." v-model="uid" autofocus>
					</div>
					<div class="input-field-1 m-t-1">
						<label for="password" class="password-label login-label">
							<span class="d-none">uid</span>
						</label>
						<input type="password" class="input-text" placeholder="비밀번호를 입력해주세요." v-model="pwd">
					</div>
					<div class="find-box">
						<div class="find-box-2">
							<router-link to="/login/findid">
								<span class="find-text">아이디 찾기</span>
							</router-link>
							<span class="find-text2">|</span>
							<router-link to="/login/findpwd">
								<span class="find-text">비밀번호 찾기</span>
							</router-link>
						</div>
					</div>
					<div class="error">{{ this.errormsg }}</div>
					<div>
						<input @click.prevent="login()" class="btn-2" type="submit" value="로그인">
					</div>
					<div>
						<router-link to="signup" class="signup-text">
							<input class="btn-signup" type="submit" value="회원가입">
						</router-link>
					</div>
				</form>
			</div>
			<!-- 아이디찾기 ~ 플렉스 -->
			<div class="find-container">
				<div class="d-fl">
					<hr>
					<span class="text">&nbsp;또는&nbsp;</span>
					<hr>
				</div>
				<GoogleLogin :callback="customLoginHandler" popup-type="TOKEN">
					<div class="google-login">
						<div class="google-icon"></div>
						<div class="text">Google 계정 로그인</div>
					</div>
				</GoogleLogin>
			</div>

		</main>

	</div>
</template>

<script>
import { useDefaultStore } from '../stores/useDefaultStore';
import { useUserDetailsStore } from '../stores/useUserDetailsStore';

export default {
	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			uid: "",
			pwd: "",
			errormsg: "",
			loginMember: {},
			userDetails: useUserDetailsStore(),
			credential: ""
		}
	},
	methods: {
		async login() {
			this.errormsg = "";

			var myHeaders = new Headers();
			myHeaders.append("Content-Type", "application/json");

			var raw = JSON.stringify({
				"uid": this.uid,
				"pwd": this.pwd
			});

			var requestOptions = {
				method: 'POST',
				headers: myHeaders,
				body: raw,
				redirect: 'follow'
			};

			await fetch(`${this.defaultStore.host}/api/member/login`, requestOptions)
				.then(response => response.json())
				.then(result => {
					console.log(result);
					if (result.loginMember == null)
						this.errormsg = "아이디 또는 비밀번호를 확인하세요";
					else {
						this.userDetails.id = result.loginMember.id;
						this.userDetails.uid = result.loginMember.uid;
						let level = result.loginMember.level;
						if (level == 1)
							this.userDetails.roles = ["MEMBER"]
						else if (level == 2)
							this.userDetails.roles = ["ADMIN", "MEMBER"]
						this.$router.push('/member/stuff/list');
					}
				})
				.catch(error => console.log('error', error));
		},

		async customLoginHandler(response) {
			await fetch(`https://www.googleapis.com/oauth2/v3/userinfo?access_token=${response.access_token}`)
				.then(res => res.json())
				.then(credential => {
					this.userDetails.email = credential.email
				})
				.catch(e => {
					console.log("error");
				});
			this.checkEmail();
		},
		async checkEmail() {
			await fetch(`${this.defaultStore.host}/api/signup/checkEmail?email=${this.userDetails.email}`)
				.then(response => response.json())
				.then(result => {
					if (result)// 이메일 없으면 true
						this.$router.push("/googlesignup");
					else if (!result) {
						this.getMemberByEmail();
					}
				})
		},
		async getMemberByEmail() {
			var myHeaders = new Headers();
			myHeaders.append("Content-Type", "application/json");

			var raw = JSON.stringify({
				"email": this.userDetails.email
			});

			var requestOptions = {
				method: 'POST',
				headers: myHeaders,
				body: raw,
				redirect: 'follow'
			};
			// credential의 이메일로 db에서 member객체 가져오기
			await fetch(`${this.defaultStore.host}/api/member/googleLogin`, requestOptions)
				.then(res => res.json())
				.then(result => {
					this.userDetails.id = result.loginMember.id;
					this.userDetails.uid = result.loginMember.uid;
					let level = result.loginMember.level;
					if (level == 1)
						this.userDetails.roles = ["MEMBER"]
					else if (level == 2)
						this.userDetails.roles = ["ADMIN", "MEMBER"]
				})
			this.$router.push('/member/stuff/list');
		}
	},
}
</script>

<style scoped>
@import "/css/component/component.css";
@import "/css/component/component-login.css";
@import "/css/button.css"
</style>