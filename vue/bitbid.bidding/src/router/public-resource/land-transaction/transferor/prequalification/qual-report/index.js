export default [
  {
    path: '/lt/transferor/prequalification/qual-report/index',
    name: 'qualReportPre-list',
    meta: {
      title: '提交资格审查报告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/qual-report/index'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/qual-report/review-result',
    name: 'qualReportPre-result',
    meta: {
      title: '设置审查报告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/qual-report/review-result'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/qual-report/submit-report',
    name: 'qualReportPre-submit',
    meta: {
      title: '提交报告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/qual-report/submit-report'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/qual-report/detail/:objectId',
    name: 'qualReportPre-look',
    meta: {
      title: '查看报告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/qual-report/look-report'], resolve)
  }
]
