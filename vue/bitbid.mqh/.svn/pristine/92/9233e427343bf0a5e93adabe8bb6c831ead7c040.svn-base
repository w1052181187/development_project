'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  ENV_CONFIG: '"dev"',
  //BASE_API: '"https://cedp.gqcgbb.com/api/v1/"',
  BASE_API: '"http://192.168.1.51:8101/api/v1/"',
})
