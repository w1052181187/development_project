// pages/annoucement/index.js
import { toast, addressMate, init } from '../../utils/util.js'
import { baseApi } from '../../apis/baseApi.js'
import pageUtil from '../../utils/page.js'

const date = new Date()
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    // navigationBarTitle: app.globalData.navigationBarTitle,
    // isShowRefresh: false,
    isHasNetwork: true,
    contentHeight: 0,
    // swiper不显示之current问题
    current: 0,
    indicatorDots: false,
    autoplay: true,
    circular: true,
    interval: 4000,
    duration: 1000,
    year: date.getFullYear(),
    month: date.getMonth() + 1,
    day: date.getDate(),
    week: '日一二三四五六'.charAt(date.getDay()),
    address: '广东省',
    addressDialogVisable: false,
    annoList: [],
    totalNum: 0,
    provinceId: 440000, // 省
    cityId: '', // 市
    bannerList: [],
    imgUrl: app.globalData.baseUrl + app.globalData.imgBaseUrl
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (app.globalData.userInfo === null) {
      wx.reLaunch({
        url: '/pages/login/index'
      })
      return
    }
    init((isHasNet, height) => {
      this.pageNo = 1
      this.setData({
        isHasNetwork: isHasNet,
        contentHeight: height
      })
      if (isHasNet) {
        pageUtil.initPage()
        this.getBanner()
        this.getList()
      }
    })
  },
  // banner数据获取
  getBanner () {
    let params = {
      isDelete: 0,
      type: 1
    }
    let url = app.globalData.baseUrl + '/api/wx/zbGongGaoBiaoDuan/miniProManagement'
    baseApi({
      url: url,
      data: params,
    }, false).then((resData) => {
      if (resData.resCode === '0000') {
        let list = resData.miniProManagements.filter(item => item.relativePath)
        this.setData({
          bannerList: list,
          indicatorDots: list.length > 1
        })
      }
    })
  },
  // 列表数据获取
  getList () {
    if (!this.data.isHasNetwork) {
      wx.stopPullDownRefresh()
      return
    }
    let that = this
    let params = {
      pageNo: this.pageNo || 1
    }
    if (this.searchKey) {
      params.gonggaomingcheng = this.searchKey
    } 
    if (this.data.provinceId) {
      params.provinceId = this.data.provinceId
    }
    if (this.data.cityId) {
      params.cityId = this.data.cityId
    }
    let url = app.globalData.baseUrl + '/api/wx/gonggao'
    baseApi({
      url: url,
      data: params,
      complete: () => {
        this.isLoading = false
        wx.stopPullDownRefresh()
      }
    }, false).then((resData) => {
      if (resData.resCode === '0000') {
        let list = this.pageNo === 1 ? [] : this.data.annoList
        list = list.concat(addressMate(resData.gonggaoList))
        this.setData({
          annoList: list,
          totalNum: resData.total
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
    this.setData({ current: 0 })
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
  refresh () {
    // if (this.data.isShowRefresh) {
    //   return
    // }
    // this.setData({
    //   isShowRefresh: true
    // })
    this.pageNo = 1
    pageUtil.initPage()
    this.getList()
    this.getBanner()
  },

  /**
   * s上拉加载更多
   */
  loadMore: function () {
    if (this.isLoading) {
      return
    }
    this.isLoading = true
    if (pageUtil.isHasNextPage(this.data.annoList, this.pageNo, this.data.totalNum)) {
      this.pageNo = pageUtil.getPage()
      this.getList()
    }
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
    pageUtil.initPage()
    this.pageNo = 1
    this.searchKey = e.detail.value
  },

  /**
   * 处理搜索
   */
  handleSearch: function () {
    this.getList()
  },

  /**
   * 显示地址弹框
   */
  handleAddresClick: function () {
    this.setData({
      addressDialogVisable: true
    })
  },
  /**
   * 关闭地址弹框
   */
  closeDialog: function () {
    this.setData({
      addressDialogVisable: false
    })
  },
  /**
   * 地址弹框确认
   */
  handleConfirm: function (event) {
    this.closeDialog()
    let item = event.detail.item
    if (item.isProvinceId) {
      this.setData({
        provinceId: item.id,
        address: item.city,
        cityId: ''
      })
    } else {
      this.setData({
        cityId: item.id,
        address: item.city,
        provinceId: ''
      })
    }
    let option = {
      id: item.id
    }
    this.pageNo = 1
    pageUtil.initPage()
    this.getList()
  },
  /**
   * 查看公告
   */
  handleLookClick: function (event) {
    let detailLink = event.currentTarget.dataset.url
    let id = event.currentTarget.dataset.id
    wx.navigateTo({
      url: 'detail/index' + '?url=' + detailLink + id,
    })
  },
  handleBannerClick: function (event) {
    let url = event.currentTarget.dataset.url
    if (!url) {
      return
    }
    wx.navigateTo({
      url: 'detail/index' + '?url=' + url,
    })
  }
})