import confirmPeopleList from '@/pages/mineral-rights/transferor/business-confirmation/confirm-people/index'
import confirmPeopleConfirm from '@/pages/mineral-rights/transferor/business-confirmation/confirm-people/confirm-people'
import confirmPeopleDetail from '@/pages/mineral-rights/transferor/business-confirmation/confirm-people/detail'

export default [
  {
    path: '/mr/transferor/business-confirmation/confirm-people',
    name: 'confirmPeople-list',
    meta: {
      title: '确认竞得人',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: confirmPeopleList
  },
  {
    path: '/mr/transferor/business-confirmation/confirm-people/confirm/:objectId',
    name: 'confirmPeople-confirm/:objectId',
    meta: {
      title: '确认竞得人',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: confirmPeopleConfirm
  },
  {
    path: '/mr/transferor/business-confirmation/confirm-people/detail/:objectId',
    name: 'confirmPeople-detail',
    meta: {
      title: '查看竞得人',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: confirmPeopleDetail
  }
]
