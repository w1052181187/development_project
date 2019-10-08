// pages/course-detail/course-detail.js
import { baseApi } from '../../apis/baseApi.js'

let app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseImageUrl: app.globalData.baseUrl + '/review?filePath=',
    studyRecordId: '',
    studyChapter: 1,
    studyPitch: 1,
    selectedChapterId: '',
    selectedPitchId: '',
    // 是否学习过
    isStudy: 0,
    showStartStudy: true, // 本地字段（判断是否点击开始学习）
    url: '',
    courseCover: '',
    courseName: '',
    courseDesc: '',
    courseChapter: '',
    coursePitch: '',
    list: [
    ],
    teacherList: [
    ],
    summary: '',
    code: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this
    let code = options.code
    // that.setData({
    //   code: code
    // })

    let url = app.globalData.baseUrl + '/applet/detail.json?code=' + options.code + '&wechatId=' + app.globalData.user.openid
    baseApi({url: url}).then((resData) => {
      let course = resData.course
      let sectionList = resData.sectionList
      let chapter = course.studyChapter - 1 || 0
      let pitch = course.studyPitch - 1 || 0
      let studyId = course.studyRecordId
      if (course.isStudy === 0) {
        chapter = 0
        pitch = 0
        studyId = ''
      }
      // 处理默认或者学习到的视频
      let playUrl =''
      let chapterId = 0
      let pitchId = 0
      if (sectionList.length !== 0) {
        playUrl = sectionList[chapter].hours[pitch].video
        chapterId = sectionList[chapter].objectId
        pitchId = sectionList[chapter].hours[pitch].objectId
      }
      // wx.setNavigationBarTitle({
      //   title: course.courseName,
      // })

      that.setData({
        courseCover: course.courseCover,
        courseDesc: course.courseIntro,
        courseName: course.courseName,
        url: playUrl,
        selectedChapterId: chapterId,
        selectedPitchId: pitchId,
        teacherList: resData.teacherList,
        list: sectionList,
        studyRecordId: studyId,
        summary: app.convertHtmlToText(course.courseSummarize),
        isStudy: course.isStudy
      })
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
    this.videoContext = wx.createVideoContext('myVideo')
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

    if (this.data.showStartStudy) {
      return
    }
    let params = {
      wechatId: app.globalData.user.openid,
      courseCode: this.data.code,
      sectionId: this.data.selectedChapterId,
      hourId: this.data.selectedPitchId,
      sectionNum: this.data.studyChapter,
      hourNum: this.data.studyPitch
    }
    if (this.data.isStudy === 1) {
      params.objectId = this.data.studyRecordId
    }

    for (var index in params) {
      if (params[index] === 'undefined' || params[index] === '') {
        return
      }
    }

    this.uploadStudyRecord(params)
  },

  /**
   * 上传学习记录
   */
  uploadStudyRecord(data) {
    let options = {
      url: app.globalData.baseUrl + '/applet/save.json',
      data: data,
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      method: 'post',
      loadingText: '提交中...'
    }
    baseApi(options)
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
   * 点击开始学习
   */
  handleStudyClick () {
    this.setData({
      showStartStudy: false
    })
    this.videoContext.play()
    app.globalData.isCourseListClick = true
    app.globalData.isRecordListClick = true
  },

  /**
   * 章点击
   */
  handleChapterClick(event) {
    this.setData({
      selectedChapterId: event.currentTarget.dataset.id,
      studyChapter: event.currentTarget.dataset.index + 1
    })
  },

  /**
   * 节点击
   */
  handlePitchClick(event) {
    let item = event.currentTarget.dataset.item
    if (item.objectId === this.data.selectedPitchId) {
      return
    }
    this.setData({
      studyPitch: event.currentTarget.dataset.index + 1,
      selectedPitchId: item.objectId,
      url: item.video,
      showStartStudy: false
    })
    let that = this
    setTimeout(function () {
      //将点击视频进行播放
      that.videoContext.play();
    }, 700)

    // 回到顶部
    wx.pageScrollTo({
      scrollTop: 0,
      duration: 300
    })
  },

  handlePlay(e) {
    app.globalData.isCourseListClick = true
    app.globalData.isRecordListClick = true
  },

  // handleError(e) {
  //   console.log(e)
  // },

  /**
   * 教师展开收起
   */
  handleShowClick(event) {
    let index = event.currentTarget.dataset.index
    let teachers = this.data.teacherList
    teachers[index].isShow = !teachers[index].isShow
    this.setData({
      teacherList: teachers
    })
  }
})