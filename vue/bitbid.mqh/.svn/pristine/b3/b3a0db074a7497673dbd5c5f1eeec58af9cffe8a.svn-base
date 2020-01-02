import ajax from '@/common/js/ajax'

export const bidInfoConfirm = {
  /**
   * 招标信息确认查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/bidInfoConfirm-info',
      method: 'get',
      params: query
    })
  },
  /**
   * 招标信息确认单条查询
   */
  getById (id) {
    return ajax({
      url: '/bidInfoConfirm-info/' + id,
      method: 'get'
    })
  },
  /**
   * 修改接口
   * @param {*} data [主体数据]
   */
  update (data) {
    return ajax({
      url: '/bidInfoConfirm-info',
      method: 'put',
      data
    })
  }
}
