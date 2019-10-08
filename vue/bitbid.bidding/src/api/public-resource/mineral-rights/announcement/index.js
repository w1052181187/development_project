/**
 * Created by lvke on 2018/7/26.
 */

import request from '@/utils/axios/pubresource-axios'

export const anno = {
  // 查询列表
  announcementList (query) {
    return request({
      url: '/announcement',
      method: 'get',
      params: query
    })
  },
  // 查询公告是否可以新增补充公告
  annoQueryIsAddSupp (query) {
    return request({
      url: '/announcement/isAddSupp/' + query,
      method: 'get'
    })
  },
  // 查询公告详情
  announcementDetail (query) {
    return request({
      url: '/announcement/' + query,
      method: 'get'
    })
  },
  // 删除一条公告
  announcementDelete (query) {
    return request({
      url: '/announcement/' + query,
      method: 'delete'
    })
  },
  // 新增一条公告信息
  announcementAdd (query) {
    return request({
      url: '/announcement',
      method: 'post',
      data: query
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
  // 查询公告详情
  querySupplement (objectId) {
    return request({
      url: '/announcement/supplement/' + objectId,
      method: 'get'
    })
  },
  // 查询列表(补充公告)
  announcementSuupList (query) {
    return request({
      url: '/announcement/queryList',
      method: 'get',
      params: query
    })
  },
  // 发布(补充公告)
  announcementRelease (query) {
    return request({
      url: '/announcement/release',
      method: 'put',
      data: query
    })
  }
}
