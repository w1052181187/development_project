/**
 * Created by lixuqiang on 2018/7/23.
 */
import list from '@/pages/mineral-rights/transferor/notice/supplement-notice/index'
import selectionproject from '@/pages/mineral-rights/transferor/notice/supplement-notice/selection-project'
import add from '@/pages/mineral-rights/transferor/notice/supplement-notice/add'
import update from '@/pages/mineral-rights/transferor/notice/supplement-notice/update'
import detail from '@/pages/mineral-rights/transferor/notice/supplement-notice/detail'
import release from '@/pages/mineral-rights/transferor/notice/supplement-notice/release'
export default [
  {
    path: '/mr/transferor/submission/supplement-notice',
    name: 'supplementNotice-list',
    meta: {
      title: '提交补充公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: list
  },
  {
    path: '/mr/transferor/submission/supplement-notice/selection-project',
    name: 'supplementNotice-selectionProject',
    meta: {
      title: '选择项目',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: selectionproject
  },
  {
    path: '/mr/transferor/submission/supplement-notice/add',
    name: 'supplementNotice-add',
    meta: {
      title: '添加补充公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: add
  },
  {
    path: '/mr/transferor/submission/supplement-notice/update',
    name: 'supplementNotice-update',
    meta: {
      title: '修改补充公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: update
  },
  {
    path: '/mr/transferor/submission/supplement-notice/release',
    name: 'supplementNotice-release',
    meta: {
      title: '发布补充公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: release
  },
  {
    path: '/mr/transferor/submission/supplement-notice/detail/:objectId',
    name: 'supplementNotice-detail/:objectId',
    meta: {
      title: '查看补充公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: detail
  }
]
