import request from '@/utils/axios'

export const complain = {
  queryList (params) {
    return request({
      url: '/complain',
      method: 'get',
      params
    })
  },
  queryOne (params) {
    return request({
      url: `/complain/queryByObjectId/${params}`,
      method: 'get'
    })
  },
  update (data) {
    return request({
      url: '/complain',
      method: 'post',
      data
    })
  },
  deleteComplain (params) {
    return request({
      url: `/complain/${params}`,
      method: 'delete'
    })
  },
  // 获取电商平台名称
  merchantName () {
    return request({
      url: '/complain/merchantName',
      method: 'get'
    })
  }
}
