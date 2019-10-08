import request from '@/utils/axios/up-axios'

export const limitJurisdiction = {
  /**
   * 根据用户Id查询管辖范围
   * @param data
   */
  queryByUserId (params) {
    return request({
      url: `/limits-jurisdictions/queryByUserId/${params}`,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/limits-jurisdictions',
      method: 'post',
      data
    })
  }
}
