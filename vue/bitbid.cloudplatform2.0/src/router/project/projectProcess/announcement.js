/**
 * Created by Administrator on 2019-3-7.
 * 招标公告/资格预审公告
 */
export default [
  {
    path: '/project/projectProcess/announcement/add',
    name: '项目进度-添加公告',
    meta: {
      title: '项目进度-添加公告',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/announcement/add')
  },
  {
    path: '/project/projectProcess/announcement/update',
    name: '项目进度-修改公告',
    meta: {
      title: '项目进度-修改公告',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/announcement/update')
  },
  {
    path: '/project/projectProcess/announcement/detail/:code',
    name: '项目进度-查看公告',
    meta: {
      title: '项目进度-查看公告',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/announcement/detail')
  },
  {
    path: '/project/projectProcess/announcement/add_notice',
    name: '项目进度-添加更正公告',
    meta: {
      title: '项目进度-添加更正公告',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/announcement/addNotice')
  },
  {
    path: '/project/projectProcess/announcement/update_notice',
    name: '项目进度-修改更正公告',
    meta: {
      title: '项目进度-修改更正公告',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/announcement/updateNotice')
  },
  {
    path: '/project/projectProcess/announcement/notice_detail/:code',
    name: '项目进度-查看更正公告',
    meta: {
      title: '项目进度-查看更正公告',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/announcement/noticeDetail')
  }
]
