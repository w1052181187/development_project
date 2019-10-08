import request from '@/utils/axios/components-axios'

export const customer = {
  /** 查询列表 */
  queryList (params) {
    return request({
      url: `/customer-managements`,
      method: 'get',
      params
    })
  },
  /** 根据主键查询 */
  queryOne (params) {
    return request({
      url: `/customer-managements/${params}`,
      method: 'get'
    })
  },
  /** 根据code查询 */
  queryOneByCode (params) {
    return request({
      url: `/customer-managements/code/${params}`,
      method: 'get'
    })
  },
  /** 根据code查询 */
  querySelfOneByCode (params) {
    return request({
      url: `/customer-managements/selfOne/code/${params}`,
      method: 'get'
    })
  },
  /** 保存/修改 */
  update (data) {
    return request({
      url: '/customer-managements',
      method: 'post',
      data
    })
  },
  /** 修改字段属性 */
  updateSelf (data) {
    return request({
      url: '/customer-managements/updateSelf',
      method: 'post',
      data
    })
  },
  /** 删除 */
  logoff (params) {
    return request({
      url: `/customer-managements/${params}`,
      method: 'delete'
    })
  },
  /** 验证企业下名称是否重复 */
  isNoRepeated (params) {
    return request({
      url: `/customer-managements/isNoRepeated`,
      method: 'get',
      params
    })
  }
}
