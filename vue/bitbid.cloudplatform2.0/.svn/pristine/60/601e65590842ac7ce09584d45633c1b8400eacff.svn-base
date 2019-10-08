import request from '@/utils/axios/up-axios'

export const parameterSetting = {
  /** 查询列表 */
  queryOne (params) {
    return request({
      url: `/parameter-settings/${params}`,
      method: 'get',
      params
    })
  },
  /** 保存 */
  update (data) {
    return request({
      url: '/parameter-settings',
      method: 'post',
      data
    })
  }
}
