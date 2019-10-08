import downloadClarifyFile from '@/pages/mineral-rights/assignee/transfer-documents/download-clarify-file/index'
import downloadClarify from '@/pages/mineral-rights/assignee/transfer-documents/download-clarify-file/download'

export default[
  {
    path: '/mr/assignee/transfer-documents/download-clarify-file',
    name: 'downloadClarifyFile-list',
    meta: {
      title: '下载澄清/修改文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: downloadClarifyFile
  },
  {
    path: '/mr/assignee/transfer-documents/download-clarify-file/download',
    name: 'downloadClarify-download',
    meta: {
      title: '下载',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: downloadClarify
  }
]
