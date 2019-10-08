import resourceSet from '@/components/resource/resource-set'
import ownerSet from '@/components/resource/owner-set'
export default [
  {
    path: '/owner-set',
    component: ownerSet
  },
  {
    path: '/owner-set/:name',
    component: resourceSet
  }
]
