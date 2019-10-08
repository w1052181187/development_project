import invitefileindex from '@/pages/system-management/government-procurement/procurement-invite-file/index'
import invitefileDetailOrCheck from '@/pages/system-management/government-procurement/procurement-invite-file/detail-or-check'
import checkconfirmlist from '@/pages/system-management/government-procurement/procurement-invite-file/check-confirm-list'
import supconfirmationlist from '@/pages/system-management/government-procurement/procurement-invite-file/supplier-confirmation-list'
import supconfirmationdetail from '@/pages/system-management/government-procurement/procurement-invite-file/supplier-confirmation-detail'

export default[
  {
    path: '/system-management/government-procurement/procurement-invite-file',
    name: 'inviteFile-list',
    meta: {
      title: '采购邀请备案',
      layout: 'SystemManagement'
    },
    component: invitefileindex
  },
  {
    path: '/system-management/government-procurement/procurement-invite-file/detail-or-check/:type/:objectId',
    name: 'inviteFile-check',
    meta: {
      title: '采购邀请备案',
      layout: 'SystemManagement'
    },
    component: invitefileDetailOrCheck
  },
  {
    path: '/system-management/government-procurement/procurement-invite-file/check-confirm/:objectId',
    name: 'check-confirm-list',
    meta: {
      title: '查看确认情况',
      layout: 'SystemManagement'
    },
    component: checkconfirmlist
  },
  {
    path: '/system-management/government-procurement/procurement-invite-file/check-confirm/supplier-confirmation/:objectId',
    name: 'supconfirmation-list',
    meta: {
      title: '供应商确认情况',
      layout: 'SystemManagement'
    },
    component: supconfirmationlist
  },
  {
    path: '/system-management/government-procurement/procurement-invite-file/supplier-confirmation/detail/:objectId',
    name: 'supconfirmation-detail',
    meta: {
      title: '供应商确认情况详情',
      layout: 'SystemManagement'
    },
    component: supconfirmationdetail
  }

]
