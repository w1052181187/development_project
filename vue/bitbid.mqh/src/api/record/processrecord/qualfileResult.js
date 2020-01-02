import ajax from '@/common/js/ajax'

export const qualfileResult = {
  /**
   * 详情查询
   * @param {*} query [查询信息]
   */
  getQualifiPreResult (query) {
    return ajax({
      url: '/QualifiPre-result',
      method: 'get',
      params: query
    })
  },
  /**
   * 详情查询
   * @param {*} query [查询信息]
   */
  getBidderInfo (query) {
    return ajax({
      url: '/QualifiPre-result/bidderInfo',
      method: 'get',
      params: query
    })
  },
  /**
   * 新增或修改
   * @param {*} data [主体数据]
   */
  saveQualifiPreResult (data) {
    return ajax({
      url: '/QualifiPre-result',
      method: 'post',
      data: data
    })
  },
  /**
   * 更新公告进度接口
   * @param {string} noticeCode
   */
  updateProgressStage (noticeCode) {
    return ajax({
      url: '/QualifiPre-result/' + noticeCode,
      method: 'put'
    })
  }
}
