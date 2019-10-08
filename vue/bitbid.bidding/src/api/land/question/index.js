/**
 * Created by Qinzy on 2018/10/23.
 */
import request from '@/utils/axios/pubresource-axios'

export const question = {
  queryList (query) {
    return request({
      url: '/question',
      params: query,
      method: 'get'
    })
  },
  save (form) {
    return request({
      url: '/question',
      method: 'post',
      data: form
    })
  },
  update (form) {
    return request({
      url: '/question',
      method: 'put',
      data: form
    })
  },
  detail (objectId) {
    return request({
      url: '/question/' + objectId,
      method: 'get'
    })
  },
  count (query) {
    return request({
      url: '/question/count',
      params: query,
      method: 'get'
    })
  },
  accept (form) {
    return request({
      url: '/question/accept',
      method: 'put',
      data: form
    })
  }
}
