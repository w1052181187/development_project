import request from '@/utils/axios/up-axios'

export const permission = {
  /**
   * 获取所有权限，并按照模块Id分类
   * @param data
   */
  queryList (data) {
    return request({
      url: '/permissions',
      method: 'get'
    })
  },
  queryByRoleId (params) {
    return request({
      url: `/permissions/queryByRoleId/${params}`,
      method: 'get'
    })
  }
}
