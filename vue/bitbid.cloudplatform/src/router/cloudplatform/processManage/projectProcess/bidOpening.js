/**
 * Created by Administrator on 2019-3-7.
 * 开标评标
 */
export default [
  {
    path: '/processManage/projectProcess/project/process/bidOpening/bidexpert/add',
    name: 'projectProcess-winbidpublic-add',
    meta: {
      title: '开标评标-评标专家-添加'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/addBidexpert.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/bidexpert/update',
    name: 'projectProcess-winbidpublic-update',
    meta: {
      title: '开标评标-评标专家-修改'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/updateBidexpert.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/bidexpert/detail/:objectId',
    name: 'projectProcess-winbidpublic-detail',
    meta: {
      title: '开标评标-评标专家-查看'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/detailBidexpert.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/docclear/add',
    name: 'projectProcess-docclear-add',
    meta: {
      title: '开标评标-投标文件疑义及澄清-添加'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/addDocclear.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/docclear/update',
    name: 'projectProcess-docclear-update',
    meta: {
      title: '开标评标-投标文件疑义及澄清-修改'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/updateDocclear.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/docclear/detail/:objectId',
    name: 'projectProcess-docclear-detail',
    meta: {
      title: '开标评标-投标文件疑义及澄清-查看'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/detailDocclear.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/bidrecord/add',
    name: 'projectProcess-bidrecord-add',
    meta: {
      title: '开标评标-开标记录-添加'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/addBidrecord.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/bidrecord/update',
    name: 'projectProcess-bidrecord-update',
    meta: {
      title: '开标评标-开标记录-修改'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/updateBidrecord.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/bidrecord/detail/:objectId',
    name: 'projectProcess-bidrecord-detail',
    meta: {
      title: '开标评标-开标记录-查看'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/detailBidrecord.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/recordclear/add',
    name: 'projectProcess-recordclear-add',
    meta: {
      title: '开标评标-开标记录表疑义及澄清-添加'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/addRecordclear.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/recordclear/update',
    name: 'projectProcess-recordclear-update',
    meta: {
      title: '开标评标-开标记录表疑义及澄清-修改'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/updateRecordclear.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/recordclear/detail/:objectId',
    name: 'projectProcess-recordclear-detail',
    meta: {
      title: '开标评标-开标记录表疑义及澄清-查看'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/detailRecordclear.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/bidreport/add',
    name: 'projectProcess-bidreport-add',
    meta: {
      title: '开标评标-评标报告-添加'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/addBidreport.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/bidreport/update',
    name: 'projectProcess-bidreport-update',
    meta: {
      title: '开标评标-评标报告-修改'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/updateBidreport.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/bidOpening/bidreport/detail/:objectId',
    name: 'projectProcess-bidreport-detail',
    meta: {
      title: '开标评标-评标报告-查看'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidOpening/detailBidreport.vue'], resolve)
  }
]
