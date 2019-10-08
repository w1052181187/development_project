/**
 * Created by Administrator on 2019-3-7.
 * 资格审查结果
 */
export default [
  {
    path: '/project/projectProcess/checkresult/add',
    name: '项目进度-添加资格预审专家',
    meta: {
      title: '项目进度-添加资格预审专家',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/checkResult/add')
  },
  {
    path: '/project/projectProcess/checkresult/update',
    name: '项目进度-修改资格预审专家',
    meta: {
      title: '项目进度-修改资格预审专家',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/checkResult/update')
  },
  {
    path: '/project/projectProcess/checkresult/detail/:objectId',
    name: '项目进度-查看资格预审专家',
    meta: {
      title: '项目进度-查看资格预审专家',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/checkResult/detail')
  },
  {
    path: '/project/projectProcess/checkresult/add_report',
    name: '项目进度-添加资格审查报告',
    meta: {
      title: '项目进度-添加资格审查报告',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/checkResult/addReport')
  },
  {
    path: '/project/projectProcess/checkresult/update_report',
    name: '项目进度-修改资格审查报告',
    meta: {
      title: '项目进度-修改资格审查报告',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/checkResult/updateReport')
  },
  {
    path: '/project/projectProcess/checkresult/report_detail/:objectId',
    name: '项目进度-查看资格审查报告',
    meta: {
      title: '项目进度-查看资格审查报告',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/checkResult/reportDetail')
  },
  {
    path: '/project/projectProcess/checkresult/add_result',
    name: '项目进度-添加资格预审结果通知',
    meta: {
      title: '项目进度-添加资格预审结果通知',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/checkResult/addResult')
  },
  {
    path: '/project/projectProcess/checkresult/update_result',
    name: '项目进度-修改资格预审结果通知',
    meta: {
      title: '项目进度-修改资格预审结果通知',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/checkResult/updateResult')
  },
  {
    path: '/project/projectProcess/checkresult/result_detail/:objectId',
    name: '项目进度-查看资格预审结果通知',
    meta: {
      title: '项目进度-查看资格预审结果通知',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/checkResult/resultDetail')
  }
]
