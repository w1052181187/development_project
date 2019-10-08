import request from '@/utils/axios'

export const adminUser = {
  queryList (query) {
    return request({
      url: '/users',
      params: query,
      method: 'get'
    })
  },
  save (data) {
    return request({
      url: '/users',
      data: data,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/users/' + objectId,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/users',
      data: data,
      method: 'put'
    })
  },
  // 重置密码
  resetPwd (data) {
    return request({
      url: '/users/resetPwd',
      data: data,
      method: 'put'
    })
  },
  remove (objectId) {
    return request({
      url: '/users/' + objectId,
      method: 'delete'
    })
  },
  // 冻结
  freeze (objectId) {
    return request({
      url: '/users/freeze/' + objectId,
      method: 'put'
    })
  },
  // 启用
  enabled (objectId) {
    return request({
      url: '/users/enabled/' + objectId,
      method: 'put'
    })
  }
}
