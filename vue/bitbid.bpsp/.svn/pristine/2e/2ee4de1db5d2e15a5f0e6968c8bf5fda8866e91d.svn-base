import request from '@/utils/axios'

export const bidwin = {
  queryList (query) {
    return request({
      url: '/bulletins',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/winBidBulletins',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/winBidBulletins/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/winBidBulletins',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/winBidBulletins/' + objectId,
      method: 'delete'
    })
  },
  approve (data) {
    return request({
      url: '/winBidBulletins/approve',
      data: data,
      method: 'put'
    })
  },
  queryByRelatedCode (query) {
    return request({
      url: '/winBidBulletins/queryByRelatedCode',
      params: query,
      method: 'get'
    })
  },
  withdraw (objectId, code) {
    return request({
      url: '/winBidBulletins/withdraw/' + objectId + '/' + code,
      method: 'put'
    })
  }
}
