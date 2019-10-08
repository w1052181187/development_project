import request from '@/utils/axios'

export const bidanno = {
  queryList (query) {
    return request({
      url: '/bulletins',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/tenderBulletins',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/tenderBulletins/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/tenderBulletins',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/tenderBulletins/' + objectId,
      method: 'delete'
    })
  },
  approve (data) {
    return request({
      url: '/tenderBulletins/approve',
      data: data,
      method: 'put'
    })
  },
  print (objectId) {
    return request({
      url: '/tenderBulletins/receipt/' + objectId,
      method: 'get'
    })
  },
  change (data) {
    return request({
      url: '/tenderBulletins/change',
      data: data,
      method: 'post'
    })
  },
  again (data) {
    return request({
      url: '/tenderBulletins/again',
      data: data,
      method: 'post'
    })
  }
}
