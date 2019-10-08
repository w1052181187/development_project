import request from '@/utils/axios/components-axios'

export const consultingReviewControl = {
  // 组建小组列表
  setUpNegotiationList (query) {
    return request({
      url: '/user',
      method: 'get',
      params: query
    })
  },
  // 增加小组
  setUpNegotiationAdd (query) {
    return request({
      url: '/user',
      method: 'post',
      data: query
    })
  },
  // 查看小组
  setUpNegotiationLook (query) {
    return request({
      url: '/user/' + query,
      method: 'get'
    })
  },
  // 编辑小组后提交数据
  setUpNegotiationUpdate (query) {
    return request({
      url: '/user/update',
      method: 'post',
      data: query
    })
  },
  // 提交小组组长
  setLeader (objectId, oldRole, newRole) {
    return request({
      url: '/user/update/' + objectId + '/' + oldRole + '/' + newRole,
      method: 'post'
    })
  },
  // 查看小组
  setUpNegotiationDelete (query) {
    return request({
      url: '/user/' + query,
      method: 'delete'
    })
  },
  // ************************************下载评标报告***********************************************
  // 下载评标报告
  downloadReport () {
    return request({
      url: '/report/downloadReport',
      method: 'get'
    })
  },
  // 生成评标报告
  creatReport () {
    return request({
      url: '/report/createReport',
      method: 'post'
    })
  },
  // 上传文件
  uploadResponse (query) {
    return request({
      url: '/report/update',
      method: 'post',
      data: query
    })
  },
  // 保存文件
  saveResponse (query) {
    return request({
      url: '/report/getSave',
      method: 'get',
      params: query
    })
  },
  // ************************************下达开始评标命令***********************************************
  // 下达开始评标命令
  douwnStartEvaluation () {
    return request({
      url: '/negotiating-log/beginNeoLog',
      method: 'post'
    })
  },
  // ************************************下达开始资审命令***********************************************
  // 下达开始资审命令
  douwnStartTrial () {
    return request({
      url: '/check/beginZGSH',
      method: 'post'
    })
  },
  // ************************************下达结束评标命令***********************************************
  // 下达结束评标命令
  douwnEndEvaluation () {
    return request({
      url: '/negotiating-log/endNeoLog',
      method: 'post'
    })
  },
  // ************************************下达结束资审命令***********************************************
  // 下达结束资审命令
  douwnEndTrial () {
    return request({
      url: '/check/endZGSH',
      method: 'post'
    })
  },
  // ************************************查询命令状态(开始)***********************************************
  // 查询开命令状态
  inquiryCommandStatus () { // 谈判
    return request({
      url: '/negotiating-log/beginNeoLogPage',
      method: 'get'
    })
  },
  inquiryZishenCommandStatus () { // 资审
    return request({
      url: '/check/beginZGSHPage',
      method: 'get'
    })
  },
  inquiryCommandEnd () { // 谈判
    return request({
      url: '/negotiating-log/endNeoLogPage',
      method: 'get'
    })
  },
  retrialCommandEnd () { // 资审
    return request({
      url: '/check/endZGSHPage',
      method: 'get'
    })
  },
  // 登陆信息获取
  loginInfo (query) {
    return request({
      url: '/activeUser/' + query,
      method: 'get'
    })
  },
  // ************************************评审项目设置***********************************************
  // 左侧边评审项目列表
  navReviewProjectList (query) {
    return request({
      url: '/checkType',
      method: 'get',
      params: query
    })
  },
  // 右侧边表格列表
  tableProjectList (query) {
    return request({
      url: '/checkItem',
      method: 'get',
      params: query
    })
  },
  // ************************************项目信息数据***********************************************
  projectList (query) {
    return request({
      url: '/task/info',
      method: 'get',
      params: query
    })
  },
  // ************************************获取评标报告***********************************************
  reportView () {
    return request({
      url: '/report/view',
      method: 'get'
    })
  },
  // 获取pdf
  pdfDetail (query) {
    return request({
      url: '/report/PDFSignatureServer/index',
      method: 'get',
      params: query
    })
  },
  // 发布pdf
  pdfPulish (query) {
    return request({
      url: '/PDFSignatureServer/getSignFileReadonly/' + query,
      method: 'get'
    })
  },
  // 签章pdf
  pdfSignature (query) {
    return request({
      url: '/PDFSignatureServer/getSignFileReadonly/' + query,
      method: 'get'
    })
  },
  // 是否开始签章
  isStartEndReport (query) {
    return request({
      url: '/report/getSignFileReadonly/' + query,
      method: 'get'
    })
  },
  // 开始签章（锁定评审报告）
  startSignature (query) {
    return request({
      url: '/report/setSignFileReadonly/' + query,
      method: 'get'
    })
  },
  // 解除锁定评审报告
  unlockReport (query) {
    return request({
      url: '/report/cancelSignFileReadonly/' + query,
      method: 'get'
    })
  },
  // ************************************废标***********************************************
  inquiryScrapInstruct () { // 获取是否废标
    return request({
      url: '/task/info',
      method: 'get'
    })
  },
  downScrapInstruct (params) { // 获取是否废标
    return request({
      url: '/task/unused',
      method: 'post',
      data: params
    })
  },
  // ************************************招标文件***********************************************
  // 获取谈判轮数
  getBidNumList () {
    return request({
      url: '/check/bidNumList',
      method: 'get'
    })
  },
  // 根据谈判轮数获取招标文件
  getBidFile (params) {
    return request({
      url: '/check/viewBuyerFileByBidNum/' + params,
      method: 'get'
    })
  },
  // 根据谈判轮数获取投标文件
  getTenderFile (params) {
    return request({
      url: '/check/viewTenderFileByBidNum/' + params,
      method: 'get'
    })
  }
}
