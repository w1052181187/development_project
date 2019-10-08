import { baseApi } from '../../apis/baseApi.js'

let app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseImageUrl: app.globalData.baseUrl + '/review?filePath=',
    detailHeight: 0,
    coverUrl: '',
    content: '',
    description: '',
    masterCourse: '',
    showDialog: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 获取系统信息
    let that = this;
    wx.getSystemInfo({
      success: function (res) {
        // 可使用窗口宽度、高度（单位 px）
        // 计算列表高度,单位为rpx
        let height = (res.windowHeight - 38) * 750 / res.windowWidth;
        that.setData({
          detailHeight: height
        })
      }
    })

    let id = options.id
    let url = app.globalData.baseUrl + '/applet/lectureDetail.json?objectId=' + id
    baseApi({url: url}).then((resData) => {
      let teacher = resData.lecturer
      that.setData({
        coverUrl: teacher.fileInformationList[1].relativePath,
        content: teacher.content,
        description: app.convertHtmlToText(teacher.description),
        // description: teacher.description,
        masterCourse: teacher.masterCourse
      })
    })

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    wx.setNavigationBarTitle({
      title: '导师详情',
    })
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

  closeDialog() {
    this.setData({
      showDialog: false
    })
  },

  showDialog() {
    this.setData({
      showDialog: true
    })
  }
})