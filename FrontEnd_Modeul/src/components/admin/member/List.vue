<script>
export default {
	data() {
		return {
			list: '',
			openModal: false,
			openModal2: false,
			selectedId: ''
		}
	},
	methods: {
		async load() {

			const response = await fetch(`${this.$store.state.host}/api/member/list`);
			const result = await response.json();
			this.list = result;
		},

		deleteBtnHandler(e) {
			this.openModal = !this.openModal
			this.selectedId = e.target.value
		},

		async removeMember(memberId) {

			var myHeaders = new Headers();
			myHeaders.append("Content-Type", "application/json");

			var raw = JSON.stringify({
				"id": this.selectedId,
			});
			var requestOptions = {
				method: 'DELETE',
				headers: myHeaders,
				body: raw,
				redirect: 'follow'
			};

			const response = await fetch(`${this.$store.state.host}/api/member/delete`, requestOptions)
			const result = await response.json();

			if (result == 1) {
				this.openModal = !this.openModal
				this.openModal2 = !this.openModal2
				this.load();
			}
			else {
				alert("실패")
				this.openModal = !this.openModal
			}
		}
	},
	mounted() {
		this.load();
	}
}

</script>

<template>
	<!-- 모달 배경 -->
	<div v-if="openModal" class="black-bg">
		<!-- 취소 확인 모달 -->
		<div class="delete-box">
			<div class="delete-box-1">정말로 삭제하시겠습니까?</div>
			<div class="delete-box-2">
				<div @click="removeMember(e)" class="delete-box-3">삭제</div>
				<div @click="deleteBtnHandler" class="delete-box-4">취소</div>
			</div>
		</div>
	</div>

	<div v-if="openModal2" class="black-bg">
		<div class="delete-box">
			<div class="delete-box-1">삭제되었습니다.</div>
			<div class="delete-box-2">
				<div @click="openModal2 = !openModal2" class="delete-box-5">확인</div>
			</div>
		</div>
	</div>


	<main>
		<div class="admin-header">
			<span>회원 관리</span>
		</div>
		<table>
			<thead>
				<tr>
					<th>
						ID
					</th>
					<th>
						이름
					</th>
					<th>
						닉네임
					</th>
					<th>
						EMAIL
					</th>
					<th>
						
					</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="item in list" :key="item.name">
					<td >{{ item.uid }}</td>
					<td >{{ item.name }}</td>
					<td >{{ item.nickname }}</td>
					<td >{{ item.email }}</td>
					<td> <button @click="deleteBtnHandler" :value="item.id" class="icon-admin3 icon-delete">삭제</button> </td>
				</tr>
			</tbody>
		</table>
	</main>
</template>


<style scoped>
@import "/css/component/component.css";
@import "/css/component/admin/component-admin.css";
@import "/css/component/admin/member/component-member-list.css";

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
</style>