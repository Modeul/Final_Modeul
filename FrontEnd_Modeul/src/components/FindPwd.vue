<template>
    <div class="wrap">
        <!-- header -->
        <header class="header">
            <router-link to="/login" class="back"></router-link>
            <div class="changpwd-title">비밀번호 찾기</div>
        </header>
        <!-- input section -->
        <section class="input-sec">
            <!-- id -->
            <div class="input-box">
                <div class="input-field">
                    <div class="id-icon"></div>
                    <input class="txt" type="text" autofocus>
                    <div class="btn-check"></div>
                </div>
                <div class="error-txt">kjhgfd</div>
            </div>
            <!-- email -->
            <div class="input-box">
                <div class="input-field">
                    <div class="email-icon"></div>
                    <input class="txt" type="text">
                    <!-- <div class="btn-x"></div> -->
                    <button class="btn-change">전송</button>
                </div>
                <div class="error-txt">kjhgfd</div>
            </div>
            
            <!-- email check -->
            <div class="input-box">
                <div class="input-field">
                    <div class="email-check-icon"></div>
                    <input class="txt" type="text">
                    <button class="btn-change" @click.prevent="modalHandler">확인</button>
                </div>
                <div class="error-txt">kjhgfd</div> 
            </div>
            
        </section>

        <div v-if="openModal" class="black-bg">
            <div class="findpwd-modal-box">
                <div class="modal-txt">입력하신 이메일 주소로<br>
                임시 비밀번호가 발송되었습니다.</div>
                <button @click.prevent="modalHandler" class="modal-btn">확인</button>
                
            </div>
	    </div>
    </div>
