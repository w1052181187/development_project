import project from './project/index'
import remiseInvite from './remise-invite/index'
import transferAnnoun from './transfer-announ/index'
import suppAnnoun from './supp-announ/index'
import qualification from './qualification/index'
import auctionListed from './auction-listed/index'
import auction from './auction/index'
import listed from './listed/index'
import prequalificationFile from './prequalification-file/index'
import clarify from './clarify/index'
import preliminaryResults from './preliminary-results/index'
import bidDocu from './bid-docu/index'
import tenderClarify from './tender-clarify/index'
import openingRecord from './opening-record/index'
import evaluationReport from './evaluation-report/index'
import agreement from './agreement/index'
import makeResult from './make-result/index'
import contract from './contract/index'

export default[
  ...project,
  ...remiseInvite,
  ...transferAnnoun,
  ...suppAnnoun,
  ...qualification,
  ...auctionListed,
  ...auction,
  ...listed,
  ...prequalificationFile,
  ...clarify,
  ...preliminaryResults,
  ...bidDocu,
  ...tenderClarify,
  ...openingRecord,
  ...evaluationReport,
  ...agreement,
  ...makeResult,
  ...contract
]
