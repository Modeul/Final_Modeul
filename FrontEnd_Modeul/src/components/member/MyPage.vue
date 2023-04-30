<template>
    <div class="canvas">
        <router-link to="/member/stuff/list">
            <header class="header">
                <img src="/images/member/stuff/mypage-logo.svg">
            </header>
        </router-link>
        <section>
            <div class="profile-wrap">
                <div class="profile-img">
                    <img class="profile-img" :src="'/images/member/'+loginInfo.image">
                </div>
                <router-link to="/member/mypage/edit" class="edit-btn">
                    <img src="/images/member/stuff/mp-edit-btn.svg">
                </router-link>
                <div class="profile-name">{{ loginInfo.nickname }}</div>
                <!-- <div class="star">
                    <img src="/images/member/stuff/star.svg">
                    <span class="star-avg">5.0</span>
                </div> -->
            </div>
            <ul class="mypage-list">
                <router-link to="/member/mypage/myreglist">
                <li>
                    <img src="/images/member/stuff/write.svg">
                    <span>작성 글 목록</span>
                </li>
                </router-link>
                <router-link to="/member/participation/list">
                    <li>
                        <img src="/images/member/stuff/cart.svg">
                        <span>참여 딜 목록</span>
                    </li>
                </router-link>
                    <li>
                        <img src="/images/member/stuff/heart_plus.svg">
                        <span>관심목록</span>
                    </li>
                <router-link to="/member/mypage/changepwd">
                    <li>
                        <img src="/images/member/stuff/password-icon.svg">
                        <span>비밀번호 변경</span>
                    </li>
                </router-link>
                <li>
                    <img src="/images/member/stuff/logout.svg">
                    <span @click="modalHandler2">로그아웃</span>
                </li>
                <li>
                    <img src="/images/member/stuff/out.svg">
                    <span @click="modalHandler">탈퇴하기</span>
                </li>
            </ul>
        </section>
    </div>
    <!-- 회원탈퇴 재확인 모달 -->
    <div v-if="openModal" class="black-bg">
        <div class="delete-box">
            <div class="delete-box-1">정말로 탈퇴하시겠습니까?</div>
            <div class="delete-box-2">
                <div @click="deleteMember" class="delete-box-3">탈퇴</div>
                <div @click="modalHandler" class="delete-box-4">취소</div>
            </div>
        </div>
    </div>
    <!-- 회원탈퇴 재확인 모달 -->
    <div v-if="openModal2" class="black-bg">
        <div class="delete-box">
            <div class="delete-box-1">정말로 로그아웃 하시겠습니까?</div>
            <div class="delete-box-2">
                <div @click="logout" class="delete-box-3">로그아웃</div>
                <div @click="modalHandler2" class="delete-box-4">취소</div>
            </div>
        </div>
    </div>
</template>
<script>
    export default{
        data(){
            return{
                myMemberId : 118,
			    loginInfo : '',
                openModal : false,
                openModal2 : false,
            }

        },
        methods:{
            deleteMember(){
                var myHeaders = new Headers();
                myHeaders.append("Content-Type", "application/json");

                var raw = JSON.stringify({
                "id": 118
                });

                var requestOptions = {
                method: 'PUT',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
                };

                fetch(`${this.$store.state.host}/api/member/delete`, requestOptions)
                .then(response => response.text())
                .then(result => console.log(result))
                .catch(error => console.log('error', error));
                this.$router.replace('/index');
            },
            logout(){

            },
            modalHandler(){
                this.openModal = !this.openModal;
            },
            modalHandler2(){
                this.openModal2 = !this.openModal2;
            },
        },
        mounted(){
            fetch(`${this.$store.state.host}/api/member/${this.myMemberId}`)
                .then(response => response.json())
                .then(data => {
                    this.loginInfo = data;
                    console.log(data);
                })
        },
    }
</script>
<style scoped>
    .canvas{
        margin: 0 auto;
        width: 320px;
    }
    .header{
        width: 91px;
        margin-top: 25px;
    }
    section{
        display: flex;
        flex-direction: column;
    }
    .profile-wrap{
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-top: 42px;
    }
    .profile-img{
        width: 108px;
        height: 108px;
        border-radius: 50%;
        background-color: antiquewhite;
    }
    .edit-btn{
        width: 36px;
        height: 36px;
        border-radius: 50%;
        position: absolute;
        top: 180px;
        cursor: pointer;
    }
    .edit-btn:hover{
        transform: scale(1.1);
        transition: 0.3s;
    }
    
    .profile-name{
        margin-top: 26px;
        font-size: 14px;
    }
    .star{
        margin-top: 4px;
        display: flex;
        align-items: center;
    }
    .star-avg{
        font-size: 10px;
        margin-left: 4px;
        color: #777;
        font-weight: 500;
    }
    .mypage-list{
        margin-top: 34px;
    }
    .mypage-list li{
        padding: 12px 15px;
        box-sizing: border-box;
        border-bottom: 1px solid #dbdbdb;
        display: flex;
        align-items: center;
        cursor: pointer;
    }
    .mypage-list li:hover{
        background-color: #f7f7f7d2;
        transition: 0.3s;
    }
    .mypage-list span{
        margin-left: 6px;
        font-size: 14px;
    }

    /* 탈퇴 확인 모달 */
.black-bg {
	position: fixed;
	background: rgba(0, 0, 0, 0.7);
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 1007;
}
.delete-box{
	width: 253px;
	height: 113px;
	background: #FFFFFF;
	border-radius: 10px;
	color: #000000;
	font-weight: 400;
	font-size: 12px;
	display: flex;
	align-items: center;
	flex-direction: column;
	position: relative;
	top: 50%;
	left: 50%;
	transform: translate(-50%,-50%);
	z-index: 1007;

}
.delete-box-1{
	width: 135px;
	height: 12px;
	background: #FFFFFF;
	border-radius: 5px;
	color: #000000;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 12px;
	margin-top: 28px;
}
.delete-box-2{
	width: 180px;
	height: 26px;
	margin-top: 23px;
	display: flex;
	justify-content: center;
}
.delete-box-3{
	width: 65px;
	height: 26px;
	background: #FFFFFF;
	border-radius: 5px;
	border:0.5px solid #E01616;
	color: #E01616;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 26px;
	cursor: pointer;
}
.delete-box-4{
	width: 65px;
	height: 26px;
	background: #FFFFFF;
	border-radius: 5px;
	border:0.5px solid #6A6A6A;
	color: #6A6A6A;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 26px;
	margin-left: 25px;
	cursor: pointer;
}
</style>