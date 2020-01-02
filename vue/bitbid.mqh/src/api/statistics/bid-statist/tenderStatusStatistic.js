import ajax from '@/common/js/ajax'

export const tenderStatusStatistic = {

  /**
   * 查询接口
   */
  getList (query) {
    return ajax({
      url: '/tenderStatusStatistic-infos',
      method: 'get',
      params: query
    })
  }
}
