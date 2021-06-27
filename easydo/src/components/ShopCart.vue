<template>

	<div class="shop-cart">
		<el-container class="shop-cart">
			<el-header class="cart-header" style="height: 80px; text-align: center;">
				<p class="el-icon-shopping-cart-2">我的购物车</p>
			</el-header>
			<el-main class="cart-main">

				<!-- 购物车信息展示块，绑定数据CommodityDate, handleSelectionChange事件处理多选事件 -->
				<el-table border ref="multipleTable" :data="CommodityDate" tooltip-effect="dark" style="width: 100%; text-align: center; min-width: 1000px"
				 @selection-change="handleSelectionChange">
					<el-table-column type="selection" width="55">
					</el-table-column>
					<el-table-column label="商品图片" width="100">
						<template slot-scope="scope">
							<img :src="'data:image/jpg;base64,'+scope.row.image" height="60" width="60" style="background-color: #409EFF" />
						</template>
					</el-table-column>
					<el-table-column prop="goodname" label="商品名称" width="120">
						<template slot-scope="scope">
							<!-- 商品名单机事件处理 -->
							<p @click="showDialog(scope.row)">{{scope.row.goodname}}</p>
						</template>
					</el-table-column>
					<el-table-column prop="ownername" label="商家" width="120">
					</el-table-column>
					<el-table-column prop="price" label="价格" width="120px">
					</el-table-column>
					<el-table-column prop="description" label="商品描述" show-overflow-tooltip>
					</el-table-column>
					<el-table-column label="操作" width="200px">
						<template slot-scope="scope">
							<!-- 移除购物车事件处理 -->
							<el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">移出购物车</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-main>
			<el-footer class="cart-footer" style="text-align: center">
				<!-- 结算事件处理 -->
				<el-button type="danger" v-model="allPrice" style="width: 200px; text-align: center" @click="showEnd()">
					{{ '结算 ( ' + allPrice + ' 元)'}}
				</el-button>
			</el-footer>
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


		<el-dialog title="结算" :visible.sync="endDialogVisible" width="30%" :modal="false">
			<span>总计{{this.allPrice}}元，确定购买吗？</span>
			<span slot="footer" class="dialog-footer">
				<el-button @click="endDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="removeCommodity()">确 定</el-button>
			</span>
		</el-dialog>

	</div>

</template>

<script>
	// import Pic from '../assets/Support.jpg'
	export default {
		created() {
			const _this = this;
			//获取父界面传来的用户信息、服务器地址信息

			if (sessionStorage.getItem("username") && sessionStorage.getItem("Url")) {
				//获取sessionStorage临时会话中的信息
				_this.username = sessionStorage.getItem("username")
				_this.url = sessionStorage.getItem("Url")

				//发送请求，从后端获取数据
				axios.get(_this.url + 'shoppingcart/find').then(function(resp) {
					_this.CommodityDate = resp.data;
					console.log('测试')
					console.log(resp.data)
				})
			} else {
				_this.$router.replace('/')
			}

		},
		name: "ShopCart",
		data() {
			return {
				username: '',
				url: '',
				count: 0,
				CommodityDate: [],
				multipleSelection: [],
				allPrice: 0,
				commodityDialogVisible: false,
				commodity: '',
				endDialogVisible: false
			}
		},
		methods: {

			//移除购物车
			handleDelete(index, row) {
				const _this = this;

				//删除商品被选中时事件处理
				if (_this.multipleSelection.length !== 0) {
					for (const item in _this.multipleSelection) {
						if (row.id === _this.multipleSelection[item].id) {
							console.log(item);
							_this.$delete(_this.multipleSelection, item);
							break;
						}
					}
				}
				//像后端发送移除请求
				axios.post(_this.url + 'shoppingcart/delete', _this.CommodityDate[index]).then(function(resp) {
					console.log(resp)
					if (resp.data == true) {
						_this.$message.error("以将商品" + _this.CommodityDate[index].goodname + "移除购物车");
						// console.log(_this.CommodityDate[index]);
						_this.$delete(_this.CommodityDate, index);
					} else {
						_this.$message.error("服务器内部错误")
					}
				})
			},

			//无用函数
			output(row) {
				console.log(row.pic)
			},

			//选中事件处理，将选中行对应的数据加入到multip***数组中
			handleSelectionChange(val) {
				this.multipleSelection = val;
				this.allPrice = 0;
				for (const item in this.multipleSelection) {
					this.allPrice += parseFloat(this.multipleSelection[item].price);
				}
			},

			//展示商品详情页
			showDialog(row) {
				this.commodityDialogVisible = true;
				this.commodity = row;
			},

			//结算提示信息
			showEnd() {
				if (this.allPrice !== 0) {
					this.endDialogVisible = true;
				}
			},

			//下订单
			removeCommodity() {
				const _this = this;
				_this.endDialogVisible = false;

				//像后端发送下单请求
				for (const item in _this.multipleSelection) {
					axios.post(_this.url + 'order/addorder', _this.multipleSelection[item]).then(function(resp) {
						console.log(resp)
						if (resp.data == true) {
							_this.$message.error("成功购买商品 " + _this.multipleSelection[item].goodname)
						} else {
							_this.$message.error("购买失败，服务器内部错误")
						}
					})
				}

				//处理前端数据变更
				while (_this.multipleSelection.length != 0) {
					let i = 0;
					for (const item1 in _this.multipleSelection) {
						for (const item2 in _this.CommodityDate) {
							if (_this.CommodityDate[item2].goodID ===
								_this.multipleSelection[item1].goodID) {
								//删除商品数组中对应的数据
								_this.$delete(_this.CommodityDate, item2);
								//处理选中数组
								_this.$delete(_this.multipleSelection, item1);
								i = -1;
								break;
							}
						}
						if (i === -1)
							break;
					}
				}
			}
		}
	}
</script>

<style scoped>
	.shop-cart {
		width: 100%;
		height: 100%;
		min-width: 1200px;
	}

	.cart-header {
		width: 100%;
		height: 80px;
		background-color: #409EFF;
	}

	.el-icon-shopping-cart-2 {
		position: relative;
		top: 15px;
	}

	.cart-footer {
		width: 100%;
		height: 60px;
		background-color: #409EFF;
		text-align: center;
		line-height: 60px;
	}
</style>
