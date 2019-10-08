import sendConfirmation from '@/pages/land-transaction/transferor/online-registration/send-confirmation'
import sendConfirmationSituation from '@/pages/land-transaction/transferor/online-registration/send-confirmation/situation'
import sendConfirmationSend from '@/pages/land-transaction/transferor/online-registration/send-confirmation/send'
import sendConfirmationDetail from '@/pages/land-transaction/transferor/online-registration/send-confirmation/detail'
export default[
  {
    path: '/lt/transferor/online-registration/send-confirmation',
    name: 'sendConfirmationLand-list',
    meta: {
      title: '发送资格确认书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: sendConfirmation
  },
  {
    path: '/lt/transferor/online-registration/send-confirmation/situation/:objectId',
    name: 'sendConfirmationLand-situation',
    meta: {
      title: '查看发送情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: sendConfirmationSituation
  },
  {
    path: '/lt/transferor/online-registration/send-confirmation/send/:objectId',
    name: 'sendConfirmationLand-send',
    meta: {
      title: '发送资格确认书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: sendConfirmationSend
  },
  {
    path: '/lt/transferor/online-registration/send-confirmation/detail/:objectId',
    name: 'sendConfirmationLand-detail',
    meta: {
      title: '查看资格确认书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: sendConfirmationDetail
  }
]
