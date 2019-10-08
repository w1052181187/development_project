import { toast, showLoading, hideLoading } from '../utils/util.js'

const baseApi = function (options = {}) {

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
  } = options;

  return new Promise((resolve, reject) => {
    showLoading(loadingText)
    wx.request({
      url,
      data: data,
      header: header,
      method: method,
      dataType: dataType,
      responseType: responseType,
      success: success ? success : function (res) {
        hideLoading()
        if (res.data.resCode === '0000') {
          resolve(res.data)
        } else {
          toast(res.data.resMessage)
        }
      },
      fail: fail ? fail : function (err) {
        hideLoading()
        toast('请求错误，请重新加载')
      }
    })
  })
}

module.exports = {
  baseApi
}