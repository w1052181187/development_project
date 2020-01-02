import ajax from '@/common/js/ajax'

export const message = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/message-info',
      method: 'get',
      params: query
    })
  },
  /**
   * 数量查询
   * @param {*} query [查询信息]
   */
  count (query) {
    return ajax({
      url: '/message-info/count',
      method: 'get',
      params: query
    })
  },
  /**
   * 修改接口
   * @param {*} data [主体数据]
   */
  update (data) {
    return ajax({
      url: '/message-info',
      method: 'put',
      data
    })
  },
  /**
   * 修改接口
   * @param {*} consumedStatus [主体数据]
   */
  updateConsumedStatus (consumedStatus) {
    return ajax({
      url: '/message-info/' + consumedStatus,
      method: 'put'
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/message-info/' + id,
      method: 'delete'
    })
  }
}
