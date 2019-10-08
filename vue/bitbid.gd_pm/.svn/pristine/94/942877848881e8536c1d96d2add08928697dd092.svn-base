/** 项目接口 */
import request from '@/utils/axios/up-axios'

export const subject = {
  queryList (query) {
    return request({
      url: '/subject',
      params: query,
      method: 'get'
    })
  },
  save (form) {
    return request({
      url: '/subject/saveBatch',
      method: 'post',
      data: form
    })
  },
  delete (query) {
    return request({
      url: '/subject/deleteByProjectId/' + query,
      method: 'delete'
    })
  },
  detail (objectId) {
    return request({
      url: '/subject/' + objectId,
      method: 'get'
    })
  },
  detailAll (objectId) {
    return request({
      url: '/subject/detailAll/' + objectId,
      method: 'get'
    })
  },
  detailWeb (objectId) {
    return request({
      url: '/webRoom/' + objectId,
      method: 'get'
    })
  },
  detailAllWeb (objectId) {
    return request({
      url: '/webRoom/detailAll/' + objectId,
      method: 'get'
    })
  },
  update (form) {
    return request({
      url: '/subject',
      method: 'put',
      data: form
    })
  },
  queryCount (query) {
    return request({
      url: '/subject/queryCount',
      params: query,
      method: 'get'
    })
  },
  // 获取当前标的
  curInfor (query) {
    return request({
      url: '/subject/queryStatus/' + query,
      method: 'get'
    })
  },
  // 当前价
  curPiceInfo (objectId) {
    return request({
      url: '/webRoom/currentPrice/' + objectId,
      method: 'get'
    })
  }
}
