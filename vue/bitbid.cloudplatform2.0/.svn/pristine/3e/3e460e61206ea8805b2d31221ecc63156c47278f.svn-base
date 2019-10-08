import request from '@/utils/axios/components-axios'

export const analysis = {
  /** 根据managementCode查询 */
  queryByManagementCode (params) {
    return request({
      url: `/opportunity-analysises/managementCode/${params}`,
      method: 'get'
    })
  },
  /** 保存/修改 */
  update (data) {
    return request({
      url: '/opportunity-analysises',
      method: 'post',
      data
    })
  }
}
