<template>
    <div class="canvas">
        <router-link to="/member/stuff/list">
            <header class="header">
                <img src="/public/images/member/stuff/mypage-logo.svg">
            </header>
        </router-link>
        <section>
            <div class="profile-wrap">
                <div class="profile-img">
                    <img>
                </div>
                <router-link to="/member/mypage/edit" class="edit-btn">
                    <img src="/public/images/member/stuff/mp-edit-btn.svg">
                </router-link>
                <div class="profile-name">날아라 감자맨</div>
                <div class="star">
                    <img src="/public/images/member/stuff/star.svg">
                    <span class="star-avg">5.0</span>
                </div>
            </div>

            <ul class="mypage-list">
                <li>
                    <img src="/public/images/member/stuff/write.svg">
                    <span>작성 글 목록</span>
                </li>
                <router-link to = "participation/list">
                    <li>
                        <img src="/public/images/member/stuff/cart.svg">
                        <span>참여 딜 목록</span>
                    </li>
                </router-link>
                <li>
                    <img src="/public/images/member/stuff/heart_plus.svg">
                    <span>관심목록</span>
                </li>
                <li>
                    <img src="/public/images/member/stuff/logout.svg">
                    <span>로그아웃</span>
                </li>
                <li>
                    <img src="/public/images/member/stuff/out.svg">
                    <span>탈퇴하기</span>
                </li>
            </ul>
        </section>
    </div>
</template>
<script>
    export default{
        data(){

        },
        methods:{
            
        },
        mounted(){

        }
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
</style>