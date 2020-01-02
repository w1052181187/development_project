import ajax from '@/common/js/ajax'

export const changeRecord = {
  /**
   * 通过招标项目code查询变更历史
   * @param {[string]} tenderProjectCode
   */
  getByRelatedCode (tenderProjectCode) {
    return ajax({
      url: '/change-record/' + tenderProjectCode,
      method: 'get'
    })
  }
}
