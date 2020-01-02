import ajax from '@/common/js/ajax'

export const expertmanage = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/expert-info',
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
      url: '/expert-info/' + id,
      method: 'get'
    })
  },
  /**
   * 通过身份证号查询单条
   * @param {*} query [查询信息]
   */
  getByIdCard (query) {
    return ajax({
      url: '/expert-info/idCard',
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
      url: '/expert-info',
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
      url: '/expert-info',
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
      url: '/expert-info/' + id,
      method: 'delete'
    })
  }
}
