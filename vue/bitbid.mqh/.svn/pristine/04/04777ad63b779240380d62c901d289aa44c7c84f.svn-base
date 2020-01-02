/**
 * Created by DELL on 2018/7/2.
 */
import ajax from '@/common/js/ajax'

export const planproject = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/project-plans',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询选聘备案申请中所有可选的招标计划
   */
  getOptionalProjectPlan (requiresEnterpriseId) {
    return ajax({
      url: '/project-plans/query/' + requiresEnterpriseId,
      method: 'get'
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getOne (id) {
    return ajax({
      url: '/project-plans/' + id,
      method: 'get'
    })
  },
  /**
   * 审批记录查询
   * @param {*} query [查询信息]
   */
  getApproveRecordList (aproveRecord) {
    return ajax({
      url: '/approve-record',
      method: 'get',
      params: aproveRecord
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  saveApprovalRecords (data) {
    return ajax({
      url: '/approve-record/submit',
      method: 'post',
      data: data
    })
  },

  /**
   * 修改接口
   * @param data
   */
  update (data) {
    return ajax({
      url: '/project-plans',
      method: 'put',
      data: data
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (objectId) {
    return ajax({
      url: '/project-plans/' + objectId,
      method: 'delete'
    })
  },
  /**
   * 档案管理中通过code查询招标计划基本信息
   */
  getByCode (code) {
    return ajax({
      url: '/project-plans/query-by-code/' + code,
      method: 'get'
    })
  }
}
