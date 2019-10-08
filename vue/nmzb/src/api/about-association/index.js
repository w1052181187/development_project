import request from '@/utils/axios'

export const association = {
  query (params) {
    return request({
      url: '/notices',
      params: params,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/notices',
      data: data,
      method: 'post'
    })
  }
}
