
const toast = function(title, icon="none", duration=1000) {
  wx.showToast({
    title: title,
    duration: duration,
    icon: icon,
    mask: true
  })
}

const showLoading = function (title='加载中...') {
  wx.showLoading({
    title: title,
    mask: true
  })
}

const hideLoading = function () {
  wx.hideLoading()
}

module.exports = {
  toast: toast,
  showLoading: showLoading,
  hideLoading: hideLoading
}
