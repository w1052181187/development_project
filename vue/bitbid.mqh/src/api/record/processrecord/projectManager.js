import ajax from '@/common/js/ajax'

export const projectManager = {
  /**
   * 详情查询
   * @param relatedCode
   */
  getByRelatedCode (relatedCode) {
    return ajax({
      url: '/project-manager/query/' + relatedCode,
      method: 'get'
    })
  }
}
