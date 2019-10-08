import request from '@/utils/axios/pubresource-axios'

export const sclarifyFile = {
  // 查询列表
  queryList (params) {
    return request({
      url: '/clarify-files',
      method: 'get',
      params: params
    })
  },
  // 查看详情
  detail (params) {
    return request({
      url: '/clarify-files/' + params,
      method: 'get'
    })
  },
  // 查询关联关系
  detailRelation (params) {
    return request({
      url: '/ref-clarifyfiles/queryList',
      method: 'get',
      params: params
    })
  },
  // 查询关联关系(根据公告进行查询)
  selectRelation (params) {
    return request({
      url: '/ref-clarifyfiles/relSection',
      method: 'get',
      params: params
    })
  }
}
