import ajax from '@/common/js/ajax'

export const department = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/department-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/department-infos',
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
      url: '/department-infos',
      method: 'put',
      data: data
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/department-infos/' + id,
      method: 'delete'
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [企业Id]
   */
  getByEnterpriseId (enterpriseId) {
    return ajax({
      url: '/department-infos/' + enterpriseId,
      method: 'get'
    })
  },
  /**
   * 详情查询
   * @param {[long]} id
   */
  getOne (objectId) {
    return ajax({
      url: '/department-infos/query/' + objectId,
      method: 'get'
    })
  },
  /**
   * 修改状态
   * @param {*} id, stauts [主体数据]
   */
  updateStatusById (id, stauts) {
    return ajax({
      url: '/department-infos/' + id + '/' + stauts,
      method: 'put'
    })
  }
}
