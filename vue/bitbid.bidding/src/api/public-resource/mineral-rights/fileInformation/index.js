import request from '@/utils/axios/pubresource-axios'

export const fileInfo = {
  get (params) {
    return request({
      url: '/file-information',
      method: 'get',
      params: params
    })
  },
  saveFile (fileInformation) {
    return request({
      url: '/file-information',
      method: 'post',
      data: fileInformation
    })
  },
  queryList (params) {
    return request({
      url: '/file-information/list',
      method: 'get',
      params: params
    })
  },
  deleteFile (params) {
    return request({
      url: '/file-information/' + params,
      method: 'delete'
    })
  }
}
