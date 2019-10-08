import request from '@/utils/axios/components-axios'

export const archivesProject = {
  /** 查询 */
  getArchivesProjectList (params) {
    return request({
      url: `/archives-projects`,
      method: 'get',
      params: params
    })
  },
  /** 查询 */
  detailArchivesProject (objectId) {
    return request({
      url: `/archives-projects/` + objectId,
      method: 'get'
    })
  },
  /** 修改 */
  updateArchivesProject (data) {
    return request({
      url: '/archives-projects',
      method: 'put',
      data: data
    })
  },
  /** 获取对应的标段档案，并设置阅读记录 */
  getArchivesInfo (objectId, isSetRecord) {
    return request({
      url: '/archives-projects/getArchivesInfo/' + objectId + '/' + isSetRecord,
      method: 'get'
    })
  }
}
