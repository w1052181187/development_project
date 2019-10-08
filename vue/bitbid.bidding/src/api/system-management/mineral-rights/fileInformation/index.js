import request from '@/utils/axios/pubresource-axios'

export const sfileInfo = {
  queryList (params) {
    return request({
      url: '/file-information/list',
      method: 'get',
      params: params
    })
  }
}
