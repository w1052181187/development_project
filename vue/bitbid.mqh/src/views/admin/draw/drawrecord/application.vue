<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="apply-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/apply' }">选聘申请表</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 表单模块区域 -->
    <div class="maincont-box apply-cont">
      <h2>太原煤气化公司招标备案表</h2>
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
        <el-form :model="formData" :rules="formRules" ref="formData" label-width="150px" class="demo-ruleForm">
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
            <el-col :span="24">
              <el-form-item label="招标项目地址" prop="projectAddress">
                <el-input v-model="formData.projectAddress"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="招标项目名称" prop="projectName">
                <el-input v-model="formData.projectName"></el-input>
               <!-- <el-select v-model="formData.projectName" placeholder="请选择" style="width:100%;">
                  <el-option
                    v-for="item in tendProjectList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>-->
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="计划工期(月)">
                <span style="margin-left: -180px">{{formData.timeLimit}}</span>
                <!--<el-select v-model="formData.timeLimit" placeholder="请选择" style="width:100%;">
                  <el-option
                    v-for="item in planTimeList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>-->
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="立项批准文件" prop="approvalFile">
                <el-input v-model="formData.approvalFile"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="(概算)计划额度(万元)">
                <!--<el-input v-model="formData.planQuota"></el-input>-->
                <span style="margin-left: -180px">{{formData.planQuota}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="拟招标时间">
                <span style="margin-left: -180px">{{formData.biddingTime | filterDate}}</span>
               <!-- <el-date-picker
                  v-model="formData.biddingTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  style="width:100%;"
                  @change="changeBiddingTime">
                </el-date-picker>-->
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="计划开工(到货)时间">
                <span style="margin-left: -180px">{{formData.plannedTime | filterDate}}</span>
                <!--<el-date-picker
                  v-model="formData.plannedTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  style="width:100%;"
                  @change="changePlannedTime">
                </el-date-picker>-->
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="拟招标方式">
                <span style="margin-left: -180px">{{formData.biddingType | filteredType}}</span>
               <!-- <el-select v-model="formData.biddingType" placeholder="请选择" style="width:100%;">
                  <el-option
                    v-for="item in orgTypeFirstList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>-->
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="拟招标组织形式">
                <span style="margin-left: -180px">{{formData.organizationalType | filteredOrgan}}</span>
                 <!--<el-select v-model="formData.organizationalType" disabled placeholder="请选择" style="width:100%;">
                  <el-option
                    v-for="item in orgTypeSecondList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>-->
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="依法必须招标" prop="isAccordToLaw">
                <el-radio-group v-model="formData.isAccordToLaw" style="margin-left: -150px">
                  <el-radio :label="1">是</el-radio>
                  <el-radio :label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="是否电子招标" prop="isElectronicBidding">
                <el-radio-group v-model="formData.isElectronicBidding" style="margin-left: -150px">
                  <el-radio :label="1">是</el-radio>
                  <el-radio :label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="施工设计和技术方案(设备参数)批注文件" prop="executionFile">
                <el-input type="textarea" v-model="formData.executionFile"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="招标内容简介" prop="content">
                <el-input type="textarea" v-model="formData.content"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20" class="form-row-last">
            <el-col :span="24">
              <el-form-item label="招标最高限价" prop="highestPrice">
                <el-input type="textarea" v-model="formData.highestPrice"></el-input>
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
                    <!--<img src="../../../../common/images/upload-icon/doc.png" alt="">-->
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
import {drawrecord, planproject, user} from 'api/index'
import {commonJs} from 'common/js/common'
import {fileIcon} from 'common/js/base'
import {isvalidPhone, isvalidPrice} from '../../../../assets/js/validate'
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
    let validPrice = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPrice(value)) {
        callback(new Error('请输入数字,最多两位小数'))
      } else {
        callback()
      }
    }
    return {
      formData: {
        contacts: '',
        phone: '',
        projectName: '',
        organizationalType: 1,
        type: 1,
        isAccordToLaw: 1,
        isElectronicBidding: 1
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
        projectAddress: [
          { required: true, message: '请输入招标项目地址', trigger: 'blur' },
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ],
        projectName: [
          { required: true, message: '请输入项目名称', trigger: 'change' },
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ],
        timeLimit: [
          { required: true, message: '请输入计划工期', trigger: 'change' }
        ],
        planQuota: [
          { required: true, message: '请输入(概算)计划额度', trigger: 'change' },
          {validator: validPrice, trigger: 'change'}
        ],
        biddingTime: [
          { required: true, message: '请输入拟招标时间', trigger: 'change' }
        ],
        plannedTime: [
          { required: true, message: '请输入计划开工(到货)时间', trigger: 'change' }
        ],
        biddingType: [
          { required: true, message: '请输入拟招标方式', trigger: 'change' }
        ],
        organizationalType: [
          { required: true, message: '请输入拟招标组织形式', trigger: 'change' }
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
          { required: true, message: '请输入招标内容简介', trigger: 'blur' },
          {min: 1, max: 500, message: '请输入1~500个字符', trigger: ['blur', 'change']}
        ],
        highestPrice: [
          { required: true, message: '请输入招标最高限价', trigger: 'blur' },
          {validator: validPrice, trigger: 'blur'}
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
        },
        {
          label: '项目七',
          value: '项目七'
        }
      ],
      // 计划工期
      planTimeId: '',
      /* planTimeList: [
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
      ], */
      planTimeList: this.getPlanTimeList(),
      // 拟招标方式机及组织形式
      orgTypeFirstList: [
        {
          label: '公开招标',
          value: 1
        },
        {
          label: '邀请招标',
          value: 2
        },
        {
          label: '谈判采购',
          value: 3
        },
        {
          label: '询比采购',
          value: 4
        },
        {
          label: '竞价采购',
          value: 5
        },
        {
          label: '直接采购',
          value: 6
        },
        {
          label: '框架协议',
          value: 7
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
      upLoad: commonJs.fileUploadUrl
    }
  },
  computed: {},
  // 实例创建
  created () {
    this.getCurrentDrawRecordInfo()
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
    // 格式化申请时间
    filterDate (approveTime) {
      if (approveTime) {
        let date = new Date(approveTime)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    },
    filteredOrgan (value) {
      if (value === 1) {
        return '委托招标'
      } else {
        return '自行招标'
      }
    }
  },
  methods: {
    getPlanTimeList () {
      let arr = []
      for (let i = 1; i < 25; i++) {
        let obj = {
          label: i + '个月',
          value: i
        }
        arr.push(obj)
      }
      return arr
    },
    changePlannedTime (data) {
      if (data) {
        if (data.toString().replace('-', '').replace('-', '') < formatDate(new Date(), 'yyyyMMdd')) {
          this.$confirm('选择的时间不能小于当前时间', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            beforeClose: (action, instance, done) => {
              this.formData.plannedTime = ''
              done()
            }
          }).then().catch(() => {
            return false
          })
        }
      }
    },
    changeBiddingTime (data) {
      if (data) {
        if (data.toString().replace('-', '').replace('-', '') < formatDate(new Date(), 'yyyyMMdd')) {
          this.$confirm('选择的时间不能小于当前时间', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            beforeClose: (action, instance, done) => {
              this.formData.biddingTime = ''
              done()
            }
          }).then().catch(() => {
            return false
          })
        }
      }
    },
    /** 带出联系人和联系电话信息 */
    getCurrentUserInfo () {
      user.getOne(this.$store.getters.authUser.userId).then((res) => {
        this.formData.contacts = res.data.user.name
        this.formData.phone = res.data.user.cellphone
      })
    },
    getPlanNameInfo () {
      planproject.getOne(this.$route.query.bidPlanId).then((res) => {
        this.formData.projectName = res.data.projectPlan.projectPlanName
        this.formData.biddingType = res.data.projectPlan.bidType
        this.formData.timeLimit = res.data.projectPlan.planLimit
        this.formData.planQuota = res.data.projectPlan.planTotalAmount
        this.formData.biddingTime = res.data.projectPlan.bidStartTime
        this.formData.plannedTime = res.data.projectPlan.executeStartTime
        // 招标计划对应的附件信息
        if (res.data.projectPlan.fileInformationList.length > 0) {
          res.data.projectPlan.fileInformationList.forEach(item => {
            item.objectId = null
            item.businessType = 3
            this.fileList.push(item)
          })
          this.getUploadFileType(this.fileList)
        }
      })
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
          this.formData.approveStatus = 1
          this.formData.projectId = this.formData.projectId !== undefined ? this.formData.projectId : this.$route.query.bidPlanId
          this.formData.enterpriseId = this.formData.enterpriseId !== undefined ? this.formData.enterpriseId : this.$store.getters.authUser.enterpriseId
          if (this.fileList.length > 0) {
            for (let i = 0; i < this.fileList.length; i++) {
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
          this.formData.approveStatus = 2
          this.formData.projectId = this.formData.projectId !== undefined ? this.formData.projectId : this.$route.query.bidPlanId
          this.formData.enterpriseId = this.formData.enterpriseId !== undefined ? this.formData.enterpriseId : this.$store.getters.authUser.enterpriseId
          if (this.fileList.length > 0) {
            for (let i = 0; i < this.fileList.length; i++) {
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
        for (let i = 0; i < fileList.length; i++) {
          if (!fileList[i].response) {
            existFileSize++
            fileInformations[i] = fileList[i]
          }
        }
        // 添加新的附件信息
        if (existFileSize < fileList.length) {
          for (let j = existFileSize; j < fileList.length; j++) {
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
            if (this.formData.fileInformationList.length > 0) {
              let fileInformations = []
              for (let i = 0; i < this.formData.fileInformationList.length; i++) {
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
        this.getPlanNameInfo()
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
      height: 40px;
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
      margin-bottom: 30px;
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
        display: block;
        font-size: 14px;
        padding: 5px 20px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
      .el-icon-error.upload-file {
        font-size: 22px;
        color: red;
        float: right;
        margin-top: -20px;
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
  .el-upload-dragger {
    width: 280px;
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
