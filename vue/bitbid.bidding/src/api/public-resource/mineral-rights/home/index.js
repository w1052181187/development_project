/**
 * Created by lvjiawei on 2018-8-29.
 */
import request from '@/utils/axios/pubresource-axios'

export const home = {
  // 查询列表
  homeList (url) {
    return request({
      url: '/home/list',
      method: 'get',
      params: url
    })
  }
}
