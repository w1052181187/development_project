import registration from './online-registration/index'
import assigneeauction from './auction/index'
import assigneequotation from './quotation/index'
import postBidBusiness from './post-bid-business/index'
import businessConfirmation from './business-confirmation/index'
import lookTransferProject from './look-transfer-project/index'
import transferDocuments from './transfer-documents/index'
import feePayment from './fee-payment/index'
export default[
  ...registration,
  ...assigneeauction,
  ...assigneequotation,
  ...postBidBusiness,
  ...businessConfirmation,
  ...lookTransferProject,
  ...transferDocuments,
  ...feePayment
]
