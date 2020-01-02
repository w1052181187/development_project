import ajax from '@/common/js/ajax'

export const constantMaintenance = {

  /**
   * 查询招标项目接口
   */
  getList (query) {
    return ajax({
      url: '/constant-maintenance',
      method: 'get',
      params: query
    })
  },
  /**
   * 新增招标项目接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/constant-maintenance',
      method: 'post',
      data: data
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getOne (id) {
    return ajax({
      url: '/constant-maintenance/' + id,
      method: 'get'
    })
  }
}
