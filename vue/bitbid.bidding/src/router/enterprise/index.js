/**
 * Created by lixuqiang on 2018/5/12.
 */
/**
 * Created by lixuqiang on 2018/5/10.
 */
import subject from '@/components/enterprise/subject'
import information from '@/components/enterprise/information'
import authentication from '@/components/enterprise/authentication'
import autheninform from '@/components/enterprise/authen-inform'
import informationEdit from '@/components/enterprise/informationEdit'
export default[
  {
    path: '/subject',
    name: 'subject',
    component: subject
  },
  {
    path: '/information',
    name: 'information',
    component: information
  },
  {
    path: '/authentication',
    name: 'authentication',
    component: authentication
  },
  {
    path: '/authen-inform',
    name: 'authen-inform',
    component: autheninform
  },
  {
    path: '/informationEdit',
    name: 'informationEdit',
    component: informationEdit
  }
]
