import request from '@/utils/axios/components-axios'

export const pushData = {
  // 项目
  projectList (params) {
    return request({
      url: '/push-data/project',
      method: 'get',
      params: params
    })
  },
  projectDetail (params) {
    return request({
      url: '/push-data/project/' + params,
      method: 'get'
    })
  },

  // 采购项目
  fabaoList (params) {
    return request({
      url: '/push-data/fabao',
      method: 'get',
      params: params
    })
  },
  fabaoDetail (params) {
    return request({
      url: '/push-data/fabao/' + params,
      method: 'get'
    })
  },
  // 包组信息
  sectionList (params) {
    return request({
      url: '/push-data/section',
      method: 'get',
      params: params
    })
  },
  sectionDetail (params) {
    return request({
      url: '/push-data/section/' + params,
      method: 'get'
    })
  },
  // 文件公示
  filePublicityList (params) {
    return request({
      url: '/push-data/filePublicity',
      method: 'get',
      params: params
    })
  },
  filePublicityDetail (params) {
    return request({
      url: '/push-data/filePublicity/' + params,
      method: 'get'
    })
  },
  // 采购公告
  annoList (params) {
    return request({
      url: '/push-data/announcement',
      method: 'get',
      params: params
    })
  },
  annoDetail (params) {
    return request({
      url: '/push-data/announcement/' + params,
      method: 'get'
    })
  },
  // 变更公告
  annoBgList (params) {
    return request({
      url: '/push-data/announcementBg',
      method: 'get',
      params: params
    })
  },
  annoBgDetail (params) {
    return request({
      url: '/push-data/announcementBg/' + params,
      method: 'get'
    })
  },
  // 采购邀请
  yaoqingList (params) {
    return request({
      url: '/push-data/yaoqing',
      method: 'get',
      params: params
    })
  },
  // 采购邀请供应商确认列表
  supplierList (params) {
    return request({
      url: '/push-data/gongYsAffirm',
      method: 'get',
      params: params
    })
  },
  // 采购邀请供应商确认详情
  supplierDetail (params) {
    return request({
      url: '/push-data/gongYsAffirm/' + params,
      method: 'get'
    })
  },
  yaoqingDetail (params) {
    return request({
      url: '/push-data/yaoqing/' + params,
      method: 'get'
    })
  },
  // 采购邀请（查看确认情况）
  yaoqingAffirmList (params) {
    return request({
      url: '/push-data/yaoqingAffirm',
      method: 'get',
      params: params
    })
  },
  // 资格预审文件
  ysFile (params) {
    return request({
      url: '/push-data/ysFile',
      method: 'get',
      params: params
    })
  },
  ysFileDetail (params) {
    return request({
      url: '/push-data/ysFile/' + params,
      method: 'get'
    })
  },
  ysFileDownload (params) {
    return request({
      url: '/push-data/ysFileDownload/',
      method: 'get',
      params: params
    })
  },
  // 资格预审澄清文件
  ysCqFile (params) {
    return request({
      url: '/push-data/ysCqFile',
      method: 'get',
      params: params
    })
  },
  ysCqFileDetail (params) {
    return request({
      url: '/push-data/ysCqFile/' + params,
      method: 'get'
    })
  },
  // 资格预审结果通知书
  zgscBgFile (params) {
    return request({
      url: '/push-data/zgscBgFile',
      method: 'get',
      params: params
    })
  },
  zgscBgResult (params) {
    return request({
      url: '/push-data/zgscBgResult',
      method: 'get',
      params: params
    })
  },
  zgscBgFileDetail (params) {
    return request({
      url: '/push-data/zgscBgFile/' + params,
      method: 'get'
    })
  },
  // 采购文件
  zbwjFile (params) {
    return request({
      url: '/push-data/zbwjFile',
      method: 'get',
      params: params
    })
  },
  zbwjDownload (params) {
    return request({
      url: '/push-data/zbwjDownload',
      method: 'get',
      params: params
    })
  },
  zbwjFileDetail (params) {
    return request({
      url: '/push-data/zbwjFile/' + params,
      method: 'get'
    })
  },
  // 采购文件澄清
  zbwjbcFile (params) {
    return request({
      url: '/push-data/zbwjbcFile',
      method: 'get',
      params: params
    })
  },
  zbwjbcFileDetail (params) {
    return request({
      url: '/push-data/zbwjbcFile/' + params,
      method: 'get'
    })
  },
  // 缴费情况
  zbOrder (params) {
    return request({
      url: '/push-data/zbOrder',
      method: 'get',
      params: params
    })
  },
  zbOrderDetail (params) {
    return request({
      url: '/push-data/zbOrder/' + params,
      method: 'get'
    })
  },
  // 评标报告
  pingBiao (params) {
    return request({
      url: '/push-data/pingBiao',
      method: 'get',
      params: params
    })
  },
  pingBiaoDetail (params) {
    return request({
      url: '/push-data/pingBiao/' + params,
      method: 'get'
    })
  },
  // 中标确认函
  confirmation (params) {
    return request({
      url: '/push-data/confirmation',
      method: 'get',
      params: params
    })
  },
  confirmationDetail (params) {
    return request({
      url: '/push-data/confirmation/' + params,
      method: 'get'
    })
  },
  // 中标结果公告
  dingBiao (params) {
    return request({
      url: '/push-data/dingBiao',
      method: 'get',
      params: params
    })
  },
  dingBiaoDetail (params) {
    return request({
      url: '/push-data/dingBiao/' + params,
      method: 'get'
    })
  },
  // 中标通知书
  advice (params) {
    return request({
      url: '/push-data/advice',
      method: 'get',
      params: params
    })
  },
  adviceDetail (params) {
    return request({
      url: '/push-data/advice/' + params,
      method: 'get'
    })
  },
  // 合同
  contract (params) {
    return request({
      url: '/push-data/contract',
      method: 'get',
      params: params
    })
  },
  contractDetail (params) {
    return request({
      url: '/push-data/contract/' + params,
      method: 'get'
    })
  },
  baoGaoDetail (params) {
    return request({
      url: '/push-data/baoGao/' + params,
      method: 'get'
    })
  },
  // 审批
  check (params) {
    return request({
      url: '/push-data/approve',
      method: 'post',
      params: params
    })
  },
  // 开标记录表
  kbLog (params) {
    return request({
      url: '/push-data/kbLog',
      method: 'get',
      params: params
    })
  },
  kbLogDetail (params) {
    return request({
      url: '/push-data/kbLog/' + params,
      method: 'get'
    })
  },
  kbLogProgress (id, biaoDuanId) {
    return request({
      url: '/push-data/kbLogConPro/' + id + '/' + biaoDuanId,
      method: 'get'
    })
  },
  // 采购情况备案
  buyingSituationList (params) {
    return request({
      url: '/push-data/annoBaoGao',
      method: 'get',
      params: params
    })
  },
  buyingSituationDetail (id) {
    return request({
      url: '/push-data/annoBaoGao/' + id,
      method: 'get'
    })
  }
}
