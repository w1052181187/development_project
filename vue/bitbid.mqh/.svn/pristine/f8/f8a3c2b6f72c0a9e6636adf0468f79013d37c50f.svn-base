import ajax from '@/common/js/ajax'

export const auth = {
  login: (data) => {
    return ajax({
      url: '/login',
      method: 'post',
      data: data
    })
  },
  getLoginInfo: (data) => {
    return ajax({
      url: '/getLoginInfo',
      method: 'get',
      data: data
    })
  },
  logout: () => {
    return ajax({
      url: '/logout',
      method: 'get'
    })
  }
}
