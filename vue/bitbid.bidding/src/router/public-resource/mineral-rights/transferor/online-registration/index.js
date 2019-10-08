import enrolment from '../online-registration/enrolment'
import lookregistration from '../online-registration/look-registration'
import sendregistration from '../online-registration/send-confirmation'
export default [
  ...enrolment,
  ...lookregistration,
  ...sendregistration
]
