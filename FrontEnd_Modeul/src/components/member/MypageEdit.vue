<template>
    <div class="mypage">
        <div class="header">
            <router-link to="/member/mypage" class="back"></router-link>
            <div class="title">프로필 수정</div>
        </div>
        <div class="profile-pic">
            <div class="profile-img">
                <img>
            </div>
            <div class="edit-btn">
                <img src="/public/images/member/stuff/mypageEditIcon.svg">
            </div>
        </div>
        <div class="mypage-input">
            <div class="input-field">
                <div class="uid-icon"></div>
                <span class="text">uid</span>
            </div>
            <div class="input-field">
                <div class="email-icon"></div>
                <span class="text">uid</span>
            </div>
            <div class="input-field">
                <div class="nickname-icon"></div>
                <span class="text">uid</span>
            </div>
            <div class="email"></div>
            <div class="nickname"></div>
        </div>
        <div class="btn-save">저장하기</div>
    </div>
</template>
<script>
export default {
    
}
</script>
<style>
    .mypage{
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 0;
        
        position: relative;
        width: 360px;
        height: 740px;
        
        background: #FFFFFF;
    }
    .mypage .header{
        display: flex;
        flex-direction: row;
        align-items: center;
        padding: 0px;
        gap: 10px;
        
        width: 312px;
        height: 88px;
    }
    .mypage .header .back{
        background-image: url("data:image/svg+xml,%3Csvg width='16' height='16' viewBox='0 0 16 16' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M16 7H3.83L9.42 1.41L8 0L0 8L8 16L9.41 14.59L3.83 9H16V7Z' fill='black'/%3E%3C/svg%3E%0A");
        width: 23.04px;
        height: 24px;
        margin-top: 9px;
    }
    .mypage .header .title{
        margin-left: 86px;
    }
    .profile-pic{
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        padding: 10px;
        gap: 10px;
        
        width: 312px;
        height: 130px;
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
        top: 183px;
        cursor: pointer;
    }
    .edit-btn:hover{
        transform: scale(1.1);
        transition: 0.3s;
    }
    .mypage-input{
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 0px 10px 10px;
        gap: 20px;
        margin-top: 52px;
        
        width: 312px;
        height: 225px;
    }
    .mypage-input .input-field{
        display: flex;
        align-items: center;
        width: 300px;
        height: 45px;
        border: 1px solid #D5D5D5;
        border-radius: 10px;
    }
    .mypage-input .uid-icon{
        width: 25px;
        height: 25px;
        left: 10px;
        top: 7px;
        margin-left: 12px;
        display: inline-block;
        background-size: contain;
        background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_649' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_649' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAj5JREFUaEPtmIExRDEQhv+rgA7oABWgAlSAClABOqACVIAKUAE6oAMqYD6TJZdzL3nvJY+7SWbe3LxLsvn/3X83ezfSjI/RjONXJfDXEawRqBHo6YG5ltBHT+c0bX+QtJHDflMEKoEGDw8SgRwRLm5jrpO4uPdyHFAjkMOLfWw0ReCuj2Fv77Okw8DWrqRtSavu+ydJN5Ku2p45xD2wJgmAjEVJ1w2X2L2kHUlvqURKE7h1njY8RDV2A0NiMweB2EEpZ7xI4mHsSbrwNh052djcsTe3L+ky5YAhqxCA0D7jVNJJAJB3I0EuQDg6hiTw6CWtnxcGkoRmDYOcYU10DEkAba87RGicd38gWat8yb3SkARiEjqTdOAYZZGQn1TRUAYLAGDJa1PUfUqoDQBT+xnM+XcFpdTmGs8uVUZ/kwhAALUV8UZYev8VAS4ypDSNBOCpPlkusrDMtZERIEMJ+fuRDEnrtxIkdZJsfENDJnEbBySvrQSSXfWzkDxYca/WrtidQOearH9sDBUBgNJG8LkcIc0tDKHzSB59mSlNANAUgxjoaZwgQ9MX3trf60sRoLrQeVqVCQGaVAwYkfGlFa5nHUTsd0VRAn5PYwe9uxJJmQTMNJ1Dgv2UWetczQZ7uCDHSJSIgN91ApyWgadVcrqI0F7wLDgWEz92ShCwvyRfnYTaAg/lQ1Tw+pKbGMNckkCHCpu0pRKIuankv9oTpb+EhGIEs85XAlnd2cFYjUAHp2XdUiOQ1Z0djM18BD4B8lljMc3J4QIAAAAASUVORK5CYII='/%3E%3C/defs%3E%3C/svg%3E%0A");
    }
    .mypage-input .email-icon{
        width: 25px;
        height: 25px;
        left: 10px;
        top: 7px;
        margin-left: 12px;
        display: inline-block;
        background-size: contain;
        background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_666' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_666' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAf9JREFUaEPtme01xEAUht+tgA7QgQ7ogA5QAR2gAlSADuiADnSwVIAKOI8z15ldid2bmUw2e+b+yTnJzJ37fsxHkolGHpOR168KYGgFqwJVgUQG1tZC55LOJG0mEpSr+4eka0mX8wmbFLiQBIBVDABQ3280AXhfIebnSXyVtLMIwFfU4EXSiSSuQ8SupFtJXC1mSG9SIAZgnZDtRhJeLBXYeMYuYWAXgE9JG6Ej8qHGU88I9iVdRazHNTC0CwB+u5O0FxVtq0FuNVjxbPWz4Z4lHUuadrWQoWVJRc6+1IB1vL4dCoV1xoMsIra1S4G4MclJeBCx8RBs1VUNWMcusGzxGPYgLGuRBYAlOwy2MjUonrkBGE+QB9Zts4R1gDTlyQqAIhmUudFFDfpSOABi1im+TcnsAP5Tg/ly3yLFUbDhMqzHKXoD0KYGSy22Mh8zf2Cdybos68UAxGowybfCDexgGxFXY/0teN2zn/SqQMwURVLsaYuF2M157l21igGwurEKk9zU6MJ6cQvNk25qcL8L64MD8OwJi9oWt9CigrzPKwAvY7nbVwVyM+rNVxXwMpa7fVUgN6PefFUBL2O527sU4Khr77u5C0nNx6nWvlz85FrLj7t2/OUl287zqcyl9od53i/+fGpc2x8cqYwV618VKEZ1y0BVgapAIgOjt9A34VGAMWTd1zgAAAAASUVORK5CYII='/%3E%3C/defs%3E%3C/svg%3E%0A");
    }
    .mypage-input .nickname-icon{
        width: 25px;
        height: 25px;
        left: 10px;
        top: 7px;
        margin-left: 12px;
        display: inline-block;
        background-size: contain;
        background-image: url("data:image/svg+xml,%3Csvg width='25' height='25' viewBox='0 0 25 25' fill='none' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Crect width='25' height='25' fill='url(%23pattern0)'/%3E%3Cdefs%3E%3Cpattern id='pattern0' patternContentUnits='objectBoundingBox' width='1' height='1'%3E%3Cuse xlink:href='%23image0_126_665' transform='scale(0.0208333)'/%3E%3C/pattern%3E%3Cimage id='image0_126_665' width='48' height='48' xlink:href='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAiZJREFUaEPtmY0xBEEQhd9FgAgQASJABkRABogAESADIiADZCADRIAIqK9qV82N2Z3ZuZ49WzVddXV7d/PTr1//zdxME5fZxPVXBbBsBq0ZWJV0LulYEs+ufEp6kHQmiWcTsQZwL+kgotmTpH0T7SXTGMDiH4mKrVmxYMnAnqTHBsCzJD67guV3my9ggM8LSwXgmLAykONP1YWqC+X4jTOny4WopqeBapq6XSyNpq7TjqNyX0u69CeGAFw07cDQTdzx1gDatQGAfr8SAkA19fuYoWBKAXiTtBkD8J3gYiFAJVuJTp1CDOQCAJTbLnSxFmInxvBoAGCBYKMjXfG0+mraaZLD0HZ6NAAxS+b+XgHkWs5qXmWgz5IE7FYTmHcJAcp5+ShCjX/kLMYAGeXKUeZF0k5EuZRK76f3YgBeJW14CseOizDAq0/842gxAORzP98fNvneKoBZpxgAitaJo+m7pO2EOBgKrhgAFMGnoZxGi2fe+wSXW4+Mod1wpSiAodb8V0HMPQ8u07bfZCGs19frLB0ACnNiw226zg3cgd50XF7hQn7m8ln0L73MXAiFSZ2pBx4OH7GYSHFBMwBUXS5wU+XPETB1YqkgTilC7t63kngtKmYMLKpI7vwKINdyVvMqA1aWzF1nEAOhDjN3Y+t5NItzRbDU1aK14u16SVeLDKZfIefHusZSivrrYnnqydy9KIMs/+AYC8zcPhXAUszubDp5Bn4AITeSMeOKBiYAAAAASUVORK5CYII='/%3E%3C/defs%3E%3C/svg%3E%0A");
    }
    .mypage-input .input-field .text{
        display: inline-block;
        font-size: 14px;
        margin-left: 8px;
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
    }
</style>