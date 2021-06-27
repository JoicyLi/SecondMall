<template>
	<div id="market">
		<div id="left_menu">
			<div class="bar">
				<!-- <img class="img" src="../assets/Main.jpg" @click="goTo('/controller/manager', 'Manager')"> -->
				<img class="img" src="../assets/Main.jpg">
			</div>
<!-- 			<div class="bar">
				<img class="img" src="../assets/Account.jpg" @click="goTo('/controller/account', 'Account')">
			</div> -->
		</div>
		<div id="right_content">
			<router-view></router-view>
		</div>
	</div>
</template>

<script>
	export default {
		created() {
			const _this = this;
			
			if (sessionStorage.getItem("username") && sessionStorage.getItem("Url")) {
				_this.username = sessionStorage.getItem("username")
				_this.url = sessionStorage.getItem("Url")
				_this.$router.replace({
					path: '/controller/manager',
					name: 'Manager',
				})
			} else {
				_this.$router.replace('/')
			}
		},
		name: "Market",
		data() {
			return {
				username: '',
				isManager: '',
				url: '',
			}
		},
		methods: {
		}
	}
</script>

<style scoped>
	#detail_img {
		width: 80px;
		height: auto;
	}

	li {
		list-style: none;
	}

	.right-li {
		padding: 20px;
		position: relative;
	}

	ul li {
		box-sizing: border-box;
		float: left;
		width: 300px;
		height: 150px;
	}

	ul li:nth-child(1),
	ul li:nth-child(2),
	ul li:nth-child(3) {
		border-bottom: 1px dashed #ddd;
	}

	ul li:nth-child(1),
	ul li:nth-child(2),
	ul li:nth-child(4),
	ul li:nth-child(5) {
		border-right: 1px dashed #ddd;
	}

	.right-li img {
		position: absolute;
		top: 25px;
		right: 17px;
		width: 100px;
		height: 100px;
	}

	.a-wrap {
		color: white;
		background: rgb(20, 36, 103);
		width: 100px;
		height: 30px;
		text-align: center;
		line-height: 30px;
		border-top-right-radius: 15px;
		border-bottom-right-radius: 15px;
		margin-bottom: 20px;

	}

	.left-wrap a {
		color: rgb(26, 26, 25);
		padding-bottom: 20px;
	}

	#left_menu {
		position: fixed;
		top: 0;
		left: 0;
		height: 100%;
		width: 125px;
		z-index: 21;
		text-align: center;
		background: #142467;
		box-shadow: 0 2px 24px rgba(11, 15, 20, 0.08);
		overflow-y: false;
		overflow: -moz-scrollbars-none;
		-ms-overflow-style: none;
	}

	.img {
		display: table-cell;
		width: 65px;
		height: auto;
		padding: 60px 1px 16px 22px;
		/*top right bottom left*/
		vertical-align: middle;
		transition: all 500ms ease-in-out;
	}

	.img:hover {
		width: 73px;
		height: auto;
		transform-style: preserve-3d;
		transition: all 500ms ease-in-out;
	}

	.bar {
		display: table;
	}

	#right_content {
		width: 1300px;
		height: 700px;
		margin-left: 150px;
		margin-top: 0px;
		padding: 0;
		background-color: #fff;
		/* border: #DC143C outset; */
	}

	#search {
		margin-left: 300px;
		width: 500px;
		height: 40px;
		border-radius: 10px;
		border: none;
		vertical-align: middle;
	}

	#new_item {
		width: auto;
		height: 43px;
		display: inline-block;
		vertical-align: middle;
		padding: 12px 24px;
		margin-left: 150px;
		font-size: 15px;
		font-weight: bold;
		cursor: pointer;
		color: #ffffff;
		background-color: #2f40df;
		border-radius: 10px;
		border: none;
		transition: all 500ms ease-in-out;
	}

	#new_item:hover {
		background-color: #092d67;
		transition: all 500ms ease-in-out;
	}

	.item_img {
		transition: all 500ms ease-in-out;
	}

	.item_img:hover {
		transform: scale(1.35);
		transition: all 500ms ease-in-out;
	}
</style>
