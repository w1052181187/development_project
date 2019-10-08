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

// 文件上传路径
const fileUploadUrl = `${process.env.ASSETS_API}file/upload`
// 富文本编辑中图片上传路径
const ueditorUploadUrl = `${process.env.ASSETS_API}file/ueditor-upload`
// 文件下载路径
const fileDownloadUrl = `${process.env.ASSETS_API}file/`
// pdf路径
const pdfDemoDownloadUrl = `${process.env.ASSETS_API}ftlDemo/`
// 图片展示路径
const imgShowUrl = `${process.env.ASSETS_API}file/review`
// 登录验证图片地址
const loginUrl = process.env.ASSETS_API
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
// 下载组件
const downloadFile = (fileName, filePath) => {
  window.open(`${fileDownloadUrl}download?fileName=${encodeURI(fileName)}&filePath=${filePath}`)
}
export  {checkResponse, fileUploadUrl, ueditorUploadUrl, fileSize, addtitle, downloadFile, parseDate, pdfDemoDownloadUrl, imgShowUrl, loginUrl}
