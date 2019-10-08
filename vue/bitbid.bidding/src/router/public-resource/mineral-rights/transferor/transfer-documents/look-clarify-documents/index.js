/**
 * Created by lixuqiang on 2018/7/26.
 */
/**
 * Created by lixuqiang on 2018/7/26.
 */
import list from '@/pages/mineral-rights/transferor/transfer-documents/look-clarify-documents/index'

export default[
  {
    path: '/mr/transferor/transfer-documents/look-clarify-documents',
    name: 'lookClarifyDocuments-list',
    meta: {
      title: '查看澄清/修改文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: list
  }
]
