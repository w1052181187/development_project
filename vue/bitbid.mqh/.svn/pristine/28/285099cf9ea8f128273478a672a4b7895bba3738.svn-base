import ajax from '@/common/js/ajax'

export const bidderInfo = {
  /**
   * 查询投标人接口
   */
  getList (query) {
    return ajax({
      url: '/bidder-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询单条招标项目接口
   */
  getOne (id) {
    return ajax({
      url: '/bidder-infos/query/' + id,
      method: 'get'
    })
  },
  /**
   * 新增投标人接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/bidder-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 通过公告编号和标段编号查询投标人信息 /{noticeCode}/{sectionCode}
   */
  getByRelatedCode (noticeCode, sectionCode) {
    return ajax({
      url: '/bidder-infos/' + noticeCode + '/' + sectionCode,
      method: 'get'
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/bidder-infos/' + id,
      method: 'delete'
    })
  },
  /**
   * 通过投标人代码来查看对应的供应商是否处于黑名单中
   * 若存在于黑名单中则不允许添加
   */
  getByByCreditCode (bidderCreditCode) {
    return ajax({
      url: '/bidder-infos/supply/' + bidderCreditCode,
      method: 'get'
    })
  }
}
