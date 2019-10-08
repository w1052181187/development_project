/**
 * Created by lvke on 2018/8/6.
 */

import request from '@/utils/axios/pubresource-axios'

export const squaCon = {
  // 查询详情
  quaConDetail (query) {
    return request({
      url: '/qualiConfirmation',
      method: 'get',
      params: query
    })
  },
  // 新增资格确认书
  quaConAdd (query) {
    return request({
      url: '/qualiConfirmation',
      method: 'post',
      data: query
    })
  }
}
