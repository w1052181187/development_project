import request from '@/utils/axios/components-axios'

export const archivesCatalog = {
  /** 查询 */
  getArchivesCatalogList (params) {
    return request({
      url: `/archives-catalogs`,
      method: 'get',
      params: params
    })
  },
  /** 启用文件目录 */
  enableCatalog (objectId) {
    return request({
      url: '/archives-catalogs/enable/' + objectId,
      method: 'put'
    })
  },
  /** 禁用文件目录 */
  disableCatalog (objectId) {
    return request({
      url: '/archives-catalogs/disable/' + objectId,
      method: 'put'
    })
  },
  /** 删除文件目录 */
  deleteCatalog (objectId) {
    return request({
      url: '/archives-catalogs/' + objectId,
      method: 'delete'
    })
  }
}
