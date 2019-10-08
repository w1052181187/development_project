'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./dev.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  ENV_CONFIG: '"dev"',
  COMPONENTS_BASE_API: '"http://localhost:8999/components/api/v1/"',
  UP_BASE_API: '"http://localhost:8999/userprofile/api/v1/"',
  PUBRESOURCE_BASE_API: '"http://localhost:8999/pubresource/api/v1/"',
  ROOM_BASE_API: '"http://192.168.1.13:8104/"'
})
