<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="apply-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/interapply' }">选聘申请表</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 表单模块区域 -->
    <div class="maincont-box apply-cont">
      <h2>太原煤气化公司{{currentOrganizationName}}中介机构选聘备案表</h2>
      <div class="apply-tab-tit">
        <el-row :gutter="20">
          <el-col :span="12" style="text-align:left;">
            <span class="apply-name-color">备案单位:</span>
            <span>{{this.$store.getters.authUser.enterpriseName}}</span>
          </el-col>
          <el-col :span="12" style="text-align:right;">
            <span class="apply-name-color">报备日期:</span>
            <span>{{new Date() | filterDate}}</span>
          </el-col>
        </el-row>
      </div>
      <!-- 表格 -->
      <div class="apply-form">
        <el-form :model="formData" :rules="formRules" ref="formData" label-width="140px" class="demo-ruleForm">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="项目单位">
                <span>{{this.$store.getters.authUser.enterpriseName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="联系人" prop="contacts">
                <el-input v-model="formData.contacts"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系方式" prop="phone">
                <el-input v-model="formData.phone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="项目名称" prop="projectName">
                <!--<el-select v-model="formData.projectName" placeholder="请选择" style="width:100%;">
                  <el-option
                    v-for="item in tendProjectList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>-->
                <el-input v-model="formData.projectName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="拟定实施时间" prop="carryTime">
                  <el-date-picker
                    v-model="formData.carryTime"
                    type="date"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    style="width:100%;"
                    @change="changeCarryTime">
                  </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="立项批准文件" prop="approvalFile">
                <el-input v-model="formData.approvalFile"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20" class="form-row-last">
            <el-col :span="24">
              <el-form-item label="项目内容简介" prop="content">
                <el-input type="textarea" v-model="formData.content"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 上传 -->
      <div class="apply-upload">
        <el-row :gutter="20">
          <el-col :span="3">
            <span class="apply-name-color">上传附件：</span>
          </el-col>
          <el-col :span="10" style="text-align: left;">
            <el-upload
              drag
              class="upload-demo"
              :action="upLoad"
              :auto-upload="true"
              :on-success="uploadSuccess"
              :file-list="fileList"
              :show-file-list="false"
              multiple>
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </el-upload>
            <!-- 上传附件列表 -->
            <div class="apply-upload">
              <el-row>
                <el-col :span="24">
                  <span class="file-list" v-for="(item, index) in fileList" :key="index">
                    <img :src="item.fileSrc" alt="">
                    <span>{{item.fileName}}</span>
                    <i class="el-icon-error upload-file" @click="clickDel(index)"></i>
                  </span>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 上传结束 -->
      <!-- 按钮开始 -->
      <div class="apply-sub-btn">
        <el-button type="success" @click="submitData('formData')">提交</el-button>
        <el-button type="primary" @click="saveData('formData')">保存</el-button>
        <el-button @click="$router.go(-1)">取消</el-button>
      </div>
      <!-- 按钮结束 -->
    </div>
    <!-- 表单模块区域结束 -->
  </div>
</template>

<script>
import {drawrecord, user} from 'api/index'
import {commonJs} from 'common/js/common'
import {fileIcon} from 'common/js/base'
import {isvalidPhone} from '../../../../assets/js/validate'
import {formatDate} from 'common/js/date'
// 实例
export default {
  data () {
    let validPhone = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    return {
      formData: {
        contacts: '',
        phone: ''
      },
      fileList: [],
      formRules: {
        contacts: [
          { required: true, message: '请输入联系人名称', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入联系方式', trigger: 'blur' },
          {validator: validPhone, trigger: 'blur'}
        ],
        projectName: [
          { required: true, message: '请输入项目名称', trigger: 'change' }
        ],
        carryTime: [
          { required: true, message: '请输入拟定实施时间', trigger: 'change' }
        ],
        planQuota: [
          { required: true, message: '请输入(概算)计划额度', trigger: 'change' }
        ],
        approvalFile: [
          { required: true, message: '请输入立项批准文件', trigger: 'blur' },
          {min: 1, max: 80, message: '请输入1~80个字符', trigger: ['blur', 'change']}
        ],
        executionFile: [
          { required: true, message: '请输入施工设计和技术方案(设备参数)批注文件', trigger: 'blur' },
          {min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change']}
        ],
        content: [
          { required: true, message: '请输入项目内容简介', trigger: 'blur' },
          {min: 1, max: 500, message: '请输入1~500个字符', trigger: ['blur', 'change']}
        ]
      },
      tendProjectList: [
        {
          label: '项目一',
          value: '项目一'
        },
        {
          label: '项目二',
          value: '项目二'
        },
        {
          label: '项目三',
          value: '项目三'
        },
        {
          label: '项目四',
          value: '项目四'
        },
        {
          label: '项目五',
          value: '项目五'
        },
        {
          label: '项目六',
          value: '项目六'
        }
      ],
      // 计划工期
      planTimeId: '',
      planTimeList: [
        {
          label: '1个月',
          value: '1'
        },
        {
          label: '2个月',
          value: '2'
        },
        {
          label: '3个月',
          value: '3'
        },
        {
          label: '4个月',
          value: '4'
        },
        {
          label: '5个月',
          value: '5'
        },
        {
          label: '6个月',
          value: '6'
        },
        {
          label: '7个月',
          value: '7'
        },
        {
          label: '8个月',
          value: '8'
        },
        {
          label: '9个月',
          value: '9'
        },
        {
          label: '10个月',
          value: '10'
        },
        {
          label: '11个月',
          value: '11'
        },
        {
          label: '12个月',
          value: '12'
        },
        {
          label: '13个月',
          value: '13'
        },
        {
          label: '14个月',
          value: '14'
        },
        {
          label: '15个月',
          value: '15'
        },
        {
          label: '16个月',
          value: '16'
        },
        {
          label: '17个月',
          value: '17'
        },
        {
          label: '18个月',
          value: '18'
        },
        {
          label: '19个月',
          value: '19'
        },
        {
          label: '20个月',
          value: '20'
        },
        {
          label: '21个月',
          value: '21'
        },
        {
          label: '22个月',
          value: '22'
        },
        {
          label: '23个月',
          value: '23'
        },
        {
          label: '24个月',
          value: '24'
        },
        {
          label: '25个月',
          value: '25'
        },
        {
          label: '26个月',
          value: '26'
        },
        {
          label: '27个月',
          value: '27'
        },
        {
          label: '28个月',
          value: '28'
        },
        {
          label: '29个月',
          value: '29'
        },
        {
          label: '30个月',
          value: '30'
        },
        {
          label: '31个月',
          value: '31'
        },
        {
          label: '32个月',
          value: '32'
        },
        {
          label: '33个月',
          value: '33'
        },
        {
          label: '34个月',
          value: '34'
        },
        {
          label: '35个月',
          value: '35'
        },
        {
          label: '36个月',
          value: '36'
        }
      ],
      // 拟招标方式机及组织形式
      orgTypeFirstList: [
        {
          label: '公开招标',
          value: 1
        },
        {
          label: '邀请招标',
          value: 2
        }
      ],
      orgTypeSecondList: [
        {
          label: '委托招标',
          value: 1
        },
        {
          label: '自行招标',
          value: 2
        }
      ],
      upLoad: commonJs.fileUploadUrl,
      currentOrganizationName: ''
    }
  },
  computed: {},
  // 实例创建
  created () {
    this.getCurrentDrawRecordInfo()
  },
  filters: {
    // 格式化申请时间
    filterDate (approveTime) {
      if (approveTime) {
        let date = new Date(approveTime)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    }
  },
  methods: {
    /** 带出联系人和联系电话信息 */
    getCurrentUserInfo () {
      user.getOne(this.$store.getters.authUser.userId).then((res) => {
        this.formData.contacts = res.data.user.name
        this.formData.phone = res.data.user.cellphone
      })
    },
    changeCarryTime (data) {
      if (data) {
        if (data.toString().replace('-', '').replace('-', '') < formatDate(new Date(), 'yyyyMMdd')) {
          this.$confirm('拟定实施时间不能小于当前时间', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            beforeClose: (action, instance, done) => {
              this.formData.carryTime = ''
              done()
            }
          }).then().catch(() => {
            return false
          })
        }
      }
    },
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
    /** 删除上传附件 */
    clickDel (index) {
      this.fileList.splice(index, 1)
    },
    /** 保存 */
    saveData (formName) {
      this.$refs[formName].validate((vaild) => {
        if (vaild) {
          let fileInformationList = []
          this.formData.type = this.$route.query.type
          this.formData.approveStatus = 1
          this.formData.enterpriseId = this.formData.enterpriseId !== undefined ? this.formData.enterpriseId : this.$store.getters.authUser.enterpriseId
          if (this.fileList.length > 0) {
            for (var i = 0; i < this.fileList.length; i++) {
              fileInformationList[i] = this.fileList[i]
            }
          }
          this.formData.fileInformationList = fileInformationList
          drawrecord.save(this.formData).then(() => {
            this.$router.push({path: './drawrecord'})
          })
        } else {
          return false
        }
      })
    },
    /** 提交 */
    submitData (formName) {
      this.$refs[formName].validate((vaild) => {
        if (vaild) {
          let fileInformationList = []
          this.formData.type = this.$route.query.type
          this.formData.approveStatus = 2
          this.formData.enterpriseId = this.formData.enterpriseId !== undefined ? this.formData.enterpriseId : this.$store.getters.authUser.enterpriseId
          if (this.fileList.length > 0) {
            for (var i = 0; i < this.fileList.length; i++) {
              fileInformationList[i] = this.fileList[i]
            }
          }
          this.formData.fileInformationList = fileInformationList
          drawrecord.save(this.formData).then(() => {
            this.$router.push({path: './drawrecord'})
          })
        } else {
          return false
        }
      })
    },
    /** 上传文件 */
    uploadSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        let fileInformations = []
        let existFileSize = 0
        //  得到已经保存的附件信息
        for (var i = 0; i < fileList.length; i++) {
          if (!fileList[i].response) {
            existFileSize++
            fileInformations[i] = fileList[i]
          }
        }
        // 添加新的附件信息
        if (existFileSize < fileList.length) {
          for (var j = existFileSize; j < fileList.length; j++) {
            if (fileList[j].response) {
              fileInformations[j] = fileList[j].response.fileInformation
            }
          }
        }
        this.fileList = fileInformations // 获得目前项目中所有的附件信息
        this.getUploadFileType(this.fileList)
      }
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
    getCurrentDrawRecordInfo () {
      //  判断是否为编辑操作
      if (this.$route.query.name === 'edit') {
        drawrecord.getOne(this.$route.query.objectId).then((res) => {
          if (res) {
            this.formData = res.data.selectApply
            //  获取当前中介机构的名称
            this.filterType(this.formData)
            if (this.formData.fileInformationList.length > 0) {
              let fileInformations = []
              for (var i = 0; i < this.formData.fileInformationList.length; i++) {
                fileInformations[i] = this.formData.fileInformationList[i]
              }
              this.fileList = fileInformations // 获得目前项目中所有的附件信息
              this.getUploadFileType(this.fileList)
            }
          } else {
            return false
          }
        })
      } else {
        this.getCurrentUserInfo()
        //  获取当前中介机构的名称
        this.filterType(this.$route.query)
      }
    }
  },
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
#apply-page {
  .apply-cont {
    .apply-tab-tit {
      width: 900px;
      height: 35px;
      margin: 0 auto;
      .apply-name-color {
        color: #999;
      }
    }
  }
  .apply-form {
    width: 900px;
    margin: 0 auto;
    border: 1px solid #999;
    .el-row {
      .el-form-item {
        margin-bottom: 0;
        padding: 10px;
      }
    }
    .form-row-last {
      border-bottom: 0;
    }
  }
  .apply-upload {
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
        width: 150px;
        display: block;
        font-size: 14px;
        padding: 10px 20px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
      .el-icon-error.upload-file {
        font-size: 22px;
        color: red;
        float: right;
        margin-top: -50px;
        margin-right: 20px;
        cursor: pointer;
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
  .apply-sub-btn {
    padding-top: 10px;
  }
}
</style>
