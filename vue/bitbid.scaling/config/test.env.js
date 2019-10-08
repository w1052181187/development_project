'use strict'
module.exports = {
  NODE_ENV: '"test"',
  ENV_CONFIG: '"test"',
  // BASE_API: '"http://192.168.1.17:8201/api/v1/"'
  // BASE_API: '"http://test.sdbidding.org.cn/api/v1/"'
  BASE_API: '"http://192.168.1.127:8110/api/v1/bid-scalingsystem"',
  DOWNLOAD_LINK: '"http://192.168.1.127:8110/api/v1/"',
  PDF_LINK: '"http://192.168.1.127:8110/PDFSignatureServer/index"',
  ROOM_BASE_API: '"http://192.168.1.127:8104/api/v1/"', // 这个接口用来做竞价
  WEBSOCKETURL: '"192.168.1.127:8220"', // websocket-api配置
  WEBSOCKETROOMURL: '"192.168.1.127:8204"' // websocket-api配置
}
