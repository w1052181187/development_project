export default [
  {
    path: '/websiteManage/home/imageNews',
    name: 'websiteManage-imageNews-list',
    meta: {
      title: '图片新闻'
    },
    component: resolve => require(['@/pages/websiteManage/home/imageNews/index'], resolve)
  },
  {
    path: '/websiteManage/home/imageNews/add',
    name: 'websiteManage-imageNews-add',
    meta: {
      title: '图片新闻-添加'
    },
    component: resolve => require(['@/pages/websiteManage/home/imageNews/add'], resolve)
  },
  {
    path: '/websiteManage/home/imageNews/update',
    name: 'websiteManage-imageNews-update',
    meta: {
      title: '图片新闻-修改'
    },
    component: resolve => require(['@/pages/websiteManage/home/imageNews/update'], resolve)
  },
  {
    path: '/websiteManage/home/imageNews/detail/:objectId',
    name: 'websiteManage-imageNews-detail',
    meta: {
      title: '图片新闻-查看'
    },
    component: resolve => require(['@/pages/websiteManage/home/imageNews/detail'], resolve)
  }
]
