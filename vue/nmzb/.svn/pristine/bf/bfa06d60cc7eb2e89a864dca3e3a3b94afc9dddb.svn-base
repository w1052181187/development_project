import request from '@/utils/axios'

export const friendlyLink = {
  queryList (query) {
    return request({
      url: '/friendly-links',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/friendly-links',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/friendly-links/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/friendly-links',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/friendly-links/' + objectId,
      method: 'delete'
    })
  },
  // 禁用
  disable (objectId) {
    return request({
      url: '/friendly-links/disable/' + objectId,
      method: 'put'
    })
  },
  // 启用
  enabled (objectId) {
    return request({
      url: `/friendly-links/enabled/${objectId}`,
      method: 'put'
    })
  }
}
