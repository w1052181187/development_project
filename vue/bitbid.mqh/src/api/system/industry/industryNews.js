import ajax from '@/common/js/ajax'

export const industry = {
  /**
   * 查询项目
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/industry-news',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询单条
   * @param {*} query [查询信息]
   */
  selectOne (id) {
    return ajax({
      url: '/industry-news/' + id,
      method: 'get'
    })
  },
  /**
   * 保存
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/industry-news',
      method: 'post',
      data: data
    })
  }
}
