import request from '@/utils/axios/up-axios'

export const rotationInfo = {
  queryList (params) {
    return request({
      url: '/home-rotation-info',
      method: 'get',
      params: params
    })
  },
  save (fileInformation) {
    return request({
      url: '/home-rotation-info',
      method: 'post',
      data: fileInformation
    })
  },
  update (params) {
    return request({
      url: '/home-rotation-info',
      method: 'put',
      params: params
    })
  },
  remove (params) {
    return request({
      url: '/home-rotation-info/' + params,
      method: 'delete'
    })
  },
  detail (params) {
    return request({
      url: '/home-rotation-info/' + params,
      method: 'get'
    })
  },
  disableEnabled (objectId, status) {
    return request({
      url: '/home-rotation-info/disableEnabled/' + objectId + '/' + status,
      method: 'put'
    })
  }
}
