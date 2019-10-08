import request from '@/utils/axios/components-axios'

export const archivesClassify = {
  /** 查询 */
  getArchivesClassifyList (params) {
    return request({
      url: `/archives-classifys`,
      method: 'get',
      params: params
    })
  },
  /** 新增|修改文件分类 */
  saveOrUpdateArchivesClassify (data) {
    return request({
      url: '/archives-classifys',
      method: 'post',
      data: data
    })
  },
  /** 删除文件分类 */
  deleteClassify (objectId) {
    return request({
      url: '/archives-classifys/' + objectId,
      method: 'delete'
    })
  },
  /** 初始化企业下的文件目录 */
  initEnterpriseClassify () {
    return request({
      url: `/archives-classifys/initEnterpriseClassify`,
      method: 'get'
    })
  }
}
