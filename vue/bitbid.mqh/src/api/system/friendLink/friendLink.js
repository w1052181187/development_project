import ajax from '@/common/js/ajax'

export const friendLink = {

  /**
   * 查询友情链接接口
   */
  getList () {
    return ajax({
      url: '/friend-link',
      method: 'get'
    })
  },
  /**
   * 新增或更新接口
   * @param {*} data [主体数据]
   */
  update (data) {
    return ajax({
      url: '/friend-link',
      method: 'post',
      data: data
    })
  },
  /**
   * 发布
   * @param {*} data [主体数据]
   */
  publish (id) {
    return ajax({
      url: '/friend-link/publish/' + id,
      method: 'post'
    })
  },
  /**
   * 批量更新接口
   * @param {*} data [主体数据]
   */
  updateRank (data) {
    return ajax({
      url: '/friend-link/updateRank',
      method: 'post',
      data: data
    })
  },
  /**
   * 获取单条
   * @param {[long]} id [主键Id]
   */
  getOne (id) {
    return ajax({
      url: '/friend-link/query/' + id,
      method: 'get'
    })
  },
  /**
   * 删除
   * @param {[long]} id [主键Id]
   */
  del (id) {
    return ajax({
      url: '/friend-link/' + id,
      method: 'delete'
    })
  }
}
