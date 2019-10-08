import request from '@/utils/axios'

export const login = {
  login (data) {
    return request({
      url: '/login',
      data: data,
      method: 'post'
    })
  },
  getLoginInfo (data) {
    return request({
      url: '/getLoginInfo',
      method: 'get',
      data: data
    })
  },
  logout () {
    return request({
      url: '/logout',
      method: 'get'
    })
  },
  register (data) {
    return request({
      url: '/register',
      method: 'post',
      data: data
    })
  },
  updatePwd (data) {
    return request({
      url: '/updatePwd',
      method: 'post',
      data: data
    })
  }
}
