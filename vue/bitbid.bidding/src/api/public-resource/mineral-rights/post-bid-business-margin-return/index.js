/**
 * Created by admin on 2018-8-8.
 */
import request from '@/utils/axios/pubresource-axios'
export const refundedSecurity = {
  save (refundedSecurityForm) {
    return request({
      url: '/RefundedSecurity/',
      method: 'post',
      data: refundedSecurityForm
    })
  },
  queryList (params) {
    return request({
      url: '/RefundedSecurity',
      method: 'get',
      params: params
    })
  },
  queryOne (objectId) {
    return request({
      url: '/RefundedSecurity/' + objectId,
      method: 'get'
    })
  },
  update (contractForm) {
    return request({
      url: '/RefundedSecurity/',
      method: 'put',
      data: contractForm
    })
  },
  delete (objectId) {
    return request({
      url: '/RefundedSecurity/' + objectId,
      method: 'delete'
    })
  }
}
