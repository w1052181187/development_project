import ajax from '@/common/js/ajax'

export const resnotice = {
  /**
   * 详情查询
   * @param {*} query [查询信息]
   */
  getResultNotice (query) {
    return ajax({
      url: '/resultNotice-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询招标项目下标段的最新一条结果公示数据
   * @param {*} query [查询信息]
   */
  getLastResult (query) {
    return ajax({
      url: '/resultNotice-infos/lastResult',
      method: 'get',
      params: query
    })
  },
  /**
   * 详情查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/resultNotice-infos/list',
      method: 'get',
      params: query
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getById (id) {
    return ajax({
      url: '/resultNotice-infos/' + id,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  saveResultNotice (data) {
    return ajax({
      url: '/resultNotice-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 更新接口
   * @param {*} data [主体数据]
   */
  updateResultNotice (data) {
    return ajax({
      url: '/resultNotice-infos',
      method: 'put',
      data: data
    })
  },
  /**
   * 更新公告进度接口
   * @param {string} noticeCode
   */
  updateProgressStage (noticeCode) {
    return ajax({
      url: '/resultNotice-infos/' + noticeCode,
      method: 'put'
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  deleteById (id) {
    return ajax({
      url: '/resultNotice-infos/' + id,
      method: 'delete'
    })
  }
}
