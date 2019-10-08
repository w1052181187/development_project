import request from '@/utils/axios/pubresource-axios'
/**
 * 审批记录
 * @type {{announcementList: ((query?)), announcementDetail: ((query)), announcementUpdate: ((query?))}}
 */
export const sApproveRecord = {
  // 保存
  save (query) {
    return request({
      url: '/approve-records',
      method: 'post',
      data: query
    })
  }
}
