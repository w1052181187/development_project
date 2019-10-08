export default [
  {
    path: '/websiteManage/notice',
    name: 'websiteManage-notice-list',
    meta: {
      title: '企业网站-信息公告'
    },
    component: resolve => require(['@/pages/websiteManage/notice/index'], resolve)
  }
]
