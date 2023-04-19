<script>
    import Header from './Header.vue';
    import Aside from './Aside.vue';

    export default {
        components:{
            Header,
            Aside
        },
        data(){
            return{
            }
        }
    }
</script>

<template>
    <section class="canvas-2 d-fl fl-dir-col">
        <Header/>
        <!-- --------------------------------------- -->
        <Aside/>
        <!-- --------------------------------------- -->
        <router-view></router-view>
    </section>
</template>
