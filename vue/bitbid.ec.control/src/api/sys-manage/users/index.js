import request from '@/utils/axios'

export const user = {
  queryList (params) {
    return request({
      url: '/users',
      method: 'get',
      params
    })
  },
  queryOne (params) {
    return request({
      url: `/users/${params}`,
      method: 'get'
    })
  },
  saveUsers (data) {
    return request({
      url: '/users',
      method: 'post',
      data: data
    })
  },
  delUsers (objectId) {
    return request({
      url: '/users/' + objectId,
      method: 'delete'
    })
  },
  resetPassword (data) {
    return request({
      url: '/users/resetPwd',
      method: 'post',
      data: data
    })
  },
  rolesList () {
    return request({
      url: '/roles/list',
      method: 'get'
    })
  },
  // 禁用
  freezeUser (objectId, enableType) {
    return request({
      url: '/users/freeze/' + objectId + '/' + enableType,
      method: 'put'
    })
  },
  // 获得平台信息
  ecPlatUsers () {
    return request({
      url: '/users/ecPlatUsers',
      method: 'get'
    })
  },
  // 跳转第三方登录
  jumpToUrl (ecPlatformId) {
    return request({
      url: '/jumpToUrl/' + ecPlatformId,
      method: 'get'
    })
  }
}
