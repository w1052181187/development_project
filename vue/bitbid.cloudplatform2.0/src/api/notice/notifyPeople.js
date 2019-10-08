import request from '@/utils/axios/components-axios'

export const notifyPeople = {
  /** 远程查询知会人 */
  getDefaultNotifyPeopleList (tenderProjectCode) {
    return request({
      url: `/notify-people/getDefaultNotifyPeopleList/` + tenderProjectCode,
      method: 'get'
    })
  },
  /** 远程查询知会人 */
  getNotifyPeopleListByRelatedCode (relatedCode) {
    return request({
      url: `/notify-people/getNotifyPeopleListByRelatedCode/` + relatedCode,
      method: 'get'
    })
  }

}
