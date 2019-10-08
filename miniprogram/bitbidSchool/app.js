//app.js
import {toast} from 'utils/util.js'
App({
  onLaunch: function () {
    // 展示本地存储能力
    // var logs = wx.getStorageSync('logs') || []
    // logs.unshift(Date.now())
    // wx.setStorageSync('logs', logs)

    let that = this;
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        if (res.code) {
          that.globalData.code = res.code
          wx.request({
            url: this.globalData.baseUrl + '/applet/login.json?appid=' + this.globalData.appid + '&secret=' + this.globalData.secret + '&js_code=' + res.code,
            data: {},
            success: function (res) {
              if (res.data.resCode === '0000') {
                that.globalData.user = res.data.result
              } else {
                toast(res.data.resMessage)
              }
            }
          });
        } else {
          toast('用户登录失败！' + res.errMsg)
        }
      }
    })
    // 获取用户信息
    // wx.getSetting({
    //   success: res => {
    //     if (res.authSetting['scope.userInfo']) {
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
    //       wx.getUserInfo({
    //         success: res => {
    //           // 可以将 res 发送给后台解码出 unionId
    //           this.globalData.userInfo = res.userInfo
    //           console.log(res)

    //           // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
    //           // 所以此处加入 callback 以防止这种情况
    //           if (this.userInfoReadyCallback) {
    //             this.userInfoReadyCallback(res)
    //           }
    //         }
    //       })
    //     } else {
    //       wx.authorize({
    //         scope: 'scope.userInfo',
    //         success() {
    //           // 用户已经同意
    //           wx.getUserInfo({
    //             success: res => {
    //               // 可以将 res 发送给后台解码出 unionId
    //               this.globalData.userInfo = res.userInfo
    //               console.log(1111)

    //               // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
    //               // 所以此处加入 callback 以防止这种情况
    //               if (this.userInfoReadyCallback) {
    //                 this.userInfoReadyCallback(res)
    //               }
    //             }
    //           })
    //         }
    //       })
    //     }
    //   }
    // })
  },

  convertHtmlToText: function convertHtmlToText(inputText) {
    var returnText = "" + inputText;
    var re1 = new RegExp("<.+?>", "g")
    returnText = returnText.replace(re1, "");
    returnText = returnText.replace(/&nbsp;/ig, "");
    return returnText;
  },
  
  globalData: {
    code: "",
    user: null,
    appid: 'wxa0df94038d06e8e6',
    secret: '42a1edfe7d57ef96e2c7fae1dda35267',
    // baseUrl: 'https://wx.bibenet.com',
    baseUrl: 'https://www.bibenet.com',
    isCourseListClick: false, // 课程学习列表是否点击过，用来判断列表刷新
    isRecordListClick: false  // 我的学习记录列表是否点击过，用来判断列表刷新
  }
})