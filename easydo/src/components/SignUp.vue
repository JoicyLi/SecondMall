<template>
	<div id="login_container">
		<form method="post">
			<div style="font-size:60px;">
				注册
			</div>
			<hr color="#1e88e5">
			<br>

			<div id="login_name">
				<input name="user_name" class="input" type="text" placeholder="用户名" v-model.trim.lazy="signUpData.username" value
				 required="required" autofocus>
			</div>
			<hr class="blank">
			<div id="login_password">
				<input name="user_password" class="input" type="password" placeholder="密码" v-model.trim.lazy="signUpData.password"
				 required="required">
			</div>
			<hr class="blank">
			<div id="login_newpassword">
				<input name="user_newpassword" class="input" type="password" placeholder="确认密码" v-model.trim.lazy="signUpData.newpassword"
				 required="required">
			</div>
			<hr class="blank">
			<div id="login_classroom">
				<input name="user_sex" class="input" type="text" placeholder="地址" v-model.trim.lazy="signUpData.classroom" required="required">
			</div>
			<hr class="blank">

			<div id="login_number">
				<input name="user_number" class="input" type="text" placeholder="银行卡号" v-model.trim="signUpData.studentID" required="required">
			</div>
			<hr class="blank">
			<div id="login_phonenumber">
				<input name="user_phonenumber" class="input" type="text" placeholder="手机号" v-model.trim="signUpData.phone" required="required">
			</div>
			<hr class="blank">
			<div id="login_sex">
				<!-- <input name="user_sex" class="input" type="text" placeholder="性别" v-model.trim="signUpData.sex" required="required"> -->
				<el-radio v-model="signUpData.sex" label="0">女</el-radio>
				<el-radio v-model="signUpData.sex" label="1">男</el-radio>
			</div>
			<hr class="blank">

			<input type="button" value="注册" class="button" @click="doSign">
			<hr class="blank">

			已有账号 ?<el-link type="primary" @click="goTo('/')">登录</el-link>
			<hr class="blank">
		</form>
	</div>
</template>

<script>
	export default {
		created() {
			this.url = sessionStorage.getItem("Url")
		},
		name: 'sighup',
		data() {
			return {
				url: '',
				signUpData: {
					username: '',
					password: '',
					classroom: '',
					phone: '',
					newpassword: '',
					sex: '0',
					studentID: ''
				},
			}
		},
		methods: {
			goTo(path) {
				const _this = this;
				_this.$router.replace({
					path: path,
				})
			},
			doSign() {
				const _this = this;
				
				axios.post(_this.url + 'user/regist', _this.signUpData).then(function(resp){
					if(resp.data == true){
						_this.$message.success("注册成功，请去登入");
						_this.signUpData = {
							username: '',
							password: '',
							classroom: '',
							phone: '',
							newpassword: '',
							sex: '',
							studentID: ''
						}
					}else{
						_this.$message.error("注册失败")
					}
				})
			},
		}
	}
</script>

<style scoped>
	#login_container {
		width: 430px;
		height: 700px;
		display: table-cell;
		vertical-align: middle;
		text-align: center;
		background-color: #fff;
		border-radius: 5px 5px 5px 5px;
	}

	.input {
		border: 1px solid #DCDEE0;
		vertical-align: middle;
		border-radius: 3px;
		height: 50px;
		padding: 0 16px;
		font-size: 14px;
		color: #555555;
		width: 250px;
		background-color: #ffffff;
		font-family: "Raleway";
		font-weight: inherit;
	}

	.blank {
		height: 18px;
		border: none;
		margin: 0;
		padding: 0;
		width: 100%;
	}

	.button {
		width: 250px;
		display: inline-block;
		vertical-align: middle;
		padding: 12px 24px;
		margin: 0;
		font-size: 15px;
		font-weight: bold;
		line-height: 24px;
		text-align: center;
		white-space: nowrap;
		cursor: pointer;
		color: #ffffff;
		background-color: #1e88e5;
		border-radius: 3px;
		border: none;
		-webkit-appearance: none;
		transition: all ease-in .1s;
	}
</style>
