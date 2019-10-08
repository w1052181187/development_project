import request from '@/utils/axios/components-axios'

export const billClearData = {
  // 方案列表
  programList (params) {
    return request({
      url: '/cleaningbid/program',
      method: 'get',
      params: params
    })
  },
  // 新建方案之前 先校验是否能打开房间
  isOpen () {
    return request({
      url: '/cleaningbid/room/isOpen',
      method: 'get'
    })
  },
  // 新建、保存方案
  save (program) {
    return request({
      url: '/cleaningbid/program',
      method: 'post',
      data: program
    })
  },
  // 更新、修改方案
  update (program) {
    return request({
      url: '/cleaningbid/program',
      method: 'put',
      data: program
    })
  },
  // 查看方案
  detail (objectId) {
    return request({
      url: '/cleaningbid/program/' + objectId,
      method: 'get'
    })
  },
  // 删除方案
  delete (objectId) {
    return request({
      url: '/cleaningbid/program/' + objectId,
      method: 'delete'
    })
  },
  // 打开房间
  open (objectId) {
    return request({
      url: '/cleaningbid/room/open/' + objectId,
      method: 'get'
    })
  },
  // 保存招标控制价
  saveControlInfo (program) {
    return request({
      url: '/cleaningbid/project/control/data',
      method: 'post',
      data: program
    })
  },
  // 读取投标信息
  getTenderInfo (objectId) {
    return request({
      url: 'cleaningbid/room/tenders/' + objectId,
      method: 'get'
    })
  },
  // 删除投标信息
  delTenderInfo (objectId, programId) {
    return request({
      url: 'cleaningbid/project/tender/' + objectId + '?programId=' + programId,
      method: 'delete'
    })
  },
  // 一键清标 开始检查
  startCheck (objectId) {
    return request({
      url: 'cleaningbid/room/start/' + objectId,
      method: 'get'
    })
  },
  // 一键清标 获取进度
  getProgress (objectId) {
    return request({
      url: 'cleaningbid/room/progress/' + objectId,
      method: 'get'
    })
  },
  // 清标结果汇总
  totalResult (objectId) {
    return request({
      url: 'cleaningbid/examine/summary/' + objectId,
      method: 'get'
    })
  },
  // 导出结果报告
  importResult (objectId) {
    return request({
      url: 'cleaningbid/examine/import/' + objectId,
      method: 'get'
    })
  },
  // 查看投标单位
  unitList (id, type) {
    return request({
      url: 'cleaningbid/examine/rule/' + id + '/' + type,
      method: 'get'
    })
  },
  // 查看检查结果
  checkResult (id, type, enterId) {
    return request({
      url: 'cleaningbid/examine/rule/' + id + '/' + type + '/' + enterId,
      method: 'get'
    })
  },
  // 不平衡检查
  unbalanceCheckItem (id, type, enterId, deviation, unbalanProportion, referenceValue, projectId) {
    return request({
      url: 'cleaningbid/examine/unbalance/' + id + '/' + type + '/' + enterId + '?deviation=' + deviation + '&unbalanProportion=' + unbalanProportion + '&referenceValue=' + referenceValue + '&projectId=' + projectId,
      method: 'get'
    })
  },
  // 雷同检查
  sameCheckItem (id, enterId, similarityType, judgeAmountPercent, judgeTotalPricePercent) {
    return request({
      url: 'cleaningbid/examine/similarity/' + id + '/' + enterId + '?similarityType=' + similarityType + '&judgeAmountPercent=' + judgeAmountPercent + '&judgeTotalPricePercent=' + judgeTotalPricePercent,
      method: 'get'
    })
  },
  sameTenderCheck (id, enterId, tenderId) {
    return request({
      url: 'cleaningbid/examine/similarity/' + id + '/' + enterId + '/' + tenderId,
      method: 'get'
    })
  }
}
