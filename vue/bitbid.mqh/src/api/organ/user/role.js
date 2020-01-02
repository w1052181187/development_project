import ajax from '@/common/js/ajax'
// 登录api方法集
export const role = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/role-infos',
      method: 'get',
      params: query
    })
  }
}