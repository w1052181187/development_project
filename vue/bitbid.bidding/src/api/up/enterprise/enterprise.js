/**
 * Created by Qinzy on 2018/9/14.
 */
import request from '@/utils/axios/up-axios'

export const enterprise = {
  // 查询企业认证主体
  subjectList (query) {
    return request({
      url: 'enterprise-subjects',
      method: 'get',
      params: query
    })
  },
  // 根据类型查询企业主体
  subjectListByType (query) {
    return request({
      url: 'enterprise-subjects/subjectList',
      method: 'get',
      params: query
    })
  },
  // 查询企业信息
  enterpriseList (query) {
    return request({
      url: 'enterprises/queryByUserId/' + query,
      method: 'get'
    })
  },
  // 保存数据
  save (query) {
    return request({
      url: 'enterprises',
      method: 'post',
      data: query
    })
  },
  // 提交认证后查询企业信息
  enterpriseData (query) {
    return request({
      url: 'enterprises/getInfoByUserId/' + query,
      method: 'get'
    })
  },
  // 修改
  update (query) {
    return request({
      url: 'enterprises',
      method: 'put',
      data: query
    })
  }
}
