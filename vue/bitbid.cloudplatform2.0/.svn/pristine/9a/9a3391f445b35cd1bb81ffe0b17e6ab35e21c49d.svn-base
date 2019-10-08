import request from '@/utils/axios/projectflow-axios'

export const approvalNode = {
  // 查询当前企业所对应的审批节点
  approvalNodeList (query) {
    return request({
      url: '/approvalNodes',
      method: 'get',
      params: query
    })
  },
  // 修改/新增
  approvalNodeSave (query) {
    return request({
      url: '/approvalNodes',
      method: 'post',
      data: query
    })
  },
  // 修改审批节点是否启用状态
  approvalNodeUpdateStatus (query) {
    return request({
      url: '/approvalNodes/' + query,
      method: 'put'
    })
  },
  // 修改审批节点是否启用状态
  checkedIsNeedFlow (flowStatus) {
    return request({
      url: '/approvalNodes/checkedIsNeedFlow/' + flowStatus,
      method: 'get'
    })
  }
}
