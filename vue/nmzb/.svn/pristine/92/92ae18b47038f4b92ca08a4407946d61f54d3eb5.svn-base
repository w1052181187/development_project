import request from '@/utils/axios'

export const dynamicNotice = {
  queryList (query) {
    return request({
      url: '/dynamic-notices',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/dynamic-notices',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/dynamic-notices/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/dynamic-notices',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/dynamic-notices/' + objectId,
      method: 'delete'
    })
  },
  // 禁用
  disable (objectId) {
    return request({
      url: '/dynamic-notices/disable/' + objectId,
      method: 'put'
    })
  },
  // 启用
  enabled (objectId) {
    return request({
      url: '/dynamic-notices/enabled/' + objectId,
      method: 'put'
    })
  }
}
