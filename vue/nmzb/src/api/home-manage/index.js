import request from '@/utils/axios'

export const homeManage = {
  queryList (query) {
    return request({
      url: '/pictureInfos',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/pictureInfos',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/pictureInfos/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/pictureInfos',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/pictureInfos/' + objectId,
      method: 'delete'
    })
  },
  // 禁用
  disable (objectId) {
    return request({
      url: '/pictureInfos/disable/' + objectId,
      method: 'put'
    })
  },
  // 启用
  enabled (objectId, type) {
    return request({
      url: `/pictureInfos/enabled/${objectId}/${type}`,
      method: 'put'
    })
  }
}
