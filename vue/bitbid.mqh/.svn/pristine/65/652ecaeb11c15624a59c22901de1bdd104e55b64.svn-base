<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="child-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <!-- <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item> -->
        <el-breadcrumb-item > 组织机构</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/childcomp' }">二级公司管理</el-breadcrumb-item>
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
          <el-col :span="5" style="width:400px">
            <el-form-item label="企业类型:">
              <el-select v-model="searchForm.enterpriseClassify" clearable placeholder="请选择企业类型" class="search-sel">
                <el-option
                  v-for="item in enterpriseClassifyList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2" style="padding-left:30px;">
            <el-form-item>
              <el-button type="primary" @click="onSearch(searchForm)" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!--搜索end-->
    <!-- 内容区域 -->
    <div class="maincont-box child-cont">
      <div class="child-add-btn">
        <el-button type="warning" icon="el-icon-plus" @click="addChildCopm">新增二级公司</el-button>
      </div>
      <el-row :gutter="20" style="width:100%">
        <el-col :span="8" v-for="(item,index) in dataTree" :key="index" style="padding-bottom:20px;">
          <!-- Card区域 -->
          <el-card class="box-card" style="height:320px">
            <div slot="header" class="clearfix">
              <span class="card-tit-weight">{{item.label}}</span>
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
    <!-- 内容区域结束 -->
    <!-- 弹窗区域 -->
    <!--增加公司弹窗-->
    <div class="dialog-box">
      <el-dialog title="编辑信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="formInfo" ref="formInfo" :rules="rules">
          <el-form-item label="公司名称" :label-width="formLabelWidth" prop="name">
            <el-input v-model="formInfo.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="企业类型" :label-width="formLabelWidth" prop="enterpriseClassify">
            <el-select v-model="formInfo.enterpriseClassify" style="width: 100%;" clearable placeholder="请选择企业类型">
              <el-option
                v-for="item in enterpriseClassifyList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="公司简称" :label-width="formLabelWidth" prop="shortName">
            <el-input v-model="formInfo.shortName"></el-input>
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
      currentNode: { }, // 当前节点的数据
      dataTree: [],
      formInfo: {},
      enterpriseClassifyList: [
        {
          label: '煤矿',
          value: 1
        },
        {
          label: '燃气',
          value: 2
        },
        {
          label: '煤化工',
          value: 3
        },
        {
          label: '其他',
          value: 4
        }
      ],
      formInfos: {},
      dialogFormVisible: false,
      dialogFormVisibleDepartment: false,
      formLabelWidth: '80px',
      btnType: null,
      parentId: null,
      pageNo: 0,
      currentPage: 1,
      pageSizeList: [6, 12, 18, 24, 30],
      pageSize: 6, // 每页条数
      total: 0, // 总条数
      rules: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'},
          {min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change']}
        ],
        shortName: [
          {required: true, message: '请输入简称', trigger: 'blur'},
          {min: 1, max: 20, message: '请输入1~20个字符', trigger: ['blur', 'change']}
        ],
        enterpriseClassify: [
          {required: true, message: '请选择企业类型', trigger: 'blur'}
        ]
      },
      rulesDepartment: {
        name: [
          {required: true, message: '请输入部门名称', trigger: 'blur'},
          {min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change']}
        ]
      }
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
    this.getList()
  },
  methods: {
    // 分页
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getList(parseInt(this.pageNo), parseInt(this.pageSize))
    },
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
    /** 取消 部门 */
    cancelForm (formName) {
      this.formInfos = {}
      this.dialogFormVisibleDepartment = false
      this.$refs[formName].resetFields()
    },
    /** 取消 企业 */
    cancelSaveEnterprise (formName) {
      this.formInfo = {}
      this.dialogFormVisible = false
      this.$refs[formName].resetFields()
    },
    /** 新增 企业 */
    saveEnterprise (formName) {
      this.$refs[formName].validate((vaild) => {
        if (vaild) {
          this.formInfo.type = 3
          enterprise.save(this.formInfo).then((res) => {
            this.getList()
            // 关闭弹窗
            this.dialogFormVisible = false
            this.$refs[formName].resetFields()
          })
        } else {
          return false
        }
      })
    },
    /** 根据类型判断 操作 */
    submitForm (formName) {
      this.$refs[formName].validate((vaild) => {
        if (vaild) {
          this.dialogFormVisibleDepartment = false
          if (this.btnType === 'add') {
            department.save(this.formInfos).then((res) => {
              this.getList()
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
            let obj = {
              name: this.formInfos.name,
              objectId: this.currentNode.id,
              parentDepartmentId: this.formInfo.parentDepartmentId
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
    /** 新增企业 */
    addChildCopm (formName) {
      this.dialogFormVisible = true
      this.formInfo = {}
    },
    /** 删除卡片 */
    deleteCard (id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enterprise.delete(id).then((res) => {
          this.getList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /** 搜索 */
    onSearch () {
      this.getList()
    },
    /** 列表数据展示 */
    getList () {
      let obj = {
        type: 3,
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        enterpriseClassify: this.searchForm.enterpriseClassify || null,
        messageLike: this.searchForm.name || null
      }
      department.getList(obj).then((res) => {
        this.dataTree = res.data.department.list
        this.total = res.data.department.total
      })
    },
    getOne (data) {
      department.getOne(data.id).then((res) => {
        this.formInfo = res.data.enterprise
      })
    },
    /** 根据类型判断  操作 */
    edit (data, node, type) {
      this.btnType = type
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
    renderContent (h, { node, data, store }) {
      if (data.parentId === 0) {
        return (
          <span class = "custom-tree-node">
            <span>{node.label}</span>
            <span class="branch-btn">
              <el-button size = "mini" type = "text" icon = "el-icon-plus" on-click = {() => this.append(data, 'add')}></el-button>
              <el-button size = "mini" type = "text" icon = "el-icon-edit" on-click = {() => this.edit(data, node, 'pedit')}></el-button>
            </span>
          </span>
        )
      } else {
        return (
          <span class= "custom-tree-node">
            <span>{node.label}</span>
            <span class= "branch-btn">
              <el-button size = "mini" type = "text" icon = "el-icon-edit" on-click = {() => this.edit(data, node, 'cedit')}></el-button>
              <el-button size = "mini" type = "text" icon = "el-icon-delete" on-click = {() => this.remove(node, data)}></el-button>
            </span>
          </span>
        )
      }
    },
    // 实例创建完成
    mounted () {
    }
  }
}
</script>

<style lang="less">
  #child-page {
    .btn-tree {
      position: absolute;
      margin-left: 100px;
    }
    .child-add-btn {
      text-align: left;
      padding-bottom: 20px;
    }
    .el-card__header {
      height: 60px;
      overflow: hidden;
      white-space: nowrap;
      .clearfix {
        text-align: left;
      }
    }
    .el-card__body {
      height: 220px;
      overflow-y: auto;
    }
    .el-tree-node__content {
      padding: 10px;
      border-bottom: 1px solid #ddd;
      .branch-btn {
        position: absolute;
        right: 0;
        visibility: visible;
      }
    }
    .clearfix {
      span {
        display: inline-block;
        width: 85%;
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
