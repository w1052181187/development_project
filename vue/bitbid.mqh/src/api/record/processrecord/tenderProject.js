import ajax from '@/common/js/ajax'

export const tenderproject = {

  /**
   * 根据招标代理机构的id来查询该企业负责的全部的项目信息
   * @param {[long]} enterpriseId
   */
  getSelectApplyByEnterpriseId (enterpriseId) {
    return ajax({
      url: '/tenderProject-infos/' + enterpriseId,
      method: 'get'
    })
  },
  /**
   * 查询招标项目接口
   */
  getList (query) {
    return ajax({
      url: '/tenderProject-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询招标项目接口
   */
  getListInArchives (query) {
    return ajax({
      url: '/tenderProject-infos/query-in-archives',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询单条招标项目接口
   */
  getOne (id) {
    return ajax({
      url: '/tenderProject-infos/query/' + id,
      method: 'get'
    })
  },
  /**
   * 查询单条招标项目接口
   */
  getOneByCode (code) {
    return ajax({
      url: '/tenderProject-infos/query-tenderProject/' + code,
      method: 'get'
    })
  },
  /**
   * 查询单条招标项目接口
   */
  getNameByCode (code) {
    return ajax({
      url: '/tenderProject-infos/Name/' + code,
      method: 'get'
    })
  },
  /**
   * 新增招标项目接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/tenderProject-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 新增项目经理接口
   * @param {*} data [主体数据]
   */
  saveProjectManager (data) {
    return ajax({
      url: '/project-manager',
      method: 'post',
      data: data
    })
  },
  /**
   * 查询接口
   * @param {*} data [主体数据]
   */
  getAllProjectManager (query) {
    return ajax({
      url: '/project-manager',
      method: 'get',
      params: query
    })
  },
  /**
   * 通过公告code查询对应招标项目信息
   * @param {*} data [主体数据]
   */
  queryByNotice (query) {
    return ajax({
      url: '/tenderProject-infos/queryByNotice',
      method: 'get',
      params: query
    })
  },
  /**
   * 通过招标项目code查询申请表中的codeUser信息
   */
  getCodeUserByCode (tenderProjectCode) {
    return ajax({
      url: '/tenderProject-infos/select-codeUser/' + tenderProjectCode,
      method: 'get'
    })
  },
  /**
   * 通过招标项目名称查询招标项目的code
   */
  getCodeByName (tenderProjectName) {
    return ajax({
      url: '/tenderProject-infos/select-code/' + tenderProjectName,
      method: 'get'
    })
  },
  /**
   * 查询招标项目接口
   */
  getListOnMainPage (query) {
    return ajax({
      url: '/tenderProject-infos/query-on-mainPage',
      method: 'get',
      params: query
    })
  },
  /**
   * 考评模块：查询招标项目接口
   */
  getListInEvaluate (query) {
    return ajax({
      url: '/tenderProject-infos/query-in-evaluate',
      method: 'get',
      params: query
    })
  },
  /**
   * 考评模块：查询单条招标项目接口
   */
  getOneInEvaluate (code) {
    return ajax({
      url: '/tenderProject-infos/query-in-evaluate/' + code,
      method: 'get'
    })
  },
  /**
   * 考评模块：查询待审核招标项目接口
   */
  getCheckInfoInEvaluate (query) {
    return ajax({
      url: '/tenderProject-infos/query-in-evaluate/check',
      method: 'get',
      params: query
    })
  },
  /**
   * 资源管理模块：查询单条招标项目接口
   */
  getOneInResource (code, labelType, expertId) {
    return ajax({
      url: '/tenderProject-infos/query-in-resource/' + code + '/' + labelType + '/' + expertId,
      method: 'get'
    })
  },
  /**
   * 根据招标项目code查出对应招标计划下的建议计划信息接口
   */
  getSuggestedPlan (code) {
    return ajax({
      url: '/tenderProject-infos/querySuggestedPlan/' + code,
      method: 'get'
    })
  },
  /**
   * 查询招标项目接口
   */
  getTenderProjectList (query) {
    return ajax({
      url: '/tenderProject-infos/query',
      method: 'get',
      params: query
    })
  }
}
