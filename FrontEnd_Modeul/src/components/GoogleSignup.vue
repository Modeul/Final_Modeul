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
                            <input type="text" id="address" name="address" class="input-text-2" v-model="member.address"
                                hidden />
                            <div class="input-text-3" v-text="member.address"></div>
                        </label>
                    </div>
                    <div class="input-field-2 m-t-18">
                        <label for="addr2" class="address-label signup-label">
                            <input type="text" id="addr2" name="addr2" class="input-text-2" v-model="addr2"
                                placeholder="상세 주소를 입력해주세요." />
                        </label>
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
                        <div class="modal-txt">가입 완료</div>
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
            nameError: "",
            nicknameError: "",
            ErrorMsg: "",
            // 중복체크
            nicknameDupl: "",
            // 체크버튼
            namebtn: null,
            nicknamebtn: null,
            // 모달
            openModal: false,
            openModal2: false,
        };
    },
    methods: {
        async submit() {
            this.ErrorMsg = "";


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
            // 만약 ErrorMsg 가 있다면 모달창 띄우기
            if (this.ErrorMsg) {
                this.openModal = true;
                return false;
            }
            // 전체 체크
            if (
                this.namebtn &&
                this.nicknameDupl &&
                !this.addrError
            ) {
                var myHeaders = new Headers();
                myHeaders.append("Content-Type", "application/json");

                this.member.address = this.member.address + ',' + this.addr2;

                var raw = JSON.stringify({
                    "name": this.member.name,
                    "email": this.userDetails.email,
                    "nickname": this.member.nickname,
                    "address": this.member.address,
                    "coordX": this.member.coordX,
                    "coordY": this.member.coordY,

                });

                var requestOptions = {
                    method: 'POST',
                    headers: myHeaders,
                    body: raw,
                    redirect: 'follow'
                };

                fetch(`${this.defaultStore.host}/api/signup/google`, requestOptions)
                    .then(response => response.json())
                    .catch(error => console.log('error', error));
                this.toggleModal2();
            }
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

        toggleModal() {
            this.openModal = !this.openModal;
        },
        toggleModal2() {
            this.openModal2 = !this.openModal2;
        },
        move(){
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
