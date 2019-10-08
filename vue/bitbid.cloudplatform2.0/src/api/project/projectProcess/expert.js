import request from '@/utils/axios/projectflow-axios'

export const expert = {
  /**
   * 专家信息列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return request({
      url: '/expert-info',
      method: 'get',
      params: query
    })
  },
  /**
   * 专家信息详情查询
   * @param {[long]} id [主键Id]
   */
  getById (id) {
    return request({
      url: '/expert-info/' + id,
      method: 'get'
    })
  },
  /**
   * 新增/修改专家信息
   * @param {*} data [主体数据]
   */
  update (data) {
    return request({
      url: '/expert-info',
      method: 'put',
      data
    })
  },
  /**
   * 删除专家信息
   * @param {[long]} id [主键]
   */
  deleteById (id) {
    return request({
      url: '/expert-info/' + id,
      method: 'delete'
    })
  },
  /**
   * 删除专家信息
   * @param {[long]} id [主键]
   */
  deleteByIdAndSectionSystemCode (id, sectionSystemCode) {
    return request({
      url: '/expert-info/' + id + '/' + sectionSystemCode,
      method: 'delete'
    })
  },
  /**
   * 当前标段下的专家查询
   * @param {*} query [查询信息]
   */
  getByRelaSection (query) {
    return request({
      url: '/expert-info/relaSection',
      method: 'get',
      params: query
    })
  },
  /**
   * 当前标段下的专家查询
   * @param {*} query [查询信息]
   */
  getInProjectSummary (tenderSystemCode) {
    return request({
      url: '/expert-info//listInProjectSummary/' + tenderSystemCode,
      method: 'get'
    })
  }
}
