// 项目备案
import gpprojectfile from './project-file'
import procurementprojectfile from './procurement-project-file'
import documentsfile from './documents-file'
import procurementnoticefile from './procurement-notice-file'
import correctnoticefile from './correct-notice-file'
import procurementinvitefile from './procurement-invite-file'
import predocuments from './pre-qualification-documents-file'
import preclarificationdocuments from './pre-qualification-clarification-documents-file'
import preresultsfile from './pre-qualification-results-file'
import prodocumentsfile from './procurement-documents-file'
import proclarificationfile from './procurement-clarification-documents-file'
import prepurchasesfile from './pre-qualification-purchases-file'
// import systemusagefeefile from './system-usage-fee-file'
import propurchasesfile from './procurement-documents-purchases-file'
import bidbondpaymentfile from './bid-bond-payment-file'
import bidopeningrecordfile from './bid-opening-record-file'
import bidreportwinningfile from './bidreport-winning-file'
import letterofacceptancefile from './letter-of-acceptance-file'
import bidwinningnoticefile from './bid-winning-notice-file'
import buyingsituationfile from './buying-situation-file'
import contractfile from './contract-file'
import bidresultfile from './bid-result-file'

export default[
  ...gpprojectfile,
  ...procurementprojectfile,
  ...documentsfile,
  ...procurementnoticefile,
  ...correctnoticefile,
  ...procurementinvitefile,
  ...predocuments,
  ...preclarificationdocuments,
  ...preresultsfile,
  ...prodocumentsfile,
  ...proclarificationfile,
  ...prepurchasesfile,
  // ...systemusagefeefile,
  ...propurchasesfile,
  ...bidbondpaymentfile,
  ...bidopeningrecordfile,
  ...bidreportwinningfile,
  ...letterofacceptancefile,
  ...bidwinningnoticefile,
  ...buyingsituationfile,
  ...contractfile,
  ...bidresultfile
]
