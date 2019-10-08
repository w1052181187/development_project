// pages/project/detail/index.js
import { baseApi } from '../../../apis/baseApi.js'
import { init, toast } from '../../../utils/util.js'

const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    isHasNetwork: true,
    contentHeight: 0,
    processStatusKeys: [],
    processMap: {},
    statusObj: {},
    // 招标
    statusObjZb: {
      1: '招标公告已发布',
      2: '招标项目采购文件已发售',
      3: '采购文件收到异议',
      4: '采购文件的有关澄清/修改文件已发布',
      5: '招标项目发布变更公告',
      6: '项目已开标，开标一览表已发布',
      7: '中标结果公告已发布 ',
      8: '本项目中标结果公告收到质疑',
      9: '中标结果公告质疑答复成功发布',
      10: '《中标通知书》已发布',
      11: '《中标结果通知》已发布',
      12: '资格预审公告已发布',
      13: '招标项目预审文件已发售',
      14: '资格预审文件收到异议',
      15: '资格预审文件的有关澄清/修改文件已发布',
      16: '资格预审结果已发布',
      19: '采购邀请书已发送',
      20: '采购邀请已被确认',
      21: '采购邀请已被拒绝'
    },
    // 投标
    statusObjTb: {
      1: '招标公告已发布',
      2: '招标项目采购文件已发售',
      3: '采购文件异议发出',
      4: '采购文件的有关澄清/修改文件已发布',
      5: '招标项目发布变更公告',
      6: '项目已开标，开标一览表已发布',
      7: '中标结果公告已发布 ',
      8: '中标结果公告质疑已发出',
      9: '中标结果公告质疑收到答复',
      10: '《中标通知书》已发布',
      11: '《中标结果通知》已发布',
      12: '资格预审公告已发布',
      13: '招标项目预审文件已发售',
      14: '资格预审文件异议发出',
      15: '资格预审文件的有关澄清/修改文件已发布',
      16: '资格预审结果已发布',
      19: '收到采购邀请',
      20: '已确认采购邀请',
      21: '已拒绝采购邀请'
    },
    projectId: '',
    projectInfo: {},
    annoId: '',
    annoUrl: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let tempStatus
    switch (options.type) {
      case 'zb':
        tempStatus = this.data.statusObjZb
        break
      case 'tb':
        tempStatus = this.data.statusObjTb
        break
    }
    init((isHasNet, height) => {
      this.setData({
        isHasNetwork: isHasNet,
        contentHeight: height,
        statusObj: tempStatus,
        projectId: options.id
      })
      if (isHasNet) {
        this.getDetail(options.id)
      }
    })
  },

  /**
   * 获取详情数据
   */
  getDetail (id) {
    let statusKeys = []
    let statusMap = new Map()
    let url = app.globalData.baseUrl + '/api/wx/zbGongGaoBiaoDuan/' + id
    baseApi({url: url}).then((resData) => {
      if (resData.resCode === '0000') {
        this.setData({
          projectInfo: resData.zbGongGaoBiaoDuan,
          annoId: resData.zbGongGaoBiaoDuan.gongGaoId,
          annoUrl: resData.detailUrl
        })
        if (resData.refProjectStatusVos.length > 0) {
          resData.refProjectStatusVos.map((item) => {
            if (!statusMap.hasOwnProperty(item.status)) {
              statusMap[item.status] = {
                isExpand: false,
                data: []
              }
              statusKeys.push(item.status)
            }
            statusMap[item.status].data.push(item)
          })
          // console.log(statusKeys)
          // console.log(statusMap)
          this.setData({
            processStatusKeys: statusKeys,
            processMap: statusMap
          })
        }
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
   * 点击查看详情
   */
  handleDetail: function (event) {
    wx.navigateTo({
      url: '../../annoucement/detail/index' + '?url=' + this.data.annoUrl + this.data.annoId,
    })
  },
  /**
   * 状态展开收起
   */
  handleExpandClick: function (event) {
    let statusValue = event.currentTarget.dataset.status
    let map = this.data.processMap
    map[statusValue].isExpand = !map[statusValue].isExpand
    this.setData({
      processMap: map
    })
  }
})