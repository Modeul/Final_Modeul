<template>
    <div class="wrap">
        <header class="header">
            <router-link to="/member/mypage" class="back"></router-link>
            <div class="changpwd-title">비밀번호 변경</div>
        </header>
        <section>
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
        </section>
    </div>
</template>
<script>

</script>
<style scoped>
    @import url(/css/component/component.css);
    @import url(/css/component/component-sign-up.css);
    @import url(/css/button.css);
</style>