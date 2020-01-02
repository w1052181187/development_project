import ajax from '@/common/js/ajax'

export const bidagent = {
  /**
   * 查询项目
   * @param {*} query [查询信息]
   */
  getProjectList (query) {
    return ajax({
      url: '/select-applys',
      method: 'get',
      params: query
    })
  },
  /**
   * 获取计算历史
   * @param {*} query [查询信息]
   */
  getComputerHistory (query) {
    return ajax({
      url: '/calculator-history',
      method: 'get',
      params: query
    })
  },
  /**
   * 保存
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/calculator-history',
      method: 'post',
      data: data
    })
  },
  /**
   * 查询单条
   * @param {*} data [主体数据]
   */
  selectOne (data) {
    return ajax({
      url: '/calculator-history/' + data,
      method: 'get'
    })
  },
  /**
   * 删除单条计算历史
   * @param {[long]} id [主键Id]
   */
  del (id) {
    return ajax({
      url: '/calculator-history/delete/' + id,
      method: 'delete'
    })
  }
}
