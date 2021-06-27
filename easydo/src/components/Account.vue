<template>
	<div>
		<div>
			<router-link to="/">
				<input type="button" value="退出" class="button1">
			</router-link>
		</div>
		<div align="center">
			<el-avatar :size="100" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
			<hr class="blank">
		</div>
		<hr>

		<!-- 个人资料展示页 -->
		<div>
			<el-tabs v-model="activeName" @tab-click="handleClick" class="pane">
				<el-tab-pane label="个人资料" name="first">

					<el-form ref="showData" :model="showData" label-width="80px">
						<el-form-item label="姓名">
							<el-input v-model="showData.username" :disabled="true" style="width: 95%;"></el-input>
						</el-form-item>
						<el-form-item label="银行卡号">
							<el-input v-model="showData.studentID" :disabled="true" style="width: 95%;"></el-input>
						</el-form-item>
						<el-form-item label="性别">
							<el-input v-model="showData.sex" :disabled="true" style="width: 95%;"></el-input>
						</el-form-item>
						<el-form-item label="手机号">
							<el-input v-model="showData.phone" :disabled="true" style="width: 95%;"></el-input>
						</el-form-item>
						<el-form-item label="密码">
							<el-input v-model="showData.password" :disabled="true" style="width: 95%;" show-password></el-input>
						</el-form-item>
						<el-form-item label="地址">
							<el-input v-model="showData.classroom" :disabled="true" style="width: 95%;"></el-input>
						</el-form-item>
					</el-form>
				</el-tab-pane>

				<!-- 愿望单 -->
				<el-tab-pane label="愿望单" name="second">
					<el-table :data="forwardItems" border style="width: 100%">
						<el-table-column prop="wishname" label="商品名称" width="120">
						</el-table-column>
						<el-table-column prop="wishprice" label="价格" width="120">
						</el-table-column>
						<el-table-column label="操作" width="180">
							<template slot-scope="scope">
								<el-button size="mini" @click.native.prevent="updateWishDialog(scope.row)">编辑</el-button>
								<el-button size="mini" type="danger" @click.native.prevent="deleteForwordItem(scope.$index, scope.row)">删除</el-button>
							</template>
						</el-table-column>
					</el-table>
					<div align="center">
						<input type="button" value="增加愿望" @click="showAddDialog()" class="button3">
					</div>
				</el-tab-pane>

				<!-- 修改密码 -->
				<el-tab-pane label="修改密码" name="third">
					<el-form :model="tempPass" status-icon :rules="rulesPass" ref="tempPass" label-width="100px" class="demo-ruleForm">
						<el-form-item label="原密码" prop="old">
							<el-input v-model.number="showData.password" :disabled="true" style="width: 95%;" show-password></el-input>
						</el-form-item>
						<el-form-item label="新密码" prop="pass">
							<el-input type="password" v-model="tempPass.pass" autocomplete="off" style="width: 95%;"></el-input>
						</el-form-item>
						<el-form-item label="确认密码" prop="checkPass">
							<el-input type="password" v-model="tempPass.checkPass" autocomplete="off" style="width: 95%;"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="updatePass('tempPass')">提交</el-button>
							<el-button @click="resetPass('tempPass')">重置</el-button>
						</el-form-item>
					</el-form>
				</el-tab-pane>

				<!-- 修改个人资料 -->
				<el-tab-pane label="修改个人资料" name="fourth">
					<el-form ref="showData" :model="showData" label-width="80px">
						<el-form-item label="姓名">
							<el-input v-model="showData.username" style="width: 95%;"></el-input>
						</el-form-item>
						<el-form-item label="银行卡号">
							<el-input v-model="showData.studentID" style="width: 95%;"></el-input>
						</el-form-item>
						<el-form-item label="性别">
							<el-input v-model="showData.sex" :disabled="true" style="width: 95%;"></el-input>
						</el-form-item>
						<el-form-item label="手机号">
							<el-input v-model="showData.phone" style="width: 95%;"></el-input>
						</el-form-item>
						<el-form-item label="地址">
							<el-input v-model="showData.classroom" style="width: 95%;"></el-input>
						</el-form-item>
					</el-form>
					<div align="center">
						<input type="button" value="确定修改" class="button2" @click="updateUserDat()">
					</div>
				</el-tab-pane>
			</el-tabs>
		</div>

		<!-- 添加愿望单 -->
		<div>
			<el-dialog :visible.sync="forwardDialog" :modal="false" :close-on-click-modal="false" :show-close="false">
				<el-form ref="forwardItem" :model="forwardItem" label-width="80px">
					<el-form-item label="商品名称">
						<el-input v-model="forwardItem.wishname"></el-input>
					</el-form-item>
					<el-form-item label="价格">
						<el-input v-model="forwardItem.wishprice"></el-input>
					</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button @click="cancleAddForwad()">取 消</el-button>
					<el-button type="primary" @click="addForwad()">确 定</el-button>
				</div>
			</el-dialog>
		</div>
		
		<!-- 修改愿望单 -->
		<div>
			<el-dialog :visible.sync="updateDialog" :modal="false" :close-on-click-modal="false" :show-close="false">
				<el-form ref="forwardItem" :model="forwardItem" label-width="80px">
					<el-form-item label="商品名称">
						<el-input v-model="forwardItem.wishname"></el-input>
					</el-form-item>
					<el-form-item label="价格">
						<el-input v-model="forwardItem.wishprice"></el-input>
					</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button @click="cancleUpdate()">取 消</el-button>
					<el-button type="primary" @click="updateForwordItem()">确 定</el-button>
				</div>
			</el-dialog>
		</div>
	</div>
