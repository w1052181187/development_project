import tenderFile from './tender-file/index'
import openingRecorder from './opening-recorder/index'
import lookRecorder from './look-recorder/index'
import bidReport from './bid-report/index'
import lookReport from './look-report/index'
export default [
  {
    path: '/lt/transferor/open-criticize-mark/bid-opening',
    name: 'openCriticize-list',
    meta: {
      title: '开标',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/index'], resolve)
  },
  ...tenderFile,
  ...openingRecorder,
  ...lookRecorder,
  ...bidReport,
  ...lookReport
]
