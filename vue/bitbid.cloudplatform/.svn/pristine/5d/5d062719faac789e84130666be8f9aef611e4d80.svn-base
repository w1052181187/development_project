import request from '@/utils/axios/up-axios'

export const user = {
// 注册用户
  regist (data) {
    return request({
      url: '/users/regist',
      method: 'post',
      data: data
    })
  },
  // 登陆
  login (data) {
    return request({
      url: '/login',
      method: 'post',
      data: data
    })
  },
  // 退出
  logoOut () {
    return request({
      url: '/logout',
      method: 'post'
    })
  }
}
