import request from '@/utils/axios/components-axios'

export const cooperationRecord = {
  /**
   * 业务模块列表查询
   * @param {*} query [查询信息]
   */
  getCooperationRecordList (query) {
    return request({
      url: '/cooperation-records',
      method: 'get',
      params: query
    })
  },
  /**
   * 保存 || 修改 合作记录
   */
  saveOrUpdateCooperationRecord (query) {
    return request({
      url: '/cooperation-records',
      method: 'post',
      data: query
    })
  },
  /**
   * 删除合作记录
   */
  deleteAppointmentRecord (objectId) {
    return request({
      url: '/cooperation-records/' + objectId,
      method: 'delete'
    })
  }
}
