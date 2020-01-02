<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="tend-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <!-- <el-breadcrumb-item :to="{ path: '/admin/1' }">首页</el-breadcrumb-item> -->
        <el-breadcrumb-item > 组织机构</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/tendagency' }">招标机构管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!--搜索start-->
    <div class="search-box">
      <el-form :model="searchForm" label-width="100px" class="demo-ruleForm">
        <el-row :gutter="20">
          <el-col :span="5" style="width:400px">
            <el-form-item label="企业名称:">
              <el-input type="text" v-model="searchForm.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="2" style="padding-left:30px;">
            <el-form-item>
              <el-button type="primary" @click="onSearch" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!--搜索end-->
    <div class="maincont-box tend-cont">
      <el-tabs v-model="tabName" type="card" @tab-click="handleClick">
        <el-tab-pane label="合作机构" name="first" value="first">
          <div class="tend-add-btn">
            <el-button type="warning" icon="el-icon-plus" @click="addTendCopm">新增招标机构</el-button>
          </div>
          <div class="cooper-tree">
            <el-row :gutter="20" style="width:100%">
              <el-col :span="8" v-for="(item,index) in formList" :key="index" style="padding-bottom:20px;">
                <!-- Card区域 -->
                <el-card class="box-card" style="height:320px">
                  <div slot="header" class="clearfix">
                    <span class="card-tit-weight">{{item.label}}</span>
                    <el-button style="padding: 3px 0;line-height: 1;" type="text" @click="moveInBlacklist(item.id)">移入</el-button>
                    <el-button style="padding: 3px 0;line-height: 1;" type="text" @click="deleteCard(item.id)">删除</el-button>
                  </div>
                  <div class="text item">
                    <el-tree
                      style="text-align:left;"
                      :data="[item]"
                      node-key="id"
                      default-expand-all
                      :expand-on-click-node="false"
                      :render-content="renderContent">
                    </el-tree>
                  </div>
                </el-card>
                <!-- Card区域结束 -->
              </el-col>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="黑名单" name="second" value="second">
          <div class="blaklist-tree">
            <el-row :gutter="20" style="width:100%">
              <el-col :span="8" v-for="(item,index) in formList" :key="index" style="padding-bottom:20px;">
                <!-- Card区域 -->
                <el-card class="box-card" style="height:320px">
                  <div slot="header" class="clearfix">
                    <span class="card-tit-weight">{{item.label}}</span>
                    <el-button style="padding: 3px 0;line-height: 1;" type="text" @click="moveOutBlacklist(item.id)">移出</el-button>
                    <el-button style="padding: 3px 0;line-height: 1;" type="text" @click="deleteCard(item.id)">删除</el-button>
                  </div>
                  <div class="text item">
                    <el-tree
                      style="text-align:left;"
                      :data="[item]"
                      node-key="id"
                      default-expand-all
                      :expand-on-click-node="false"
                      :render-content="renderBlackContent">
                    </el-tree>
                  </div>
                </el-card>
                <!-- Card区域结束 -->
              </el-col>
            </el-row>
          </div>
        </el-tab-pane>
      </el-tabs>
      <!--分页-->
      <div class="block-page" style="height: 100px;">
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
    <!-- 弹窗区域 -->
    <div class="dialog-box">
      <el-dialog title="编辑信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="formInfo" ref="formInfo" :rules="rules">
          <el-form-item label="招标机构名称" :label-width="formLabelWidth" prop="name">
            <el-input v-model="formInfo.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="企业简称" :label-width="formLabelWidth" prop="shortName">
            <el-input v-model="formInfo.shortName" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelSaveEnterprise('formInfo')">取 消</el-button>
          <el-button type="primary" @click="saveEnterprise('formInfo')">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!--增加部门弹窗-->
    <div class="dialog-box">
      <el-dialog title="编辑信息" :visible.sync="dialogFormVisibleDepartment" width="30%">
        <el-form :model="formInfos" ref="formInfos" :rules="rulesDepartment">
          <el-form-item label="部门名称" :label-width="formLabelWidth" prop="name">
            <el-input v-model="formInfos.name"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelForm('formInfos')">取 消</el-button>
          <el-button type="primary" @click="submitForm('formInfos')">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!-- 弹窗区域结束 -->
  </div>
