/**
 * Created by lixuqiang on 2018/7/27.
 */
import subMinRegList from '@/pages/mineral-rights/transferor/mining-register-announcement/sub-mining-register/index'
import subMinRegAdd from '@/pages/mineral-rights/transferor/mining-register-announcement/sub-mining-register/add'
import subMinRegUpdate from '@/pages/mineral-rights/transferor/mining-register-announcement/sub-mining-register/update'
import subMinRegDetail from '@/pages/mineral-rights/transferor/mining-register-announcement/sub-mining-register/detail'
import subMinRegRelease from '@/pages/mineral-rights/transferor/mining-register-announcement/sub-mining-register/release'

export default [
  {
    path: '/mr/transferor/mining-register-announcement/sub-mining-register',
    name: 'subMiningRegister-list',
    meta: {
      title: '提交矿业权登记公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subMinRegList
  },
  {
    path: '/mr/transferor/mining-register-announcement/sub-mining-register/add',
    name: 'subMiningRegister-add',
    meta: {
      title: '添加公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subMinRegAdd
  },
  {
    path: '/mr/transferor/mining-register-announcement/sub-mining-register/update',
    name: 'subMiningRegister-update',
    meta: {
      title: '修改公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subMinRegUpdate
  },
  {
    path: '/mr/transferor/mining-register-announcement/sub-mining-register/detail/:objectId',
    name: 'subMiningRegister-detail',
    meta: {
      title: '查看公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subMinRegDetail
  },
  {
    path: '/mr/transferor/mining-register-announcement/sub-mining-register/release',
    name: 'subMiningRegister-release',
    meta: {
      title: '发布公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subMinRegRelease
  }
]
