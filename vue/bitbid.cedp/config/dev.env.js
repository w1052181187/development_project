'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./dev.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  ENV_CONFIG: '"dev"',

  COMPONENTS_BASE_API: '"http://192.168.1.84:8103/api/v1/"',
  PROJECT_FLOW_BASE_API: '"http://192.168.1.84:8101/api/v1/"',
  UP_BASE_API: '"http://192.168.1.84:8102/api/v2/"',
  FILE_UPLOAD_API: '"http://192.168.1.84:8102/api/v1/"'
  // BIBENET_PATH: '"http://iip.test.nw.bibenet.com/"'
})
