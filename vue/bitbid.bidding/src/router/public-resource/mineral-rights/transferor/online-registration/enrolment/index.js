import enrolmentindex from '@/pages/mineral-rights/transferor/online-registration/enrolment'
import situation from '@/pages/mineral-rights/transferor/online-registration/enrolment/situation'
import enrolment from '@/pages/mineral-rights/transferor/online-registration/enrolment/enrolment'
import enrolmentdetail from '@/pages/mineral-rights/transferor/online-registration/enrolment/detail'
// import add from '@/pages/transferor/project/registration-project/add'
export default[
  {
    path: '/mr/transferor/online-registration/enrolment',
    name: 'enrolment-list',
    meta: {
      title: '报名受理',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: enrolmentindex
  },
  {
    path: '/mr/transferor/online-registration/enrolment/situation',
    name: 'situation-list',
    meta: {
      title: '报名情况',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: situation
  },
  {
    path: '/mr/transferor/online-registration/enrolment/enrolment/:objectId',
    name: 'enrolment-update/:objectId',
    meta: {
      title: '受理报名信息',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: enrolment
  },
  {
    path: '/mr/transferor/online-registration/enrolment/detail/:objectId',
    name: 'enrolment-detail/:objectId',
    meta: {
      title: '查看报名信息',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: enrolmentdetail
  }
]
