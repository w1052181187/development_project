/**
 * Created by lixuqiang on 2018/7/23.
 */
import list from '@/pages/mineral-rights/transferor/notice/submission-notice/index'
import selectionproject from '@/pages/mineral-rights/transferor/notice/submission-notice/selection-project'
import add from '@/pages/mineral-rights/transferor/notice/submission-notice/add'
import update from '@/pages/mineral-rights/transferor/notice/submission-notice/update'
import detail from '@/pages/mineral-rights/transferor/notice/submission-notice/detail'
import release from '@/pages/mineral-rights/transferor/notice/submission-notice/release'

export default [
  {
    path: '/mr/transferor/submission/subminssion-notice',
    name: 'subMinssionNotice-list',
    meta: {
      title: '提交出让公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: list
  },
  {
    path: '/mr/transferor/submission/subminssion-notice/selection-project',
    name: 'subMinssionNotice-selectionProject',
    meta: {
      title: '选择项目',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: selectionproject
  },
  {
    path: '/mr/transferor/submission/subminssion-notice/add',
    name: 'subMinssionNotice-add',
    meta: {
      title: '添加出让公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: add
  },
  {
    path: '/mr/transferor/submission/subminssion-notice/update',
    name: 'subMinssionNotice-update',
    meta: {
      title: '修改出让公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: update
  },
  {
    path: '/mr/transferor/submission/subminssion-notice/release',
    name: 'subMinssionNotice-release',
    meta: {
      title: '发布出让公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: release
  },
  {
    path: '/mr/transferor/submission/subminssion-notice/detail/:objectId',
    name: 'subMinssionNotice-detail/:objectId',
    meta: {
      title: '查看出让公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: detail
  }
]
