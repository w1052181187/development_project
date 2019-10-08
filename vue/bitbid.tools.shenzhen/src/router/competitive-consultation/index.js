import consultationAgency from './consultation-agency/index'
import bidder from './bidder/index'
import tribunal from './tribunal/index'
import consultationHeadman from './consultation-headman/index'
import lookTender from './look-tender/index'
import controlIndex from './control/index'

export default [
  ...consultationAgency,
  ...bidder,
  ...tribunal,
  ...consultationHeadman,
  ...lookTender,
  ...controlIndex
]
