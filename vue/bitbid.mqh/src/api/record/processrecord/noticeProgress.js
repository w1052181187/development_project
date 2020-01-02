import ajax from '@/common/js/ajax'

export const noticeProgress = {
  /**
   * 通过项目编号和公告编号查询公告进度信息
   */
  getOne (query) {
    return ajax({
      url: '/noticeprogress-infos',
      method: 'get',
      params: query
    })
  }
}
