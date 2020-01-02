<template>
  <!-- 后台主容器 -->
  <div class="role-layout" id="role-page">
    <div class="breadcrumb-mqh-tit">
       <el-breadcrumb separator="/">
        <!-- <el-breadcrumb-item :to="{ path: '/admin/1' }">首页</el-breadcrumb-item> -->
        <el-breadcrumb-item > 组织机构</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/index' }">角色管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="maincont-box role-main">
      <div class="role-btn role-pos">
        <el-button type="warning" icon="el-icon-plus" @click="handleClick('addForm','add')">
          新增角色
        </el-button>
      </div>
      <el-table
        :data="tableData"
        border
        :row-class-name="tableRowClassName"
        style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          align="center"
          :index="indexMethod"
          width="55">
        </el-table-column>
        <el-table-column
          prop="name"
          label="角色名"
          header-align="center"
          align="left">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
            <el-tooltip content="分配权限" placement="top">
              <el-button type="primary" icon="el-icon-plus" @click="assignRole(scope)" class="iconfont mqh-icon-wodejuhuasuan role-icon" size="small">
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column
          prop="ownedEnterpriseType"
          label="企业类型"
          :formatter="filterEnterpriseType"
          header-align="center"
          align="left">
        </el-table-column>
        <el-table-column
          prop="description"
          label="角色描述"
          header-align="center"
          align="left">
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="150">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row,'edit')" type="text" size="medium">编辑</el-button>
            <el-button @click="handleClick(scope.row,'look')" type="text" size="medium">查看</el-button>
            <el-button @click="handleClick(scope.row,'del')" type="text" size="medium" v-if="scope.row.isFixed === 0">删除</el-button>
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
    <!--弹框区域 -->
    <div class="dialog-box">
      <!-- 新增和编辑弹窗 -->
      <el-dialog title="编辑角色" :visible.sync="dialogFormVisible" width="30%" @close="colseDialog('formInfo')">
        <el-form :model="formInfo" ref="formInfo" :rules="rules">
          <el-form-item label="角色名称" :label-width="formLabelWidth" prop="name">
            <el-input v-model="formInfo.name"></el-input>
          </el-form-item>
          <el-form-item label="角色描述" :label-width="formLabelWidth" prop="description">
            <el-input type="textarea" v-model="formInfo.description"></el-input>
          </el-form-item>
          <el-form-item label="企业类型" :label-width="formLabelWidth" prop="ownedEnterpriseType">
            <el-select v-model="formInfo.ownedEnterpriseType"  clearable placeholder="请选择" class="search-sel" style="float:left;width:100%;">
              <el-option
                v-for="item in enterpriseTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <!--<el-form-item label="上传头像" :label-width="formLabelWidth" >
            <el-upload
            :before-upload="beforeAvatarUpload"
            class="avatar-uploader"
            :action="upLoad"
            :auto-upload="true"
            :on-success="uploadSuccess"
            :show-file-list="showUploadList"
            >
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>-->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel()">取 消</el-button>
          <el-button type="primary" @click="addRole('formInfo')">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!--弹框结束 -->
  </div>
