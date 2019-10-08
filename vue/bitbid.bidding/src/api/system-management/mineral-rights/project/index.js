import request from '@/utils/axios/pubresource-axios'

export const sprProject = {
  queryList (params) {
    return request({
      url: '/section',
      method: 'get',
      params: params
    })
  },
  queryOne (objectId) {
    return request({
      url: '/section/' + objectId,
      method: 'get'
    })
  },
  update (section) {
    return request({
      url: '/section/',
      method: 'put',
      data: section
    })
  }
}
