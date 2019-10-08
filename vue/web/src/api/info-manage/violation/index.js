/**
 * Created by Qinzy on 2018/11/5.
 */
import request from '@/utils/axios'

export const infoManageViolation = {
  queryList (query) {
    return request({
      url: '/violationNotices',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/violationNotices',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/violationNotices/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/violationNotices',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/violationNotices/' + objectId,
      method: 'delete'
    })
  }
}
