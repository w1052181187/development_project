import request from '@/utils/axios/pubresource-axios'

export const transResAnno = {
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
  save (sectionForm) {
    return request({
      url: '/trans-results-annos',
      method: 'post',
      data: sectionForm
    })
  },
  update (section) {
    return request({
      url: '/trans-results-annos',
      method: 'put',
      data: section
    })
  },
  delete (objectId) {
    return request({
      url: '/trans-results-annos/' + objectId,
      method: 'delete'
    })
  },
  // 保存关联关系
  saveRelation (ruleForm) {
    return request({
      url: '/ref-results-sections',
      method: 'put',
      data: ruleForm
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
  },
  // 修改成交结果公示状态
  updateStatus (ruleForm) {
    return request({
      url: '/trans-results-annos/updateStatus',
      method: 'put',
      data: ruleForm
    })
  }
}
