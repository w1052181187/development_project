/**
 * Created by Administrator on 2019-3-7.
 * 开标评标
 */
export default [
  {
    path: '/project/projectProcess/project/process/bidOpening/bidexpert/add',
    name: '项目进度-添加评标专家',
    meta: {
      title: '项目进度-添加评标专家',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/addBidexpert.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/bidexpert/update',
    name: '项目进度-修改评标专家',
    meta: {
      title: '项目进度-修改评标专家',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/updateBidexpert.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/bidexpert/detail/:objectId',
    name: '项目进度-查看评标专家',
    meta: {
      title: '项目进度-查看评标专家',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/detailBidexpert.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/docclear/add',
    name: '项目进度-添加投标文件疑义及澄清',
    meta: {
      title: '项目进度-添加投标文件疑义及澄清',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/addDocclear.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/docclear/update',
    name: '项目进度-修改投标文件疑义及澄清',
    meta: {
      title: '项目进度-修改投标文件疑义及澄清',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/updateDocclear.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/docclear/detail/:objectId',
    name: '项目进度-查看投标文件疑义及澄清',
    meta: {
      title: '项目进度-查看投标文件疑义及澄清',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/detailDocclear.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/bidrecord/add',
    name: '项目进度-添加开标记录',
    meta: {
      title: '项目进度-添加开标记录',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/addBidrecord.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/bidrecord/update',
    name: '项目进度-修改开标记录',
    meta: {
      title: '项目进度-修改开标记录',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/updateBidrecord.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/bidrecord/detail/:objectId',
    name: '项目进度-查看开标记录',
    meta: {
      title: '项目进度-查看开标记录',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/detailBidrecord.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/recordclear/add',
    name: '项目进度-添加开标记录表疑义及澄清',
    meta: {
      title: '项目进度-添加开标记录表疑义及澄清',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/addRecordclear.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/recordclear/update',
    name: '项目进度-修改开标记录表疑义及澄清',
    meta: {
      title: '项目进度-修改开标记录表疑义及澄清',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/updateRecordclear.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/recordclear/detail/:objectId',
    name: '项目进度-查看开标记录表疑义及澄清',
    meta: {
      title: '项目进度-查看开标记录表疑义及澄清',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/detailRecordclear.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/bidreport/add',
    name: '项目进度-添加评标报告',
    meta: {
      title: '项目进度-添加评标报告',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/addBidreport.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/bidreport/update',
    name: '项目进度-修改评标报告',
    meta: {
      title: '项目进度-修改评标报告',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/updateBidreport.vue')
  },
  {
    path: '/project/projectProcess/project/process/bidOpening/bidreport/detail/:objectId',
    name: '项目进度-查看评标报告',
    meta: {
      title: '项目进度-查看评标报告',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidOpening/detailBidreport.vue')
  }
]
