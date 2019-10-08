/**
 * Created by lixuqiang on 2018/5/10.
 */
import openservice from '@/components/openservice/openservice'
export default[
  {
    path: '/openservice',
    name: 'openservice',
    meta: {
      permission: 'admin'
    },
    component: openservice
  }
]
