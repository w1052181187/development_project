/**
 * Created by lvke on 2018/7/26.
 */

import request from '@/utils/axios/pubresource-axios'

export const sanno = {
  // 查询列表
  announcementList (query) {
    return request({
      url: '/announcement',
      method: 'get',
      params: query
    })
  },
  // 查询公告详情
  announcementDetail (query) {
    return request({
      url: '/announcement/' + query,
      method: 'get'
    })
  },
  // 修改公告信息
  announcementUpdate (query) {
    return request({
      url: '/announcement',
      method: 'put',
      data: query
    })
  },
  // 查询列表(补充公告)
  announcementSuupList (query) {
    return request({
      url: '/announcement/queryList',
      method: 'get',
      params: query
    })
  }
}
