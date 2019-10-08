'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./dev.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  ENV_CONFIG: '"dev"',
  // COMPONENTS_BASE_API: '"http://192.168.1.61:8103/api/v1/"',
  // PROJECT_FLOW_BASE_API: '"http://192.168.1.61:8101/api/v1/"',
  // UP_BASE_API: '"http://192.168.1.61:8102/api/v2/"',
  // FILE_UPLOAD_API: '"http://192.168.1.61:8103/api/v1/"'

  // UP_BASE_API: '"http://192.168.1.81:8102/api/v2/"',
  // FILE_UPLOAD_API: '"http://192.168.1.81:8101/api/v1/"',
  // PROJECT_FLOW_BASE_API: '"http://192.168.1.81:8101/api/v1/"',
  // COMPONENTS_BASE_API: '"http://192.168.1.81:8103/api/v1/"'
  COMPONENTS_BASE_API: '"http://192.168.1.123:8999/components/api/v1"',
  PROJECT_FLOW_BASE_API: '"http://192.168.1.123:8999/projectflow/api/v1/"',
  UP_BASE_API: '"http://192.168.1.123:8999/userprofile/api/v2/"',
  FILE_UPLOAD_API: '"http://192.168.1.123:8101/api/v1/"'

  // COMPONENTS_BASE_API: '"http://192.168.1.114:8103/api/v1/"',
  // PROJECT_FLOW_BASE_API: '"http://192.168.1.114:8101/api/v1/"',
  // UP_BASE_API: '"http://192.168.1.114:8999/userprofile/api/v2/"',
  // FILE_UPLOAD_API: '"http://192.168.1.114:8103/api/v1/"'
})

