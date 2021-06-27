<template>
	<div class="hello">
		<!-- 标签页，同商城 -->
		<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
			<el-menu-item index="1" @click="underway()">正在进行</el-menu-item>
			<el-menu-item index="2" @click="toBeConfirmed()">待确定</el-menu-item>
			<el-menu-item index="3" @click="completed()">已完成</el-menu-item>
		</el-menu>

		<!-- 订单展示块，绑定数据showData -->
		<el-container style="height: 500px; border: 1px solid #eee">
			<el-container>
				<el-main>
					<el-table :data="showData" border>

						<el-table-column prop="goodname" label="商品名" width="180">
						</el-table-column>
						<el-table-column prop="price" label="价格" width="80">
						</el-table-column>
						<el-table-column prop="orderstate" label="订单状态" width="100">
						</el-table-column>
						<el-table-column prop="buyer" label="买家" width="100">
						</el-table-column>
						<el-table-column prop="seller" label="卖家" width="100">
						</el-table-column>
						<el-table-column prop="description" label="商品描述">
						</el-table-column>

						<el-table-column label="操作" width="200">
							<template slot-scope="scope">
								<!-- 操作板块，通过v-if处理显隐性 -->
								<el-button @click.native.prevent="receivingGoods(scope.$index, showData)" type="primary" size="small" v-if="scope.row.orderstate=='买家待确认'">买家待确认</el-button>
								<el-button @click.native.prevent="receivingGoods(scope.$index, showData)" type="primary" size="small" v-if="scope.row.orderstate=='卖家待确认'">卖家待确认</el-button>
								<el-button @click.native.prevent="receivingGoods(scope.$index, showData)" type="primary" size="small" v-if="scope.row.orderstate=='进行中'">确认收货</el-button>
								<el-button @click.native.prevent="cancelGoods(scope.$index, showData)" type="danger" size="small" v-if="scope.row.orderstate=='进行中'">取消订单</el-button>
								<el-button @click.native.prevent="returnGoods(scope.$index, showData)" type="warning" size="small" v-if="scope.row.orderstate=='已完成'">
									退货
								</el-button>
							</template>
						</el-table-column>

					</el-table>
				</el-main>
			</el-container>
		</el-container>

	</div>

</template>





<script>
	export default {
		created() {
			const _this = this;

			if (sessionStorage.getItem("username") && sessionStorage.getItem("Url")) {
				//获取sessionStorage临时会话中的信息
				_this.username = sessionStorage.getItem("username")
				_this.url = sessionStorage.getItem("Url")

				_this.activeIndex = '1';

				//像后端请求订单数据
				_this.updateData(_this.activeIndex)
			} else {
				_this.$router.replace('/')
			}
		},
		data() {
			return {

				//数据类型展示，没有用到
				commodity: {
					orderID: '',
					goodname: '',
					price: '',
					description: '',
					buyer: '',
					seller: '',
					orderstate: '',
					comment: '',
					isshow: '',
				},

				username: '',
				url: '',

				dialogFormVisible: false,
				//展示数据
				showData: [],
				activeIndex: '',
				//后端订单数据缓存处
				data: [],
				finishedOrder: [],
				unFinishedOrder: [],
				returnOrder: [],
				receiveOrder: [],
				cancelOrder: [],
				isBuyer: false,
				isSeller: false,
			}
		},
		methods: {
			//易行交易系统
			
			handleSelect(key, keyPath) {
				if(key == 3){
					this.tempIndex = parseInt(this.activeIndex)
				}
				this.activeIndex = key
			},

			//更新订单数据缓存
			updateData(activeIndex) {
				const _this = this
				axios.get(_this.url + 'order/findorder').then(function(resp) {
					_this.data = resp.data;
					if (activeIndex == 1) {
						_this.underway();
					} else if (activeIndex == 2) {
						_this.toBeConfirmed();
					} else if (activeIndex == 3) {
						_this.completed();
					}
				})
			},

			completed() { //根据标签筛选已完成订单
				const _this = this;
				_this.showData = [];
				_this.completedOrder = [];
				for (var i = 0; i < _this.data.length; i++) {
					if (_this.data[i].orderstate == '已完成') {
						_this.completedOrder.push(_this.data[i]);
					}
					_this.showData = _this.completedOrder;
				}
			},
			toBeConfirmed() { //根据标签筛选待确认订单
				const _this = this;
				_this.showData = []
				_this.toBeConfirmedOrder = []
				for (var i = 0; i < _this.data.length; i++) {
					if (_this.data[i].orderstate == '卖家待确认' ||
						_this.data[i].orderstate == '买家待确认') {
						_this.toBeConfirmedOrder.push(_this.data[i])
					}
					_this.showData = _this.toBeConfirmedOrder
				}
			},
			underway() { //根据标签筛选正在进行订单
				const _this = this;
				_this.showData = []
				_this.underwayOrder = []
				for (var i = 0; i < _this.data.length; i++) {
					if (_this.data[i].orderstate == '进行中') {
						_this.underwayOrder.push(_this.data[i])
					}
				}
				_this.showData = _this.underwayOrder;
			},
			returnGoods(index, rows) { //退货操作
				const _this = this;
				_this.returnOrder[0] = rows[index];

				console.log(_this.url + 'order/returngood')
				axios.post(_this.url + 'order/returngood', _this.returnOrder[0]).then(function(resp) {
					console.log(resp)
					if (resp.data == true) {
						_this.$message.success("退货成功")
					} else {
						_this.$message.error("退货失败，服务器内部错误")
					}
				})
			},

			receivingGoods(index, rows) { //确认收货操作
				const _this = this;
				_this.receiveOrder[0] = rows[index];
				console.log(_this.url + 'order/confirm')
				axios.post(_this.url + 'order/confirm', _this.receiveOrder[0]).then(function(resp) {
					console.log(resp)
					if (resp.data == true) {
						_this.$message.error('确认成功')
						_this.updateData(_this.activeIndex)
					} else {
						console.log(_this.activeIndex)
						if(_this.activeIndex === '2'){
							_this.$message.warning("您已确认，请耐心等待")
						}else{
							_this.$message.error("服务器出错")	
						}
					}
				})
			},
			cancelGoods(index, rows) { //取消订单
				const _this = this;
				_this.returnOrder[0] = rows[index];

				axios.post(_this.url + 'order/cancel', _this.returnOrder[0]).then(function(resp) {
					if (resp.data == true) {
						_this.$message.error('取消成功')
						_this.updateData(_this.activeIndex)
					} else {
						_this.$message.error('取消失败，请联系管理员处理')
					}
				})
			},
		},
	};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
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
