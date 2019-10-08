/**
 * Created by lvke on 2018/7/26.
 */

import request from '@/utils/axios/pubresource-axios'

export const notice = {
  // 公告列表
  announcementList (query) {
    return request({
      url: '/announcement',
      method: 'get',
      params: query
    })
  }
}
