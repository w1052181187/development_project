import request from '@/utils/axios/projectflow-axios'

export const signUpInfo = {
  /**
   * 报名信息列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return request({
      url: '/signUp-info',
      method: 'get',
      params: query
    })
  },
  /**
   * 报名信息详情查询
   * @param {[long]} id [主键Id]
   */
  getById (id) {
    return request({
      url: '/signUp-info/' + id,
      method: 'get'
    })
  },
  /**
   * 新增/修改报名信息
   * @param {*} data [主体数据]
   */
  update (data) {
    return request({
      url: '/signUp-info',
      method: 'put',
      data
    })
  },
  /**
   * 删除报名信息
   * @param {[long]} id [主键]
   */
  deleteById (id) {
    return request({
      url: '/signUp-info/' + id,
      method: 'delete'
    })
  }
}
