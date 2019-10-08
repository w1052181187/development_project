/** 项目接口 */
import request from '@/utils/axios/up-axios'

export const versionManagement = {
  queryList (query) {
    return request({
      url: '/versionManagement',
      params: query,
      method: 'get'
    })
  },
  update (form) {
    return request({
      url: '/versionManagement',
      method: 'put',
      data: form
    })
  },
  queryOne (objectId) {
    return request({
      url: '/versionManagement/' + objectId,
      method: 'get'
    })
  },
  queryNotPageList (query) {
    return request({
      url: '/versionManagement/queryNotPageList',
      params: query,
      method: 'get'
    })
  }
}
