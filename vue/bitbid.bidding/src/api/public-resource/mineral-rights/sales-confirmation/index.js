/**
 * Created by lvke on 2018/8/7.
 */

import request from '@/utils/axios/pubresource-axios'

export const salesCon = {
  // 新增成交确认书
  salesConAdd (query) {
    return request({
      url: '/salesConfirmation',
      method: 'post',
      data: query
    })
  },
  // 修改成交确认书信息
  salesConUpdate (query) {
    return request({
      url: '/salesConfirmation',
      method: 'put',
      data: query
    })
  },
  // 确认竞得人列表
  confirmList (params) {
    return request({
      url: '/salesConfirmation',
      method: 'get',
      params: params
    })
  },
  detail (objectId) {
    return request({
      url: '/salesConfirmation/' + objectId,
      method: 'get'
    })
  }
}
