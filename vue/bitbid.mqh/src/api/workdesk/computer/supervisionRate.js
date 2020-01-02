import ajax from '@/common/js/ajax'

export const supervisionRate = {

  /**
   * 查询列表
   */
  getList (query) {
    return ajax({
      url: '/construction-supervision-rate',
      method: 'get',
      params: query
    })
  },
  /**
   * 保存
   * @param {*} data [主体数据]
   */
  save (type, data) {
    return ajax({
      url: '/construction-supervision-rate/save/' + type,
      method: 'post',
      data: data
    })
  },
  /**
   * 删除单条
   * @param {[long]} id [主键Id]
   */
  delete (id) {
    return ajax({
      url: '/construction-supervision-rate/delete/' + id,
      method: 'delete'
    })
  },
  /**
   * 获取系数信息
   */
  getRatioInfo (query) {
    return ajax({
      url: '/construction-supervision-ratio-info',
      method: 'get',
      params: query
    })
  },
  /**
   * 获取计费额的上下限
   */
  getUpperLowLimit (type, chargeAmount, sourceType) {
    return ajax({
      url: '/construction-supervision-rate/queryUpperLowLimit/' + type + '/' + chargeAmount + '/' + sourceType,
      method: 'get'
    })
  }
}
