import lookClarifyList from '@/pages/land-transaction/transferor/auction-listed-file/look-clarify/index'
import lookClarifyDetail from '@/pages/land-transaction/transferor/auction-listed-file/look-clarify/detail'

export default [
  {
    path: '/lt/transferor/auction-listed-file/look-clarify',
    name: 'lookClarify-list',
    meta: {
      title: '查看出让文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookClarifyList
  },
  {
    path: '/lt/transferor/auction-listed-file/look-clarify/detail/:objectId',
    name: 'lookClarify-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookClarifyDetail
  }
]
