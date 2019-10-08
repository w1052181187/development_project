import request from '@/utils/axios/components-axios'

export const settlementItem = {
  /** 查询 */
  getSettlementItemList (params) {
    return request({
      url: `/settlement-items`,
      method: 'get',
      params: params
    })
  },
  /** 查询单个 */
  getSettlementItem (objectId) {
    return request({
      url: `/settlement-items/` + objectId,
      method: 'get'
    })
  },
  /** 新增 */
  saveSettlementItem (record) {
    return request({
      url: `/settlement-items`,
      method: 'post',
      data: record
    })
  },
  /** 修改 */
  updateSettlementItem (record) {
    return request({
      url: `/settlement-items`,
      method: 'put',
      data: record
    })
  },
  /** 批量退保 */
  returnMoney (params) {
    return request({
      url: `/settlement-items/returnMoney`,
      method: 'put',
      data: params
    })
  },
  /** 删除 */
  deleteSettlementItem (objectId) {
    return request({
      url: `/settlement-items/` + objectId,
      method: 'delete'
    })
  }
}
