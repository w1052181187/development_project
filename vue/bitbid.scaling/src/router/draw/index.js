export default [
  {
    path: '/admin/draw-ready/:type',
    name: 'draw-ready',
    meta: {
      title: '抽签准备'
    },
    component: resolve => require(['@/views/admin/draw/index.vue'], resolve)
  },
  {
    path: '/admin/draw-room',
    name: 'draw-room',
    meta: {
      title: '抽签室'
    },
    component: resolve => require(['@/views/admin/draw/draw-room.vue'], resolve)
  },
  {
    path: '/admin/vote-room',
    name: 'vote-room',
    meta: {
      title: '投票室'
    },
    component: resolve => require(['@/views/admin/draw/vote-room.vue'], resolve)
  },
  {
    path: '/admin/accu-vote-room',
    name: 'accu-vote-room',
    meta: {
      title: '累积-投票室'
    },
    component: resolve => require(['@/views/admin/draw/accu-vote-room'], resolve)
  },
  {
    path: '/admin/report-cand',
    name: 'report-cand',
    meta: {
      title: '评标报告及定标候选人'
    },
    component: resolve => require(['@/views/admin/draw/report-cand.vue'], resolve)
  },
  {
    path: '/admin/leader-sum',
    name: 'leader-sum',
    meta: {
      title: '组长汇总'
    },
    component: resolve => require(['@/views/admin/draw/leader-sum.vue'], resolve)
  },
  {
    path: '/admin/accu-leader-sum',
    name: 'accu-leader-sum',
    meta: {
      title: '累积-组长汇总'
    },
    component: resolve => require(['@/views/admin/draw/accu-leader-sum.vue'], resolve)
  }
]
