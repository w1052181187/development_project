import request from '@/utils/axios/components-axios'

export const bidder = {
  /** 查询 */
  getBidderList (params) {
    return request({
      url: `/bidders`,
      method: 'get',
      params: params
    })
  },
  /** 保存 */
  saveBidder (record) {
    return request({
      url: `/bidders`,
      method: 'post',
      data: record
    })
  }
}
