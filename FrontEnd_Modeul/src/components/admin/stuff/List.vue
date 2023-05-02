<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko'

export default {
    data() {
        return {
			page: '',
			list: [],
			categoryList: [],
			categoryId: '',
			listCount: '',
        }
    },
    methods: {

        categoryHandler(e) {
            this.page = 1;
            this.categoryId = e.target.value;
            console.log(this.categoryId);
            fetch(`${this.$store.state.host}/api/stuffs?p=${this.page}&c=${this.categoryId}`)
                .then(response => response.json())
                .then(dataList => {
                    this.list = this.formatDateList(dataList.list);
                    this.listCount = dataList.listCount;
                    this.categoryList = dataList.categoryList;
                    console.log(this.list)
                }).catch(error => console.log('error', error));
        },
        async addListHandler() {

            this.$store.commit('LOADING_STATUS', true); // 해당 함수 true/false 로 어디서나 추가 가능

            this.page++;
            await fetch(`${this.$store.state.host}/api/stuffs?p=${this.page}&c=${this.categoryId}`)
                .then(response => response.json())
                .then(dataList => {
                    this.list = this.formatDateList(dataList.list);
                    this.listCount = dataList.listCount;
                    this.categoryList = dataList.categoryList;
                    console.log(dataList);
                    this.$store.commit('LOADING_STATUS', false);
                })
                .catch(error => console.log('error', error));

        },
        scrollCheck() {
            if (window.innerHeight >= 718) {
                this.addListHandler();
            }
        },

        formatDateList(list) {
            if (list == null)
                return;
            let resultList = [];
            for (let item of list) {
                if (item.deadline == null)
                    continue;
                const today = new dayjs().format('YYYY-MM-DD');

                const deadlineObj = dayjs(item.deadline).locale('ko');
                const isToday = (deadlineObj.format('YYYY-MM-DD') == today) ? '오늘, ' : ''
                item.deadline = isToday + deadlineObj.format("M월 D일 (dd) HH시까지");

                item.dDay = dayjs().diff(deadlineObj, 'day');
                if (parseInt(item.dDay) < 0) {
                    item.dDay = 'D' + item.dDay;
                    item.deadlineState = 1;
                }
                else if (parseInt(item.dDay) == 0) {
                    item.dDay = deadlineObj.diff(dayjs(), 'hours')
                    if (parseInt(item.dDay) > 0) {
                        item.dDay = '마감 ' + deadlineObj.diff(dayjs(), 'hours') + '시간 전'
                        item.deadlineState = 2;
                    }
                    else if (parseInt(item.dDay) == 0) {
                        item.dDay = '1시간 내 마감';
                        item.deadlineState = 3;
                    }
                    else {
                        item.dDay = '마감';
                        item.deadlineState = 0;
                    }
                }
                else {
                    item.dDay = '마감';
                    item.deadlineState = 0;
                }
                resultList.push(item);
            }
            return resultList;
        },
    },
    mounted() {
        this.page = 0;
        this.addListHandler();
        this.scrollCheck();

        document.addEventListener("scroll", (e) => {

            if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
                if (this.listCount !== 0) {
                    this.addListHandler();
                }

            }
        })

    }
}
</script>

<template>
    <main>
        <h1 class="d-none">물건 관리 목록</h1>

        <div class="tablebox-admin m-l-250px m-t-138px">
            <div class="d-fl fl-dir-col al-fe">
                <div class="search-container-admin-sr m-t-35px">
                    <form action="" class="d-fl d-b-none search-form1" method="get">
                        <h1 class="icon search-dodbogi m-l-6px">돋보기</h1>
                        <input type="search" name="admin-list" class="search-input m-l-6px" placeholder="검색어 입력"
                            onkeypress="search_result(event)">
                    </form>
                </div>
            </div>

            <div class="admin-categ-table">
                <table class="table-admin m-t-35px">
                    <thead class="table-head">
                        <tr>
                            <th><input type="checkbox"></th>
                            <th>사진</th>
                            <th>글 제목</th>
                            <th>카테고리</th>
                            <th>장소</th>
                            <th>인원</th>
                            <th>내용</th>
                            <th>장소</th>
                            <th>URL</th>
                        </tr>
                    </thead>
                    <tbody class="table-body">
                        <tr v-for="s in list">
                            <td><input type="checkbox"></td>
                            <td>{{s.image}}</td>
                            <td>{{s.title}}</td>
                            <td>{{s.categoryName}}</td>
                            <td>{{s.place}}</td>
                            <td>{{s.numPeople}}</td>
                            <td>{{s.content}}</td>
                            <td>{{s.place}}</td>
                            <td>{{s.url}}</td>
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