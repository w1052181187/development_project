// 招标过程备案
import processrecord from './processrecord'
// 招标结果查询
import resultquery from './resultquery'
// 招标变更查询
import changequery from './changequery'
// 招标信息确认
import infoconfirm from './infoconfirm'
export default [
  ...processrecord,
  ...resultquery,
  ...changequery,
  ...infoconfirm
]
