import ajax from '@/common/js/ajax'

export const candNotice = {
  /**
   * 查询标段集合
   * @param {*} query [查询信息]
   */
  getSectionList (noticeCode) {
    return ajax({
      url: '/section-infos/' + noticeCode,
      method: 'get'
    })
  },
  /**
   * 新增或修改
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/publicity-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 通过标段code获取单条数据
   * @param {*} data [主体数据]
   */
  getOne (query) {
    return ajax({
      url: '/publicity-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 提交候选人公示
   * @param {*} data [主体数据]
   */
  submitPublicity (noticeCode) {
    return ajax({
      url: '/publicity-infos/' + noticeCode,
      method: 'put'
    })
  }
}
