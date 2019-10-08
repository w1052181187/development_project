
import request from '@/utils/axios/projectflow-axios'

export const approvalFlow = {
  // 通过审批节点查询审批流程
  approvalFlowList (query) {
    return request({
      url: '/approvalFlows/' + query,
      method: 'get'
    })
  },
  // 批量修改/新增
  approvalFlowListUpdate (query) {
    return request({
      url: '/approvalFlows',
      method: 'post',
      data: query
    })
  }
}
