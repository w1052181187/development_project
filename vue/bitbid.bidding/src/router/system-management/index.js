import systermindex from '@/pages/system-management/index'
// 矿权交易
import mineralrights from './mineral-rights'
// 政府采购
import governmentprocurement from './government-procurement'
// 土地交易
import landTransaction from './land-transaction'
export default[
  {
    path: '/system-management',
    name: 'systemManagementIndex',
    meta: {
      title: '首页',
      layout: 'SystemManagement'
    },
    component: systermindex
  },
  ...mineralrights,
  ...governmentprocurement,
  ...landTransaction
]
