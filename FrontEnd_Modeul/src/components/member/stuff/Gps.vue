<template>
    <section class="canvas-1">

        <header class="d-fl">
            <div>
                <router-link to="/member/stuff/list" class="icon icon-back">뒤로가기</router-link>
            </div>
            <div class="hd-title-box">
                <h1 class="hd-title">우리 동네 설정</h1>
            </div>
        </header>

        <div class="search-box">
            <form class="d-fl d-b-none search-form1">
                <h1 class="icon search-dodbogi m-l-6px">돋보기</h1>
                <input type="search" class="search-input m-l-6px" placeholder="(동,읍,면) + 아파트 or 마을로 검색">
            </form>

            <input type="button" class="icon icon-gps" onclick="startGeolocation()" />
        </div>

        <main>
            <div id="map" class="m-t-72px"></div>
            <div class="m-t-3">
                <span>우리 동네를 설정하고 이웃 주민들과 물건을 함께 구매해봐요</span>
            </div>
        </main>

    </section>
</template>

<style scoped>
    @import "/css/component/member/stuff/component-gps.css";
</style>
