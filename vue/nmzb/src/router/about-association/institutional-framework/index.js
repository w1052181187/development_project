import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/about-association/institutional-framework',
    name: 'institutional-framework',
    meta: {
      permissions: [1],
      title: '协会简介' + titleName,
      active: '/index/about-association/institutional-framework'
    },
    component: resolve => require(['@/pages/about-association/institutional-framework/index'], resolve)
  }
]
