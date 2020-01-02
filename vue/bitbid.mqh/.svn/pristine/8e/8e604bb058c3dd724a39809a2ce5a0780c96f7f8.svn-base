<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="indreview-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawreview'}">选聘备案审批</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/drawreview/indreview'}">审批</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="dreview-cont">
      <h2>太原煤气化公司{{currentOrganizationName}}中介机构选聘备案表</h2>
      <div class="form-title">
        <span class="apply-name-color apply-pos-l">备案单位:<span>{{this.$route.query.enterpriseName}}</span></span>
        <span class="apply-name-color apply-pos-r">报备日期:<span>{{tableObj.createDate | filterDate}}</span></span>
      </div>
      <div class="dreview-form">
        <table :data="tableData">
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
        <!-- 附件列表 -->
        <div class="dreview-file">
          <el-row>
            <el-col :span="2">
              <span class="file-tit">附件:</span>
            </el-col>
            <el-col :span="22">
              <span class="file-list" v-for="(item, index) in this.tableObj.fileInformationList" :key="index">
                <img :src="item.fileSrc" alt="">
                <span><el-button type="text" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button></span>
              </span>
            </el-col>
          </el-row>
        </div>
        <!-- 审批 -->
        <div class="review-pass">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
            <el-form-item label="审批状态:" prop="approveResult">
              <el-select v-model="ruleForm.approveResult" placeholder="请选择审批状态" style="width: 280px;">
                <el-option
                  v-for="item in stateList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="审批意见:" v-if="ruleForm.approveResult !== 5" prop="approveOpinion">
              <el-input type="textarea" v-model="ruleForm.approveOpinion" style="width: 280px;">{{ruleForm.approveResult}}</el-input>
            </el-form-item>
            <el-form-item label="审批意见:" v-if="ruleForm.approveResult === 5" >
              <el-input type="textarea" v-model="ruleForm.approveOpinion" style="width: 280px;">{{ruleForm.approveResult}}</el-input>
            </el-form-item>
          </el-form>
        </div>
        <!-- 按钮 -->
        <div class="review-sub-btn">
          <el-button @click="$router.go(-1)">取消</el-button>
          <el-button type="primary" @click="submitData('ruleForm')"  v-loading="btnLoading">提交</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {drawreview} from 'api/index'
import {downloadFile} from 'common/js/common'
import {formatDate} from 'common/js/date'
import {fileIcon} from 'common/js/base'
// 实例
export default {
  data () {
    return {
      btnLoading: false,
      tableObj: {},
      stateList: [
        {
          label: '通过',
          value: 5
        },
        {
          label: '退回',
          value: 6
        },
        {
          label: '终止',
          value: 7
        }
      ],
      ruleForm: {
        approveResult: '',
        relatedCode: '',
        approveOpinion: ''
      },
      rules: {
        approveResult: [
          { required: true, message: '请选择审批状态', trigger: 'change' }],
        approveOpinion: [
          {required: true, message: '请输入审批意见', trigger: 'change'}]
      },
      tableData: {
        contacts: '',
        phone: '123',
        timeLimit: '',
        planQuota: '',
        biddingTime: '',
        organizationalType: '',
        biddingType: '',
        plannedTime: '',
        executionFile: '',
        highestPrice: '',
        enterpriseName: '',
        projectName: '',
        approvalFile: '',
        content: ''
      },
      currentOrganizationName: '',
      computed: {}
    }
  },
  // 实例创建
  created () {
  },
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
    /** 格式化申请时间 */
    filterDate (createDate) {
      if (createDate) {
        let date = new Date(createDate)
        return formatDate(date, 'yyyy-MM-dd')
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
  methods: {
    /** 提交 */
    submitData (data) {
      this.$refs[data].validate((vaild) => {
        if (vaild) {
          // 加载动画
          this.btnLoading = true
          // 插入审批表信息
          this.ruleForm.approveName = this.$store.getters.authUser.userName
          this.ruleForm.approveOrganization = this.$store.getters.authUser.enterpriseName
          this.ruleForm.approveType = 1
          drawreview.saveApprovalRecords(this.ruleForm).then(() => {
            // 关闭动画
            this.btnLoading = false
            this.$router.push({path: '/draw/drawreview'})
          })
        } else {
          return false
        }
      })
    },
    /**  查看选中项目的信息 */
    getCurrentDrawReviewInfo () {
      drawreview.getOne(this.$route.query.objectId).then((res) => {
        if (res) {
          this.tableObj = res.data.selectApply
          this.ruleForm.relatedCode = res.data.selectApply.code
          //  获取当前中介机构的名称
          if (this.tableObj.type) {
            if (this.tableObj.type === 2) {
              this.currentOrganizationName = '咨询造价机构'
            } else if (this.tableObj.type === 3) {
              this.currentOrganizationName = '审计评估机构'
            } else if (this.tableObj.type === 4) {
              this.currentOrganizationName = '法律服务机构'
            }
          }
          //  附件文件信息
          this.getUploadFileType(this.tableObj.fileInformationList)
        }
      })
    },
    /** 根据附件类型确认显示图标 */
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
    this.getCurrentDrawReviewInfo()
  }
}
</script>
<style lang="less">
#indreview-page {
  .dreview-cont {
    padding: 20px;
    margin: 20px 20px 100px;
    background-color: #fff;
    .form-title {
      width: 900px;
      height: 40px;
      margin: 0 auto;
    }
    .apply-name-color {
      font-size: 14px;
      color: #999;
      span {
        color: #000;
      }
    }
    .apply-pos-l {
      display: inline-block;
      padding-bottom: 5px;
      float: left;
    }
    .apply-pos-r {
      display: inline-block;
      padding-bottom: 5px;
      float: right;
    }
  }
  .dreview-form {
    width: 900px;
    height: auto;
    margin: 0 auto;
    table {
      width: 100%;
      line-height: 40px;
      td:nth-child(2n) {
        min-width: 200px;
        padding: 0 10px;
        text-align: left;
        font-size: 14px;
      }
      td:nth-child(2n-1) {
        min-width: 100px;
        padding: 2px;
        font-size: 14px;
      }
      .apply-tab-color {
        background: #F6F7F9;
        color: #999;
      }
    }
    table, tr, td{
      border: 1px solid #dddddd;
      border-collapse:collapse;
    }
  }
  // 附件
  .dreview-file {
    padding: 10px 0;
    text-align: left;
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
        width: 170px;
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
  // 审批
  .review-pass {
    text-align: left;
    .el-form-item__label {
      color: #999;
      padding-right: 20px;
    }
    .el-textarea__inner {
      min-height: 100px !important;
    }
  }
}
</style>
