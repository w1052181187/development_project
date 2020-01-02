import ajax from '@/common/js/ajax'

export const notice = {

  /**
   * 查询招标项目接口
   */
  getList (query) {
    return ajax({
      url: '/notice-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 新增招标项目接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/notice-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getOne (id) {
    return ajax({
      url: '/notice-infos/' + id,
      method: 'get'
    })
  },
  /**
   * 详情查询
   * @param {[string]} code
   */
  getByCode (code) {
    return ajax({
      url: '/notice-infos/code/' + code,
      method: 'get'
    })
  },
  /**
   * 招标项目对应公告信息查询
   * @param {[string]} tenderProjectCode
   */
  selectListByCode (tenderProjectCode) {
    return ajax({
      url: '/notice-infos/tenderProjectCode/' + tenderProjectCode,
      method: 'get'
    })
  },

  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/notice-infos/' + id,
      method: 'delete'
    })
  },
  /**
   * 提交审批
   */
  saveApprovalRecords (data) {
    return ajax({
      url: '/notice-infos/submit',
      method: 'post',
      data: data
    })
  },
  /**
   * 根据招标项目code得到所有公告的建立时间
   */
  selectNoticeDateByCode (tenderProjectCode) {
    return ajax({
      url: '/notice-infos/query-tenderProjectCode/' + tenderProjectCode,
      method: 'get'
    })
  },
  /**
   * 根据招标项目code得到所有公告的开标时间
   */
  selectBidOpenTimeByCode (startDate) {
    return ajax({
      url: '/notice-infos/query-bidOpenTime/' + startDate,
      method: 'get'
    })
  }
}
