import request from '@/utils/axios'

export const drawRecord = {
  getRecord (params) {
    return request({
      url: '/drawRecord',
      params: params,
      method: 'get'
    })
  },
  addRecord (data, query) {
    return request({
      url: '/drawRecord',
      params: query,
      data: data,
      method: 'post'
    })
  },
  updateRecord (data) {
    return request({
      url: '/drawRecord/update',
      data: data,
      method: 'post'
    })
  },
  revokeRecord (data, query) {
    return request({
      url: '/drawRecord/updateByProjectId',
      params: query,
      data: data,
      method: 'post'
    })
  },
  submitRecord (projectId, query) {
    return request({
      url: '/drawRecord/submit/' + projectId,
      params: query,
      method: 'post'
    })
  },
  confirmRecord (projectId, query) {
    return request({
      url: '/drawRecord/confirm/' + projectId,
      params: query,
      method: 'post'
    })
  },
  // 设置就位
  setReadyUser (projectId, query) {
    return request({
      url: '/drawRecord/setReadyUser/' + projectId,
      params: query,
      method: 'get'
    })
  },
  // 获取就位
  getReadyUser (projectId, query) {
    return request({
      url: '/drawRecord/getReadyUser/' + projectId,
      params: query,
      method: 'get'
    })
  }
}
