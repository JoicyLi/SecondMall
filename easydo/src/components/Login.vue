<template>
	<div id="login_container">
		<form method="post">
			<div style="font-size:60px;">
				登录
			</div>
			<hr>
			<br><br>
			
			<!-- 登入框，绑定数据loginData -->
			<div id="login_name">
				<input name="user_name" class="input" type="text" placeholder="用户名" v-model.lazy="loginData.username" value required="required"
				 autofocus>
			</div>
			<hr class="blank">
			<div id="login_password">
				<input name="user_password" class="input" type="password" placeholder="密码" v-model.lazy="loginData.password" required="required">
			</div>

			<br>
			<!-- 管理员权限 -->
			<div>
				<el-radio v-model="loginData.ismanager" label="0">普通用户</el-radio>
				<el-radio v-model="loginData.ismanager" label="1">管理员</el-radio>
			</div>
			<br>
			<hr class="blank">

			<!-- 登入事件绑定按键 -->
			<input type="button" value="登录" class="button" @click="doLogin">

			<!-- 注册、忘记密码待完成 -->
			<hr class="blank">
			<hr class="blank">
			没有账号 ?<el-link type="primary" @click="goTo('/signup', 'Singup')">点我注册</el-link>
			<hr class="blank">

			<hr class="blank">
			<el-link type="info" @click="goTo('/signup', 'Singup')">忘记密码</el-link>
			<hr class="blank">
		</form>
	</div>
</template>

<script>
	export default {
		created() {
			sessionStorage.clear()
			sessionStorage.setItem("Url", this.urlHead + this.ip + this.urlEnd)
		},
		name: 'login',
		data() {
			return {
				//服务器ip
				ip: '10.3.97.98',
				//请求头部
				urlHead: 'http://',
				//请求尾部
				urlEnd: ':8181/',
				//用户登入数据
				loginData: {
					username: '',
					password: '',
					ismanager: '0',
				}
			}
		},
		methods: {
			goTo(path, name) {
				const _this = this;
				_this.$router.replace({
					path: path,
					name: name,
				})
			},
			//登入事件处理
			doLogin() {
				const _this = this;
				
				//测试请求链接
				// console.log(_this.urlHead + _this.ip + _this.urlEnd);
				console.log(_this.loginData)
				
				//发送校验请求
				axios.post(_this.urlHead + _this.ip + _this.urlEnd + 'user/login', _this.loginData).then(function(resp) {
					
					//根据请求数据处理逻辑
					if (resp.data == true) {
						if(_this.loginData.ismanager == 0){
							_this.$router.replace({
								//目的路径
								path: '/market',
								name: 'Market',
							});
						}else if(_this.loginData.ismanager == 1){
							_this.$router.replace({
								path: '/controller'
							})
						}
						// sessionStorage.setItem("username", _this.loginData.username);
						sessionStorage.setItem("username", _this.loginData.username);
						// document.cookie="username"
					} else {
						//错误提示信息
						_this.$message.error('账户或密码错误，或者用户身份错误');
					}
				})
			},
		}
	}
</script>

<!-- CSS 样式 -->
<style scoped>
	#login_container {
		width: 365px;
		height: 531px;
		display: table-cell;
		vertical-align: middle;
		text-align: center;
		background-color: #fff;
		border-radius: 5px 5px 5px 5px;
	}

	#logo {
		display: block;
		margin: 0 auto 30px auto;
		background-size: auto 89px;
		background-repeat: no-repeat;
		/*background-image: url("logo.png");*/
		border-radius: 5px;
		height: 89px;
	}

	input {
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
		height: 20px;
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
