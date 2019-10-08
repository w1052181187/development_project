import onlineregistration from '@/pages/mineral-rights/assignee/online-registration/online-registration'
import choose from '@/pages/mineral-rights/assignee/online-registration/online-registration/choose-project'
import submitregistration from '@/pages/mineral-rights/assignee/online-registration/online-registration/submit'
import registrationupdate from '@/pages/mineral-rights/assignee/online-registration/online-registration/update'
import lookregistrationdetail from '@/pages/mineral-rights/assignee/online-registration/online-registration/detail'
import seereceipt from '@/pages/mineral-rights/assignee/online-registration/online-registration/see-receipt'
export default[
  {
    path: '/mr/assignee/online-registration/online-registration',
    name: 'onlineRegistration-list',
    meta: {
      title: '网上报名',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: onlineregistration
  },
  {
    path: '/mr/assignee/online-registration/online-registration/choose-project',
    name: 'choose-list',
    meta: {
      title: '选择项目',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: choose
  },
  {
    path: '/mr/assignee/online-registration/look-registration/submit-registration',
    name: 'submitRegistration',
    meta: {
      title: '提交报名信息',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: submitregistration
  },
  {
    path: '/mr/assignee/online-registration/look-registration/update/:objectId',
    name: 'registration-update/:objectId',
    meta: {
      title: '修改报名信息',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: registrationupdate
  },
  {
    path: '/mr/assignee/online-registration/look-registration/detail/:objectId',
    name: 'registration-detail/:objectId',
    meta: {
      title: '查看报名信息',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: lookregistrationdetail
  },
  {
    path: '/mr/assignee/online-registration/look-registration/receipt-detail',
    name: 'seereceipt-detail',
    meta: {
      title: '查看回执',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: seereceipt
  }
]
