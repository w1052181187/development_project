import request from '@/utils/axios/components-axios'

export const autoNumber = {
  /** 查询列表 */
  queryList (params) {
    return request({
      url: `/auto-numbers/queryTypeList`,
      method: 'get',
      params
    })
  },
  /** 根据主键查询 */
  queryOne (params) {
    return request({
      url: `/auto-numbers/queryTypeDetail`,
      method: 'get',
      params
    })
  },
  /** 保存 */
  update (data) {
    return request({
      url: '/auto-numbers',
      method: 'post',
      data
    })
  }
}
