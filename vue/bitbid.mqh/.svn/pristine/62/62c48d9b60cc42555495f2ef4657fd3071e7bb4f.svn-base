<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="apply-details-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawrecord' }" v-if="this.$route.query.flag === 'record'">选聘申请表</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawreview' }" v-if="this.$route.query.flag === 'review'">选聘备案审批</el-breadcrumb-item>
        <el-breadcrumb-item>查看详情</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 表单模块区域 -->
    <div class="apply-cont">
      <h2>太原煤气化公司{{currentOrganizationName}}选聘备案表</h2>
      <el-row :gutter="20" style="width:900px;margin:15px auto;">
        <el-col :span="12" style="text-align:left;padding-left:0;">
          <span class="apply-name-color">备案单位:</span><span>{{tableObj.enterpriseName}}</span>
        </el-col>
        <el-col :span="12" style="text-align:right;padding-right:0;">
          <span class="apply-name-color">报备日期:</span>
          <span>{{tableObj.createDate | filterDate}}</span>
        </el-col>
      </el-row>
      <!-- 表格 -->
      <div class="apply-table">
        <table>
          <tr>
            <td rowspan="2" class="apply-tab-color">项目单位</td>
            <td rowspan="2">{{tableObj.enterpriseName}}</td>
            <td class="apply-tab-color">联系人</td>
            <td><span>{{tableObj.contacts}}</span></td>
          </tr>
          <tr>
            <td class="apply-tab-color" style="text-align:center">联系方式</td>
            <td><span style="text-align:left">{{tableObj.phone}}</span></td>
          </tr>
          <tr>
            <td class="apply-tab-color">项目名称</td>
            <td>
              <span>{{tableObj.projectName}}</span>
            </td>
            <td class="apply-tab-color">拟定实施时间</td>
            <td>
              <span>{{tableObj.carryTime}}</span>
            </td>
          </tr>
          <tr>
            <td class="apply-tab-color">立项批准文件</td>
            <td colspan="3"><span>{{tableObj.approvalFile}}</span></td>
          </tr>
           <tr>
            <td class="apply-tab-color">项目内容介绍</td>
            <td colspan="3"><span>{{tableObj.content}}</span></td>
          </tr>
        </table>
      </div>
      <!-- 附件列表 -->
      <div class="apply-upload">
        <el-row :gutter="20">
          <el-col :span="2"><span class="apply-name-color">附件:</span></el-col>
          <el-col :span="22">
            <span class="file-list" v-for="(item, index) in this.tableObj.fileInformationList" :key="index">
              <img :src="item.fileSrc" alt="">
              <span><el-button type="text" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button></span>
            </span>
          </el-col>
        </el-row>
      </div>
      <!-- 附件列表结束 -->
      <!-- 审批记录 -->
      <div class="apply-shenpi">
        <el-row :gutter="20">
          <el-col :span="3"><span class="apply-name-color">审批记录:</span></el-col>
          <el-col :span="21" class="apply-cont-box">
            <el-steps direction="vertical" :active="1" :space="100">
              <el-step
                v-for="(item,index) in this.tableObj.approveRecordList"
                :key="index"
                :status="fliteredStatus(item.approveResult)">
                <span slot="title" class="step-span span-color span-first" v-if="item.approveResult === 0">{{item.applyName}}</span>
                <span slot="title" class="step-span span-color span-first" v-if="item.approveResult !== 0">{{item.approveName}}</span>
                <span slot="title" class="step-span span-color" v-if="item.approveResult === 0">{{item.applyOrganization}}</span>
                <span slot="title" class="step-span span-color" v-if="item.approveResult !== 0">{{item.approveOrganization}}</span>
                <span slot="title" class="step-span span-third">{{item.approveResult |filterApproveResult}}</span>
                <span slot="title" class="step-span span-color span-last">{{item.approveTime |filterDate }}</span>
                <div slot="title" class="apply-option" v-if="item.approveOpinion">
                  <span>审批意见:{{item.approveOpinion}}</span>
                </div>
              </el-step>
            </el-steps>
          </el-col>
        </el-row>
      </div>
      <!-- 审批记录结束 -->
    </div>
    <!-- 表单模块区域结束 -->
  </div>
</template>

