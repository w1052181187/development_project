/**
 * Created by lixuqiang on 2018/4/19.
 */
// 档案管理
import archives from '@/components/archives/archives'
import see from '@/components/archives/see'
import newlyadd from '@/components/archives/newlyadd'
import newlyonlineadd from '@/components/archives/newlyonlineadd'
import editdocuments from '@/components/archives/editdocuments'

export default[
  // 档案管理
  {
    path: '/archives',
    name: 'archives',
    meta: {
      permission: '/archives'
    },
    component: archives
  },
  {
    path: '/archives/detail/:objectId',
    name: 'see',
    meta: {
      permission: '/archives/detail'
    },
    component: see
  },
  {
    path: '/archives/add',
    name: 'archives/add',
    meta: {
      permission: '/archives/add'
    },
    component: newlyadd
  },
  {
    path: '/archives/onlineadd',
    name: 'archives/onlineadd',
    meta: {
      permission: '/archives/onlineadd'
    },
    component: newlyonlineadd
  },
  {
    path: '/archives/update',
    name: 'archives/update',
    meta: {
      permission: '/archives/update'
    },
    component: newlyadd
  },
  {
    path: '/archives/archived-file',
    name: 'archives/archived-file',
    meta: {
      permission: '/archives/update'
    },
    component: editdocuments
  }
]
