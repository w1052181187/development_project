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
    <div class="maincont-box apply-cont">
      <h2>太原煤气化公司招标备案表</h2>
      <el-row :gutter="20" style="width:900px;margin:15px auto;">
        <el-col :span="12" style="text-align:left;padding-left:0;">
          <span class="apply-name-color">备案单位:</span><span>{{tableObj.enterpriseName}}</span>
        </el-col>
        <el-col :span="12" style="text-align:right;padding-right:0;">
          <span class="apply-name-color">报备日期:</span><span>{{tableObj.createDate | filterDate}}</span>
        </el-col>
      </el-row>
      <!-- 表格 -->
      <div class="apply-table">
        <table>
          <tr>
            <td rowspan="2" class="apply-tab-color">项目单位</td>
            <td colspan="3" rowspan="2">{{tableObj.enterpriseName}}</td>
            <td class="apply-tab-color">联系人</td>
            <td><span>{{tableObj.contacts}}</span></td>
          </tr>
          <tr>
            <td class="apply-tab-color">联系方式</td>
            <td><span>{{tableObj.phone}}</span></td>
          </tr>
          <tr>
            <td class="apply-tab-color">招标项目地址</td>
            <td colspan="5"><span>{{tableObj.projectAddress}}</span></td>
          </tr>
          <tr>
            <td class="apply-tab-color">招标项目名称</td>
            <td colspan="3">
              <span>{{tableObj.projectName}}</span>
            </td>
            <td class="apply-tab-color">计划工期</td>
            <td>
              <span>{{tableObj.timeLimit}}</span>
            </td>
          </tr>
          <tr>
            <td class="apply-tab-color">立项批准文件</td>
            <td><span>{{tableObj.approvalFile}}</span></td>
            <td class="apply-tab-color">(概算)计划额度</td>
            <td><span>{{tableObj.planQuota}}</span></td>
            <td class="apply-tab-color">拟招标时间</td>
            <td><span>{{tableObj.biddingTime}}</span></td>
          </tr>
          <tr>
            <td class="apply-tab-color">拟招标方式机及组织形式</td>
            <td colspan="3">
              <div><span>{{tableObj.biddingType | filteredType}}</span></div>
              <div><span>{{tableObj.organizationalType | filteredOrgan}}</span></div>
            </td>
            <td class="apply-tab-color">计划开工(到货)时间</td>
            <td><span>{{tableObj.plannedTime}}</span></td>
          </tr>
          <tr>
            <td class="apply-tab-color">依法必须招标</td>
            <td colspan="3">
              <span>{{tableObj.isAccordToLaw | filteredIsToLaw}}</span>
            </td>
            <td class="apply-tab-color">是否电子招标</td>
            <td>
              <span>{{tableObj.isElectronicBidding | filteredIsToLaw}}</span>
            </td>
          </tr>
          <tr>
            <td class="apply-tab-color">施工设计和技术方案(设备参数)批注文件</td>
            <td colspan="5">
              <span>{{tableObj.executionFile}}</span>
            </td>
          </tr>
          <tr>
            <td class="apply-tab-color">招标内容简介</td>
            <td colspan="5"><span>{{tableObj.content}}</span></td>
          </tr>
           <tr>
            <td class="apply-tab-color">招标最高限价</td>
            <td colspan="5"><span>{{tableObj.highestPrice}}</span></td>
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
                :status='fliteredStatus(item.approveResult)'>
                <span slot="title" class="step-span span-color span-first" v-if="item.approveResult === 0">{{item.applyName}}</span>
                <span slot="title" class="step-span span-color span-first" v-if="item.approveResult !== 0">{{item.approveName}}</span>
                <span slot="title" class="step-span span-color" v-if="item.approveResult === 0">{{item.applyOrganization}}</span>
                <span slot="title" class="step-span span-color" v-if="item.approveResult !== 0">{{item.approveOrganization}}</span>
                <span slot="title" class="step-span span-third">{{item.approveResult | filterApproveResult}}</span>
                <span slot="title" class="step-span span-color span-last">{{item.approveTime | filterDate}}</span>
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
import {downloadFile} from 'common/js/common'
import {formatDate} from 'common/js/date'
import {fileIcon} from 'common/js/base'
// 实例
export default {
  data () {
    return {
      tableObj: {},
      fileInformationList: [], // 附件列表
      approveRecordList: [] // 审批流程列表
    }
  },
  computed: {
  },
  // 实例创建
  created () {
    this.getCurrentDrawRecordInfo()
  },
  methods: {
    /**  查看选中项目的信息 */
    getCurrentDrawRecordInfo () {
      drawrecord.getOne(this.$route.query.objectId).then((res) => {
        if (res) {
          this.tableObj = res.data.selectApply
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
    /** 设置审批状态 */
    fliteredStatus (status) {
      if (status === 5) {
        return 'success'
      } else if (status === 6) {
        return 'error'
      } else if (status === 7) {
        return 'wait'
      }
    },
    /** 下载附件文件 */
    downloadFile
  },
  /** 过滤 */
  filters: {
    filteredType (value) {
      if (value === 1) {
        return '公开招标'
      } else if (value === 2) {
        return '邀请招标'
      } else if (value === 3) {
        return '谈判采购'
      } else if (value === 4) {
        return '询比采购'
      } else if (value === 5) {
        return '竞价采购'
      } else if (value === 6) {
        return '直接采购'
      } else if (value === 7) {
        return '框架协议'
      }
    },
    filteredOrgan (value) {
      if (value === 1) {
        return '委托招标'
      } else {
        return '自行招标'
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
    },
    // 格式化申请时间
    filterDate (approveTime) {
      if (approveTime) {
        let date = new Date(approveTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return null
      }
    },
    filteredIsToLaw (value) {
      if (value === 1) {
        return '是'
      } else {
        return '否'
      }
    }
  },
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
#apply-details-page {
  .apply-cont {
    padding: 20px;
    background-color: #fff
  }
  .apply-table {
    width: 900px;
    margin: 0 auto;
    table {
      width: 100%;
      line-height: 40px;
    }
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
      margin-right: 20px;
      margin-bottom: 10px;
      display: inline-block;
      border: 1px solid #eeeeee;
      span {
        display: block;
        font-size: 14px;
        padding: 5px 20px;
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
  // 审批记录
  .apply-shenpi {
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
  .apply-tab-color {
    background: #F3F3F3;
  }
  .apply-tab-color, .apply-name-color {
    color: #999;
  }
}
</style>
