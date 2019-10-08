/**
 * Created by lixuqiang on 2018/5/10.
 */
import openservice from '@/components/openservice/openservice'
import renewal from '@/components/public/renewal'
export default[
  {
    path: '/openservice',
    name: 'openservice',
    meta: {
      permission: 'admin'
    },
    component: openservice
  },
  {
    path: '/renewal',
    name: 'renewal',
    meta: {
      permission: 'admin'
    },
    component: renewal
  }
]
