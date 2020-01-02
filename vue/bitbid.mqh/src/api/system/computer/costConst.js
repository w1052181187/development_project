import ajax from '@/common/js/ajax'

export const costConst = {

  /**
   * 查询列表
   */
  getList () {
    return ajax({
      url: '/cost-consulation-rate',
      method: 'get'
    })
  },
  /**
   * 保存优惠比例
   * @param {*} data [主体数据]
   */
  saveDiscountRate (data) {
    return ajax({
      url: '/cost-consulation-rate',
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
      url: '/cost-consulation-rate',
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
      url: '/cost-consulation-rate/delete/' + id,
      method: 'delete'
    })
  }
}
