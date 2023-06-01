<template>
	<main>
		<h1 class="d-none">카테고리 관리 목록</h1>
		<div class="admin-header">
			<span>카테고리 관리</span>
		</div>
		<div class="add-cate-box">
			<button class="addCategory-show-btn" @click.prevent="showAddInput">카테고리 추가</button>
		</div>
		<div class="tablebox-admin">
			<div>
				<table>
					<thead>
						<tr>
							<th style="width: 200px;  min-width: 200px;">아이디</th>
							<th style="width: 200px;  min-width: 200px;">이름</th>
							<th style="width: 200px;  min-width: 200px;"></th>
						</tr>
					</thead>
					<tbody>
						<tr v-if="this.showAdd == true">
							<td>
								<input class="input input-id" v-model="id" placeholder="아이디">
							</td>
							<td>
								<input class="input" v-model="name" placeholder="이름을 입력해주세요.">
							</td>
							<td>
								<button class="save-btn" @click.prevent="[addCategory(), savemodalHandler()]">저장</button>
							</td>
						</tr>
						<tr v-for="item in categoryList">
							<td>{{ item.id }}</td>

							<td><span v-if="editId != item.id">{{ item.name }}</span><input class="input" v-model="item.name" v-else></td>

							<td>
								<span v-if="editId != item.id">
									<button :value="item.id" class="icon-admin3 admin-edit-icon" @click.prevent="editId = item.id">수정하기
										버튼</button>
									<button :value="item.id" class="icon-admin3 icon-delete" @click.prevent="modalHandler">지우기 버튼</button>
								</span>
								<button class="save-btn" v-else @click.prevent="editCategory(item)">확인</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>


		</div>

		<!-- 카테고리 삭제 모달 -->
		<div v-if="openModal" class="black-bg">
			<div class="delete-box">
				<div class="delete-box-1">정말로 삭제하시겠습니까?</div>
				<div class="delete-box-2">
					<div @click="delCategory" class="delete-box-3">삭제</div>
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

		<!-- 카테고리 저장 모달 -->
		<div v-if="saveModal" class="black-bg">
			<div class="delete-box">
				<div class="delete-box-1">저장되었습니다.</div>
				<div class="delete-box-2">
					<div @click="savemodalHandler" class="delete-box-5">확인</div>
				</div>
			</div>
		</div>
	</main>
</template>
<script>
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';
import { useDefaultStore } from '../../../stores/useDefaultStore';

export default {
	
	data() {
		return {

			userDetails: useUserDetailsStore(),
			defaultStore: useDefaultStore(),
			categoryList: [],
			id: "",
			name: "",
			openModal: false,
			openModal2: false,
			deleteId: '',
			saveModal: false,
			showAdd: false,
			editId: '',
		}
	},
	methods: {
		load() {
			fetch(`${this.defaultStore.host}/api/category`)
				.then(response => response.json())
				.then(result => {
					this.categoryList = result
				})
				.catch(error => console.log('error', error));
		},
		showAddInput() {
			this.showAdd = !this.showAdd;
		},
		modalHandler(e) {
			this.deleteId = e.target.value;
			this.openModal = !this.openModal;
		},
		modalHandler2() {
			this.openModal2 = !this.openModal2;
		},
		savemodalHandler() {
			this.saveModal = !this.saveModal;
		},
		async addCategory() {
			var myHeaders = new Headers();
			myHeaders.append("Content-Type", "application/json");

			var raw = JSON.stringify({
				"id": this.id,
				"name": this.name
			});

			var requestOptions = {
				method: 'POST',
				headers: myHeaders,
				body: raw,
				redirect: 'follow'
			};

			await fetch(`${this.defaultStore.host}/api/category`, requestOptions)
				.then(response => response.text())
				.then(result => {
					this.load();
					this.showAdd = false;
				})
				.catch(error => console.log('error', error));
		},
		editCategory(item) {
			var myHeaders = new Headers();
			myHeaders.append("Content-Type", "application/json");
			var raw = JSON.stringify({
				"id": item.id,
				"name": item.name
			});

			var requestOptions = {
				method: 'PUT',
				headers: myHeaders,
				body: raw,
				redirect: 'follow'
			};

			fetch(`${this.defaultStore.host}/api/category`, requestOptions)
				.then(response => response.text())
				.catch(error => console.log('error', error));
			this.editId = '';
		},
		async delCategory() {
			this.openModal = true;
			var requestOptions = {
				method: 'DELETE',
				redirect: 'follow'
			};
			await fetch(`${this.defaultStore.host}/api/category?id=${this.deleteId}`, requestOptions)
				.then(response => response.text())
				.then(result => {
					this.openModal = false;
					this.openModal2 = true;
					this.load();
				})
				.catch(error => console.log('error', error));

		}
	},
	mounted() {
		this.load();
	}
}
</script>
<style scoped>
@import "/css/style.css";
@import "/css/component/component.css";
@import "/css/component/admin/category/component-category-list.css";
@import "/css/component/admin/component-admin.css";

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
}</style>