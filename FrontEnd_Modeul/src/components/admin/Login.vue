<template>

    <header>
        <div class="header-container">
            <h1 class="logo-main logo-moduel m-t-138px">mo_deul</h1>
        </div>
    </header>
    <main>
        <div class="input-container">
            
            <form action="./analytics/list.html" method="get">

                <div class="input-field-1">
                    <label for="uid" class="uid-label">
                        <span class="d-none">uid</span>
                        <input type="text" id="uid" name="uid" 
                            class="input-text" placeholder=" 아이디">
                    </label>
                </div>
                <div class="input-field-1 m-t-1">
                    <label for="password" class="password-label">
                        <span class="d-none">uid</span>
                        <input type="text" id="password" name="password" 
                            class="input-text" placeholder=" 비밀번호">
                    </label>
                </div>
                <div>
                    <input class="btn-2" type="submit" value="관리자 로그인">
                </div>
            </form>

        </div>

    </main>    
</template>

<style scoped>
    @import "/css/component/component.css";
    @import "/css/component/admin/component-admin.css";
    @import "/css/component/admin/admin-login.css";
</style>