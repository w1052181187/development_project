import request from '@/utils/axios/projectflow-axios'

export const agencyContract = {
  /**
   * 查询招标项目接口
   */
  getList (query) {
    return request({
      url: '/agencyContract-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询单条招标项目接口
   */
  getOne (code) {
    return request({
      url: '/agencyContract-infos/' + code,
      method: 'get'
    })
  },
  /**
   * 新增招标项目接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return request({
      url: '/agencyContract-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 删除招标项目接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return request({
      url: '/agencyContract-infos/' + id,
      method: 'delete'
    })
  }
}
