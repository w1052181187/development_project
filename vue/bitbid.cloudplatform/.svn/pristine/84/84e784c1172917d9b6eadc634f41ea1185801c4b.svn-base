import request from '@/utils/axios/projectflow-axios'

export const projectFlow = {
  /**
   * 查询项目流程接口,以数组形式展示给前台页面
   */
  getList (query) {
    return request({
      url: '/projectFlow-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 新增项目流程接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return request({
      url: '/projectFlow-infos',
      method: 'post',
      data: data
    })
  }
}
