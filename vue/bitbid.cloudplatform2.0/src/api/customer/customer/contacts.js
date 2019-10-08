import request from '@/utils/axios/components-axios'

export const contacts = {
  /** 查询列表 */
  queryList (params) {
    return request({
      url: `/contacts`,
      method: 'get',
      params
    })
  }
}
