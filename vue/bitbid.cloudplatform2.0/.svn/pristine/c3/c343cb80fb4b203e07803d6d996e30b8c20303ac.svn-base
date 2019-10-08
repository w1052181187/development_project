import request from '@/utils/axios/projectflow-axios'
export const recordOfCommunicate = {
  // 查询当前企业所对应的审批记录
  getRecordOfCommunicateList (query) {
    return request({
      url: '/recordOfCommunicates',
      method: 'get',
      params: query
    })
  },
  // 保存
  saveRecordOfCommunicate (query) {
    return request({
      url: '/recordOfCommunicates',
      method: 'post',
      data: query
    })
  }
}
