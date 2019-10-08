import homeimage from './homeImage.js'
import imagenews from './imagenews.js'
import admanage from './admanage.js'
import enterpriseimage from './enterpriseimage.js'
import cooperator from './cooperator.js'
export default [
  {
    path: '/websiteManage/home',
    name: '网站首页',
    meta: {
      title: '网站首页',
      active: '/websiteManage/home'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/home/index')
  },
  ...homeimage,
  ...imagenews,
  ...admanage,
  ...enterpriseimage,
  ...cooperator
]
