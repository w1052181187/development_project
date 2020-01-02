import ajax from '@/common/js/ajax'

export const prorequest = {
  /**
   * 详情查询
   * @param {[long]} id [主键Id]
   */
  getProjectPlan (id) {
    return ajax({
      url: '/project-plans/' + id,
      method: 'get'
    })
  }
}
