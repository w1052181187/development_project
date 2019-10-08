/**
 * Created by lixuqiang on 2018/5/10.
 */
import admin from '@/components/admin/index'

export default[
  {
    path: '/admin',
    name: 'admin',
    meta: {
      permission: 'admin'
    },
    component: admin
  }
]
