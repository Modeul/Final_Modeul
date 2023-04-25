<template>
    <div class="wrap">
        <header class="header">
            <router-link to="/member/mypage" class="back"></router-link>
            <div class="changpwd-title">비밀번호 변경</div>
        </header>
        <section class="input-sec">

            <div class="input-field">
                <div class="email-icon"></div>
                <span class="txt">{{ loginInfo.email }}</span>
                <input
                @click="sendTempPwd"
                class="btn-post"
                id="btn-post"
                type="button"
                value="전송"
                />
            </div>

            <div class="input-field">
                <div class="emailconfirm-icon"></div>
            <label for="email" class="email-confirm-label">
                <span class="d-none">email-confirm</span>
                <input
                type="text"
                id="email"
                name="reemail"
                class="input-text-2"
                placeholder="인증번호를 입력해주세요."
                v-model="emailconfirm"
                />
                <input
                class="btn-auth"
                id="btn-auth"
                type="button"
                value="확인"
                @click.prevent="checkTempPwd"
                />
            </label>
            </div>
            
            <div class="btn-save">저장하기</div>
            <div>
                <span>{{this.errorMsg}}</span><br>
                <span>{{this.tempPwd}}</span>
            </div>
        </section>
        <v-dialog v-model="dialog" width="320px" height="400px" padding="0">
            <v-card>
                <v-card-text>
                    <!-- <v-text-field size="small" label="인증번호 입력" v-model="emailconfirm"></v-text-field> -->
                    <v-text-field label="새 비밀번호" v-model="newpwd" type="password" @change="isValidPwd"></v-text-field>
                    <v-text-field label="비밀번호 확인" v-model="checkpwd" type="password"></v-text-field>
                    <span>{{this.pwdError}}</span>
                </v-card-text>
                <v-card-actions>
                    <v-btn min-width="unset" max-width="unset" color="#63A0C2" block @click="dialog = false">취소</v-btn>
                    <v-btn min-width="unset" max-width="unset" color="#222" block @click="checkRealPwd">변경</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>
<script>
export default{
    data(){
        return{
            myMemberId: '103',
            loginInfo: '',
            dialog: false,
            name:"",
            newpwd:"",
            checkpwd:"",
            tempPwd:"",
            checkTemp:"",
            checkReal:"",
            emailconfirm:"",
            errorMsg:"",
            pwdError:"",
        };
    },
    methods:{
        sendTempPwd(){
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            redirect: 'follow'
            };

            fetch(`${this.$store.state.host}/api/member/sendTempPwd?email=${this.loginInfo.email}`, requestOptions)
            .then(response => response.text())
            .then((result) => {
                this.tempPwd = result;
                console.log("tempPwd : "+result);
            })
            .catch(error => console.log('error', error));
        },
        checkTempPwd(){
            if(this.emailconfirm == this.tempPwd){
                this.checkTemp = true;
                this.errorMsg = "";
                console.log("임시비밀번호 일치");
                this.dialog=true;
            }
            else{
                this.checkTemp = false;
                this.errorMsg = "임시 비밀번호가 잘못되었습니다";
                console.log("임시비밀번호 틀림");
            }
        },
        checkRealPwd(){
            if(this.newpwd == this.checkpwd){
                this.checkReal = true;
                this.pwdError = "";
                this.dialog=false;
            }
            else{
                this.checkReal = false;
                this.pwdError = "비밀번호가 일치하지 않습니다.";
            }
        },
        isValidPwd() {
            this.pwdError = "";
            // 비밀번호는 8자 이상이어야 합니다.
            if (this.newpwd.length < 8) {
                this.pwdError = "올바른 비밀번호를 입력해주세요.(8자 이상 영어+숫자)";
                return false;
            }

            // 비밀번호는 영문자와 숫자를 반드시 포함해야 합니다.
            const hasLetter = /[a-zA-Z]/.test(this.newpwd.pwd);
            const hasNumber = /\d/.test(this.newpwd.pwd);
            if (!hasLetter || !hasNumber) {
                this.pwdError = "비밀번호는 영문자와 숫자를 반드시 포함해야 합니다.";
                return false;
            }

            // 모든 검증을 통과한 경우 유효한 비밀번호입니다.
            this.pwdError = "";
            return true;
        }
    },
    mounted(){
        fetch(`${this.$store.state.host}/api/member/${this.myMemberId}`)
                .then(response => response.json())
                .then(data => {
                    this.loginInfo = data;
                    console.log(data);
                })
    }
}

