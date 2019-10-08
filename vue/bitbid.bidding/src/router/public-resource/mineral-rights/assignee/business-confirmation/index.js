import receiveSales from './receive-sales-confirmation/index'
import lookResults from './look-results-transaction/index'

export default [
  ...receiveSales,
  ...lookResults
]
