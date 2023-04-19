<template>
    <aside class="admin-sidebar1" :class="{'d-none':isCheck}">
        <div class="iconbox-admin">
            <a href="" class="icon-admin icon-menu2" @click.prevent="openAside">메뉴</a>
        </div>  
        <!-- <div class="circle"></div> -->

        <div class="sidebar-admin m-t-218px">
            <div class="iconbox-admin">
                <router-link to="/admin/index" class="icon-admin1 icon-home">홈</router-link>
            </div>
            <div class="iconbox-admin m-t-35px">
                <router-link to="/admin/member/list" class="icon-admin1 icon-membermanage">회원관리</router-link>
            </div>
            <div class="iconbox-admin m-t-35px">
                <router-link to="/admin/stuff/list" class="icon-admin1 icon-stuffmanage">게시글관리</router-link>
            </div>
            <div class="iconbox-admin m-t-35px">
                <router-link to="/admin/category/list" class="icon-admin1 icon-category">카테고리 관리</router-link>
            </div>

            <div class="iconbox-admin m-t-35px">
                <router-link to="/admin/analytics/list" class="icon-admin1  icon-analytics">통계</router-link>
            </div>
        </div>
    </aside>

    <aside class="admin-sidebar2" :class="{'d-none':isCheck===false}">
        <h1 class="d-none">사이드바</h1>
        <div class="iconbox-admin">
            <div>
                <a href="" class="icon-admin icon-menu2" @click.prevent="openAside">메뉴</a>
            </div>
            <div>
                <h1 class="logo-admin">mo_deul</h1>
            </div>  
        </div>

        <div class="m-t-218px">
            <div class="iconbox-admin" >
                <a href="../analytics/list.html" class="icon-admin1 icon-home">홈</a>
                <h1 class="label-admin">홈</h1>
            </div>
            <div class="iconbox-admin m-t-35px">
                <a href="../member/list.html" class="icon-admin1 icon-membermanage">회원 관리</a>
                <h1 class="label-admin">회원 관리</h1>
            </div>
            <div class="iconbox-admin m-t-35px">
                <a href="../stuff/list.html" class="icon-admin1 icon-stuffmanage">게시글 관리</a>
                <h1 class="label-admin">게시글 관리</h1>
            </div>
            <div class="iconbox-admin m-t-35px">
                <a href="../category/list.html" class="icon-admin1 icon-category">카테고리 관리</a>
                <h1 class="label-admin">카테고리 관리</h1>
            </div>
            <div class="iconbox-admin m-t-35px">
                <a href="../analytics/list.html" class="icon-admin1 icon-analytics">통계</a>
                <h1 class="label-admin">통계</h1>
            </div>
        </div>
    </aside>
    
</template>

<script>
    export default {
        data() {
            return {
                isCheck:false
                
            }
        },
        methods: {
            /* reg1-> reg2 페이지 이동 */
            openAside(){
                this.isCheck = !this.isCheck;
            }
        },
    }
</script>

<style scoped>
    @import "/css/component/component.css";
    @import "/css/component/admin/component-admin.css";
</style>