</template>

<script>
	export default {
		created(){
			const _this = this
			
			if (sessionStorage.getItem("username") && sessionStorage.getItem("Url")) {
				//获取sessionStorage临时会话中的信息
				_this.showData.username = sessionStorage.getItem("username")
				_this.url = sessionStorage.getItem("Url")
			
				axios.get(_this.url + 'user/message').then(function(resp){
					_this.userData = resp.data
					_this.showData = {
						userID: _this.userData.userID,
						classroom: _this.userData.classroom,
						username: _this.userData.username,
						password: _this.userData.password,
						studentID: _this.userData.studentID,
						sex: '',
						isManager: _this.userData.isManager,
						phone: _this.userData.phone,
					}
					if(_this.userData.sex == 1){
						_this.showData.sex = '男'
					}else if(_this.userData.sex == 0){
						_this.showData.sex = '女'
					}
				})
				
				_this.updateWishs()
			} else {
				_this.$router.replace('/')
			}
		},
		name: "Account",
		data() {
			var validatePass = (rule, value, callback) => {
				if (value === this.showData.password) {
					callback(new Error('新密码与原密码不能相同'));
				} else {
					callback();
				}
			};
			var validatePass2 = (rule, value, callback) => {
				if (value !== this.tempPass.pass) {
					callback(new Error('与新密码不一致!'))
				} else {
					callback()
				}
			};
			return {
				url: '',
				tempPass: {
					pass: '',
					checkPass: '',
				},
				rulesPass: {
					pass: [{
						validator: validatePass,
						trigger: 'blur'
					}],
					checkPass: [{
						validator: validatePass2,
						trigger: 'blur'
					}]
				},
				forwardDialog: false,
				updateDialog: false,
				activeName: 'first',
				deleteData: [],
				
				//前端展示用数据
				showData: {
					classroom: '',
					username: '',
					password: '',
					studentID: '',
					sex: '',
					isManager: '',
					phone: '',
				},
				//后端获取到的用户数据
				userData: {
					userID: '',
					classroom: '',
					username: '',
					password: '',
					studentID: '',
					sex: '',
					isManager: '',
					phone: '',
				},
				//待添加愿望单数据
				forwardItem: {
					wishID: '',
					userID: '',
					wishname: '',
					wishprice: ''
				},
				//愿望单数据
				forwardItems: [{
					wishID: '',
					userID: '',
					wishname: '鸡哥',
					wishprice: 1
				}, ],
			}
		},
		methods: {
			handleClick(tab, event) {
			},
			
			//修改用户资料
			updateUserDat() {
				const _this = this
				_this.userData = {
					userID: _this.showData.userID,
					classroom: _this.showData.classroom,
					username: _this.showData.username,
					password: _this.showData.password,
					studentID: _this.showData.studentID,
					sex: '',
					isManager: _this.showData.isManager,
					phone: _this.showData.phone,
				}
				if(_this.showData.sex === '男')
					_this.userData.sex = '1'
				else if(_this.showData.sex === '女')
					_this.userData.sex = '0'
					
				axios.post(_this.url + 'user/edit', _this.userData).then(function(resp){
				})
			},
			
			//修改密码
			updatePass(formName) {
				const _this = this
				_this.$refs[formName].validate((valid) => {
					if (valid) {
						_this.showData.password = _this.tempPass.pass;
						axios.post(_this.url + 'user/edit', _this.showData).then(function(resp){
							_this.showData = {
								classroom: resp.data.classroom,
								username: resp.data.username,
								password: resp.data.password,
								sex: resp.data.sex,
								studentID: resp.data.studentID,
								phone: resp.data.phone
							}
							_this.tempPass = {
								pass: '',
								checkPass: '',
							}
							_this.$message.success("修改成功，下次登入有效")
						})
					} else {
						_this.$message.error("修改失败，服务器内部错误")
						return false;
					}
				});
			},
			
			//清空修改密码时的数据
			resetPass(formName) {
				this.$refs[formName].resetFields();
			},
			
			//愿望单部分代码实现
			//获取愿望单数据
			updateWishs(){
				const _this = this
				axios.get(_this.url + 'wishlist/findwish').then(function(resp){
					_this.forwardItems = resp.data
				})
			},
			
			//取消添加愿望
			cancleAddForwad(){
				this.forwardDialog = false;
				this.forwardItem = {
					wishID: '',
					userID: '',
					wishname: '',
					wishprice: ''
				}
			},
			
			//添加愿望单
			//将dialog展示出来
			showAddDialog() {
				this.forwardDialog = true
			},
			
			//添加愿望单具体操作
			addForwad(){
				const _this = this
				axios.post(_this.url + 'wishlist/addwish', _this.forwardItem).then(function(resp){
					if(resp.data == true){
						_this.$message.success('添加成功')
						_this.forwardItem = {
							wishID: '',
							userID: '',
							wishname: '',
							wishprice: ''
						}
						//获取愿望单请求
						_this.updateWishs()
					}else{
						_this.$message.error('添加失败，服务器内部错误')
					}
				})
				_this.forwardDialog = false;
			},
			
			//修改愿望单
			//显示更新Dialog
			updateWishDialog(row){
				this.forwardItem = {
					wishID: row.wishID,
					userID: row.userID,
					wishname: row.wishname,
					wishprice: row.wishprice
				}
				this.updateDialog = true;
			},
			
			//取消修改
			cancleUpdate(){
				this.updateDialog = false;
				this.forwardItem = {
					wishID: '',
					userID: '',
					wishname: '',
					wishprice: ''
				}
			},
			
			//更新愿望单的处理逻辑
			updateForwordItem() {
				const _this = this
				axios.post(_this.url + 'wishlist/edit', _this.forwardItem).then(function(resp){
					if(resp.data == true){
						_this.$message.success('修改成功')
						_this.forwardItem = {
							wishID: '',
							userID: '',
							wishname: '',
							wishprice: ''
						}
						_this.updateWishs()
					}else{
						_this.$message.error('添加失败，服务器内部错误')
					}
				})
				_this.updateDialog = false;
			},
			
			//删除愿望单数据
			deleteForwordItem(index, row) {
				const _this = this
				
				//删除愿望单请求
				axios.post(_this.url + 'wishlist/delete', row).then(function(resp){
					if(resp.data == true){
						_this.$message.success('删除成功')
						_this.updateWishs()
					}else{
						_this.$message.error('删除失败，服务器内部错误')
					}
				})
			},
		}
	}
