'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  ENV_CONFIG: '"dev"',
  // COMPONENTS_BASE_API: '"http://192.168.1.9:8101/api/v1/"',
  // UP_BASE_API: '"http://192.168.1.55:8102/api/v1/"',
  // COMPONENTS_BASE_API: '"http://192.168.1.126:8102/components/api/v1/"',
  // UP_BASE_API: '"http://192.168.1.126:8999/userprofile/api/v1/"',
  PROJECT_FLOW_BASE_API: '"http://192.168.1.81:8101/api/v1/"',
  UP_BASE_API: '"http://192.168.1.61:8102/api/v1/"',
  FILE_UPLOAD_API: '"http://192.168.1.81:8102/api/v1/"',
  // PROJECT_FLOW_BASE_API: '"http://192.168.1.114:8103/api/v1/"'
  // PROJECT_FLOW_BASE_API: '"http://192.168.1.61:8101/api/v1/"'
})
