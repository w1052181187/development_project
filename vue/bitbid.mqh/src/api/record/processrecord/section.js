import ajax from '@/common/js/ajax'

export const section = {
  /**
   * 查询招标项目接口
   */
  getList (query) {
    return ajax({
      url: '/section-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 标段信息查询
   * @param {[string]} noticeCode
   */
  getSections (noticeCode) {
    return ajax({
      url: '/section-infos/' + noticeCode,
      method: 'get'
    })
  },
  /**
   * 未锁定标段信息查询
   * @param {[string]} noticeCode
   */
  getNotLockedSections (noticeCode) {
    return ajax({
      url: '/section-infos/notLocked/' + noticeCode,
      method: 'get'
    })
  },
  /**
   * 新增标段接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/section-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 通过公告编号和投标人编号查询标段信息 /{noticeCode}/{bidderCode}
   */
  getByRelatedCode (noticeCode, bidderCode) {
    return ajax({
      url: '/section-infos/' + noticeCode + '/' + bidderCode,
      method: 'get'
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/section-infos/' + id,
      method: 'delete'
    })
  },
  /**
   * 标段信息查询
   * @param {[string]} tenderProjectCode
   */
  getAllByTenderCode (tenderProjectCode) {
    return ajax({
      url: '/section-infos/select-all/' + tenderProjectCode,
      method: 'get'
    })
  },
  /**
   * 锁定标段信息查询
   * @param {[string]} noticeCode
   */
  getLockedSections (noticeCode) {
    return ajax({
      url: '/section-infos/locked/' + noticeCode,
      method: 'get'
    })
  },
  /**
   * 未在任何公告中标段信息查询
   * @param {[string]} tenderProjectCode
   */
  getUnusedSections (tenderProjectCode) {
    return ajax({
      url: '/section-infos/select-unused/' + tenderProjectCode,
      method: 'get'
    })
  },
  /**
   * 更新标段接口
   * @param {*} data [主体数据]
   */
  updateBatch (data) {
    return ajax({
      url: '/section-infos/list',
      method: 'post',
      data: data
    })
  },
  /**
   * 档案管理中对标段的操作（CRUD）
   */
  updateSectionsInArchives (data) {
    return ajax({
      url: '/section-infos/update-section',
      method: 'post',
      data: data
    })
  },
  /**
   * 查询单条
   */
  getOne (code) {
    return ajax({
      url: '/section-infos/queryByCode/' + code,
      method: 'get'
    })
  }
}
