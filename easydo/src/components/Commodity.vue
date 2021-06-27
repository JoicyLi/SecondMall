<template>
	<div id="commodity-view">

		<!-- 标签页，展示数据依据标签页改变，默认进入商城，绑定数据activeIndex-->
		<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
			<el-menu-item index="1" @click="shoppingMall()">商城</el-menu-item>
			<el-menu-item index="2" @click="myItems()">我的商品</el-menu-item>
			<el-menu-item index="3" @click="releaseItem()">发布商品</el-menu-item>
			<el-menu-item @click="help()">联系管理员</el-menu-item>
		</el-menu>

		<!-- 搜索栏 -->
		<el-row style="height:100px;background:#eeeeee" type="flex" justify="center">
			<el-col :span="15">
				<br>
				<el-input v-model="input" placeholder="请输入内容" prefix-icon="el-icon-search">
					<el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
				</el-input>
				<br>
			</el-col>
		</el-row>


		<!-- 商品详细信息，绑定数据dialogForVisible控制dialog标签的显隐性 -->
		<el-dialog title="商品详细信息" :visible.sync="dialogFormVisible" :modal="false" :close-on-click-modal="false">

			<el-image style="width: 200px; height: 200px" :src="'data:image/jpg;base64,'+form.image" fit="fill">
			</el-image>
			<!-- 商品详细信息表单，绑定数据form -->
			<el-form ref="form" :model="form" label-width="80px">

				<el-form-item label="商品名称">
					<el-input v-model="form.goodname" :disabled="true"></el-input>
				</el-form-item>

				<el-form-item label="商家">
					<el-input v-model="form.ownername" :disabled="true"></el-input>
				</el-form-item>

				<el-form-item label="商品价格">
					<el-input v-model="form.price" :disabled="true"></el-input>
				</el-form-item>

				<el-form-item label="详细信息">
					<el-input type="textarea" v-model="form.description" :disabled="true"></el-input>
				</el-form-item>

			</el-form>

			<div slot="footer">
				<el-button @click="contactSeller()">联系商家</el-button>
				<el-button type="warning" @click="collectionSeller()">收藏商家</el-button>
				<el-button type="primary" @click="addToCart()">加入购物车</el-button>
			</div>

		</el-dialog>

		<!-- 	发布商品 -->
		<el-dialog title="填写商品详细信息" :visible.sync="dialogFormVisible2" :modal="false" :close-on-click-modal="false" :show-close="false">

			<!-- 图片上传部分 -->
			<template>
				<div>
					<el-upload ref="imgBroadcastUpload" :auto-upload="false" multiple :file-list="diaLogForm.imgBroadcastList"
					 list-type="picture-card" :on-change="imgBroadcastChange" :on-remove="imgBroadcastRemove" accept="image/jpg,image/png,image/jpeg"
					 action="">
						<i class="el-icon-plus"></i>
						<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过70k</div>
					</el-upload>
					<!-- <el-button @click="submitDialogData()">submitData</el-button> -->
				</div>
			</template>

			<!-- 商品详细信息 -->
			<el-form ref="form" :model="form2" label-width="80px">

				<el-form-item label="商品名称">
					<el-input v-model.lazy="form2.goodname"></el-input>
				</el-form-item>

				<el-form-item label="商家">
					<el-input v-model.lazy="form2.ownername" :disabled="true"></el-input>
				</el-form-item>

				<el-form-item label="商品价格">
					<el-input v-model.lazy="form2.price"></el-input>
				</el-form-item>

				<el-form-item label="详细信息">
					<el-input type="textarea" v-model.lazy="form2.description"></el-input>
				</el-form-item>

			</el-form>

			<div slot="footer">
				<el-button type="text" @click="cancelToRelease()">取消</el-button>
				<el-button type="primary" @click="confirmToRelease()">确认发布</el-button>
			</div>

		</el-dialog>

		<!-- 展示商品模块，绑定数据itemToShow -->
		<el-row style="background:#eeeeee" type="flex" justify="center">
			<el-col :span="15">
				<el-card shadow="never" v-for="(item, index) in itemToShow" :key="index">
					<h2>{{item.goodname}}</h2>
					<el-image style="width: 200px; height: 200px" :src="'data:image/jpg;base64,'+item.image" fit="fill"></el-image>
					<p>{{item.description}}</p>

					<el-button size="small" @click="showItem(item)" v-if="isMyItems==false">浏览商品详细信息 >></el-button>
					<el-button size="small" @click="modifyInformation(item)" type="primary" v-if="isMyItems==true">修改商品信息</el-button>
					<el-button size="small" @click="offTheShelf(item)" type="danger" v-if="isMyItems==true">下架商品</el-button>

				</el-card>
			</el-col>
		</el-row>
		
		<!-- 修改商品，绑定数据changeDialogVisible控制dialog标签的显隐性 -->
		<el-dialog title="商品详细信息" :visible.sync="changeDialogVisible" :modal="false" :close-on-click-modal="false" :show-close="false">
		
			<el-image style="width: 200px; height: 200px" :src="'data:image/jpg;base64,'+form.image" fit="fill">
			</el-image>
			<!-- 商品详细信息表单，绑定数据form -->
			<el-form ref="form" :model="form" label-width="80px">
		
				<el-form-item label="商品名称">
					<el-input v-model="form.goodname"  ></el-input>
				</el-form-item>
		
				<el-form-item label="商家">
					<el-input v-model="form.ownername" :disabled="true"></el-input>
				</el-form-item>
		
				<el-form-item label="商品价格">
					<el-input v-model="form.price" ></el-input>
				</el-form-item>
		
				<el-form-item label="详细信息">
					<el-input type="textarea" v-model="form.description"  ></el-input>
				</el-form-item>
		
			</el-form> `
		
			<div slot="footer">
				<el-button type="text" @click="cancelChange()">取消</el-button>
				<el-button type="primary" @click="upDataComodity()">确认</el-button>
			</div>
		
		</el-dialog>

		<!-- 底栏 -->
		<el-row style="background:#eeeeee" type="flex" justify="center">
			<el-col :span="15">
				<el-divider></el-divider>
				<p>© 2020 Company, Inc.</p>
			</el-col>
		</el-row>

	</div>
</template>

<script src="js/vue.js" type="text/javascript" charset="utf-8"></script>
<script>
	import Vue from 'vue';
	import {
		uploadImgToBase64
	} from '@/utils' // 导入本地图片转base64的方法
	export default {
		name: 'Commodity',

		//页面初始化函数
		created() {
			const _this = this;

			if (sessionStorage.getItem("username") && sessionStorage.getItem("Url")) {
				//获取sessionStorage临时会话中的信息
				_this.username = sessionStorage.getItem("username")
				_this.url = sessionStorage.getItem("Url")

				//像后端请求商品信息
				axios.get(_this.url + '/good/release').then(function(resp) {
					_this.items = resp.data;
					_this.itemToShow = _this.items;
				})
			} else {
				_this.$router.replace('/')
			}
		},
		data() {
			return {
				//当前已登录账号的用户名
				username: '',
				url: '',
				isMyItems: false,
				activeIndex: '1',
				diaLogForm: {
					goodsName: '', // 商品名称字段
					imgBroadcastList: [], // 储存选中的图片列表
					imgsStr: '' // 后端需要的多张图base64字符串,分割
				},
				dialogFormVisible: false, //商品详细信息dialog显隐性控制
				dialogFormVisible2: false, //发布商品
				changeDialogVisible: false,//修改商品信息dialog
				dialogVisible: false,
				input: '', //搜索栏
				searchedItem: [], //搜索到的商品
				ownItems: [], //自己发布的商品
				tempIndex: '1',
				items: [{
					goodID: '1',
					goodname: '商品1',
					price: '100元',
					ownername: 'user',
					goodstate: '正在进行',
					description: '这是商品1',
					image: '',
					amount: '1',
				}, ],
				itemToShow: [{
					goodID: '1',
					goodname: '商品1',
					price: '100元',
					ownername: 'user',
					goodstate: '正在进行',
					description: '这是商品1',
					image: '',
					amount: '1',
				}, ],
				// url: '@/assets/Main.jpg',
				form: {
					goodID: '',
					goodname: '',
					price: '',
					ownername: '',
					goodstate: '',
					description: '',
					image: '',
					amount: '',
				},
				form2: {
					goodID: '',
					goodname: '',
					price: '',
					ownername: '',
					goodstate: '',
					description: '',
					image: '',
					amount: '',
				},
				tempCommodityData: {
					goodID: '',
					goodname: '',
					price: '',
					ownername: '',
					goodstate: '',
					description: '',
					image: '',
					amount: '',
				}
			};
		},
		methods: {
			handleSelect(key, keyPath) {
				this.tempIndex = this.activeIndex.toString()
				this.activeIndex = key
			},
			
			//更新现有商品数据
			updateItems(){
				const _this = this
				axios.get(_this.url + '/good/release').then(function(resp) {
					_this.items = resp.data;
					_this.itemToShow = _this.items;
				})
			},
			
			// 图片选择后 保存在 diaLogForm.imgBroadcastList 对象中
			imgBroadcastChange(file, fileList) {
				const isLt2M = file.size / 1024 / 1024 < 2 // 上传头像图片大小不能超过 2MB
				if (!isLt2M) {
					this.diaLogForm.imgBroadcastList = fileList.filter(v => v.uid !== file.uid)
					this.$message.error('图片选择失败，每张图片大小不能超过 2MB,请重新选择!')
				} else {
					this.diaLogForm.imgBroadcastList.push(file)
					this.$message.error('上传成功')
				}
			},
			// 有图片移除后 触发
			imgBroadcastRemove(file, fileList) {
				this.diaLogForm.imgBroadcastList = fileList
				this.$message.error('移除成功')
			},
			
			shoppingMall() { //商城
				this.itemToShow = this.items
				this.isMyItems = false;
			},

			//我的商品
			myItems() {
				this.isMyItems = true;
				this.ownItems = []
				for (var i = 0; i < this.items.length; i++) {
					if (this.items[i].ownername == this.username) {
						this.ownItems.push({
							goodID: this.items[i].goodID,
							goodname: this.items[i].goodname,
							price: this.items[i].price,
							ownername: this.items[i].ownername,
							goodstate: this.items[i].goodstate,
							description: this.items[i].description,
							image: this.items[i].image,
							amount: this.items[i].amount,
						})
					}
					this.itemToShow = this.ownItems
				}
			},

			search() { //按商品名模糊查询
				this.itemToShow = []
				this.searchedItem = []
				for (var i = 0; i < this.items.length; i++) {
					if (this.items[i].goodname.includes(this.input)) {
						this.searchedItem.push({
							goodID: this.items[i].goodID,
							goodname: this.items[i].goodname,
							price: this.items[i].price,
							ownername: this.items[i].ownername,
							goodstate: this.items[i].goodstate,
							description: this.items[i].description,
							image: this.items[i].image,
							amount: this.items[i].amount,
						})
					}
					this.itemToShow = this.searchedItem
				}
			},
			
			showItem: function(item) { //展示商品详细信息
				this.form.goodID = item.goodID
				this.form.goodname = item.goodname
				this.form.ownername = item.ownername
				this.form.image = item.image
				this.form.goodstate = item.goodstate
				this.form.amount = item.amount
				this.form.price = item.price
				this.form.description = item.description
				this.dialogFormVisible = true
				console.log(this.form)
			},
			
			modifyInformation(item) { //修改商品信息
				// console.log(item)
				this.changeDialogVisible = true;
				this.form = {
					goodID: item.goodID,
					goodname: item.goodname,
					ownername: item.ownername,
					image: item.image,
					goodstate: item.goodstate,
					amount: item.amount,
					price: item.price,
					description: item.description,
				}
				console.log(this.form)
			},
			
			//取消修改商品
			cancelChange(){
				this.changeDialogVisible = false;
				this.form = {
					goodID: '',
					goodname: '',
					price: '',
					ownername: '',
					goodstate: '',
					description: '',
					image: '',
					amount: '',
				}
			},
			
			//确认修改商品
			upDataComodity(){
				const _this = this;
				console.log(_this.form)
				axios.post(_this.url + 'good/edit', _this.form).then(function(resp){
					if(resp.data == true){
						_this.$message.success('修改成功了')
						axios.get(_this.url + '/good/release').then(function(resp) {
							_this.items = resp.data;
							_this.itemToShow = _this.items;
							_this.myItems()
						})
					}else{
						_this.$message.error('修改失败，服务器内部错误')
					}
					_this.cancelChange()
				})
			},
			
			offTheShelf(item) { //下架商品
				const _this = this
				axios.post(_this.url + 'good/delete', item).then(function(resp){
					if(resp.data == true){
						_this.$message.success('下架成功')
						// _this.updateItems()
						// _this.myItems()
						axios.get(_this.url + '/good/release').then(function(resp) {
							_this.items = resp.data;
							_this.itemToShow = _this.items;
							_this.myItems()
						})
					}else{
						_this.$message.error('下架失败')
					}
				})
			},
			help() {
				const h = this.$createElement;
				
				this.$notify({
					title: '联系管理员',
					message: h('i', {
						style: 'color: teal'
					}, '8:00--12:00为上班时间,欢迎致电0123456789联系管理员')
				});
			},

			//联系卖家
			contactSeller() {
				this.$notify({
					title: '需要联系的卖家名',
					message: this.form.ownername,
					duration: 0
				});
				this.$router.push('/market/message')
				this.dialogFormVisible = false
			},
			
			//收藏商家
			collectionSeller(){
				const _this = this
				console.log('商品信息')
				console.log(_this.form)
				axios.post(_this.url + 'collection/addcollection', _this.form).then(function(resp){
					console.log('收藏返回')
					console.log(resp.data)
					if(resp.data == true){
						_this.$message.success('收藏成功')
					}else{
						_this.$message.error('收藏失败，服务器内部错误')
					}
				})
			},

			//添加到购物车的事件处理
			addToCart() {
				const _this = this;
				console.log(_this.form);
				axios.post(_this.url + 'shoppingcart/addshoppingcart', _this.form).then(function(resp) {
					// console.log(resp)
					if (resp.data == true) {
						// console.log("success");
						_this.$message.success('成功添加到购物车')
					} else {
						// console.log("fail");
						_this.$message.error('添加失败，服务器内部错误')
					}
				})
				this.dialogFormVisible = false
			},

			//取消发布商品
			cancelToRelease() {
				this.form2 = {
					goodID: '',
					goodname: '',
					ownername: '',
					image: '',
					goodstate: '',
					amount: '',
					price: '',
					description: '',
				}
				this.diaLogForm = {
					goodsName: '', 
					imgBroadcastList: [], 
					imgsStr: '' 
				}
				this.activeIndex = this.tempIndex.toString()
				this.dialogFormVisible2 = false
			},
			
			//发布商品dialog
			releaseItem() {
				this.form2.ownername = this.username;
				this.dialogFormVisible2 = true
			},

			//发布商品处理事件
			async confirmToRelease() {
				const _this = this;
				
				const imgBroadcastListBase64 = []
				
				const filePromises = this.diaLogForm.imgBroadcastList.map(async file => {
					const response = await uploadImgToBase64(file.raw)
					return response.result.replace(/.*;base64,/, '') // 去掉data:image/jpeg;base64,
				})
				
				for (const textPromise of filePromises) {
					imgBroadcastListBase64.push(await textPromise)
				}
				this.form2.image = imgBroadcastListBase64.join()
				
				_this.form2.amount = 1;
				axios.post(_this.url + '/good/add', _this.form2).then(function(resp) {
					if (resp.data == true) {
						_this.$message.success("商品发布成功，请等待管理员审核");
						axios.get(_this.url + '/good/release').then(function(resp) {
							_this.items = resp.data;
							_this.itemToShow = _this.items;
							if(_this.tempIndex === '1'){
								_this.shoppingMall()
							}else if(_this.tempIndex === '2'){
								_this.myItems()
							}
							_this.activeIndex = _this.tempIndex.toString()
						})
					} else {
						_this.$message.error("发布商品失败，服务器内部错误");
					}
					_this.form2 = {
						goodID: '',
						goodname: '',
						price: '',
						ownername: '',
						goodstate: '',
						description: '',
						image: '',
						amount: '',
					}
					_this.diaLogForm = {
						goodsName: '', 
						imgBroadcastList: [], 
						imgsStr: '' 
					}
				})
				this.dialogFormVisible2 = false
			}


		}
	}
</script>

<style>
	#commodity-view {
		width: 1300px;
		height: 700px;
		overflow-x: hidden;
		overflow-y: auto;
	}

	.el-card {
		background: #fbfbfb;
		width: 300px;
		display: inline-block;
		margin: 0.3em;
		border-radius: 20px;
	}

	.grid-content {
		/* background: rgb(14, 214, 131); */
		border-radius: 4px;
		min-height: 80px;
	}

	.text {
		font-size: 14px;
	}

	.item {
		padding: 18px 0;
	}

	.header-h2 {
		color: #d1d1d1;

	}

	.header-h2:hover {
		color: #fff;

	}

	.row-header {
		background: #409eff;
	}

	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>
