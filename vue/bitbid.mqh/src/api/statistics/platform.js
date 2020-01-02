import ajax from '@/common/js/ajax'

export const platform = {
  /**
   * 查询
   */
  queryData () {
    return ajax({
      url: '/trade-statistic',
      method: 'get'
    })
  },
  /**
   * 查询代理机构项目信息
   */
  queryAgencyData (orderType) {
    return ajax({
      url: '/trade-statistic/' + orderType,
      method: 'get'
    })
  },
  /**
   * 查询投标人项目信息
   */
  querySupplierData (orderType) {
    return ajax({
      url: '/trade-statistic/supplier/' + orderType,
      method: 'get'
    })
  }
}
