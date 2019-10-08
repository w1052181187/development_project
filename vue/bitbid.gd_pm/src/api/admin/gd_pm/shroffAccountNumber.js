/** 项目接口 */
import request from '@/utils/axios/up-axios'

export const shroffAccountNumber = {
  queryList (query) {
    return request({
      url: '/shroffAccountNumber',
      params: query,
      method: 'get'
    })
  },
  save (form) {
    return request({
      url: '/shroffAccountNumber',
      method: 'post',
      data: form
    })
  },
  selectAnc (agencyId) {
    return request({
      url: '/shroffAccountNumber/queryCount/' + agencyId,
      method: 'get'
    })
  },
  detail (objectId) {
    return request({
      url: '/shroffAccountNumber/' + objectId,
      method: 'get'
    })
  },
  update (form) {
    return request({
      url: '/shroffAccountNumber',
      method: 'put',
      data: form
    })
  },
  delete (query) {
    return request({
      url: '/shroffAccountNumber/' + query,
      method: 'delete'
    })
  },
  forbidden (objectId, status) {
    return request({
      url: '/shroffAccountNumber/forbidden/' + objectId + '/' + status,
      method: 'post'
    })
  }
}
