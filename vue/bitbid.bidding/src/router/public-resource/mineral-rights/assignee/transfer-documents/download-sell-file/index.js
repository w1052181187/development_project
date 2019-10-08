import downloadSellFile from '@/pages/mineral-rights/assignee/transfer-documents/download-sell-file/index'
import downloadsell from '@/pages/mineral-rights/assignee/transfer-documents/download-sell-file/download'

export default[
  {
    path: '/mr/assignee/transfer-documents/download-sell-file',
    name: 'downloadSellFile-list',
    meta: {
      title: '下载出让文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: downloadSellFile
  },
  {
    path: '/mr/assignee/transfer-documents/download-sell-file/download',
    name: 'downloadSell-download',
    meta: {
      title: '下载',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: downloadsell
  }
]
