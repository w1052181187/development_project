// 项目备案
import projectfile from './project-file/index'
// 出让公告备案
import noticefile from './notice-file/index'
// 出让文件备案
import transferdocumentsfile from './transfer-documents-file/index'
// 补充公告备案
import supplementarynoticefile from './supplementary-notice-file/index'
// 澄清/修改文件备案
import clarifydocumentsfile from './clarify-documents-file/index'
// 资格确认情况查看
import qualificationconfirmation from './qualification-confirmation/index'
// 拍卖竞价情况查看
import auctionbidding from './auction-bidding/index'
// 挂牌竞价情况查看
import Listingbidding from './Listing-bidding/index'
// 成交结果公示备案
import resultdisclosurefile from './result-disclosure-file/index'
// 合同备案
import recordthecontractfile from './record-the-contract-file/index'
// 其他方式探矿权出让公示备案
import otherwayexplorationfile from './other-way-exploration-file/index'
// 其他方式采矿权出让公示备案
import otherwayminingfile from './other-way-mining-file/index'
// 划定矿区范围公示备案
import delimitminingareafile from './delimit-mining-area-file/index'
// 矿业权登记公告备案
import miningrightregistrationfile from './mining-right-registration-file/index'

export default[
  ...projectfile,
  ...noticefile,
  ...transferdocumentsfile,
  ...supplementarynoticefile,
  ...clarifydocumentsfile,
  ...qualificationconfirmation,
  ...auctionbidding,
  ...Listingbidding,
  ...resultdisclosurefile,
  ...recordthecontractfile,
  ...otherwayexplorationfile,
  ...otherwayminingfile,
  ...delimitminingareafile,
  ...miningrightregistrationfile
]
