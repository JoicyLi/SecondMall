<template>
	<div class="manager">
		<!-- 标签页，同商城 -->
		<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
			<el-menu-item index="1" @click="updateWiatItems()">待审核商品</el-menu-item>
			<el-menu-item index="2">待审核用户</el-menu-item>
		</el-menu>

		<el-container style="height: 100; width: 100%;">
			<el-container>
				<el-main>

					<!-- 商品审核界面 -->
					<div v-if="activeIndex==='1'">
						<el-table border :data="goodsToBeApproved" tooltip-effect="dark" style="width: 100%; text-align: center; min-width: 1000px">
							<el-table-column label="商品图片" width="100">
								<template v-if="activeIndex==='1'" slot-scope="scope">
									<img :src="'data:image/jpg;base64,'+scope.row.image" height="60" width="60" style="background-color: #409EFF" />
								</template>
							</el-table-column>

							<el-table-column v-if="activeIndex==='1'" prop="goodname" label="商品名称" width="120">
								<template slot-scope="scope" v-if="activeIndex==='1'">
									<!-- 商品名单机事件处理 -->
									<p @click="showDialog(scope.row)">{{scope.row.goodname}}</p>
								</template>
							</el-table-column>
							<el-table-column v-if="activeIndex==='1'" prop="ownername" label="商家" width="120">
							</el-table-column>
							<el-table-column v-if="activeIndex==='1'" prop="price" label="价格" width="120px">
							</el-table-column>
							<el-table-column v-if="activeIndex==='1'" prop="description" label="商品描述" show-overflow-tooltip>
							</el-table-column>
							<el-table-column v-if="activeIndex==='1'" label="操作" width="200px">
								<template slot-scope="scope1" v-if="activeIndex==='1'">
									<el-button size="mini" @click.native.prevent="changeItemState(scope1.row, '已发布')">通过</el-button>
									<el-button size="mini" type="danger" @click.native.prevent="changeItemState(scope1.row, '已下架')">不通过</el-button>
								</template>
							</el-table-column>
						</el-table>
					</div>

					<div v-if="activeIndex==='2'">
						<el-table v-if="activeIndex==='2'" border :data="usersToBeAudited" tooltip-effect="dark" style="width: 100%; text-align: center; min-width: 1000px">
							<el-table-column v-if="activeIndex==='2'" prop="classroom" label="班级" width="120">
							</el-table-column>
							<el-table-column v-if="activeIndex==='2'" prop="num" label="学号" width="120">
							</el-table-column>
							<el-table-column v-if="activeIndex==='2'" prop="name" label="用户名" width="120">
							</el-table-column>
							<el-table-column v-if="activeIndex==='2'" prop="sex" label="性别" width="80">
							</el-table-column>
							<el-table-column v-if="activeIndex==='2'" prop="number" label="手机号" width="120">
							</el-table-column>
							<el-table-column v-if="activeIndex==='2'" prop="content" label="举报原因">
							</el-table-column>

							<el-table-column v-if="activeIndex==='2'" label="操作" width="200">
								<template slot-scope="scope2" v-if="activeIndex==='2'">
									<el-button size="mini" @click.native.prevent="passUser(scope2.$index, usersToBeAudited)">举报通过</el-button>
									<el-button size="mini" type="danger" @click.native.prevent="unpassUser(scope2.$index, usersToBeAudited)">不通过</el-button>
								</template>
							</el-table-column>
						</el-table>
					</div>

				</el-main>
			</el-container>
		</el-container>
		
		<!-- 商品详情页面卡 -->
		<el-dialog title="商品详细信息" :visible.sync="commodityDialogVisible" :modal="false">
		
			<el-image style="width: 200px; height: 200px" :src="'data:image/jpg;base64,'+commodity.image">
			</el-image>
			<el-form ref="form" :model="commodity" label-width="80px">
		
				<el-form-item label="商品名称">
					<el-input v-model="commodity.goodname" :disabled="true"></el-input>
				</el-form-item>
		
				<el-form-item label="商品价格">
					<el-input v-model="commodity.price" :disabled="true"></el-input>
				</el-form-item>
		
				<el-form-item label="详细信息">
					<el-input type="textarea" v-model="commodity.description" :disabled="true"></el-input>
				</el-form-item>
		
			</el-form>
		
		</el-dialog>
	</div>
