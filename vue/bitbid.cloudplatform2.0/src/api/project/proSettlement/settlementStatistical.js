import request from '@/utils/axios/components-axios'

export const settlementStatistical = {
  /**
   * 查询招标项目接口
   */
  getList (tenderSystemCode) {
    return request({
      url: '/settlementStatistical-infos/' + tenderSystemCode,
      method: 'get'
    })
  }
}
