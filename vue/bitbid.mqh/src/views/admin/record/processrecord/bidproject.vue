<template>
  <!-- 招标项目 -->
  <div class="admin-layout" id="bidproject-page">
    <!-- 招标项目start -->
    <div class="bidproject-cont">
      <div class="bidpro-title">
        <span>招标项目</span>
        <el-button size="mini" class="right-btnbg" @click="getChangeRecord">变更历史查询</el-button>
        <!--<el-button size="mini" class="right-btnbg" @click="getBidProChangeInfo" v-if="this.$store.getters.authUser.enterpriseType === 4 && this.buttonFlag">招标项目变更</el-button>-->
        <el-button size="mini" class="right-btnbg" @click="getBidProChangeInfo" v-if="(this.$store.getters.authUser.enterpriseType === 2 || this.$store.getters.authUser.enterpriseType === 3) && this.operationCheckFlag">招标项目变更</el-button>
      </div>
      <div class="bidpro-cont">
        <table class="bidpro-table" ref="bidProForm">
          <tr>
            <td>招标项目名称</td>
            <td colspan="3">{{selectApplyForm.projectName}}</td>
          </tr>
          <tr>
            <td>招标项目编号</td>
            <td colspan="3">{{selectApplyForm.codeUser}}</td>
          </tr>
          <tr>
            <td rowspan="2">招标人</td>
            <td rowspan="2">{{selectApplyForm.enterpriseName}}</td>
            <td>联系人</td>
            <td>{{selectApplyForm.contacts}}</td>
          </tr>
          <tr>
            <td>招标人联系电话</td>
            <td>{{selectApplyForm.phone}}</td>
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
            <td>{{bidProForm.isPrequalification | filterPrequalification}}
              <span style="padding-left: 30px;" v-if="bidProForm.isPrequalification === 1">资格预审方法:&nbsp;
              {{bidProForm.prequalificationType | filterPrequalificationType}}</span>
              <span v-if="bidProForm.isPrequalification === 1 && bidProForm.prequalificationType === 2">
                &nbsp;{{bidProForm.prequalificationNumber}}家
              </span>
              <!--<el-radio-group v-model="bidProForm.isPrequalification">
                <el-radio :label="1" disabled>是</el-radio>
                <el-radio :label="0" disabled>否</el-radio>
              </el-radio-group>
              <span style="padding-left: 30px;" v-if="bidProForm.isPrequalification === 1">资格预审方法:</span>
              <el-radio-group v-model="bidProForm.prequalificationType">
                <el-radio :label="1" disabled>合格制</el-radio>
                <el-radio :label="2" disabled>有限数量制</el-radio>
              </el-radio-group>
              <span v-if="bidProForm.prequalificationType === 2">
                <el-input v-model="bidProForm.prequalificationNumber" style="width: 80px;" :disabled="true"></el-input>家
              </span>-->
            </td>
          </tr>
          <tr>
            <td>招标项目类型</td>
            <td>{{bidProForm.tenderProjectType | filterType}}</td>
            <td>招标方式</td>
            <td>{{bidProForm.biddingType | filterBiddingType}}</td>
          </tr>
          <tr>
            <td>招标项目地址</td>
            <td colspan="3">{{selectApplyForm.projectAddress}}</td>
          </tr>
          <tr>
            <td>依法必须招标</td>
            <td>{{bidProForm.isAccordToLaw | filterIsAccordToLaw}}</td>
            <td>是否电子招标</td>
            <td>{{bidProForm.isElectronicBidding | filterIsElectronicBidding}}
             <!-- <span v-if="bidProForm.bidLaw===1">{{bidProForm.bidType}}</span>
              <el-radio-group v-model="bidProForm.dzzb" v-if="!bidProForm.bidLaw===1">
                <el-radio label="是"></el-radio>
                <el-radio label="否"></el-radio>
              </el-radio-group>-->
            </td>
          </tr>
          <tr>
            <td>立项批复文件</td>
            <td>
              <span class="file-list" v-for="(item, index) in this.selectApplyForm.fileInfoList" :key="index">
              <span><el-button type="text" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>&nbsp;</span>
              </span>
            </td>
            <td>合同委托书</td>
            <td>
              <span><el-button type="text" @click="downloadFile(bidProForm.contract.fileName,bidProForm.contract.relativePath)"><span v-if="bidProForm.contract">{{bidProForm.contract.fileName}}</span></el-button></span>
            </td>
          </tr>
          <tr>
            <td>项目编号（代理）</td>
            <td>{{bidProForm.agencyProjectCode}}</td>
            <td>委托协议书</td>
            <td>
              <span class="file-list" v-for="(item, index) in this.bidProForm.fileInfoList" :key="index">
              <span><el-button type="text" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>&nbsp;</span>
              </span>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <!-- 招标项目end -->
    <!-- 项目经理start -->
    <div class="pro-manager bidproject-cont">
      <div class="bidpro-title">
        <span>项目经理</span>
      </div>
      <div class="pro-mana-table">
        <el-table
          :data="tableData"
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
            width="100">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row, 'look')" type="text" size="medium">查看</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <!-- 项目经理end -->

    <!-- 弹窗区域start -->
    <div class="pro-dialog">
      <!-- 招标项目变更start -->
      <el-dialog
        title="招标项目变更"
        :visible.sync="changeBidProVisible"
        :before-close="handleClose"
        width="38%">
        <div class="bidpro-change">
          <el-form :model="bidProChangeForm" :rules="bidProChangeRules" ref="bidProChangeForm" label-width="120px">
            <el-form-item label="招标项目名称:" prop="tenderProjectName">
              <span>{{bidProChangeForm.tenderProjectName}}</span>
            </el-form-item>
            <el-form-item label="招标方式:" prop="biddingType">
              <el-select v-model="bidProChangeForm.biddingType" placeholder="请选择招标方式">
                <el-option
                  v-for="item in orgTypeFirstList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="是否资格预审:" prop="isPrequalification">
              <el-radio-group v-model="bidProChangeForm.isPrequalification" @change="changeRadioPretrial">
                <el-radio :label="1" value="1">是</el-radio>
                <el-radio :label="0" value="0">否</el-radio>
              </el-radio-group>
              <span style="padding-left: 30px;" v-if="showQualPre">资格预审方法:</span>
              <el-radio-group v-model="bidProChangeForm.prequalificationType" @change="changeRadioMethod" v-if="showQualPre">
                <el-radio :label="1" value="1">合格制</el-radio>
                <el-radio :label="2" value="2">有限数量制</el-radio>
              </el-radio-group>
              <span v-if="showQualPre && showNumber">
                <el-input v-model="bidProChangeForm.prequalificationNumber" style="width: 80px;"></el-input>家
              </span>
            </el-form-item>
            <el-form-item label="是否依法招标:" prop="isAccordToLaw">
              <el-radio-group v-model="bidProChangeForm.isAccordToLaw">
                <el-radio :label="1" value="1">是</el-radio>
                <el-radio :label="0" value="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="是否电子招标:" prop="isElectronicBidding">
              <el-radio-group v-model="bidProChangeForm.isElectronicBidding">
                <el-radio :label="1" value="1">是</el-radio>
                <el-radio :label="0" value="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="是否委托采购:" prop="isEntrustBidding">
              <el-radio-group v-model="bidProChangeForm.isEntrustBidding">
                <el-radio :label="1" value="1">是</el-radio>
                <el-radio :label="0" value="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="招标范围:" prop="content">
              <el-input type="textarea" v-model="bidProChangeForm.content"></el-input>
            </el-form-item>
            <el-form-item label="修改原因:" prop="changeReason">
              <el-input type="textarea" v-model="bidProChangeForm.changeReason"></el-input>
            </el-form-item>
            <el-form-item label="修改依据:">
              <el-upload
                class="upload-demo"
                :action="upLoad"
                :auto-upload="true"
                :on-success="uploadDialogSuccess"
                :on-remove="handleDialogRemove"
                :file-list="fileList"
                multiple>
                <el-button size="small" type="primary">上传附件</el-button>
              </el-upload>
            </el-form-item>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="changeBidProSubmit('bidProChangeForm')">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </span>
      </el-dialog>
      <!-- 招标项目变更end -->
      <!-- 变更历史查询start -->
      <el-dialog
        title="变更历史查询"
        :visible.sync="queryHistoryVisible"
        width="40%">
        <div class="change-query">
          <span class="file-list" v-for="(item, index) in this.changeQueryForm" :key="index">
          <el-card class="box-card" shadow="hover" style="margin-bottom: 20px;">
            <el-form label-width="100px">
              <el-form-item label="变更内容:">
                <span>{{item.changeContent}}</span>
              </el-form-item>
              <el-form-item label="变更原因:">
                <span>{{item.changeReason}}</span>
              </el-form-item>
              <el-form-item label="变更依据:">
                <span class="file-list" v-for="(fileInfo, index) in item.fileInformationList" :key="index">
                <span><el-button type="text" @click="downloadFile(fileInfo.fileName,fileInfo.relativePath)">{{fileInfo.fileName}}&nbsp;</el-button></span>
                </span>
              </el-form-item>
            </el-form>
            <div class="card-foot-cont">
              <span>变更人:{{item.creatorName}}</span>
              <span>变更时间:{{item.createDate | filterDate}}</span>
            </div>
          </el-card>
          </span>
          <!--<el-card class="box-card" shadow="hover" style="margin-bottom: 20px;">
            <el-form :model="changeQueryForm" status-icon :rules="changeQueryRules" ref="changeQueryForm" label-width="100px">
              <el-form-item label="变更内容:">
                <span>{{changeQueryForm.changeContent}}</span>
              </el-form-item>
              <el-form-item label="变更原因:">
                <span>{{changeQueryForm.changeReason}}</span>
              </el-form-item>
              <el-form-item label="变更依据:">
                <span class="file-list" v-for="(item, index) in this.changeQueryForm.fileInformationList" :key="index">
                <span><el-button type="text" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button></span>
                </span>
              </el-form-item>
            </el-form>
            <div class="card-foot-cont">
              <span>变更人:{{changeQueryForm.creatorName}}</span>
              <span>变更时间:{{changeQueryForm.createDate}}</span>
            </div>
          </el-card>-->
        </div>
      </el-dialog>
      <!-- 变更历史查询end -->

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
import {tenderproject, changeRecord, projectManager} from 'api/index'
import {interclassify} from 'api/resource/intermanage/interclassify'
import {downloadFile, commonJs} from 'common/js/common'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      showQualPre: false, // 资格预审方法-隐藏
      showNumber: false, // 有限数量制数量-隐藏
      fileList: [], // 上传列表
      lookDialogVisible: false, // 项目经理-表格查看-弹窗
      lookDialogForm: {}, // 项目经理弹窗-表单
      changeBidProVisible: false, // 招标项目变更-弹窗
      bidProChangeForm: {}, // 招标项目变更-表单
      bidProChangeRules: { // 招标项目变更-规则
        biddingType: [
          { required: true, message: '请选择招标方式', trigger: ['blur', 'change'] }
        ],
        isPrequalification: [
          { required: true, message: '请选择是否资格预审', trigger: ['blur', 'change'] }
        ],
        isAccordToLaw: [
          { required: true, message: '请选择是否依法招标', trigger: ['blur', 'change'] }
        ],
        isElectronicBidding: [
          { required: true, message: '请选择是否电子招标', trigger: ['blur', 'change'] }
        ],
        isEntrustBidding: [
          { required: true, message: '请选择是否委托采购', trigger: ['blur', 'change'] }
        ],
        content: [
          { required: true, message: '请输入招标范围', trigger: 'blur' },
          {min: 1, max: 500, message: '请输入1~500个字符', trigger: ['blur', 'change']}
        ],
        changeReason: [
          { required: true, message: '请输入修改原因', trigger: 'blur' },
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ]
      },
      queryHistoryVisible: false, // 变更历史查询-弹窗
      changeQueryForm: [], // 变更历史查询-表单
      changeQueryRules: {}, // 变更历史查询-规则
      selectApplyForm: {
        entrustEnterprise: {
          objectId: null
        },
        projectType: null
      }, // 选聘备案申请表表单
      bidProForm: {}, // 招标项目表单
      agencyForm: {
        agencyName: '',
        agencyAddress: '',
        agencyPhone: ''
      },
      bidProRules: {},
      tableData: [], // 项目经理表格数据
      upLoad: commonJs.fileUploadUrl,
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
      buttonFlag: true,
      selectApplyCode: ''
    }
  },
  props: ['callbackdata', 'noticeCode', 'operationCheckFlag'],
  computed: {
    /* getSelectApplyCode () {
     return this.selectApplyCode
     } */
  },
  watch: {
    /* getSelectApplyCode () {
      this.getSelectApplyInfo()
    } */
  },
  created () {
    this.getTenderProjectInfo()
    this.getProjectManagerInfo()
    this.checkNoticeStage()
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
    filterPrequalification (type) {
      if (type === 0) {
        return '否'
      } else if (type === 1) {
        return '是'
      }
    },
    filterPrequalificationType (type) {
      if (type === 1) {
        return '合格制'
      } else if (type === 2) {
        return '有限数量制'
      }
    },
    filterIsAccordToLaw (type) {
      if (type === 0) {
        return '否'
      } else if (type === 1) {
        return '是'
      }
    },
    filterIsElectronicBidding (type) {
      if (type === 0) {
        return '否'
      } else if (type === 1) {
        return '是'
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
    }
  },
  /** 方法集 */
  methods: {
    /** 获取当前公告流程状态 */
    checkNoticeStage () {
      if (this.callbackdata && this.noticeCode) {
        this.buttonFlag = false
      }
    },
    /** 获取选中项目的详细信息 */
    getTenderProjectInfo () {
      this.selectApplyForm.fileInfoList = []
      this.bidProForm.fileInfoList = []
      this.bidProForm.contract = {}
      tenderproject.getOneByCode(this.callbackdata).then((res) => {
        this.bidProForm = res.data.tenderProject
        this.selectApplyForm = res.data.tenderProject.selectApply
        if (this.bidProForm.fileInformations) {
          let listInTender = []
          this.bidProForm.fileInformations.forEach(item => {
            if (item.businessType === null) {
              this.bidProForm.contract = item
            } else if (item.businessType === '4') {
              listInTender.push(item)
            }
          })
          this.bidProForm.fileInfoList = listInTender
          let fileData = res.data.tenderProject.selectApply.fileInformationList
          let newFileData = fileData.filter((item) => {
            if (item.businessType !== '2') {
              return item
            }
          })
          this.selectApplyForm.fileInfoList = newFileData
        }
        this.getAgencyInfo(res.data.tenderProject.selectApply.entrustEnterprise)
      })
    },
    /* getSelectApplyInfo () {
      drawrecord.getOneByCode(this.selectApplyCode).then((res) => {
        this.selectApplyForm = res.data.selectApply
        this.getAgencyInfo(res.data.selectApply.entrustEnterprise)
      })
    }, */
    getProjectManagerInfo () {
      projectManager.getByRelatedCode(this.callbackdata).then((res) => {
        this.tableData = res.data.projectManagerList
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
    /** 是否资格预审-单选 */
    changeRadioPretrial (value) {
      if (value === 1) {
        this.showQualPre = true
      } else {
        this.showQualPre = false
        this.showNumber = false
      }
    },
    /** 下载附件文件 */
    downloadFile,
    /** 获取可变更的信息 */
    getBidProChangeInfo () {
      this.changeBidProVisible = true
      tenderproject.getOneByCode(this.callbackdata).then((res) => {
        // 将信息展示在招标项目变更列表中
        this.bidProChangeForm = res.data.tenderProject
        this.changeRadioPretrial(this.bidProChangeForm.isPrequalification)
        this.changeRadioMethod(this.bidProChangeForm.prequalificationType)
      })
    },
    /** 查询变更历史 */
    getChangeRecord () {
      this.queryHistoryVisible = true
      // changeQueryForm
      changeRecord.getByRelatedCode(this.callbackdata).then((res) => {
        this.changeQueryForm = res.data.changeRecordList
      })
    },
    /** 资格预审方法-单选 */
    changeRadioMethod (value) {
      if (value === 2) {
        this.showNumber = true
      } else {
        this.showNumber = false
      }
    },
    /** 上传-合同委托书 */
    uploadSuccess () {},
    /** 招标项目变更弹窗-上传附件 */
    uploadDialogSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        this.fileList = []
        /* fileList.forEach(item => this.fileList.push(item.response.fileInformation)) */
        this.fileList = fileList
      }
    },
    /** 招标项目变更弹窗-删除附件 */
    handleDialogRemove (index) {
      this.fileList.splice(index, 1)
    },
    /** 招标项目变更弹窗-确定 */
    changeBidProSubmit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 变更记录信息
          let list = []
          if (this.fileList.length > 0) {
            this.fileList.forEach(item => list.push(item.response.fileInformation))
          }
          let obj = {
            changeReason: this.bidProChangeForm.changeReason,
            fileInformationList: list
          }
          this.bidProChangeForm.changeRecordList = []
          this.bidProChangeForm.changeRecordList.push(obj)
          this.bidProChangeForm.selectApply = {
            content: this.bidProForm.content
          }
          tenderproject.save(this.bidProChangeForm).then(() => {
            this.changeBidProVisible = false
            if (this.bidProChangeForm.biddingType === 2) {
              this.$router.push({path: '/admin/record/processrecord', query: {status: true}})
            } else {
              this.getTenderProjectInfo()
            }
          })
        } else {
          return false
        }
      })
    },
    /** 项目经理表格-查看 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.lookDialogVisible = true
        this.lookDialogForm = row
      }
    },
    /** 关闭弹框 */
    cancel () {
      this.changeBidProVisible = false
      this.$refs['bidProChangeForm'].resetFields()
      this.showQualPre = false
      this.showNumber = false
      tenderproject.getOneByCode(this.callbackdata).then((res) => {
        // 将信息展示在招标项目变更列表中
        let a = {}
        let b = {}
        a = res.data.tenderProject
        b.projectName = res.data.tenderProject.selectApply.projectName
        this.bidProChangeForm = Object.assign(a, b)
      })
    },
    /** 关闭弹框 */
    handleClose () {
      this.cancel()
    },
    // 上传
    handleSuccess () {}
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
#bidproject-page {
  background: #EAEDF2;
  /** 招标项目 */
  .bidproject-cont {
    .bidpro-title {
      height: 50px;
      line-height: 50px;
      text-align:left;
      padding: 0 20px;
      background:#F7F6FB;
      span {
        font-weight: 800;
      }
      .right-btnbg {
        background: #007dfe;
        float: right;
        color: #fff;
        margin-top: 12px;
        margin-left: 20px;
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
          min-width: 100px;
          background: #F6F7F9;
        }
        tr td:nth-child(even) {
          min-width: 320px;
          text-align: left;
          padding: 0 10px;
        }
      }
    }
  }
  /** 项目经理 */
  .pro-manager {
    background: #fff;
    margin-top: 20px;
    .pro-mana-table {
      padding: 20px;
    }
  }
  /** 弹窗 */
  .pro-dialog {
    .bidpro-change, .change-query {
      .el-form-item__content {
        text-align: left;
      }
    }
    .change-query {
      .box-card {
        .el-card__body {
          padding: 0;
        }
        .card-foot-cont {
          heigth: 30px;
          line-height: 30px;
          background: #eee;
          text-align: right;
          span {
            padding-left: 20px;
            padding-right: 10px;
          }
        }
      }
    }
    .el-dialog__footer {
      text-align: center;
    }
  }
}
</style>
