import request from '@/utils/axios/projectflow-axios'

export const costInfo = {
  /**
   * 费用信息列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return request({
      url: '/cost-info',
      method: 'get',
      params: query
    })
  },
  /**
   * 费用信息详情查询
   * @param {[long]} id [主键Id]
   */
  getById (id) {
    return request({
      url: '/cost-info/' + id,
      method: 'get'
    })
  },
  /**
   * 新增/修改费用信息
   * @param {*} data [主体数据]
   */
  update (data) {
    return request({
      url: '/cost-info',
      method: 'put',
      data
    })
  },
  /**
   * 删除费用信息
   * @param {[long]} id [主键]
   */
  deleteById (id) {
    return request({
      url: '/cost-info/' + id,
      method: 'delete'
    })
  }
}
