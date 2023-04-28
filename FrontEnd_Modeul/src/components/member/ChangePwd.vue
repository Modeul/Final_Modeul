<template>
    <div class="wrap">
        <header class="header">
            <router-link to="/member/mypage" class="back"></router-link>
            <div class="changpwd-title">비밀번호 변경</div>
        </header>
        <section>
            <!-- 현재 비밀번호 -->
            <div @change="isValidPwd"
                class="input-field-2 m-t-1">
            <label for="password" class="password-label">
                <span class="d-none">pw</span>
                <input
                type="password"
                id="pwd"
                name="pwd"
                class="input-text-2"
                placeholder="현재 비밀번호를 입력해주세요."
                v-model="pwd"
                @change="checkPwd"
                />
            </label>
            <div v-if="this.pwdbtn==''" class="btn-null"></div>
            <div v-if="this.pwdbtn==true" class="btn-check"></div>
            <div v-if="this.pwdbtn==false" class="btn-x"></div>        
            </div>
            <div>{{ this.pwdError }}</div>
            <!-- 새로운 비밀번호 -->
            <div @change="isValidPwd"
                class="input-field-2 m-t-1">
            <label for="password" class="password-label">
                <span class="d-none">pw</span>
                <input
                type="password"
                id="pwd"
                name="pwd"
                class="input-text-2"
                placeholder="새로운 비밀번호를 입력해주세요."
                v-model="newPwd"
                />
            </label>
            <div v-if="pwdbtn2==''" class="btn-null"></div>
            <div v-if="pwdbtn2==true" class="btn-check"></div>
            <div v-if="pwdbtn2==false" class="btn-x"></div>        
            </div>
            <div>{{ this.newPwdError }}</div>
            <!-- 새로운 비밀번호 검증 -->
            <div @change="isValidPwdConfirm" 
                    class="input-field-2 m-t-1">
                <label for="password-confirm" class="password-confirm-label">
                <span class="d-none">pw-confirm</span>
                <input
                    type="password"
                    id="password-confirm"
                    name="repassword"
                    class="input-text-2"
                    placeholder="비밀번호를 다시 입력해주세요."
                    v-model="newPwdConfirm"
                />
                </label>
                <div v-if="pwdbtn3==''" class="btn-null"></div>
                <div v-if="pwdbtn3==true" class="btn-check"></div>
                <div v-if="pwdbtn3==false" class="btn-x"></div>   
            </div>
            <div>{{ this.newPwdConfirmError }}</div>
        </section>
    </div>
</template>

<script>
export default {
    data() {
        return {
            myMemberId: '120',
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
        }
    },
    methods: {
        async checkPwd() {
            this.pwdbtn = null;

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

            await fetch(`${this.$store.state.host}/api/member/checkpwd`, requestOptions)
                .then(response => response.json())
                .then((result) => {
                    console.log(result);
                    if (result)
                        this.pwdbtn = true;
                    else {
                        this.pwdbtn = false;
                        this.pwdError = "비밀번호를 확인하세요."
                    }
                    })
                .catch(error => console.log('error', error));
        },
        // pwd 검사
        isValidPwd() {
            this.pwdbtn2 = null;
            // 비밀번호는 8자 이상이어야 합니다.
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
            if (this.newPwd !== this.newPwdConfirm) {
                this.pwdbtn3 = false;
                this.newPwdConfirmError = "비밀번호가 일치하지 않습니다.";
                return false
            }
            this.pwdbtn3 = true;
            this.newPwdConfirmError = "";
            return true
        },

    },
    mounted(){
        fetch(`${this.$store.state.host}/api/member/${this.myMemberId}`)
                .then(response => response.json())
                .then(data => {
                    this.loginInfo = data;
                })
    }
}

</script>

<!-- <script setup>
import {computed, onMounted, reactive, ref} from 'vue';
import { useStore } from 'vuex'

// === Variaables =============================
let model = reactive({
    member:'',
    input:''
})
const store = useStore();   
let myMemberId = '120';
let pwdbtn = null;


// === Life Cycles =============================
onMounted(async() => {
    let response = await fetch(`${store.state.host}/api/member/${myMemberId}`)
    let member = await response.json();
    model.member = member;
    console.log(model.member);
});


// === methods =============================
async function checkPwd(){
    console.log(model.member.uid);
    console.log(model.input.pwd);
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({
        "uid": model.member.uid,
        "pwd": model.input.pwd
    });

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };
    let result = await fetch(`${store.state.host}/api/member/checkpwd`, requestOptions)
        pwdbtn = await result.json();
        console.log(result);
}


</script> -->
<style scoped>
    @import url(/css/component/component.css);
    @import url(/css/component/component-sign-up.css);
    @import url(/css/button.css);
</style>