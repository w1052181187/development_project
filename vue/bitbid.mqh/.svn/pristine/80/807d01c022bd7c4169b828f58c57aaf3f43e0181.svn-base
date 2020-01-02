import ajax from '@/common/js/ajax'

export const agendaMessage = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/agendaMessage-info',
      method: 'get',
      params: query
    })
  },
  /**
   * 数量查询
   * @param {*} query [查询信息]
   */
  count (query) {
    return ajax({
      url: '/agendaMessage-info/count',
      method: 'get',
      params: query
    })
  },
  /**
   * 修改接口
   * @param {*} consumedStatus [主体数据]
   */
  updateConsumedStatus (consumedStatus) {
    return ajax({
      url: '/agendaMessage-info/' + consumedStatus,
      method: 'put'
    })
  },
  /**
   * 修改接口
   * @param {*} realatedCode [主体数据]
   */
  updateConsumedStatusByrealatedCode (realatedCode) {
    return ajax({
      url: '/agendaMessage-info/query/' + realatedCode,
      method: 'put'
    })
  }
}
