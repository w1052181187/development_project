/**
 * Created by admin on 2018-8-2.
 */
import request from '@/utils/axios/pubresource-axios'
export const otherProject = {
  save (otherPublicityForm) {
    return request({
      url: '/OtherPublicity',
      method: 'post',
      data: otherPublicityForm
    })
  },
  queryList (params) {
    return request({
      url: '/OtherPublicity',
      method: 'get',
      params: params
    })
  },
  queryOne (objectId) {
    return request({
      url: '/OtherPublicity/' + objectId,
      method: 'get'
    })
  },
  update (otherPublicityForm) {
    return request({
      url: '/OtherPublicity/',
      method: 'put',
      data: otherPublicityForm
    })
  },
  delete (objectId) {
    return request({
      url: '/OtherPublicity/' + objectId,
      method: 'delete'
    })
  }
}
