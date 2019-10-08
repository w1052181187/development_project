import request from '@/utils/axios'

export const purchase = {
  queryList (query) {
    return request({
      url: '/bulletins',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/purchaseBulletins',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/purchaseBulletins/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/purchaseBulletins',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/purchaseBulletins/' + objectId,
      method: 'delete'
    })
  },
  approve (data) {
    return request({
      url: '/purchaseBulletins/approve',
      data: data,
      method: 'put'
    })
  },
  withdraw (objectId, code) {
    return request({
      url: '/purchaseBulletins/withdraw/' + objectId + '/' + code,
      method: 'put'
    })
  }
}
