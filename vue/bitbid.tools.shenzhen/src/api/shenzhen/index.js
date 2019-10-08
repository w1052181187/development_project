import request from '@/utils/axios/components-axios'

export const szca = {
  // 深圳CA接口
  szcaSub (query) {
    return request({
      url: '/szca/verifyLogin',
      method: 'post',
      data: query
    })
  }
}
