import request from '@/utils/axios/projectflow-axios'

export const bidSection = {
  /**
   * 查询标段接口
   */
  getList (query) {
    return request({
      url: '/bidSection-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 新增标段接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return request({
      url: '/bidSection-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 删除标段接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return request({
      url: '/bidSection-infos/' + id,
      method: 'delete'
    })
  },
  /**
   * 查询某节点未关联的标段接口
   */
  getNotRelation (query) {
    return request({
      url: '/bidSection-infos/notRelation',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询可添加专家的标段
   */
  getAddExpert (query) {
    return request({
      url: '/bidSection-infos/addExpert',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询单条标段接口
   */
  getOne (code) {
    return request({
      url: '/bidSection-infos/' + code,
      method: 'get'
    })
  },
  /**
   * 查询未在任何公告中的标段信息接口
   */
  getNotUsedBidSection (tenderSystemCode) {
    return request({
      url: '/bidSection-infos/queryNotUsedBidSection/' + tenderSystemCode,
      method: 'get'
    })
  },
  /**
   * 根据标段code来更新其流转状态
   */
  updateFlowStatusByCode (code, flowStatus, tenderSystemCode) {
    return request({
      url: '/bidSection-infos/' + code + '/' + flowStatus + '/' + tenderSystemCode,
      method: 'get'
    })
  },
  /**
   * 项目全貌：标段进度一览表
   */
  getProjectViewList (enterpriesId) {
    return request({
      url: '/bidSection-infos/queryProjectViewList/' + enterpriesId,
      method: 'get'
    })
  },
  /**
   * 查询待开标数量
   */
  getToOpenBidCount () {
    return request({
      url: '/bidSection-infos/queryToOpenBidCount',
      method: 'get'
    })
  },
  /**
   * 查询待开标数据
   */
  getToOpenBidList (query) {
    return request({
      url: '/bidSection-infos/queryToOpenBidList',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询开标时间
   */
  getOpenBidTimeList (params) {
    return request({
      url: `/bidSection-infos/queryOpenBidTimeList`,
      method: 'get',
      params
    })
  },
  /**
   * 项目全貌：标段进度一览表
   */
  getListInProcess (tenderSystemCode) {
    return request({
      url: '/bidSection-infos/listInProcess/' + tenderSystemCode,
      method: 'get'
    })
  },
  /**
   * 项目全貌：项目资料完整度
   */
  getProjectViewInfoList (query) {
    return request({
      url: '/bidSection-infos/queryProjectViewInfoList',
      method: 'get',
      params: query
    })
  }
}
