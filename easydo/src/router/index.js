import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/components/Login'
import Market from '@/components/Market.vue'
import Commodity from '@/components/Commodity.vue'
import ShopCart from '../components/ShopCart.vue'
import Message from '../components/UserMessage.vue'
import Order from '../components/Order.vue'
import Account from '../components/Account.vue'
import Singup from '../components/SignUp.vue'
import Manager from '../components/Manager.vue'
import Controller from '../components/Controller.vue'

Vue.use(VueRouter)

const routes = [{
		path: '/',
		name: 'Login',
		component: Login
	},
	{
		path: '/signup',
		name: 'Singup',
		component: Singup
	},
	{
		path: '/controller',
		name: 'Controller',
		component: Controller,
		children: [
			{
				path: '/controller/manager',
				name: 'Manager',
				component: Manager
			},
		]
	},
	{
		path: '/market',
		name: 'Market',
		component: Market,
		// meta: { needLogin: true },
		children: [{
				path: '/market/commodity',
				// path: '/market',
				name: 'Commodity',
				component: Commodity
			},
			{
				path: '/market/cart',
				name: 'ShopCart',
				component: ShopCart
			},
			{
				path: '/market/message',
				name: 'Message',
				component: Message
			},
			{
				path: '/market/order',
				name: 'Order',
				component: Order
			},
			{
				path: '/market/account',
				name: 'Account',
				component: Account
			},
		]
	},
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router
