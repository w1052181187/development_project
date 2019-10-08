/** 小程序接口 */
import request from '@/utils/axios/up-axios'

export const smallProgram = {
  // *************************************************系统通知*************************************************
  queryList (query) {
    return request({
      url: '/miniProSysNotice',
      params: query,
      method: 'get'
    })
  },
  // 保存
  saveList (form) {
    return request({
      url: '/miniProSysNotice',
      method: 'post',
      data: form
    })
  },
  // 单个查询
  detailQuery (objectId) {
    return request({
      url: '/miniProSysNotice/' + objectId,
      method: 'get'
    })
  },
  // 发布
  releaseQuery (objectId) {
    return request({
      url: '/miniProSysNotice/' + objectId,
      method: 'put'
    })
  },
  // 撤回
  withdrawQuery (objectId) {
    return request({
      url: '/miniProSysNotice/withdraw/' + objectId,
      method: 'put'
    })
  },
  // 修改
  updateList (form) {
    return request({
      url: '/miniProSysNotice',
      method: 'put',
      data: form
    })
  },
  // 删除
  delectList (objectId) {
    return request({
      url: '/miniProSysNotice/' + objectId,
      method: 'delete'
    })
  },
  // *************************************************轮播图管理*************************************************
  // 查询
  queryBanner (query) {
    return request({
      url: '/miniProMana',
      params: query,
      method: 'get'
    })
  },
  // 保存
  saveBanner (form) {
    return request({
      url: '/miniProMana',
      method: 'post',
      data: form
    })
  }
}
