import request from '@/utils/axios/projectflow-axios'
export const approvalRecord = {
  // 查询当前企业所对应的审批记录
  approvalRecordList (query) {
    return request({
      url: '/recordOfApprovals',
      method: 'get',
      params: query
    })
  },
  // 保存
  approvalRecordSave (query) {
    return request({
      url: '/recordOfApprovals',
      method: 'post',
      params: query
    })
  }
}
