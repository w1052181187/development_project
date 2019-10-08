/**
 * Created by Qinzy on 2018/11/5.
 */
import request from '@/utils/axios'

export const infoManageLaw = {
  queryList (query) {
    return request({
      url: '/lawsAndRegulations',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/lawsAndRegulations',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/lawsAndRegulations/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/lawsAndRegulations',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/lawsAndRegulations/' + objectId,
      method: 'delete'
    })
  }
}
