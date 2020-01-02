import ajax from '@/common/js/ajax'

export const systemnotice = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/systemNotice-info',
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
      url: '/systemNotice-info',
      method: 'post',
      data
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/systemNotice-info/' + id,
      method: 'delete'
    })
  }
}
