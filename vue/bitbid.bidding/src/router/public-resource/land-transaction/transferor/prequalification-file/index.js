import subTenderFile from './sub-tender-file/index'
import lookTenderFile from './look-tender-file/index'
import subClarifyFile from './sub-clarify-file/index'
import lookClarifyFile from './look-clarify-file/index'
import lookDissent from './look-dissent/index'
import tenderSubmit from './tender-submit/index'
export default [
  ...subTenderFile,
  ...lookTenderFile,
  ...subClarifyFile,
  ...lookClarifyFile,
  ...lookDissent,
  ...tenderSubmit
]
