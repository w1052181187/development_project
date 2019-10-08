<template>
  <div>
    <div id="yzfl" class="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item><router-link :to="{ path: '/index' }">首页</router-link></el-breadcrumb-item>
        <el-breadcrumb-item><router-link to="/owner-set">资源分类设置</router-link></el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">供应商管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="da_xz_ckbigbox">
        <el-button icon="el-icon-plus" size='medium' type="primary" @click="dialogFormVisible = true">增加分类</el-button>
        <el-dialog @close='cancle' title="新增分类" :visible.sync="dialogFormVisible">
          <el-form :model="addForm" ref="ruleForm">
            <el-form-item label="分类名称" prop="classifyName" :label-width="formLabelWidth" :rules="[
            { required: true, message: '分类名称不能为空', trigger: 'blur' },
            {min: 1, max: 10, message: '请输入1~10个字符',trigger: ['blur', 'change'] }
          ]">
              <el-input v-model.trim="addForm.classifyName"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click = "cancle">取 消</el-button>
            <el-button type="primary" @click = "addClassify('ruleForm')">确 定</el-button>
          </div>
        </el-dialog>
      </div>
      <el-table
        :data='dataTable'
        header-cell-class-name="header"
        style="width: 100%"
        border>
        <el-table-column
          type="index"
          label="序号"
          width="50"
          :index='dataIndex' align="center">
        </el-table-column>
        <el-table-column
          prop='classifyName'
          label="分类名称">
        </el-table-column>
        <el-table-column
          label="操作"
          width="120" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleEdit(scope)" v-if="$store.getters.permissions.includes('/owner-set/update') || (scope.row.fileStatus == 0)">
              修改
            </el-button>
            <el-button type="text" size="small" @click="deleteRow(scope)" v-if="$store.getters.permissions.includes('/owner-set/delete') || (scope.row.fileStatus == 0)">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog @close='cancle' title="修改分类"  :visible.sync="changeVisible">
        <el-form :model="addForm" ref="ruleForm">
          <el-form-item label="分类名称" prop="classifyName" :label-width="formLabelWidth" :rules="[
            { required: true, message: '分类名称不能为空', trigger: 'blur' },
            {min: 1, max: 10, message: '请输入1~10个字符',trigger: ['blur', 'change'] }
          ]">
            <el-input type="text" v-model.trim="addForm.classifyName"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.stop="cancle">取 消</el-button>
          <el-button type="primary" @click="open('ruleForm')">确 定</el-button>
        </div>
      </el-dialog>
      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="inAll"
        :page-size="pageSize"
        @current-change="handleCurrentChange"
        style="text-align:center;padding:50px 0">
      </el-pagination>
      <!--分页-->
    </div>
  </div>
</template>
<script>
import {checkResponse} from '../../assets/js/common'
export default{
  data () {
    return {
      dataTable: [],
      addForm: {
        classifyName: ''
      },
      dialogFormVisible: false,
      changeVisible: false,
      changedFrom: {},
      formLabelWidth: '80px',
      inAll: 0,
      dataIndex: 1,
      pageSize: 15
    }
  },
  methods: {
    // 列表获取：
    getData () {
      this.axios.get(`classify-infos?pageNo=${this.dataIndex - 1}&pageSize=${this.pageSize}&relatedModuleType=${this.$route.query.moduleType}`).then((res) => {
        this.dataTable = res.data.classifyInfo.list
        this.inAll = res.data.classifyInfo.total
      })
    },
    // 分页器改变时：
    handleCurrentChange (currentpage) {
      this.dataIndex = (currentpage - 1) * this.pageSize + 1
      this.getData()
    },
    // 分类增加：
    addClassify (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addForm.relatedModuleType = this.$route.query.moduleType
          this.axios.post(`classify-infos`, this.addForm).then((res) => {
            checkResponse(this, res, () => {
              this.cancle()
              this.getData()
              this.addForm = {}
            })
          })
        }
      })
    },
    // 删除当前行：
    deleteRow (scope) {
      this.$confirm('此操作将永久移除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.delete(`classify-infos/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.getData()
          })
        }, () => {
          this.$message.error('Not found')
        })
      }).catch(() => {
        return false
      })
    },
    // 修改当行：
    handleEdit (scope) {
      this.changeVisible = true
      this.axios.get(`classify-infos/${scope.row.objectId}`).then((res) => {
        this.addForm = res.data.ownerInfo
      })
    },
    // 确认修改：
    open (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.changeVisible = false
          this.addForm.relatedModuleType = this.$route.query.moduleType
          this.axios.put(`classify-infos`, this.addForm).then((res) => {
            checkResponse(this, res, () => {
              this.getData()
              this.changeVisible = false
              this.addForm = {}
            })
          })
        }
      })
    },
    // 取消
    cancle () {
      this.changeVisible = false
      this.dialogFormVisible = false
      this.addForm = {}
    }
  },
  mounted () {
    this.getData()
  }
}
</script>
<style lang='less'>
#yzfl{
    .header{
      background: #f5f7f8;
    }
    .breadcrumb_box{
        width: 100%;
        height: 60px;
        border-bottom: 1px solid #eeeeee;
        padding: 0 20px;
        box-sizing: border-box;
        line-height: 60px;
    }
    .el-breadcrumb__inner{
        color: #999999!important;
    }
    .active_bread>.el-breadcrumb__inner{
        color:#333333!important;
    }
    .da_xz_ckbigbox{
        border: none;
        line-height:60px;
        padding:0 0 0 20px;
        .el-form-item{padding: 0px}
        .el-dialog__header{padding-top: 0;padding-bottom: 0px}
        .el-dialog__body{padding-bottom: 0px}
    }
    .content_bigbox{margin: 0px auto;width: 1200px;background: #fff}
}
</style>
