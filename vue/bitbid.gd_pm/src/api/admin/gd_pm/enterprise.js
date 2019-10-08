/** 项目接口 */
import request from '@/utils/axios/up-axios'

export const enterprise = {
  // 查询企业信息
  enterpriseInfo (userId) {
    return request({
      url: '/sys-user-expand/' + userId,
      method: 'get'
    })
  },
  // 修改企业信息
  enterpriseUpdate (form) {
    return request({
      url: '/sys-user-expand',
      data: form,
      method: 'put'
    })
  }
}
