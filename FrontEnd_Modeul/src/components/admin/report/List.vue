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
			page: '',
			list: [],
			openModal: null,
			openModal2: null,
			openModal3: null,
			deleteValid: null,
			deleteId: '',
		}
	},
	methods: {
		async addListHandler() {

			this.defaultStore.loadingStatus = true; // 해당 함수 true/false 로 어디서나 추가 가능

			await fetch(`${this.defaultStore.host}/api/reports/stuff`)
				.then(response => response.json())
				.then(dataList => {
					this.list = dataList;
					this.defaultStore.loadingStatus = false;
				})
				.catch(error => console.log('error', error));

		},
		async deleteReport() {
			this.openModal3 = true;
			var requestOptions = {
				method: 'DELETE',
				redirect: 'follow'
			};
			// this.$router.push("/member/stuff/list");
			await fetch(`${this.defaultStore.host}/api/reports/stuff?id=${this.deleteId}&c=c`, requestOptions)
				.then(response => response.text())
				.catch(error => console.log('error', error));
			this.openModal3 = false;
			this.addListHandler();
			this.openModal2 = true;
		},
		async deleteStuff() {
			this.openModal = true;
			var requestOptions = {
				method: 'DELETE',
				redirect: 'follow'
			};
			// this.$router.push("/member/stuff/list");
			await fetch(`${this.defaultStore.host}/api/reports/stuff?id=${this.deleteId}`, requestOptions)
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
		modalHandler3(e){
			this.deleteId = e.target.value;
			this.openModal3 = !this.openModal3;
		},
		modalHandler2() {
			this.openModal2 = !this.openModal2;
		},
		formatDate(date){

				let regdate = dayjs(date).locale('ko');
				let formatDate = regdate.format('YYYY-M-D HH:mm');
				return formatDate;
		}
	},

	mounted() {
		this.addListHandler();
	}
}
</script>

<template>
	<main>
		<h1 class="d-none">신고 관리 목록</h1>
		<div class="admin-header">
			<span>신고 관리</span>
		</div>
		<!-- 취소 확인 모달 -->
		<div v-if="openModal3" class="black-bg">
			<div class="delete-box">
				<div class="delete-box-1">정말로 삭제하시겠습니까?</div>
				<div class="delete-box-2">
					<div @click="deleteReport" class="delete-box-3">삭제</div>
					<div @click="modalHandler3" class="delete-box-4">취소</div>
				</div>
			</div>
		</div>
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

		<h1 class="d-none">신고 목록</h1>

		<div class="m-t-13">
			<table class="admin-categ-table">
				<thead class="table-head">
					<tr>
						<th style="width: 100px;">번호</th>
						<th style="width: 100px;">신고 일</th>
						<th style="width: 150px;">신고 자</th>
						<th style="width: 100px;">신고 글</th>
						<th style="width: 600px; text-align: left;">신고 사유</th>
					</tr>
				</thead>
				<tbody class="table-body" style="width: 100%">
					<tr v-for="s in list">
						<td style="width: 100px; ">{{ s.id }} <button @click="modalHandler3" :value="s.id" class="icon-admin3 icon-delete">지우기 버튼</button></td>
						<td style="width: 100px; " v-text=formatDate(s.regdate)></td>
						<td style="width: 150px; ">{{ s.nickname }}</td>
						<td style="width: 100px;  color: rgba(114, 153, 190, 1);"><router-link :to="{ path: '/member/stuff/' + s.stuffId }">{{ s.stuffId }}</router-link></td>
						<td style="width: 600px; text-align: left;">{{s.detail}}<button style="float: right;" @click="modalHandler" :value="s.stuffId" class="icon-admin3 icon-delete">지우기 버튼</button></td>
					</tr>
				</tbody>
			</table>
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