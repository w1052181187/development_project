import request from '@/utils/axios'

export const projectInfo = {
  queryList (query) {
    return request({
      url: '/project',
      params: query,
      method: 'get'
    })
  },
  // 跟新定标状态（1是定标， 0是为定标）
  updateBidStatus (objectId) {
    return request({
      url: '/project/updateProjectStatus/' + objectId,
      method: 'post'
    })
  },
  detail (objectId) {
    return request({
      url: '/project/' + objectId,
      method: 'get'
    })
  },
  add (data) {
    return request({
      url: '/project',
      data: data,
      method: 'post'
    })
  },
  update (data) {
    return request({
      url: '/project/update',
      data: data,
      method: 'post'
    })
  },
  // 根据项目Id获取项目信息及定标人信息和监标人信息和竞价开始时间
  getProjectInfo (objectId, query) {
    return request({
      url: '/project/getObjectId/' + objectId,
      params: query,
      method: 'get'
    })
  },
  scalingEndInfo (objectId, query) {
    return request({
      url: '/project/getScalingEndInfo/' + objectId,
      params: query,
      method: 'get'
    })
  },
  getScalingProjectInfo (objectId, query) {
    return request({
      url: '/project/getScalingProjectInfo/' + objectId,
      params: query,
      method: 'get'
    })
  },
  getScalingReport (objectId, query) {
    return request({
      url: '/project/getScalingReport/' + objectId,
      params: query,
      method: 'get'
    })
  },
  getScalingEndReport (objectId, query) {
    return request({
      url: '/project/getScalingEndReport/' + objectId,
      params: query,
      method: 'get'
    })
  },
  updateCandidateUser (projectId, data, query) {
    return request({
      url: '/project/updateCandidateUserList/' + projectId,
      data: data,
      params: query,
      method: 'post'
    })
  },
  // 同步数据-竞价室
  synDataToBidRoom (projectId, query) {
    return request({
      url: '/project/roomData/' + projectId,
      params: query,
      method: 'get'
    })
  },
  // 竞价室-下达开始命令
  roomDataPage (projectId, query) {
    return request({
      url: '/project/roomDataPage/' + projectId,
      params: query,
      method: 'get'
    })
  },
  // 控制多轮-确定下一轮
  updateCandidateUserListPage (projectId, query) {
    return request({
      url: '/project/updateCandidateUserListPage/' + projectId,
      params: query,
      method: 'get'
    })
  },
  getBidFile (projectId) {
    return request({
      url: '/project/getBidFile/' + projectId,
      method: 'get'
    })
  },
  getBuyerFile (projectId) {
    return request({
      url: '/project/getBuyerFile/' + projectId,
      method: 'get'
    })
  },
  getTenderFile (projectId) {
    return request({
      url: '/project/getTenderFile/' + projectId,
      method: 'get'
    })
  }
}
