import request from '@/utils/axios/components-axios'

export const consultingPoints = {
  // 获取磋商要点信息
  consultingPointsList (query) { // query表示谈判轮数
    return request({
      url: '/negotiating-points/bytask/' + query,
      method: 'get'
    })
  },
  // 磋商要点新增
  consultingPointsAdd (query) {
    return request({
      url: '/negotiating-points',
      method: 'post',
      data: query
    })
  },
  // 磋商要点修改
  consultingPointsUpdate (query) {
    return request({
      url: '/negotiating-points/update',
      method: 'post',
      data: query
    })
  },
  // 获取磋商内容
  negotiatingLogList (tenderId, num) {
    return request({
      url: '/negotiating-log/bytender/' + tenderId + '/' + num,
      method: 'get'
    })
  },
  // 磋商修改
  consultingLogUpdate (query) {
    return request({
      url: '/negotiating-log/update',
      method: 'post',
      data: query
    })
  },
  // 磋商
  consultingCon (query) {
    return request({
      url: '/negotiating-points/' + query,
      method: 'get'
    })
  },
  // 磋商结果
  consultingOutcomeList (query) { // query表示谈判轮数
    return request({
      url: '/negotiating-log/result/' + query,
      method: 'get'
    })
  },
  // 提交磋商结果
  consultingOutcomeSub (query) {
    return request({
      url: '/negotiating-log/submitNeoLog/' + query,
      method: 'post'
    })
  },
  // 磋商汇总
  consultingHeadSummaryList (query) { // query表示谈判轮数
    return request({
      url: '/negotiating-log/report/' + query,
      method: 'get'
    })
  },
  // 磋商汇总(评委评审情况)
  JudgesList (query, num) {
    return request({
      url: '/negotiating-log/result-by-tender/' + query + '/' + num,
      method: 'get'
    })
  },
  // 磋商汇总(磋商纪要)
  consultingBriefList (query) { // query表示谈判轮数
    return request({
      url: '/negotiating-brief/bytask/' + query,
      method: 'get'
    })
  },
  // 磋商汇总(发布)
  publish (query, from) {
    return request({
      url: '/negotiating-brief/checkPublicNeoBrief/' + query,
      method: 'post',
      data: from
    })
  },
  // 磋商汇总(驳回)
  backNeoBrief (num, query) {
    return request({
      url: '/negotiating-brief/checkBackNeoBrief/' + num,
      method: 'post',
      params: query
    })
  }
}
