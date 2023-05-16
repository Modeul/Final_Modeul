<template>
    <div class="canvas">
        <header>
            <h1 class="d-none">제목입니다.</h1>
            <div class="header-container d-fl-jf">
                <h1 class="logo-main logo-moduel m-t-80px">mo_deul</h1>
            </div>
        </header>
        <!--  -->
        <main>
            <!-- flex start -->
            <div class="main-container">
                <h1 class="d-none">바디</h1>
                <p class="index-txt-wrap">
                    <h2 class="index-title">우리 동네 공구 마켓</h2><br>
                    <span class="index-text">
                        모들은 이웃 주민들과<br>
                        물건을 함께 구매하는 마켓이에요<br>
                        로그인을 하여 이용해보세요<br>
                    </span>
                </p>
            </div>
            <!-- flex end -->
            
            <!-- flex start -->
            <div class="link-container">
                <h1 class="d-none">시작</h1>
                <div class="btn-1">
                    <router-link to="/login" class="start-text" >START </router-link>
                </div>
            </div>
            <!-- flex end -->
        </main>
    </div>
</template>


<style scoped>
    @import "/css/component/component.css";
    @import "/css/component/component-index.css";
</style>
