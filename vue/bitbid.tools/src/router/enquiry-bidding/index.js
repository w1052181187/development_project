import evaBidCommand from './evaluation-bid-command/index'
import evaBidPrepare from './evaluation-bid-prepare/index'
import evaBidSummary from './evaluation-bid-summary/index'
import inquirySummaryEnquiry from './inquiry-summary/index'
import review from './review/index'
import controlIndex from './control/index'

export default [
  ...evaBidCommand,
  ...evaBidPrepare,
  ...evaBidSummary,
  ...review,
  ...inquirySummaryEnquiry,
  ...controlIndex
]
