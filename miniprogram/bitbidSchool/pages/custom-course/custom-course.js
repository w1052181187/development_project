import { baseApi } from '../../apis/baseApi.js'
const pageUtil = require('../../utils/page.js');

let app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseImageUrl: app.globalData.baseUrl + '/review?filePath=',
    listHeight: 0,
    curTab: 0,
    tabList: [
      {
        id: 0,
        objectId: 10001,
        name: '招标采购'
      },
      {
        id: 1,
        objectId: 10002,
        name: 'PPP课程'
      },
      {
        id: 2,
        objectId: 10003,
        name: '电子商务'
      },
      {
        id: 3,
        objectId: 10004,
        name: '供应链管理'
      }
    ],
    teacherList: [],
    showDialog: false,
    totalNum: 0,
    page: 1
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
        let height = (res.windowHeight - 44 - 35) * 750 /res.windowWidth;
        that.setData({
          listHeight: height
        })
      }
    })
    pageUtil.initPage()
    let objectId = this.data.tabList[this.data.curTab].objectId
    this.getList(objectId)
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
    if (pageUtil.isHasNextPage(this.data.teacherList, this.data.page, this.data.totalNum)) {
      this.setData({
        page: pageUtil.getPage()
      })
      let objectId = this.data.tabList[this.data.curTab].objectId
      this.getList(objectId)
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  },

  /** 
  * 点击tab切换 
  */
  swichNav(e) {
    let id = e.target.dataset.id
    if (this.data.curTab === id) {
      return false;
    } else {
      this.setData({
        curTab: id,
        page: 1,
        teacherList: []
      })
      this.getList(this.data.tabList[id].objectId)
    }
  },

  // 滚动切换标签样式
  switchTab: function (e) {
    if (e.detail.source === 'touch') {
      let id = e.detail.current
      this.setData({
        curTab: id,
        page: 1,
        teacherList: []
      })
      this.getList(this.data.tabList[id].objectId)
    }
  },

  getList(id) {
    let that = this
    let url = app.globalData.baseUrl + '/applet/lecturerListPage.json?pageNum=' + that.data.page + '&type=' + id
    baseApi({url: url}).then((resData) => {
      let list = that.data.teacherList
      list = list.concat(resData.Lecturers)
      that.setData({
        teacherList: list,
        totalNum: resData.page.totalRows
      })
    })
  },

  handleItemClick(event) {
    let id = event.currentTarget.dataset.id
    wx.navigateTo({
      url: '../teacher-detail/teacher-detail' + '?id=' + id,
    })
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