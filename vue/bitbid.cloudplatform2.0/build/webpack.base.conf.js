'use strict'
const path = require('path')
const utils = require('./utils')
const config = require('../config')
const vueLoaderConfig = require('./vue-loader.conf')
// happyPack
const os = require('os')
const HappyPack = require('happypack')
// 根据电脑的cpu判断线程数
const happyThreadPool = HappyPack.ThreadPool({size: os.cpus().length})
// hard-source-webpack-plugin
const HardSourceWebpackPlugin = require('hard-source-webpack-plugin')

function resolve (dir) {
  return path.join(__dirname, '..', dir)
}

const createLintingRule = () => ({
  test: /\.(js|vue)$/,
  loader: 'eslint-loader',
  enforce: 'pre',
  include: [resolve('src'), resolve('test')],
  options: {
    formatter: require('eslint-friendly-formatter'),
    emitWarning: !config.dev.showEslintErrorsInOverlay
  }
})

module.exports = {
  context: path.resolve(__dirname, '../'),
  entry: {
    app: ['./node_modules/babel-polyfill/dist/polyfill.js', './src/main.js']
  },
  output: {
    path: config.build.assetsRoot,
    filename: '[name].js',
    publicPath: process.env.NODE_ENV === 'production'
      ? config.build.assetsPublicPath
      : config.dev.assetsPublicPath
  },
  resolve: {
    extensions: ['.js', '.vue', '.json'],
    alias: {
      'vue$': 'vue/dist/vue.esm.js',
      '@': resolve('src'),
      'jquery': path.resolve(__dirname, '../node_modules/jquery/src/jquery'),
      // 静态资源目录
      'static': resolve('static')
    }
  },
  module: {
    rules: [
      ...(config.dev.useEslint ? [createLintingRule()] : []),
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: vueLoaderConfig
      },
      {
        test: /\.js$/,
        // loader: 'babel-loader',
        //把对.js 的文件处理交给id为happyBabel 的HappyPack 的实例执行
        loader: 'happypack/loader?id=happyBabel',
        include: [resolve('src'), resolve('test'), resolve('node_modules/webpack-dev-server/client'),
          resolve('/node_modules/element-ui/src'),resolve('/node_modules/element-ui/packages')],
        //排除node_modules 目录下的文件
        exclude: /node_modules/
      },
      {
        test:/\.css$/,
        loader: ['style-loader','css-loader', 'less-loader'],
        include: [resolve('src'), resolve('test'),resolve('static'),
          resolve('node_modules/webpack-dev-server/client'),
          resolve('/node_modules/element-ui/src'),resolve('/node_modules/element-ui/packages')]
      },
      {
        test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('img/[name].[hash:7].[ext]')
        }
      },
      {
        test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('media/[name].[hash:7].[ext]')
        }
      },
      {
        test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('fonts/[name].[hash:7].[ext]')
        }
      },
      {
        //视频加载
        test: /\.(mp4|flv|swf)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        loader: "file-loader",
        options: {
          name: utils.assetsPath("video/[name].[hash:7].[ext]")
        }
      }
    ]
  },
  node: {
    // prevent webpack from injecting useless setImmediate polyfill because Vue
    // source contains it (although only uses it if it's native).
    setImmediate: false,
    // prevent webpack from injecting mocks to Node native modules
    // that does not make sense for the client
    dgram: 'empty',
    fs: 'empty',
    net: 'empty',
    tls: 'empty',
    child_process: 'empty'
  },
  plugins: [
    new HappyPack({
      //用id来标识 happypack处理那里类文件
      id: 'happyBabel',
      //如何处理  用法和loader 的配置一样
      loaders: [{
        loader: 'babel-loader?cacheDirectory=true',
      }],
      //共享进程池
      threadPool: happyThreadPool,
      //允许 HappyPack 输出日志
      verbose: true,
    }),
    // new HardSourceWebpackPlugin({
    //   // cacheDirectory是在高速缓存写入。默认情况下，将缓存存储在node_modules下的目录中，因此如
    //   // 果清除了node_modules，则缓存也是如此
    //   cacheDirectory: 'node_modules/.cache/hard-source/[confighash]',
    //   // Either an absolute path or relative to webpack's options.context.
    //   // Sets webpack's recordsPath if not already set.
    //   recordsPath: 'node_modules/.cache/hard-source/[confighash]/records.json',
    //   // configHash在启动webpack实例时转换webpack配置，并用于cacheDirectory为不同的webpack配
    //   // 置构建不同的缓存
    //   configHash: function(webpackConfig) {
    //     // node-object-hash on npm can be used to build this.
    //     return require('node-object-hash')({sort: false}).hash(webpackConfig);
    //   },
    //   // 当加载器，插件，其他构建时脚本或其他动态依赖项发生更改时，hard-source需要替换缓存以确保输
    //   // 出正确。environmentHash被用来确定这一点。如果散列与先前的构建不同，则将使用新的缓存
    //   environmentHash: {
    //     root: process.cwd(),
    //     directories: [],
    //     files: ['package-lock.json', 'yarn.lock'],
    //   }
    // })
  ]
}
