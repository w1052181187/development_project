import sendconfirmation from '@/pages/mineral-rights/transferor/online-registration/send-confirmation'
import checksend from '@/pages/mineral-rights/transferor/online-registration/send-confirmation/situation'
import send from '@/pages/mineral-rights/transferor/online-registration/send-confirmation/send'
import senddetail from '@/pages/mineral-rights/transferor/online-registration/send-confirmation/detail'
export default[
  {
    path: '/mr/transferor/online-registration/send-confirmation',
    name: 'sendConfirmation-list',
    meta: {
      title: '发送资格确认书',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: sendconfirmation
  },
  {
    path: '/mr/transferor/online-registration/send-confirmation/situation/:objectId',
    name: 'checkSend-list/:objectId',
    meta: {
      title: '查看发送情况',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: checksend
  },
  {
    path: '/mr/transferor/online-registration/send-confirmation/send',
    name: 'send-update',
    meta: {
      title: '发送资格确认书',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: send
  },
  {
    path: '/mr/transferor/online-registration/send-confirmation/senddetail',
    name: 'send-detail',
    meta: {
      title: '查看资格确认书',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: senddetail
  }
]
