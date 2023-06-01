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
			pageSize: 6,
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
					if (dataList.length % this.pageSize > 0) { this.totalPage += 1 };
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
				.catch(error => console.log('error', error));
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
			this.totalPage = Math.floor(this.list.length / this.pageSize);
			if (this.list.length % this.pageSize > 0) { this.totalPage += 1 };
			this.pagingList()
		},
		pagingList() {
			if (this.page > this.totalPage)
				this.page = 1;
			let start = (this.page - 1) * this.pageSize;
			let end = start + this.pageSize;
			this.pageList = this.list.slice(start, end);
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
	created() {

	},
	mounted() {
		this.addListHandler();
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
				<div class="d-fl d-b-none search-form1">
					<h1 class="icon search-dodbogi m-l-6px">돋보기</h1>
					<input type="search" name="admin-list" class="search-input m-l-6px" placeholder="제목이나 내용으로 검색" :value="query"
						@input="queryHandler">
				</div>
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
						<td style="width: 230px;  min-width: 230px;">
							<router-link :to="{ path: '/member/stuff/' + s.id }"><span></span>{{ s.title }}</router-link>
						</td>
						<td style="width: 150px;  min-width: 150px;">
							<span>{{ s.categoryName }}</span>
						</td>
						<td style="width: 200px;  min-width: 200px;">
							<span>{{ s.place }}</span>
						</td>
						<td style="width: calc(100vw - 890px);  min-width: 700px; text-align:left;">
							<span>{{ s.content }}</span>
						</td>
						<td style="width: 20px;   min-width: 20px; text-align:left;">
							<button @click="modalHandler" :value="s.id" class="icon-admin3 icon-delete">지우기 버튼</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="paging-nav">
			<div class="nav-items">
				<button class="btn-prev-page" :disabled="page === 1" @click="prevPage()">
				</button>
			</div>
			<div class="nav-items"> <span>{{ page }} / {{ totalPage === 0 ? 1 : totalPage }}</span></div>
			<div class="nav-items">
				<button class="btn-next-page" :disabled="page >= totalPage" @click="nextPage()">
				</button>
			</div>
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


thead tr {
	height: 50px;
}

tbody tr {
	height: 70px;
}

table {
	height: 470px;
	margin-bottom: 40px;
}

tbody td span {
	text-overflow: ellipsis;
	overflow: hidden;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 2;
}

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

.paging-nav {
	margin: 15px auto 0;
	display: flex;
	justify-content: center;
}

.nav-items>button {
	width: 12px;
	height: 20px;
}

.nav-items>button.btn-next-page {
	background-image: url("data:image/svg+xml,%3Csvg width='12' height='20' viewBox='0 0 12 20' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath fill-rule='evenodd' clip-rule='evenodd' d='M0.586061 19.414C0.211119 19.0389 0.000488281 18.5303 0.000488281 18C0.000488281 17.4696 0.211119 16.961 0.586061 16.586L7.17206 9.99996L0.586061 3.41396C0.221745 3.03676 0.0201557 2.53155 0.0247126 2.00716C0.0292694 1.48276 0.239608 0.981139 0.610424 0.610323C0.98124 0.239507 1.48287 0.0291686 2.00726 0.0246117C2.53165 0.0200549 3.03686 0.221644 3.41406 0.585961L11.4141 8.58596C11.789 8.96102 11.9996 9.46963 11.9996 9.99996C11.9996 10.5303 11.789 11.0389 11.4141 11.414L3.41406 19.414C3.03901 19.7889 2.53039 19.9995 2.00006 19.9995C1.46973 19.9995 0.961117 19.7889 0.586061 19.414Z' fill='%2363A0C2'/%3E%3C/svg%3E%0A");
}

.nav-items>button.btn-prev-page {
	background-image: url("data:image/svg+xml,%3Csvg width='12' height='20' viewBox='0 0 12 20' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath fill-rule='evenodd' clip-rule='evenodd' d='M11.4139 0.58604C11.7889 0.961096 11.9995 1.46971 11.9995 2.00004C11.9995 2.53037 11.7889 3.03898 11.4139 3.41404L4.82794 10L11.4139 16.586C11.7783 16.9632 11.9798 17.4684 11.9753 17.9928C11.9707 18.5172 11.7604 19.0189 11.3896 19.3897C11.0188 19.7605 10.5171 19.9708 9.99274 19.9754C9.46835 19.9799 8.96314 19.7784 8.58594 19.414L0.585939 11.414C0.210997 11.039 0.000366211 10.5304 0.000366211 10C0.000366211 9.46971 0.210997 8.9611 0.585939 8.58604L8.58594 0.58604C8.96099 0.211098 9.46961 0.0004673 9.99994 0.0004673C10.5303 0.0004673 11.0389 0.211098 11.4139 0.58604Z' fill='%2363A0C2'/%3E%3C/svg%3E%0A");
}

.nav-items {
	margin: auto 8px;
	display: flex;
}

</style>