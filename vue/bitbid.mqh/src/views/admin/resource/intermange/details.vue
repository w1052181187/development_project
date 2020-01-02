<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="intermange-details-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资源管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/resource/intermange' }">中介机构管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/resource/intermange/detailsinter' }">查看</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="returnMinPage" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 主体内容区域start -->
    <div class="detailsinter-info">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClickTabs">
        <!-- 专家基本信息start -->
        <el-tab-pane label="企业基本信息" name="first" class="enterprise-tabs">
          <el-form :model="enterpriseInfoForm" label-width="100px">
            <el-row>
              <el-col :span="6">
                <el-form-item label="企业名称:">
                  <span>{{enterpriseInfoForm.name}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="中介类型:">
                  <span>{{enterpriseInfoForm.type |filterType}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="所属区域:">
                  <span>{{enterpriseInfoForm.interClassify}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="状态:">
                  <span>{{enterpriseInfoForm.status |filterStatus}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="联系人:">
                  <span>{{enterpriseInfoForm.contactsName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="联系电话:">
                  <span>{{enterpriseInfoForm.phone}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="公司地址:">
                  <span>{{enterpriseInfoForm.address}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="18">
                <el-form-item label="企业证件:">
                  <el-table
                    :data="enterpriseTableData"
                    border
                    style="width: 100%">
                    <el-table-column
                      align="left"
                      prop="certificateName"
                      label="证件名称">
                    </el-table-column>
                    <el-table-column
                      align="left"
                      prop="fileInformation"
                      label="证件信息（附件）">
                      <template slot-scope = "scope">
                        <span><el-button type="text" @click="downloadFile(scope.row.fileInformation.fileName,scope.row.fileInformation.relativePath)" style="padding-right: 30px;">{{scope.row.fileInformation.fileName}}</el-button></span>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div class="set-line"></div><!-- 分割线 -->

          <!-- 项目经理start -->
          <div class="det-project-manager">
            <div class="manager-title"><h4>项目经理</h4></div>
            <div class="manager-table">
              <el-table
                :data="proManagerTableData"
                :row-class-name="tableRowClassName"
                border
                style="width: 100%">
                <el-table-column
                  type="index"
                  label="序号"
                  :index="indexMethod"
                  width="55">
                </el-table-column>
                <el-table-column
                  align="left"
                  header-align="center"
                  prop="enterpriseName"
                  label="中介机构名称">
                </el-table-column>
                <el-table-column
                  align="center"
                  prop="name"
                  label="姓名">
                </el-table-column>
                <el-table-column
                  align="center"
                  prop="phone"
                  label="联系方式">
                </el-table-column>
                <el-table-column
                  align="center"
                  prop="lastUpdateDate"
                  label="维护时间"
                  :formatter="filterLastUpdateDate">
                </el-table-column>
                <el-table-column
                  align="left"
                  header-align="center"
                  prop="remark"
                  label="描述"
                  width="100">
                </el-table-column>
                <el-table-column
                  align="center"
                  label="操作"
                  width="80">
                  <template slot-scope="scope">
                    <el-tooltip content="查看" placement="top">
                      <el-button @click="handleTableClick(scope.row,'manager')" type="info" size="small" icon="el-icon-search"></el-button>
                    </el-tooltip>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
          <!-- 项目经理end -->
          <div class="set-line"></div><!-- 分割线 -->
          <!-- 法人代表start -->
          <div class="det-legal-rep">
            <div class="manager-title"><h4>法人代表</h4></div>
            <el-form :model="legalInfoForm" label-width="100px">
              <el-row>
                <el-col :span="6">
                  <el-form-item label="姓名:">
                    <span>{{legalInfoForm.legalPerson}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="联系方式:">
                    <span>{{legalInfoForm.legalPhone}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <!--<el-row>
                <el-col :span="6">
                  <el-form-item label="身份证附件:">
                    <span class="file-list">
                    <span><el-button type="text" @click="downloadFile(legalInfoForm.fileName,legalInfoForm.relativePath )" style="padding-right: 30px;">{{legalInfoForm.fileName}}</el-button></span>
                    </span>
                  </el-form-item>
                </el-col>
              </el-row>-->
            </el-form>
          </div>
          <!-- 法人代表end -->
        </el-tab-pane>
        <!-- 专家基本信息end -->

        <!-- 工作及评价start -->
        <el-tab-pane label="工作及评价" name="second" class="work-evaluate-pane">
          <div class="work-eval-title"><span>共参与项目{{this.workTableData.length}}个</span></div>
          <div class="evaluate-table">
            <el-table
              :data="workTableData"
              :row-class-name="tableRowClassName"
              border
              style="width: 100%">
              <el-table-column
                align="center"
                prop="tenderProjectName"
                label="项目名称">
              </el-table-column>
              <el-table-column
                align="center"
                prop="codeUser"
                label="项目编号">
              </el-table-column>
              <el-table-column
                align="center"
                prop="drawLotsTime"
                label="评标日期"
                :formatter="filterDate">
              </el-table-column>
              <el-table-column
                align="center"
                label="操作"
                width="80">
                <template slot-scope="scope">
                  <el-tooltip content="查看评价" placement="top">
                    <el-button @click="handleTableClick(scope.row,'look')" type="primary" size="small" icon="el-icon-tickets"></el-button>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="set-line"></div><!-- 分割线 -->

          <!-- 评价区域start -->
          <div class="det-evaluate-box">
            <div class="evaluate-tit"><h4>评价</h4></div>
            <div class="evaluate-cont">
              <el-form :model="allEvaluateForm" label-width="140px" class="demo-dynamic" :inline="true">
              <el-row>
                <el-col :span="22">
                  <p>该企业共{{allEvaluateForm.evaluateNums}}次获评，好评度为{{allEvaluateForm.favourableValue}}%</p>
                  <div class="evaluate-star">
                    <el-row>
                      <el-col :span="1"><span>好评度</span></el-col>
                      <el-col :span="22">
                        <el-rate
                          v-model="allEvaluateForm.rateValue"
                          :allow-half="true"
                          disabled
                          :colors="['#46C47B', '#46C47B', '#46C47B']">
                        </el-rate>
                      </el-col>
                    </el-row>
                  </div>
                  <div class="evaluate-box">
                    <span>好评:</span>
                    <el-form-item v-for="(domain, index) in allEvaluateForm.favourableCommentList" :key="index">
                      <el-button type="primary" round>{{domain.labelName}}&nbsp;{{domain.nums}}</el-button>
                    </el-form-item>
                  </div>
                  <div class="evaluate-box">
                    <span>中评:</span>
                    <el-form-item v-for="(domain, index) in allEvaluateForm.middleCommentList" :key="index">
                      <el-button type="success" round>{{domain.labelName}}&nbsp;{{domain.nums}}</el-button>
                    </el-form-item>
                  </div>
                  <div class="evaluate-box">
                    <span>差评:</span>
                    <el-form-item v-for="(domain, index) in allEvaluateForm.badCommentList" :key="index">
                      <el-button type="warning" round>{{domain.labelName}}&nbsp;{{domain.nums}}</el-button>
                      <span>{{domain.evaluateRemark}}</span>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              </el-form>
            </div>
          </div>
          <!-- 评价区域end -->
        </el-tab-pane>
        <!-- 工作及评价end -->
      </el-tabs>
    </div>
    <!-- 主体内容区域end -->

    <!-- 弹窗区域start -->
    <div class="dialog-box">
      <el-dialog
        title="评价详情"
        :visible.sync="dialogVisible"
        width="40%"
        center>
        <!-- 项目基本信息 -->
        <span class="title-info">项目基本信息</span>
        <el-form :model="dialogInfoForm" label-width="100px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="招标项目名称:">
                <span>{{dialogInfoForm.tenderProjectName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="招标项目编号:">
                <span>{{dialogInfoForm.codeUser}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="项目单位:">
                <span>{{dialogInfoForm.projectEnterpriseName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="需求方:">
                <span>{{dialogInfoForm.requiresEnterpriseName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="选聘备案时间:">
                <span>{{dialogInfoForm.approveTime | filterEvaluateDate}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="中签单位:">
                <span>{{dialogInfoForm.agencyEnterpriseName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="公告时间:">
                <span>{{dialogInfoForm.firstNoticeApproveTime | filterEvaluateDate}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="开标时间:">
                <span>{{dialogInfoForm.firstActualBidOpenTime | filterEvaluateDate}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <!-- 工作评价 -->
        <span class="title-info">工作评价</span>
        <el-form :model="evaluateForm" label-width="140px" class="demo-dynamic" :inline="true">
        <div class="eval-cont">
          <div class="evaluate-box">
            <span class="span-left">评价时间:</span>
            <span>{{evaluateForm.evaluateDate | filterEvaluateDate}}</span>
          </div>
          <div class="evaluate-box">
            <span class="span-left">好评:</span>
            <el-form-item v-for="(domain, index) in evaluateForm.favourableCommentList" :key="index">
               <el-button type="primary" round>{{domain.labelName}}</el-button>
            </el-form-item>
          </div>
          <div class="evaluate-box">
            <span class="span-left">中评:</span>
            <el-form-item v-for="(domain, index) in evaluateForm.middleCommentList" :key="index">
              <el-button type="success" round>{{domain.labelName}}</el-button>
            </el-form-item>
          </div>
          <div class="evaluate-box">
            <span class="span-left">差评:</span>
            <el-form-item v-for="(domain, index) in evaluateForm.badCommentList" :key="index">
              <el-button type="warning" round>{{domain.labelName}}</el-button>
              <span>{{domain.evaluateRemark}}</span>
            </el-form-item>
          </div>
        </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>

      <!--项目经理查看start-->
      <el-dialog
        title="查看"
        :visible.sync="proDialogVisible"
        width="30%"
        center>
        <el-form label-width="80px" :model="proLookForm">
          <el-form-item label="姓名:">
            <span>{{proLookForm.name}}</span>
          </el-form-item>
          <el-form-item label="联系方式:">
            <span>{{proLookForm.phone}}</span>
          </el-form-item>
          <el-form-item label="描述:">
            <span>{{proLookForm.remark}}</span>
          </el-form-item>
          <el-form-item label="个人证书:">
            <el-table
              :data="proTableData"
              style="width: 100%">
              <el-table-column
                prop="certificateName"
                label="证书名称">
              </el-table-column>
              <el-table-column
                prop="fileInformation"
                label="证件信息（附件）">
                <template slot-scope="scope">
                  <span><el-button type="text" @click="downloadFile(scope.row.fileInformation.fileName,scope.row.fileInformation.relativePath)" style="padding-right: 30px;">{{scope.row.fileInformation.fileName}}</el-button></span>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!--项目经理查看end-->
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import {intermanage, projectEvaluate, tenderproject} from 'api/index'
import {downloadFile} from 'common/js/common'
import {interclassify} from 'api/resource/intermanage/interclassify'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      platFlag: this.$route.query.platFlag,
      proDialogVisible: false, // 项目经理-查看弹窗
      proLookForm: {},
      proTableData: [],
      dialogInfoForm: {}, // 弹窗表单
      dialogVisible: false,
      currentPage: 1,
      activeName: 'first',
      enterpriseInfoForm: {}, // 企业基本信息表单
      fileInformation: [],
      fileInformationList: [],
      enterpriseTableData: [], // 企业表格数据
      proManagerTableData: [], // 项目经理表格数据
      legalInfoForm: {}, // 法人代表-表单
      workTableData: [], // 工作及评价-表格s
      evaluateForm: {
        evaluateDate: '',
        favourableCommentList: [],
        middleCommentList: [],
        badCommentList: []
      }, // 评价内容
      flag: 0,
      allEvaluateForm: {
        evaluateNums: 0,
        favourableValue: 0,
        rateValue: 0, // 好评
        favourableCommentList: [],
        middleCommentList: [],
        badCommentList: []
      }
    }
  },
  computed: {},
  created () {
  },
  /*
  * 方法集
  */
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 格式化维护时间 */
    filterLastUpdateDate (row, column) {
      let date = new Date(row.lastUpdateDate)
      return formatDate(date, 'yyyy-MM-dd hh:mm')
    },
    // 项目经理-查看 工作评价-查看
    handleTableClick (row, type) {
      if (Object.is(type, 'manager')) {
        if (row) {
          this.proLookForm = row
          this.proTableData = this.proLookForm.certificatesList
          this.proDialogVisible = true
        }
      } else if (Object.is(type, 'look')) {
        let labelType = ''
        if (this.$store.getters.authUser.enterpriseType === 4) {
          labelType = 2
        } else {
          labelType = 1
        }
        tenderproject.getOneInResource(row.code, Number(labelType), Number('')).then((res) => {
          this.dialogInfoForm = res.data.tenderProject
          if (res.data.notice) {
            this.dialogInfoForm.firstNoticeApproveTime = res.data.notice.approvePassTime
            this.dialogInfoForm.firstActualBidOpenTime = res.data.notice.firstActualBidOpenTime
          }
          if (res.data.projectEvaluate) {
            this.evaluateForm.evaluateDate = res.data.projectEvaluate.lastUpdateDate
            this.classifyEvaluateResults(res.data.projectEvaluate.evaluateInfoList)
            this.dialogVisible = true
          }
        })
      }
    },
    /** 对评价结果进行分类 */
    classifyEvaluateResults (evaluateInfoList) {
      this.evaluateForm.favourableCommentList = []
      this.evaluateForm.middleCommentList = []
      this.evaluateForm.badCommentList = []
      if (evaluateInfoList && evaluateInfoList.length > 0) {
        evaluateInfoList.forEach(item => {
          if (Number(item.evaluateResult) === 1) { // 好评
            this.evaluateForm.favourableCommentList.push(item)
          } else if (Number(item.evaluateResult) === 2) { // 中评
            this.evaluateForm.middleCommentList.push(item)
          } else {
            this.evaluateForm.badCommentList.push(item)
          }
        })
      }
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
    /** 列表数据展示 */
    getOneIntermangeData () {
      intermanage.getOne(this.$route.query.objectId).then((res) => {
        let tempObj = res.data.enterprise
        this.enterpriseInfoForm = Object.assign(tempObj, tempObj.enterpriseExpend)
        this.proManagerTableData = res.data.enterprise.projectManagerList
        this.legalInfoForm = this.enterpriseInfoForm
        if (this.enterpriseInfoForm || this.enterpriseInfoForm.provinceId || this.enterpriseInfoForm.cityId || this.enterpriseInfoForm.countyId) {
          this.enterpriseInfoForm.interClassify = ''
          let provinceId = this.getLabelName(interclassify, this.enterpriseInfoForm.provinceId)
          let cityId = this.getLabelName(interclassify, this.enterpriseInfoForm.cityId)
          let countyId = this.getLabelName(interclassify, this.enterpriseInfoForm.countyId)
          if (provinceId.node) {
            this.enterpriseInfoForm.interClassify += `${provinceId.node.label}`
          }
          if (cityId.node) {
            this.enterpriseInfoForm.interClassify += `${cityId.node.label}`
          }
          if (countyId.node) {
            this.enterpriseInfoForm.interClassify += `${countyId.node.label}`
          }
        }
        this.enterpriseTableData = []
        if (tempObj.certificatesList) {
          if (tempObj.certificatesList.length > 0) {
            for (let i = 0; i < tempObj.certificatesList.length; i++) {
              if (tempObj.certificatesList[i].certificatesType === 1) {
                this.legalInfoForm = Object.assign(tempObj, tempObj.certificatesList[i].fileInformation)
              } else {
                this.enterpriseTableData.push(tempObj.certificatesList[i])
              }
            }
          }
        }
      })
    },
    /** 进行查看操作后返回当前页 */
    returnMinPage () {
      if (this.platFlag === 'platFlag') {
        this.$router.go(-1)
      } else {
        this.$router.push({path: '/admin/resource/intermange', query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
      }
    },
    /** 下载附件文件 */
    downloadFile,
    /** 切换表单操作 */
    handleClickTabs (data) {
      this.pageSize = 10
      this.pageNo = 0
      this.currentPage = 1
      this.activeName = data.name
      // 页面切换后重新获取数据
      if (this.activeName === 'first') {
        this.getOneIntermangeData()
      } else {
        this.getEvaluateInfo()
      }
    },
    /** 获取评价信息 */
    getEvaluateInfo () {
      projectEvaluate.getListInResource({
        // targetEnterpriseId: 10229,
        targetEnterpriseId: this.$route.query.objectId,
        projectEvaluateType: 1,
        status: 1
        // auditStatus: 1
      }).then((res) => {
        this.workTableData = res.data.tenderProjectList
        // 获取评价信息
        if (res.data.projectEvaluateList.length > 0) {
          this.getAllEvaluateFormInfo(res.data.projectEvaluateList)
        }
      })
    },
    getAllEvaluateFormInfo (projectEvaluateList) {
      this.allEvaluateForm.favourableCommentList = []
      this.allEvaluateForm.middleCommentList = []
      this.allEvaluateForm.badCommentList = []
      projectEvaluateList.forEach(item => {
        this.classifyEvaluateFormInfo(item.evaluateInfoList)
      })
      let total = this.calSum(this.allEvaluateForm.favourableCommentList) + this.calSum(this.allEvaluateForm.middleCommentList) + this.calSum(this.allEvaluateForm.badCommentList)
      let favourableCommentNums = this.calSum(this.allEvaluateForm.favourableCommentList)
      this.allEvaluateForm.evaluateNums = Number(total)
      this.allEvaluateForm.favourableValue = ((Number(favourableCommentNums) / Number(total)) * 100).toFixed(2)
      this.allEvaluateForm.rateValue = Number(this.allEvaluateForm.favourableValue) / 20
    },
    /** 得到标签中的总次数 */
    calSum (arr) {
      let nums = 0
      if (arr.length > 0) {
        arr.forEach((item) => {
          nums += item.nums
        })
      }
      return nums
    },
    classifyEvaluateFormInfo (evaluateInfoList) {
      if (evaluateInfoList && evaluateInfoList.length > 0) {
        evaluateInfoList.forEach(item => {
          if (Number(item.evaluateResult) === 1) { // 好评
            if (this.allEvaluateForm.favourableCommentList.length === 0) {
              item.nums = 1
              this.allEvaluateForm.favourableCommentList.push(item)
            } else {
              let flag = this.compareResult(item, this.allEvaluateForm.favourableCommentList)
              if (!flag) {
                item.nums = 1
                this.allEvaluateForm.favourableCommentList.push(item)
              }
            }
          } else if (Number(item.evaluateResult) === 2) { // 中评
            if (this.allEvaluateForm.middleCommentList.length === 0) {
              item.nums = 1
              this.allEvaluateForm.middleCommentList.push(item)
            } else {
              let flag = this.compareResult(item, this.allEvaluateForm.middleCommentList)
              if (!flag) {
                item.nums = 1
                this.allEvaluateForm.middleCommentList.push(item)
              }
            }
          } else {
            if (this.allEvaluateForm.badCommentList.length === 0) {
              item.nums = 1
              this.allEvaluateForm.badCommentList.push(item)
            } else {
              let flag = this.compareResult(item, this.allEvaluateForm.badCommentList)
              if (!flag) {
                item.nums = 1
                this.allEvaluateForm.badCommentList.push(item)
              }
            }
          }
        })
      }
    },
    compareResult (evaluateInfo, commentList) {
      let flag = false
      for (let i = 0; i < commentList.length; i++) {
        if (commentList[i].labelName === evaluateInfo.labelName) {
          commentList[i].nums++
          flag = true
          break
        }
      }
      return flag
    },
    filterDate (row, column) {
      let date = ''
      if (row.drawLotsTime) {
        date = new Date(row.drawLotsTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      } else {
        return null
      }
    }
  },
  /*
  * 实例创建完成
  */
  mounted () {
    this.getOneIntermangeData()
  },
  /*
  * 过滤
  */
  filters: {
    /** 格式化 */
    filterType (row, column) {
      if (row === 4) {
        return '招标管理机构'
      } else if (row === 5) {
        return '造价咨询机构'
      } else if (row === 6) {
        return '审计评估机构'
      } else if (row === 7) {
        return '法律服务机构'
      }
    },
    filterStatus (row, column) {
      if (row === 0) {
        return '激活'
      } else if (row === 1) {
        return '锁定'
      }
    },
    filterEvaluateDate (value) {
      let date = ''
      if (value) {
        date = new Date(value)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      } else {
        return null
      }
    }
  }
}
</script>
<style lang="less">
#intermange-details-page {
  /*企业基本信息*/
  .detailsinter-info {
    margin: 20px 20px 120px;
    background-color: #fff;
    .el-tabs__header.is-top {
      margin: 20px;
      padding-top: 20px;
    }
    .set-line {
      height: 20px;
      background-color: #EAEDF2;
    }
    .manager-table {
      margin: 20px;
    }
    .manager-title {
      background-color: #F7F6FB;
      height: 40px;
      text-align: left;
      h4 {
        margin: 0;
        padding-top: 10px;
        padding-left: 20px;
      }
    }
    .el-form-item__content {
      text-align: left;
    }
  }
  /*工作及评价*/
  .work-evaluate-pane {
    .work-eval-title {
      text-align: left;
      margin: 0 20px;
    }
    .evaluate-table {
      margin: 0 20px 20px;
    }
    .set-line {
      height: 20px;
      background-color: #EAEDF2;
    }
    .evaluate-star {
      margin-bottom: 40px;
    }
    .det-evaluate-box {
      text-align: left;
      height: 400px;
      .evaluate-tit {
        background-color: #F7F6FB;
        height: 40px;
        h4 {
          margin: 0;
          padding-top: 10px;
          padding-left: 20px;
        }
      }
      .evaluate-box {
        margin-top: 20px;
      }
      .evaluate-cont {
        margin: 20px;
      }
    }
  }
  /*弹窗样式*/
  .dialog-box {
    .title-info {
      width: 100%;
      display: inline-block;
      background-color: #F7F6FB;
      padding: 8px 8px;
      text-align: left;
      font-weight: 800;
    }
    .eval-cont {
      background: #F7F7F7;
      margin-top: 20px;
      padding: 20px;
      .evaluate-box {
        text-align: left;
        margin: 20px 0;
        .span-left {
          display: inline-block;
          width: 80px;
        }
      }
    }
    .el-form-item__content {
      text-align: left;
    }
  }
}
</style>
