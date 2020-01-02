import ajax from '@/common/js/ajax'

export const contractInfo = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/contract-Infos',
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
      url: '/contract-Infos/' + id,
      method: 'get'
    })
  },
  /**
   * 详情查询
   */
  getBySupplier (tenderProjectCode, sectionCode) {
    return ajax({
      url: '/contract-Infos/supplier/' + tenderProjectCode + '/' + sectionCode,
      method: 'get'
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (objectId) {
    return ajax({
      url: '/contract-Infos/' + objectId,
      method: 'delete'
    })
  },
  /**
   * 修改/新增
   * @param contract
   */
  save (data) {
    return ajax({
      url: '/contract-Infos',
      method: 'put',
      data: data
    })
  }
}
