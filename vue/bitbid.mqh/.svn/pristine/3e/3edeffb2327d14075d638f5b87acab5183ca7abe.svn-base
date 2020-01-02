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
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getOne (id) {
    return ajax({
      url: '/role-infos/' + id,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/role-infos',
      method: 'post',
      data
    })
  },
  /**
   * 修改接口
   * @param {*} data [主体数据]
   */
  update (data) {
    return ajax({
      url: '/role-infos',
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
      url: '/role-infos/' + id,
      method: 'delete'
    })
  },
  /**
   * 上传角色头像接口
   * @param {*} data [主体数据]
   */
  saveHeadImg (data) {
    return ajax({
      url: '/role-infos/save-headImg',
      method: 'post',
      data
    })
  }
}
