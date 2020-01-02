/** 考评系统 */
// 代理机构考评
import agenteval from './agenteval'
// 项目单位考评
import projecteval from './projecteval'
// 评标专家考评
import experteval from './experteval'
export default [
  ...agenteval,
  ...projecteval,
  ...experteval
]
