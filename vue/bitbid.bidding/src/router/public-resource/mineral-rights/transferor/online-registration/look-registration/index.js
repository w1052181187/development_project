import lookregistration from '@/pages/mineral-rights/transferor/online-registration/look-registration'
import registrationdetail from '@/pages/mineral-rights/transferor/online-registration/look-registration/detail'
export default[
  {
    path: '/mr/transferor/online-registration/look-registration',
    name: 'lookregistration-list',
    meta: {
      title: '查看报名情况',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: lookregistration
  },
  {
    path: '/mr/transferor/online-registration/look-registration/detail/:objectId',
    name: 'lookregistration-detail/:objectId',
    meta: {
      title: '报名详情',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: registrationdetail
  }
]
