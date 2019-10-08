/**
 * Created by lixuqiang on 2018/7/30.
 */
import confirmPeople from './confirm-people/index'
import issueSales from './issue-sales-confirmation/index'
import subResults from './sub-results-transaction/index'
import lookResults from './look-results-transaction/index'

export default [
  ...confirmPeople,
  ...issueSales,
  ...subResults,
  ...lookResults
]
