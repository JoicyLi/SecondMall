import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'

Vue.config.productionTip = false

// router.beforeEach(function(to, from, next) {
// 	if(to.meta.needLogin){
// 		if(localStorage.getItem("username")){
// 			next();
// 		}else{
// 			next({
// 				name: "Account"
// 			})
// 		}
// 	}else{
// 		next();
// 	}
// })

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
