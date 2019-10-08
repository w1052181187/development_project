/**
 * Created by Qinzy on 2018/11/5.
 */
import request from '@/utils/axios'

export const notice = {
  queryList (query) {
    return request({
      url: '/notices',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/notices',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/notices/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/notices',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/notices/' + objectId,
      method: 'delete'
    })
  }
}
