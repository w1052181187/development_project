export default [
  {
    path: '/websiteManage/home/enterpriseImage',
    name: 'websiteManage-enterpriseImage-list',
    meta: {
      title: '企业图片'
    },
    component: resolve => require(['@/pages/websiteManage/home/enterpriseImage/index'], resolve)
  },
  {
    path: '/websiteManage/home/enterpriseImage/add',
    name: 'websiteManage-enterpriseImage-add',
    meta: {
      title: '企业图片-添加'
    },
    component: resolve => require(['@/pages/websiteManage/home/enterpriseImage/add'], resolve)
  },
  {
    path: '/websiteManage/home/enterpriseImage/update',
    name: 'websiteManage-enterpriseImage-update',
    meta: {
      title: '企业图片-修改'
    },
    component: resolve => require(['@/pages/websiteManage/home/enterpriseImage/update'], resolve)
  }
]
