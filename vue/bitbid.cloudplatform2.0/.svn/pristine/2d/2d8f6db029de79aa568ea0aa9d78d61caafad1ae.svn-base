/**
 * Created by lixuqiang on 2018/4/27.
 */

//response处理
const _RESPONSE_SUCCESS_CODE = "0000"
const checkResponse = (_this, res, callBack) => {
  if (res.data.resCode === _RESPONSE_SUCCESS_CODE){
    _this.$message({ message: res.data.resMessage, type: 'success' });
    if(typeof callBack === 'function'){
      callBack();
    }
  }else{
    _this.$message.error(res.data.resMessage);
  }
}

const loadUrl = process.env.FILE_UPLOAD_API;

// 文件上传路径
const fileUploadUrl = `${process.env.FILE_UPLOAD_API}file/upload`
const ueditorUploadUrl = `${process.env.FILE_UPLOAD_API}file/ueditor-upload`
const fileDownloadUrl = `${process.env.FILE_UPLOAD_API}file/`
const fileReviewUrl = `${process.env.FILE_UPLOAD_API}file/review?filePath=`
// 钱包地址
const bibenetWalletPath = `${process.env.BIBENET_PATH}enterpriseInfoWallet/indexFlowListPage.htm`

const archivesFileDownload = (archivesFileIds) => {
  window.open(`${process.env.FILE_UPLOAD_API}file/downloadArchivesFile?` + archivesFileIds)
}
// 文件上传限制
const fileSize = (_this, file, biggerSize) => {
  // 默认上传文件大小50M
  let baseSize = biggerSize || 100
  // 上传文件大小拓展为100M
  // biggerSize ? baseSize = 50 : baseSize
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

const downloadFile = (fileName, filePath) => {
  window.open(`${loadUrl}download?fileName=${encodeURI(fileName)}&filePath=${filePath}`)
}
const toChinesNum = (num) => {
  let changeNum = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九']
  let unit = ["", "十", "百", "千", "万"]
  num = parseInt(num)
  let getWan = (temp) => {
    let strArr = temp.toString().split("").reverse()
    let newNum = ""
    for (var i = 0; i < strArr.length; i++) {
      newNum = (i == 0 && strArr[i] == 0 ? "" : (i > 0 && strArr[i] == 0 && strArr[i - 1] == 0 ? "" : changeNum[strArr[i]] + (strArr[i] == 0 ? unit[0] : unit[i]))) + newNum;
    }
    return newNum
  }
  let overWan = Math.floor(num / 10000)
  let noWan = num % 10000
  if (noWan.toString().length < 4) {
    noWan = "0" + noWan
  }
  return overWan ? getWan(overWan) + "万" + getWan(noWan) : getWan(num)
}

/**  字符串去空 */
const trimStr = (str) => {
  return str.replace(/^\s+|\s+$/gm,'')
}

/** 浅拷贝 */
const extend = (destination, source) => {
  for (var property in source) {
    destination[property] = source[property]
  }
}

// 将yyyy-MM-dd hh:mm:ss转换为Date对象
const parseDate = (date) => {
  return new Date(date.substring(0, 4), parseInt(date.substring(5, 7)) - 1, date.substring(8, 10), date.substring(11, 13), date.substring(14, 16), date.substring(17))
}

// date转换成指定日期格式
// 兼容ie
const dateFormat = function(date, format) {
  var time;
  if(!date)return "";
  if(typeof date === 'string'){
    time = new Date(date.replace(/-/g,'/').replace(/T|Z/g,' ').trim());
  }else if(typeof date === 'object'){
    time = new Date(date) ;
  }
  var o = {
    "M+": time.getMonth() + 1, //月份
    "d+": time.getDate(), //日
    "h+": time.getHours(), //小时
    "m+": time.getMinutes(), //分
    "s+": time.getSeconds(), //秒
    "q+": Math.floor((time.getMonth() + 3) / 3), //季度
    "S": time.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (time.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(format)) format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return format
}

export  { checkResponse, loadUrl, fileSize, addtitle, downloadFile, toChinesNum, fileUploadUrl, archivesFileDownload, ueditorUploadUrl, fileDownloadUrl, fileReviewUrl, trimStr, extend, parseDate, dateFormat, bibenetWalletPath}
