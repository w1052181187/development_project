/**
 * Created by Administrator on 2019-3-7.
 * 响应招标
 */
export default [
  {
    path: '/processManage/projectProcess/bidresponse/add_bond',
    name: 'projectProcess-bidresponse-add',
    meta: {
      title: '流程管理-响应招标-添加保证金'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidResponse/addBond'], resolve)
  },
  {
    path: '/processManage/projectProcess/bidresponse/update_bond',
    name: 'projectProcess-bidresponse-update',
    meta: {
      title: '流程管理-响应招标-编辑保证金'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidResponse/updateBond'], resolve)
  },
  {
    path: '/processManage/projectProcess/bidresponse/bond_detail/:objectId',
    name: 'projectProcess-bidresponse-detail',
    meta: {
      title: '流程管理-响应招标-查看保证金'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidResponse/bondDetail'], resolve)
  },
  {
    path: '/processManage/projectProcess/bidresponse/add_cost',
    name: 'projectProcess-bidresponse-add_cost',
    meta: {
      title: '流程管理-响应招标-添加文件费用'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidResponse/addCost'], resolve)
  },
  {
    path: '/processManage/projectProcess/bidresponse/update_cost',
    name: 'projectProcess-bidresponse-update_cost',
    meta: {
      title: '流程管理-响应招标-修改文件费用'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidResponse/updateCost'], resolve)
  },
  {
    path: '/processManage/projectProcess/bidresponse/cost_detail/:objectId',
    name: 'projectProcess-bidresponse-cost_detail',
    meta: {
      title: '流程管理-响应招标-查看文件费用'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidResponse/costDetail'], resolve)
  },
  {
    path: '/processManage/projectProcess/bidresponse/add_file',
    name: 'projectProcess-bidresponse-add_file',
    meta: {
      title: '流程管理-响应招标-添加投标文件'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidResponse/addFile'], resolve)
  },
  {
    path: '/processManage/projectProcess/bidresponse/update_file',
    name: 'projectProcess-bidresponse-update_file',
    meta: {
      title: '流程管理-响应招标-修改投标文件'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidResponse/updateFile'], resolve)
  },
  {
    path: '/processManage/projectProcess/bidresponse/file_detail/:objectId',
    name: 'projectProcess-bidresponse-file_detail',
    meta: {
      title: '流程管理-响应招标-查看投标文件'
    },
    component: resolve => require(['@/pages/processManage/projectProcess/bidResponse/fileDetail'], resolve)
  }
]
