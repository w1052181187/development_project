export default [
  {
    path: '/websiteManage/information',
    name: 'websiteManage-information-list',
    meta: {
      title: '企业网站-综合信息'
    },
    component: resolve => require(['@/pages/websiteManage/information/index'], resolve)
  }
]
