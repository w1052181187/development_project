import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/about-association/association-profile',
    name: 'association-profile',
    meta: {
      permissions: [1],
      title: '协会简介' + titleName,
      active: '/index/about-association/association-profile'
    },
    component: resolve => require(['@/pages/about-association/association-profile/index'], resolve)
  }
]
