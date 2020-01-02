<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="filemanage-page">
    <div v-if="this.$route.path == '/admin/files/filemanage'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>档案管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/files/filemanage' }">档案管理列表</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box">
        <el-form :model="searchForm" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="6" style="width:400px">
              <el-form-item label="项目名称:">
                <el-input v-model="searchForm.projectNameLike"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:400px">
              <el-form-item label="项目编号:">
                <el-input v-model="searchForm.codeUserLike"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:400px">
              <el-form-item label="项目类型:">
                <el-select v-model="searchForm.tenderProjectType" clearable placeholder="请输入项目类型" style="width:280px">
                  <el-option label="工程" value="1"></el-option>
                  <el-option label="货物" value="2"></el-option>
                  <el-option label="服务" value="3"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="5" style="width:400px">
              <el-form-item label="采购方式:">
                <el-select v-model="searchForm.biddingType" clearable placeholder="请输入采购方式" style="width:280px">
                  <el-option label="公开招标" value="1"></el-option>
                  <el-option label="邀请招标" value="2"></el-option>
                  <el-option label="谈判采购" value="3"></el-option>
                  <el-option label="询比采购" value="4"></el-option>
                  <el-option label="竞价采购" value="5"></el-option>
                  <el-option label="直接采购" value="6"></el-option>
                  <el-option label="框架协议" value="7"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:400px">
              <el-form-item label="代理机构编号:">
                <el-input v-model="searchForm.userProjectCodeLike"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2" style="padding-left:30px;">
              <el-form-item>
                <el-button type="primary" @click="onSearch('searchForm')" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 搜索区域end -->

      <!-- 主体内容区域start -->
      <div class="maincont-box main-warp">
        <div class="add-file-btn">
          <el-button type="warning" @click="addFilesClick" icon="el-icon-plus" v-if="$store.getters.permissions.includes('/files/filemanage/operation')">新增档案</el-button>
        </div>
        <el-table
          class="table-box"
          :data="tableData"
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
            prop="tenderProjectName"
            label="项目名称">
          </el-table-column>
          <el-table-column
            align="left"
            header-align="center"
            prop="codeUser"
            label="项目编号">
          </el-table-column>
          <el-table-column
            align="center"
            prop="tenderProjectType"
            label="项目类型"
            :formatter="filterTenderProjectType">
          </el-table-column>
          <el-table-column
            align="center"
            prop="biddingType"
            label="采购方式"
            :formatter="filterBiddingType">
          </el-table-column>
          <el-table-column
            align="center"
            prop="projectEnterpriseName"
            label="项目单位">
          </el-table-column>
          <el-table-column
            align="center"
            prop="fileFrom"
            label="档案来源">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="120">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" class="dresult-span" v-if="$store.getters.permissions.includes('/files/filemanage/detail') && (scope.row.isFiledFlag === 1)">查看
              </el-button>
              <el-button @click="handleTableClick(scope.row,'edit')" type="text" size="medium" class="dresult-span" v-if="$store.getters.permissions.includes('/files/filemanage/operation') && (scope.row.isFiledFlag === 0)">编辑
              </el-button>
              <el-button @click="handleTableClick(scope.row,'del')" type="text" size="medium" class="dresult-span" v-if="$store.getters.permissions.includes('/files/filemanage/operation')  && (scope.row.isFiledFlag === 0)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <!--分页-->
        <div class="block-page">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="pageSizeList"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>
      </div>
      <!-- 主体内容区域end -->
      <!--弹窗区域start-->
      <div class="dialog-box">
        <!-- 新增和编辑弹窗start -->
        <el-dialog
          title="新增"
          :visible.sync="dialogVisible"
          width="35%">
          <div class="fileadd-form">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px">
              <el-form-item label="招标方式:" prop="bidType">
                <el-select v-model="ruleForm.bidType" clearable placeholder="请选择招标方式" style="width: 260px" @change="changeBidType" @clear="clear">
                  <el-option label="公开招标" value="1" v-if="this.$store.getters.authUser.enterpriseType === 4"></el-option>
                  <el-option label="邀请招标" value="2"></el-option>
                  <el-option label="谈判采购" value="3"></el-option>
                  <el-option label="询比采购" value="4"></el-option>
                  <el-option label="竞价采购" value="5"></el-option>
                  <el-option label="直接采购" value="6"></el-option>
                  <el-option label="框架协议" value="7"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="招标项目名称:" prop="bidProCode" v-if="this.buttonFlag">
                <el-select v-model="ruleForm.bidProCode" clearable placeholder="请选择项目名称" style="width: 260px" @change="changeBidPro"  @clear="clear">
                  <el-option
                    v-for="item in bidProList"
                    :key="item.code"
                    :label="item.tenderProjectName"
                    :value="item.code">
                  </el-option>
                </el-select>
              </el-form-item>
              <!--<el-form-item label="招标项目名称:" prop="tenderProjectName" v-if="!this.buttonFlag">
                <el-input v-model="ruleForm.tenderProjectName" style="width: 260px"></el-input>
              </el-form-item>-->
              <el-form-item label="招标项目编号:" prop="codeUser" v-if="bidAgency">
                <span>{{ruleForm.codeUser}}</span>
              </el-form-item>
              <el-form-item label="招标项目编号:" prop="codeUser" v-if="!bidAgency">
                <el-input v-model="ruleForm.codeUser" style="width: 260px"></el-input>
              </el-form-item>
            </el-form>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="confirmFilesClick('ruleForm')">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </span>
        </el-dialog>
        <!-- 新增和编辑弹窗end -->
      </div>
      <!--弹窗区域end-->
    </div>
    <div v-else>
      <router-view>
      </router-view>
    </div>
  </div>
