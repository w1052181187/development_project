// 引入服务器请求模块
import Axios from 'axios'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import store from '@/store'
import router from '@/router'
import {TokenKey} from './auth'

// response处理
const _RESPONSE_SUCCESS_CODE = '0000'
// 伪异步成功请求，前台判断如果返回为该状态时不会弹窗操作成功（用户有些返回数据请求不适合弹窗提示时，用此返回码）。
const _RESPONSE_SUCCESS_CODE_ASYN = '0001'

const ajax = Axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  timeout: 10000, // request timeout
  withCredentials: true
})
// axios配置
ajax.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'

// ajax 请求拦截
ajax.interceptors.request.use(
  config => {
    // 如果数据为JSON对象
    // 判断token
    if (store.getters.token) {
      // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
      config.headers[TokenKey] = store.getters.token
    }
    // 解决Ie不刷新问题
    config.url = config.url.indexOf('?') === -1 ? config.url + '?_date=' + new Date().getTime() : config.url + '&_date=' + new Date().getTime()
    // 请求开始前打开进度条
    NProgress.start()
    return config
  }, err => {
    console.log(err)
  }
)

// ajax 响应拦截
ajax.interceptors.response.use(
  response => {
    // 请求结束时关闭进度条
    NProgress.done()
    const res = response.data
    if (res.resCode === _RESPONSE_SUCCESS_CODE && response.config.method !== 'get') {
      Message({
        message: res.resMessage,
        type: 'success',
        duration: 10 * 1000
      })
    } else if (res.resCode !== _RESPONSE_SUCCESS_CODE && res.resCode !== _RESPONSE_SUCCESS_CODE_ASYN) {
      Message({
        message: res.resMessage,
        type: 'error',
        duration: 3 * 1000
      })
    }
    return response
  }, error => {
    if (error.response) {
      switch (error.response.status) {
        // 401 认证失败
        case 401:
          Message({
            showClose: true,
            message: '登录信息已过期，请重新登录',
            type: 'warning',
            duration: 3 * 1000
          })
          // 清除数据
          store.dispatch('ClearLoginInfo').then(() => {
            // 为了重新实例化vue-router对象 避免bug
            // location.reload()
            // 跳转到登录页面
            router.push('/login')
          })
          break
        default:
          Message({
            message: error.message,
            type: 'error',
            duration: 3 * 1000
          })
      }
    }
    return Promise.reject(error)
  }
)

export default ajax
