import ajax from '@/common/js/ajax'

export const archivesFile = {
  /**
   * 单条查询
   * @param {*} query [查询信息]
   */
  getOne (query) {
    return ajax({
      url: '/archivesFile-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 多条查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/archivesFile-infos/list',
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
      url: '/archivesFile-infos',
      method: 'post',
      data: data
    })
  }
}
