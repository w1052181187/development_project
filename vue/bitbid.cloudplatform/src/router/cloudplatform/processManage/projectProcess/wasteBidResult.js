/**
 * Created by Administrator on 2019-3-7.
 * 废标结果
 */
export default [
  {
    path: '/processManage/projectProcess/project/process/wasteBidResult/add',
    name: 'projectProcess-wasteBidResult-add',
    meta: {
      title: '定标结果-废标结果公告-添加'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/wasteBidResult/add.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/wasteBidResult/update',
    name: 'projectProcess-wasteBidResult-update',
    meta: {
      title: '定标结果-废标结果公告-修改'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/wasteBidResult/update.vue'], resolve)
  },
  {
    path: '/processManage/projectProcess/project/process/wasteBidResult/detail/:code',
    name: 'projectProcess-wasteBidResult-detail',
    meta: {
      title: '定标结果-废标结果公告-查看'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/wasteBidResult/detail.vue'], resolve)
  }
]
