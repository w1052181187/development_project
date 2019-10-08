/**
 * Created by lixuqiang on 2018/8/27.
 */
import arcUnderstand from '@/components/understand/arc-understand'
import ownUnderstand from '@/components/understand/own-understand'
import expUnderstand from '@/components/understand/exp-understand'
import resUnderstand from '@/components/understand/res-understand'
import incUnderstand from '@/components/understand/inc-understand'

export default[
  {
    path: '/arc-understand',
    name: 'arc-understand',
    meta: {
      permission: 'index'
    },
    component: arcUnderstand
  },
  {
    path: '/own-understand',
    name: 'own-understand',
    meta: {
      permission: 'index'
    },
    component: ownUnderstand
  },
  {
    path: '/exp-understand',
    name: 'exp-understand',
    meta: {
      permission: 'index'
    },
    component: expUnderstand
  },
  {
    path: '/res-understand',
    name: 'res-understand',
    meta: {
      permission: 'index'
    },
    component: resUnderstand
  },
  {
    path: '/inc-understand',
    name: 'inc-understand',
    meta: {
      permission: 'index'
    },
    component: incUnderstand
  }
]
