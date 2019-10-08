/** 项目接口 */
import request from '@/utils/axios/up-axios'

export const agencyEnterprise = {
  queryList (query) {
    return request({
      url: '/agencyEnterprise',
      params: query,
      method: 'get'
    })
  },
  queryNotPageList (query) {
    return request({
      url: '/agencyEnterprise/queryNotPageList',
      params: query,
      method: 'get'
    })
  },
  save (form) {
    return request({
      url: '/agencyEnterprise',
      method: 'post',
      data: form
    })
  },
  detail (objectId) {
    return request({
      url: '/agencyEnterprise/' + objectId,
      method: 'get'
    })
  },
  update (form) {
    return request({
      url: '/agencyEnterprise',
      method: 'put',
      data: form
    })
  },
  delete (query) {
    return request({
      url: '/agencyEnterprise/' + query,
      method: 'delete'
    })
  },
  refUser (query) {
    return request({
      url: '/agencyEnterprise/refUser',
      params: query,
      method: 'get'
    })
  },
  // *********************************合同保存************************************
  contractSave (form) {
    return request({
      url: '/contract',
      method: 'post',
      data: form
    })
  },
  contractUpdate (form) {
    return request({
      url: '/contract',
      method: 'put',
      data: form
    })
  },
  // *********************************关联信息保存************************************
  associatedSave (form) {
    return request({
      url: '/agencyEnterprise/saveRefVersionUsers',
      method: 'post',
      data: form
    })
  },
  // *********************************关联信息后查询*********************************
  queryRefVersionUserList (query) {
    return request({
      url: '/agencyEnterprise/queryRefVersionUser',
      method: 'get',
      params: query
    })
  },
  // *********************************关联信息后禁用启用*********************************
  queryEnable (query, status) {
    return request({
      url: '/agencyEnterprise/forbidden/' + query + '/' + status,
      method: 'post'
    })
  }
}
