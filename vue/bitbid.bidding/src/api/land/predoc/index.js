
import request from '@/utils/axios/pubresource-axios'

export const landPreDoc = {
  queryList (query) {
    return request({
      url: '/land-pre-doc',
      params: query,
      method: 'get'
    })
  },
  detail (objectId) {
    return request({
      url: '/land-pre-doc/' + objectId,
      method: 'get'
    })
  },
  // 删除出让文件
  delete (params) {
    return request({
      url: '/land-pre-doc/' + params,
      method: 'delete'
    })
  },
  // 查询关联关系
  detailRelation (params) {
    return request({
      url: '/ref-landPre-section/clarify',
      method: 'get',
      params: params
    })
  },
  // 查询出让文件添加状态
  selectStatus (params) {
    return request({
      url: '/land-pre-doc/statusCount',
      method: 'get',
      params: params
    })
  },
  remove (objectId) {
    return request({
      url: '/land-pre-doc/' + objectId,
      method: 'delete'
    })
  },
  selectCountByTraId (objectId) {
    return request({
      url: '/land-pre-doc/selectCountByTraId/' + objectId,
      method: 'get'
    })
  },
  save (form) {
    return request({
      url: '/land-pre-doc',
      data: form,
      method: 'post'
    })
  },
  update (form) {
    return request({
      url: '/land-pre-doc',
      data: form,
      method: 'put'
    })
  },
  // 保存关联关系
  saveRelation (ruleForm) {
    return request({
      url: '/ref-landPre-section',
      method: 'put',
      data: ruleForm
    })
  },
  selectByAnnoId (ruleForm) {
    return request({
      url: '/ref-landPre-section/clarify',
      method: 'get',
      params: ruleForm
    })
  },
  // 修改出让文件状态(发布)
  updateDocuStatus (ruleForm) {
    return request({
      url: '/land-pre-doc/updateDocuStatus',
      method: 'put',
      data: ruleForm
    })
  },
  // 修改出让文件状态(发布)
  updateStatus (ruleForm) {
    return request({
      url: '/land-pre-doc/updateStatus',
      method: 'put',
      data: ruleForm
    })
  },
  // 查看详情(受让人)
  detailByAssignee (params) {
    return request({
      url: '/land-pre-doc/queryPreDocByAssignee/' + params,
      method: 'get'
    })
  },
}
