<script>
import dayjs from 'dayjs';
import 'dayjs/locale/ko'
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../../stores/useDefaultStore';


export default {
	data() {
		return {
			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),

			listOrigin: '',
			list: '',
			query: '',
			openModal: null,
			openModal2: null,
			deleteValid: null,
			deleteId: '',

			page: 1,
			totalPage: 0,
			pageSize: 5,
			pageList: [],

		}
	},
	methods: {
		async addListHandler() {

			this.defaultStore.loadingStatus = true; // 해당 함수 true/false 로 어디서나 추가 가능

			await fetch(`${this.defaultStore.host}/api/stuffAll`)
				.then(response => response.json())
				.then(dataList => {
					this.list = dataList;
					this.totalPage = Math.floor(dataList.length / this.pageSize);
					if (dataList.length % this.pageSize > 0) {this.totalPage += 1};
					this.pagingList();
					this.listOrigin = dataList;
					this.defaultStore.loadingStatus = false;
				})
				.catch(error => console.log('error', error));

		},
		async deleteStuff() {
			this.openModal = true;
			var requestOptions = {
				method: 'DELETE',
				redirect: 'follow'
			};
			// this.$router.push("/member/stuff/list");
			await fetch(`${this.defaultStore.host}/api/stuff/${this.deleteId}`, requestOptions)
				.then(response => response.text())
				.then(result => console.log(result))
				.catch(error => console.log('error', error));
			console.log("삭제완료");
			this.openModal = false;
			this.addListHandler();
			this.openModal2 = true;
		},
		modalHandler(e) {
			this.deleteId = e.target.value;
			this.openModal = !this.openModal;
		},
		modalHandler2() {
			this.openModal2 = !this.openModal2;
		},
		queryHandler(e) {
			this.query = e.target.value
			this.list = this.listOrigin.filter(stuff => stuff.title.includes(this.query) || stuff.content.includes(this.query));
		},
		pagingList() {
			let start = this.page * this.pageSize;
			let end = start + this.pageSize;
			this.pageList = this.list.slice(start, end);
			console.log(this.pageList);
		},
		nextPage() {
			this.page += 1;
			this.pagingList();
		},
		prevPage() {
			this.page -= 1;
			this.pagingList();
		},
	},
	created(){

	},
	mounted() {
		this.addListHandler();
		this.pagingList();
	}
}
</script>

<template>
	<main>
		<h1 class="d-none">물건 관리 목록</h1>
		<div class="admin-header">
			<span>게시글 관리</span>
		</div>
		<div class="admin-search-box">
			<div class="search-container-admin-sr">
				<form action="" class="d-fl d-b-none search-form1" method="get">
					<h1 class="icon search-dodbogi m-l-6px">돋보기</h1>
					<input type="search" name="admin-list" class="search-input m-l-6px" placeholder="제목이나 내용으로 검색"
						:value="query" @input="queryHandler">
				</form>
			</div>
		</div>

		<div class="m-t-13">
			<table class="admin-categ-table">
				<thead class="table-head">
					<tr>
						<th style="width: 230px;  min-width: 230px;">글 제목</th>
						<th style="width: 150px;  min-width: 150px;">카테고리</th>
						<th style="width: 200px;  min-width: 200px;">장소</th>
						<th style="width: calc(100vw - 890px); min-width: 700px">내용</th>
						<th style="width: 20px;   min-width: 20px;"></th>
					</tr>
				</thead>
				<tbody class="table-body">
					<tr v-for="s in pageList">
						<td style="width: 230px;  min-width: 230px;  ">{{ s.title }}</td>
						<td style="width: 150px;  min-width: 150px;   ">{{ s.categoryName }}</td>
						<td style="width: 200px;  min-width: 200px;  ">{{ s.place }}</td>
						<td style="width: calc(100vw - 890px);  min-width: 700px; text-align:left;">{{ s.content }}</td>
						<td style="width: 20px;   min-width: 20px;  text-align:left;"><button @click="modalHandler"
								:value="s.id" class="icon-admin3 icon-delete">지우기 버튼</button></td>
					</tr>
				</tbody>
			</table>
		</div>

		<div>
			<button :disabled="page === 0" @click="prevPage()">
					left
			</button>
			<span>{{ page }} / {{ totalPage === 0 ? 1 : totalPage }}</span>
			<button :disabled="page >= totalPage - 1" @click="nextPage()">
					right
			</button>
		</div>

		<!-- 취소 확인 모달 -->
		<div v-if="openModal" class="black-bg">
			<div class="delete-box">
				<div class="delete-box-1">정말로 삭제하시겠습니까?</div>
				<div class="delete-box-2">
					<div @click="deleteStuff" class="delete-box-3">삭제</div>
					<div @click="modalHandler" class="delete-box-4">취소</div>
				</div>
			</div>
		</div>
		<div v-if="openModal2" class="black-bg">
			<div class="delete-box">
				<div class="delete-box-1">삭제되었습니다.</div>
				<div class="delete-box-2">
					<div @click="modalHandler2" class="delete-box-5">확인</div>
				</div>
			</div>
		</div>
	</main>
</template>

<style scoped>
@import "/css/component/component.css";
@import "/css/component/admin/component-admin.css";
@import "/css/component/admin/stuff/component-stuff-list.css";


.black-bg {
	position: fixed;
	background: rgba(0, 0, 0, 0.7);
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 9;
}

.delete-box {
	width: 253px;
	height: 113px;
	background: #FFFFFF;
	border-radius: 10px;
	color: #000000;
	font-weight: 400;
	font-size: 12px;
	display: flex;
	align-items: center;
	flex-direction: column;
	position: relative;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

.delete-box-1 {
	width: 135px;
	height: 12px;
	background: #FFFFFF;
	border-radius: 5px;
	color: #000000;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 12px;
	margin-top: 28px;
}

.delete-box-2 {
	width: 180px;
	height: 26px;
	margin-top: 23px;
	display: flex;
	justify-content: center;
}

.delete-box-3 {
	width: 65px;
	height: 26px;
	background: #FFFFFF;
	border-radius: 5px;
	border: 0.5px solid #E01616;
	color: #E01616;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 26px;
	cursor: pointer;
}

.delete-box-4 {
	width: 65px;
	height: 26px;
	background: #FFFFFF;
	border-radius: 5px;
	border: 0.5px solid #6A6A6A;
	color: #6A6A6A;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 26px;
	margin-left: 25px;
	cursor: pointer;
}

.delete-box-5 {
	width: 65px;
	height: 26px;
	background: #FFFFFF;
	border-radius: 5px;
	border: 0.5px solid #6A6A6A;
	color: #6A6A6A;
	font-weight: 400;
	font-size: 10px;
	text-align: center;
	line-height: 26px;
	margin: auto;
	cursor: pointer;
}
</style>