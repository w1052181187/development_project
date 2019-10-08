// 注册、忘记密码
import register from './register/index'
// 企业信息
import enterprise from './dailyManage/enterprise/index'
// 部门管理
import department from './dailyManage/department/index'
// 用户管理
import users from './dailyManage/users/index'
// 角色管理
import roles from './dailyManage/roles/index'
// 角色管理
import onlineApprove from './dailyManage/onlineApprove/index'
// 登记项目
import registProject from './processManage/registProject/index'
// 代理分配项目
import agentProject from './processManage/agentProject/index'
// 项目进度管理
import projectProcess from './processManage/projectProcess/index'
// 代理合同管理
import agencyContract from './processManage/agencyContract/index'
// 企业信息
import projectProcessSet from './processManage/projectProcessSet/index'
// 企业网站管理
// 首页管理
import homeManage from './websiteManage/home/index'
// 公司概况
import company from './websiteManage/company/index'
// 信息公告
import notice from './websiteManage/notice/index'
// 综合信息
import information from './websiteManage/information/index'
// 联系我们
import contact from './websiteManage/contact/index'

export default [
  ...enterprise,
  ...register,
  ...department,
  ...users,
  ...roles,
  ...onlineApprove,
  ...registProject,
  ...agentProject,
  ...projectProcess,
  ...agencyContract,
  ...projectProcessSet,
  ...homeManage,
  ...company,
  ...notice,
  ...information,
  ...contact
]