</script>
<style scoped>
@import url(/css/component/component.css);
@import url(/css/button.css);
.wrap{
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 0;
        
        position: relative;
        width: 360px;
        
        background: #FFFFFF;
}
.header{
        display: flex;
        flex-direction: row;
        align-items: center;
        padding: 0px;
        gap: 10px;
        
        width: 312px;
        margin-top: 25px;
}
.back{
    background-image: url("data:image/svg+xml,%3Csvg width='16' height='16' viewBox='0 0 16 16' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M16 7H3.83L9.42 1.41L8 0L0 8L8 16L9.41 14.59L3.83 9H16V7Z' fill='black'/%3E%3C/svg%3E%0A");
    width: 23.04px;
    height: 24px;
    margin-top: 9px;
}
.changpwd-title{
    margin-left: 86px;
    /* font-size: 14px; */
    color:#333;
}
.input-sec{
    margin-top: 120px;
}
.btn-save{
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
.input-sec .input-field{
    display: flex;
    position: relative;
    align-items: center;
    width: 300px;
    height: 45px;
    border: 1px solid #D5D5D5;
    border-radius: 10px;
}

.input-sec .btn-post{
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
.input-field .email-icon{
    width: 25px;
    height: 25px;
    left: 10px;
    top: 7px;
    margin-left: 12px;
    display: inline-block;
    background-size: contain;
    background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_666' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_666' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAf9JREFUaEPtme01xEAUht+tgA7QgQ7ogA5QAR2gAlSADuiADnSwVIAKOI8z15ldid2bmUw2e+b+yTnJzJ37fsxHkolGHpOR168KYGgFqwJVgUQG1tZC55LOJG0mEpSr+4eka0mX8wmbFLiQBIBVDABQ3280AXhfIebnSXyVtLMIwFfU4EXSiSSuQ8SupFtJXC1mSG9SIAZgnZDtRhJeLBXYeMYuYWAXgE9JG6Ej8qHGU88I9iVdRazHNTC0CwB+u5O0FxVtq0FuNVjxbPWz4Z4lHUuadrWQoWVJRc6+1IB1vL4dCoV1xoMsIra1S4G4MclJeBCx8RBs1VUNWMcusGzxGPYgLGuRBYAlOwy2MjUonrkBGE+QB9Zts4R1gDTlyQqAIhmUudFFDfpSOABi1im+TcnsAP5Tg/ly3yLFUbDhMqzHKXoD0KYGSy22Mh8zf2Cdybos68UAxGowybfCDexgGxFXY/0teN2zn/SqQMwURVLsaYuF2M157l21igGwurEKk9zU6MJ6cQvNk25qcL8L64MD8OwJi9oWt9CigrzPKwAvY7nbVwVyM+rNVxXwMpa7fVUgN6PefFUBL2O527sU4Khr77u5C0nNx6nWvlz85FrLj7t2/OUl287zqcyl9od53i/+fGpc2x8cqYwV618VKEZ1y0BVgapAIgOjt9A34VGAMWTd1zgAAAAASUVORK5CYII='/%3E%3C/defs%3E%3C/svg%3E%0A");
}
.input-field .emailconfirm-icon{
    width: 25px;
    height: 25px;
    left: 10px;
    top: 7px;
    margin-left: 12px;
    display: inline-block;
    background-size: contain;
    background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_670' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_670' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAmlJREFUaEPtmI1NwzAQhV8ngA2ADdgANgAmACagTABMAEwATABMQNmADYAN6ASgr7Kra3BqJ05Kimypipr65969e3fnjrTmY7Tm9qsA+GsGCwOFgUwP/NsQupB0Imk700FdLf+QdC/pqrphiIFLSQAY4gAA9s1HCMD7gDxfdSJM7MQAfJsJT5LOJbHwLwYhfC3psM7pIQYsANZ9OdpuV4zgzJ27WTl3weYUAH79m6RTSTz7HLuS7iTxDI1GAI6c+jfMTjcuG8BMlwNPkzzGZtOpy4aPbUMItGyM8qHUDzSBNtBIF4MYJ9Zt2iZkORdH2bBuxICdvO/Y2DIW54o8JNJP5/WJOacTAH6/ap1oK/KQSH/leXdopwDYE4GhhT3jJTxGWMVEHhLpq4v9urWdA/B2IzgYsSLnO/FbFTla8l736xEp83HGstEbAA4ljjHgoCJyUq6PY/RDarQifXZeTymSvQLwdpNJaLgsGz5L2UrqU2OTDGb7n2gvVIs2IV8SJrBxXDPXpsaE7eJTYpW47X2hmnJDqTFuXcKMvgBwNGz4qgorbSs3+6Av9INuFjJVnwAS/BedApO0EbahQzu0OLMxZAAYzd2E8CMJkK1glJZjXvCGDIArLamXC4xNtWQ6CujsYjNkAL5lqdq48L4AiMqw3QTEy92A50Lhcu94jx4ehsgAxr0k4p4MEUCjv3UKgESqm0wrDOR0o008XTc3mwGqnr24d2FUbA97F84G0GiDmGWJv7cFMK3r9ykSfFbFhAVAE0fHaf8wCPmBm9247YUl0bH9TysA+vfx8hMKA4WBTA+UEMp0YPbyH51li0r80MF1AAAAAElFTkSuQmCC'/%3E%3C/defs%3E%3C/svg%3E%0A");
}
.txt{
    margin-left: 12px;
    line-height: 45px;
    font-size: 12px;
}
.txt{
    padding-left: 4px;
}
</style>