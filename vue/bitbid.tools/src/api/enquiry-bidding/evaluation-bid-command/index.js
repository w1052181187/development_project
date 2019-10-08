import request from '@/utils/axios/components-axios'

export const evaBidCommand = {
  // ************************************下载评标报告***********************************************
  // 下载评标报告
  downloadReport () {
    return request({
      url: '/report/downloadReport',
      method: 'get'
    })
  },
  // ************************************下达开始评标命令***********************************************
  // 下达开始评标命令
  startEvaluation () {
    return request({
      url: '/check/beginNeoLog',
      method: 'post'
    })
  },
  // ************************************下达结束评标命令***********************************************
  // 下达结束评标命令
  endEvaluation () {
    return request({
      url: '/check/endNeoLog',
      method: 'post'
    })
  },
  // ************************************查询命令状态(开始)***********************************************
  // 查询开命令状态
  inquiryCommandStatus () {
    return request({
      url: '/check/beginNeoLogPage',
      method: 'get'
    })
  },
  inquiryCommandEnd () {
    return request({
      url: '/check/endNeoLogPage',
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
  inquiryTrialCommandStart () { // 资审
    return request({
      url: '/check/beginZGSHPage',
      method: 'get'
    })
  },
  inquiryTrialCommandEnd () { // 资审
    return request({
      url: '/check/endZGSHPage',
      method: 'get'
    })
  },
  // ************************************下达开始资审命令***********************************************
  // 下达开始资审命令
  startTrial () {
    return request({
      url: '/check/beginZGSH',
      method: 'post'
    })
  },
  // 下达结束资审命令
  endTrial () {
    return request({
      url: '/check/endZGSH',
      method: 'post'
    })
  },
  // ************************************获取评标报告***********************************************
  reportView () {
    return request({
      url: '/report/view',
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
  // 上传文件
  uploadResponse (query) {
    return request({
      url: '/report/update',
      method: 'post',
      data: query
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
  }
}
