/** 项目接口 */
import request from '@/utils/axios/up-axios'

export const invoiceAdmin = {
  // 代理机构
  queryNotPageList (query) {
    return request({
      url: '/agencyEnterprise/queryNotPageList',
      params: query,
      method: 'get'
    })
  },
  // 列表已申请
  invoiceAdminList (query) {
    return request({
      url: '/apply-invoices-record',
      params: query,
      method: 'get'
    })
  },
  // 列表未申请
  invoiceAdminListNO (query) {
    return request({
      url: '/apply-invoices-record/queryZborderList',
      params: query,
      method: 'get'
    })
  },
  // 已申请详情
  invoiceAdminDetail (query) {
    return request({
      url: '/apply-invoices-record/' + query,
      params: query,
      method: 'get'
    })
  },
  // 未申请详情
  noapplyinvoiceAdminDetail (query) {
    return request({
      url: '/apply-invoices-record/zborder/' + query,
      method: 'get'
    })
  },
  // 审批
  approveList (form) {
    return request({
      url: '/approve-record',
      data: form,
      method: 'post'
    })
  },
  // 修改审批状态
  updateApproveStatus (form) {
    return request({
      url: '/apply-invoices-record/updateStatus',
      data: form,
      method: 'put'
    })
  },
  // 上传文件
  uploadFile (form) {
    return request({
      url: '/apply-invoices-record/uploadFile',
      data: form,
      method: 'put'
    })
  },
  // 修改状态
  updateStatus (query) {
    return request({
      url: '/apply-invoices-record/updaStatus/' + query,
      method: 'put'
    })
  }
}
