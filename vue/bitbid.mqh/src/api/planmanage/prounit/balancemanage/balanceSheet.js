import ajax from '@/common/js/ajax'

export const balanceSheet = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/balance-sheet',
      method: 'get',
      params: query
    })
  },
  /**
   * 详情查询
   */
  getByCode (code) {
    return ajax({
      url: '/balance-sheet/' + code,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/balance-sheet',
      method: 'post',
      data: data
    })
  },
  /**
   * 删除接口- 建议计划
   * @param {[long]} id [主键]
   */
  delete (objectId) {
    return ajax({
      url: '/balance-sheet/' + objectId,
      method: 'delete'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  saveAll (data) {
    return ajax({
      url: '/funds-Info',
      method: 'post',
      data: data
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getOne (enterpriseId, year) {
    return ajax({
      url: '/funds-Info/' + enterpriseId + '/' + year,
      method: 'get'
    })
  },
  /**
   * 详情查询
   */
  getOneByCode (code) {
    return ajax({
      url: '/funds-Info/query-by-code/' + code,
      method: 'get'
    })
  }

}
