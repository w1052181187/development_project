import ajax from '@/common/js/ajax'

export const drawreview = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/select-applys',
      method: 'get',
      params: query
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getOne (id) {
    return ajax({
      url: '/select-applys/query/' + id,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/approve-record',
      method: 'post',
      data: data
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
   * @param {*} data [主体数据]
   */
  update (data) {
    return ajax({
      url: '/select-applys',
      method: 'put',
      data
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/select-applys/' + id,
      method: 'delete'
    })
  },
  /**
   * 详情查询
   * @param {[string]} code[关联code]
   */
  getApproveRecord (relatedCode, approveType) {
    return ajax({
      url: '/approve-record/' + relatedCode + '/' + approveType,
      method: 'get'
    })
  }
}
