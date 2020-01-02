import ajax from '@/common/js/ajax'

export const archivesManagement = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/archives-management',
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
      url: '/archives-management',
      method: 'post',
      data: data
    })
  },
  /**
   * 单条查询
   * @param {*} query [查询信息]
   */
  getOne (code) {
    return ajax({
      url: '/archives-management/code/' + code,
      method: 'get'
    })
  },
  /**
   * 单条查询
   * @param {*} query [查询信息]
   */
  getOneByRelatedCode (tenderProjectCode) {
    return ajax({
      url: '/archives-management/tenderProjectCode/' + tenderProjectCode,
      method: 'get'
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/archives-management/' + id,
      method: 'delete'
    })
  }
}
