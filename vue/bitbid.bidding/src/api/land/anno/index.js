/**
 * Created by Qinzy on 2018/10/24.
 */
import request from '@/utils/axios/pubresource-axios'

export const landAnno = {
  queryAssignee (query) {
    return request({
      url: '/land-anno/queryAssignee',
      params: query,
      method: 'get'
    })
  },
  save (form) {
    return request({
      url: '/land-anno',
      data: form,
      method: 'post'
    })
  },
  update (form) {
    return request({
      url: '/land-anno',
      data: form,
      method: 'post'
    })
  },
  saveAnnoun (form) {
    return request({
      url: '/land-anno/saveAnnoun',
      data: form,
      method: 'post'
    })
  },
  update1 (form) {
    return request({
      url: '/land-anno',
      data: form,
      method: 'put'
    })
  },
  queryList (query) {
    return request({
      url: '/land-anno',
      params: query,
      method: 'get'
    })
  },
  detail (objectId) {
    return request({
      url: '/land-anno/' + objectId,
      method: 'get'
    })
  },
  remove (objectId) {
    return request({
      url: '/land-anno/' + objectId,
      method: 'delete'
    })
  },
  sendAnno (objectId) {
    return request({
      url: '/land-anno/sendAnno/' + objectId,
      method: 'get'
    })
  },
  examine (objectId) {
    return request({
      url: '/land-anno/examine/' + objectId,
      method: 'get'
    })
  }
}
