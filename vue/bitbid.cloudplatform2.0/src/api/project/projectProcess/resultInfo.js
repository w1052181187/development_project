import request from '@/utils/axios/projectflow-axios'

export const resultInfo = {
  /**
   * 结果信息列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return request({
      url: '/result-info',
      method: 'get',
      params: query
    })
  },
  /**
   * 结果信息详情查询
   * @param {[long]} id [主键Id]
   */
  getById (id) {
    return request({
      url: '/result-info/' + id,
      method: 'get'
    })
  },
  /**
   * 新增/修改结果信息
   * @param {*} data [主体数据]
   */
  update (data) {
    return request({
      url: '/result-info',
      method: 'put',
      data
    })
  },
  /**
   * 删除结果信息
   * @param {[long]} id [主键]
   */
  deleteById (id) {
    return request({
      url: '/result-info/' + id,
      method: 'delete'
    })
  }
}
