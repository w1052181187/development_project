import request from '@/utils/axios/pubresource-axios'

export const stransResAnno = {
  queryList (params) {
    return request({
      url: '/trans-results-annos',
      method: 'get',
      params: params
    })
  },
  queryOne (objectId) {
    return request({
      url: '/trans-results-annos/' + objectId,
      method: 'get'
    })
  },
  update (section) {
    return request({
      url: '/trans-results-annos/updateStatus',
      method: 'put',
      data: section
    })
  },
  // 查询关联关系
  detailRelation (params) {
    return request({
      url: '/ref-results-sections',
      method: 'get',
      params: params
    })
  },
  // 查询关联关系(根据公告进行查询)
  selectRelation (params) {
    return request({
      url: '/ref-results-sections/relSection',
      method: 'get',
      params: params
    })
  }
}
