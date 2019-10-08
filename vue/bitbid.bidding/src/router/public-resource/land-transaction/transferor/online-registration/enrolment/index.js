import enrolmentindexLand from '@/pages/land-transaction/transferor/online-registration/enrolment'
import situationLand from '@/pages/land-transaction/transferor/online-registration/enrolment/situation'
import enrolmentLand from '@/pages/land-transaction/transferor/online-registration/enrolment/enrolment'
import enrolmentdetailLand from '@/pages/land-transaction/transferor/online-registration/enrolment/detail'
export default[
  {
    path: '/lt/transferor/online-registration/enrolment',
    name: 'landenrolment-list',
    meta: {
      title: '报名受理',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: enrolmentindexLand
  },
  {
    path: '/lt/transferor/online-registration/enrolment/situation',
    name: 'landsituation-list',
    meta: {
      title: '报名情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: situationLand
  },
  {
    path: '/lt/transferor/online-registration/enrolment/enrolment/:objectId',
    name: 'landenrolment-update',
    meta: {
      title: '受理报名信息',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: enrolmentLand
  },
  {
    path: '/lt/transferor/online-registration/enrolment/detail/:objectId',
    name: 'landenrolment-detail',
    meta: {
      title: '查看报名信息',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: enrolmentdetailLand
  }
]
