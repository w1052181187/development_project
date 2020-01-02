import ajax from '@/common/js/ajax'

export const homeIndex = {
  /**
   * 获取在线用户数量，友情链接，未读消息等基础数据
   */
  getBaseData: () => {
    return ajax({
      url: '/homeIndex/getHomeData',
      method: 'get'
    })
  },
  /**
   * 获取日历上待开标和待抽签数据信息
   */
  getCalendarData: (currentTime) => {
    return ajax({
      url: '/homeIndex/getCalendarData/' + currentTime,
      method: 'get'
    })
  },
  // 获取行业资讯信息
  getIndustryData: () => {
    return ajax({
      url: '/homeIndex/getIndustryData',
      method: 'get'
    })
  },
  getOnlineUserInfo: () => {
    return ajax({
      url: '/homeIndex/getOnlineUserInfo',
      method: 'get'
    })
  }
}
