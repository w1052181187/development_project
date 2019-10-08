import lookAucListedList from '@/pages/land-transaction/transferor/auction-listed-file/look-auc-listed/index'
import lookAucListedDetail from '@/pages/land-transaction/transferor/auction-listed-file/look-auc-listed/detail'

export default [
  {
    path: '/lt/transferor/auction-listed-file/look-auc-listed',
    name: 'lookAucListed-list',
    meta: {
      title: '查看出让文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookAucListedList
  },
  {
    path: '/lt/transferor/auction-listed-file/look-auc-listed/detail/:objectId',
    name: 'lookAucListed-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookAucListedDetail
  }
]
