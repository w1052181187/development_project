
import request from '@/utils/axios/pubresource-axios'

export const landClarifyFile = {
  queryList (query) {
    return request({
      url: '/land-clarifyFile-doc',
      params: query,
      method: 'get'
    })
  },
  detail (objectId) {
    return request({
      url: '/land-clarifyFile-doc/' + objectId,
      method: 'get'
    })
  },
  selectByAnnoId (ruleForm) {
    return request({
      url: '/land-clarifyFile-doc/selectByAnnoId',
      method: 'get',
      params: ruleForm
    })
  },
  // 删除出让文件
  delete (params) {
    return request({
      url: '/land-clarifyFile-doc/' + params,
      method: 'delete'
    })
  },
  // 查询关联关系
  detailRelation (params) {
    return request({
      url: '/land-clarifyFile-doc/queryList',
      method: 'get',
      params: params
    })
  },
  // 查询出让文件添加状态
  selectStatus (params) {
    return request({
      url: '/land-clarifyFile-doc/statusCount',
      method: 'get',
      params: params
    })
  },
  remove (objectId) {
    return request({
      url: '/land-clarifyFile-doc/' + objectId,
      method: 'delete'
    })
  },
  save (form) {
    return request({
      url: '/land-clarifyFile-doc',
      data: form,
      method: 'post'
    })
  },
  update (form) {
    return request({
      url: '/land-clarifyFile-doc',
      data: form,
      method: 'put'
    })
  },
  // 保存关联关系
  saveRelation (ruleForm) {
    return request({
      url: '/land-clarifyFile-doc/saveRef',
      method: 'post',
      data: ruleForm
    })
  },
  // 修改关联关系
  updateRelation (ruleForm) {
    return request({
      url: '/land-clarifyFile-doc/updateRef',
      method: 'put',
      data: ruleForm
    })
  },
  // 修改出让文件状态(发布)
  updateDocuStatus (ruleForm) {
    return request({
      url: '/land-clarifyFile-doc/updateDocuStatus',
      method: 'put',
      data: ruleForm
    })
  },
  // 修改出让文件状态(发布)
  updateStatus (ruleForm) {
    return request({
      url: '/land-clarifyFile-doc/updateStatus',
      method: 'put',
      data: ruleForm
    })
  }
}
