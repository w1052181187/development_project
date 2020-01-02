import ajax from '@/common/js/ajax'

export const investmanage = {
  /**
   * 列表查询- 投资建议计划
   * @param {*} query [查询信息]
   */
  getList (query) {
    return ajax({
      url: '/investmentProposalScheme-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询- 投资建议计划
   * @param {*} query [查询信息]
   */
  getNewestOne (query) {
    return ajax({
      url: '/investmentProposalScheme-infos/newestOne',
      method: 'get',
      params: query
    })
  },
  /**
   * 确认- 投资建议计划
   * @param {*} data [主体数据]
   */
  confirm (data) {
    return ajax({
      url: '/investmentProposalScheme-infos/confirm',
      method: 'put',
      data: data
    })
  },
  /**
   * 列表查询- 资金来源分类
   * @param {*} query [查询信息]
   */
  getClassifyList (query) {
    return ajax({
      url: '/capitalSourceClassify-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询- 资金来源分类
   * @param {*} query [查询信息]
   */
  getClassify (query) {
    return ajax({
      url: '/capitalSourceClassify-infos/queryOne',
      method: 'get',
      params: query
    })
  },
  /**
   * 列表查询- 资金来源
   * @param {*} query [查询信息]
   */
  getCapitalSourceList (query) {
    return ajax({
      url: '/capitalSource-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 列表查询- 建议计划
   * @param {*} query [查询信息]
   */
  getSuggestedPlanList (query) {
    return ajax({
      url: '/suggestedPlan-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询- 建议计划
   * @param {*} query [查询信息]
   */
  getNewestSuggestedPlanList (query) {
    return ajax({
      url: '/suggestedPlan-infos/newestList',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询- 建议计划
   */
  getSuggestedPlanBySectionCode (sectionCode) {
    return ajax({
      url: '/suggestedPlan-infos/' + sectionCode,
      method: 'get'
    })
  },
  /**
   * 查询- 统计完成情况
   */
  getStatisticsElementTree (query) {
    return ajax({
      url: '/suggestedPlan-infos/statistics',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询- 资金投向
   * @param {*} query [查询信息]
   */
  getFundPurposeList (query) {
    return ajax({
      url: '/fundPurpose-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询- 资金投向 用于统计分析
   * @param {*} query [查询信息]
   */
  getFundPurposeOneList (query) {
    return ajax({
      url: '/fundPurpose-infos/query',
      method: 'get',
      params: query
    })
  },
  /**
   * 单条查询- 申报表
   * @param {*} query [查询信息]
   */
  getDeclarationForm (query) {
    return ajax({
      url: '/declarationForm-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 列表查询- 审核记录
   * @param {*} query [查询信息]
   */
  getRecordList (query) {
    return ajax({
      url: '/sourceClassifyRecord-infos',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询同一资金来源下首个资源管理分类编号
   * @param {*} query [查询信息]
   */
  getFirstClassifyCode (query) {
    return ajax({
      url: '/capitalSourceClassify-infos/firstClassifyCode',
      method: 'get',
      params: query
    })
  },
  /**
   * 更新-投资建议计划
   * @param {*} data [主体数据]
   */
  saveScheme (data) {
    return ajax({
      url: '/investmentProposalScheme-infos',
      method: 'put',
      data: data
    })
  },
  /**
   * 更新-资金来源分类表
   * @param {*} data [主体数据]
   */
  saveClassify (data) {
    return ajax({
      url: '/capitalSourceClassify-infos',
      method: 'put',
      data: data
    })
  },
  /**
   * 新增接口-建议计划
   * @param {*} data [主体数据]
   */
  saveSuggestedPlan (data) {
    return ajax({
      url: '/suggestedPlan-infos',
      method: 'put',
      data: data
    })
  },
  /**
   * 批量修改接口-建议计划
   * @param {*} data [主体数据]
   */
  updateSuggestedPlans (data) {
    return ajax({
      url: '/suggestedPlan-infos/batch',
      method: 'put',
      data: data
    })
  },
  /**
   * 新增接口-申报表
   * @param {*} data [主体数据]
   */
  saveDeclarationForm (data) {
    return ajax({
      url: '/declarationForm-infos',
      method: 'put',
      data: data
    })
  },
  /**
   * 新增接口-资金来源分类维护记录表
   * @param {*} data [主体数据]
   */
  saveRecord (data) {
    return ajax({
      url: '/sourceClassifyRecord-infos',
      method: 'put',
      data: data
    })
  },
  /**
   * 删除接口- 建议计划
   * @param {[long]} id [主键]
   */
  deleteSuggestedPlan (id) {
    return ajax({
      url: '/suggestedPlan-infos/' + id,
      method: 'delete'
    })
  },
  /**
   * 查询未制定成招标计划的采购建议计划数量
   * @param {*} query [查询信息]
   */
  selectCount (query) {
    return ajax({
      url: '/suggestedPlan-infos/count',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询建议计划树结构
   * @param {*} query [查询信息]
   */
  selectElementTree (query) {
    return ajax({
      url: '/suggestedPlan-infos/test',
      method: 'get',
      params: query
    })
  },
  /**
   * 根据企业类型查询对应的资金来源
   */
  selectCapitalSourceByEnterpriseType (enterpriseType) {
    return ajax({
      url: '/enterprise-type-capital-source/' + enterpriseType,
      method: 'get'
    })
  },
  /**
   * 单条查询- 资金来源
   * @param {*} query [查询信息]
   */
  getOneCapitalSource (id) {
    return ajax({
      url: '/capitalSource-infos/query/' + id,
      method: 'get'
    })
  },
  /**
   * 查询列表- 申报表
   * @param {*} query [查询信息]
   */
  getDeclarationFormList (query) {
    return ajax({
      url: '/declarationForm-infos/list',
      method: 'get',
      params: query
    })
  },
  /**
   * 查询历史申报表
   */
  getHistoryDeclList (enterpriseId, capitalSource) {
    return ajax({
      url: '/declarationForm-infos/' + enterpriseId + '/' + capitalSource,
      method: 'get'
    })
  }
}
