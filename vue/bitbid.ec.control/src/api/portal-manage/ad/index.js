import request from '@/utils/axios'

export const ad = {
  queryList (params) {
    return request({
      url: '/ad',
      method: 'get',
      params
    })
  },
  queryOne (params) {
    return request({
      url: `/ad/${params}`,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/ad',
      method: 'post',
      data
    })
  },
  deleteAd (params) {
    return request({
      url: `/ad/${params}`,
      method: 'delete'
    })
  }
}
