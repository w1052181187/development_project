/**
 * Created by Qinzy on 2018/11/5.
 */
import request from '@/utils/axios'

export const download = {
  queryList (query) {
    return request({
      url: '/downloadInfos',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/downloadInfos',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/downloadInfos/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/downloadInfos',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/downloadInfos/' + objectId,
      method: 'delete'
    })
  }
}
