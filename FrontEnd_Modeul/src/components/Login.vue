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
					<!-- <div> 또는</div>
					<GoogleLogin :callback="googleLoginHandler" /> -->
				</form>
			</div>
			<!-- 아이디찾기 ~ 플렉스 -->
			<div class="find-container">
				<div class="d-fl">
					<hr>
					<span class="text">&nbsp;또는&nbsp;</span>
					<hr>
				</div>
				<div class="google-login">
					<div class="google-icon"></div>
					<div class="text">Google 계정 로그인</div>
				</div>
			</div>

		</main>

	</div>
</template>

<script>
import { useUserDetailsStore } from '../stores/useUserDetailsStore';
import { useDefaultStore } from '../stores/useDefaultStore';
import { decodeCredential } from 'vue3-google-login';

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

		googleLoginHandler(response) {
			let userData = decodeCredential(response.credential);
			console.log(userData);

			// userDetails.username = userData.userName;
			// userDetails.email = userData.email;
			// userDetails.roles = userData.["ADMIN", "MEMBER"];

			// let returnURL = route.query.returnURL;

			// if (returnURL) {
			// 	router.push(returnURL);
			// } else {
			// 	router.push("/index");
			// }
		}
	},
}
</script>


<style scoped>
@import "/css/component/component.css";
@import "/css/component/component-login.css";
@import "/css/button.css"
</style>