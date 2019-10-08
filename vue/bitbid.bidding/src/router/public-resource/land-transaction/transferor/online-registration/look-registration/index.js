import lookregistrationLand from '@/pages/land-transaction/transferor/online-registration/look-registration'
import registrationdetailLand from '@/pages/land-transaction/transferor/online-registration/look-registration/detail'
export default[
  {
    path: '/lt/transferor/online-registration/look-registration',
    name: 'lookregistrationLand-list',
    meta: {
      title: '查看报名情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookregistrationLand
  },
  {
    path: '/lt/transferor/online-registration/look-registration/detail/:objectId',
    name: 'lookregistrationLand-detail',
    meta: {
      title: '报名详情',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: registrationdetailLand
  }
]
