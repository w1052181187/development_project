import request from '@/utils/axios/pubresource-axios'

export const prProject = {
  queryList (params) {
    return request({
      url: '/section',
      method: 'get',
      params: params
    })
  },
  queryListForBidder (params) {
    return request({
      url: '/section/queryListForBidder',
      method: 'get',
      params: params
    })
  },
  queryListConAnno (params) {
    return request({
      url: '/section/containAnno',
      method: 'get',
      params: params
    })
  },
  querySalesConList (params) {
    return request({
      url: '/section/sectionSalesConfirmation',
      method: 'get',
      params: params
    })
  },
  queryOne (objectId) {
    return request({
      url: '/section/' + objectId,
      method: 'get'
    })
  },
  save (sectionForm) {
    return request({
      url: '/section',
      method: 'post',
      data: sectionForm
    })
  },
  update (section) {
    return request({
      url: '/section/',
      method: 'put',
      data: section
    })
  },
  delete (objectId) {
    return request({
      url: '/section/' + objectId,
      method: 'delete'
    })
  },
  queryNopageList (params) {
    return request({
      url: '/section/queryNopageList',
      method: 'get',
      params: params
    })
  },
  selectBySectionNameAndSectionNumber (params) {
    return request({
      url: '/section/selectBySectionNameAndSectionNumber',
      method: 'get',
      params: params
    })
  },
  setFloorPrice (params) {
    return request({
      url: '/section/roomData/' + params.objectId,
      method: 'get',
      params: params
    })
  },
  // 受让人查询项目信息
  queryByAssignee (params) {
    return request({
      url: '/section/byAssignee',
      method: 'get',
      params: params
    })
  },
  // 查询拍卖项目信息
  queryAuctionList (params) {
    return request({
      url: '/auctionSection',
      method: 'get',
      params: params
    })
  }
}
