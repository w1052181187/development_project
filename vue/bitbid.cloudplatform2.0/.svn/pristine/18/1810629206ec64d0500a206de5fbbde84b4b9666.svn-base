import request from '@/utils/axios/components-axios'

export const category = {
  /**
   * 业务模块列表查询
   * @param {*} query [查询信息]
   */
  getCategoryList (query) {
    return request({
      url: '/categorys',
      method: 'get',
      params: query
    })
  },
  getCategoryLevelList (parentId) {
    return request({
      url: '/categorys/' + parentId,
      method: 'get'
    })
  }
}