</template>
<script>
import {department, enterprise} from '../../../../api/index'
// 实例
export default {
  data () {
    return {
      searchForm: {}, // 搜索表单
      formList: [],
      dialogFormVisibleDepartment: false,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      formInfo: {},
      formInfos: {},
      activeTabName: 'first',
      activeBlackName: '0', // 黑名单手风琴默认打开第一项（字符串类型）
      dataTree: [],
      btnType: null,
      parentId: null,
      tabName: 'first', // tabs默认值
      currentNode: '', // 当前树形节点的值
      isEditEnterpriseInfo: false, // 用于判断是编辑企业还是部门信息
      rules: {
        name: [
          {required: true, message: '请输入企业名称', trigger: 'blur'},
          {min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change']}
        ],
        shortName: [
          {required: true, message: '请输入简称', trigger: 'blur'},
          {min: 1, max: 50, message: '请输入1~20个字符', trigger: ['blur', 'change']}
        ]
      },
      rulesDepartment: {
        name: [
          {required: true, message: '请输入部门名称', trigger: 'blur'},
          {min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change']}
        ]
      },
      pageNo: 0,
      currentPage: 1,
      pageSizeList: [6, 12, 18, 24, 30],
      pageSize: 6, // 每页条数
      total: 0 // 总条数

    }
  },
  computed: {
    rootNodeIds: function () {
      const ids = []
      for (let i of this.dataTree) {
        ids.push(i.id)
      }
      return ids
    }
  },
  created () {
    this.getList(0)
  },
  methods: {
    /** 分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.getList(this.pageNo, this.pageSize, function (resp) {
          likeThis.total = resp.data.Data.Total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getList(0)
    },
    /** 删除卡片 */
    deleteCard (id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enterprise.delete(id).then((res) => {
          let status = 0
          if (this.tabName === 'first') {
            status = 0
          } else {
            status = 1
          }
          this.getList(status)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /** 移入黑名单 */
    moveInBlacklist (id) {
      this.$confirm('将此公司移入黑名单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enterprise.updateStatusById(id, 1).then((res) => {
          this.getList(0)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消移入！'
        })
      })
    },
    /** 移出黑名单 */
    moveOutBlacklist (id) {
      this.$confirm('将此公司移出黑名单，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enterprise.updateStatusById(id, 0).then((res) => {
          this.getList(1)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消移入！'
        })
      })
    },
    handleClick (data) {
      this.tabName = data.name
      let status = 0
      if (this.tabName === 'first') {
        status = 0
      } else {
        status = 1
      }
      department.getList({type: 4, status: status, pageNo: this.pageNo, pageSize: this.pageSize}).then((res) => {
        this.formList = res.data.department.list
        this.total = res.data.department.total
      })
    },
    /** 取消 部门 */
    cancelForm (formName) {
      this.formInfos = {}
      this.dialogFormVisibleDepartment = false
      this.$refs[formName].resetFields()
    },
    /** 取消 公司 */
    cancelSaveEnterprise (formName) {
      this.dialogFormVisible = false
      this.$refs[formName].resetFields()
    },
    /** 确定
     * 新增公司 */
    saveEnterprise (formName) {
      this.$refs[formName].validate((vaild) => {
        if (vaild) {
          this.dialogFormVisible = false
          this.formInfo.type = 4
          enterprise.save(this.formInfo).then((res) => {
            this.getList(0)
            this.$refs[formName].resetFields()
          })
        } else {
          return false
        }
      })
    },
    /** 确定
     * 新增，编辑，部门； 编辑 企业  */
    submitForm (formName) {
      this.$refs[formName].validate((vaild) => {
        if (vaild) {
          this.dialogFormVisibleDepartment = false
          // 新增部门
          if (this.btnType === 'add') {
            department.save(this.formInfos).then((res) => {
              this.getList(0)
              this.formInfos = {}
              this.$refs[formName].resetFields()
            })
          } else if (this.btnType === 'pedit') {
            // 编辑企业
            let obj = {
              name: this.formInfo.name,
              objectId: this.currentNode.id
            }
            enterprise.update(obj).then((res) => {
              this.getList(0)
              this.dialogFormVisible = false
              this.formInfo = {}
            })
          } else if (this.btnType === 'cedit') {
            // 编辑部门
            let excludeIds = []
            excludeIds.push(this.currentNode.id)
            let obj = {
              name: this.formInfos.name,
              objectId: this.currentNode.id,
              parentDepartmentId: this.formInfo.parentDepartmentId,
              excludeIds: excludeIds
            }
            department.update(obj).then((res) => {
              this.getList(0)
              this.dialogFormVisibleDepartment = false
              this.formInfos = {}
            })
          }
        } else {
          return false
        }
      })
    },
    /** 新增招标机构 */
    addTendCopm (formName) {
      this.dialogFormVisible = true
      this.formInfo = {}
      this.$refs[formName].resetFields()
    },
    /** 搜索 */
    onSearch () {
      let status = 0
      if (this.tabName === 'first') {
        status = 0
      } else {
        status = 1
      }
      this.getList(status)
    },
    /** 查询部门表 */
    getList (status) {
      let obj = {
        type: 4,
        status: status,
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        messageLike: this.searchForm.name || null
      }
      department.getList(obj).then((res) => {
        this.formList = res.data.department.list
        this.total = res.data.department.total
      })
    },
    /** 通过联表查询企业表 */
    getOne (data) {
      department.getOne(data.id).then((res) => {
        this.formInfo = res.data.enterprise
      })
    },
    edit (data, node, type) {
      this.btnType = type
      // 父级修改 type=pedit
      if (data.parentId === 0 && type === 'pedit') {
        this.dialogFormVisible = true
        this.currentNode = data
        this.formInfo.parentDepartmentId = node.data.parentId
        this.formInfo.name = data.label
        this.getOne(data)
        this.formInfo.shortName = data.shortName
      } else if (type === 'cedit') {
        this.dialogFormVisibleDepartment = true
        this.currentNode = data
        this.formInfo.parentDepartmentId = node.data.parentId
        this.formInfos.name = data.label
      }
    },
    /** 新增部门 */
    append (data, type) {
      this.btnType = type
      this.currentNode = data
      // 打开弹层
      this.dialogFormVisibleDepartment = true
      this.formInfos = {}
      this.formInfos.parentDepartmentId = data.id
    },
    /** 删除部门 */
    remove (node, data) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        department.delete(data.id).then((res) => {
          this.getList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 黑名单
    renderBlackContent (h, { node, data, store }) {
      if (data.children) {
        if (data.parentId === 0) {
          return (
            <span class="custom-tree-node">
              <span>{node.label}</span>
            </span>
          )
        } else {
          return (
            <span class="custom-tree-node">
              <span>{node.label}</span>
            </span>
          )
        }
      }
    },
    // 招标机构
    renderContent (h, { node, data, store }) {
      if (data.children) {
        if (data.parentId === 0) {
          return (
            <span class="custom-tree-node">
              <span>{node.label}</span>
              <span class="enterp-btn">
                <el-button size="mini" type="text" icon="el-icon-plus" on-click={ () => this.append(data, 'add') }></el-button>
                <el-button size="mini" type="text" icon="el-icon-edit" on-click={ () => this.edit(data, node, 'pedit') }></el-button>
              </span>
            </span>
          )
        } else {
          return (
            <span class="custom-tree-node">
              <span>{node.label}</span>
              <span class="enterp-btn">
                <el-button size="mini" type="text" icon="el-icon-edit" on-click={ () => this.edit(data, node, 'cedit') }></el-button>
                <el-button size="mini" type="text" icon="el-icon-delete" on-click={ () => this.remove(node, data) }></el-button>
              </span>
            </span>
          )
        }
      }
    }
  },
  // 实例创建完成
  mounted () {
  }
}
</script>

<style lang="less">
#tend-page {
  .cooper-tree,blaklist-tree {
    padding: 20px;
    border: 1px solid #f1f1f1;
  }
  .tend-add-btn {
    text-align: left;
    padding-bottom: 20px;
  }
  .el-card__header {
    width: 100%;
    height: 60px;
    white-space: nowrap;
  }
  .el-card__body {
    height: 220px;
    overflow-y: auto;
  }
  .branch-btn {
    position: absolute;
    left: 260px;
    visibility: hidden;
  }
  .el-tree-node__content {
    padding: 10px;
    border-bottom: 1px solid #ddd;
  }
  .clearfix {
    text-align: left;
    > span {
      display: inline-block;
      width: 75%;
      overflow: hidden;
    }
    .el-button--text {
      width: 10%;
    }
    .card-tit-weight {
      font-weight: 800;
      font-size: 16px;
    }
  }
  .el-tree-node__content {
    .enterp-btn {
      position: absolute;
      right: 0;
      visibility: visible;
    }
  }
  .custom-tree-node {
    width: 200px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    .el-tooltip.item {
      display: inline-block;
      width: 200px;
    }
  }
  .dialog-box {
    .dialog-footer {
      text-align: center;
    }
  }
}
</style>
