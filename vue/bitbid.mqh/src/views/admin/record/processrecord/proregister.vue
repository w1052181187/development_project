<template>
  <!-- 招标项目登记 -->
  <div class="admin-layout" id="proregister-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/record/processrecord' }">招标过程备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/record/proregister' }">项目登记</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 招标项目start -->
    <div class="proregister-cont">
      <div class="bidpro-title">
        <span>招标项目</span>
      </div>
      <div class="bidpro-cont">
        <table class="bidpro-table">
          <tr>
            <td>招标项目名称</td>
            <td colspan="3">
              <el-select v-model="bidProForm.objectId" placeholder="请选择招标项目" style="width: 280px;" @change="getSelectApplyInfo">
                <el-option
                  v-for="item in selectApplyList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </td>
          </tr>
          <tr>
            <td>招标项目编号</td>
            <td colspan="3">{{bidProForm.codeUser}}</td>
          </tr>
          <tr>
            <td rowspan="2">招标人</td>
            <td rowspan="2">{{bidProForm.enterpriseName}}</td>
            <td>联系人</td>
            <td>{{bidProForm.contacts}}</td>
          </tr>
          <tr>
            <td>招标人联系电话</td>
            <td>{{bidProForm.phone}}</td>
          </tr>
          <tr>
            <td rowspan="2">招标代理机构</td>
            <td rowspan="2">{{agencyForm.agencyName}}</td>
            <td>地址</td>
            <td>{{agencyForm.agencyAddress}}</td>
          </tr>
          <tr>
            <td>代理联系电话</td>
            <td>{{agencyForm.agencyPhone}}</td>
          </tr>
          <tr>
            <td>招标范围</td>
            <td>{{bidProForm.content}}</td>
            <td>是否资格预审</td>
            <td>
              <el-radio-group v-model="prequalificationStatus" @change="changeRadioPretrial">
                <el-radio :label="1" value="1">是</el-radio>
                <el-radio :label="0" value="0">否</el-radio>
              </el-radio-group>
              <span style="padding-left: 30px;" v-if="showQualPre">资格预审方法:</span>
              <el-radio-group v-model="bidProForm.prequalificationType" @change="changeRadioMethod" v-if="showQualPre">
                <el-radio :label="1" value="1">合格制</el-radio>
                <el-radio :label="2" value="2">有限数量制</el-radio>
              </el-radio-group>
              <span v-if="showNumber">
                <el-input v-model="bidProForm.prequalificationNumber" style="width: 80px;"></el-input>家
              </span>
            </td>
          </tr>
          <tr>
            <td>招标项目类型</td>
            <td>{{bidProForm.projectType | filterType}}</td>
            <td>采购方式</td>
            <td>{{bidProForm.biddingType | filterBiddingType}}</td>
          </tr>
          <tr>
            <td>招标项目地址</td>
            <td colspan="3">{{bidProForm.projectAddress}}</td>
          </tr>
          <tr>
            <td>依法必须招标</td>
            <td>{{bidProForm.isAccordToLaw | filteredIsToLaw}}
              <!--<el-radio-group v-model="bidProForm.isAccordToLaw" v-if="!bidProForm.bidLaw===1">
                <el-radio label="是"></el-radio>
                <el-radio label="否"></el-radio>
              </el-radio-group>-->
            </td>
            <td>是否电子招标</td>
            <td>{{bidProForm.isElectronicBidding | filteredIsToLaw}}
              <!--<span v-if="bidProForm.bidLaw===1">{{bidProForm.bidType}}</span>
              <el-radio-group v-model="bidProForm.isElectronicBidding" v-if="!bidProForm.bidLaw===1">
                <el-radio label="是"></el-radio>
                <el-radio label="否"></el-radio>
              </el-radio-group>-->
            </td>
          </tr>
          <tr>
            <td>立项批复文件</td>
            <td>
             <span class="file-list" v-for="(item, index) in this.bidProForm.fileInfoList" :key="index">
              <span><el-button type="text" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>&nbsp;</span>
            </span>
            </td>
            <td>合同委托书</td>
            <td>
              <el-upload
                class="upload-demo"
                :action="upLoad"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                :auto-upload="true"
                :on-success="uploadSuccess"
                :limit="1"
                :on-exceed="handleExceed"
                :before-upload="beforeAvatarUpload"
                :file-list="fileList">
                <el-button size="small" type="primary" class="upload-btn">点击上传</el-button>
                <span>限制为pdf格式，只可上传一个附件</span>
              </el-upload>
            </td>
          </tr>
          <tr>
            <td>项目编号（代理）</td>
            <td>
              <el-input type="text" v-model="bidProForm.agencyProjectCode" style="width: 260px"></el-input>
            </td>
            <td>委托协议书</td>
            <td>
              <el-upload
                class="upload-demo"
                :action="upLoad"
                :auto-upload="true"
                :on-success="uploadFileSuccess"
                :file-list="EntrustmentFileList">
                <el-button size="small" type="primary" class="upload-btn">点击上传</el-button>
              </el-upload>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <!-- 招标项目end -->

    <!-- 项目经理start -->
    <div class="pro-manager proregister-cont">
      <div class="bidpro-title">
        <span>项目经理</span>
      </div>
      <div class="promanager-add">
         <el-button type="warning" icon="el-icon-plus" @click="addManagerVisible=true">添加</el-button>
      </div>
      <div class="promanager-table">
        <el-table
          ref="multipleTable"
          :data="managerTableData"
          border
          style="width: 100%">
          <el-table-column
            align="left"
            prop="name"
            label="姓名">
          </el-table-column>
          <el-table-column
            align="left"
            prop="phone"
            label="联系方式">
          </el-table-column>
          <el-table-column
            align="left"
            prop="remark"
            label="描述"
            width="300">
          </el-table-column>
          <el-table-column
            align="left"
            label="操作"
            width="120">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row, 'look')" type="text" size="medium">查看</el-button>
              <el-button @click="handleTableClick(scope.row, 'del')" type="text" size="medium">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="promanager-btn">
        <el-button type="primary" @click="saveFormData" v-loading="btnLoading">保存</el-button>
        <el-button type="info" @click="$router.go(-1)">取消</el-button>
      </div>
    </div>
    <!-- 项目经理end -->

    <!-- 弹窗区域start -->
    <div class="pro-dialog">
      <!-- 项目经理添加satrt -->
      <el-dialog
        title="添加"
        :visible.sync="addManagerVisible"
        width="40%">
        <div class="bidpro-addbtn">
          <el-button type="warning" @click="addOneVisible=true" icon="el-icon-plus" v-if="false">新 增</el-button>
        </div>
        <div class="bidmanager-table">
          <el-table
            ref="multipleTable"
            border
            :data="multipleTableData"
            style="width: 100%"
            @selection-change="handleSelectionChange">
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column
              prop="name"
              label="姓名"
              align="left">
            </el-table-column>
            <el-table-column
              prop="phone"
              label="联系方式"
              align="left">
            </el-table-column>
            <el-table-column
              prop="remark"
              label="描述"
              align="left">
            </el-table-column>
          </el-table>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="addManagerSubmit">确 定</el-button>
          <el-button @click="addManagerVisible = false">取 消</el-button>
        </span>
      </el-dialog>
      <!-- 项目经理添加end -->

      <!-- 新增弹窗start -->
      <el-dialog
        title="新增"
        :visible.sync="addOneVisible"
        width="40%">
        <div class="bidpro-addbtn">
          <el-form :model="addOneForm" :rules="addOneRules" ref="addOneForm" label-width="100px">
            <el-form-item label="姓名:" prop="name">
              <el-input v-model="addOneForm.name"></el-input>
            </el-form-item>
            <el-form-item label="联系方式:" prop="type">
              <el-input v-model="addOneForm.type"></el-input>
            </el-form-item>
            <el-form-item label="描述:" prop="desc">
              <el-input v-model="addOneForm.desc" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="证书信息:" prop="info">
              <el-table
                border
                :data="oneTableData"
                style="width: 100%">
                <el-table-column
                  label="证件名称"
                  prop="certificateName"
                  align="left">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.certificateName" placeholder="请输入内容"></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="fileName"
                  label="证件信息（附件）"
                  align="left">
                  <template slot-scope="scope">
                      <el-upload
                        :before-upload="beforeAvatarUpload"
                        style="float:left;"
                        class="upload-demo"
                        :action="upLoad"
                        :auto-upload="true"
                        :show-file-list="false"
                        :limit="1"
                        :file-list="epFileList"
                        :on-success="uploadSuccess">
                        <el-button size="small" type="primary" @click="epClick(scope.$index)" v-if="scope.row.flagIndex">上传附件</el-button>
                      </el-upload>
                    </template>
                </el-table-column>
                <el-table-column
                  prop="desc"
                  label="操作"
                  align="left">
                  <template slot-scope="scope">
                    <el-button @click="handleOneTableClick(scope.row,'confirm', scope.$index)" type="text" size="medium" v-if="scope.row.flagIndex === 1">确定</el-button>
                    <el-button @click="handleOneTableClick(scope.row,'del', scope.$index)" type="text" size="medium" v-if="scope.row.flagIndex === 1">删除</el-button>
                    <el-button @click="handleOneTableClick(scope.row,'edit', scope.$index)" type="text" size="medium" v-if="scope.row.flagIndex !== 1">编辑</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div class="certif-box">
                <el-button @click="handleAddRow" type="text" size="medium" icon="el-icon-plus" class="certif-add-btn"></el-button>
              </div>
            </el-form-item>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="addOneSubmit">确 定</el-button>
          <el-button @click="addOneVisible = false">取 消</el-button>
        </span>
      </el-dialog>
      <!-- 新增弹窗end -->

      <!-- 项目经理查看start -->
      <el-dialog
        title="查看"
        :visible.sync="lookDialogVisible"
        width="30%">
        <div class="bidpro-change">
          <el-form :model="lookDialogForm" label-width="100px">
            <el-form-item label="姓名:" prop="name">
              <span>{{lookDialogForm.name}}</span>
            </el-form-item>
            <el-form-item label="联系方式:" prop="phone">
              <span>{{lookDialogForm.phone}}</span>
            </el-form-item>
            <el-form-item label="描述:" prop="remark">
              <span>{{lookDialogForm.remark}}</span>
            </el-form-item>
            <el-form-item label="证书信息:" prop="info">
              <el-table
                :data="lookDialogForm.certificatesList"
                border
                style="width: 100%">
                <el-table-column
                  prop="certificateName"
                  label="证书名称"
                  align="left">
                </el-table-column>
                <el-table-column
                  prop="fileInformation"
                  label="证件信息（附件）"
                  align="left">
                  <template slot-scope="scope">
                    <span><el-button type="text" @click="downloadFile(scope.row.fileInformation.fileName,scope.row.fileInformation.relativePath)">可下载</el-button></span>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <!-- 项目经理查看end -->
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import {tenderproject, drawrecord} from 'api/index'
import {interclassify} from 'api/resource/intermanage/interclassify'
import {downloadFile, commonJs} from 'common/js/common'
export default {
  data () {
    return {
      btnLoading: false, // 提交加载动画
      showQualPre: false, // 资格预审方法-隐藏
      showNumber: false, // 有限数量制数量-隐藏
      managerTableData: [], // 项目经理-表格数据
      lookDialogVisible: false, // 项目经理-查看弹窗
      lookDialogForm: {}, // 项目经理-查看-表单
      oneTableData: [], // 新增一个-表格数据
      addOneForm: {}, // 新增一个-表单
      addOneRules: {}, // 新增一个-规则
      bidProForm: {
        prequalificationType: 1,
        entrustmentLetters: []
      }, // 招标项目-表单
      agencyForm: {
        agencyName: '',
        agencyAddress: '',
        agencyPhone: ''
      },
      bidProRules: {}, // 招标项目-规则
      fileList: [], // 上传列表
      addManagerVisible: false, // 添加项目经理-弹窗
      addOneVisible: false, // 新增
      epFileList: [],
      multipleTableData: [],
      selectApplyList: [], // 当前用户所在企业中所中标的项目信息
      prequalificationStatus: 0, // 是否资格预审,默认否
      upLoad: commonJs.fileUploadUrl,
      hasSelectedProManager: [],
      EntrustmentFileList: [] // 委托协议书附件信息
    }
  },
  computed: {},
  created () {
    this.getAllSelectApplyList()
  },
  filters: {
    /** 选聘中介类型的选择 */
    filterType (type) {
      if (type === 1) {
        return '工程'
      } else if (type === 2) {
        return '货物'
      } else if (type === 3) {
        return '服务'
      }
    },
    filterBiddingType (type) {
      if (type === 1) {
        return '公开招标'
      } else if (type === 2) {
        return '邀请招标'
      } else if (type === 3) {
        return '谈判采购'
      } else if (type === 4) {
        return '询比采购'
      } else if (type === 5) {
        return '竞价采购'
      } else if (type === 6) {
        return '直接采购'
      } else if (type === 7) {
        return '框架协议'
      }
    },
    filteredIsToLaw (value) {
      if (value === 1) {
        return '是'
      } else if (value === 0) {
        return '否'
      } else {
        return ''
      }
    }
  },
  /** 方法集 */
  methods: {
    /** 获取前用户所在企业中所中标的项目信息 */
    getAllSelectApplyList () {
      this.selectApplyList = []
      tenderproject.getSelectApplyByEnterpriseId(this.$store.getters.authUser.enterpriseId).then((res) => {
        if (res.data.selectApplyList.length > 0) {
          res.data.selectApplyList.forEach(selectApply => {
            this.selectApplyList.push({
              value: selectApply.objectId,
              label: selectApply.projectName
            })
          })
        }
      })
    },
    getSelectApplyInfo () {
      drawrecord.getOne(this.bidProForm.objectId).then((res) => {
        // this.bidProForm.bidproId = res.data.selectApply.objectId
        this.bidProForm = Object.assign(this.bidProForm, res.data.selectApply)
        this.bidProForm.projectType = res.data.selectApply.projectPlan.projectType
        // 处理附件信息，只展示用户上传的附件信息
        if (res.data.selectApply.fileInformationList) {
          this.bidProForm.fileInfoList = []
          for (let i = 0; i < res.data.selectApply.fileInformationList.length; i++) {
            if (Number(res.data.selectApply.fileInformationList[i].businessType) !== 2) {
              this.bidProForm.fileInfoList.push(res.data.selectApply.fileInformationList[i])
            }
          }
        }
        this.getAgencyInfo(res.data.selectApply.entrustEnterprise)
      })
    },
    getAgencyInfo (entrustEnterprise) {
      if (entrustEnterprise.enterpriseExpend.provinceId && entrustEnterprise.enterpriseExpend.provinceId && entrustEnterprise.enterpriseExpend.provinceId) {
        this.agencyForm.agencyAddress = ''
        this.agencyForm.agencyAddress += this.getLabelName(interclassify, entrustEnterprise.enterpriseExpend.provinceId).node.label
        this.agencyForm.agencyAddress += this.getLabelName(interclassify, entrustEnterprise.enterpriseExpend.cityId).node.label
        this.agencyForm.agencyAddress += this.getLabelName(interclassify, entrustEnterprise.enterpriseExpend.countyId).node.label
      }
      this.agencyForm.agencyName = entrustEnterprise.name
      this.agencyForm.agencyAddress += entrustEnterprise.enterpriseExpend.address
      this.agencyForm.agencyPhone = entrustEnterprise.enterpriseExpend.legalPhone
      this.multipleTableData = entrustEnterprise.projectManagerList
    },
    /** treeData [原始数据集]， key 传入的key值 */
    getLabelName (treeData, key) {
      let parentNode = null
      let node = null
      function getTreeDeepArr (treeData, key) {
        for (let i = 0; i < treeData.length; i++) {
          // 1.如果没有子节点 beark
          if (node) {
            break
          }
          // 定义需要操作的数据
          let obj = treeData[i]
          // 没有节点就下一个
          if (!obj || !obj.value) {
            continue
          }
          // 2.有节点就继续找，一直递归
          if (obj.value === key) {
            node = obj
            break
          } else {
            // 3.如果有子节点就继续找
            if (obj.children) {
              // 4.递归前，记录当前节点，作为parentNode
              parentNode = obj
              // 递归
              getTreeDeepArr(obj.children, key)
            } else {
              // 跳出当前递归
              continue
            }
          }
        }
        // 如果没有找到父节点，置为null
        if (!node) {
          parentNode = null
        }
        // 返回结果
        return {
          node: node,
          parentNode: parentNode
        }
      }
      return getTreeDeepArr(treeData, key)
    },
    /** 下载附件文件 */
    downloadFile,
    /** 是否资格预审-单选 */
    changeRadioPretrial (value) {
      if (value === 1) {
        this.showQualPre = true
        this.bidProForm.prequalificationType = 1
        this.showNumber = false
      } else {
        this.showQualPre = false
        this.showNumber = false
      }
    },
    handleRemove (file, fileList) {
      // console.log(file, fileList)
    },
    handlePreview (file) {
      // console.log(file)
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove (file, fileList) {
      // return this.$confirm(`确定移除 ${file.name}？`)
    },
    /** 上传限制 */
    beforeAvatarUpload (file) {
      const isPDF = file.type === 'application/pdf'
      if (!isPDF) {
        this.$message.error('只能上传PDF格式文件!')
      }
      return isPDF
    },
    /** 上传成功 */
    uploadSuccess (response, file, fileList) {
      this.bidProForm.contract = response.fileInformation
    },
    /** 上传委托协议书附件 */
    uploadFileSuccess (response, file, fileList) {
      response.fileInformation.businessType = 4
      this.bidProForm.entrustmentLetters.push(response.fileInformation)
    },
    /** 资格预审方法-单选 */
    changeRadioMethod (value) {
      if (value === 2) {
        this.showNumber = true
      } else {
        this.showNumber = false
      }
    },
    /** 保存 */
    saveFormData () {
      if (this.bidProForm.agencyProjectCode && this.bidProForm.agencyProjectCode.length > 50) {
        this.$message.error('代理机构编号长度小于等于50个字符！')
        return false
      }
      let obj = {
        relatedHistoryCode: this.bidProForm.code,
        historySourceType: 1,
        tenderProjectType: this.bidProForm.projectType,
        biddingType: this.bidProForm.biddingType,
        content: this.bidProForm.content,
        isPrequalification: this.prequalificationStatus,
        prequalificationType: this.bidProForm.prequalificationType || null,
        prequalificationNumber: this.bidProForm.prequalificationNumber || null,
        isAccordToLaw: this.bidProForm.isAccordToLaw,
        isElectronicBidding: this.bidProForm.isElectronicBidding,
        sourceType: 1,
        tenderProjectName: this.bidProForm.projectName,
        projectEnterpriseId: this.bidProForm.projectPlan.annualProjectPlan.enterpriseId,
        requiresEnterpriseId: this.bidProForm.projectPlan.requiresEnterpriseId,
        contract: this.bidProForm.contract,
        projectManagerList: this.hasSelectedProManager,
        entrustmentLetters: this.bidProForm.entrustmentLetters,
        agencyProjectCode: this.bidProForm.agencyProjectCode
      }
      // 加载动画
      this.btnLoading = true
      tenderproject.save(obj).then(() => {
        this.btnLoading = false
        this.$router.push({path: '/admin/record/processrecord', query: {status: true}})
        this.$refs.multipleTable.clearSelection()
      })
    },
    /** 添加-弹窗-确定 */
    addManagerSubmit () {
      this.addManagerVisible = false
      this.getAllProjectManager()
    },
    /** 获取全部已选择的项目经理的信息 */
    getAllProjectManager () {
      this.managerTableData = []
      this.managerTableData = this.hasSelectedProManager
    },
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.lookDialogVisible = true
        this.lookDialogForm = row
      } else if (Object.is(type, 'del')) {
        let index = this.getIndex(row, this.hasSelectedProManager)
        if (index !== -1) {
          // this.managerTableData.splice(index, 1)
          this.hasSelectedProManager.splice(index, 1)
          this.$refs.multipleTable.toggleRowSelection(row, false)
        }
        if (this.hasSelectedProManager.length === 0) {
          this.managerTableData = []
        }
      }
    },
    /** 获取要删除的项目经理的下标索引 */
    getIndex (obj, arr) {
      let len = arr.length
      for (let i = 0; i < len; i++) {
        if (arr[i].objectId === obj.objectId) {
          return parseInt(i)
        }
      }
      return -1
    },
    /** 新增-弹窗-确定 */
    addOneSubmit () {},
    // 多选
    handleSelectionChange (data) {
      this.hasSelectedProManager = []
      this.hasSelectedProManager = data
    },
    // 新增一行
    handleAddRow () {
      let obj = {
        certificateName: '',
        flagIndex: 1
      }
      this.oneTableData.push(obj)
    },
    // 下载
    downLoadFile () {},
    // 新增-弹窗-表格操作
    handleOneTableClick (row, type, index) {
      if (Object.is(type, 'confirm')) {
        // 设置按钮切换
        this.oneTableData = this.setTogglebutton(this.oneTableData, index, type)
      } else if (Object.is(type, 'edit')) {
        this.oneTableData = this.setTogglebutton(this.oneTableData, index, type)
      }
    },
    /** 设置按钮切换 */
    setTogglebutton (arr, index, type) {
      let newArr = []
      // 将设置好的数据拷贝一份
      newArr = arr.map((item, current) => {
        if (index === current) {
          if (Object.is(type, 'edit')) {
            item.flagIndex = 0
          } else if (Object.is(type, 'confirm')) {
            item.flagIndex = 1
          }
          return item
        } else {
          return item
        }
      })
      return newArr
    }
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
#proregister-page {
  /** 招标项目 */
  .proregister-cont{
    margin: 20px;
    .bidpro-title {
      height: 50px;
      line-height: 50px;
      text-align:left;
      padding: 0 20px;
      background:#F7F6FB;
      span {
        font-weight: 800;
      }
    }
    .bidpro-cont {
      background: #fff;
      padding: 20px;
      .el-form-item__content {
        text-align: left;
      }
      .bidpro-table {
        width: 100%;
        border-collapse: collapse;
        border: 1px solid #eee;
        tr td {
          min-height: 50px;
          line-height: 50px;
          border: 1px solid #eee;
        }
        tr td:nth-child(odd) {
          width: 120px;
          background: #F6F7F9;
        }
        tr td:nth-child(even) {
          width: 480px;
          text-align: left;
          padding: 0 10px;
        }
      }
    }
  }
  /** 项目经理 */
  .pro-manager {
    margin-top: 20px;
    margin-bottom: 100px;
    background: #fff;
    .promanager-add {
      text-align: left;
      padding: 20px;
    }
    .promanager-table {
      padding-left: 20px;
      padding-right: 20px;
      padding-bottom: 30px;
    }
    .promanager-btn {
      text-align: center;
      padding-bottom: 20px;
    }
  }
  /** 弹窗 */
  .pro-dialog {
    .bidpro-addbtn {
      text-align: left;
      padding-bottom: 20px;
    }
    .bidpro-change {
      .el-form-item__content {
        text-align: left;
      }
    }
    .el-dialog__footer {
      text-align: center;
    }
    .certif-box {
      background-color: #ECEFFE;
      text-align: center;
      .certif-add-btn {
        width: 100%;
      }
    }
  }
}
</style>
