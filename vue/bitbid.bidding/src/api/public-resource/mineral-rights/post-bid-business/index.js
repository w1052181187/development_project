/**
 * Created by admin on 2018-8-6..
 */
import request from '@/utils/axios/pubresource-axios'
export const contract = {
  save (contractForm) {
    return request({
      url: '/contract/',
      method: 'post',
      data: contractForm
    })
  },
  queryList (params) {
    return request({
      url: '/contract',
      method: 'get',
      params: params
    })
  },
  queryOne (objectId) {
    return request({
      url: '/contract/' + objectId,
      method: 'get'
    })
  },
  update (contractForm) {
    return request({
      url: '/contract/',
      method: 'put',
      data: contractForm
    })
  },
  delete (objectId) {
    return request({
      url: '/contract/' + objectId,
      method: 'delete'
    })
  },
  // 出让人查询合同列表
  queryListByTrans (params) {
    return request({
      url: '/contract/queryList',
      method: 'get',
      params: params
    })
  },
  batchSave (contractForm) {
    return request({
      url: '/contract/batchSave',
      method: 'post',
      data: contractForm
    })
  },
  //  受让人查询合同列表
  queryAllData (bidderName) {
    return request({
      url: '/contract/list',
      method: 'get',
      params: bidderName
    })
  }
}
