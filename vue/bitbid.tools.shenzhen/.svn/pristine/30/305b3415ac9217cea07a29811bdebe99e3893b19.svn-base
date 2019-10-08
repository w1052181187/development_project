import request from '@/utils/axios/components-axios'

export const agencyReviewSummary = {
  // 谈判纪要中谈判内容获取
  reviewAgencySummaryList (query) {
    return request({
      url: '/negotiating-brief/bytask/' + query,
      method: 'get'
    })
  },
  // 谈判纪要中谈判内容添加
  reviewAgencySummaryadd (query) {
    return request({
      url: '/negotiating-brief',
      method: 'post',
      data: query
    })
  },
  // 谈判纪要中谈判内容修改
  reviewAgencySummaryUpdate (query) {
    return request({
      url: '/negotiating-brief/update',
      method: 'post',
      data: query
    })
  },
  // 侧边导航数据
  leftNav () {
    return request({
      url: '/resource/autoList',
      method: 'get'
    })
  },
  // -------------------------------------------------  谈判汇总 ---------------------------------------------------
  // 获取谈判要点信息
  negotiatingPointsList (query) { // query表示谈判轮数
    return request({
      url: '/negotiating-points/bytask/' + query,
      method: 'get'
    })
  },
  // 获取谈判结果
  negotiationSummaryTablelList (query) {
    return request({
      url: '/negotiating-log/result/' + query,
      method: 'get'
    })
  },
  // 获取谈判汇总(没有提交不显示)
  resultList (query) {
    return request({
      url: '/negotiating-log/report/' + query,
      method: 'get'
    })
  },
  // 评委评审情况列表
  tabReviewJudgesList (tenderId, itemId) {
    return request({
      url: '/negotiating-log/result-by-tender/' + tenderId + '/' + itemId,
      method: 'get'
    })
  },
  // -------------------------------------------------  上传响应澄清与承诺 ---------------------------------------------------
  // 页面访问(谈判要点)
  viewPage (query) {
    return request({
      url: '/negotiating-points/viewPage/' + query,
      method: 'get'
    })
  },
  // 页面访问（谈判纪要）
  viewSummaryPage (query) {
    return request({
      url: '/negotiating-brief/viewPage/' + query,
      method: 'get'
    })
  },
  // 页面访问（谈判澄清）
  viewClarifyPage (query) {
    return request({
      url: '/negotiating-answer/viewPage/' + query,
      method: 'get'
    })
  },
  // 页面提交（谈判澄清）
  viewClarifySub (query) {
    return request({
      url: '/negotiating-answer/submit/' + query,
      method: 'post'
    })
  },
  // 获取文件
  obtainResponse (query) {
    return request({
      url: '/negotiating-answer/bytask/' + query,
      method: 'get'
    })
  },
  // 获取下一轮报价
  gainOffer (query) {
    return request({
      url: '/tender/bytask/' + query,
      method: 'get'
    })
  },
  // 上传文件
  uploadResponse (query) {
    return request({
      url: '/negotiating-answer/update',
      method: 'post',
      data: query
    })
  },
  // 报价新增
  offerAdd (query) {
    return request({
      url: '/tender',
      method: 'post',
      data: query
    })
  },
  // 报价修改
  offerUpdate (query) {
    return request({
      url: '/tender/update',
      method: 'post',
      data: query
    })
  },
  handleNextOpenBid () {
    return request({
      url: '/negotiating-brief/nextActiviti',
      method: 'get'
    })
  },
  getOpenBid () {
    return request({
      url: '/negotiating-brief/nextActivitiPage',
      method: 'get'
    })
  },
  // 页面访问（响应与澄清文件页面）
  clarifyPage (query) {
    return request({
      url: '/negotiating-answer/viewPage/' + query,
      method: 'get'
    })
  },
  // 审核澄清与承诺列表
  clarifys (query) {
    return request({
      url: '/negotiating-answer',
      method: 'get',
      params: query
    })
  },
  // 确认澄清与承诺
  confirmClarifys (query) {
    return request({
      url: '/negotiating-answer/update',
      method: 'post',
      data: query
    })
  },
  // 查看下一轮状态
  lookNext (query) {
    return request({
      url: '/negotiating-brief/nextActivitiPage/' + query,
      method: 'get'
    })
  },
  // 开始下一轮谈判
  getNextOpenBid () {
    return request({
      url: '/negotiating-brief/nextActiviti',
      method: 'post'
    })
  },
  // 下一轮报价列表
  nextOffers (query) {
    return request({
      url: '/tender',
      method: 'get',
      params: query
    })
  },
  // 报价人中的列表
  tenderList (query) {
    return request({
      url: '/negotiating-answer/getTenderList/' + query,
      method: 'get'
    })
  },
  // 评审汇总列表
  addNum (query) {
    return request({
      url: '/negotiating-brief/addNegoRound/' + query,
      method: 'post'
    })
  }
}
