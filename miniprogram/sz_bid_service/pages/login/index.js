// pages/login/index.js
import { toast, init } from '../../utils/util.js'
import { baseApi } from '../../apis/baseApi.js'
import { tokenKey, userInfoKey, roleIdsKey } from '../../utils/constant.js'

const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    isHasNetwork: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    init((isHasNet) => {
      this.setData({
        isHasNetwork: isHasNet
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
   * 登录
   */
  formSubmit: function (e) {
    let account = e.detail.value.name
    let password = e.detail.value.password
    if (!account) {
      toast('用户名或手机号不能为空')
    } else if (!password) {
      toast('密码不能为空')
    } else {
      if (this.data.isHasNetwork) {
        let url = app.globalData.baseUrl + '/api/wx/login'
        baseApi({ url: url, method: 'post', data: e.detail.value, loadingText: '登录中...' }).then((resData) => {
          if (resData.resCode === '0000') {
            wx.setStorageSync(tokenKey, resData.token)
            this.getUserInfo()
          }
        })
      }
    }
  },
  /**
   * // 获取用户信息
   */
  getUserInfo: function () {
    let url = app.globalData.baseUrl + '/api/wx/getLoginInfo'
    baseApi({ url: url }, false).then((resData) => {
      if (resData.resCode === '0000') {
        app.globalData.userInfo = resData.loginUserInfo
        app.globalData.roleIds = resData.roleIds
        wx.setStorageSync(userInfoKey, JSON.stringify(resData.loginUserInfo))
        wx.setStorageSync(roleIdsKey, JSON.stringify(resData.roleIds))
        wx.switchTab({
          url: '/pages/annoucement/index'
        })
      } else {
        toast('用户信息获取失败')
      }
    })
  }
})