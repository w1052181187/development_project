import request from '@/utils/axios/up-axios'

export const enterApply = {
  queryList (params) {
    return request({
      url: '/home-enter-apply-info',
      method: 'get',
      params: params
    })
  },
  check (params) {
    return request({
      url: '/home-enter-apply-info/check/' + params,
      method: 'put'
    })
  }
}
