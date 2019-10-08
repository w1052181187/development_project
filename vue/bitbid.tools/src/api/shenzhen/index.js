import request from '@/utils/axios/components-axios'

export const szca = {
  // 深圳CA接口
  szcaSub (query, params) {
    return request({
      url: '/szca/verifyLogin/' + query + '/' + params,
      method: 'get'
    })
  }
}
