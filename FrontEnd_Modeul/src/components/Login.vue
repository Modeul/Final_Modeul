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
                        <label for="uid" class="uid-label">
                            <span class="d-none">uid</span>
                        </label>
                        <input type="text" id="uid" name="uid" 
                            class="input-text" placeholder=" 아이디">
                    </div>
                    <div class="input-field-1 m-t-1">
                        <label for="password" class="password-label">
                            <span class="d-none">uid</span>
                        </label>
                        <input type="password" id="password" name="password" 
                            class="input-text" placeholder=" 비밀번호">
                    </div>
                    <div>
                        <router-link to="/member/stuff/list"><input class="btn-2" type="submit" value="로그인"></router-link>
                    </div>
                </form>

            </div>
        <!-- 아이디찾기 ~ 플렉스 -->
            <div class="find-container">
                <div class="find-box">
                    <span class="find-text" >아이디 찾기</span>
                    <span class="find-text2">|</span>
                    <span class="find-text" >비밀번호 찾기</span>
                </div>
            
                <div class="signup-box">
                    <span class="signup-text2">Modeul이 처음이세요?</span>
                    <router-link to="signup" class="signup-text"><h2 class="fromCenter">회원가입</h2></router-link>
                </div>
            </div>

        </main>

    </div>

</template>


<style scoped>
	@import "/css/component/component.css";
	@import "/css/component/component-login.css";
	@import "/css/button.css"
</style>