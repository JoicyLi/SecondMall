<template>
	<div class="message-center">

		<el-container class="message-center">

			<el-container class="message-left">
				<el-header class="message-header" style="height: 80px">
					<div :key="index" class="message-right-text" v-for="(item, index) in textData" @click="change(index)">
						<div :class='currentIndex==index?"text-background-color":""'>
							{{item}}
						</div>
					</div>
				</el-header>
				<el-main class="left-main">
					<div>
						<!-- 正在聊天列表 -->
						<div v-if="currentIndex==0" :key="index" v-for="(item, index) in communicatingUsers" @click="changeUser(item)" @dblclick="removeUser()">
							<el-card style="text-align: left; border-radius: 0; background-color: #EBEEF5; margin: 1px"
							 shadow="never">
								{{item}}
							</el-card>
						</div>
						<!-- 收藏列表 -->
						<div v-if="currentIndex==1" :key="index" v-for="(item, index) in favorUsers" @click="changeUser(item)" @dblclick="removeUser()">
							<el-card style="text-align: left; border-radius: 0; background-color: #EBEEF5; margin: 1px"
							 shadow="never">
								{{item}}
							</el-card>
						</div>
					<!-- 	<el-card v-if="currentIndex==1" :key="index" v-for="(item, index) in favorUsers" style="text-align: left; border-radius: 0; background-color: #EBEEF5; margin: 1px"
						 shadow="never">
							<div @click="changeUser(item)" @dblclick="removeUser()">
								{{item}}
							</div>
						</el-card> -->
					</div>
				</el-main>
				<el-footer class="message-footer"></el-footer>
			</el-container>

			<el-container class="message-right">
				<el-header class="message-header" style="height: 80px">{{communicatingUserName.username}}</el-header>
				<el-main class="right-main">
					<div class="right-align-left" :key="item.chatID" v-for="item in chatHistorys">
						<el-card shadow="always" style="max-width: 50%; width: max-content;
                     position: relative; left: 1%;text-align: left">
							<div slot="header" class="clearfix">
								<span>{{item.sender}}</span>
							</div>
							<div style="word-wrap: break-all; word-wrap: break-word;">
								{{item.content}}
							</div>
						</el-card>
					</div>
				</el-main>
				<el-footer class="message-right-footer">
					<el-input class="message-input" type="textarea" 
					:autosize="{ minRows: 1, maxRows: 1}" 
					resize='both'
					placeholder="请输入内容" v-model="chating.content">
					</el-input>
					<el-button class="message-submit" type="primary" @click="submintChat()">发送</el-button>
				</el-footer>
			</el-container>

		</el-container>

	</div>
</template>

