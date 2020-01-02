import request from '@/utils/axios/components-axios'

export const borrowRecord = {
  /** 查询 */
  getRecordList (params) {
    return request({
      url: `/borrowing-records`,
      method: 'get',
      params: params
    })
  }
}
