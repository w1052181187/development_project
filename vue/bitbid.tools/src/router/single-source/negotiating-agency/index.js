import reviewControl from './review-control/index'
import qualificationReview from './qualification-review/index'
import reviewSummary from './review-summary/index'
import parameterSetting from './parameter-setting/index'
import bidEvaluationReport from './bid-evaluation-report/index'

export default [
  ...reviewControl,
  ...qualificationReview,
  ...reviewSummary,
  ...parameterSetting,
  ...bidEvaluationReport
]