</template>
<script>
import {tenderproject, archivesManagement, section} from 'api/index'
export default {
  data () {
    return {
      bidAgency: false, // 代理机构
      searchForm: {}, // 搜索条件表单
      tableData: [], // 表格数据
      dialogVisible: false, // 新增弹窗
      ruleForm: {
        codeUser: '',
        bidType: '',
        bidProCode: ''
      },
      rules: {
        bidType: [
          {required: true, message: '请选择招标方式', trigger: ['blur', 'change']}
        ],
        bidProCode: [
          {required: true, message: '请选择招标项目', trigger: ['blur', 'change']}
        ],
        tenderProjectName: [
          { required: true, message: '请输入招标项目名称', trigger: ['blur', 'change'] }
        ],
        codeUser: [
          { required: true, message: '请输入招标项目编号', trigger: 'blur' },
          {min: 1, max: 64, message: '请输入1~64个字符', trigger: ['blur', 'change']}
        ]
      },
      pageNo: 0,
      currentPage: 1,
      pageSize: 10,
      total: null, // 总条数
      pageSizeList: [10, 20, 30, 40, 50],
      bidProList: [],
      buttonFlag: true,
      selectType: ''
    }
  },
  props: ['bidType'],
  computed: {},
  watch: {
    '$route': 'updateTableData'
  },
  created () {
    if (this.$route.query.key === true) {
      this.dialogVisible = true
    }
    this.getTableData()
  },
  filters: {
  },
  /** 方法集 */
  methods: {
    updateTableData () {
      this.getTableData()
    },
    /** 得到列表上的数据 */
    getTableData () {
      let obj = {}
      if (this.searchForm.userProjectCode) {
        obj = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          creator: this.$store.getters.authUser.userId,
          messageLike: this.searchForm.projectNameLike || '',
          codeUserLike: this.searchForm.codeUserLike || '',
          tenderProjectType: this.searchForm.tenderProjectType || '',
          biddingType: this.searchForm.biddingType || '',
          userProjectCode: this.searchForm.userProjectCodeLike || ''
        }
      } else {
        obj = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          creator: this.$store.getters.authUser.userId,
          messageLike: this.searchForm.projectNameLike || '',
          codeUserLike: this.searchForm.codeUserLike || '',
          tenderProjectType: this.searchForm.tenderProjectType || '',
          biddingType: this.searchForm.biddingType || ''
        }
      }
      archivesManagement.getList(obj).then((res) => {
        if (res.data.archivesList && res.data.archivesList.list) {
          this.tableData = res.data.archivesList.list
          this.total = res.data.archivesList.total
          this.tableData.map(item => {
            if (item.tenderProject) {
              item.biddingType = item.tenderProject.biddingType
              item.tenderProjectName = item.tenderProject.tenderProjectName
              item.tenderProjectType = item.tenderProject.tenderProjectType
              item.projectEnterpriseName = item.tenderProject.projectEnterpriseName
              item.isFiledFlag = item.tenderProject.isFiled
              if (item.tenderProject.selectApply || item.tenderProject.projectPlan) {
                item.fileFrom = '业务系统'
                item.codeUser = item.tenderProject.selectApply ? item.tenderProject.selectApply.codeUser : item.tenderProject.customProjectCode
              } else {
                item.fileFrom = '新增'
                item.codeUser = item.tenderProject.customProjectCode
              }
            }
          })
        }
      })
    },
    onSearch () {
      this.getTableData()
    },
    /** 清空下拉选项 */
    clear () {
      if (this.ruleForm.bidProCode) {
        this.ruleForm.bidProCode = ''
      }
      if (this.ruleForm.bidType) {
        this.ruleForm.bidType = ''
      }
      this.bidProList = []
    },
    filterTenderProjectType (row, column) {
      if (row.tenderProjectType === 1) {
        return '工程'
      } else if (row.tenderProjectType === 2) {
        return '货物'
      } else if (row.tenderProjectType === 3) {
        return '服务'
      }
    },
    filterBiddingType (row, column) {
      if (row.biddingType === 1) {
        return '公开招标'
      } else if (row.biddingType === 2) {
        return '邀请招标'
      } else if (row.biddingType === 3) {
        return '谈判采购'
      } else if (row.biddingType === 4) {
        return '询比采购'
      } else if (row.biddingType === 5) {
        return '竞价采购'
      } else if (row.biddingType === 6) {
        return '直接采购'
      } else if (row.biddingType === 7) {
        return '框架协议'
      }
    },
    /** 改变招标方式时查出招标项目信息 */
    changeBidType (bidType) {
      if (this.ruleForm.bidType) {
        if (this.ruleForm.bidProCode) {
          this.ruleForm.bidProCode = ''
        }
        if (Number(bidType) === 1 || Number(bidType) === 2) {
          this.buttonFlag = true
          this.bidAgency = true
        } else if (Number(bidType) !== 1 && Number(bidType) !== 2) {
          this.bidAgency = false
          /* if (this.$store.getters.authUser.enterpriseType !== 4) {
           this.buttonFlag = false
           } */
        }
        this.getInfoByConditions(bidType)
      }
    },
    /** 根据条件查询下拉框展示的数据 */
    getInfoByConditions (bidType) {
      let obj = {} // 查询条件
      if (this.$store.getters.authUser.enterpriseType === 4) {
        // 公开招标：来源于招标过程备案，继续委托招标的项目（已经包含已经抽签且采购方式为公开招标的招标计划）
        if (Number(bidType) === 1) {
          obj = {
            isFiled: 0,
            isConfirm: 1,
            biddingType: this.ruleForm.bidType,
            entrustEnterpriseId: this.$store.getters.authUser.enterpriseId,
            isEntrustBidding: 1
          }
        } else if (Number(bidType) === 2) {
          // 邀请招标：1.来源于招标过程备案，继续委托招标的项目 2.招标计划中已经抽签且采购方式为邀请招标的招标计划
          obj = {
            isFiled: 0,
            biddingType: this.ruleForm.bidType,
            entrustEnterpriseId: this.$store.getters.authUser.enterpriseId,
            isEntrustBidding: 1
          }
        } else {
          // 其他采购方式：招标计划中已经抽签且采购方式为其他采购方式的招标计划
          obj = {
            isFiled: 0,
            biddingType: this.ruleForm.bidType,
            entrustEnterpriseId: this.$store.getters.authUser.enterpriseId
          }
        }
      } else {
        // 邀请招标：1.来源于招标过程备案，非继续委托招标的项目 2.招标计划中未进行抽签且采购方式为邀请招标的招标计划
        if (Number(bidType) === 2) {
          obj = {
            isFiled: 0,
            biddingType: this.ruleForm.bidType,
            projectEnterpriseId: this.$store.getters.authUser.enterpriseId,
            isEntrustBidding: 0
          }
        } else {
          // 其他采购方式：招标计划中未抽签且采购方式为其他采购方式的招标计划
          obj = {
            isFiled: 0,
            biddingType: this.ruleForm.bidType,
            projectEnterpriseId: this.$store.getters.authUser.enterpriseId
          }
        }
      }
      tenderproject.getListInArchives(obj).then((res) => {
        // this.bidProList = res.data.tenderProjectList.list
        let tenderProjectList = []
        let projectPlanListByProjectUnit = []
        let projectPlanListByAgencyUnit = []
        if (res.data.tenderProjectList && res.data.tenderProjectList.list.length > 0) {
          res.data.tenderProjectList.list.forEach(tenderProject => {
            if (this.checkTenderProjectStatus(tenderProject.noticeProgressList).length > 0) {
              tenderProject.selectType = 0 // 来源于招标过程备案中，用于单条查询功能
              tenderProjectList.push(tenderProject)
            }
          })
        }
        if (res.data.projectPlanList && res.data.projectPlanList.length > 0) {
          res.data.projectPlanList.forEach(item => {
            item.selectType = 1 // 来源于招标计划中，用于单条查询功能
            item.tenderProjectName = item.projectPlanName
          })
          if (this.$store.getters.authUser.enterpriseType === 4) {
            projectPlanListByAgencyUnit = res.data.projectPlanList
          } else {
            projectPlanListByProjectUnit = res.data.projectPlanList
          }
        }
        this.bidProList = tenderProjectList.concat(projectPlanListByAgencyUnit.length > 0 ? projectPlanListByAgencyUnit : projectPlanListByProjectUnit)
      })
    },
    /** 判断招标项目是否已完成（即已有的公告全部完成） */
    checkTenderProjectStatus (noticeProgressList) {
      let results = []
      let flag = false
      if (noticeProgressList) {
        for (let i = 0; i < noticeProgressList.length; i++) {
          if (noticeProgressList[i].progressStage !== 8) {
            flag = false
            break
          } else {
            flag = true
          }
        }
        if (flag) {
          results = noticeProgressList
        }
      }
      return results
    },
    /** 根据选择的招标项目带出招标项目编号 */
    changeBidPro (code) {
      if (code) {
        this.selectType = this.getSelectType(code)
        if (this.selectType === 0) {
          this.bidAgency = true
          tenderproject.getCodeUserByCode(code).then((res) => {
            this.ruleForm.codeUser = res.data.codeUser
          })
        } else if (this.selectType === 1) {
          this.bidAgency = false
          this.ruleForm.codeUser = ''
        }
      } else {
        this.ruleForm.codeUser = ''
        // this.bidAgency = false
      }
    },
    /** 获取选择的数据来源（招标过程备案、招标计划） */
    getSelectType (code) {
      let selectType = ''
      this.bidProList.forEach(item => {
        if (item.code === code) {
          selectType = item.selectType
        }
      })
      return selectType
    },
    /** 清空表单的操作 */
    cancel () {
      this.dialogVisible = false
      if (this.bidAgency) {
        this.ruleForm.codeUser = ''
        let fields = this.$refs['ruleForm'].fields
        for (let i = 0; i < fields.length; i++) {
          if (fields[i].prop === 'bidType' || fields[i].prop === 'bidProCode') {
            // 通过唯一的prop属性值相同来判别是哪个输入框
            fields[i].resetField()
          }
        }
      } else {
        this.$refs['ruleForm'].resetFields()
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.getTableData(this.pageNo, this.pageSize, function (resp) {
          likeThis.total = resp.data.archivesList.list.total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 新增档案 */
    addFilesClick () {
      /* this.$nextTick(() => {
        this.cancel()
      }) */
      this.dialogVisible = true
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'edit')) {
        this.$router.push({path: '/admin/files/filemanage/editfile', query: {operation: 'edit', bidType: row.biddingType, code: row.code}})
      } else if (Object.is(type, 'del')) {
        this.delRow(row)
      } else if (Object.is(type, 'look')) {
        this.$router.push({path: '/admin/files/filemanage/editfile', query: {type: false, operation: 'look', bidType: row.biddingType, code: row.code}})
      }
    },
    /** 删除操作 */
    delRow (row) {
      // 查找该档案信息对应的招标项目下的标段信息是否已全部归档
      section.getAllByTenderCode(row.tenderProjectCode).then((res) => {
        let isFiledNum = 0
        res.data.sectionList.forEach(section => {
          if (section.archivingStatus === 2) {
            isFiledNum++
          }
        })
        let msg = (isFiledNum === 0) ? '确定要删除该条数据？' : '该招标项目下存在' + isFiledNum + '条已归档标段，确定要删除该条数据？'
        this.$confirm(msg, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          beforeClose: (action, instance, done) => {
            done()
          }
        }).then(() => {
          archivesManagement.delete(row.objectId).then((res) => {
            if (res.data.resCode === '0000') {
              this.getTableData()
            }
          })
        }).catch(() => {
          return false
        })
      })
    },
    /** 确定-新增 */
    confirmFilesClick (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 跳转路由-传参
          let bidType = this.ruleForm.bidType
          let tenderProjectCode = ''
          let projectPlanCode = ''
          if (Number(bidType) === 2) {
            if (this.selectType === 0) {
              tenderProjectCode = this.ruleForm.bidProCode
            } else if (this.selectType === 1) {
              projectPlanCode = this.ruleForm.bidProCode
            }
          } else if (Number(bidType) !== 1 && Number(bidType) !== 2) {
            projectPlanCode = this.ruleForm.bidProCode
          } else {
            tenderProjectCode = this.ruleForm.bidProCode
          }
          let tenderProjectName = this.ruleForm.tenderProjectName ? this.ruleForm.tenderProjectName : ''
          let codeUser = this.ruleForm.codeUser ? this.ruleForm.codeUser : ''
          this.cancel()
          this.$router.push({path: '/admin/files/filemanage/editfile', query: {operation: 'save', bidType: bidType, tenderProjectCode: tenderProjectCode, tenderProjectName: tenderProjectName, codeUser: codeUser, projectPlanCode: projectPlanCode, selectType: this.selectType}})
        } else {
          return false
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
#filemanage-page {
  .maincont-box {
    margin-bottom: 130px;
    .add-file-btn {
      padding-bottom: 20px;
      text-align: left;
    }
  }
  /** 弹窗 */
  .dialog-box {
    .el-form-item__content {
      text-align: left;
    }
    .el-dialog__footer {
      text-align: center;
    }
  }
  .block-page {
    padding: 20px;
  }
}
</style>
