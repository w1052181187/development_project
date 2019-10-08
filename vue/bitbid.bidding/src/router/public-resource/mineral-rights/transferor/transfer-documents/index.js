/**
 * Created by lixuqiang on 2018/7/26.
 */
import subtransfer from './sub-transfer-documents/index'
import looktransfer from './look-transfer-documents/index'
import subclarify from './sub-clarify-documents/index'
import lookclarify from './look-clarify-documents/index'

export default [
  ...subtransfer,
  ...looktransfer,
  ...subclarify,
  ...lookclarify
]
