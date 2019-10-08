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
      url: '/winCandidateBulletins',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/winCandidateBulletins/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/winCandidateBulletins',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/winCandidateBulletins/' + objectId,
      method: 'delete'
    })
  },
  approve (data) {
    return request({
      url: '/winCandidateBulletins/approve',
      data: data,
      method: 'put'
    })
  },
  change (data) {
    return request({
      url: '/winCandidateBulletins/change',
      data: data,
      method: 'post'
    })
  }
}
