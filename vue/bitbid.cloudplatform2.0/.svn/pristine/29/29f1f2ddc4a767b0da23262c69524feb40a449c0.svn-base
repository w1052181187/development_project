import request from '@/utils/axios/components-axios'

export const comparison = {
  /** 日历列表聚合查询 */
  calendars (params) {
    return request({
      url: `/comparison-info/calendars`,
      method: 'get',
      params
    })
  },
  /**
   * 比选公告列表
   * @param params
   */
  annoQueryList (params) {
    return request({
      url: `/comparison-info/announcements`,
      method: 'get',
      params
    })
  },
  /**
   * 比选公告详情
   * @param relatedCode
   */
  annoDetail (relatedCode) {
    return request({
      url: `/comparison-info/` + relatedCode,
      method: 'get'
    })
  },
  /**
   * 招标业主分析
   * @param params
   */
  ownerAnalyze (params) {
    return request({
      url: `/comparison-info/owner-analyze`,
      method: 'get',
      params
    })
  },
  /**
   * 竞争对手分析
   * @param params
   */
  competitorsAnalyze (params) {
    return request({
      url: `/comparison-info/competitors-analyze`,
      method: 'get',
      params
    })
  },
  /**
   * 竞争对手详情
   * @param relatedCode
   */
  competitorDetail (relatedCode) {
    return request({
      url: `/comparison-info/competitor-detail/` + relatedCode,
      method: 'get'
    })
  },
  /**
   * 加入商机
   * @param data
   */
  joinBusinessOpportunities (data) {
    return request({
      url: '/comparison-info/join-business-opportunities',
      method: 'post',
      data
    })
  },
  /**
   * 判断当前公告是否已加入商机
   * @param comparisonCode
   */
  isJoinOpportunity (comparisonCode) {
    return request({
      url: `/comparison-info/is-join-opportunity/` + comparisonCode,
      method: 'get'
    })
  }
}
