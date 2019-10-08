// 合作代理管理
import contractAgency from './contract-agency'
// 收款账号
import collection from './collection'
// 交易记录
import record from './record'
// 轮换信息
import rotation from './portal/rotation-info/index'
// 入驻申请
import enterApply from './portal/enter-apply/index'
// 发票管理
import invoice from './invoice-admin/index'
// 结算代理
import settlement from './settlement/index'
// 结算运营
import operatingSettl from './operating-settl/index'
// 代理企业
import enterprise from './enterprise/index'
// 小程序轮播管理
import slideshow from './slideshow/index'
// 小程序系统通知
import systemNotice from './system-notice/index'

export default [
  {
    path: '/admin',
    name: 'left-menu',
    component: resolve => require(['@/components/common/admin/side-nav.vue'], resolve),
    children: [
      ...contractAgency,
      ...collection,
      ...record,
      ...rotation,
      ...enterApply,
      ...invoice,
      ...settlement,
      ...operatingSettl,
      ...enterprise,
      ...slideshow,
      ...systemNotice
    ]
  }
]
