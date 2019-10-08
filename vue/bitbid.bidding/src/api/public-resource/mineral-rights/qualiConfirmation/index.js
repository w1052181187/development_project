/**
 * Created by lvke on 2018/8/6.
 */

import request from '@/utils/axios/pubresource-axios'

export const quaCon = {
  // 查询详情
  quaConDetail (query) {
    return request({
      url: '/qualiConfirmation',
      method: 'get',
      params: query
    })
  },
  // 报名
  quaConAdd (query) {
    return request({
      url: '/qualiConfirmation',
      method: 'post',
      data: query
    })
  },
  // 修改资格确认书信息
  quaConUpdate (query) {
    return request({
      url: '/qualiConfirmation',
      method: 'put',
      data: query
    })
  },
  queryList (query) {
    return request({
      url: '/qualiConfirmation/queryList',
      method: 'get',
      params: query
    })
  },
  // 确认进入限时竞价室
  enterLimitRoom (query) {
    return request({
      url: '/qualiConfirmation/enterLimit',
      method: 'put',
      data: query
    })
  }
}
