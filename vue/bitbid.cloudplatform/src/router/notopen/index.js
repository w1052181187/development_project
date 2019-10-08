/**
 * Created by lixuqiang on 2018/5/10.
 */
import notopen from '@/components/adminpublic/notopen'
import recenotopens from '@/components/public/notopen'

export default[
  {
    path: '/notopen',
    name: 'notopen',
    component: notopen
  },
  {
    path: '/recenotopens',
    name: 'recenotopens',
    component: recenotopens
  }
]
