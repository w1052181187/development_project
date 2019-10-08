import request from '@/utils/axios'

export const enterprise = {
  save (data) {
    return request({
      url: '/enterprises',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/enterprises/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/enterprises',
      data: data,
      method: 'put'
    })
  },
  // 审核
  audit (data) {
    return request({
      url: '/enterprises/audit',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/enterprises/' + objectId,
      method: 'delete'
    })
  },
  change (data) {
    return request({
      url: '/enterprises/change',
      data: data,
      method: 'put'
    })
  }
}
