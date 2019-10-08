import request from '@/utils/axios/components-axios'

export const review = {
  // 谈判供应商
  negotiatingSuppliersSele (query) {
    return request({
      url: 'check/getTenderUserList/' + query,
      method: 'get'
    })
  },
  // 谈判供应商选择数据
  negotiatingSuppliersList (query) {
    return request({
      url: '/check',
      method: 'get',
      params: query
    })
  },
  // 不合格理由保存
  preservationUnqualified (query) {
    return request({
      url: '/check/update',
      method: 'post',
      data: query
    })
  },
  /* ==================================================评审结果=============================================== */
  // 评审结果列表
  reviewResultsList (query) {
    return request({
      url: '/check/result',
      method: 'get',
      params: query
    })
  },
  // 提交评审结果
  reviewResultsSubmit (checkType, query) {
    return request({
      url: '/check/submitSH/' + checkType,
      method: 'post',
      data: query
    })
  },
  /* ==================================================评审汇总=============================================== */
  // 评审汇总列表
  reviewSummaryList (checkType) {
    return request({
      url: '/check/report/' + checkType,
      method: 'get'
    })
  },
  // 评审项目汇总结果列表
  ReviewProjectSummaryList (query, checkType) {
    return request({
      url: '/check/reportByTender/' + query + '/' + checkType,
      method: 'get'
    })
  },
  // 评委评审情况列表
  tabReviewJudgesList (tenderId, itemId, checkType) {
    return request({
      url: '/check/reportByItem/' + tenderId + '/' + itemId + '/' + checkType,
      method: 'get'
    })
  },
  // pdf 文件列表选择
  pdfSelectList (checkType) {
    return request({
      url: '/check/file-list/' + checkType,
      method: 'get'
    })
  },
  getInquiryResult () {
    return request({
      url: '/check/report/0?isLast=true',
      method: 'get'
    })
  }
}
