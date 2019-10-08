import request from '@/utils/axios/projectflow-axios'

export const approvalTask = {
  // 我发起的审批任务
  launchedByMe (query) {
    return request({
      url: '/approvalTasks/launchedByMe',
      method: 'get',
      params: query
    })
  },
  // 我发起的审批任务详情
  detail (objectId) {
    return request({
      url: '/approvalTasks/' + objectId,
      method: 'get'
    })
  },
  // 撤回我的审批任务
  recallMyTask (query) {
    return request({
      url: '/approvalTasks/' + query,
      method: 'put'
    })
  },
  // 待我审批的审批任务
  needMeApproval (query) {
    return request({
      url: '/approvalTasks/needMeApproval',
      method: 'get',
      params: query
    })
  },
  // 待我审批的审批任务详情
  needMeApprovalOne (objectId) {
    return request({
      url: '/approvalTasks/needMeApproval/' + objectId,
      method: 'get'
    })
  }
}
