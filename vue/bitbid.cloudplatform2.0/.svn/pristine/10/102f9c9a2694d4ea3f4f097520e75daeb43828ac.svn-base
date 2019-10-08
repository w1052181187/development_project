import request from '@/utils/axios/components-axios'

export const journalSummary = {
  /**
   * 查询工作日志/计划总结列表接口
   */
  getList (query) {
    return request({
      url: '/journal-summary',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询单条工作日志/计划总结接口
   */
  getById (objectId) {
    return request({
      url: '/journal-summary/' + objectId,
      method: 'get'
    })
  },
  /**
   * 新增/修改工作日志/计划总结接口
   */
  update (data) {
    return request({
      url: '/journal-summary',
      method: 'put',
      data: data
    })
  },
  /**
   * 删除工作日志/计划总结
   * @param {[long]} id [主键]
   */
  deleteById (id) {
    return request({
      url: '/journal-summary/' + id,
      method: 'delete'
    })
  }
}
