import request from '@/utils/axios/components-axios'

export const followup = {
  /** 查询列表 */
  queryList (params) {
    return request({
      url: `/follow-up-records`,
      method: 'get',
      params
    })
  },
  /** 根据主键查询 */
  queryOne (params) {
    return request({
      url: `/follow-up-records/${params}`,
      method: 'get'
    })
  },
  /** 保存/修改 */
  update (data) {
    return request({
      url: '/follow-up-records',
      method: 'post',
      data
    })
  },
  /** 删除 */
  logoff (params) {
    return request({
      url: `/follow-up-records/${params}`,
      method: 'delete'
    })
  }
}
