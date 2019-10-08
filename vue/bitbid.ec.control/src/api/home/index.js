import request from '@/utils/axios'

export const home = {
  /**
   * 列表查询-成交公示
   * @param {*} query [查询信息]
   */
  getList (query) {
    return request({
      url: 'Index/transactionRecord',
      method: 'get',
      params: query
    })
  },
  /**
   * 列表查询-政策法规
   * @param {*} query [查询信息]
   */
  getListLaws (query) {
    return request({
      url: 'Index/news',
      method: 'get',
      params: query
    })
  },
  /**
   * 列表查询-信用评价
   * @param {*} query [查询信息]
   */
  getListEval (query) {
    return request({
      url: 'Index/creditEvaluation',
      method: 'get',
      params: query
    })
  },
  /**
   * 列表查询-电商平台名称
   * @param {*} query [查询信息]
   */
  getListEcname (query) {
    return request({
      url: 'Index/merchantName',
      method: 'get',
      params: query
    })
  },
  /**
   * 列表查询-成交公示-详情
   * @param {*} query [查询信息]
   */
  queryOne (params) {
    return request({
      url: `Index/query/${params}`,
      method: 'get'
    })
  },
  queryLawsOne (params) {
    return request({
      url: `Index/queryNewsByObjectId/${params}`,
      method: 'get'
    })
  },
  /**
   * 广告
   */
  getAdList (query) {
    return request({
      url: 'Index/ad',
      method: 'get',
      params: query
    })
  }
}
