//app.js
import { baseApi } from '/apis/baseApi.js'
import { userInfoKey, roleIdsKey } from '/utils/constant.js'

let customNavigationBarHeight = 45
let bottomTabHeight = 48

App({
  onLaunch: function (options) {
    // console.log("[onLaunch] 场景值:", options.scene)
    // let globalData = this.globalData
    // wx.login({
    //   success: function(res){
    //     let url = globalData.baseUrl + '/miniProgram/getOpenid?code=' + res.code
    //     baseApi({ url: url }, false).then((resData) => {
    //       console.log(resData)
    //       if (resData.resCode === '0000') {
    //         console.log(resData)
    //       }
    //     })
    //   }
    // })
  },

  onShow: function (options) {
    let storeInfo = wx.getStorageSync(userInfoKey)
    if (storeInfo) {
      this.globalData.userInfo = JSON.parse(storeInfo)
    }
    let storeRoleId = wx.getStorageSync(roleIdsKey)
    if (storeRoleId) {
      this.globalData.roleIds = JSON.parse(storeRoleId)
    }
  },

  globalData: {
    userInfo: null,
    roleIds: [],
    // statusBarHeight: 0,
    appid: 'wxfa8ed51d61b8895f',
    secret: '97ebf63a1da3038b68cf580ae80c55f1',
    baseUrl: 'https://www.bibeinfo.com', // 正式
    // baseUrl: 'https://szyd.wx.gqcgbb.com', // 测试
    imgBaseUrl: '/api/v1/file/review?filePath='
    // navigationBarTitle: '远东（深圳）招投标信息服务'
  }
})