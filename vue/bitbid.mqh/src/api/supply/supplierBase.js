import ajax from '@/common/js/ajax'

export const supplierBase = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/supplierBase-info',
      method: 'get',
      params: query
    })
  },
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getOne (query) {
    return ajax({
      url: '/supplierBase-info/selectOne',
      method: 'get',
      params: query
    })
  },
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getSupplierJoinProjects (query) {
    return ajax({
      url: '/supplierJoinProject-info',
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
      url: '/supplierBase-info',
      method: 'put',
      data
    })
  },
  /**
   * 修改接口
   * @param {*} data [主体数据]
   */
  updateBlacklistEndDate (query) {
    return ajax({
      url: '/supplierBase-info/blacklist',
      method: 'put',
      params: query
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/supplierBase-info/' + id,
      method: 'delete'
    })
  }
}
