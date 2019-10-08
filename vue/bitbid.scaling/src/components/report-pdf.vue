<template>
  <div class="">
    <form ref="webform" name="webform" id="webform" action="" method="post" :style="heightStyle">
      <object id="WebPDF" classid="clsid:39E08D82-C8AC-4934-BE07-F6E816FD47A1" width="100%"  height="100%"  codebase="iWebPDF.ocx#version=7,0,0,0" VIEWASTEXT>
        <PARAM name="ShowMenus" value="0" />
      </object>
    </form>
  </div>
</template>

<script>
export default {
  name: '',
  props: ['objectId', 'pdfData'],
  data () {
    return {
      serverUrl: window.location.protocol + '//' + window.location.host,
      // heightStyle: 'height: 0px'
      heightStyle: this.handleHeight()
    }
  },
  methods: {
    init (fileName, filePath) {
      if (this.loadIWebPDF()) {
        this.openWebUrl(fileName, filePath)
      }
    },
    // 载入iWebPDF控件
    loadIWebPDF () {
      if (!('ActiveXObject' in window) && !window.ActiveXObject) {
        alert('请使用IE8以上浏览器查看此页面,点击确定返回!')
        return false
      } else {
        try {
          // 以下属性必须设置，实始化iWebPDF
          // WebUrl:系统服务器路径，与服务器文件交互操作，如保存、打开文档
          this.$refs.webform.WebPDF.WebUrl = this.serverUrl + '/PDFSignatureServer/PDFServer'
          // FileName:文档名称
          this.$refs.webform.WebPDF.FileName = '评标报告'
          // UserName:操作用户名
          this.$refs.webform.WebPDF.UserName = '金格科技'
          // RecordID:本文档记录编号
          this.$refs.webform.WebPDF.RecordID = new Date().getTime()
          // 工具栏可见（1,可见；0,不可见）
          this.$refs.webform.WebPDF.ShowTools = 0
          // 是否允许保存当前文档（1,允许；0,不允许）
          this.$refs.webform.WebPDF.SaveRight = 1
          // 是否允许打印当前文档（1,允许；0,不允许）
          this.$refs.webform.WebPDF.PrintRight = 0
          // 是否允许由控件弹出提示框 true表示允许  false表示不允许
          this.$refs.webform.WebPDF.AlterUser = false
          // 是否显示菜单栏（1,可见；0,不可见）
          this.$refs.webform.WebPDF.ShowMenus = 0
          // 是否显示侧边栏（1,可见；0,不可见）
          this.$refs.webform.WebPDF.ShowSides = this.pdfData.isSign
          // 是否显示书签树按钮（1,显示；0,不显示）
          this.$refs.webform.WebPDF.ShowBookMark = 0
          // 设置签章工具栏当前是否可见（1,可见；0,不可见）
          this.$refs.webform.WebPDF.ShowSigns = this.pdfData.isSign
          // 设置侧边栏的宽度
          this.$refs.webform.WebPDF.SideWidth = 60
          // webform.WebPDF.WebOpen(); //打开该文档    交互OfficeServer的OPTION="LOADFILE"    <参考技术文档>
          // StatusMsg(webform.WebPDF.Status);  //状态信息
          // 缩放比例
          this.$refs.webform.WebPDF.Zoom = 80
          // 当显示页释放角度
          this.$refs.webform.WebPDF.Rotate = 360
          // 当前显示的页码
          this.$refs.webform.WebPDF.CurPage = 1
          return true
        } catch (e) {
          // 显示出错误信息
          alert('请检查是否允许运行ActiveX组件或是否正确安装驱动程序!')
          return false
        }
      }
    },
    // 根据文件路径打开pdf文件
    openWebUrl (fileName, filePath) {
      try {
        this.$refs.webform.WebPDF.FileName = fileName
        if (this.$refs.webform.WebPDF.WebOpenUrlFile(filePath)) {
        } else {
          alert('加载失败')
        }
      } catch (e) {
        // 显示出错误信息
        alert('请检查是否允许运行ActiveX组件或是否正确安装驱动程序!')
      }
    },
    getClientHeight () {
      let clientHeight = window.screen.availHeight
      return clientHeight
    },
    handleHeight () {
      let height = this.getClientHeight() - 340
      return `height: ${height}px`
    }
  },
  mounted () {
    // this.heightStyle = this.handleHeight()
  }
}
</script>

<style scoped>
</style>
