import request from '@/utils/axios/projectflow-axios'

export const directPurchase = {
  /**
   * 直采列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return request({
      url: '/project/direct',
      method: 'get',
      params: query
    })
  },
  getOne (objectId) {
    return request({
      url: '/project/direct/' + objectId,
      method: 'get'
    })
  }
}
