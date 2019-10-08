/**
 * Created by Administrator on 2019-3-7.
 * 资格预审文件
 */
export default [
  {
    path: '/project/projectProcess/prequalification_file/add',
    name: '项目进度-添加资格预审文件',
    meta: {
      title: '项目进度-添加资格预审文件',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationFile/add')
  },
  {
    path: '/project/projectProcess/prequalification_file/update',
    name: '项目进度-修改资格预审文件',
    meta: {
      title: '项目进度-修改资格预审文件',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationFile/update')
  },
  {
    path: '/project/projectProcess/prequalification_file/detail/:objectId',
    name: '项目进度-查看资格预审文件',
    meta: {
      title: '项目进度-查看资格预审文件',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/prequalificationFile/detail')
  }
]
