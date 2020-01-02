import ajax from '@/common/js/ajax'
// 登录api方法集
export const user = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/user-infos',
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
      url: '/user-infos/' + id,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/user-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 修改接口
   * @param {*} data [主体数据]
   */
  update (data) {
    return ajax({
      url: '/user-infos',
      method: 'put',
      data
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (id) {
    return ajax({
      url: '/user-infos/' + id,
      method: 'delete'
    })
  },
  /**
   * 初始化密碼
   * @param {[long]} id [主键Id]
   */
  initPassword (id) {
    return ajax({
      url: '/user-infos/init-password/' + id,
      method: 'get'
    })
  }
}
