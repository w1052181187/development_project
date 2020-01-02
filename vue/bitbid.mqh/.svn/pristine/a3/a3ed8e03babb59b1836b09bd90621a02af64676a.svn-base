import ajax from '@/common/js/ajax'

export const openbid = {
  /**
   * 详情查询
   * @param {*} query [查询信息]
   */
  getOpenRecord (query) {
    return ajax({
      url: '/opening-record',
      method: 'get',
      params: query
    })
  },
  /**
   * 详情查询
   * @param {*} query [查询信息]
   */
  getEvaluationResult (query) {
    return ajax({
      url: '/evaluation-result',
      method: 'get',
      params: query
    })
  },
  /**
   * 公告进度查询
   * @param {*} query [查询信息]
   */
  getNoticeProgress (query) {
    return ajax({
      url: '/noticeprogress-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  saveOpenRecord (data) {
    return ajax({
      url: '/opening-record',
      method: 'post',
      data: data
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  saveEvaluationResult (data) {
    return ajax({
      url: '/evaluation-result',
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
      url: '/evaluation-result/' + noticeCode,
      method: 'put'
    })
  }
}
