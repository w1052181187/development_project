// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import promise from 'es6-promise'
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import {commonJs} from './common/js/common'
import 'babel-polyfill'
// 引入ElementUI和样式表
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import echarts from 'echarts'
// 引入阿里字体图标
import './assets/icon/iconfont.css'
// 引入公共样式
import './common/css/base.css'
// 解决不识别es6语法
promise.polyfill()
Vue.config.productionTip = false

Vue.use(ElementUI)
// 将api地址绑定到Vue对象上
Vue.prototype.$commonJs = commonJs
// 将echarts绑定到Vue对象上
Vue.prototype.$echarts = echarts
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
