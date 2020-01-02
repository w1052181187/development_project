import ajax from '@/common/js/ajax'

export const expertEvaluateInfo = {
  /**
   * 查询
   */
  getListByRelatedCode (tenderProjectCode) {
    return ajax({
      url: '/expert-evaluate-info/' + tenderProjectCode,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/expert-evaluate-info',
      method: 'post',
      data: data
    })
  },
  /**
   * 查询
   */
  getLabelByRelatedCode (idCard, expertType, noticeCode) {
    return ajax({
      url: '/expert-evaluate-info/' + idCard + '/' + expertType + '/' + noticeCode,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  saveBatch (list) {
    return ajax({
      url: '/expert-evaluate-info/batch',
      method: 'post',
      data: list
    })
  },
  /**
   * 列表查询
   */
  getListInResource (expertId) {
    return ajax({
      url: '/expert-evaluate-info/query-in-resource/' + expertId,
      method: 'get'
    })
  },
  /**
   * 查询指定专家的专家评价表信息
   */
  getOne (idCard, expertType, noticeCode) {
    return ajax({
      url: '/expert-evaluate-info/query-one/' + idCard + '/' + expertType + '/' + noticeCode,
      method: 'get'
    })
  }
}