</template>
<script>
export default{
    data(){
        return{
            myMemberId: '103',
            loginInfo: '',
            openModal: false,
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
        modalHandler() {
			this.openModal = !this.openModal;
		},
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
                // this.dialog=true;
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
                // this.dialog=false;
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
        margin:0 auto;
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
.input-box{
    margin-bottom: 10px;
    height: 63px;
}
.input-sec .input-field{
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
.input-sec .input-field:last-child{
    margin-bottom: 0;
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
.id-icon{
    width: 25px;
    height: 25px;
    background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_649' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_649' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAj5JREFUaEPtmIExRDEQhv+rgA7oABWgAlSAClABOqACVIAKUAE6oAMqYD6TJZdzL3nvJY+7SWbe3LxLsvn/3X83ezfSjI/RjONXJfDXEawRqBHo6YG5ltBHT+c0bX+QtJHDflMEKoEGDw8SgRwRLm5jrpO4uPdyHFAjkMOLfWw0ReCuj2Fv77Okw8DWrqRtSavu+ydJN5Ku2p45xD2wJgmAjEVJ1w2X2L2kHUlvqURKE7h1njY8RDV2A0NiMweB2EEpZ7xI4mHsSbrwNh052djcsTe3L+ky5YAhqxCA0D7jVNJJAJB3I0EuQDg6hiTw6CWtnxcGkoRmDYOcYU10DEkAba87RGicd38gWat8yb3SkARiEjqTdOAYZZGQn1TRUAYLAGDJa1PUfUqoDQBT+xnM+XcFpdTmGs8uVUZ/kwhAALUV8UZYev8VAS4ypDSNBOCpPlkusrDMtZERIEMJ+fuRDEnrtxIkdZJsfENDJnEbBySvrQSSXfWzkDxYca/WrtidQOearH9sDBUBgNJG8LkcIc0tDKHzSB59mSlNANAUgxjoaZwgQ9MX3trf60sRoLrQeVqVCQGaVAwYkfGlFa5nHUTsd0VRAn5PYwe9uxJJmQTMNJ1Dgv2UWetczQZ7uCDHSJSIgN91ApyWgadVcrqI0F7wLDgWEz92ShCwvyRfnYTaAg/lQ1Tw+pKbGMNckkCHCpu0pRKIuankv9oTpb+EhGIEs85XAlnd2cFYjUAHp2XdUiOQ1Z0djM18BD4B8lljMc3J4QIAAAAASUVORK5CYII='/%3E%3C/defs%3E%3C/svg%3E%0A");
}
.email-icon{
    width: 25px;
    height: 25px;
    background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_666' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_666' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAf9JREFUaEPtme01xEAUht+tgA7QgQ7ogA5QAR2gAlSADuiADnSwVIAKOI8z15ldid2bmUw2e+b+yTnJzJ37fsxHkolGHpOR168KYGgFqwJVgUQG1tZC55LOJG0mEpSr+4eka0mX8wmbFLiQBIBVDABQ3280AXhfIebnSXyVtLMIwFfU4EXSiSSuQ8SupFtJXC1mSG9SIAZgnZDtRhJeLBXYeMYuYWAXgE9JG6Ej8qHGU88I9iVdRazHNTC0CwB+u5O0FxVtq0FuNVjxbPWz4Z4lHUuadrWQoWVJRc6+1IB1vL4dCoV1xoMsIra1S4G4MclJeBCx8RBs1VUNWMcusGzxGPYgLGuRBYAlOwy2MjUonrkBGE+QB9Zts4R1gDTlyQqAIhmUudFFDfpSOABi1im+TcnsAP5Tg/ly3yLFUbDhMqzHKXoD0KYGSy22Mh8zf2Cdybos68UAxGowybfCDexgGxFXY/0teN2zn/SqQMwURVLsaYuF2M157l21igGwurEKk9zU6MJ6cQvNk25qcL8L64MD8OwJi9oWt9CigrzPKwAvY7nbVwVyM+rNVxXwMpa7fVUgN6PefFUBL2O527sU4Khr77u5C0nNx6nWvlz85FrLj7t2/OUl287zqcyl9od53i/+fGpc2x8cqYwV618VKEZ1y0BVgapAIgOjt9A34VGAMWTd1zgAAAAASUVORK5CYII='/%3E%3C/defs%3E%3C/svg%3E%0A");
}
.email-check-icon{
    width: 25px;
    height: 25px;
    background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_670' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_670' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAmlJREFUaEPtmI1NwzAQhV8ngA2ADdgANgAmACagTABMAEwATABMQNmADYAN6ASgr7Kra3BqJ05Kimypipr65969e3fnjrTmY7Tm9qsA+GsGCwOFgUwP/NsQupB0Imk700FdLf+QdC/pqrphiIFLSQAY4gAA9s1HCMD7gDxfdSJM7MQAfJsJT5LOJbHwLwYhfC3psM7pIQYsANZ9OdpuV4zgzJ27WTl3weYUAH79m6RTSTz7HLuS7iTxDI1GAI6c+jfMTjcuG8BMlwNPkzzGZtOpy4aPbUMItGyM8qHUDzSBNtBIF4MYJ9Zt2iZkORdH2bBuxICdvO/Y2DIW54o8JNJP5/WJOacTAH6/ap1oK/KQSH/leXdopwDYE4GhhT3jJTxGWMVEHhLpq4v9urWdA/B2IzgYsSLnO/FbFTla8l736xEp83HGstEbAA4ljjHgoCJyUq6PY/RDarQifXZeTymSvQLwdpNJaLgsGz5L2UrqU2OTDGb7n2gvVIs2IV8SJrBxXDPXpsaE7eJTYpW47X2hmnJDqTFuXcKMvgBwNGz4qgorbSs3+6Av9INuFjJVnwAS/BedApO0EbahQzu0OLMxZAAYzd2E8CMJkK1glJZjXvCGDIArLamXC4xNtWQ6CujsYjNkAL5lqdq48L4AiMqw3QTEy92A50Lhcu94jx4ehsgAxr0k4p4MEUCjv3UKgESqm0wrDOR0o008XTc3mwGqnr24d2FUbA97F84G0GiDmGWJv7cFMK3r9ykSfFbFhAVAE0fHaf8wCPmBm9247YUl0bH9TysA+vfx8hMKA4WBTA+UEMp0YPbyH51li0r80MF1AAAAAElFTkSuQmCC'/%3E%3C/defs%3E%3C/svg%3E%0A");
}
.txt{
    width: 70%;
    line-height: 45px;
    font-size: 12px;
    margin-left: 10px;
}
.btn-check2{
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
.btn-x2{
    background-image: url("data:image/svg+xml,%3Csvg width='9' height='9' viewBox='0 0 9 9' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath fill-rule='evenodd' clip-rule='evenodd' d='M0.360716 2.09522C-0.120239 1.61939 -0.120239 0.841672 0.360716 0.360716C0.836555 -0.120239 1.61427 -0.120239 2.09522 0.360716L4.5 2.76549L6.90478 0.360716C7.38062 -0.120239 8.15833 -0.115122 8.63929 0.360716C9.12024 0.836555 9.12024 1.61427 8.63929 2.09522L6.23451 4.5L8.63929 6.90478C9.12024 7.38062 9.11512 8.15833 8.63929 8.63929C8.16345 9.12024 7.38573 9.12024 6.90478 8.63929L4.5 6.23451L2.09522 8.63929C1.61939 9.12024 0.841672 9.11512 0.360716 8.63929C-0.120239 8.16345 -0.120239 7.38573 0.360716 6.90478L2.76549 4.5L0.360716 2.09522Z' fill='%23E4674B'/%3E%3C/svg%3E%0A");
    width: 9px;
    height:9px;
    background-repeat: no-repeat;
    background-size: cover;
    position: absolute;
    right: 12px;
    top: 50%;
    transform: translate(-50%, -50%);
}
.error-txt{
    font-size: 12px;
    color: red;
    margin-left: 8px;
}

/***** modal *****/ 
.findpwd-modal-box{
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
    transform: translate(-50%,-50%);
}
.modal-txt{
    font-size: 12px;
    text-align: center;
}
.modal-btn{
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
.modal-btn:hover{
    background-color: #d5d5d566;
}

</style>