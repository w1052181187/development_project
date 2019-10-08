import request from '@/utils/axios/projectflow-axios'

export const evaluateInfo = {
  /**
   * 查询招标项目接口
   */
  getList (query) {
    return request({
      url: '/evaluateInfo-infos',
      method: 'get',
      params: query
    })
  }
}
