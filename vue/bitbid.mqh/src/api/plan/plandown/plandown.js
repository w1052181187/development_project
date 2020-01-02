import ajax from '@/common/js/ajax'

export const plandown = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/purchase-suggested',
      method: 'get',
      params: query
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getProject (query) {
    return ajax({
      url: '/project',
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
      url: '/purchase-suggested',
      method: 'put',
      data
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/purchase-suggested/' + id,
      method: 'delete'
    })
  }
}
