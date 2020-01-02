import ajax from '@/common/js/ajax'

export const bidprice = {
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/control-price',
      method: 'post',
      data: data
    })
  },
  /**
   * 修改接口
   * @param {*} data [主体数据]
   */
  update (data) {
    return ajax({
      url: '/control-price',
      method: 'put',
      data
    })
  },
  /**
   * 详情查询
   * @param {*} query [查询信息]
   */
  getOne (query) {
    return ajax({
      url: '/control-price',
      method: 'get',
      params: query
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  savePriceSublist (data) {
    return ajax({
      url: '/controlPriceSublist-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 详情查询
   * @param {*} query [查询信息]
   */
  getPriceSublist (query) {
    return ajax({
      url: '/controlPriceSublist-infos',
      method: 'get',
      params: query
    })
  }
}
