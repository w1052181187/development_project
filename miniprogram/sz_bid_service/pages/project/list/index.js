// pages/project/list/index.js
import { init } from '../../../utils/util.js'
import { baseApi } from '../../../apis/baseApi.js'
import pageUtil from '../../../utils/page.js'

const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    // isShowRefresh: false,
    contentHeight: 0,
    projectList: [],
    totalNum: 4,
    isHasNetwork: true,
    isShow: false
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
    let title = '项目'
    switch (options.type) {
      case 'zb':
        title = '我的招标项目'
        break
      case 'tb':
        title = '我的投标项目'
        break
    }
    wx.setNavigationBarTitle({
      title: title
    })
    init((isHasNet, height) => {
      this.pageNo = 1
      this.setData({
        isHasNetwork: isHasNet,
        contentHeight: height
      })
      if (isHasNet) {
        pageUtil.initPage()
        this.roleIds = app.globalData.roleIds.filter(item => Number(item) === 1003 || Number(item) === 1002)
        this.projectType = options.type
        this.getList(options.type)
      }
    })    
  },

  /**
   * 获取项目列表
   */
  getList(type) {
    if (!this.data.isHasNetwork) {
      wx.stopPullDownRefresh()
      return
    }
    let url = ''
    let params = {
      pageNo: this.pageNo || 1
    }
    if (this.searchKey) {
      params.faBaoMingCheng = this.searchKey
    }
    if (type === 'tb') {
      url = '/api/wx/zbGongGaoBiaoDuan/tb'
      params.userId = app.globalData.userInfo.userId
    } else {
      // 采购人
      if (this.roleIds.length !== 0) {
        url = '/api/wx/zbGongGaoBiaoDuan/cg'
        params.userName = app.globalData.userInfo.userName
      } else {
        url = '/api/wx/zbGongGaoBiaoDuan/zb'
        params.userId = app.globalData.userInfo.userId
      }
    }
    url = app.globalData.baseUrl + url
    baseApi({
      url: url,
      data: params,
      complete: () => {
        this.isLoading = false
        wx.stopPullDownRefresh()
      }
    }).then((resData) => {
      if (resData.resCode === '0000') {
        let list = this.pageNo === 1 ? [] : this.data.projectList
        list = list.concat(resData.zbGongGaoBiaoDuanList)
        this.setData({
          isShow: true,
          projectList: list,
          totalNum: resData.total
        })
      }
    })
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
    // if (this.data.isShowRefresh) {
    //   return
    // }
    // this.setData({
    //   isShowRefresh: true
    // })
    this.pageNo = 1
    pageUtil.initPage()
    this.getList(this.projectType)
  },

  /**
   * 上拉加载更多
   */
  loadMore: function () {
    if (this.isLoading) {
      return
    }
    this.isLoading = true
    if (pageUtil.isHasNextPage(this.data.projectList, this.pageNo, this.data.totalNum)) {
      this.pageNo = pageUtil.getPage()
      this.getList(this.projectType)
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
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  /**
   * 搜索输入
   */
  searchInput: function (e) {
    this.pageNo = 1
    this.searchKey = e.detail.value
  },

  /**
   * 处理搜索
   */
  handleSearch: function () {
    this.getList(this.projectType)
  },

  /**
   * 查看项目详情
   */
  handleDetailClick: function (event) {
    let id = event.currentTarget.dataset.id
    wx.navigateTo({
      url: '../detail/index' + '?id=' + id + '&type=' + this.projectType,
    })
  }
})