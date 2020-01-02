import ajax from '@/common/js/ajax'

export const enterprise = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/enterprise-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getOne (objectId) {
    return ajax({
      url: '/enterprise-infos/' + objectId,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/enterprise-infos',
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
      url: '/enterprise-infos',
      method: 'put',
      data: data
    })
  },
  /**
   * 修改状态
   * @param {*} id, stauts [主体数据]
   */
  updateStatusById (id, stauts) {
    return ajax({
      url: '/enterprise-infos/' + id + '/' + stauts,
      method: 'put'
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/enterprise-infos/' + id,
      method: 'delete'
    })
  },
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getAll (query) {
    return ajax({
      url: '/enterprise-infos/getAll',
      method: 'get',
      params: query
    })
  },
  /**
   * 根据企业id查询对应企业下的所有部门信息
   * @param {[long]} id [主键Id]
   */
  getByParentId (id) {
    return ajax({
      url: '/enterprise-infos/query/' + id,
      method: 'get'
    })
  },
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getByType (query) {
    return ajax({
      url: '/enterprise-infos/getByType',
      method: 'get',
      params: query
    })
  }
}
