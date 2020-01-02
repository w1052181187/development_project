import ajax from '@/common/js/ajax'

export const evaluateLabel = {
  /**
   * 通过标签类型查询评价标签库表数据
   */
  getList (labelType) {
    return ajax({
      url: '/evaluate-label/' + labelType,
      method: 'get'
    })
  },
  /**
   * 通过关联项目评价表code查询评价信息表数据
   */
  getListByRelatedCode (relatedProjectCode, labelType) {
    return ajax({
      url: '/evaluate-label/query/' + relatedProjectCode + '/' + labelType,
      method: 'get'
    })
  }
}
