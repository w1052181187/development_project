// components/custom-navigationBar/index.js

const app = getApp()

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    title: String,
    isShowBack: {
      type: Boolean,
      value: false
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    statusBarHeight: app.globalData.statusBarHeight,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    handleBackClick: function () {
      wx.navigateBack({
        delta: 1
      })
    }
  }
})
