import request from '@/utils/axios/up-axios'

export const attachinfo = {
  /**
   * 查看企业附加信息
   * @param params
   */
  queryOne (params) {
    return request({
      url: '/attachInfo',
      params: params,
      method: 'get'
    })
  },
  /**
   * 完善企业信息
   * @param data
   */
  update (data) {
    return request({
      url: '/attachInfo',
      method: 'post',
      data: data
    })
  },
  /**
   * 验证企业邀请码
   */
  verInvitationCode (params) {
    return request({
      url: `/enterprises/verInvitationCode`,
      method: 'get',
      params
    })
  }
}
