import roomInfo from '@/utils/axios/room-axios'
// 竞价
export const biddingPrice = {
  // 获取竞价室内容数据
  getBidRoomData (roomId, userId) {
    return roomInfo.request({
      url: '/room/info/' + roomId + '/' + userId,
      method: 'get'
    })
  },
  // 竞价室浏览器同步时间
  asnBrowserTime (roomId) {
    return roomInfo.request({
      url: '/room/browserTime/' + roomId,
      method: 'get'
    })
  },
  // 报价
  quotedPrice (data) {
    return roomInfo.request({
      url: '/room/bid',
      data: data,
      method: 'post'
    })
  },
  // 获取竞价室倒计时
  getCountDownBidRoom (roomId) {
    return roomInfo.request({
      url: '/room/time/' + roomId,
      method: 'get'
    })
  },
  // 开启竞价室
  openBidRoom (roomId) {
    return roomInfo.request({
      url: '/room/open/' + roomId,
      method: 'post'
    })
  },
  // 获取报价列表
  getOfferList (query) {
    return roomInfo.request({
      url: '/room/getAuctionRecordList',
      params: query,
      method: 'get'
    })
  }
}
