import request from '@/utils/axios/components-axios'

export const archivesCabinet = {
  /** 查询 */
  getArchivesCabinetList (params) {
    return request({
      url: `/archives-cabinets`,
      method: 'get',
      params: params
    })
  },
  /** 删除 */
  deleteCabinet (objectId) {
    return request({
      url: '/archives-cabinets/' + objectId,
      method: 'delete'
    })
  },
  /** 修改|更新 */
  saveOrUpdateCabinet (data) {
    return request({
      url: '/archives-cabinets/',
      method: 'post',
      data: data
    })
  }
}
