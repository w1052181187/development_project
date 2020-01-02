import ajax from '@/common/js/ajax'

export const drawresult = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/draw-lots-results',
      method: 'get',
      params: query
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getOne (id) {
    return ajax({
      url: '/draw-lots-results/query/draw/' + id,
      method: 'get'
    })
  },
  /**
   * 详情查询
   * @param {[string]} code [编号code]
   */
  getByCode (code) {
    return ajax({
      url: '/draw-lots-results/' + code,
      method: 'get'
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getEntrust (id) {
    return ajax({
      url: '/draw-lots-results/query/entrust/' + id,
      method: 'get'
    })
  },
  /**
   * 委托详情查询
   * @param {[string]} code [编号code]
   */
  getBiddingByCode (code) {
    return ajax({
      url: '/bidding-Entrusts/' + code,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  saveBiddingEntrust (data) {
    return ajax({
      url: '/bidding-Entrusts',
      method: 'put',
      data: data
    })
  },
  /**
   * 详情查询
   * @param {[string]} relatedCode [relatedCode]
   */
  getByRelatedCode (relatedCode) {
    return ajax({
      url: '/draw-lots-results/group/' + relatedCode,
      method: 'get'
    })
  }
}
