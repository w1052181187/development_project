import request from '@/utils/axios'

export const infoManageBidState = {
  queryList (query) {
    return request({
      url: '/bidInvitations',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/bidInvitations',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/bidInvitations/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/bidInvitations',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/bidInvitations/' + objectId,
      method: 'delete'
    })
  }
}
