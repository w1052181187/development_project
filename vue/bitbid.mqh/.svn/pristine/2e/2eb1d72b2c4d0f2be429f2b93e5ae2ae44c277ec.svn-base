<template>
  <!-- 标段文件-其他采购组件 -->
  <div class="admin-layout" id="otherfile-page">
    <div class="collapse-warp">
      <el-collapse v-model="activeName" accordion>
        <el-collapse-item  v-for="(section, index) in sectionData" :name=section.code :key="index">
          <template slot="title">
            <span  @click="initializeData(section, index)" style="width:100%;display:block;position:relative;z-index:1;">{{section.name}}
              <span>{{section.archivingStatus | filterStatus}}</span>
              <el-button
                type="text"
                @click.stop="submitCilck(section)"
                style="float: right;margin-right: 40px;line-height: 25px;"
                v-if="section.archivingStatus !== 2 && $route.query.operation !== 'look'">
                提交
              </el-button>
            </span>
          </template>
          <div class="collspase-cont">
            <el-form  v-loading="loading" :model="archivesExtrasForm" :rules="archivesExtrasRules" ref="archivesExtrasForm" label-width="120px" class="demo-ruleForm tabs-pane-form">
              <table class="info-table">
                <tr>
                  <td>单位名称</td>
                  <td colspan="3">{{nameForm.projectEnterpriseName}}</td>
                </tr>
                <tr>
                  <td>授权委托人</td>
                  <td>
                    <el-form-item prop="fileDelegateList" ref="fileDelegateList">
                      <el-upload
                        class="upload-demo"
                        :action="upLoad"
                        :on-success="handleDelegateSuccess"
                        :show-file-list="false"
                        :auto-upload="true"
                        :file-list="fileDelegateList">
                        <el-button size="small" type="success" v-if="dialogEdit">点击上传</el-button>
                        <span>只可上传一个附件</span>
                      </el-upload>
                      <span class="file-list" v-for="(item, index) in fileDelegateList" :key="index" v-if="item.fileName || false">
                      <el-button
                        type="text"
                        style="padding-right: 20px;"
                        @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}
                      </el-button>
                    </span>
                    </el-form-item>
                  </td>
                  <td>授权书</td>
                  <td>
                  <el-form-item prop="fileAccreditList" ref="fileAccreditList">
                    <el-upload
                      class="upload-demo"
                      :action="upLoad"
                      :on-success="handleAccreditSuccess"
                      :show-file-list="false"
                      :auto-upload="true"
                      :file-list="fileAccreditList">
                      <el-button size="small" type="success" v-if="dialogEdit">点击上传</el-button>
                      <span>只可上传一个附件</span>
                    </el-upload>
                    <span class="file-list" v-for="(item, index) in fileAccreditList" :key="index" v-if="item.fileName || false">
                    <el-button
                      type="text"
                      style="padding-right: 20px;"
                      @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}
                    </el-button>
                  </span>
                  </el-form-item>
                  </td>
                </tr>
                <tr>
                  <td>单位资质证件</td>
                  <td>
                   <el-form-item prop="fileAptitudeList" ref="fileAptitudeList">
                     <el-upload
                       class="upload-demo"
                       :action="upLoad"
                       :on-success="handleAptitudeSuccess"
                       :file-list="fileAptitudeList"
                       :show-file-list="false">
                       <el-button size="small" type="success" class="upload-btn" v-if="dialogEdit">上传文件</el-button>
                     </el-upload>
                     <span class="file-list" v-for="(item, index) in fileAptitudeList" :key="index" v-if="item.fileName || false">
                      <el-button
                        type="text"
                        style="padding-right: 20px;width: 80%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;display: inline-block;text-align: left;"
                        @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}
                      </el-button>
                      <i class="el-icon-error upload-file" @click="clickDel(index)" v-if="dialogEdit"></i>
                     </span>
                   </el-form-item>
                  </td>
                  <td>谈判记录</td>
                  <td>
                    <el-form-item prop="fileNegotiateList" ref="fileNegotiateList">
                      <el-upload
                        class="upload-demo"
                        :action="upLoad"
                        :on-success="handleNegotiateSuccess"
                        :show-file-list="false"
                        :auto-upload="true"
                        :before-upload="beforeNegotiateUpload"
                        :file-list="fileNegotiateList">
                        <el-button size="small" type="success" v-if="dialogEdit">上传文件</el-button>
                      </el-upload>
                      <span class="file-list" v-for="(item, index) in fileNegotiateList" :key="index" v-if="item.fileName || false">
                        <el-button
                          type="text"
                          style="padding-right: 20px;"
                          @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}
                        </el-button>
                        <i class="el-icon-error upload-file" @click="clickDelFile(index)" v-if="dialogEdit"></i>
                      </span>
                    </el-form-item>
                  </td>
                </tr>
                <tr>
                  <td>合同金额</td>
                  <td>
                    <el-form-item prop='contractAmount'>
                      <el-input v-model="archivesExtrasForm.contractAmount" type="text" style="width:200px;"  v-if="dialogEdit"></el-input>
                      <span v-else>{{archivesExtrasForm.contractAmount}}</span>万元
                    </el-form-item>
                  </td>
                  <td>工期</td>
                  <td>
                    <el-form-item prop="timeLimit">
                      <el-input v-model="archivesExtrasForm.timeLimit" type="text" style="width:200px;" v-if="dialogEdit"></el-input>
                      <span v-else>{{archivesExtrasForm.timeLimit}}</span>个月
                    </el-form-item>
                  </td>
                </tr>
                <tr>
                  <td>质保金</td>
                  <td>
                    <el-form-item prop="warrantyGold">
                      <el-input v-model="archivesExtrasForm.warrantyGold" type="text" style="width:200px;"  v-if="dialogEdit"></el-input>
                      <span v-else>{{archivesExtrasForm.warrantyGold}}</span>万元
                    </el-form-item>
                  </td>
                  <td>付款方式</td>
                  <td>
                    <el-form-item prop="payMethod">
                      <el-input  v-model="archivesExtrasForm.payMethod" type="text" style="width:200px;"  v-if="dialogEdit"></el-input>
                      <span v-else>{{archivesExtrasForm.payMethod}}</span>
                    </el-form-item>
                  </td>
                </tr>
                <tr>
                  <td>质保期</td>
                  <td colspan="3">
                    <el-form-item prop="warrantyPeriod">
                      <el-input  v-model="archivesExtrasForm.warrantyPeriod" type="text" style="width:200px;"  v-if="dialogEdit"></el-input>
                      <span v-else>{{archivesExtrasForm.warrantyPeriod}}</span>个月
                    </el-form-item>
                  </td>
                </tr>
                <tr  v-if="tenderProjectType === 1">
                  <td>项目经理</td>
                  <td>
                    <el-form-item prop="projectManager">
                      <el-input  v-model="archivesExtrasForm.projectManager" type="text" style="width:200px;" v-if="dialogEdit"></el-input>
                      <span v-else>{{archivesExtrasForm.projectManager}}</span>
                    </el-form-item>
                  </td>
                  <td>结算依据</td>
                  <td>
                    <el-form-item prop="basisSettlement">
                      <el-input  v-model="archivesExtrasForm.basisSettlement" type="text" style="width:200px;"  v-if="dialogEdit"></el-input>
                      <span v-else>{{archivesExtrasForm.basisSettlement}}</span>
                    </el-form-item>
                  </td>
                </tr>
                <tr v-if="tenderProjectType === 2">
                  <td>规格，型号，数量</td>
                  <td>
                    <el-form-item prop="goodsExplain">
                      <el-input  v-model="archivesExtrasForm.goodsExplain" type="text" style="width:200px;" v-if="dialogEdit"></el-input>
                      <span v-else>{{archivesExtrasForm.goodsExplain}}</span>
                    </el-form-item>
                  </td>
                  <td>供货期</td>
                  <td>
                    <el-form-item prop="supplyPeriod">
                      <el-input  v-model="archivesExtrasForm.supplyPeriod" type="text" style="width:200px;" v-if="dialogEdit"></el-input>
                      <span v-else>{{archivesExtrasForm.supplyPeriod}}</span>个月
                    </el-form-item>
                  </td>
                </tr>
                <tr v-if="tenderProjectType === 3">
                  <td>服务期</td>
                  <td>
                    <el-form-item prop="serverPeriod">
                      <el-input  v-model="archivesExtrasForm.serverPeriod" type="text" style="width:200px;"  v-if="dialogEdit"></el-input>
                      <span v-else>{{archivesExtrasForm.serverPeriod}}</span>个月
                    </el-form-item>
                  </td>
                </tr>
              </table>
            </el-form>
            <div class="tabpane-btn">
              <el-button type="primary"  @click="saveArchivesExtras('archivesExtrasForm', index)" v-if="dialogEdit">保 存</el-button>
            </div>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>
