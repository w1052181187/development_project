import request from '@/utils/axios/components-axios'

export const openTenderReviewSummary = {
  // 侧边导航数据
  leftNav () {
    return request({
      url: '/resource/autoList',
      method: 'get'
    })
  }
}
