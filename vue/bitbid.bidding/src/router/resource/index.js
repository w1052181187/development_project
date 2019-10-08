import resourceSet from '@/components/resource/resource-set'
import ownerSet from '@/components/resource/owner-set'
export default [
  {
    path: '/owner-set',
    name: 'owner-set',
    meta: {
      permission: '/owner-set'
    },
    component: ownerSet
  },
  {
    path: '/owner-set/resource-set',
    name: 'owner-set/resource-set',
    meta: {
      permission: '/owner-set/detail'
    },
    component: resourceSet
  }
]
