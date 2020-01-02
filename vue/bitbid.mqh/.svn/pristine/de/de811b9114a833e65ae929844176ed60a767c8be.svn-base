import ajax from '@/common/js/ajax'

export const projectEvaluate = {
  /**
   * 列表查询
   */
  getList (query) {
    return ajax({
      url: '/project-evaluate',
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
      url: '/project-evaluate',
      method: 'post',
      data: data
    })
  },
  /**
   * 单条查询
   */
  getOne (relatedProjectCode, labelType) {
    return ajax({
      url: '/project-evaluate/' + relatedProjectCode + '/' + labelType,
      method: 'get'
    })
  },
  /**
    * 列表查询
    */
  getListInResource (query) {
    return ajax({
      url: '/project-evaluate/query-in-resource',
      method: 'get',
      params: query
    })
  }
}
