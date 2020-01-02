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
  /** 查询被借阅档案 */
  getBorringlist (params) {
    return request({
      url: `/archives-projects/getBorringlist`,
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
  /** 清点档案 */
  inventoryByObjectId (objectId, status) {
    return request({
      url: '/archives-projects/inventoryByObjectId/' + objectId + '/' + status,
      method: 'put'
    })
  },
  /** 获取对应的标段档案，并设置阅读记录 */
  getArchivesInfo (objectId, isSetRecord) {
    return request({
      url: '/archives-projects/getArchivesInfo/' + objectId + '/' + isSetRecord,
      method: 'get'
    })
  },
  /** 查询项目档案 */
  getTenderProject (params) {
    return request({
      url: '/archives-projects/getTenderProject',
      method: 'get',
      params: params
    })
  },
  /** 归还档案 */
  returnByObjectId (objectId) {
    return request({
      url: '/archives-projects/returnByObjectId/' + objectId,
      method: 'put'
    })
  },
  /** 借出档案 */
  lendByObjectId (objectId) {
    return request({
      url: '/archives-projects/lendByObjectId/' + objectId,
      method: 'put'
    })
  },
  /** 查询档案是否被借出 */
  getIsLendedByCode (code) {
    return request({
      url: '/archives-projects/getIsLendedByCode/' + code,
      method: 'get'
    })
  }

}
