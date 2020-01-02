// /**
//  * 引入所有服务器请求模块
//  */

// 示例
export { demo } from './demo/demo'
// 权限
export { auth } from './base/auth'
// 首页信息
export { homeIndex } from './base/homeIndex'
// 系统信息
export { systemInfo } from './base/systemInfo'
// 友情链接
export { friendLink } from './system/friendLink/friendLink'
// 用户管理
export { user } from './organ/user/user'
// 角色管理
export { role } from './organ/role/role'
// 角色的权限管理
export { permission } from './organ/role/permission'
// 集团公司管理
export { enterprise } from './organ/enterprise/enterprise'
// 部门管理
export { department } from './organ/department/department'
// 选聘备案申请
export { drawrecord } from './draw/drawrecord/drawrecord'
// 选聘备案审批
export { drawreview } from './draw/drawreview/drawreview'
// 抽签项目管理
export { drawproject } from './draw/drawproject/drawproject'
// 抽签
export { drawroom } from './draw/drawroom/drawroom'
// 抽签结果备案查询
export { drawresult } from './draw/drawresult/drawresult'
// 年度项目计划
export { planapp } from './plan/planapp/planapp'
// 采购建议计划下达
export { plandown } from './plan/plandown/plandown'
// 招标计划提交
export { plansubmit } from './plan/plansubmit/plansubmit'

export { planproject } from './plan/planproject/planproject'
// 项目立项申请
export { prorequest } from './plan/prorequest/prorequest'
// 评标专家管理
export { expertmanage } from './resource/expertmanage/expertmanage'
// 中介机构管理
export { intermanage } from './resource/intermanage/intermanage'
// 招标备案-招标项目管理
export { tenderproject } from './record/processrecord/tenderProject'
// 招标备案-招标项目项目经理管理
export { projectManager } from './record/processrecord/projectManager'
// 招标备案-招标项目变更历史管理
export { changeRecord } from './record/processrecord/changeRecord'
// 招标备案-标段管理
export { section } from './record/processrecord/section'
// 招标备案-公告管理
export { notice } from './record/processrecord/notice'
// 招标备案-公告进度管理
export { noticeProgress } from './record/processrecord/noticeProgress'
// 招标备案-投标人管理
export { bidderInfo } from './record/processrecord/bidderInfo'
// 招标备案- 中标人管理
export { bidWinner } from './record/processrecord/bidWinner'
// 招标文件
export { bidfile } from './record/processrecord/bidfile'
// 招标控制价
export { bidprice } from './record/processrecord/bidprice'
// 资格预审文件
export { qualfile } from './record/processrecord/qualfile'
// 开评标
export { openbid } from './record/processrecord/openbid'
// 资格预审文件答疑补遗
export { answerques } from './record/processrecord/answerques'
// 候选人公示
export { candNotice } from './record/processrecord/candNotice'
// 结果公示
export { resnotice } from './record/processrecord/resnotice'
// 资格预审会
export { qualfileResult } from './record/processrecord/qualfileResult'
// 招标结果查询
export { resultquery } from './record/resultquery/resultquery'
// 档案管理
export { archivesManagement } from './files/filemanage/archivesManagement'
// 档案信息管理
export { archivesInfo } from './files/filemanage/archivesInfo'
// 档案管理归档附件
export { archivesFile } from './files/filemanage/archivesFile'
// 档案管理附加信息表
export { sectionArchivesExtras } from './files/filemanage/sectionArchivesExtras'
// 档案管理邀请招标开评定标信息
export { inviteOpenEvalFile } from './files/filemanage/inviteOpenEvalFile'
// 资金平衡表管理
export { balanceSheet } from './planmanage/prounit/balancemanage/balanceSheet'
// 资金平衡表维护记录管理
export { balanceSheetRecord } from './planmanage/prounit/balancemanage/balanceSheetRecord'
// 投资建议计划管理
export { investmanage } from './planmanage/prounit/investmanage'
// 系统管理 - 计算配置管理
export { constantMaintenance } from './system/comconfig/constantMaintenance'
// 首页- 通知消息
export { message } from './workdesk/notice/message'
// 公告通知
export { systemnotice } from './system/notice'
// 首页- 待办消息
export { agendaMessage } from './workdesk/todo/agendaMessage'
// 考评模块 - 评价标签库
export { evaluateLabel } from './evaluate/evaluateLabel'
// 考评模块 - 项目评价信息
export { projectEvaluate } from './evaluate/projectEvaluate'
// 考评模块 - 专家评价信息
export { expertEvaluateInfo } from './evaluate/expertEvaluateInfo'
// 供应商库
export { supplierBase } from './supply/supplierBase'
// 合同管理 - 合同备案
export { contractInfo } from './contract/contractInfo'
// 招标信息确认
export { bidInfoConfirm } from './record/infoconfirm/bidInfoConfirm'
// 招标信息确认
export { loginLog } from './journal/loginLog'
// 代理机构计算器配置
export { computer } from './system/computer/computer'
// 招标代理费计算器
export { bidagent } from './workdesk/computer/bidagent'
// 行业资讯
export { industry } from './system/industry/industryNews'
// 造价咨询费计算器
export { costConst } from './system/computer/costConst'
// 统计分析模块 - 煤安费汇总子表
export { coalSafetyFeeSummarySublist } from './statistics/coal_use/coalSafetyFeeSummarySublist'
// 统计分析模块 - 煤安费汇总表
export { coalSafetyFeeSummary } from './statistics/coal_use/coalSafetyFeeSummary'
// 建议计划关联标段
export { suggestedPlanSectionInfo } from './statistics/suggestedPlanSectionInfo'
// 年度汇总
export { yearPlanSummary } from './statistics/yearPlanSummary'
// 建议计划年度总计
export { suggestedPlanAnnualTotal } from './statistics/coal_saftey/SuggestedPlanAnnualTotal'
// 交易统计
export { platform } from './statistics/platform'
// 统计分析模块 - 招标情况统计
export { tenderStatusStatistic } from './statistics/bid-statist/tenderStatusStatistic'
// 施工监理费计算器
export { supervisionRate } from './workdesk/computer/supervisionRate'
// 计算器信息(管理员部分）的新增
export { calculatorInfo } from './system/computer/calculatorInfo'
