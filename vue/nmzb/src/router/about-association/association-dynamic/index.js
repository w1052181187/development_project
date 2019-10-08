import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/about-association/association-dynamic',
    name: 'association-dynamic',
    meta: {
      permissions: [1],
      title: '协会简介' + titleName,
      active: '/index/about-association/association-dynamic'
    },
    component: resolve => require(['@/pages/about-association/association-dynamic/index'], resolve)
  }
]
