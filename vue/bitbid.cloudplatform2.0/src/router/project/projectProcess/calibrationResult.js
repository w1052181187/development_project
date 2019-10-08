/**
 * Created by Administrator on 2019-3-7.
 * 定标结果
 */
export default [
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidpublic/add',
    name: '项目进度-添加中标候选人公示',
    meta: {
      title: '项目进度-添加中标候选人公示',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/addWinbidpublic.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidpublic/update',
    name: '项目进度-修改中标候选人公示',
    meta: {
      title: '项目进度-修改中标候选人公示',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/updateWinbidpublic.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidpublic/detail/:code',
    name: '项目进度-查看中标候选人公示',
    meta: {
      title: '项目进度-查看中标候选人公示',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/detailWinbidpublic.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidclear/add',
    name: '项目进度-添加中标候选人公示疑义及澄清',
    meta: {
      title: '项目进度-添加中标候选人公示疑义及澄清',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/addWinbidclear.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidclear/update',
    name: '项目进度-修改中标候选人公示疑义及澄清',
    meta: {
      title: '项目进度-修改中标候选人公示疑义及澄清',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/updateWinbidclear.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidclear/detail/:objectId',
    name: '项目进度-查看中标候选人公示疑义及澄清',
    meta: {
      title: '项目进度-查看中标候选人公示疑义及澄清',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/detailWinbidclear.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidnotice/add',
    name: '项目进度-添加中标结果公告',
    meta: {
      title: '项目进度-添加中标结果公告',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/addWinbidnotice.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidnotice/update',
    name: '项目进度-修改中标结果公告',
    meta: {
      title: '项目进度-修改中标结果公告',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/updateWinbidnotice.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidnotice/detail/:code',
    name: '项目进度-查看中标结果公告',
    meta: {
      title: '项目进度-查看中标结果公告',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/detailWinbidnotice.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidinform/add',
    name: '项目进度-添加中标通知书',
    meta: {
      title: '项目进度-添加中标通知书',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/addWinbidinform.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidinform/update',
    name: '项目进度-修改中标通知书',
    meta: {
      title: '项目进度-修改中标通知书',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/updateWinbidinform.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/winbidinform/detail/:objectId',
    name: '项目进度-查看中标通知书',
    meta: {
      title: '项目进度-查看中标通知书',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/detailWinbidinform.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/tenderinform/add',
    name: '项目进度-添加招标结果通知书',
    meta: {
      title: '项目进度-添加招标结果通知书',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/addTenderinform.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/tenderinform/update',
    name: '项目进度-修改招标结果通知书',
    meta: {
      title: '项目进度-修改招标结果通知书',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/updateTenderinform.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/tenderinform/detail/:objectId',
    name: '项目进度-查看招标结果通知书',
    meta: {
      title: '项目进度-查看招标结果通知书',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/detailTenderinform.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/returnbond/add',
    name: '项目进度-添加退还保证金',
    meta: {
      title: '项目进度-添加退还保证金',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/addReturnBond.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/returnbond/update',
    name: '项目进度-退还保证金',
    meta: {
      title: '项目进度-退还保证金',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/updateReturnBond.vue')
  },
  {
    path: '/project/projectProcess/project/process/calibrationResult/returnbond/detail/:objectId',
    name: '项目进度-查看保证金退还记录',
    meta: {
      title: '项目进度-查看保证金退还记录',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/calibrationResult/detailReturnBond.vue')
  }
]
