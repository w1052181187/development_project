<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="addinter-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资源管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/resource/intermange' }">中介机构管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/resource/intermange/addinter' }">{{breadDescName?'新增中介机构':'编辑'}}</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="goBack()" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 企业基本信息start -->
    <div class="enterprise-info">
      <div class="entrprise-tit"><h4>企业基本信息</h4></div>
      <div class="enterprise-form">
        <el-form :model="ruleEnterForm" :rules="rulesForm" ref="ruleEnterForm" label-width="100px" class="demo-ruleForm">
          <el-row :gutter="20">
            <el-col :span="8" style="width: 395px;">
              <el-form-item label="企业名称:" prop="name">
                <el-input v-model="ruleEnterForm.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="中介类型:" prop="type">
                <el-select v-model="ruleEnterForm.type" placeholder="请选择" style="width: 275px;">
                  <el-option
                    v-for="item in typeList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8" style="width: 395px;">
              <el-form-item label="所属区域:" prop="area">
                <el-cascader
                  style="width: 275px;"
                  expand-trigger="hover"
                  :clearable="true"
                  change-on-select
                  :options="cascaderSelectData"
                  v-model="ruleEnterForm.area"
                  @change="handlelogChange">
                </el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="状态:" prop="status">
                <el-select v-model="ruleEnterForm.status" placeholder="请选择" style="width: 275px;">
                  <el-option
                    v-for="item in statusList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8" style="width: 395px;">
              <el-form-item label="联系人:" prop="contactsName">
                <el-input v-model="ruleEnterForm.contactsName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="联系电话:" prop="phone">
                <el-input v-model="ruleEnterForm.phone" style="width: 275px;"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="16" style="width: 790px;">
              <el-form-item label="公司地址:" prop="address">
                <el-input v-model="ruleEnterForm.address"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-form-item label="企业证件:" prop="certificate" ref="certificateForm">
              <el-table
                border
                :data="ruleEnterForm.certificateTabData"
                style="width: 98%">
                <el-table-column
                  prop="certificateName"
                  label="证件名称">
                  <template slot-scope="scope">
                    <el-form-item
                      :prop="'certificateTabData.' + scope.$index + '.certificateName'"
                      :rules="[
                          { required: true, message: '证件名称不能为空'},
                          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
                        ]">
                      <span v-if="!scope.row.flagIndex">{{ruleEnterForm.certificateTabData[scope.$index].certificateName}}</span>
                      <el-input v-model="ruleEnterForm.certificateTabData[scope.$index].certificateName" placeholder="请输入内容" v-if="scope.row.flagIndex"></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="fileInformation"
                  label="证件信息(附件)">
                  <template slot-scope="scope">
                    <el-form-item
                      :prop="'certificateTabData.' + scope.$index + '.fileName'"
                      :rules="[
                          { required: true, message: '证件信息(附件)不能为空'}
                        ]">
                      <div v-show="false"><el-input v-model="ruleEnterForm.certificateTabData[scope.$index].fileName"></el-input></div>
                      <el-button
                        type="text"
                        @click="downloadFile(ruleEnterForm.certificateTabData[scope.$index].fileInformation.fileName,
                          ruleEnterForm.certificateTabData[scope.$index].fileInformation.relativePath)"
                        style="float: left;padding: 0;">
                        <span v-if="ruleEnterForm.certificateTabData[scope.$index].fileInformation" style="display:inline-block;float:left;padding-right:20px;">{{ruleEnterForm.certificateTabData[scope.$index].fileInformation.fileName}}</span>
                      </el-button>
                      <el-upload
                        class="upload-demo"
                        style="line-height: 20px"
                        :before-upload="beforeAvatarUpload"
                        :action="upload"
                        :on-change="handleChange"
                        :show-file-list="false"
                        :file-list="epFileList"
                        :on-success="uploadSuccess">
                        <el-button size="small" type="success" @click="epClick(scope.$index)" v-if="scope.row.flagIndex">{{ruleEnterForm.certificateTabData[scope.$index].fileInformation?'更换附件':'上传附件'}}</el-button>
                      </el-upload>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作"
                  align="center"
                  width="100">
                  <template slot-scope="scope">
                    <el-button @click="handleTableClick(scope.row,'confirm',scope.$index)" type="text" size="medium" v-if="scope.row.flagIndex">确定</el-button>
                    <el-button @click="handleTableClick(scope.row,'del',scope.$index)" type="text" size="medium" v-if="scope.row.flagIndex">删除</el-button>
                    <el-button @click="handleTableClick(scope.row,'edit',scope.$index)" type="text" size="medium" v-if="!scope.row.flagIndex">编辑</el-button>
                  </template>
                </el-table-column>
                <!-- 表格新增按钮 -->
              </el-table>
              <div class="certif-box">
                <el-button @click="handleAddRow" type="text" size="medium" icon="el-icon-plus" class="certif-add-btn"></el-button>
              </div>
            </el-form-item>
          </el-row>
        </el-form>
      </div>
    </div>
    <!-- 企业基本信息end -->

    <!-- 项目经理start -->
    <div class="project-manager">
      <div class="entrprise-tit"><h4>项目经理</h4></div>
      <div class="project-add-btn">
        <el-button type="warning" icon="el-icon-plus" @click="handleProjectAddClick">新增</el-button>
      </div>
      <div class="pro-manage-tab">
        <el-table
          border
          :data="projectTableData"
          :row-class-name="tableRowClassName"
          style="width: 100%;">
          <el-table-column
            label="序号"
            width="55"
            align="center"
            :index="indexMethod"
            type="index">
          </el-table-column>
          <el-table-column
            prop="name"
            label="姓名"
            align="center">
          </el-table-column>
          <el-table-column
            prop="phone"
            label="联系方式"
            align="center">
          </el-table-column>
          <el-table-column
            prop="lastUpdateDate"
            :formatter="filterUpdDate"
            label="维护时间"
            align="center">
          </el-table-column>
          <el-table-column
            prop="remark"
            label="描述"
            align="left"
            header-align="center">
          </el-table-column>
          <el-table-column
            label="操作"
            align="center">
            <template slot-scope="scope">
              <el-button @click="handleProTabClick(scope.row,'look', scope.$index)" type="text" size="small">查看</el-button>
              <el-button @click="handleProTabClick(scope.row,'edit', scope.$index)" type="text" size="small">修改</el-button>
              <el-button @click="handleProTabClick(scope.row,'del', scope.$index)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <!-- 项目经理end -->

    <!-- 法人代表start -->
    <div class="legal-repr">
      <div class="entrprise-tit"><h4>法定代表人</h4></div>
      <div class="legal-repr-form">
        <el-form :model="ruleLegalForm" :rules="rulesLegalForm" ref="ruleLegalForm" label-width="100px" class="demo-ruleForm">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="姓名:" prop="legalPerson">
                <el-input v-model="ruleLegalForm.legalPerson"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="联系方式:" prop="legalPhone">
                <el-input v-model="ruleLegalForm.legalPhone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!--<el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="身份证附件:" prop="fileInformation" v-model="ruleLegalForm.fileInformation">
                <el-upload
                  class="upload-demo"
                  :before-upload="beforeAvatarUpload"
                  :action="upload"
                  :on-change="handleCardChange"
                  :file-list="lpFileList"
                  :show-file-list="false"
                  :on-success="uploadCardSuccess">
                  <el-button size="small" type="success">上传附件</el-button>
                  <span>提示：请将法人身份证正反面扫描在同一附件进行上传</span>
                </el-upload>
                <span @click="downloadFile(ruleLegalForm.fileInformation.fileName,ruleLegalForm.fileInformation.relativePath)" style="color:#409EFF;cursor:pointer;">
                  <span v-if="ruleLegalForm.fileInformation" style="display:block;float:left;padding-right:20px;">
                    {{ruleLegalForm.fileInformation.fileName}}
                  </span>
                </span>
              </el-form-item>
            </el-col>
          </el-row>-->
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item>
                <el-button type="primary" @click="submitForm()">保存</el-button>
                <el-button @click="$router.go(-1)">取消</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <!-- 法人代表end -->

    <!-- 弹窗区域start -->
    <div class="dialog-man-box">
      <el-dialog
        :title="dialogStatusName"
        :visible.sync="dialogVisible"
        width="40%">
        <div class="manger-cont">
          <el-form :model="managerRuleForm" :rules="rules" ref="managerRuleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="姓名" prop="name">
              <span v-if="lookStatus">{{managerRuleForm.name}}</span>
              <el-input v-model="managerRuleForm.name" v-else></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone">
              <span v-if="lookStatus">{{managerRuleForm.phone}}</span>
              <el-input v-model="managerRuleForm.phone" v-else></el-input>
            </el-form-item>
            <el-form-item label="描述" prop="remark">
              <span v-if="lookStatus">{{managerRuleForm.remark}}</span>
              <el-input v-model="managerRuleForm.remark" type="textarea" v-else></el-input>
            </el-form-item>
            <el-form-item label="证书信息">
              <el-table
                :data="managerRuleForm.fileTableData"
                border
                style="width: 100%">
                <el-table-column
                  label="证件名称">
                  <template slot-scope="scope">
                    <el-form-item
                      :prop="'fileTableData.' + scope.$index + '.certificateName'"
                      :rules="[
                        { required: true, message: '证件名称不能为空'},
                        { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
                      ]">
                      <span v-if="lookStatus">{{managerRuleForm.fileTableData[scope.$index].certificateName}}</span>
                      <el-input v-model="managerRuleForm.fileTableData[scope.$index].certificateName" placeholder="请输入内容" v-else>
                      </el-input>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column
                  label="证件信息(附件)">
                  <template slot-scope="scope">
                    <el-form-item
                      :prop="'fileTableData.' + scope.$index + '.fileName'"
                      :rules="rules.fileName">
                      <!-- <div style="display:none;">
                        <el-input v-model="managerRuleForm.fileTableData[scope.$index].fileName"></el-input>
                      </div> -->
                      <span @click="downloadFile(managerRuleForm.fileTableData[scope.$index].fileInformation.fileName,managerRuleForm.fileTableData[scope.$index].fileInformation.relativePath)" style="color:#409EFF;cursor:pointer;">
                        <span v-if="managerRuleForm.fileTableData[scope.$index].fileInformation" style="display:block;float:left;padding-right:20px;">
                        {{managerRuleForm.fileTableData[scope.$index].fileInformation.fileName}}
                        </span>
                      </span>
                      <el-upload
                        v-if="!lookStatus"
                        class="upload-demo"
                        :before-upload="beforeAvatarUpload"
                        :action="upload"
                        :on-change="handleDialogChange"
                        :show-file-list="false"
                        :file-list="pmFileList"
                        :on-success="uploadDialogSuccess">
                        <el-button size="small" type="success" @click="pmClick(scope.$index)" v-if="scope.row.diaFlagIndex">{{managerRuleForm.fileTableData[scope.$index].fileInformation?'更换附件':'上传附件'}}</el-button>
                      </el-upload>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column
                  v-if="!lookStatus"
                  label="操作"
                  width="100">
                  <template slot-scope="scope">
                    <el-button @click="handleDialogClick(scope.row, 'diaConfirm',scope.$index)" type="text" size="small" v-if="scope.row.diaFlagIndex">确认</el-button>
                    <el-button @click="handleDialogClick(scope.row, 'diaDel',scope.$index)" type="text" size="small" v-if="scope.row.diaFlagIndex">删除</el-button>
                    <el-button @click="handleDialogClick(scope.row, 'diaEdit',scope.$index)" type="text" size="small" v-if="!scope.row.diaFlagIndex">编辑</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div class="certif-box" v-if="!lookStatus">
                <el-button @click="handleDialogAddRow" type="text" size="medium" icon="el-icon-plus" class="certif-add-btn"></el-button>
              </div>
            </el-form-item>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="onFormCancel" v-if="!lookStatus">取 消</el-button>
          <el-button type="primary" @click="submitDialog('managerRuleForm')" v-if="!lookStatus">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import {intermanage} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
