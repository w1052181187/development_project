import axios from 'axios'
import store from '@/store'
import {TokenKey} from '@/utils/auth'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import router from '@/router'

// response处理
const _RESPONSE_SUCCESS_CODE = '0000'

const service = axios.create({
  // baseURL: process.env.COMPONENTS_BASE_API, // api的base_url
  timeout: 60000 * 3, // request timeout
  withCredentials: true
})
service.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
// 设置默认请求地址
service.defaults.baseURL = process.env.COMPONENTS_BASE_API

// request interceptor
service.interceptors.request.use(config => {
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
}, error => {
  Promise.reject(error)
})

// resposne interceptor
service.interceptors.response.use(
  response => {
    // 请求结束时关闭进度条
    NProgress.done()
    const res = response.data
    if (res.resCode === _RESPONSE_SUCCESS_CODE && response.config.method !== 'get') {
      Message({
        message: res.resMessage,
        type: 'success',
        duration: 3 * 1000
      })
    } else if (res.resCode !== _RESPONSE_SUCCESS_CODE) {
      Message({
        message: res.resMessage,
        type: 'error',
        duration: 1000
      })
      if (res.resCode === '4444') {
        router.push('/welcome')
      }
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
  })

export default service
