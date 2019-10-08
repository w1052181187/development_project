<template>
  <div class="">
    <div class="pageFormContent" style="width: 100%">
      <div align=center style="width: 100%">
        <OBJECT class="pdf" ref="TZPdfViewer" ID=TZPdfViewer align=middle  style='width: 100%;height: 61vh' CLASSID="CLSID:8537D1E8-73B4-4060-8AC5-8817E3344C8E"></OBJECT>
      </div>
    </div>
  </div>
</template>

<script>
import {pdfUrl} from '@/assets/js/common'
export default {
  props: ['objectId', 'pdfData'],
  name: '',
  data () {
    return {
      serverUrl: pdfUrl,
      starStatus: true
    }
  },
  methods: {
    init (filePath) {
      if (this.loadWebPDF()) {
        this.OpenFile(filePath)
      }
    },
    // 载入WebPDF控件
    loadWebPDF () {
      if (!('ActiveXObject' in window) && !window.ActiveXObject) {
        alert('请使用IE8以上浏览器查看此页面,点击确定返回!')
        return false
      } else {
        try {
          // 以下属性必须设置，实始化WebPDF
          // 禁用打开按钮
          this.$refs.TZPdfViewer.ShowToolBarButton(0, false)
          // 禁用关闭按钮
          this.$refs.TZPdfViewer.ShowToolBarButton(1, false)
          // 禁用另存为按钮
          this.$refs.TZPdfViewer.ShowToolBarButton(2, false)
          // 禁用关于按钮
          this.$refs.TZPdfViewer.ShowToolBarButton(13, false)
          // 禁用批量验证按钮
          this.$refs.TZPdfViewer.ShowToolBarButton(24, false)
          // 禁用文档打印按钮
          this.$refs.TZPdfViewer.ShowToolBarButton(25, false)
          // 禁用参数设置按钮
          this.$refs.TZPdfViewer.ShowToolBarButton(26, false)
          // 禁用单页签章
          this.$refs.TZPdfViewer.ShowToolBarButton(21, false)
          // 禁用骑缝签章
          this.$refs.TZPdfViewer.ShowToolBarButton(22, false)
          // 禁用手写签名
          this.$refs.TZPdfViewer.ShowToolBarButton(23, false)
          if (this.pdfData.isSign === 1) {
            // 显示单页签章
            this.$refs.TZPdfViewer.ShowToolBarButton(21, true)
            // 显示骑缝签章
            this.$refs.TZPdfViewer.ShowToolBarButton(22, true)
            // 显示手写签名
            this.$refs.TZPdfViewer.ShowToolBarButton(23, true)
          }
          return true
        } catch (e) {
          // 显示出错误信息
          alert('请检查是否允许运行ActiveX组件或是否正确安装驱动程序!')
          return false
        }
      }
    },
    OpenFile (filePath) {
      try {
        this.$refs.TZPdfViewer.TZOpenPdfByPath(filePath, 1)
      } catch (e) {
        // 显示出错误信息
        alert('请检查是否允许运行ActiveX组件或是否正确安装驱动程序!')
      }
    },
    addSeal () {
      var sealResult = this.$refs.TZPdfViewer.TZInsertESeal()
      if (sealResult !== 0) {
        alert('读取签章用户信息出错！')
        return sealResult
      }
    },
    saveToService (fn) {
      if (this.$refs.TZPdfViewer.PageCount > 0) {
        // 文档的页数
        // 模拟HTTP实现初始化 return true,false
        this.$refs.TZPdfViewer.HttpInit()
        // alert(TZPdfViewer.TZIsPdfSigned);
        if (this.$refs.TZPdfViewer.TZIsPdfSigned === 0) {
          alert('请签章！')
          return
        }
        if (confirm('确定要发布吗？')) {
          let filepath = this.serverUrl.replace('/PDFSignatureServer/index', '') + '/PDFSignatureServer/webform'
          // 添加请求参数
          this.$refs.TZPdfViewer.HttpAddPostString('objectId', this.pdfData.objectId)
          // 添加请求参数
          this.$refs.TZPdfViewer.HttpAddPostString('signType', this.pdfData.signType)
          // 添加请求参数
          this.$refs.TZPdfViewer.HttpAddPostString('fileName', this.pdfData.fileName)
          // HttpAddPostCurrFile上传编辑器当前文件，第二个参数传"" ,随即产生Word的文件名
          this.$refs.TZPdfViewer.HttpAddPostCurrFile('file', '')
          // return true,false
          var ispost = this.$refs.TZPdfViewer.HttpPost(filepath)
          if (ispost) {
            alert('发布成功!')
            fn() // 重新获取是否锁定方法
          } else {
            alert('文档上传失败！错误代码：' + ispost)
          }
        }
      } else {
        alert('当前无文件打开！')
      }
    }
  },
  mounted () {
  }
}
</script>

<style scoped>
</style>
