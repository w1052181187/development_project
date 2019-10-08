import request from '@/utils/axios/pubresource-axios'

export const clarifyFile = {
  // 查询列表
  queryList (params) {
    return request({
      url: '/clarify-files',
      method: 'get',
      params: params
    })
  },
  // 保存数据
  save (ruleForm) {
    return request({
      url: '/clarify-files',
      method: 'post',
      data: ruleForm
    })
  },
  // 修改数据
  update (ruleForm) {
    return request({
      url: '/clarify-files',
      method: 'put',
      data: ruleForm
    })
  },
  // 删除数据
  delete (params) {
    return request({
      url: '/clarify-files/' + params,
      method: 'delete'
    })
  },
  // 查看详情
  detail (params) {
    return request({
      url: '/clarify-files/' + params,
      method: 'get'
    })
  },
  // 保存关联关系
  saveRelation (ruleForm) {
    return request({
      url: '/ref-clarifyfiles',
      method: 'post',
      data: ruleForm
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
  // 修改澄清文件状态
  updateStatus (ruleForm) {
    return request({
      url: '/clarify-files/updateStatus',
      method: 'put',
      data: ruleForm
    })
  },
  // 查询关联关系(根据公告进行查询)
  selectRelation (params) {
    return request({
      url: '/ref-clarifyfiles/relSection',
      method: 'get',
      params: params
    })
  },
  // 查询出让文件添加状态
  statusCount (params) {
    return request({
      url: '/ref-trandoc-section/statusCount',
      method: 'get',
      params: params
    })
  },
  // 修改澄清文件
  updateRelation (ruleForm) {
    return request({
      url: '/clarify-files',
      method: 'put',
      data: ruleForm
    })
  },
  // 查看详情(受让人)
  detailByAssignee (params) {
    return request({
      url: '/clarify-files/queryByAssignee/' + params,
      method: 'get'
    })
  },
  // 项目中判断是否可以下载文件（受让人）
  projectDownlod (params) {
    return request({
      url: '/ref-trandoc-section/queryAllCount',
      method: 'get',
      params: params
    })
  },
  // 查询列表(土地)
  queryLandList (params) {
    return request({
      url: '/clarify-files/queryLandlist',
      method: 'get',
      params: params
    })
  }
}
