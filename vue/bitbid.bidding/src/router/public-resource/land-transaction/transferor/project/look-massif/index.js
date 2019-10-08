import patchRregisterLook from '@/pages/land-transaction/transferor/project/look-massif/index'
import patchDetailRregisterLook from '@/pages/land-transaction/transferor/project/look-massif/detail'
export default[
  {
    path: '/lt/transferor/project/look-massif',
    name: 'lookMassif-list',
    meta: {
      title: '查看地块',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: patchRregisterLook
  },
  {
    path: '/lt/transferor/project/look-massif/detail/:objectId',
    name: 'lookMassif-detail',
    meta: {
      title: '查看地块',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: patchDetailRregisterLook
  }
]
