import request from '@/utils/axios/components-axios'

export const appointmentRecord = {
  /**
   * 业务模块列表查询
   * @param {*} query [查询信息]
   */
  getAppointmentRecordList (query) {
    return request({
      url: '/appointmentRecords',
      method: 'get',
      params: query
    })
  },
  /**
   * 保存 || 修改 预约开评标室
   */
  saveOrUpdateAppointmentRecord (query) {
    return request({
      url: '/appointmentRecords',
      method: 'post',
      data: query
    })
  },
  /**
   * 删除预约开评标室
   */
  deleteAppointmentRecord (objectId) {
    return request({
      url: '/appointmentRecords/' + objectId,
      method: 'delete'
    })
  },
  /**
   * 取消预约
   */
  cancalAppointmentRecord (objectId) {
    return request({
      url: '/appointmentRecords/cancal/' + objectId,
      method: 'put'
    })
  }
}
