'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./dev.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  ENV_CONFIG: '"dev"',
  // 竞争性谈判
  // COMPONENTS_BASE_API: '"http://jzxtph.hz.bitbid.cn/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://jzxtph.hz.bitbid.cn/api/v1/"',
  // PDF_LINK: '"http://jzxtph.hz.bitbid.cn/PDFSignatureServer/index"',
  // COMPONENTS_BASE_API: '"http://192.168.1.104:7999/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://192.168.1.104:7999/api/v1/"',
  // PDF_LINK: '"http://192.168.1.104:7999/PDFSignatureServer/index"',
  // COMPONENTS_BASE_API: '"http://192.168.1.50:7999/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://192.168.1.50:7999/api/v1/"',
  // PDF_LINK: '"http://192.168.1.50:7999/PDFSignatureServer/index"',
  // COMPONENTS_BASE_API: '"http://192.168.1.15:7105/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://192.168.1.15:7105/api/v1/"',
  // PDF_LINK: '"http://192.168.1.15:7105/PDFSignatureServer/index"',

  // 竞争性磋商
  COMPONENTS_BASE_API: '"http://jzxcsh.hz.bitbid.cn/api/v1/bid-tools/"',
  DOWNLOAD_LINK: '"http://jzxcsh.hz.bitbid.cn/api/v1/"',
  PDF_LINK: '"http://jzxcsh.hz.bitbid.cn/PDFSignatureServer/index"',
  // COMPONENTS_BASE_API: '"http://192.168.1.104:6999/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://192.168.1.104:6999/api/v1/"',
  // PDF_LINK: '"http://192.168.1.104:6999/PDFSignatureServer/index"',
  // COMPONENTS_BASE_API: '"http://192.168.1.15:8107/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://192.168.1.15:8107/api/v1/"',
  // PDF_LINK: '"http://192.168.1.15:8107/PDFSignatureServer/index"',

  // 竞争性单一
  // COMPONENTS_BASE_API: '"http://dylyh.hz.bitbid.cn/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://dylyh.hz.bitbid.cn/api/v1/"',
  // PDF_LINK: '"http://dylyh.hz.bitbid.cn/PDFSignatureServer/index"',
  // COMPONENTS_BASE_API: '"http://192.168.1.104:5999/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://192.168.1.104:5999/api/v1/"',
  // PDF_LINK: '"http://192.168.1.104:5999/PDFSignatureServer/index"',
  // COMPONENTS_BASE_API: '"http://192.168.1.15:7108/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://192.168.1.15:7108/api/v1/"',
  // PDF_LINK: '"http://192.168.1.15:7108/PDFSignatureServer/index"',

  // 竞争性询价
  // COMPONENTS_BASE_API: '"http://xjh.hz.bitbid.cn/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://xjh.hz.bitbid.cn/api/v1/"',
  // PDF_LINK: '"http://xjh.hz.bitbid.cn/PDFSignatureServer/index"',
  // COMPONENTS_BASE_API: '"http://192.168.1.104:4999/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://192.168.1.104:4999/api/v1/"',
  // PDF_LINK: '"http://192.168.1.104:4999/PDFSignatureServer/index"'
  // COMPONENTS_BASE_API: '"http://192.168.1.15:8109/api/v1/bid-tools/"',
  // DOWNLOAD_LINK: '"http://192.168.1.15:8109/api/v1/"',
  // PDF_LINK: '"http://192.168.1.15:8109/PDFSignatureServer/index"',
  // 公开招标
  //   COMPONENTS_BASE_API: '"http://192.168.1.50:3074/api/v1/bid-tools/"',
  //   DOWNLOAD_LINK: '"http://192.168.1.50:3074/api/v1/"',
  //   PDF_LINK: '"http://192.168.1.50:3074/PDFSignatureServer/index"',
})
