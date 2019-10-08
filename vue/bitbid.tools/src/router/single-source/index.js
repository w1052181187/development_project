import negotiatingAgency from './negotiating-agency/index'
import bidder from './bidder/index'
import tribunal from './tribunal/index'
import negotiationHeadman from './negotiation-headman/index'
import lookTender from './look-tender/index'
import controlIndex from './control/index'

export default [
  ...negotiatingAgency,
  ...bidder,
  ...tribunal,
  ...negotiationHeadman,
  ...lookTender,
  ...controlIndex
]
