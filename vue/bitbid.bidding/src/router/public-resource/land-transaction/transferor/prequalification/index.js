import preClarify from './pre-clarify'
import qualReport from './qual-report'
import sendAdvice from './send-advice'
import lookAdvice from './look-advice'
import fileDissent from './file-dissent'
export default [
  {
    path: '/lt/transferor/prequalification/prepare',
    name: 'prequalification-prepare',
    meta: {
      title: '资格预审准备',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/index'], resolve)
  },
  ...preClarify,
  ...qualReport,
  ...sendAdvice,
  ...lookAdvice,
  ...fileDissent
]
