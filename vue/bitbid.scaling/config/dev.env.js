'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./dev.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  ENV_CONFIG: '"dev"',
  // BASE_API: '"http://192.168.1.104:3075/api/v1/bid-scalingsystem"',
  BASE_API: '"http://192.168.1.127:8110/api/v1/bid-scalingsystem"',
  // DOWNLOAD_LINK: '"http://192.168.1.104:3075/api/v1/"',
  DOWNLOAD_LINK: '"http://192.168.1.127:8110/api/v1/"',
  // PDF_LINK: '"http://192.168.1.104:3075/PDFSignatureServer/index"',
  PDF_LINK: '"http://192.168.1.127:8110/PDFSignatureServer/index"',
  ROOM_BASE_API: '"http://192.168.1.127:8104/api/v1/"', // 这个接口用来做竞价
  // ROOM_BASE_API: '"http://192.168.1.104:8104/api/v1/"', // 这个接口用来做竞价
  WEBSOCKETURL: '"192.168.1.127:8220"',
  // WEBSOCKETURL: '"192.168.1.104:3175"',
  WEBSOCKETROOMURL: '"192.168.1.127:8204"'
  // WEBSOCKETROOMURL: '"192.168.1.104:8204"'
})
