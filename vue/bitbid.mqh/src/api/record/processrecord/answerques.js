import ajax from '@/common/js/ajax'

export const answerques = {

  /**
   * 详情查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/clarify-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 单条查询
   * @param objectId [查询单条信息]
   * */
  getOne (objectId) {
    return ajax({
      url: '/clarify-infos/' + objectId,
      method: 'get'
    })
  },
  /**
   * 单条查询
   * @param objectId [查询单条信息]
   * */
  getByCode (code) {
    return ajax({
      url: '/clarify-infos/query/' + code,
      method: 'get'
    })
  },
  /**
   * 修改/新增
   * @param clarify
   */
  save (data) {
    return ajax({
      url: '/clarify-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 删除
   *  @param objectId [删除单条信息]
   */
  delete (objectId) {
    return ajax({
      url: '/clarify-infos/' + objectId,
      method: 'delete'
    })
  },
  /**
   * 查询审批记录
   * @param {[String]} relatedCode
   */
  getApproveRecords (relatedCode, approveType) {
    return ajax({
      url: '/approve-record/' + relatedCode + '/' + approveType,
      method: 'get'
    })
  }
}
