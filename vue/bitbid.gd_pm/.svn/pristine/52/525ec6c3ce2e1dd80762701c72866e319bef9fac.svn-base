/** 项目接口 */
import request from '@/utils/axios/up-axios'

export const anno = {
  queryList (query) {
    return request({
      url: '/announcement',
      params: query,
      method: 'get'
    })
  },
  save (form) {
    return request({
      url: '/announcement',
      method: 'post',
      data: form
    })
  },
  detail (objectId) {
    return request({
      url: '/announcement/' + objectId,
      method: 'get'
    })
  },
  update (form) {
    return request({
      url: '/announcement',
      method: 'put',
      data: form
    })
  },
  delete (query) {
    return request({
      url: '/announcement/' + query,
      method: 'delete'
    })
  },
  publish (query) {
    return request({
      url: '/announcement/publish/' + query,
      params: query,
      method: 'post'
    })
  }
}
