import request from '@/utils/axios'
export const biddingRecord = {
  getBiddingInfo (objectId) {
    return request({
      url: '/biddingRecord/' + objectId,
      method: 'get'
    })
  }
}
