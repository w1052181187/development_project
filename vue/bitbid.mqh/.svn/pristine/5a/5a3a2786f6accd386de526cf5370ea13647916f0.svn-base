import ajax from '@/common/js/ajax'

export const qualfile = {
  /**
   * 详情查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/QualifiPreFile-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getOne (id) {
    return ajax({
      url: '/QualifiPreFile-infos/' + id,
      method: 'get'
    })
  },
  /**
   * 通过公告编号和标段编号查询
   * @param {[string]} noticeCode, sectionCode
   */
  getByCode (noticeCode, sectionCode) {
    return ajax({
      url: '/QualifiPreFile-infos/' + noticeCode + '/' + sectionCode,
      method: 'get'
    })
  },
  /**
   * 修改接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/QualifiPreFile-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 通过公告编号提交招标文件
   */
  submit (noticeCode, qualifiPreStatus) {
    return ajax({
      url: '/QualifiPreFile-infos/submit/' + noticeCode + '/' + qualifiPreStatus,
      method: 'put'
    })
  },
  /**
   * 通过公告编号保存招标文件审批记录
   */
  approve (data) {
    return ajax({
      url: '/QualifiPreFile-infos/approve',
      method: 'put',
      data
    })
  },
  /**
   * 标段信息查询
   * @param {*} query [查询信息]
   */
  getsections (noticeCode) {
    return ajax({
      url: '/section-infos/' + noticeCode,
      method: 'get'
    })
  },
  /**
   * 通过公告编号查询未关联资格预审文件的标段信息
   * @param {[string]} noticeCode
   */
  getSectionList (noticeCode) {
    return ajax({
      url: '/section-infos/qualifiPreFile/' + noticeCode,
      method: 'get'
    })
  },
  /**
   * 删除接口
   * @param {[String]} code [主键]
   */
  delete (code) {
    return ajax({
      url: '/QualifiPreFile-infos/' + code,
      method: 'delete'
    })
  },
  /**
   * 查询审批记录
   * @param {[String]} relatedCode
   */
  getApproveRecords (relatedCode, approveType) {
    return ajax({
      url: '/approve-record/' + relatedCode + '/' + approveType,
      method: 'get'
    })
  }
}
