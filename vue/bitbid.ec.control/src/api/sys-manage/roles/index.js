import request from '@/utils/axios'

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
  deleteRole (params) {
    return request({
      url: `/roles/${params}`,
      method: 'delete'
    })
  },
  saveUserRoleAssociation (data) {
    return request({
      url: `/roles/saveUserRoleAssociation`,
      method: 'post',
      data
    })
  },
  menuList (params) {
    return request({
      url: '/roles/menuResources',
      method: 'get',
      params
    })
  },
  saveMenuRoleAssociation (data) {
    return request({
      url: `/roles/saveMenuRoleAssociation`,
      method: 'post',
      data
    })
  },
  menuRoleList (params) {
    return request({
      url: '/roles/roleMenuResources',
      method: 'get',
      params
    })
  }
}
