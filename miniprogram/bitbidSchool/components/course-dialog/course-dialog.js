// pages/course-dialog/course-dialog.js
import {toast} from '../../utils/util.js'
import { baseApi } from '../../apis/baseApi.js'

let app = getApp()
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    showSuccessDialog: false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    close() {
      this.triggerEvent('closeDialog')
    },

    /**
     * 提交
     */
    formSubmit(e) {
      let that = this
      let value = e.detail.value
      if (!value.name) {
        toast('请输入姓名')
        return
      }
      if (!value.contact) {
        toast('请输入手机号')
        return
      } else if (!(/^1[345789]\d{9}$/.test(value.contact))) {
        toast('请输入正确的手机号')
        return
      }
      if (!value.businessName) {
        toast('请输入企业名称')
        return
      }
      value.type = 1
      let options = {
        url: app.globalData.baseUrl + '/applet/add.json',
        data: value,
        header: {
          "Content-Type": "application/x-www-form-urlencoded"
        },
        method: 'post',
        loadingText: '提交中...'
      }
      baseApi(options).then(() => {
        that.setData({
          showSuccessDialog: true
        })
      })
    }
  }
})