</script>

<style scoped>
	.blank {
		height: 20px;
		border: none;
		margin: 0;
		padding: 0;
		width: 100%;
	}

	.pane {
		margin-left: 30px;
	}

	.button1 {
		width: 150px;
		border-radius: 4px;
		margin-left: 1130px;
		margin-top: 10px;
		display: inline-block;
		vertical-align: middle;
		padding: 12px 24px;
		font-size: 15px;
		font-weight: bold;
		line-height: 24px;
		text-align: center;
		white-space: nowrap;
		cursor: pointer;
		color: #ffffff;
		background-color: #DC143C;
		border: none;
		-webkit-appearance: none;
		transition: all ease-in .1s;
	}

	.button2 {
		width: 150px;
		border-radius: 4px;
		display: inline-block;
		vertical-align: middle;
		padding: 12px 24px;
		font-size: 15px;
		font-weight: bold;
		line-height: 24px;
		text-align: center;
		white-space: nowrap;
		cursor: pointer;
		color: #ffffff;
		background-color: #DC143C;
		border: none;
		-webkit-appearance: none;
		transition: all ease-in .1s;
	}

	.button3 {
		width: 150px;
		border-radius: 4px;
		display: inline-block;
		vertical-align: middle;
		padding: 12px 24px;
		font-size: 15px;
		font-weight: bold;
		line-height: 24px;
		text-align: center;
		white-space: nowrap;
		cursor: pointer;
		color: #ffffff;
		background-color: #1e88e5;
		border: none;
		-webkit-appearance: none;
		transition: all ease-in .1s;
	}
</style>
