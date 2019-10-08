import project from './project/index'
import notice from './notice/index'
import transfer from './transfer-documents/index'
import registration from './online-registration/index'
import otherMiningSell from './other-mining-sell/index'
import miningRegister from './mining-register-announcement/index'
import feePayment from './fee-payment/index'
import postBidBbusiness from './post-bid-business/index'
import businessConfirmation from './business-confirmation/index'
import auction from './auction/index'
import quotation from './quotation/index'
export default[
  ...project,
  ...notice,
  ...transfer,
  ...registration,
  ...otherMiningSell,
  ...miningRegister,
  ...feePayment,
  ...postBidBbusiness,
  ...businessConfirmation,
  ...auction,
  ...quotation
]
