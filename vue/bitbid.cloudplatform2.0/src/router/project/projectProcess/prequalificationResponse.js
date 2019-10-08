/**
 * Created by Administrator on 2019-3-7.
 * 响应资格预审
 */
export default [
  {
    path: '/project/projectProcess/prequalification_response/add',
    name: '项目进度-添加报名信息',
    meta: {
      title: '项目进度-添加报名信息',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/add')
  },
  {
    path: '/project/projectProcess/prequalification_response/update',
    name: '项目进度-修改报名信息',
    meta: {
      title: '项目进度-修改报名信息',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/update')
  },
  {
    path: '/project/projectProcess/prequalification_response/detail/:objectId',
    name: '项目进度-查看报名信息',
    meta: {
      title: '项目进度-查看报名信息',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/detail')
  },
  {
    path: '/project/projectProcess/prequalification_response/add_cost',
    name: '项目进度-添加资审文件发售记录',
    meta: {
      title: '项目进度-添加资审文件发售记录',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/addCost')
  },
  {
    path: '/project/projectProcess/prequalification_response/update_cost',
    name: '项目进度-修改资审文件发售记录',
    meta: {
      title: '项目进度-修改资审文件发售记录',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/updateCost')
  },
  {
    path: '/project/projectProcess/prequalification_response/cost_detail/:objectId',
    name: '项目进度-查看资审文件发售记录',
    meta: {
      title: '项目进度-查看资审文件发售记录',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/costDetail')
  },
  {
    path: '/project/projectProcess/prequalification_response/add_file',
    name: '项目进度-添加资格申请文件',
    meta: {
      title: '项目进度-添加资格申请文件',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/addFile')
  },
  {
    path: '/project/projectProcess/prequalification_response/update_file',
    name: '项目进度-修改资格申请文件',
    meta: {
      title: '项目进度-修改资格申请文件',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/updateFile')
  },
  {
    path: '/project/projectProcess/prequalification_response/file_detail/:objectId',
    name: '项目进度-查看资格申请文件',
    meta: {
      title: '项目进度-查看资格申请文件',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/fileDetail')
  },
  {
    path: '/project/projectProcess/prequalification_response/add_doubt',
    name: '项目进度-添加资格预审疑义澄清',
    meta: {
      title: '项目进度-添加资格预审疑义澄清',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/addDoubt')
  },
  {
    path: '/project/projectProcess/prequalification_response/update_doubt',
    name: '项目进度-修改资格预审疑义澄清',
    meta: {
      title: '项目进度-修改资格预审疑义澄清',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/updateDoubt')
  },
  {
    path: '/project/projectProcess/prequalification_response/doubt_detail/:objectId',
    name: '项目进度-查看资格预审疑义澄清',
    meta: {
      title: '项目进度-查看资格预审疑义澄清',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/doubtDetail')
  },
  {
    path: '/project/projectProcess/prequalification_response/add_clarify',
    name: '项目进度-添加资格预审澄清文件',
    meta: {
      title: '项目进度-添加资格预审澄清文件',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/addClarify')
  },
  {
    path: '/project/projectProcess/prequalification_response/update_clarify',
    name: '项目进度-修改资格预审澄清文件',
    meta: {
      title: '项目进度-修改资格预审澄清文件',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/updateClarify')
  },
  {
    path: '/project/projectProcess/prequalification_response/clarify_detail/:objectId',
    name: '项目进度-查看资格预审澄清文件',
    meta: {
      title: '项目进度-查看资格预审澄清文件',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationResponse/clarifyDetail')
  },
  //  备注：查看投标单位报名名（新加页面，权限未处理）
  {
    path: '/project/projectProcess/applyAttachment/detail/:objectId',
    name: '项目进度-查看投标单位报名表',
    meta: {
      title: '项目进度-查看投标单位报名表',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/common/apply_attachment_detail')
  }
]
