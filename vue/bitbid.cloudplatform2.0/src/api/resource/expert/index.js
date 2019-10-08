import request from '@/utils/axios/components-axios'

export const expert = {
  /** 查询列表 */
  queryList (params) {
    return request({
      url: `/expert-managements`,
      method: 'get',
      params
    })
  },
  /** 根据主键查询 */
  queryOne (params) {
    return request({
      url: `/expert-managements/${params}`,
      method: 'get'
    })
  },
  /** 保存/修改 */
  update (data) {
    return request({
      url: '/expert-managements',
      method: 'post',
      data
    })
  },
  /** 删除 */
  logoff (params) {
    return request({
      url: `/expert-managements/${params}`,
      method: 'delete'
    })
  },
  /** 查询名称 */
  queryName (params) {
    return request({
      url: `/expert-managements/queryName/${params}`,
      method: 'get'
    })
  },
  /** 验证重复 */
  isNoRepeated (params) {
    return request({
      url: `/expert-managements/isNoRepeated`,
      method: 'get',
      params
    })
  }
}
