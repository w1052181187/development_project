import request from '@/utils/axios/projectflow-axios'

export const projectResponse = {
  /**
   * 直采 查询供应商报价 列表
   * @param query
   */
  directList (query) {
    return request({
      url: '/project/projectResponse',
      method: 'get',
      params: query
    })
  },
  /**
   * 直采 查询供应商报价 单一查询
   * @param query
   */
  directDetail (query) {
    return request({
      url: '/project/projectResponse/detail',
      method: 'get',
      params: query
    })
  },
  /**
   * 保存单一信息
   * @param data
   */
  saveOffer (data) {
    return request({
      url: '/project/projectResponse/save',
      method: 'post',
      data: data
    })
  },
  /**
   * 删除单一信息
   * @param data
   */
  delete (data) {
    return request({
      url: '/project/projectResponse/delete',
      method: 'delete',
      data: data
    })
  },
  /**
   * 删除并重建
   * @param data
   */
  rebuild (data) {
    return request({
      url: '/project/projectResponse/rebuild',
      method: 'delete',
      data: data
    })
  },
  /**
   * 根据供应商id和项目id查询供应商报价状态
   * @param params
   */
  getProjectResponseStatus (params) {
    return request({
      url: '/project/projectResponse/getProjectResponseStatus',
      method: 'get',
      params: params
    })
  }
}
