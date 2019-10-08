import sqindex from '@/pages/system-quantybill/index'
import process from '@/pages/system-quantybill/process'
import statement from '@/pages/system-quantybill/statement'

export default[
  {
    path: '/system-quantybill',
    name: 'systemQuantybill-index',
    meta: {
      title: '工程量清单编制系统'
    },
    component: sqindex
  },
  {
    path: '/system-quantybill/process',
    name: 'systemQuantybill-process',
    meta: {
      title: '操作流程'
    },
    component: process
  },
  {
    path: '/system-quantybill/statement',
    name: 'systemQuantybill-statement',
    meta: {
      title: '导出报表',
      isshow: true
    },
    component: statement
  }
]
