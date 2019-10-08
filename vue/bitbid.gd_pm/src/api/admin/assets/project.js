/** 项目接口 */
import request from '@/utils/axios/up-axios'

export const project = {
  queryList (query) {
    return request({
      url: '/assect-project',
      params: query,
      method: 'get'
    })
  },
  queryWebList (query) {
    return request({
      url: '/webRoom',
      params: query,
      method: 'get'
    })
  },
  save (form) {
    return request({
      url: '/assect-project',
      method: 'post',
      data: form
    })
  },
  detail (objectId) {
    return request({
      url: '/assect-project/' + objectId,
      method: 'get'
    })
  },
  update (form) {
    return request({
      url: '/assect-project',
      method: 'put',
      data: form
    })
  },
  delete (query) {
    return request({
      url: '/assect-project/' + query,
      method: 'delete'
    })
  },
  queryListNotPage (query) {
    return request({
      url: '/assect-project/queryListNotPage',
      params: query,
      method: 'get'
    })
  },
  setupBidders (form) {
    return request({
      url: '/assect-project/setupBidders',
      method: 'post',
      data: form
    })
  },
  queryBidders (query) {
    return request({
      url: '/assect-project/queryBidders/' + query,
      method: 'get'
    })
  },
  querySubList (query) {
    return request({
      url: '/assect-project/querySubList',
      method: 'get',
      params: query
    })
  },
  queryWebSubList (query) {
    return request({
      url: '/webRoom/querySubList',
      method: 'get',
      params: query
    })
  }
}
