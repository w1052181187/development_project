import ajax from '@/common/js/ajax'

export const drawproject = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/project-groups',
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
      url: '/project-groups/query/' + id,
      method: 'get'
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getOneByCode (code) {
    return ajax({
      url: '/project-groups/' + code,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/project-groups',
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
      url: '/project-groups',
      method: 'put',
      data
    })
  },
  /**
   * 删除接口
   * @param {[string]} code [主键]
   */
  delete (code) {
    return ajax({
      url: '/project-groups/' + code,
      method: 'delete'
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getByEntrustEnterpriseId (entrustEnterpriseId) {
    return ajax({
      url: '/project-groups/query-by-entrustEnterpriseId/' + entrustEnterpriseId,
      method: 'get'
    })
  }
}
