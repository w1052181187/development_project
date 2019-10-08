import request from '@/utils/axios/components-axios'

export const expertProject = {
  /** 查询列表 */
  queryList (params) {
    return request({
      url: `/expert-projects`,
      method: 'get',
      params
    })
  },
  /** 查询已经包含的标段Code组 */
  queryIncludeSectionCodes (params) {
    return request({
      url: `/expert-projects/queryIncludeSectionIds/${params}`,
      method: 'get'
    })
  },
  /** 保存/修改 */
  saveBatch (data) {
    return request({
      url: '/expert-projects/saveBatch',
      method: 'post',
      data
    })
  },
  /** 删除 */
  logoff (params) {
    return request({
      url: `/expert-projects/${params}`,
      method: 'delete'
    })
  }
}
