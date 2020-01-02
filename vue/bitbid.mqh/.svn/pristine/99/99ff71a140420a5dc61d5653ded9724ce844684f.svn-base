import ajax from '@/common/js/ajax'

export const intermanage = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/enterprise-infos/getList',
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
   * 修改状态
   * @param data
   */
  update (data) {
    return ajax({
      url: '/enterprise-infos',
      method: 'put',
      data
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (objectId) {
    return ajax({
      url: '/enterprise-infos/enterprise/' + objectId,
      method: 'delete'
    })
  },
  /**
   * 企业信息保存接口
   * @param {*} data [主体数据]
   */
  saveEnterprise (data) {
    return ajax({
      url: '/enterprise-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 企业信息更新接口
   * @param {*} data [主体数据]
   */
  updateEnterprise (data) {
    return ajax({
      url: '/enterprise-infos',
      method: 'put',
      data: data
    })
  },
  /**
   * 企业信息详情查询
   * @param {[long]} id [主键Id]
   */
  getEnterprise (id) {
    return ajax({
      url: '/enterprise-infos/' + id,
      method: 'get'
    })
  }
}
