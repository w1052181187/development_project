import ajax from '@/common/js/ajax'

export const drawroom = {
  /**
   * 列表查询
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/project-groups',
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
      url: '/project-groups/query/' + id,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/draw-lots-results',
      method: 'post',
      data: data
    })
  },
  /**
   * 新增抽签单位信息
   * @param {*} data [主体数据]
   */
  saveDrawRoomInfo (data) {
    return ajax({
      url: '/draw-room/push',
      method: 'post',
      data: data
    })
  },
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getDrawRoomInfo (id) {
    return ajax({
      url: '/draw-room/' + id,
      method: 'get'
    })
  },
  /**
   * 删除接口
   * @param {[long]} id [主键]
   */
  delete (roomId) {
    return ajax({
      url: '/draw-room/push-roomId/' + roomId,
      method: 'get'
    })
  }
}
