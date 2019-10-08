import request from '@/utils/axios'

export const report = {
  getReport (projectId) {
    return request({
      url: '/report/view/' + projectId,
      method: 'get'
    })
  },
  // 获取pdf
  pdfDetail (query) {
    return request({
      url: '/report/PDFSignatureServer/index',
      method: 'get',
      params: query
    })
  },
  createReport (projectId) {
    return request({
      url: '/report/createReport/' + projectId,
      method: 'post'
    })
  },
  downloadReport (projectId) {
    return request({
      url: '/report/downloadReport/' + projectId,
      method: 'get'
    })
  },
  confirmReport (projectId, objectId) {
    return request({
      url: '/report/confirm/' + projectId + '/' + objectId,
      method: 'post'
    })
  }
}
