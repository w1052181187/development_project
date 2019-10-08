import request from '@/utils/axios/up-axios'

export const role = {
  queryList (params) {
    return request({
      url: '/roles',
      method: 'get',
      params
    })
  },
  queryOne (params) {
    return request({
      url: `/roles/${params}`,
      method: 'get'
    })
  },
  queryAll (params) {
    return request({
      url: `/roles/all/${params}`,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/roles',
      method: 'post',
      data
    })
  },
  logoff (params) {
    return request({
      url: `/roles/${params}`,
      method: 'delete'
    })
  },
  /** 判断角色名称是否重复 */
  isNoRepeated (params) {
    return request({
      url: '/roles/isNoRepeated',
      method: 'get',
      params
    })
  },
  /** 绑定权限 */
  bindPermissions (data) {
    return request({
      url: '/roles/bindPermissions',
      method: 'post',
      data
    })
  },
  /** 根据用户Id查询权限名称组,用'、'隔开 */
  queryNamesByUserId (params) {
    return request({
      url: `/roles/queryNamesByUserId/${params}`,
      method: 'get'
    })
  }
}
