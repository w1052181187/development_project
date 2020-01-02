// 行业资讯
export default [
  {
    path: 'news-details',
    name: '行业资讯详情',
    // component: resolve => require(['@/views/admin/workdesk/news/news-details.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/workdesk/news/news-details.vue'),
    meta: {
      title: '行业资讯详情'
    }
  },
  {
    path: 'news-list',
    name: '行业资讯列表',
    // component: resolve => require(['@/views/admin/workdesk/news/news-list.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/workdesk/news/news-list.vue'),
    meta: {
      title: '行业资讯列表'
    }
  }
]
