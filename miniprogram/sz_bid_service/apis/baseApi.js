import { toast, showLoading, hideLoading } from '../utils/util.js'
import { tokenKey } from '../utils/constant.js'

const baseApi = function (options = {}, isShowLoading = true, isShowReloginToast = true) {

  // 添加cookie
  let cookieHeader = {
    // 'content-type': 'application/x-www-form-urlencoded',
    // 读取cookie
    // 'cookie': wx.getStorageSync("sz_bid_service_cookie"),
    // 读取token
    'weixin-token': wx.getStorageSync(tokenKey)
  }

  if (options.header) {
    Object.assign(options.header, cookieHeader)
  } else {
    options.header = cookieHeader
  }

  const {
    url,
    data,
    header,
    method,
    dataType,
    responseType,
    success,
    fail,
    complete,
    loadingText
  } = options

  return new Promise((resolve, reject) => {
    if (isShowLoading) {
      showLoading(loadingText)
    }
    wx.request({
      url,
      data: data,
      header: header,
      method: method,
      dataType: dataType,
      responseType: responseType,
      success: success ? success : function (res) {
        if (isShowLoading) {
          hideLoading()
        }
        if (res.statusCode === 200) {
          if (res.data.resCode === '9999') {
            toast(res.data.resMessage)
          } else if (res.data.resCode === '2222') {
            toast(res.data.resMessage)
          } else {
            resolve(res.data)
          }
        } else if (res.statusCode === 401) {
          if (isShowReloginToast) {
            toast('未登录或登录已过期，请重新登录')
          }
          // 登录过期
          wx.reLaunch({
            url: '/pages/login/index'
          })
        } else {
          toast('服务器错误')
        }
      },
      fail: function (err) {
        if (isShowLoading) {
          hideLoading()
        }
        toast('请求错误，请重新加载')
        if (fail) {
          fail()
        }
      },
      complete: function () {
        if (complete) {
          complete()
        }
      }
    })
  })
}

module.exports = {
  baseApi
}