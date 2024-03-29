'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./dev.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  ENV_CONFIG: '"dev"',
  // BASE_API: '"http://localhost:8081/api/v1/"'
  // BASE_API: '"https://console.sdbidding.org.cn/service/api/v1/"'
  // BASE_API: '"https://msop.bibenet.com/api/v1/"',
  // BASE_API: '"http://192.168.1.55:8081/api/v1/"', // 王淙
  BASE_API: '"http://192.168.1.80:8100/api/v1/"',
  // BASE_API: '"http://192.168.1.95:8100/api/v1/"',
  // BASE_API: '"http://192.168.1.89:8100/api/v1/"',
  // BASE_API: '"http://test.zcsc.hzggzyjy.com/api/v1/"',

})
