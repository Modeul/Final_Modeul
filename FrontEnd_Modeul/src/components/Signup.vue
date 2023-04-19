<template>
  <div class="canvas f-family">
    <header class="d-fl fl-dir-col al-cen">
      <section>
        <span class="d-none">헤더</span>
        <div>
          <router-link to="/login" class="icon icon-back">Back</router-link>
        </div>
        <div class="d-fl-jf">
          <h1 class="logo-main logo-moduel logo-size2 m-t-44px">mo_deul</h1>
        </div>
      </section>
    </header>
    <!-- ----------------------------------------------------------- -->
    <main class="m-t-31px">
      <!-- flex 시작 -->
      <div class="sign-up-container">
        <form
          @submit.prevent="submit"
          id="signup-vue"
          action="signup"
          method="post"
        >
          <div @change="checkUidDupl"
          class="input-field-2">
            <label for="uid" class="uid-label">
              <span class="d-none">uid</span>
              <input
                type="text"
                id="uid"
                name="uid"
                class="input-text-2"
                placeholder="아이디를 입력해주세요."
                v-model="member.uid"
              />
            </label>
            <div v-if="uidbtn==''" class="btn-null"></div>
            <div v-if="uidbtn==true" class="btn-check"></div>
            <div v-if="uidbtn==false" class="btn-x"></div>
          </div>
          <div @change="isValidPwd"
                class="input-field-2 m-t-1">
            <label for="password" class="password-label">
              <span class="d-none">pw</span>
              <input
                type="password"
                id="pwd"
                name="pwd"
                class="input-text-2"
                placeholder="비밀번호를 입력해주세요."
                v-model="member.pwd"
              />
            </label>
            <div v-if="pwdbtn==''" class="btn-null"></div>
            <div v-if="pwdbtn==true" class="btn-check"></div>
            <div v-if="pwdbtn==false" class="btn-x"></div>        
          </div>
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
                v-model="member.pwdConfirm"
              />
            </label>
            <div v-if="pwdbtn2==''" class="btn-null"></div>
            <div v-if="pwdbtn2==true" class="btn-check"></div>
            <div v-if="pwdbtn2==false" class="btn-x"></div>   
          </div>

          <div @change="isValidName"
              class="input-field-2 m-t-1">
            <label for="name" class="name-label">
              <span class="d-none">name</span>
              <input
                type="text"
                id="name"
                name="name"
                class="input-text-2"
                placeholder="이름을 입력해주세요."
                v-model="member.name"
              />
            </label>
            <div v-if="namebtn==''" class="btn-null"></div>
            <div v-if="namebtn==true" class="btn-check"></div>
            <div v-if="namebtn==false" class="btn-x"></div>   
          </div>

          <div @change="checkNicknameDupl" 
              class="input-field-2 m-t-1">
            <label for="name" class="name-label">
              <span class="d-none">nickname</span>
              <input
                type="text"
                id="nickname"
                name="nickname"
                class="input-text-2"
                placeholder="닉네임을 입력해주세요."
                v-model="member.nickname"
              />
            </label> 
            <div v-if="nicknamebtn==''" class="btn-null"></div>
            <div v-if="nicknamebtn==true" class="btn-check"></div>
            <div v-if="nicknamebtn==false" class="btn-x"></div> 
          </div>

          <div @change="checkEmailDupl" 
              class="input-field-2 m-t-1">
            <label for="email" class="email-label">
              <span class="d-none">email</span>
              <input
                type="text"
                id="email"
                name="email"
                class="input-text-2"
                placeholder="이메일을 입력해주세요."
                v-model="member.email"
              />
            </label>
            <div v-if="emailbtn==''" class="btn-null"></div>
            <div v-if="emailbtn==true" class="btn-check"></div>
            <div v-if="emailbtn==false" class="btn-x"></div> 
          </div>

          <div class="input-field-2 m-t-1">
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
              <!-- 이메일이 중복이 아닐 때 전송버튼 표시(emailDupl==true) -->
              <input
                @click.prevent="checkEmail"
                v-if="emailDupl && !emailconfirmbtn"   
                class="btn-post"
                id="btn-post"
                type="button"
                value="전송"
              />
              <!-- 전송버튼 클릭하면 확인버튼 표시 하기 -->
              <input
                @click.prevent="checkEmailConfirm"
                v-if="emailconfirmbtn && !emailConfirmChk"
                class="btn-auth"
                id="btn-auth"
                type="button"
                value="확인"
              />
            </label>
            <div v-if="emailConfirmChk" class="btn-check"></div>
            <div v-if="emailcodeError" class="error-font">
              {{ emailcodeError }}
            </div>
          </div>

          <div class="d-fl-jf m-t-69px">
            <input class="btn-4" type="submit" value="가입하기" />
          </div>
        </form>

        <!-- 에러메시지 모달창 -->
        <div v-if="openModal == true" class="black-bg">
          <div class="error-box">{{ErrorMsg}}
            <div @click="toggleModal" class="error-close"></div>
          </div>
        </div>

      </div>
      <!-- flex 끝 -->
    </main>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 회원가입 정보
      member: {
        uid: "",
        pwd: "",
        pwdConfirm: "",
        name: "",
        nickname: "",
        email: "",
      },
      // 에러메시지
      uidError: "",
      nameError: "",
      nicknameError: "",
      pwdError: "",
      emailError: "",
      emailcodeError: "",
      ErrorMsg:"",
      // 이메일 인증 코드
      emailconfirm: "",
      emailcode: "",
      emailConfirmChk: false,
      // 중복체크
      uidDupl: "",
      nicknameDupl: "",
      emailDupl: "",
      // 체크버튼
      uidbtn:null,
      pwdbtn:null,
      pwdbtn2:null,
      namebtn:null,
      nicknamebtn:null,
      emailbtn:null,
      emailconfirmbtn:false,
      // 모달
      openModal:false,
    };
  },
  methods: {
    async submit() {
      this.ErrorMsg = "";
      // uid 체크
      if (!this.member.uid) {
        this.ErrorMsg = "아이디는 필수 입력사항입니다.";
        this.uidbtn = false;
      } else if (!this.uidDupl) {
        this.ErrorMsg = "중복 된 아이디입니다.";
      }
      // name 체크
      if (!this.member.name) {
        this.ErrorMsg = "이름은 필수 입력사항입니다.";
        this.namebtn = false;
      }
      // nickname 체크
      if (!this.member.nickname) {
        this.ErrorMsg = "닉네임은 필수 입력사항입니다.";
        this.nicknamebtn = false;
      } else if (!this.nicknameDupl) {
        this.ErrorMsg = "중복 된 닉네임입니다.";
      }
      // email 체크
      if (!this.member.email) {
        this.ErrorMsg = "이메일 주소는 필수 입력사항입니다.";
        this.emailbtn = false;
      } else if (!this.emailDupl) {
        this.ErrorMsg = "중복 된 이메일 주소입니다.";
      } else if (!this.isValidEmail(this.member.email)) {
        this.ErrorMsg = "올바른 이메일 주소를 입력해주세요.";
      } else if (!this.emailConfirmChk){
        this.ErrorMsg = "이메일 인증번호를 확인해주세요.";
      }
      // pwd 체크
      if (!this.member.pwd) {
        this.pwdbtn = false;
        this.ErrorMsg = "비밀번호는 필수 입력사항입니다.";
      } else if (this.member.pwd !== this.member.pwdConfirm) {
        this.ErrorMsg = "비밀번호가 일치하지 않습니다.";
      } else if (!this.isValidPwd(this.member.pwd)) {
        this.ErrorMsg = "비밀번호를 확인해주세요. (8자리 이상 영문+숫자)";
      }
      // 만약 ErrorMsg 가 있다면 모달창 띄우기
      if(this.ErrorMsg){
        this.openModal = true;
        console.log("에러메시지 존재");
        return false;
      }
      // 전체 체크
      if (
        this.uidDupl &&
        !this.pwdError &&
        this.namebtn &&
        this.nicknameDupl &&
        this.emailDupl &&
        !this.emailError &&
        this.emailconfirm
      ) {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        console.log(this.member);

        var raw = JSON.stringify(this.member);

        var requestOptions = {
          method: "POST",
          headers: myHeaders,
          body: raw,
          redirect: "follow",
        };
        fetch(`${this.$store.state.host}/api/signup`, requestOptions)
          .then((response) => response.text())
          .then((result) => console.log(result))
          .catch((error) => console.log("error", error));
        console.log("%가입완료");
        this.$router.push('/login');
      }
    },
    isValidEmail() {
      const emailRegex = /\S+@\S+\.\S+/;
      return emailRegex.test(this.member.email);
    },
    // pwd 검사
    isValidPwd() {
      // 비밀번호는 8자 이상이어야 합니다.
      if (this.member.pwd.length < 8) {
        this.pwdbtn = false;
        this.pwdError = "올바른 비밀번호를 입력해주세요.(8자 이상 영어+숫자)";
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
    isValidPwdConfirm(){
      if (this.member.pwd !== this.member.pwdConfirm) {
        this.pwdbtn2 = false;
        this.pwdError = "비밀번호가 일치하지 않습니다.";
        return false
      }
      this.pwdbtn2 = true;
      this.pwdError = "";
      return true
    },
    // name 검사
    isValidName(){
      if (this.member.name.length > 10) {
        this.nameError = "이름은 10글자 이하로 입력해주세요.";
        this.namebtn = false;
        return false;
      }
      if(!this.member.name){
        this.namebtn = false;
        return false;
      }

      this.nameError = "";
      this.namebtn = true;
      return true;
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
        `${this.$store.state.host}/api/signup/mailConfirm?email=${this.member.email}`,
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
        this.emailcodeError = "인증번호가 잘못되었습니다";
      }
    },

    // 아이디 중복검사
    async checkUidDupl() {
      this.uidError = ""; // 에러메시지
      this.uidDupl = ""; // true/false
      this.uidbtn="";

      await fetch(
        `${this.$store.state.host}/api/signup/checkUid?uid=${this.member.uid}`
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
            this.uidError = "올바른 아이디 입니다.";
            this.uidbtn = true;
          }
        })
        .catch((error) => console.log("error", error));
    },
    // 닉네임 중복 검사
    checkNicknameDupl() {
      this.nicknameDupl = "";
      this.nicknameError = "";
      fetch(
        `${this.$store.state.host}/api/signup/checkNickname?nickname=${this.member.nickname}`
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
    checkEmailDupl() {
      this.emailDupl = "";
      this.emailError = "";
      fetch(
        `${this.$store.state.host}/api/signup/checkEmail?email=${this.member.email}`
      )
        .then((response) => response.text())
        .then((result) => {
          if (result == "false") this.emailDupl = false;
          else this.emailDupl = true;

          if (!this.emailDupl) {
            this.emailError = "중복된 이메일 입니다.";
            this.emailbtn = false;
          } else {
            this.emailError = "";
            this.emailbtn = true;
          }
        })
        .catch((error) => console.log("error", error));
    },
    toggleModal() {
      this.openModal = !this.openModal;
    },
  },

  // 라이프사이클 체크

  beforeCreate() {
    console.log("beforeCreate");
  },
  created() {
    console.log("created");
  },
  beforeMount() {
    // console.log("beforeMount");
  },
  mounted() {
    console.log("mounted");
  },
  beforeUpdate() {
    // console.log("beforeUpdate");
  },
  updated() {
    console.log("updated");
  },
  beforeUnmount() {
    // console.log("beforeUnmount");
  },
  unmounted() {
    console.log("unmounted");
  },
};
</script>
<style scoped>
@import url(/css/component/component.css);
@import url(/css/component/component-sign-up.css);
@import url(/css/button.css);
</style>
