/**
 * Created by lixuqiang on 2018/4/27.
 */

//response处理
const _RESPONSE_SUCCESS_CODE = "0000"
const checkResponse = (_this, res, callBack) => {
  if (res.data.resCode === _RESPONSE_SUCCESS_CODE){
    _this.$message({ message: res.data.resMessage, type: 'success' });
    if(typeof callBack === 'function'){
      callBack()
    }
  }else{
    _this.$message.error(res.data.resMessage)
  }
}

// 文件上传路径
const fileUploadUrl = process.env.BASE_API + 'file/upload'

const fileDownloadUrl = process.env.BASE_API + 'file/'

// 文件上传限制
const fileSize = (_this, file, biggerSize) => {
  // 默认上传文件大小50M
  let baseSize = 100
  // 上传文件大小拓展为100M
  biggerSize ? baseSize = 50 : baseSize
  const maxSize = file.size / 1024 / 1024 < baseSize
  const minSize = file.size
  if (!minSize) {
    _this.$message.error('上传大小不能小于0MB!')
    return false
  }
  if (!maxSize) {
    _this.$message.error(`上传大小不能超过${baseSize}MB!`)
    return false
  }
  return true
}

// 表格单元格title属性设置
const addtitle = (_this) => {
  _this.$nextTick(() => {
    var aTd = document.getElementsByTagName('td')
    for (let i = 0; i < aTd.length; i++) {
      let text = aTd[i].innerText
      if (!aTd[i].querySelector('button')) {
        aTd[i].setAttribute('title', text)
      }
    }
  })
}

// 将yyyy-MM-dd hh:mm:ss转换为Date对象
const parseDate = (date) => {
  return new Date(date.substring(0, 4), parseInt(date.substring(5, 7)) - 1, date.substring(8, 10), date.substring(11, 13), date.substring(14, 16), date.substring(17))
}

const downloadFile = (fileName, filePath) => {
  window.open(`${fileDownloadUrl}download?fileName=${encodeURI(fileName)}&filePath=${filePath}`)
}

const downloadFileMining = (fileName, filePath) => {
  window.open(`${fileDownloadUrl}download?fileName=${encodeURI(fileName)}&filePath=${filePath}`)
}

const titleName = '-山东省采购与招标网'

// date转换成指定日期格式
const dateFormat = function(date, fmt) {
  var o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds(),
    'q+': Math.floor((date.getMonth() + 3) / 3),
    'S': date.getMilliseconds()
  }
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
  for (var k in o) {
    if (new RegExp('(' + k + ')').test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
  }
  return fmt
}

export  { checkResponse, fileUploadUrl, fileSize, addtitle, downloadFile, downloadFileMining, parseDate, titleName, dateFormat }
