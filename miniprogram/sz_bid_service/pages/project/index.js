// pages/project/index.js
import { baseApi } from '../../apis/baseApi.js'
import { init } from '../../utils/util.js'

const app = getApp()
let isHasNet = true

Page({

  /**
   * 页面的初始数据
   */
  data: {
    // navigationBarTitle: app.globalData.navigationBarTitle,
    contentHeight: 0,
    bannerList: [],
    current: 0,
    circular: true,
    indicatorDots: false, // 是否显示小圆点
    autoplay: true, // 是否自动轮播
    bannerInterval: 4000, // 切换时间
    duration: 1000, // 滑动动画时长
    isShow: false,
    msg: {},
    imgUrl: app.globalData.baseUrl + app.globalData.imgBaseUrl,
    // animationData: {} //公告动画
    isScollEnable: false,
    marqueePace: 1,//滚动速度
    marqueeDistance: 0,//初始滚动距离
    marquee_margin: 300,
    size: 12,
    interval: 20 // 时间间隔
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
      this.isHasNet = isHasNet
      this.setData({
        contentHeight: height
      })
      if (this.isHasNet) {
        this.getBanner()
        this.getSingleMsg()
      }
    })
  },
  // banner数据获取
  getBanner() {
    let params = {
      isDelete: 0,
      type: 2
    }
    let url = app.globalData.baseUrl + '/api/wx/zbGongGaoBiaoDuan/miniProManagement'
    baseApi({
      url: url,
      data: params,
    },false).then((resData) => {
      if (resData.resCode === '0000') {
        let list = resData.miniProManagements.filter(item => item.relativePath)
        this.setData({
          bannerList: list,
          indicatorDots: list.length > 1
        })
      }
    })
  },
  // 单条数据获取
  getSingleMsg() {
    let params = {
      pageNo: 1,
      isDelete: 0,
      publishStatus: 1
    }
    let url = app.globalData.baseUrl + '/api/wx/miniProSysNotice/queryOne'
    baseApi({
      url: url,
      data: params,
    },false).then((resData) => {
      if (resData.resCode === '0000') {
        this.setData({
          msg: resData.miniProSysNotice || {}
        })
        if (this.data.msg !== '{}') {
          this.initAnimation(resData.miniProSysNotice.content)
        }
      }
    })
  },

  /**
     * 开启公告字幕滚动动画
     * @param  {String} announcementText 公告内容
     * 
     */
  initAnimation: function (announcementText) {
    let length = announcementText.length * this.data.size;//文字长度
    this.contentWidth = wx.getSystemInfoSync().windowWidth - 80;
    this.setData({
      marquee_margin: this.contentWidth,
      isScrollEnable: false
    });
    this.scrolltxt(length);// 第一个字消失后立即从右边出现
  },

  scrolltxt: function (length) {
    let that = this
    let windowWidth = this.contentWidth;//屏幕宽度
    if (length > windowWidth) {
      this.setData({
        isScrollEnable: true
      })
      this.interval = setInterval(function () {
        let maxscrollwidth = length + that.data.marquee_margin;//滚动的最大宽度，文字宽度+间距，如果需要一行文字滚完后再显示第二行可以修改marquee_margin值等于windowWidth即可
        let crentleft = that.data.marqueeDistance;
        if (crentleft < maxscrollwidth) {//判断是否滚动到最大宽度
          that.setData({
            marqueeDistance: crentleft + that.data.marqueePace
          })
        } else {
          //console.log("替换");
          that.setData({
            marqueeDistance: 0 // 直接重新滚动
          });
          clearInterval(that.interval);
          that.scrolltxt(length);
        }
      }, this.data.interval);
    }
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
    this.setData({ current: 0 })
    if (this.isHasNet) {
      this.getBanner()
      this.getSingleMsg()
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    clearInterval(this.interval);
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    // clearInterval(this.interval);
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

  // 系统消息
  newSysteBtn: function () {
    wx.navigateTo({
      url: '/pages/my/systemMsg/index',
    })
  },

  /**
   * 我的招标/投标项目点击
   */
  handleClick: function (event) {
    let type = event.currentTarget.dataset.type
    wx.navigateTo({
      url: 'list/index?type=' + type,
    })
  },
  handleBannerClick: function (event) {
    let url = event.currentTarget.dataset.url
    if (!url) {
      return
    }
    wx.navigateTo({
      url: '/pages/annoucement/detail/index' + '?url=' + url,
    })
  }
})