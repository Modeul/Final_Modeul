<template>
    <main class="list-admin-main d-fl fl-dir-col al-c">
        <h1 class="d-none">카테고리 관리 목록</h1>

        <!-- <div class=""> -->
        <div class="categoryplus d-fl jf-fe m-t-62px m-r-800px">
            <button class="btn-next">카테고리 추가</button>
        </div>
        <!-- </div> -->

        <div class="tablebox-admin m-t-35px">
            <div class="d-fl fl-dir-col al-fe">
                <div class="search-container-admin2 m-t-35px">
                    <form action="" class="d-fl d-b-none search-form1" method="get">
                        <h1 class="icon search-dodbogi m-l-6px">돋보기</h1>
                        <input type="search" name="admin-list" class="search-input m-l-6px" placeholder="검색어 입력" onkeypress="search_result(event)">
                    </form>
                </div>
            </div>  

            <div class="admin-categ-table">
                <table class="m-t-35px">
                    <thead>
                        <tr>
                            <th><input type="checkbox"></th>  
                            <th>카테고리 아이디</th>
                            <th>카테고리 이름</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="checkbox"></td>
                            <td>1</td>
                            <td>일반 상품</td>
                            <td><a href="" class="icon-admin3 icon-delete">지우기 버튼</a></td>
                        </tr>
                        <tr>
                            <td><input type="checkbox"></td>
                            <td>2</td>
                            <td>딜리버리 푸드</td>
                            <td><a href="" class="icon-admin3 icon-delete">지우기 버튼</a></td>
                        </tr>
                        <tr>
                            <td><input type="checkbox"></td>
                            <td>3</td>
                            <td>대형마트 대형 물품</td>
                            <td><a href="" class="icon-admin3 icon-delete">지우기 버튼</a></td>
                        </tr>
                        <tr>
                            <td><input type="checkbox"></td>
                            <td>4</td>
                            <td>반찬</td>
                            <td><a href="" class="icon-admin3 icon-delete">지우기 버튼</a></td>
                        </tr>
                        <tr>
                            <td><input type="checkbox"></td>
                            <td>5</td>
                            <td>밀키트</td>
                            <td><a href="" class="icon-admin3 icon-delete">지우기 버튼</a></td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="d-fl-al fl-dir-col">
                <div class="m-l-2 m-t-35px m-b-35px">
                    <button class="btn-next">더보기</button>
                </div>
            </div>
        </div>
        
    </main>
</template>

<style scoped>
    @import "/css/component/component.css";
    @import "/css/component/admin/category/component-category-list.css";
    @import "/css/component/admin/component-admin.css";
</style>