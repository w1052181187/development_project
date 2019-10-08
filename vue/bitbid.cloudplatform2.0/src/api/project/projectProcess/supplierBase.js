import request from '@/utils/axios/projectflow-axios'

export const supplierBase = {
  /**
   * 查询供应商库接口
   */
  getList (query) {
    return request({
      url: '/supplierBase-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 新增供应商库接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return request({
      url: '/supplierBase-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 查询单条供应商库接口
   */
  getOne (code) {
    return request({
      url: '/supplierBase-infos/' + code,
      method: 'get'
    })
  }
}