<script>
	export default {
		//页面初始化函数
		created() {
			const _this = this;
			// this.communicatingUserName = this.communicatingUsers[0].name;
		
			if (sessionStorage.getItem("username") && sessionStorage.getItem("Url")) {
				//获取sessionStorage临时会话中的信息
				_this.username = sessionStorage.getItem("username")
				_this.url = sessionStorage.getItem("Url")
		
				//向后端请求会话列表
				axios.get(_this.url + 'chatroom/users').then(function(resp) {
					if(resp.data.length > 0){
						_this.communicatingUsers = resp.data
						_this.communicatingUserName.username = _this.communicatingUsers[0]
						_this.nowUserContext()	
					}
				})
				
				//向后端请求收藏列表
				_this.updateFavort()
			} else {
				_this.$router.replace('/')
			}
		},
		name: "UserMessage",
		data() {
			return {
				//系统后端地址
				url: '',
				
				//当前登入用户
				username: '',
				
				//聊天记录记录框
				textarea1: '',

				//头部固定选项卡内容
				textData: ['会话', '收藏'],
				//头部选项卡索引
				currentIndex: 0,

				//当前正在交流的用户名称
				communicatingUserName: {
					username: ''
				},

				//正在会话用户列表
				communicatingUsers: [],

				//收藏用户的列表
				favorUsers: [],

				//聊天记录列表
				chatHistorys: [],
				// {
				// 	chatID: '',
				// 	getter: '',
				// 	sender: '',
				// 	content: '',
				// }

				//待发送消息
				chating: {
					getter: '',
					sender: '',
					content: '',
				}
			}
		},
		methods: {
			handleOpen(key, keyPath) {
				console.log(key, keyPath);
			},
			handleClose(key, keyPath) {
				console.log(key, keyPath);
			},
			
			//向后端请求收藏列表
			updateFavort(){
				const _this = this
				axios.get(_this.url + 'collection/find').then(function(resp) {
					if(resp.data.length > 0){
						_this.favorUsers = resp.data
					}
				})
			},
			
			//双击移除用户
			removeUser(){
				const _this = this
				if(_this.currentIndex == 0){
					//对会话列表进行操作
					console.log('模块待完成')
				}else if(_this.currentIndex == 1){
					//对收藏列表进行操作
					console.log('收藏列表')
					axios.post(_this.url + 'collection/delete', _this.communicatingUserName).then(function(resp){
						console.log(resp.data)
						if(resp.data == true){
							_this.$message.success('删除成功')
							_this.updateFavort()
						}else{
							_this.$message.error('服务器内部错误')
						}
					})
				}
				_this.change(_this.currentIndex)
			},
			
			//更改会话、收藏标签
			change(index) {
				this.currentIndex = index;
				if (index == 0){
					this.communicatingUserName.username = this.communicatingUsers[0];
					this.updateCommunicatingUsers(0)
				}
				else if (index == 1)
					this.communicatingUserName.username = this.favorUsers[0];
				this.nowUserContext()
			},
			
			//像后端请求聊天列表
			updateCommunicatingUsers(index){
				const _this = this
				//向后端请求会话列表
				axios.get(_this.url + 'chatroom/users').then(function(resp) {
					_this.communicatingUsers = resp.data
					_this.communicatingUserName.username = _this.communicatingUsers[index]
				})
			},
			
			//更改当前正在交流的用户
			changeUser(name) {
				this.communicatingUserName.username = name;
				this.nowUserContext()
			},
			
			// 请求和某个用户的聊天记录
			nowUserContext(){
				const _this = this
				axios.post(_this.url + 'chatroom/find', _this.communicatingUserName).then(function(resp){
					_this.chatHistorys = resp.data
				})
			},
			
			// 提交输入框数据到后端
			submintChat(){
				const _this = this
				_this.chating.sender = _this.username;
				_this.chating.getter = _this.communicatingUserName.username;
				axios.post(_this.url + 'chatroom/addchat', _this.chating).then(function(resp){
					if(resp.data == true){
						_this.$message.success('发送成功')
						_this.nowUserContext()
					}else{
						_this.$message.error('发送失败，服务器内部错误')
					}
					_this.chating={
						getterID: '',
						senderID: '',
						content: '',
					}
				})
			}
		}
	}
</script>

<style scoped>
	.el-header {
		height: 80px;
	}

	.message-center {
		width: 1300px;
		height: 700px;
		position: absolute;
		overflow-y: hidden;
		background-color: #DCDFE6;
	}

	.message-left {
		width: 300px;
		height: 100%;
		overflow: hidden;
		position: absolute;
		top: 0;
		left: 0;
		bottom: 0;
		/*background-color: #42b983;*/
	}

	.message-right {
		min-width: 600px;
		height: 100%;
		position: absolute;
		top: 0;
		right: 0;
		left: 310px;
		bottom: 0;
		overflow: hidden;
		/*background-color: aqua;*/
	}

	.message-header {
		width: 100%;
		height: 80px;
		line-height: 80px;
		margin: 0;
		padding: 0;
		text-align: center;
		background-color: #409EFF;
		position: absolute;
		top: 0;
	}

	.left-main {
		width: 100%;
		position: absolute;
		top: 80px;
		left: 0;
		bottom: 60px;
		overflow-x: hidden;
		background-color: #FFFFFF;
		padding: 0;
	}

	.right-main {
		width: 100%;
		position: absolute;
		top: 80px;
		right: 0;
		bottom: 60px;
		overflow-x: hidden;
		background-color: #F2F6FC;
	}

	.message-footer {
		width: 100%;
		height: 60px;
		line-height: 60px;
		margin: 0;
		padding: 0;
		text-align: center;
		background-color: #C0C4CC;
		position: absolute;
		bottom: 0;
	}

	.message-right-footer {
		width: 100%;
		height: 60px;
		line-height: 60px;
		margin: 0;
		padding: 0;
		background-color: #C0C4CC;
		position: absolute;
		bottom: 0;
	}

	.message-input {
		width: 840px;
		float: left;
		height: 30px;
		position: relative;
		left: 30px;
		top: 15px;
	}

	.message-submit {
		float: right;
		height: 35px;
		position: relative;
		top: 14px;
		right: 35px;
	}

	.right-align-left {
		width: 100%;
		margin: 10px;
		/*background-color: #42b983;*/
	}

	.message-right-text {
		width: 50%;
		float: left;
	}

	.text-background-color {
		background-color: deepskyblue;
	}
</style>