</template>





<script>
	export default {
		created() {
			const _this = this;
			// this.communicatingUserName = this.communicatingUsers[0].name;
		
			if (sessionStorage.getItem("username") && sessionStorage.getItem("Url")) {
				//获取sessionStorage临时会话中的信息
				_this.username = sessionStorage.getItem("username")
				_this.url = sessionStorage.getItem("Url")
				
				//向后端请求待审批商品列表
				_this.updateWiatItems()
				
				// //向后端请求待审批用户列表
				// axios.post(_this.url + '').then(function(resp){
				// 	console.log(resp.data)
				// })
				
			} else {
				_this.$router.replace('/')
			}
		},
		data() {
			return {
				activeIndex: '1',
				commodityDialogVisible: false,
				commodity: '',
				goodsToBeApproved: [{
						goodID: '1',
						goodname: '商品1',
						price: '100元',
						ownername: 'user',
						goodState: '待审核',
						description: '这是商品1',
						image: '',
						amount: '1',
					},
				],
				usersToBeAudited: [{
						name: '霞姐',
						number: "1111111",
						sex: "女",
						classroom: "1702",
						num: "2017111111",
					},
				],
			}
		},
		methods: {
			handleSelect(key, keyPath) {
				this.activeIndex = key
				console.log(this.activeIndex)
			},
			
			//更新待审核商品列表
			updateWiatItems(){
				//向后端请求待审批商品列表
				const  _this = this
				axios.get(_this.url + 'good/review').then(function(resp){
					_this.goodsToBeApproved = resp.data
				})
			},
			
			//展示商品详情页
			showDialog(row) {
				this.commodityDialogVisible = true;
				this.commodity = row;
			},
			
			//修改商品状态
			changeItemState(row, strState){
				const _this = this
				_this.$confirm('此操作将通过商品审核, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					row.goodstate = strState
					axios.post(_this.url + 'good/changestate', row).then(function(resp){
						console.log(resp.data)
						if(resp.data == true){
							_this.$message({
								type: 'success',
								message: '操作成功'
							});
							_this.updateWiatItems()
						}else{
							_this.$message.error('操作失败，服务器内部错误')
						}
					})
				}).catch(() => { //取消审核
					_this.$message({
						type: 'info',
						message: '已取消'
					});
				});
			},
			
			passUser(index, row) { //用户举报通过
				// this.$message(row[index].name);
				this.$confirm('此操作将通过用户举报并移除被举报用户数据, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$message(row[index].name); //举报通过
					this.$message({
						type: 'success',
						message: '举报通过'
					});
				}).catch(() => { //取消
					this.$message({
						type: 'info',
						message: '已取消'
					});
				});


			},
			
			unpassUser(index, row) { //用户举报未通过
				// this.$message(row[index].name);
				this.$confirm('此操作将撤销针对该用户的举报, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$message(row[index].name); //举报通过
					this.$message({
						type: 'success',
						message: '用户举报未通过'
					});
				}).catch(() => { //取消
					this.$message({
						type: 'info',
						message: '已取消'
					});
				});

			},
		},
	};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
	.manager {
		width: 1300px;
		height: 700px;
		padding: 0;
		position: absolute;
		overflow-y: hidden;
		/* 		background-color: #DCDFE6; */
	}

	.pane {
		margin-left: 30px;
	}

	h3 {
		margin: 40px 0 0;
	}

	ul {
		list-style-type: none;
		padding: 0;
	}

	li {
		display: inline-block;
		margin: 0 10px;
	}

	a {
		color: #42b983;
	}

	.el-header {
		background-color: #B3C0D1;
		color: #333;
		line-height: 60px;
	}

	.el-aside {
		color: #333;
	}
</style>
