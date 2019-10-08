import request from '@/utils/axios/pubresource-axios'

export const tdocuments = {
  //  列表显示
  queryList (params) {
    return request({
      url: '/transfer-documents',
      method: 'get',
      params: params
    })
  },
  // 保存数据
  save (ruleForm) {
    return request({
      url: '/transfer-documents',
      method: 'post',
      data: ruleForm
    })
  },
  // 修改数据
  update (ruleForm) {
    return request({
      url: '/transfer-documents',
      method: 'put',
      data: ruleForm
    })
  },
  // 修改出让文件状态(发布)
  updateStatus (ruleForm) {
    return request({
      url: '/transfer-documents/updateStatus',
      method: 'put',
      data: ruleForm
    })
  },
  // 查看详情
  detail (objectId) {
    return request({
      url: '/transfer-documents/' + objectId,
      method: 'get'
    })
  },
  // 删除出让文件
  delete (params) {
    return request({
      url: '/transfer-documents/' + params,
      method: 'delete'
    })
  },
  // 保存关联关系
  saveRelation (ruleForm) {
    return request({
      url: '/ref-trandoc-section',
      method: 'put',
      data: ruleForm
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
  // 查询出让文件添加状态
  selectStatus (params) {
    return request({
      url: '/ref-trandoc-section/statusCount',
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
  },
  // 查看详情(受让人)
  detailByAssignee (params) {
    return request({
      url: '/transfer-documents/queryByAssignee/' + params,
      method: 'get'
    })
  },
  // 修改出让文件状态(发布)
  updateDocuStatus (ruleForm) {
    return request({
      url: '/transfer-documents/updateDocuStatus',
      method: 'put',
      data: ruleForm
    })
  },
  //  列表显示
  queryLandList (params) {
    return request({
      url: '/transfer-documents/queryLandList',
      method: 'get',
      params: params
    })
  }
}
