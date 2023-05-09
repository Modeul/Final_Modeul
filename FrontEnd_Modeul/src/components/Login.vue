<template>
    <div class="canvas f-family">

        <header>
            <div class="header-container">
                <h1 class="logo-main logo-moduel logo-size1 m-t-138px">mo_deul</h1>
            </div>
        </header>

        <main>
            <div class="input-container">

                <form>

                    <div class="input-field-1">
                        <label for="uid" class="uid-label login-label">
                            <span class="d-none">uid</span>
                        </label>
                        <input type="text" class="input-text" placeholder="아이디를 입력해주세요" v-model="uid" autofocus>
                    </div>
                    <div class="input-field-1 m-t-1">
                        <label for="password" class="password-label login-label">
                            <span class="d-none">uid</span>
                        </label>
                        <input type="password" class="input-text" placeholder="비밀번호를 입력해주세요" v-model="pwd">
                    </div>
                    <div>
                        <input @click.prevent="login()" class="btn-2" type="submit" value="로그인">
                    </div>
                </form>
                <div style="color: red; font-size: 12px; margin-top: 4px; height : 20px;">{{this.errormsg}}</div>
            </div>
            <!-- 아이디찾기 ~ 플렉스 -->
            <div class="find-container">
                <div class="find-box">
                    <router-link to="/login/findid">
                        <span class="find-text">아이디 찾기</span>
                    </router-link>
                    <span class="find-text2">|</span>
                    <router-link to="/login/findpwd">
                        <span class="find-text">비밀번호 찾기</span>
                    </router-link>
                </div>

                <div class="signup-box">
                    <span class="signup-text2">Modeul이 처음이세요?</span>
                    <router-link to="signup" class="signup-text">
                        <h2 class="fromCenter">회원가입</h2>
                    </router-link>
                </div>
            </div>

        </main>

    </div>
</template>

<script>
export default {
    data() {
        return {
            uid: "",
            pwd: "",
            errormsg: "",
            loginMember:{},
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

            await fetch(`${this.$store.state.host}/api/member/login`, requestOptions)
                .then(response => response.json())
                .then(result => {
                    console.log(result);
                    if (result.result == null)
                        this.errormsg = "아이디 또는 비밀번호를 확인하세요";
                    else{
                        this.loginMember = result.result;
                        this.$router.push('/member/stuff/list');
                    }
                })
                .catch(error => console.log('error', error));
        }
    },
}
</script>


<style scoped>
@import "/css/component/component.css";
@import "/css/component/component-login.css";
@import "/css/button.css"
</style>