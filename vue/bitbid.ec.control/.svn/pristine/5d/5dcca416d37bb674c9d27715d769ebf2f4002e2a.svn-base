import request from '@/utils/axios'

export const except = {
  queryList (params) {
    return request({
      url: '/Transaction-record',
      method: 'get',
      params
    })
  },
  update (data) {
    return request({
      url: `/Transaction-record`,
      method: 'post',
      data
    })
  },
  deleteExcept (params) {
    return request({
      url: `/Transaction-record/${params}`,
      method: 'delete'
    })
  }
}
