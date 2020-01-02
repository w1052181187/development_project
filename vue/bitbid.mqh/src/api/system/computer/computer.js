import ajax from '@/common/js/ajax'

export const computer = {

  /**
   * 查询列表
   */
  getList () {
    return ajax({
      url: '/bidding-agency-rate',
      method: 'get'
    })
  },
  /**
   * 获取优惠比例
   */
  getDiscountRate (data) {
    return ajax({
      url: '/constant-maintenance',
      method: 'get',
      params: data
    })
  },
  /**
   * 保存优惠比例
   * @param {*} data [主体数据]
   */
  saveDiscountRate (data) {
    return ajax({
      url: '/constant-maintenance',
      method: 'post',
      data: data
    })
  },
  /**
   * 保存
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/bidding-agency-rate',
      method: 'post',
      data: data
    })
  },
  /**
   * 删除单条
   * @param {[long]} id [主键Id]
   */
  del (id) {
    return ajax({
      url: '/bidding-agency-rate/delete/' + id,
      method: 'delete'
    })
  }
}
