
// import record from '../item-record/item-record'
import { baseApi } from '../../apis/baseApi.js'
const pageUtil = require('../../utils/page.js');

let app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseImageUrl: app.globalData.baseUrl + '/review?filePath=',
    recordList: [
    ],
    totalNum: 0,
    page: 1,
    isInited: false,

    bgContexts: {},
    circleContexts: {},
    size: 0, /* 圆环盒子大小 size >= 2*x ( x 为canvas的绘制半径)*/
    step: 1
    // num: 100

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    pageUtil.initPage()
    this.getRecord()
  },

  getRecord() {
    let that = this
    let url = app.globalData.baseUrl + '/applet/appletListPage.json?wechatId=' + app.globalData.user.openid + '&pageNum='
     + that.data.page
    baseApi({url: url}).then((resData) => {
      let list = that.data.page === 1 ? [] : that.data.recordList
      list = list.concat(resData.items)

      // id拼接
      // var curTime = Date.parse(new Date()) / 1000
      list.map((item) => {
        // item.id += curTime
        that.drawCircleBg('circle_bg' + item.id, 25, 4)
        that.drawCircle('circle_draw' + item.id, 25, 4, item.studyProgress / 100 * 2)
        return item
      })
      that.setData({
        recordList: list,
        totalNum: resData.page.totalRows
      })

      app.globalData.isRecordListClick = false
      if (!that.data.isInited) {
        that.setData({
          isInited: true
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    // this.circle = this.selectComponent("#circle1")
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if (app.globalData.isRecordListClick && this.data.isInited) {
      this.setData({
        page: 1
      })
      let that = this
      setTimeout(function () {
        that.getRecord()
      }, 100)
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
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (pageUtil.isHasNextPage(this.data.recordList, this.data.page, this.data.totalNum)) {
      this.setData({
        page: pageUtil.getPage()
      })
      this.getRecord()
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  },

  handleItemClick(event) {
    let code = event.currentTarget.dataset.code
    wx.navigateTo({
      url: '../course-detail/course-detail' + '?code=' + code,
    })
  },

  /*
    * 有关参数
    * id : canvas 组件的唯一标识符 canvas-id 
    * x : canvas 绘制圆形的半径 
    * w : canvas 绘制圆环的宽度 
    */
  drawCircleBg: function (id, x, w) {
    // 设置圆环外面盒子大小 宽高都等于圆环直径
    this.setData({
      size: 2 * x   // 更新属性和数据的方法与更新页面数据的方法类似
    });
    // 使用 wx.createContext 获取绘图上下文 ctx  绘制背景圆环
    
    // let ctx = this.data.bgContexts[id]
    // if (ctx === undefined) {
    //   ctx = wx.createCanvasContext(id, this)
    //   this.data.bgContexts[id] = ctx
    //   this.setData({
    //     bgContexts: this.data.bgContexts
    //   })
    // }
    let ctx = wx.createCanvasContext(id, this)
    ctx.setLineWidth(w / 2);
    ctx.setStrokeStyle('#dddddd');
    ctx.setLineCap('round')
    //设置一个原点(x,y)，半径为r的圆的路径到当前路径 此处x=y=r
    ctx.arc(x, x, x - w, 0, 2 * Math.PI, false);
    ctx.stroke();//对当前路径进行描边
    ctx.draw();
  },

  drawCircle: function (id, x, w, step) {
    // 使用 wx.createContext 获取绘图上下文 context  绘制彩色进度条圆环
    // let context = this.data.circleContexts[id]
    // if (context === undefined) {
    //   context = wx.createCanvasContext(id, this)
    //   this.data.circleContexts[id] = context
    //   this.setData({
    //     circleContexts: this.data.circleContexts
    //   })
    // }
    let context = wx.createCanvasContext(id, this)
    // 设置渐变
    var gradient = context.createLinearGradient(2 * x, x, 0);
    gradient.addColorStop("0", "#fec710");
    gradient.addColorStop("0.5", "#fec710");
    gradient.addColorStop("1.0", "#fec710");
    context.setLineWidth(w);
    context.setStrokeStyle(gradient);
    context.setLineCap('round');
    context.beginPath();//开始一个新的路径
    // step 从0到2为一周
    context.arc(x, x, x - w, -Math.PI / 2, step * Math.PI - Math.PI / 2, false);
    context.stroke();//对当前路径进行描边
    context.draw()
  }
  
})