import ajax from '@/common/js/ajax'

export const archivesInfo = {
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/archives-info',
      method: 'post',
      data: data
    })
  }
}
