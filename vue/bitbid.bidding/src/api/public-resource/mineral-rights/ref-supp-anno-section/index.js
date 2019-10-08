import request from '@/utils/axios/pubresource-axios'

export const suppAnno = {
  // 查询补充公告
  suppAnnoList (query) {
    return request({
      url: '/ref-supp-anno-section/sectionId/' + query,
      method: 'get'
    })
  }
}
