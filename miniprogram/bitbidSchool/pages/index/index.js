//index.js
import { baseApi } from '../../apis/baseApi.js'

//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //课程学习点击
  handleCourseClick () {
    wx.navigateTo({
      url: '../course/course',
    })
  },
  //企业定制点击
  handleCustomClick() {
    wx.navigateTo({
      url: '../custom-course/custom-course',
    })
  },
  onLoad: function () {
    // if (app.globalData.userInfo) {
    //   this.setData({
    //     userInfo: app.globalData.userInfo,
    //     hasUserInfo: true
    //   })
    // } else if (this.data.canIUse){
    //   // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
    //   // 所以此处加入 callback 以防止这种情况
    //   app.userInfoReadyCallback = res => {
    //     this.setData({
    //       userInfo: res.userInfo,
    //       hasUserInfo: true
    //     })
    //   }
    // } else {
    //   // 在没有 open-type=getUserInfo 版本的兼容处理
    //   wx.getUserInfo({
    //     success: res => {
    //       app.globalData.userInfo = res.userInfo
    //       this.setData({
    //         userInfo: res.userInfo,
    //         hasUserInfo: true
    //       })
    //     }
    //   })
    // }
    
  },
  onShow: function () {
    let code = app.globalData.code
    if (app.globalData.user === null && code !== "") {
      let url = app.globalData.baseUrl + '/applet/login.json?appid=' + app.globalData.appid + '&secret=' + app.globalData.secret
        + '&js_code=' + code
      baseApi({ url: url }).then((resData) => {
        app.globalData.user = resData.result
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})
