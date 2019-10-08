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
  },
  // 禁用
  disable (objectId) {
    return request({
      url: '/friendly-links/disable/' + objectId,
      method: 'put'
    })
  },
  // 启用
  enabled (objectId) {
    return request({
      url: `/friendly-links/enabled/${objectId}`,
      method: 'put'
    })
  }
}