<script>
import {drawrecord} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
import {formatDate} from 'common/js/date'
import {fileIcon} from 'common/js/base'
// 实例
export default {
  data () {
    return {
      tableObj: {},
      currentOrganizationName: ''
    }
  },
  computed: {},
  // 实例创建
  created () {
  },
  filters: {
    // 格式化申请时间
    filterDate (approveTime) {
      if (approveTime) {
        let date = new Date(approveTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return null
      }
    },
    filterApproveResult (row) {
      let result = row
      if (result === 5) {
        return '通过'
      } else if (result === 6) {
        return '退回'
      } else if (result === 7) {
        return '终止'
      } else if (result === 0) {
        return '发起申请'
      }
    }
  },
  methods: {
    /** 选聘中介类型的选择 */
    filterType (row) {
      if (row.type === 2) {
        this.currentOrganizationName = '咨询造价机构'
      } else if (row.type === 3) {
        this.currentOrganizationName = '审计评估机构'
      } else if (row.type === 4) {
        this.currentOrganizationName = '法律服务机构'
      }
    },
    fliteredStatus (status) {
      if (status === 5) {
        return 'success'
      } else if (status === 6) {
        return 'error'
      } else if (status === 7) {
        return 'wait'
      }
    },
    /**  查看选中项目的信息 */
    getCurrentDrawRecordInfo () {
      drawrecord.getOne(this.$route.query.objectId).then((res) => {
        if (res) {
          this.tableObj = res.data.selectApply
          //  获取当前中介机构的名称
          this.filterType(this.tableObj)
          let fileData = this.tableObj.fileInformationList
          let newFileData = fileData.filter((item) => {
            if (item.businessType !== '2') {
              return item
            }
          })
          this.tableObj.fileInformationList = newFileData
          this.getUploadFileType(newFileData)
        } else {
          return false
        }
      })
    },
    /** 获取附件对应的类型 */
    getUploadFileType (fileList) {
      if (fileList.length > 0) {
        fileList.forEach(item => {
          item.fileSrc = ''
          if (/(doc|docx|DOC|DOCX)$/.test(item.extName)) {
            item.fileSrc = fileIcon.doc
          } else if (/(xls|xlsx|XLS|XLSX)$/.test(item.extName)) {
            item.fileSrc = fileIcon.excel
          } else if (/(pdf|PDF)$/.test(item.extName)) {
            item.fileSrc = fileIcon.pdf
          } else if (/(txt|TXT)$/.test(item.extName)) {
            item.fileSrc = fileIcon.txt
          } else if (/(rar|RAR)$/.test(item.extName)) {
            item.fileSrc = fileIcon.rar
          } else if (/(zip|ZIP)$/.test(item.extName)) {
            item.fileSrc = fileIcon.zip
          } else if (/(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(item.extName)) {
            item.fileSrc = fileIcon.picture
          } else {
            item.fileSrc = fileIcon.unknow
          }
        })
      }
    },
    /** 下载附件文件 */
    downloadFile
  },
  // 实例创建完成
  mounted () {
    this.getCurrentDrawRecordInfo()
  }
}
</script>
<style lang="less">
#apply-details-page {
  .apply-cont {
    padding: 20px;
    margin: 20px;
    margin-bottom: 100px;
    background-color: #fff
  }
  .apply-table {
    width: 900px;
    margin: 0 auto;
    table {
      width: 100%;
      line-height: 40px;
      td:nth-child(2n) {
        min-width: 100px;
        padding-left: 10px;
        text-align: left;
        font-size: 14px;
      }
      td:nth-child(2n-1) {
        width: 100px;
        padding: 2px;
        font-size: 14px;
      }
    }
    table, tr, td{
      border: 1px solid #999;
      border-collapse:collapse;
    }
  }
  .apply-upload, .apply-shenpi {
    width: 900px;
    margin: 0 auto;
    text-align: left;
    padding-top: 20px;
  }
  // 附件列表
  .apply-upload {
    .file-tit {
      display: inline-block;
      float: left;
      font-size: 14px;
      color: #999;
      margin-top: 25px;
    }
    .file-list {
      width: 280px;
      height: 65px;
      display: inline-block;
      overflow: hidden;
      border: 1px solid #eeeeee;
      span {
        width: 150px;
        display: inline-block;
        font-size: 14px;
        padding: 10px 10px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
      .file-dx {
        color: #999;
      }
      img {
        display: inline-block;
        float: left;
        margin: 8px auto;
        padding-left: 5px;
      }
    }
  }
  .apply-tab-color {
    background: #F3F3F3;
  }
  .apply-tab-color, .apply-name-color {
    color: #999;
  }
  /*审批记录css*/
  .apply-shenpi {
    padding: 20px;
    background: #fff;
    .step-span {
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      padding-right: 20px;
      box-sizing: border-box;
      display: inline-block;
      font-weight: normal;
      font-size: 14px;
    }
    .span-first {
      width: 160px;
    }
    .span-second {
      width: 180px;
    }
    .span-last {
      float: right;
    }
    .span-third {
      width: 100px;
    }
    .span-color {
      color: #999;
    }
    .apply-cont-box {
      max-height: 500px;
      overflow: auto;
      .apply-option {
        span {
          font-size: 14px;
          color: #999;
        }
      }
    }
  }
}
</style>
