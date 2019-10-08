import request from '@/utils/axios/pubresource-axios'

export const stdocuments = {
  //  列表显示
  queryList (params) {
    return request({
      url: '/transfer-documents',
      method: 'get',
      params: params
    })
  },
  // 查看详情
  detail (params) {
    return request({
      url: '/transfer-documents/' + params,
      method: 'get'
    })
  },
  // 查询关联关系
  detailRelation (params) {
    return request({
      url: '/ref-trandoc-section/queryList',
      method: 'get',
      params: params
    })
  },
  // 查询关联关系(根据公告进行查询)
  selectRelation (params) {
    return request({
      url: '/ref-trandoc-section/relSection',
      method: 'get',
      params: params
    })
  },
  // 澄清文件查询关联关系(根据公告进行查询)
  selectclarifyRelation (params) {
    return request({
      url: '/ref-trandoc-section/clarify',
      method: 'get',
      params: params
    })
  }
}
