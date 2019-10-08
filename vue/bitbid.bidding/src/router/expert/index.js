/**
 * Created by lixuqiang on 2018/4/19.
 */
// 业主管理
import expert from '@/components/expert/expert'
import addexpert from '@/components/expert/addexpert'
import projects from '@/components/expert/projects'
import sees from '@/components/expert/sees'
import edits from '@/components/expert/edits'
export default [
  // 业主管理
  {
    path: '/expert',
    name: 'expert',
    meta: {
      permission: '/experts'
    },
    component: expert
  },
  {
    path: '/expert/add',
    name: 'expert/add',
    meta: {
      permission: '/experts/add'
    },
    component: addexpert
  },
  {
    path: '/expert/projects',
    name: 'expert/projects',
    meta: {
      permission: '/experts/projects'
    },
    component: projects
  },
  {
    path: '/expert/detail/:objectId',
    name: 'sees',
    meta: {
      permission: '/experts/detail'
    },
    component: sees
  },
  {
    path: '/expert/update',
    name: 'expert/update',
    meta: {
      permission: '/experts/update'
    },
    component: edits
  }
]
