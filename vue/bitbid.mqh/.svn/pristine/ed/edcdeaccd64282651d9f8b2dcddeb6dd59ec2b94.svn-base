<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="changedetails-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/record/changequery' }">招标变更查询</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/record/changequery/details' }">招标变更详情页</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 主体内容区域start -->
    <div class="changequery-box">
      <div class="change-title">
        <span>招标变更查询</span>
        <el-button size="mini" class="right-btnbg" @click="changeHistoryQuery">变更历史查询</el-button>
      </div>
      <div class="change-form">
        <table class="bidpro-table">
          <tr>
            <td>招标项目名称</td>
            <td colspan="3">{{tableForm.name}}</td>
          </tr>
          <tr>
            <td>招标项目编号</td>
            <td colspan="3">{{tableForm.codeUser}}</td>
          </tr>
          <tr>
            <td rowspan="2">招标人</td>
            <td rowspan="2">{{tableForm.enterpriseName}}</td>
            <td>联系人</td>
            <td>{{tableForm.contacts}}</td>
          </tr>
          <tr>
            <td>招标人联系电话</td>
            <td>{{tableForm.phone}}</td>
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
            <td>{{tableForm.content}}</td>
            <td>是否资格预审</td>
            <td>{{tableForm.isPrequalification | filterPrequalification}}
              <span style="padding-left: 30px;" v-if="tableForm.isPrequalification === 1">资格预审方法:&nbsp;
              {{tableForm.prequalificationType | filterPrequalificationType}}</span>
              <span v-if="tableForm.isPrequalification === 1 && tableForm.prequalificationType === 2">
                &nbsp;{{tableForm.prequalificationNumber}}家
              </span></td>
          </tr>
          <tr>
            <td>招标项目类型</td>
            <td>{{tableForm.tenderProjectType | filterTenderProjectType}}</td>
            <td>采购方式</td>
            <td>{{tableForm.biddingType | filterBiddingType}}</td>
          </tr>
          <tr>
            <td>招标项目地址</td>
            <td colspan="3">{{tableForm.projectAddress}}</td>
          </tr>
          <tr>
            <td>依法必须招标</td>
            <td>{{tableForm.isAccordToLaw | filterIsAccordToLaw}}</td>
            <td>是否电子招标</td>
            <td>{{tableForm.isElectronicBidding | filterIsElectronicBidding}}</td>
          </tr>
          <tr>
            <td>立项批复文件</td>
            <td>
              <span class="file-list" v-for="(item, index) in this.tableForm.fileInformationList" :key="index">
              <span><el-button type="text" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>&nbsp;</span>
              </span>
            </td>
            <td>合同委托书</td>
            <td>
              <span><el-button type="text" @click="downloadFile(tableForm.contract.fileName,tableForm.contract.relativePath)"><span v-if="tableForm.contract">{{tableForm.contract.fileName}}</span></el-button></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <!-- 主体内容区域end -->

    <!--项目经理start-->
    <div class="changequery-box manager-box">
      <div class="change-title">
        <span>项目经理</span>
      </div>
      <div class="manager-tab">
        <el-table
          class="table-box"
          :data="tableData"
          :row-class-name="tableRowClassName"
          border
          style="width: 100%">
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
            align="left"
            header-align="center"
            prop="remark"
            label="描述">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="100">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row, 'look')" type="text" size="medium">查看</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <!--项目经理end-->

    <!--弹窗区域start-->
    <div class="dialog-box">
      <!-- 变更历史查询start -->
      <el-dialog
        title="变更历史查询"
        :visible.sync="queryHistoryVisible"
        width="50%">
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
        </div>
      </el-dialog>
      <!-- 变更历史查询end -->

      <!-- 项目经理查看start -->
      <el-dialog
        title="查看"
        :visible.sync="lookDialogVisible"
        width="40%">
        <div class="bidpro-change">
          <el-form :model="lookManagerForm" label-width="100px">
            <el-form-item label="姓名:" prop="name">
              <span>{{lookManagerForm.name}}</span>
            </el-form-item>
            <el-form-item label="联系方式:" prop="phone">
              <span>{{lookManagerForm.phone}}</span>
            </el-form-item>
            <el-form-item label="描述:" prop="remark">
              <span>{{lookManagerForm.remark}}</span>
            </el-form-item>
            <el-form-item label="证书信息:" prop="info">
              <el-table
                :data="lookManagerForm.certificatesList"
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
                    <span><el-button type="text">可下载</el-button></span>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <!-- 项目经理查看end -->

    </div>
    <!--弹窗区域end-->
  </div>
</template>
<script>
import {tenderproject, projectManager} from 'api/index'
import {interclassify} from 'api/resource/intermanage/interclassify'
import {downloadFile} from 'common/js/common'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      tableForm: {},
      agencyForm: {},
      tableData: [],
      queryHistoryVisible: false,
      lookDialogVisible: false, // 项目经理查看-弹窗
      lookManagerForm: {},
      changeQueryForm: [] // 变更历史查询-表单
    }
  },
  computed: {},
  created () {
    this.getTenderProjectInfo()
  },
  filters: {
    /** 选聘中介类型的选择 */
    filterTenderProjectType (type) {
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
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 项目经理查看 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.lookDialogVisible = true
        this.lookManagerForm = row
      }
    },
    changeHistoryQuery () {
      this.queryHistoryVisible = true
      // changeQueryForm
      tenderproject.getOneByCode(this.$route.query.tenderProjectCode).then((res) => {
        this.changeQueryForm = res.data.tenderProject.changeRecordList
      })
    },
    /** 获取选中项目的详细信息 */
    getTenderProjectInfo () {
      tenderproject.getOneByCode(this.$route.query.tenderProjectCode).then((res) => {
        this.tableForm = res.data.tenderProject
        this.tableForm.name = res.data.tenderProject.selectApply.projectName
        this.tableForm.codeUser = res.data.tenderProject.selectApply.codeUser
        this.tableForm.enterpriseName = res.data.tenderProject.selectApply.enterpriseName
        this.tableForm.contacts = res.data.tenderProject.selectApply.contacts
        this.tableForm.phone = res.data.tenderProject.selectApply.phone
        this.tableForm.projectAddress = res.data.tenderProject.selectApply.projectAddress
        this.tableForm.fileInformationList = res.data.tenderProject.selectApply.fileInformationList
        this.getAgencyInfo(res.data.tenderProject.selectApply.entrustEnterprise)
        this.getProjectManagerInfo(res.data.tenderProject.code)
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
    getProjectManagerInfo (tenderProjectCode) {
      projectManager.getByRelatedCode(tenderProjectCode).then((res) => {
        this.tableData = res.data.projectManagerList
      })
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
    /** 文件下载 */
    downloadFile
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
  #changedetails-page {
    .changequery-box {
      margin: 20px;
      background: #fff;
      .change-title {
        height: 40px;
        line-height: 40px;
        text-align: left;
        padding-left: 20px;
        background: #F7F7F9;
        span {
          font-weight: 800;
        }
        .right-btnbg {
          float: right;
          margin-right: 20px;
          margin-top: 6px;
          color: #fff;
          background: #007dfe;
        }
      }
      .change-form {
        padding: 20px;
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
    .manager-box {
      margin-bottom: 120px;
      .manager-tab {
        padding: 20px;
      }
    }
    /** 弹窗 */
    .dialog-box {
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
    }
  }
</style>
