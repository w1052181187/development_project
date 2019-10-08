/** 项目接口 */
import request from '@/utils/axios/up-axios'

export const settlement = {
  // 结算单
  generateList (query) {
    return request({
      url: '/settlement-summary/queryFinalState',
      params: query,
      method: 'get'
    })
  },
  // 保存
  save (form) {
    return request({
      url: '/settlement-summary',
      method: 'post',
      data: form
    })
  },
  // 根据id查询出 所关联的合作代理机构id
  queryByUserId (query) {
    return request({
      url: '/settlement-summary/queryByUserId/' + query,
      method: 'get'
    })
  },
  // 列表
  queryList (query) {
    return request({
      url: '/settlement-summary',
      params: query,
      method: 'get'
    })
  },
  // 详情
  queryDeail (query) {
    return request({
      url: '/settlement-summary/' + query,
      method: 'get'
    })
  },
  // 修改
  queryUpdate (form) {
    return request({
      url: '/settlement-summary',
      method: 'put',
      data: form
    })
  },
  // 明细(添加)
  detailListAdd (query) {
    return request({
      url: '/settlement-summary/quertNotPageList',
      params: query,
      method: 'get'
    })
  },
  // 明细
  detailList (settleId) {
    return request({
      url: '/settlement-summary/quertBySettleId/' + settleId,
      method: 'get'
    })
  },
  // 审批
  approveList (form) {
    return request({
      url: '/approve-record',
      data: form,
      method: 'post'
    })
  },
  // 修改审批状态
  updateApproveStatus (form) {
    return request({
      url: '/settlement-summary/updateStatus',
      data: form,
      method: 'put'
    })
  },
  // 根据登录的用户,查询这个用户所关联的渠道
  detailApply (settleId) {
    return request({
      url: '/settlement-summary/queryVersionByUserId/' + settleId,
      method: 'get'
    })
  }
}
