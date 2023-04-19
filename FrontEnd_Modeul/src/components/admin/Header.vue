<template>
    <nav>
        <h1 class="d-none">네비게이션바</h1>

        <div class="search-container-admin">
            <form action="" class="d-fl d-b-none search-form2 m-r-144px" method="get">
                <h1 class="icon search-dodbogi m-l-6px">돋보기</h1>
                <input type="search" name="admin" class="search-input m-l-6px"  placeholder="검색어 입력" onkeypress="search_result(event)">
            </form>
        </div>
        
    </nav>
</template>

<style scoped>
    @import "/css/component/admin/component-admin.css";
</style>