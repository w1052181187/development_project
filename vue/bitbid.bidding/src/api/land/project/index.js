/**
 * Created by Qinzy on 2018/10/23.
 */
import request from '@/utils/axios/pubresource-axios'

export const landProject = {
  queryList (query) {
    return request({
      url: '/land-information',
      params: query,
      method: 'get'
    })
  },
  save (form) {
    return request({
      url: '/land-information',
      method: 'post',
      data: form
    })
  },
  update (form) {
    return request({
      url: '/land-information',
      method: 'put',
      data: form
    })
  },
  detail (objectId) {
    return request({
      url: '/land-information/' + objectId,
      method: 'get'
    })
  },
  remove (query) {
    return request({
      url: '/land-information/' + query,
      method: 'delete'
    })
  },
  // 保存与受让人关联关系
  saveRefAssignee (form) {
    return request({
      url: '/land-information/saveRefAssignee',
      data: form,
      method: 'post'
    })
  },
  // 删除与受让人关联关系
  delRefAssignee (userId, objectId) {
    return request({
      url: '/land-information/delRefAssignee/' + userId + '/' + objectId,
      method: 'delete'
    })
  },
  // 根据地块id查询受让人ids
  queryUserIdsById (objectId) {
    return request({
      url: '/land-information/queryUserIdsById/' + objectId,
      method: 'get'
    })
  },
  // 根据受让人id查询受让人信息
  queryAssigneeById (userId) {
    return request({
      url: '/land-information/queryAssigneeById/' + userId,
      method: 'get'
    })
  },
  // 根据地块id删除关联关系
  delRefByObjectId (objectId) {
    return request({
      url: '/land-information/delRefByObjectId/' + objectId,
      method: 'delete'
    })
  },
  setTime (form) {
    return request({
      url: '/land-information/setTime',
      method: 'post',
      data: form
    })
  },
  queryInfosByAnnoId (objectId) {
    return request({
      url: '/land-information/infos/' + objectId,
      method: 'get'
    })
  },
  queryListForBidder (params) {
    return request({
      url: '/land-information/queryListForBidder',
      method: 'get',
      params: params
    })
  },
  count (objectId) {
    return request({
      url: '/land-information/count/' + objectId,
      method: 'get'
    })
  },
  queryListForQues (query) {
    return request({
      url: '/land-information/queryListForQues',
      params: query,
      method: 'get'
    })
  }
}