</template>
<script>
import {role} from '../../../../api/index'
// 实例
export default {
  data () {
    return {
      rules: {
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          {min: 1, max: 20, message: '请输入1~20个字符', trigger: ['blur', 'change']}
        ],
        description: [
          { required: true, message: '请输入角色描述信息', trigger: 'blur' },
          {min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change']}
        ],
        ownedEnterpriseType: [
          { required: true, message: '请选择所属企业类型', trigger: 'blur' }
        ]
      },
      showUploadList: false,
      tableData: [],
      formInfo: {
        name: '',
        description: '',
        ownedEnterpriseType: ''
      },
      pageSize: 10,
      pageNo: 0,
      currentPage: 1,
      total: 0, // 总条数
      pageSizeList: [10, 20, 30, 40, 50],
      dialogFormVisible: false, // 新增编辑弹窗
      dialogDelVisible: false, // 删除弹窗
      formLabelWidth: '80px',
      btnType: null,
      rowInfo: {},
      rowData: {},
      enterpriseTypeList: [
        {
          label: '集团公司',
          value: 1
        },
        {
          label: '供销分公司',
          value: 2
        },
        {
          label: '二级公司',
          value: 3
        },
        {
          label: '招标机构',
          value: 4
        }
      ]
    }
  },
  computed: {
    nowData: function () {
      var obj = {}
      // this.templateData是父组件传递的对象
      obj = JSON.parse(JSON.stringify(this.rowData))
      return obj
    }
  },
  created () {
    this.roleData()
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    filterEnterpriseType (row, column) {
      let type = row.ownedEnterpriseType
      if (type === 1) {
        return '集团公司'
      } else if (type === 2) {
        return '供应分公司'
      } else if (type === 3) {
        return '二级公司'
      } else if (type === 4) {
        return '招标机构'
      }
    },
    /** 列表数据展示 */
    roleData () {
      role.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }).then((res) => {
        if (res.data.roles && res.data.roles.list) {
          this.tableData = res.data.roles.list
          this.total = res.data.roles.total
        }
      })
    },
    /** 表单分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.roleData(this.pageNo, this.pageSize, function (resp) {
          likeThis.total = resp.data.Data.Total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.roleData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 新增角色信息 */
    addRole (formName) {
      this.$refs[formName].validate((vaild) => {
        if (vaild) {
          // this.formInfo.fileInformation = this.headImgList
          role.save(this.formInfo).then(() => {
            this.dialogFormVisible = false
            this.roleData(this.pageNo, this.pageSize)
            // this.imageUrl = ''
          })
        } else {
          return false
        }
      })
    },
    /** 删除角色信息 */
    deleteRole () {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dialogDelVisible = false
        role.delete(this.rowInfo.objectId).then(() => {
          this.roleData(this.pageNo, this.pageSize)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /** 分配角色 */
    assignRole (scope) {
      this.$router.push({path: '/admin/assignrole', query: {objectId: scope.row.objectId}})
    },
    /** 操作 */
    handleClick (data, type) {
      this.rowData = data
      if (type === 'edit') {
        this.dialogFormVisible = true
        // this.formInfo = data
        if (this.dialogFormVisible) {
          this.formInfo = this.nowData
        }
      } else if (type === 'del') {
        this.dialogDelVisible = true
        this.rowInfo = data
        this.deleteRole()
      } else if (type === 'look') {
        this.$router.push({path: '/role/details', query: {objectId: data.objectId}})
      } else if (type === 'add') {
        this.dialogFormVisible = true
        this.formInfo = {}
      }
    },
    // 弹窗关闭--重置表单
    colseDialog (name) {
      // this.$refs[name].resetFields()
      // this.imageUrl = ''
    },
    cancel () {
      this.dialogFormVisible = false
      this.imageUrl = ''
    }
    // 上传限制
    /* beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/png' || file.type === 'image/jpg' || file.type === 'image/jpeg'
      const isLtM = file.size / 1024 / 1024 <= 0.5
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG和PNG 格式!')
      }
      if (!isLtM) {
        this.$message.error('上传头像图片大小不能超过 512KB!')
      }
      return isJPG && isLtM
    } */
  },
  // 实例创建完成
  mounted () {
    // this.roleData()
  }
}
</script>

<style lang="less">
#role-page {
  .role-icon {
    visibility: visible;
    float: right;
  }
  .role-btn {
    text-align: left;
    margin-bottom: 15px;
  }
  // 头像样式
  .avatar-uploader {
    text-align: left;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }
  .block-page {
    padding-top: 15px;
  }
  // 弹窗样式
  .dialog-box {
    .dialog-footer {
      text-align: center;
    }
  }
}
</style>
