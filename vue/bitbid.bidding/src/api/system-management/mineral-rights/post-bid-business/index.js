/**
 * Created by admin on 2018-8-6.
 */
import request from '@/utils/axios/pubresource-axios'
export const scontract = {
  queryList (params) {
    return request({
      url: '/contract',
      method: 'get',
      params: params
    })
  },
  queryOne (objectId) {
    return request({
      url: '/contract/' + objectId,
      method: 'get'
    })
  },
  update (contractForm) {
    return request({
      url: '/contract/',
      method: 'put',
      data: contractForm
    })
  }
}
