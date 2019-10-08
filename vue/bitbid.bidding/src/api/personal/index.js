/**
 * Created by lixuqiang on 2018/7/6.
 */
import request from '@/utils/axios/up-axios'

export const application = {
  applicationList (query) {
    return request({
      url: '/module-managements/userId/' + query,
      method: 'get'
    })
  },
  applicationAdd (query) {
    return request({
      url: '/module-managements',
      method: 'post',
      data: query
    })
  }
}
