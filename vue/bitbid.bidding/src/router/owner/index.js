/**
 * Created by lixuqiang on 2018/4/19.
 */
// 业主管理
import owner from '@/components/owner/owner'
import addarchives from '@/components/owner/addarchives'
import project from '@/components/owner/project'
import seeowner from '@/components/owner/seeowner'
import edit from '@/components/owner/edit'

export default [
  // 业主管理
  {
    path: '/owner',
    name: 'owner',
    meta: {
      permission: '/owners'
    },
    component: owner
  },
  {
    path: '/owner/add',
    name: 'owner/add',
    meta: {
      permission: '/owners/add'
    },
    component: addarchives
  },
  {
    path: '/owner/project',
    name: 'owner/project',
    meta: {
      permission: '/owners/projects'
    },
    component: project
  },
  {
    path: '/owner/detail/:objectId',
    name: 'seeowner',
    meta: {
      permission: '/owners/detail'
    },
    component: seeowner
  },
  {
    path: '/owner/update',
    name: 'owner/update',
    meta: {
      permission: '/owners/update'
    },
    component: edit
  }
]
