import request from '@/utils/axios/up-axios'

export const useradmin = {
  // 用户列表
  userList (query) {
    return request({
      url: '/users',
      method: 'get',
      params: query
    })
  },
  // 部门列表
  detList (query) {
    return request({
      url: '/users/enterpriseId/' + query,
      method: 'get'
    })
  },
  // 添加用户
  userAdd (query) {
    return request({
      url: '/users',
      method: 'post',
      data: query
    })
  },
  // 删除用户
  userDelete (query) {
    return request({
      url: '/users/' + query,
      method: 'delete'
    })
  },
  // 修改用户时从数据库查找这条修改的数据
  userEditlook (query) {
    return request({
      url: '/users/id/' + query,
      method: 'get'
    })
  },
  // 查出后进行修改
  userEdit (query) {
    return request({
      url: '/users',
      method: 'put',
      data: query
    })
  },
  // 搜索接口
  userSearch (query) {
    return request({
      url: '/users',
      method: 'get',
      params: query
    })
  },
  // 禁用启用接口
  userDisable (query) {
    return request({
      url: '/users/disable',
      method: 'put',
      data: query
    })
  },
  // 分配权限列表接口
  userDistribution (enterpriseId, obj) {
    return request({
      url: '/users///' + enterpriseId,
      method: 'get',
      params: obj
    })
  },
  // 分配权限列表搜索接口
  userDistrSearch (enterpriseId, obj) {
    return request({
      url: '/users///' + enterpriseId,
      method: 'get',
      params: obj
    })
  },
  // 分配权限接口
  userDistr (query) {
    return request({
      url: '/users/refRoleUser/',
      method: 'post',
      data: query
    })
  },
  // 档案管理中部门和经理接口（调用比比网）
  deptManagerlistbibi (query) {
    return request({
      url: '/users///' + query,
      method: 'get'
    })
  },
  // 档案管理中部门和经理接口
  deptManagerlist (query) {
    return request({
      url: '/users/userId/' + query,
      method: 'get'
    })
  },
  // *************************************************河南数据***************************************
  // 列表
  henanuserList (query) {
    return request({
      url: '/users/selectList',
      method: 'get',
      params: query
    })
  },
  // 增加
  henanuserAdd (query) {
    return request({
      url: '/users/save',
      method: 'post',
      data: query
    })
  },
  // 删除用户
  henanuserDelete (query) {
    return request({
      url: '/users/delete/' + query,
      method: 'delete'
    })
  },
  // 修改用户时从数据库查找这条修改的数据
  henanuserEditlook (query) {
    return request({
      url: `/users/detail/${query}?_t=${new Date().getTime()}`,
      method: 'get'
    })
  },
  // 修改用户
  henanuserEdit (query) {
    return request({
      url: '/users/update',
      method: 'put',
      data: query
    })
  },
  // 部门列表
  depthenandetList (query) {
    return request({
      url: '/departments/selectList',
      method: 'get',
      params: query
    })
  },
  // 禁用启用接口
  henanuserDisable (userId, disable) {
    return request({
      url: '/users/disable_hn/' + userId + '/' + disable,
      method: 'put'
    })
  },
  // 分配角色列表接口
  henanuserDistribution (query) {
    return request({
      url: '/roles',
      method: 'get',
      params: query
    })
  },
  // 分配权限接口
  henanuserDistr (query) {
    return request({
      url: '/users/distributeRole/',
      method: 'post',
      data: query
    })
  },
  // 搜索接口
  henanuserSearch (query) {
    return request({
      url: '/users/selectList',
      method: 'get',
      params: query
    })
  }
}
