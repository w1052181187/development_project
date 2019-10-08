import request from '@/utils/axios/projectflow-axios'

export const tenderProjectSummary = {
  /**
   * 查询指定的招标项目总结信息接口
   */
  getByRelatedCode (tenderSystemCode) {
    return request({
      url: '/tenderProjectSummary-infos/' + tenderSystemCode,
      method: 'get'
    })
  }
}
