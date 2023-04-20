<script>
    export default {
        data(){
            return {
                memberNum: 0,
                member: {
                    nickname: ''
                },
                sum: ''
            }
        }
    }
</script>
<template>
    <section class="cal">
        <h1 class="d-none">calculate</h1>
        <header class="cal-header">
            <h1 class="d-none">title</h1>
            <router-link to="list" class="icon cal-back">뒤로가기</router-link>
            <div>정산하기
                <span class="memberNum">{{ memberNum }}</span>
            </div>
        </header>

        <form method="post">
            <section class="cal-contents">
                <h1 class="d-none">memberPrice</h1>
                <div class="cal-members">
                    <img src="/images/member/stuff/category1.svg" alt="img">
                    <div>
                        {{ member.nickname }}
                    </div>
                    <div>
                        <input type="text" placeholder="금액을 입력하세요.">
                    </div>
                    <br>
                </div>
                <div class="cal-sum">
                    <label>합계:</label>
                    <span>{{ sum }}</span>
                    <span>원</span>
                </div>
                <button class="btn-cal cal-button">정산하기</button>
            </section>
        </form>
    </section>
</template>

<style scoped>
    @import "/css/component/member/stuff/component-cal.css";
</style>

