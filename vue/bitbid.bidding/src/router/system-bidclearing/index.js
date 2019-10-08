import bcindex from '@/pages/system-bidclearing/index.vue'
import bcprocess from '@/pages/system-bidclearing/process/index.vue'
import result from '@/pages/system-bidclearing/result/index.vue'
import compliance from '@/pages/system-bidclearing/result/complianceCheck.vue'
import arithmetic from '@/pages/system-bidclearing/result/arithmeticCheck.vue'
import reasonableness from '@/pages/system-bidclearing/result/reasonablenessCheck.vue'

export default[
  {
    path: '/system-bidclearing',
    name: 'bidclearing-index',
    meta: {
      title: '清标工具'
    },
    component: bcindex
  },
  {
    path: '/system-bidclearing/process',
    name: 'bidclearing-process',
    meta: {
      title: '清标准备'
    },
    component: bcprocess
  },
  {
    path: '/system-bidclearing/process/:objectId',
    name: 'bidclearing-process',
    meta: {
      title: '清标准备'
    },
    component: bcprocess
  },
  {
    path: '/system-bidclearing/result',
    name: 'bidclearing-result',
    meta: {
      title: '清标结果',
      isshow: true
    },
    component: result
  },
  {
    path: '/system-bidclearing/result/complianceCheck',
    name: 'bidclearing-result-complianceCheck',
    meta: {
      title: '清标结果-符合性检查',
      isshow: true
    },
    component: compliance
  },
  {
    path: '/system-bidclearing/result/arithmeticCheck',
    name: 'bidclearing-result-arithmeticCheck',
    meta: {
      title: '清标结果-算术性检查',
      isshow: true
    },
    component: arithmetic
  },
  {
    path: '/system-bidclearing/result/reasonablenessCheck',
    name: 'bidclearing-result-reasonablenessCheck',
    meta: {
      title: '清标结果-合理性检查',
      isshow: true
    },
    component: reasonableness
  }
]
