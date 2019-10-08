import request from '@/utils/axios/components-axios'

export const auth = {
  login: (data) => {
    return request({
      url: '/login',
      method: 'post',
      data: data
    })
  },
  getLoginInfo: () => {
    return request({
      url: '/getLoginInfo',
      method: 'get'
    })
  },
  logout: () => {
    return request({
      url: '/logout',
      method: 'get'
    })
  }
}
