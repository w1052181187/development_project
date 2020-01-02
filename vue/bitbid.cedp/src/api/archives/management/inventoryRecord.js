import request from '@/utils/axios/components-axios'

export const inventoryRecord = {
  /** 查询 */
  getRecordList (params) {
    return request({
      url: `/inventory-records`,
      method: 'get',
      params: params
    })
  },
  /** 修改|更新 */
  saveOrUpdateRecord (data) {
    return request({
      url: '/inventory-records/',
      method: 'post',
      data: data
    })
  }
}
