'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./dev.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  ENV_CONFIG: '"dev"',
  // BASE_API: '"http://192.168.1.106:8081/api/v1/"'
  BASE_API: '"http://192.168.1.123:8201/api/v1/"'
})
