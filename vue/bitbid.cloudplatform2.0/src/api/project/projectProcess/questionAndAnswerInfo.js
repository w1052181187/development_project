import request from '@/utils/axios/projectflow-axios'

export const questionAndAnswerInfo = {
  /**
   * 异议与澄清信息列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return request({
      url: '/questionAndAnswer-info',
      method: 'get',
      params: query
    })
  },
  /**
   * 异议与澄清信息详情查询
   * @param {[long]} id [主键Id]
   */
  getById (id) {
    return request({
      url: '/questionAndAnswer-info/' + id,
      method: 'get'
    })
  },
  /**
   * 新增/修改异议与澄清信息
   * @param {*} data [主体数据]
   */
  update (data) {
    return request({
      url: '/questionAndAnswer-info',
      method: 'put',
      data
    })
  },
  /**
   * 删除异议与澄清信息
   * @param {[long]} id [主键]
   */
  deleteById (id) {
    return request({
      url: '/questionAndAnswer-info/' + id,
      method: 'delete'
    })
  }
}
