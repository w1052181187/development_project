import { baseApi } from '../../apis/baseApi.js'
const pageUtil = require('../../utils/page.js');

let app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseImageUrl: app.globalData.baseUrl + '/review?filePath=',
    courseList: [
    ],
    totalNum: 0,
    page: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    pageUtil.initPage()
    this.getCourseList()
  },

  /**
   * 获取课程列表
   */
  getCourseList() {
    let that = this
    let url = app.globalData.baseUrl + '/applet/queryListPage.json?wechatId=' + app.globalData.user.openid +
      '&pageNum=' + that.data.page
    baseApi({url: url}).then((resData) => {
      let list = that.data.page === 1 ? [] : that.data.courseList
      list = list.concat(resData.items)
      that.setData({
        courseList: list,
        totalNum: resData.page.totalRows
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
    if (app.globalData.isCourseListClick) {
      this.setData({
        page: 1
      })
      this.getCourseList()
    }
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
    // wx.showToast({
    //   title: '下拉',
    // })
    // wx.stopPullDownRefresh()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (pageUtil.isHasNextPage(this.data.courseList, this.data.page, this.data.totalNum)) {
      this.setData({
        page: pageUtil.getPage()
      })
      this.getCourseList()
    }
  },

  handleItemClick (event) {
    let code = event.currentTarget.dataset.code
    wx.navigateTo({
      url: '../course-detail/course-detail' + '?code=' + code,
    })
    
  }
})
