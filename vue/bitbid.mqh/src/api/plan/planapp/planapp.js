import ajax from '@/common/js/ajax'

export const planapp = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/annualProject-plans',
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
      url: '/annualProject-plans/query/' + id,
      method: 'get'
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  queryOne (query) {
    return ajax({
      url: '/annualProject-plans/query/',
      method: 'get',
      params: query
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (objectId) {
    return ajax({
      url: '/annualProject-plans/' + objectId,
      method: 'delete'
    })
  }
}
