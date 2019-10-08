import request from '@/utils/axios/components-axios'

export const templateInfo = {
  /**
   * 查询模板信息接口
   */
  getList (query) {
    return request({
      url: '/templateInfo-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询单条模板信息接口
   */
  getOne (code) {
    return request({
      url: '/templateInfo-infos/' + code,
      method: 'get'
    })
  },
  /**
   * 新增模板信息接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return request({
      url: '/templateInfo-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 删除模板信息接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return request({
      url: '/templateInfo-infos/' + id,
      method: 'delete'
    })
  },
  /**
   * 修改发布状态
   */
  updateStatus (data) {
    return request({
      url: '/templateInfo-infos/updateStatus',
      method: 'post',
      data: data
    })
  },
  /**
   * 选用模板接口
   */
  getTemplate (data) {
    return request({
      url: '/templateInfo-infos/chooseTemplate',
      method: 'post',
      data: data
    })
  },
  /**
   * 修改共享状态
   */
  updateShareStatus (data) {
    return request({
      url: '/templateInfo-infos/updateShareStatus',
      method: 'post',
      data: data
    })
  },
  /**
   * 查询系统编号和模板名称集合
   */
  getCodeAndNameList (query) {
    return request({
      url: '/templateInfo-infos/codeAndName',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询最近使用模板信息接口
   */
  getRecentUseList (query) {
    return request({
      url: '/templateInfo-infos/recentUse',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询工作审批列表接口
   */
  getListInWorkApprove (query) {
    return request({
      url: '/templateInfo-infos/listInWorkApprove',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询工作审批业务类型接口
   */
  getAllBusinessType (enterpriseId) {
    return request({
      url: '/templateInfo-infos/listAllBusinessType/' + enterpriseId,
      method: 'get'
    })
  }
}
