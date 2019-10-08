import request from '@/utils/axios/components-axios'

export const qualificationReview = {
  // 供应商
  negotiatingSuppliersSele (query) {
    return request({
      url: '/check/getTenderUserList',
      method: 'get',
      params: query
    })
  },
  // 供应商 (谈判)
  negotiateSele (query) {
    return request({
      url: '/negotiating-log/getTenderUserList/' + query,
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
  // 提交评审结果（符合性）
  reviewResultsSubmit (query) {
    return request({
      url: '/check/submitFHSH',
      method: 'post',
      data: query
    })
  },
  // 提交评审结果（合理性）
  rationalitySubmit (query) {
    return request({
      url: '/check/submitHLSH',
      method: 'post',
      data: query
    })
  },
  // 提交评审结果（资格性）
  qualificationSubmit (query) {
    return request({
      url: '/check/submitZGSH',
      method: 'post',
      data: query
    })
  },
  /* ==================================================评审汇总=============================================== */
  // 评审汇总列表
  reviewSummaryList (query) {
    return request({
      url: '/check/report',
      method: 'get',
      params: query
    })
  },
  // 评审项目汇总结果列表
  ReviewProjectSummaryList (query, checkType) {
    return request({
      url: '/check/reportByTender/' + query,
      method: 'get',
      params: checkType
    })
  },
  // 评委评审情况列表
  tabReviewJudgesList (tenderId, itemId, checkType) {
    return request({
      url: '/check/reportByItem/' + tenderId + '/' + itemId,
      method: 'get',
      params: checkType
    })
  },
  // pdf 文件列表选择
  pdfSelectList (checkType) {
    return request({
      url: '/check/file-list',
      method: 'get',
      params: checkType
    })
  }
}