import {formatDate} from 'common/js/date.js'
import {interclassify} from 'api/resource/intermanage/interclassify'
export default {
  data () {
    var validatePhone = (rule, value, callback) => {
      if ((value && (!(/^[1][34578]\d{9}$/).test(value) || !(/^[1-9]\d*$/).test(value) || value.length !== 11)) &&
        (value && (!(/^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/).test(value)))) {
        callback(new Error('录入格式不正确，请重新录入！'))
      } else {
        callback()
      }
    }
    return {
      cascaderSelectData: interclassify, // 省市联动数据
      lookStatus: false,
      dialogStatusName: '新增',
      flag: 0, // 项目经理操作：新增：0 修改：1
      type: this.$route.query.type, // 新增/修改
      upload: commonJs.fileUploadUrl,
      epFileList: [],
      pmFileList: [],
      lpFileList: [],
      pmIndex: -1,
      epIndex: -1,
      managerRuleForm: {
        fileTableData: []
      },
      certificatesPmData: [], // 保存确认后的项目经理证件信息
      certificatesEpData: [], // 保存确认后的企业证件信息
      dialogVisible: false,
      currentPage: 1,
      ruleEnterForm: {
        status: 1,
        certificateTabData: []
      }, // 企业基本信息表单
      rules: {
        name: [
          { required: true, message: '请填写姓名', trigger: 'blur' },
          { min: 1, max: 10, message: '请输入1~10个字符', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请填写联系方式', trigger: 'blur' },
          { validator: validatePhone, trigger: 'blur' }
        ],
        fileName: [
          { required: true, message: '证件信息(附件)不能为空', trigger: 'change'}
        ],
        remark: [
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: 'blur' }
        ]
      }, // 项目经理信息验证规则
      rulesForm: {
        name: [
          { required: true, message: '请填写企业名称', trigger: 'blur' },
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择中介类型', trigger: 'change' }
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'change' }
        ],
        address: [
          { required: true, message: '请填写公司地址', trigger: 'blur' },
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: 'blur' }
        ],
        contactsName: [
          { required: true, message: '请填写联系人', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请填写联系方式', trigger: 'blur' },
          { validator: validatePhone, trigger: 'blur' }
        ],
        area: [
          { required: true, message: '请选择所属区域', trigger: 'change' }
        ],
        certificate: [
          { required: true, message: '请填写企业证件', trigger: 'blur' }
        ]
      }, // 企业基本信息验证规则
      rulesLegalForm: {
        legalPerson: [
          { required: true, message: '请填写姓名', trigger: 'blur' },
          { min: 1, max: 10, message: '请输入1~10个字符', trigger: 'blur' }
        ],
        legalPhone: [
          { required: true, message: '请填写联系方式', trigger: 'blur' },
          { validator: validatePhone, trigger: 'blur' }
        ]
      }, // 法人代表验证规则
      projectTableData: [], // 项目
      ruleLegalForm: {}, // 法人代表表单
      typeList: [
        {
          label: '招标代理机构',
          value: 4
        },
        {
          label: '造价咨询机构',
          value: 5
        },
        {
          label: '审计评估机构',
          value: 6
        },
        {
          label: '法律服务机构',
          value: 7
        }],
      statusList: [
        {
          label: '激活',
          value: 0
        },
        {
          label: '锁定',
          value: 1
        }]
    }
  },
  computed: {
    breadDescName () {
      let type = this.$route.query.type
      if (type === 'add') {
        return true
      } else {
        return false
      }
    }
  },
  created () {
    if (this.breadDescName) {
    } else {
      this.ruleEnterForm.certificateTabData = []
    }
  },

  /*
  * 方法集
  */
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    filterUpdDate (row, column) {
      if (!row.lastUpdateDate) {
        return ''
      } else {
        let date = new Date(row.lastUpdateDate)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    onFormCancel () {
      this.dialogVisible = false
      this.$refs['managerRuleForm'].resetFields()
    },
    // 所属区域
    handlelogChange () {},
    /**
     * 上传改变
     */
    handleChange (file, fileList) {
      if (fileList.length > 1) {
        this.epFileList = []
      }
      this.epFileList.push(file)
    },
    handleDialogChange (file, fileList) {
      if (fileList.length > 1) {
        this.pmFileList = []
      }
      this.$refs.managerRuleForm.clearValidate()
      this.pmFileList.push(file)
    },
    handleCardChange (file, fileList) {
      if (fileList.length > 1) {
        this.lpFileList = []
      }
      this.lpFileList = fileList.slice(-1)
    },
    /*
     * 下载
     */
    downloadFile,
    handleDialogAddRow () {
      let obj = {
        certificateName: '',
        diaFlagIndex: 1
      }
      this.managerRuleForm.fileTableData.push(obj)
    },
    /*
    * 新增-表格按钮
    */
    handleAddRow () {
      let obj = {
        certificateName: '',
        flagIndex: 1,
        fileName: ''
      }
      this.ruleEnterForm.certificateTabData.push(obj)
    },
    /** 上传限制 */
    beforeAvatarUpload (file) {
      const isLtM = file.size / 1024 / 1024 <= 5
      if (!isLtM) {
        this.$message.error('上传附件大小不能超过5M!')
      }
      return isLtM
    },
    /**
     * 企业证件上传
     */
    uploadSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.ruleEnterForm.certificateTabData[this.epIndex].fileInformation = file.response.fileInformation
        this.$set(this.ruleEnterForm.certificateTabData[this.epIndex], 'fileName', file.response.fileInformation.fileName)
      }
    },
    /**
     *  法人代表证件上传
     */
    uploadCardSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.ruleLegalForm.fileInformation = file.response.fileInformation
      }
    },
    /**
     * 项目经理证件上传
     */
    uploadDialogSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.managerRuleForm.fileTableData[this.pmIndex].fileInformation = file.response.fileInformation
        this.$set(this.managerRuleForm.fileTableData[this.pmIndex], 'fileName', file.response.fileInformation.fileName)
      }
    },
    /**
     * 获取当前行index
     */
    pmClick (index) {
      this.pmIndex = index
    },
    epClick (index) {
      this.epIndex = index
    },
    /**
     * 项目经理弹窗表格-操作
     */
    handleDialogClick (row, type, index) {
      if (type === 'diaEdit') {
        this.$set(this.managerRuleForm.fileTableData[index], 'diaFlagIndex', 1)
      } else if (type === 'diaConfirm') {
        let certificateName = row.certificateName || null
        let fileInformation = row.fileInformation || null
        if (certificateName !== null && fileInformation !== null) {
          if (this.certificatesPmData[this.certificatesPmData.indexOf(row)]) {
            this.certificatesPmData.splice(this.certificatesPmData.indexOf(row), 1)
          }
          this.certificatesPmData.push(row)
          this.certificatesPmData[this.certificatesPmData.indexOf(row)].certificatesType = 2
          this.$set(this.managerRuleForm.fileTableData[index], 'diaFlagIndex', 0)
        } else {
          this.$set(this.managerRuleForm.fileTableData[index], 'diaFlagIndex', 1)
          this.$message.error('请填写证件名称并上传附件！')
        }
        // 校验是否录入证书信息
        this.validManagerFile()
      } else if (type === 'diaDel') {
        this.managerRuleForm.fileTableData.splice(this.managerRuleForm.fileTableData.indexOf(row), 1)
        if (this.certificatesPmData[this.certificatesPmData.indexOf(row)]) {
          this.certificatesPmData.splice(this.certificatesPmData.indexOf(row), 1)
        }
        // 校验是否录入证书信息
        this.validManagerFile()
      }
    },
    /*
    * 表格操作-企业基本信息
    */
    handleTableClick (row, type, index) {
      if (type === 'edit') {
        this.$set(this.ruleEnterForm.certificateTabData[index], 'flagIndex', 1)
      } else if (type === 'confirm') {
        let certificateName = row.certificateName || null
        let fileInformation = row.fileInformation || null
        if (certificateName !== null && fileInformation !== null) {
          if (this.certificatesEpData[this.certificatesEpData.indexOf(row)]) {
            this.certificatesEpData.splice(this.certificatesEpData.indexOf(row), 1)
          }
          this.certificatesEpData.push(row)
          this.certificatesEpData[this.certificatesEpData.indexOf(row)].certificatesType = 2
          this.$set(this.ruleEnterForm.certificateTabData[index], 'flagIndex', 0)
        } else {
          this.$set(this.ruleEnterForm.certificateTabData[index], 'flagIndex', 1)
          this.$message.error('请填写证件名称并上传附件！')
        }
        // 校验是否录入证书信息
        this.validCertificates()
      } else if (type === 'del') {
        this.ruleEnterForm.certificateTabData.splice(this.ruleEnterForm.certificateTabData.indexOf(row), 1)
        if (this.certificatesEpData[this.certificatesEpData.indexOf(row)]) {
          this.certificatesEpData.splice(this.certificatesEpData.indexOf(row), 1)
        }
        // 校验是否录入证书信息
        this.validCertificates()
      }
    },
    /*
    * 表格操作-项目经理
    */
    handleProTabClick (row, type, index) {
      if (type === 'look') {
        this.lookClick(row)
      } else if (type === 'edit') {
        this.editClick(row, index)
      } else if (type === 'del') {
        this.deleteClick(row)
      }
    },
    /*
     * 表单验证
     */
    checkForm () {
      let result = true
      this.$refs['ruleEnterForm'].validate((vaild) => {
        if (!vaild) {
          result = false
        }
      })
      this.$refs['ruleLegalForm'].validate((vaild) => {
        if (!vaild) {
          result = false
        }
      })
      return result
    },
    /**
     * 校验是否填写企业证件
     */
    validCertificates () {
      if (this.certificatesEpData.length > 0) {
        this.$set(this.ruleEnterForm, 'certificate', 'certificate')
      } else {
        this.$set(this.ruleEnterForm, 'certificate', '')
      }
      if (this.ruleEnterForm.certificate) {
        this.$refs['certificateForm'].clearValidate()
      }
    },
    /**
     * 校验是否填写项目经理证书信息
     */
    validManagerFile () {
      if (this.certificatesPmData.length > 0) {
        this.$set(this.managerRuleForm, 'certificate', 'certificate')
      } else {
        this.$set(this.managerRuleForm, 'certificate', '')
      }
      if (this.managerRuleForm.certificate) {
        this.$refs['managerRuleForm'].clearValidate()
      }
    },
    /*
    * 保存
    */
    submitForm () {
      let result = this.checkForm()
      if (result) {
        this.ruleEnterForm.shortName = this.ruleEnterForm.name
        this.ruleEnterForm.enterpriseExpend = this.ruleLegalForm
        if (this.ruleEnterForm.area) {
          this.ruleEnterForm.enterpriseExpend.provinceId = this.ruleEnterForm.area[0]
          this.ruleEnterForm.enterpriseExpend.cityId = this.ruleEnterForm.area[1]
          this.ruleEnterForm.enterpriseExpend.countyId = this.ruleEnterForm.area[2]
        }
        this.ruleEnterForm.enterpriseExpend.address = this.ruleEnterForm.address
        this.ruleEnterForm.enterpriseExpend.phone = this.ruleEnterForm.phone
        this.ruleEnterForm.enterpriseExpend.contactsName = this.ruleEnterForm.contactsName
        if (this.ruleLegalForm.fileInformation) {
          let obj = {
            certificateName: ''
          }
          this.certificatesEpData.push(obj)
          let length = this.certificatesEpData.length
          this.certificatesEpData[length - 1].certificatesType = 1
          this.certificatesEpData[length - 1].certificateName = '身份证'
          this.certificatesEpData[length - 1].fileInformation = this.ruleLegalForm.fileInformation
        }
        this.ruleEnterForm.certificatesList = this.certificatesEpData
        this.ruleEnterForm.projectManagerList = this.projectTableData
        this.ruleEnterForm.resourceAdd = true
        if (this.type === 'add') {
          // 新增
          intermanage.saveEnterprise(this.ruleEnterForm).then(() => {
            this.goBack()
          })
        } else if (this.type === 'edit') {
          // 修改
          intermanage.updateEnterprise(this.ruleEnterForm).then(() => {
            this.goBack()
          })
        }
      } else {
        return false
      }
    },
    /*
     * 返回
     */
    goBack () {
      this.$router.push({path: '/admin/resource/intermange', query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize, operation: this.type, interStatus: true}})
    },
    /**
     * 项目经理新增
     */
    handleProjectAddClick () {
      // 设置状态
      this.lookStatus = false
      // 打开弹窗
      this.dialogVisible = true
      this.flag = 0
      this.dialogStatusName = '新增'
      this.managerRuleForm = {}
      this.managerRuleForm.fileTableData = []
      this.certificatesPmData = []
    },
    /*
     * 项目经理确定按钮
     */
    submitDialog (val) {
      // this.validManagerFile()
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.managerRuleForm.certificatesList = this.certificatesPmData
          if (this.flag === 0) {
            this.projectTableData.push(this.managerRuleForm)
          } else if (this.flag === 1) {
            this.projectTableData[this.managerRuleForm.index].certificatesList = this.certificatesPmData
          }
          this.dialogVisible = false
        } else {
          return false
        }
      })
    },
    /*
     * 项目经理查看按钮
     */
    lookClick (data) {
      // 打开弹窗
      this.dialogVisible = true
      // 设置状态
      this.lookStatus = true
      this.dialogStatusName = '查看'
      this.managerRuleForm = data
      this.managerRuleForm.fileTableData = data.certificatesList
    },
    /*
     * 项目经理修改按钮
     */
    editClick (data, index) {
      this.dialogVisible = true
      this.dialogStatusName = '编辑'
      // 设置状态
      this.lookStatus = false
      this.flag = 1
      this.managerRuleForm = data
      this.managerRuleForm.index = index
      this.managerRuleForm.fileTableData = data.certificatesList
      this.certificatesPmData = data.certificatesList
      if (this.managerRuleForm.fileTableData) {
        if (this.managerRuleForm.fileTableData.length > 0) {
          this.$set(this.managerRuleForm, 'certificate', 'certificate')
          for (let i = 0; i < this.managerRuleForm.fileTableData.length; i++) {
            this.managerRuleForm.fileTableData[i].fileName = this.managerRuleForm.fileTableData[i].fileInformation.fileName
          }
        }
      }
    },
    /*
    * 项目经理删除按钮
    */
    deleteClick (data) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.projectTableData.splice(this.projectTableData.indexOf(data), 1)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    getEnterprise () {
      intermanage.getEnterprise(this.$route.query.objectId).then((res) => {
        if (res.data.enterprise) {
          let enterprise = res.data.enterprise
          this.ruleEnterForm = enterprise
          if (enterprise.enterpriseExpend) {
            this.ruleEnterForm.area = [enterprise.enterpriseExpend.provinceId, enterprise.enterpriseExpend.cityId, enterprise.enterpriseExpend.countyId]
            this.ruleEnterForm.address = enterprise.enterpriseExpend.address
            this.ruleEnterForm.phone = enterprise.enterpriseExpend.phone
            this.ruleEnterForm.contactsName = enterprise.enterpriseExpend.contactsName
            this.ruleLegalForm = enterprise.enterpriseExpend
          }
          this.projectTableData = enterprise.projectManagerList
          if (enterprise.certificatesList) {
            this.ruleEnterForm.certificateTabData = []
            for (let i = 0; i < enterprise.certificatesList.length; i++) {
              // 法人代表身份证信息
              if (enterprise.certificatesList[i].certificatesType === 1) {
                this.ruleLegalForm.fileInformation = enterprise.certificatesList[i].fileInformation
              } else {
                this.ruleEnterForm.certificateTabData.push(enterprise.certificatesList[i])
                this.certificatesEpData.push(enterprise.certificatesList[i])
              }
            }
          }
          if (this.ruleEnterForm.certificateTabData) {
            if (this.ruleEnterForm.certificateTabData.length > 0) {
              this.$set(this.ruleEnterForm, 'certificate', 'certificate')
              for (let i = 0; i < this.ruleEnterForm.certificateTabData.length; i++) {
                this.ruleEnterForm.certificateTabData[i].fileName = this.ruleEnterForm.certificateTabData[i].fileInformation.fileName
              }
            }
          }
        } else {
          return false
        }
      })
    }
  },
  /*
  * 实例创建完成
  */
  mounted () {
    if (this.type === 'add') {
      // 新增
    } else if (this.type === 'edit') {
      // 修改
      this.getEnterprise()
    }
  },
  /*
  * 过滤
  */
  filters: {}
}
</script>
<style lang="less">
#addinter-page {
  .el-form-item__error {
    position: relative;
  }
  .enterprise-info, .project-manager, .legal-repr {
    margin: 20px;
    background: #fff;
    .entrprise-tit {
      height: 40px;
      background: #F7F7F9;
      text-align: left;
      h4 {
        padding-top: 10px;
        padding-left: 25px;
      }
    }
    .enterprise-form {
      padding-top: 20px;
      .el-form-item__content {
        text-align: left;
      }
    }
  }
  /*基本信息*/
  .enterprise-info .certif-box, .dialog-man-box .certif-box {
    background-color: #ECEFFE;
    text-align: center;
    .certif-add-btn {
      width: 100%;
    }
  }
  .dialog-man-box {
    .el-dialog__footer {
      text-align: center;
    }
    .el-form-item__content {
      text-align: left;
    }
  }
  /*项目经理*/
  .project-manager {
    .project-add-btn {
      text-align: left;
      padding: 20px;
    }
    .pro-manage-tab {
      margin: 0 20px;
      padding-bottom: 20px;
    }
  }
  /*法人代表*/
  .legal-repr {
    margin-bottom: 130px;
  }
  .legal-repr .legal-repr-form {
    padding-top: 20px;
  }
}
</style>
