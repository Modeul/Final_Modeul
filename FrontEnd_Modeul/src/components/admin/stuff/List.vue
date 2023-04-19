<template>

    <main>
        <h1 class="d-none">물건 관리 목록</h1>
            
            <div class="tablebox-admin m-l-250px m-t-138px">
                <div class="d-fl fl-dir-col al-fe">
                    <div class="search-container-admin-sr m-t-35px">
                        <form action="" class="d-fl d-b-none search-form1" method="get">
                            <h1 class="icon search-dodbogi m-l-6px">돋보기</h1>
                            <input type="search" name="admin-list" class="search-input m-l-6px" placeholder="검색어 입력" onkeypress="search_result(event)">
                        </form>
                    </div>
                </div>  

                <div class="admin-categ-table">
                <table class="table-admin m-t-35px">
                    <thead>
                        <tr>
                            <th><input type="checkbox"></th>  
                            <th>사진</th>
                            <th>글 제목</th>
                            <th>카테고리</th>
                            <th>장소</th>
                            <th>인원</th>
                            <th>URL</th>
                            <th>장소</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="checkbox"></td>  
                            <td>이미지1</td>
                            <td>jhjhjh</td>
                            <td>wosdsdk312</td>
                            <td>홍길동</td>
                            <td>wsdfk@naver.com</td>
                            <td>신수동</td>
                            <td>sdfsdf##@1</td>
                            <td><a href="" class="icon-admin3 icon-delete">지우기 버튼</a></td>
                        </tr>   
                        <tr>
                            <td><input type="checkbox"></td>  
                            <td>이미지1</td>
                            <td>jhjhjh</td>
                            <td>wosdsdk312</td>
                            <td>홍길동</td>
                            <td>wsdfk@naver.com</td>
                            <td>신수동</td>
                            <td>sdfsdf##@1</td>
                            <td><a href="" class="icon-admin3 icon-delete">지우기 버튼</a></td>
                        </tr> 
                        <tr>
                            <td><input type="checkbox"></td>  
                            <td>이미지1</td>
                            <td>jhjhjh</td>
                            <td>wosdsdk312</td>
                            <td>홍길동</td>
                            <td>wsdfk@naver.com</td>
                            <td>신수동</td>
                            <td>sdfsdf##@1</td>
                            <td><a href="" class="icon-admin3 icon-delete">지우기 버튼</a></td>
                        </tr>
                        <tr>
                            <td><input type="checkbox"></td>  
                            <td>이미지1</td>
                            <td>jhjhjh</td>
                            <td>wosdsdk312</td>
                            <td>홍길동</td>
                            <td>wsdfk@naver.com</td>
                            <td>신수동</td>
                            <td>sdfsdf##@1</td>
                            <td><a href="" class="icon-admin3 icon-delete">지우기 버튼</a></td>
                        </tr>
                        <tr>
                            <td><input type="checkbox"></td>  
                            <td>이미지1</td>
                            <td>jhjhjh</td>
                            <td>wosdsdk312</td>
                            <td>홍길동</td>
                            <td>wsdfk@naver.com</td>
                            <td>신수동</td>
                            <td>sdfsdf##@1</td>
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
    @import "/css/component/admin/component-admin.css";
    @import "/css/component/admin/stuff/component-stuff-list.css";
</style>