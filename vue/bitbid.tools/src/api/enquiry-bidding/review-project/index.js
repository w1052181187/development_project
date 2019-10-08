import request from '@/utils/axios/components-axios'

export const reviewProject = {
  // 组建小组列表
  setUpNegotiationList (query) {
    return request({
      url: '/user',
      method: 'get',
      params: query
    })
  },
  // 增加小组
  setUpNegotiationAdd (query) {
    return request({
      url: '/user',
      method: 'post',
      data: query
    })
  },
  // 查看小组
  setUpNegotiationLook (query) {
    return request({
      url: '/user/' + query,
      method: 'get'
    })
  },
  // 编辑小组后提交数据
  setUpNegotiationUpdate (query) {
    return request({
      url: '/user/update',
      method: 'post',
      data: query
    })
  },
  // 提交小组组长
  setLeader (objectId, oldRole, newRole) {
    return request({
      url: '/user/update/' + objectId + '/' + oldRole + '/' + newRole,
      method: 'post'
    })
  },
  // 查看小组
  setUpNegotiationDelete (query) {
    return request({
      url: '/user/' + query,
      method: 'delete'
    })
  },
  // ************************************评审项目设置***********************************************
  // 左侧边评审项目列表
  navReviewProjectList (query) {
    return request({
      url: '/checkType',
      method: 'get',
      params: query
    })
  },
  // 右侧边表格列表
  tableProjectList (query) {
    return request({
      url: '/checkItem',
      method: 'get',
      params: query
    })
  }
}
