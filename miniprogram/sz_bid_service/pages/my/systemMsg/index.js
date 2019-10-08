// pages/my/systemMsg/index.js
import { toast, addressMate, init } from '../../../utils/util.js'
import { baseApi } from '../../../apis/baseApi.js'
import pageUtil from '../../../utils/page.js'

const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    contentHeight: 0,
    msgList: [],
    totalNum: 1,
    isHasNetwork: true,
    isShow: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    init((isHasNet, height) => {
      this.pageNo = 1
      this.setData({
        isHasNetwork: isHasNet,
        contentHeight: height
      })
      if (isHasNet) {
        this.getList()
      }
    })
  },
  // 系统信息列表
  getList () {
    let params = {
      pageNo: this.pageNo,
      isDelete: 0,
      publishStatus: 1
    }
    let url = app.globalData.baseUrl + '/api/wx/miniProSysNotice'
    baseApi({
      url: url,
      data: params,
      complete: () => {
        this.isLoading = false
        wx.stopPullDownRefresh()
      }
    }, false).then((resData) => {
      if (resData.resCode === '0000') {
        let list = this.pageNo === 1 ? [] : this.data.msgList
        list = list.concat(resData.proSysNoticePageInfo.list)
        this.setData({
          isShow: true,
          msgList: list,
          totalNum: resData.proSysNoticePageInfo.total
        })
      }
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
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.refresh()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.loadMore()
  },

  /**
   * 下拉刷新
   */
  refresh() {
    this.pageNo = 1
    pageUtil.initPage()
    this.getList()
  },

  /**
   * s上拉加载更多
   */
  loadMore: function () {
    if (this.isLoading) {
      return
    }
    this.isLoading = true
    if (pageUtil.isHasNextPage(this.data.msgList, this.pageNo, this.data.totalNum)) {
      this.pageNo= pageUtil.getPage()
      this.getList()
    }
  },
})