import request from '@/utils/axios/components-axios'

export const qualificationSubject = {
  /**
   * 业务模块列表查询
   * @param {*} query [查询信息]
   */
  getQualificationSubjectList (query) {
    return request({
      url: '/qualification-subjects',
      method: 'get',
      params: query
    })
  },
  /**
   * 业务模块详情查询
   */
  getQualificationSubjectDetail (objectId) {
    return request({
      url: '/qualification-subjects/' + objectId,
      method: 'get'
    })
  },
  /**
   * 业务模块详情查询
   */
  getQualificationSubjectDetailByCode (code) {
    return request({
      url: '/qualification-subjects/detail/' + code,
      method: 'get'
    })
  },
  /**
   * 保存 || 修改 资质管理
   */
  saveOrUpdateQualificationSubject (query) {
    return request({
      url: '/qualification-subjects',
      method: 'post',
      data: query
    })
  },
  /**
   * 删除资质管理
   */
  deleteQualificationSubject (objectId) {
    return request({
      url: '/qualification-subjects/' + objectId,
      method: 'delete'
    })
  },
  /**
   * 查询资质是否可删除
   */
  checkedCanDel (code) {
    return request({
      url: '/qualification-subjects/checkedCanDel/' + code,
      method: 'get'
    })
  }
}
