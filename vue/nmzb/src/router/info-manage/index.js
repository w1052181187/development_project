// 招标声明
import bidstate from './bidstate'
// 违规公示
import violation from './violation'
// 下载中心
import download from './download'
// 行业监督
import industrySupervision from './industry-supervision'
import serviceGuide from './service-guide'
export default[
  ...bidstate,
  ...violation,
  ...download,
  ...industrySupervision,
  ...serviceGuide
]
