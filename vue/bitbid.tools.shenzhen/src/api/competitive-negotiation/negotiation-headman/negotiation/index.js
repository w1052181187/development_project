import request from '@/utils/axios/components-axios'

export const negotiatingPoints = {
  // 获取谈判要点信息
  negotiatingPointsList (query) { // query表示谈判轮数
    return request({
      url: '/negotiating-points/bytask/' + query,
      method: 'get'
    })
  },
  // 谈判要点新增
  negotiatingPointsAdd (query) {
    return request({
      url: '/negotiating-points',
      method: 'post',
      data: query
    })
  },
  // 谈判要点修改
  negotiatingPointsUpdate (query) {
    return request({
      url: '/negotiating-points/update',
      method: 'post',
      data: query
    })
  },
  // 获取谈判内容
  negotiatingLogList (tenderId, num) {
    return request({
      url: '/negotiating-log/bytender/' + tenderId + '/' + num,
      method: 'get'
    })
  },
  // 谈判修改
  negotiatingLogUpdate (query) {
    return request({
      url: '/negotiating-log/update',
      method: 'post',
      data: query
    })
  },
  // 谈判
  negotiationCon (query) {
    return request({
      url: '/negotiating-points/' + query,
      method: 'get'
    })
  },
  // 谈判结果
  negotiationOutcomeList (query) { // query表示谈判轮数
    return request({
      url: '/negotiating-log/result/' + query,
      method: 'get'
    })
  },
  // 提交谈判结果
  negotiationOutcomeSub (query) {
    return request({
      url: '/negotiating-log/submitNeoLog/' + query,
      method: 'post'
    })
  },
  // 谈判汇总
  negotiationHeadSummaryList (query) { // query表示谈判轮数
    return request({
      url: '/negotiating-log/report/' + query,
      method: 'get'
    })
  },
  // 谈判汇总(评委评审情况)
  JudgesList (query, num) {
    return request({
      url: '/negotiating-log/result-by-tender/' + query + '/' + num,
      method: 'get'
    })
  },
  // 谈判汇总(谈判纪要)
  negotiationBriefList (query) { // query表示谈判轮数
    return request({
      url: '/negotiating-brief/bytask/' + query,
      method: 'get'
    })
  },
  // 谈判汇总(发布)
  publish (query) {
    return request({
      url: '/negotiating-brief/checkPublicNeoBrief/' + query,
      method: 'post'
    })
  },
  // 谈判汇总(驳回)
  backNeoBrief (num, query) {
    return request({
      url: '/negotiating-brief/checkBackNeoBrief/' + num,
      method: 'post',
      params: query
    })
  },
  // 发送结果
  sendStatus (query) {
    return request({
      url: '/negotiating-brief/sendReport/' + query,
      method: 'post'
    })
  }
}
