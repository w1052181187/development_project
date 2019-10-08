import Vue from 'vue'
import ElementUI from 'element-ui'
import store from './store'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import VueRouter from 'vue-router'
import router from './router'
// 自定义上传组件
import VueUploadWeb from '@/components/upload'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(VueRouter)
// 注册自定义组件
Vue.use(VueUploadWeb)
new Vue({
  router,
  store,
  components: { App },
  template: '<App/>'
}).$mount('#app')
