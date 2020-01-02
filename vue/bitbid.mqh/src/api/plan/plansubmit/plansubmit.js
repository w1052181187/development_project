import ajax from '@/common/js/ajax'

export const plansubmit = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/annualProject-plans',
      method: 'get',
      params: query
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  saveProject (data) {
    return ajax({
      url: '/project',
      method: 'post',
      data: data
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  updateProject (data) {
    return ajax({
      url: '/project',
      method: 'put',
      data: data
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  deleteProject (id) {
    return ajax({
      url: '/project/' + id,
      method: 'delete'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  saveProjectPlan (data) {
    return ajax({
      url: '/project-plans',
      method: 'post',
      data: data
    })
  },
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getProjectPlan (query) {
    return ajax({
      url: '/project-plans',
      method: 'get',
      params: query
    })
  },
  /**
   * 删除接口
   * @param {*} data [主体数据]
   */
  delete (data) {
    return ajax({
      url: '/project-plans',
      method: 'delete',
      data: data
    })
  },
  /**
   * 修改接口
   * @param {*} data [主体数据]
   */
  updateStatus (data) {
    return ajax({
      url: '/project-plans/submit',
      method: 'put',
      data
    })
  },
  /**
   * 查询未制定成招标计划的采购建议计划数量
   * @param {*} query [查询信息]
   */
  selectCount (query) {
    return ajax({
      url: '/project/count',
      method: 'get',
      params: query
    })
  },
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getApproveRecord (query) {
    return ajax({
      url: '/approve-record',
      method: 'get',
      params: query
    })
  }
}
