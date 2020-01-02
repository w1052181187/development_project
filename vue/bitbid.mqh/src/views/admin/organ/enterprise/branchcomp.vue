<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="branch-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <!-- <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item> -->
        <el-breadcrumb-item > 组织机构</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/branchcomp' }"> 供销分公司管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="maincont-box branch-cont">
      <div class="branch-tree">
        <el-tree
          :data="dataTree"
          node-key="id"
          default-expand-all
          :expand-on-click-node="false"
          :render-content="renderContent">
        </el-tree>
      </div>
    </div>
    <div class="dialog">
      <el-dialog title="编辑信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="formInfo" ref="formInfo" :rules="rules">
          <el-form-item v-if="isEditEnterpriseInfo" label="企业名称" :label-width="formLabelWidth" prop="name">
            <el-input v-model="formInfo.name"></el-input>
          </el-form-item>
          <el-form-item v-if="!isEditEnterpriseInfo" label="部门名称" :label-width="formLabelWidth" prop="name">
            <el-input v-model="formInfo.name"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelForm('formInfo')">取 消</el-button>
          <el-button type="primary" @click="submitForm('formInfo')">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {department} from '../../../../api/index'
// 实例
export default {
  data () {
    return {
      currentNode: { }, // 当前节点的数据
      formLabelWidth: '120px',
      dialogFormVisible: false, // 弹窗
      formInfo: {},
      dataTree: [],
      btnType: null,
      parentId: null,
      isEditEnterpriseInfo: false, // 用于判断是编辑企业还是部门信息
      rules: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'},
          {min: 1, max: 50, message: '请输入1~50个字符', trigger: ['blur', 'change']}
        ]
      }
    }
  },
  created () {
    this.getList()
  },
  methods: {
    // 取消
    cancelForm (formName) {
      this.formInfo = {}
      this.dialogFormVisible = false
      this.isEditEnterpriseInfo = false
      this.$refs[formName].resetFields()
    },
    // 确定
    submitForm (formName) {
      this.$refs[formName].validate((vaild) => {
        if (vaild) {
          this.dialogFormVisible = false
          if (this.btnType === 'add') {
            department.save(this.formInfo).then((res) => {
              this.getList()
              this.formInfo = {}
              this.$refs[formName].resetFields()
            })
          } else if (this.btnType === 'edit') {
            let info = []
            info.push(this.currentNode.id)
            let obj = {
              name: this.formInfo.name,
              objectId: this.currentNode.id,
              parentDepartmentId: this.currentNode.parentId,
              excludeIds: info
            }
            department.update(obj).then((res) => {
              this.getList()
              this.isEditEnterpriseInfo = false
              this.formInfo = {}
            })
          }
        } else {
          return false
        }
      })
    },
    getList () {
      department.getList({type: 2}).then((res) => {
        this.dataTree = res.data.department.list
      })
    },
    append (data, type) {
      this.btnType = type
      this.currentNode = data
      this.formInfo = {}
      // 打开弹层
      this.dialogFormVisible = true
      this.formInfo.parentDepartmentId = data.id
    },
    edit (node, data, type) {
      if (data.parentId === 0) {
        this.isEditEnterpriseInfo = true
      }
      this.currentNode = data
      this.btnType = type
      // 打开弹层
      this.dialogFormVisible = true
      // console.log(data)
      this.formInfo.name = data.label
    },
    remove (node, data) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        department.delete(data.id).then(() => {
          this.getList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    renderContent (h, {node, data, store}) {
      if (data.parentId === 0) {
        return (
          <span class = "custom-tree-node">
            <span>{node.label}</span>
            <span class="branch-btn">
              <el-button size = "mini" type = "text" icon = "el-icon-plus" on-click = {() => this.append(data, 'add')}></el-button>
            </span>
          </span>
        )
      } else {
        return (
          <span class= "custom-tree-node">
            <span>{node.label}</span>
            <span class= "branch-btn">
              <el-button size = "mini" type = "text" icon = "el-icon-edit" on-click = {() => this.edit(node, data, 'edit')}></el-button>
              <el-button size = "mini" type = "text" icon = "el-icon-delete" on-click = {() => this.remove(node, data)}></el-button>
            </span>
          </span>
        )
      }
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
#branch-page {
  .branch-cont {
    margin-top: 20px;
    text-align: left;
  }
  .branch-tree {
    width: 460px;
    padding: 20px;
    border: 1px solid #f1f1f1;
  }
  .el-tree-node__content {
    padding: 10px;
    border-bottom: 1px solid #ddd;
    .branch-btn {
      position: absolute;
      left: 400px;
      visibility: visible;
    }
  }
  .branch-btn {
    position: absolute;
    left: 400px;
    visibility: hidden;
  }
  .custom-tree-node {
    width: 350px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
}
</style>
