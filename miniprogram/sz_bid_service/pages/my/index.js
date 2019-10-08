// pages/my/index.js
import { baseApi } from '../../apis/baseApi.js'
import { init } from '../../utils/util.js'

const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    // navigationBarTitle: app.globalData.navigationBarTitle,
    contentHeight: 0,
    userInfo: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 未登录跳转登录页
    if (app.globalData.userInfo === null) {
      wx.reLaunch({
        url: '/pages/login/index'
      })
      return
    }
    init((isHasNet, height) => {
      this.setData({
        userInfo: app.globalData.userInfo,
        contentHeight: height
      })
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  /**
   * 系统信息
   */
  systemBtn: function () {
    wx.navigateTo({
      url: 'systemMsg/index',
    })
  },

  /**
   * 退出登录
   */
  logout: function () {
    try {
      let url = app.globalData.baseUrl + '/api/wx/logout'
      baseApi({ url: url }).then((resData) => {
        if (resData.resCode === '0000') {
          wx.clearStorage()
          wx.reLaunch({
            url: '/pages/login/index'
          })
        }
      })
    } catch (e) {
      // Do something when catch error
    }
  }
})