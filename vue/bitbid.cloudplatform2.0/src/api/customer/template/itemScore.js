import request from '@/utils/axios/components-axios'

export const itemScore = {
  /** 查询列表 */
  queryList (params) {
    return request({
      url: `/item-scores`,
      method: 'get',
      params
    })
  },
  /** 保存/修改 */
  update (data) {
    return request({
      url: '/item-scores',
      method: 'post',
      data
    })
  }
}
