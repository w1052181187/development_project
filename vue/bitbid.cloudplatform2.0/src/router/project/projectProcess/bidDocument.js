/**
 * Created by Administrator on 2019-3-7.
 * 招标文件
 */
export default [
  {
    path: '/project/projectProcess/biddocument/add',
    name: '项目进度-添加招标文件',
    meta: {
      title: '项目进度-添加招标文件',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidDocument/add')
  },
  {
    path: '/project/projectProcess/biddocument/update',
    name: '项目进度-修改招标文件',
    meta: {
      title: '项目进度-修改招标文件',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidDocument/update')
  },
  {
    path: '/project/projectProcess/biddocument/detail/:objectId',
    name: '项目进度-查看招标文件',
    meta: {
      title: '项目进度-查看招标文件',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/bidDocument/detail')
  }
]