<script>
import {section, tenderproject, sectionArchivesExtras} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
export default {
  data () {
    var validateContractAmount = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.\d{1,6})?$/).test(value))) {
        callback(new Error('合同金额只能输入数值型'))
      } else {
        callback()
      }
    }
    var validateWarrantyGold = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.\d{1,6})?$/).test(value))) {
        callback(new Error('质保金只能输入数值型'))
      } else {
        callback()
      }
    }
    return {
      activeName: '', // 当前展开面板code
      openingTime: null, // 开标时间
      dialogEdit: false, // 是否编辑
      sectionData: [], // 标段
      nameForm: {}, // 单位名称
      tenderProjectType: '', // 工程/货物/服务
      archivesExtrasForm: {}, // 表单
      archivesExtrasRules: {
        fileDelegateList: [
          {required: true, message: '请上传授权委托人文件', trigger: 'blur'}
        ],
        fileAccreditList: [
          {required: true, message: '请上传授权人文件', trigger: 'blur'}
        ],
        fileAptitudeList: [
          {required: true, message: '请上传单位资质文件', trigger: 'blur'}
        ],
        fileNegotiateList: [
          {required: true, message: '请上传谈判记录文件', trigger: 'blur'}
        ],
        contractAmount: [
          {required: true, message: '请输入合同金额', trigger: 'blur'},
          {validator: validateContractAmount}
        ],
        timeLimit: [
          {required: true, message: '请输入工期', trigger: 'blur'}
        ],
        warrantyGold: [
          {required: true, message: '请输入质保金', trigger: 'blur'},
          {validator: validateWarrantyGold}
        ],
        payMethod: [
          {required: true, message: '请输入支付方式', trigger: 'blur'}
        ],
        warrantyPeriod: [
          {required: true, message: '请输入质保期限', trigger: 'blur'}
        ],
        projectManager: [
          {required: true, message: '请输入项目经理名称', trigger: 'blur'}
        ],
        basisSettlement: [
          {required: true, message: '请输入结算依据', trigger: 'blur'}
        ],
        goodsExplain: [
          {required: true, message: '请输入规格/型号/数量', trigger: 'blur'}
        ],
        supplyPeriod: [
          {required: true, message: '请输入供货期', trigger: 'blur'}
        ],
        serverPeriod: [
          {required: true, message: '请输入服务期', trigger: 'blur'}
        ]
      }, // 表单规则验证
      fileDelegateList: [], // 授权委托人
      fileAccreditList: [], // 授权书
      fileAptitudeList: [], // 单位资质证件
      fileNegotiateList: [], // 谈判记录
      upLoad: commonJs.fileUploadUrl,
      fileList: [],
      index: 0, // 展开一个标段文件的index
      loading: true
    }
  },
  computed: {},
  props: [
    'tenderProjectCode'
  ],
  created () {
  },
  filters: {
    /** 标段是否归档 */
    filterStatus (val) {
      if (val === 2) {
        return '已归档'
      } else {
        return '未归档'
      }
    }
  },
  /** 方法集 */
  methods: {
    downloadFile,
    /** 授权委托书--上传 */
    handleDelegateSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.fileDelegateList = []
        // 替换 ；通用
        this.fileDelegateList.push(file.response.fileInformation)
      }
    },
    /** 授权书--上传 */
    handleAccreditSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.fileAccreditList = []
        // 替换 ；通用
        this.fileAccreditList.push(file.response.fileInformation)
      }
    },
    /** 单位资质文件--上传 */
    handleAptitudeSuccess (response, file, fileList) {
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
        this.fileAptitudeList = fileInformations // 获得目前项目中所有的附件信息
      }
    },
    /** 单位资质文件--删除 */
    clickDel (index) {
      this.fileAptitudeList.splice(index, 1)
    },
    /** 谈判记录--上传 */
    handleNegotiateSuccess (response, file, fileList) {
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
        this.fileNegotiateList = fileInformations // 获得目前项目中所有的附件信息
      }
    },
    /** 谈判记录--删除 */
    clickDelFile (index) {
      this.fileNegotiateList.splice(index, 1)
    },
    /** 谈判记录--格式限制 */
    beforeNegotiateUpload (file) {
      const isPDF = file.type === 'application/pdf'
      if (!isPDF) {
        this.$message.error('只能上传PDF格式文件!')
      }
      return isPDF
    },
    /** 校验附件 */
    validFile () {
      // 授权委托人
      if (this.fileDelegateList.length > 0) {
        this.$set(this.archivesExtrasForm, 'fileDelegateList', 'fileDelegateList')
      } else {
        this.$set(this.archivesExtrasForm, 'fileDelegateList', '')
      }
      if (this.archivesExtrasForm.fileDelegateList) {
        this.$refs['fileDelegateList'][this.index].clearValidate()
      }
      // 授权书
      if (this.fileAccreditList.length > 0) {
        this.$set(this.archivesExtrasForm, 'fileAccreditList', 'fileAccreditList')
      } else {
        this.$set(this.archivesExtrasForm, 'fileAccreditList', '')
      }
      if (this.archivesExtrasForm.fileAccreditList) {
        this.$refs['fileAccreditList'][this.index].clearValidate()
      }
      // 单位资质证件
      if (this.fileAptitudeList.length > 0) {
        this.$set(this.archivesExtrasForm, 'fileAptitudeList', 'fileAptitudeList')
      } else {
        this.$set(this.archivesExtrasForm, 'fileAptitudeList', '')
      }
      if (this.archivesExtrasForm.fileAptitudeList) {
        this.$refs['fileAptitudeList'][this.index].clearValidate()
      }
      // 谈判记录
      if (this.fileNegotiateList.length > 0) {
        this.$set(this.archivesExtrasForm, 'fileNegotiateList', 'fileNegotiateList')
      } else {
        this.$set(this.archivesExtrasForm, 'fileNegotiateList', '')
      }
      if (this.archivesExtrasForm.fileNegotiateList) {
        this.$refs['fileNegotiateList'][this.index].clearValidate()
      }
    },
    /** 新增/修改档案管理附加信息 */
    saveArchivesExtras (val, index) {
      this.validFile()
      this.$refs[val][index].validate((valid) => {
        if (valid) {
          this.archivesExtrasForm.sectionCode = this.activeName
          // 设置表格上传文件数据
          let archivesFileList = this.uploadData(this.archivesExtrasForm.archivesFileList)
          this.$set(this.archivesExtrasForm, 'archivesFileList', archivesFileList)
          sectionArchivesExtras.save(this.archivesExtrasForm).then((res) => {
            this.getArchivesExtrasData()
            this.loading = true
          })
        }
      })
    },
    /** 设置表单附件上传数据 */
    uploadData (val) {
      let result = []
      if (val) {
        result = val.map((item) => {
          // 授权委托人
          if (item.fileType === 14) {
            item.fileInformationList = this.fileDelegateList
          }
          // 授权书
          if (item.fileType === 15) {
            item.fileInformationList = this.fileAccreditList
          }
          // 单位资质证件
          if (item.fileType === 16) {
            item.fileInformationList = this.fileAptitudeList
          }
          // 谈判记录
          if (item.fileType === 17) {
            item.fileInformationList = this.fileNegotiateList
          }
          return item
        })
      } else {
        // 授权委托书
        if (this.fileDelegateList.length > 0) {
          let obj1 = {
            fileType: 14,
            fileInformationList: this.fileDelegateList
          }
          result.push(obj1)
        }
        // 授权书
        if (this.fileAccreditList.length > 0) {
          let obj2 = {
            fileType: 15,
            fileInformationList: this.fileAccreditList
          }
          result.push(obj2)
        }
        // 单位资质证件
        if (this.fileAptitudeList.length > 0) {
          let obj3 = {
            fileType: 16,
            fileInformationList: this.fileAptitudeList
          }
          result.push(obj3)
        }
        // 谈判记录
        if (this.fileNegotiateList.length > 0) {
          let obj4 = {
            fileType: 17,
            fileInformationList: this.fileNegotiateList
          }
          result.push(obj4)
        }
      }
      return result
    },
    /** 初始化数据 */
    initializeData (section, index) {
      this.activeName = section.code
      this.getNameData()
      if (section.archivingStatus === 2 || this.$route.query.operation === 'look') {
        this.dialogEdit = false
      } else {
        this.dialogEdit = true
      }
      if (this.activeName) {
        this.loading = false
        this.getArchivesExtrasData()
      }
    },
    /** 根据招标项目code获得项目单位名称 */
    getNameData () {
      tenderproject.getNameByCode(this.tenderProjectCode).then((res) => {
        this.nameForm = res.data.tenderProject
        this.tenderProjectType = res.data.tenderProject.tenderProjectType
      })
    },
    /** 根据招标项目code获得对应的标段信息 */
    getSectionData () {
      section.getAllByTenderCode(this.tenderProjectCode).then((res) => {
        if (res) {
          this.sectionData = res.data.sectionList
        }
      }).then((res) => {
        if (this.sectionData.length > 0) {
          // 默认展示第一个标段信息
          this.activeName = this.sectionData[0].code
          this.openingTime = this.sectionData[0].openingTime
          if (this.sectionData[0].archivingStatus === 2 || this.$route.query.operation === 'look') {
            this.dialogEdit = false
          } else {
            this.dialogEdit = true
          }
          this.getNameData()
          this.getArchivesExtrasData()
        }
      })
    },
    /**  根据标段code获得档案管理附加信息 */
    getArchivesExtrasData () {
      this.archivesExtrasForm = {}
      sectionArchivesExtras.getOne(this.activeName).then((res) => {
        let data = res.data.sectionArchivesExtras
        if (data) {
          this.archivesExtrasForm = data
          this.getFileShow(data)
        } else {
          this.fileDelegateList = []
          this.fileAccreditList = []
          this.fileAptitudeList = []
          this.fileNegotiateList = []
        }
        this.loading = false
      })
    },
    /** 表格附件数据设置 */
    getFileShow (json) {
      let archivesJson = json || null
      if (archivesJson.archivesFileList) {
        this.fileDelegateList = [] // 授权委托人
        this.fileAccreditList = [] // 授权书
        this.fileAptitudeList = [] // 单位资质证件
        this.fileNegotiateList = [] // 谈判记录
        archivesJson.archivesFileList.forEach((item) => {
          // 授权委托人
          if (item.fileType === 14) {
            this.fileDelegateList = item.fileInformationList
          }
          // 授权书
          if (item.fileType === 15) {
            this.fileAccreditList = item.fileInformationList
          }
          // 单位资质证件
          if (item.fileType === 16) {
            this.fileAptitudeList = item.fileInformationList
          }
          // 谈判记录
          if (item.fileType === 17) {
            this.fileNegotiateList = item.fileInformationList
          }
        })
      }
    },
    /** 提交 */
    submitCilck (sectionMsg) {
      this.$confirm('确定将该标段提交归档, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let sectionFileFlag = false
        sectionArchivesExtras.getOne(sectionMsg.code).then((res) => {
          if (res.data.sectionArchivesExtras) {
            sectionFileFlag = true
          }
        }).then((res) => {
          if (sectionFileFlag) {
            sectionMsg.archivingStatus = 2
            section.save(sectionMsg).then((res) => {
              this.dialogEdit = false
            })
          } else {
            this.$message.error('标段文件信息不完整，不可提交！')
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getSectionData()
  }
}
</script>
<style lang="less">
body {
  margin: 0;
}
#otherfile-page {
  /** 折叠面板 */
  .collapse-warp {
    .el-collapse-item__header {
      font-size: 14px;
      font-weight: 800;
      padding-left: 20px;
      text-align: left;
      background: #F7F6FB;
    }
    .el-collapse-item__header.is-active {
      background: #ebeffe;
    }
    .el-collapse-item {
      margin-bottom: 10px;
    }
    .collspase-cont {
      padding-top: 20px;
      .tabpane-btn {
        padding: 20px;
      }
    }
    .info-table {
      width: 100%;
      border-collapse: collapse;
      border: 1px solid #eee;
      tr td {
        min-height: 50px;
        line-height: 50px;
        border: 1px solid #eee;
      }
      .el-form-item {
        margin-bottom: 0;
        .el-form-item__content {
          margin-left: 0 !important;
          text-align: left;
          width: 420px;
        }
      }
      tr td:nth-child(odd) {
        min-width: 100px;
        background: #F6F7F9;
      }
      tr td:nth-child(even) {
        min-width: 320px;
        text-align: left;
        padding: 0 10px;
      }
      .file-list {
        display: inline-block;
        width: 100%;
      }
    }
  }
  .el-icon-error {
    color: red;
    &:hover {
      cursor: pointer;
    }
  }
}
</style>
