import request from '@/utils/axios/up-axios'

export const enterprise = {
  /**
   * 查看企业信息
   * @param id
   */
  queryOne (id) {
    return request({
      url: `/enterprises/${id}`,
      method: 'get'
    })
  },
  /**
   * 完善企业信息
   * @param data
   */
  update (data) {
    return request({
      url: '/enterprises',
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
