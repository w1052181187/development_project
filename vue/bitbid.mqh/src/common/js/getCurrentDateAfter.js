export function getCurrentDateAfter (num, format, day) {
  let date = new Date()
  date.setMonth(date.getMonth() + (num * 1), 1)
  // 读取日期自动会减一，所以要加一
  let mo = date.getMonth() + 1
  if (mo === 4 || mo === 6 || mo === 9 || mo === 11) { // 小月
    if (day > 30) {
      day = 30
    }
  } else if (mo === 2) { // 2月
    if (isLeapYear(date.getFullYear())) {
      if (day > 29) {
        day = 29
      } else {
        day = 28
      }
    }
    if (day > 28) {
      day = 28
    }
  } else { // 大月
    if (day > 31) {
      day = 31
    }
  }
  let retureValue = date.format('yyyy' + format + mo + format + day)
  return retureValue
}
// JS判断闰年代码
function isLeapYear (Year) {
  if (((Year % 4) === 0) & ((Year % 100) !== 0) || ((Year % 400) === 0)) {
    return (true)
  } else { return (false) }
}

// 日期格式化
window.Date.prototype.format = function (format) {
  let o = {
    'M+': this.getMonth() + 1, // month
    'd+': this.getDate(), // day
    'h+': this.getHours(), // hour
    'm+': this.getMinutes(), // minute
    's+': this.getSeconds(), // second
    'q+': Math.floor((this.getMonth() + 3) / 3), // quarter
    'S': this.getMilliseconds()
    // millisecond
  }
  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  for (let k in o) {
    if (new RegExp('(" + k + ")').test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length))
    }
  }
  return format
}
