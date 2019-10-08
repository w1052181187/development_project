import request from '@/utils/axios'

export const scalingPrepare = {
  getDetail (objectId) {
    return request({
      url: '/project/getObjectId/' + objectId,
      method: 'get'
    })
  },
  add (data, query) {
    return request({
      url: '/user',
      params: query,
      data: data,
      method: 'post'
    })
  },
  // add (data) {
  //   return request({
  //     url: '/user',
  //     data: data,
  //     method: 'post'
  //   })
  // },
  update (data, query) {
    return request({
      url: '/user/update',
      params: query,
      data: data,
      method: 'post'
    })
  },
  delete (objectId, query) {
    return request({
      url: '/user/' + objectId,
      params: query,
      method: 'delete'
    })
  },
  // 设置组长
  setLeader (projectId, objectId, newRole, oldRole, query) {
    return request({
      url: '/user/update/' + projectId + '/' + objectId + '/' + newRole + '/' + oldRole,
      params: query,
      method: 'post'
    })
  },
  getUserInfo (objectId, query) {
    return request({
      url: '/user/getUserById/' + objectId,
      params: query,
      method: 'get'
    })
  },
  startScaling (projectId, query) {
    return request({
      url: '/result/beginScalingsystem/' + projectId,
      params: query,
      method: 'post'
    })
  },
  getStartScaling (projectId, query) {
    return request({
      url: '/result/beginScalingsystemPage/' + projectId,
      params: query,
      method: 'get'
    })
  },
  getActiveUser (projectId) {
    return request({
      url: '/activeUser/' + projectId,
      method: 'get'
    })
  },
  // 组建定标委员会（流程）
  getVoteRoomPage (projectId) {
    return request({
      url: '/user/createUserPage/' + projectId,
      method: 'get'
    })
  }
}
