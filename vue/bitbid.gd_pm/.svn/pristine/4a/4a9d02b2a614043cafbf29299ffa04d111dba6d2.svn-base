/** 项目接口 */
import request from '@/utils/axios/up-axios'

export const quoRecord = {
  queryList (query) {
    return request({
      url: '/quotation-record',
      params: query,
      method: 'get'
    })
  },
  save (form) {
    return request({
      url: '/quotation-record',
      method: 'post',
      data: form
    })
  }
}
