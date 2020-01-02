import ajax from '@/common/js/ajax'

export const coalSafetyFeeSummarySublist = {

  /**
   * 查询接口
   */
  getList (query) {
    return ajax({
      url: '/coalSafetyFeeSummarySublist-infos',
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
      url: '/coalSafetyFeeSummarySublist-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 获取标题接口
   */
  getTitle (year, month) {
    return ajax({
      url: '/coalSafetyFeeSummarySublist-infos/title/' + year + '/' + month,
      method: 'get'
    })
  },
  /**
   * 获取指定年份的原煤产量累计、提取总额累计、使用总额本月、使用总额累计数据
   */
  getTotalInfo (enterpriseId, year, month) {
    return ajax({
      url: '/coalSafetyFeeSummarySublist-infos/' + enterpriseId + '/' + year + '/' + month,
      method: 'get'
    })
  }
}
