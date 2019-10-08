import invite from './invite.js'
import announcement from './announcement.js'
import bidDocument from './bidDocument.js'
import bidOpening from './bidOpening.js'
import bidResponse from './bidResponse.js'
import calibrationResult from './calibrationResult.js'
import chechResult from './checkResult.js'
import prequalificationFile from './prequalificationFile.js'
import prequalificationResponse from './prequalificationResponse.js'
import wasteBidResult from './wasteBidResult.js'

export default [
  {
    path: '/processManage/projectProcess',
    name: 'projectProcess-list',
    meta: {
      title: '项目进度管理'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/index'], resolve)
  },
  {
    path: '/processManage/projectProcess/update',
    name: 'projectProcess-update',
    meta: {
      title: '项目进度管理-修改'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/update'], resolve)
  },
  {
    path: '/processManage/projectProcess/detail/:objectId',
    name: 'projectProcess-detail',
    meta: {
      title: '项目进度管理-详情'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/detail'], resolve)
  },
  {
    path: '/processManage/projectProcess/project',
    name: 'projectProcess-project',
    meta: {
      title: '项目管理'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/project'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/add',
    name: 'projectProcess-project-addsection',
    meta: {
      title: '项目管理-添加标段'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/addsection'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/update',
    name: 'projectProcess-project-addsection',
    meta: {
      title: '项目管理-修改标段'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/updatesection'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/detail/:objectId',
    name: 'projectProcess-project-addsection',
    meta: {
      title: '项目管理-标段详情'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/sectiondetail'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/:code',
    name: 'projectProcess-project-process',
    meta: {
      title: '项目管理-流程管理'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/process'], resolve)
  },
  ...invite,
  ...announcement,
  ...bidDocument,
  ...bidOpening,
  ...bidResponse,
  ...calibrationResult,
  ...chechResult,
  ...prequalificationFile,
  ...prequalificationResponse,
  ...wasteBidResult
]
