import ajax from '@/common/js/ajax'

export const yearPlanSummary = {
  /**
   * 列表查询- 年度汇总
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/yearPlan-summarys',
      method: 'get',
      params: query
    })
  }
}
