/**
 * Created by lixuqiang on 2018/7/27.
 */
import lookMinReg from '@/pages/mineral-rights/transferor/mining-register-announcement/look-mining-register/index'

export default [
  {
    path: '/mr/transferor/mining-register-announcement/look-mining-register',
    name: 'lookMiningRegister-list',
    meta: {
      title: '查看矿业权登记公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: lookMinReg
  }
]
