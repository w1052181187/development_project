<template>
  <div id="qbmain" class="qbmain">
    <div class="top">
      <div class="radiobox">
        <el-button class="addbtn"  type="primary" @click="newBuild">+ 新建方案</el-button>
      </div>
      <div class="searchbox">
        <span>地区：</span>
        <el-select v-model="searchregion" class="select" placeholder="请选择地区">
          <el-option
            v-for="item in regionOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-input class="search" style="vertical-align: top"  placeholder="请输入方案名称关键字" v-model="messageLike"></el-input>
        <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
      </div>
      <!--弹出框-->
      <el-dialog title="新建方案" :visible.sync="newBuiltDialog"   width="600px">
        <el-form :model="newBuildForm" :rules="rules" ref="newBuildForm" :validate-on-rule-change="true">
          <el-form-item label="方案名称："  prop="name">
            <el-input v-model="newBuildForm.name"></el-input>
          </el-form-item>
          <el-form-item label="地区："  prop="region">
            <el-select v-model="newBuildForm.region" placeholder="请选择地区">
              <el-option
                v-for="item in regionOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelNewBuild('newBuildForm')">取 消</el-button>
          <el-button type="primary"  @click="_submitNewBuild('newBuildForm')">确 定</el-button>
        </div>
      </el-dialog>
      <!--弹出框-->
    </div>
    <!--主要内容 table-->
    <el-table
      :data="tableData"
      border
      header-cell-class-name="tabletitles">
      <el-table-column
        type="index"
        width="50"
        label="序号"
        :index='dataIndex'
        align="center">
      </el-table-column>
      <el-table-column
        prop="name"
        label="方案名称"
        align="left">
      </el-table-column>
      <el-table-column
        prop="regionname"
        label="地区"
        align="left"
        width="100">
      </el-table-column>
      <el-table-column
        prop="lastUpdateDate"
        label="创建时间"
        align="left"
        width="200">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作" width="180" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="openBtn(scope)">
            打开
          </el-button>
          <el-button type="text" size="small" @click="reNameBtn(scope)">
            重命名
          </el-button>
          <el-button type="text" size="small" @click="_delete(scope)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--重命名弹出框-->
    <el-dialog title="重命名" :visible.sync="reNameDialog" width="600px">
      <el-form :model="reNameForm" :rules="rules" ref="reNameForm" :validate-on-rule-change="true">
        <el-form-item label="方案名称："  prop="name">
          <el-input v-model="reNameForm.name"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelReName('reNameForm')">取 消</el-button>
        <el-button type="primary"  @click="_submitReName('reNameForm')">确 定</el-button>
      </div>
    </el-dialog>
    <!--重命名弹出框-->
    <!--主要内容 table-->
    <!--分页-->
    <el-pagination
      class="pagebox"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="currentPage"
      :total="total"
      :page-size='pageSize'
      layout="prev, pager, next, jumper">
    </el-pagination>
    <!--分页-->
    <el-dialog
      title="提示"
      :visible.sync="isOpenDialog"
      width="30%">
      <p>您的方案个数已达上限，您可以删除方案继续使用该系统。</p>
    </el-dialog>
  </div>
</template>
<script>
import { billClearData } from '@/api'
import * as region from '@/assets/js/bill-region'
export default {
  data () {
    return {
      messageLike: '',
      // 表格数据
      tableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      dataIndex: 1,
      // 弹窗
      newBuiltDialog: false,
      reNameDialog: false,
      newBuildForm: {},
      reNameForm: {},
      fileList: [],
      rules: {
        name: [
          { required: true, message: '请输入方案名称', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1到 100 个字符', trigger: 'blur' }
        ],
        region: [
          { required: true, message: '请选择地区', trigger: 'change' }
        ]
      },
      searchregion: 140000,
      regionOptions: region.CityInfo,
      typeOptions: [
        {
          value: 1,
          label: '招标'
        }, {
          value: 2,
          label: '投标'
        }, {
          value: 3,
          label: '招标控制价'
        }
      ],
      isOpenDialog: false
    }
  },
  methods: {
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    // 列表数据(查询)
    _listData () {
      let params = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        messageLike: this.messageLike,
        creator: this.$store.getters.authUser.userId
      }
      if (this.value != null) {
        params.type = this.value
      }
      billClearData.programList(params).then((res) => {
        this.tableData = res.data.programInfo.list
        this.tableData.map((ite) => {
          ite.regionname = ''
          region.CityInfo.map((item) => {
            if (ite.region == item.value) {
              ite.regionname += item.label
            }
          })
          return ite
        })
        this.total = res.data.programInfo.total
      })
    },
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.dataIndex = 1
      this._listData()
    },
    // 分页
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this._listData()
    },
    // 重置表单
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    // 新建方案  （先判断是否可以新建）
    newBuild () {
      billClearData.isOpen().then((res) => {
        if (res.data.number >= 10) {
          this.isOpenDialog = true
        } else {
          this.newBuiltDialog = true
        }
      })
    },
    // 新建方案弹出框验证表单(保存)
    _submitNewBuild (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          billClearData.save(this.newBuildForm).then((res) => {
            this._listData()
            this.newBuiltDialog = false
          })
        } else {
          return false
        }
      })
    },
    // 取消新建方案
    cancelNewBuild (formName) {
      this.resetForm(formName)
      this.newBuiltDialog = false
      this.fileList = []
    },
    // 打开重命名弹窗
    reNameBtn (scope) {
      this.reNameForm.objectId = scope.row.objectId
      this.reNameForm.name = scope.row.name
      this.reNameDialog = true
    },
    // 重命名方案 (重命名)
    _submitReName (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          billClearData.update(this.reNameForm).then((res) => {
            this._listData()
            this.resetForm(formName)
          })
          this.reNameDialog = false
        } else {
          return false
        }
      })
    },
    // 取消重命名
    cancelReName (formName) {
      this.resetForm(formName)
      this.reNameDialog = false
    },
    // 删除方案
    _delete (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        billClearData.delete(scope.row.objectId).then((res) => {
          this._listData()
        })
      }).catch(() => {
        return false
      })
    },
    // 打开方案
    openBtn (scope) {
      this.$router.push({path: `/system-bidclearing/process/${scope.row.objectId}`})
    }
  },
  mounted () {
    this._listData()
  }
}
</script>
<style lang="less">
  #qbmain{
    width: 1160px;
    padding: 12px 20px;
    min-height: 300px;
    background: #ffffff;
    margin: 20px auto;
    .sqmain .top{
      width: 100%;
      overflow: hidden;
    }
    .top .radiobox{
      width: 400px;
      float: left;
      overflow: hidden;
      color: #ffffff;
    }
    .top .radiobox .addbtn{
      background: #3f65ee;
      border: 1px solid #3f65ee;
      float: left;
      margin-right: 10px;
    }
    .top .radiobox .openbtn{
      background: #00aaff;
      border: 1px solid #00aaff;
    }
    .top .radiobox .lookbtn{
      background: #ff8a00;
      border: 1px solid #ff8a00;
      float: left;
    }
    /*搜索*/
    .top .searchbox {
      width: 572px;
      height: 36px;
      min-width: 50px;
      float: right;
      margin-bottom: 20px;
    }
    .top .searchbox span{
      display: inline-block;
      float: left;
      font-size: 14px;
      line-height: 40px;
    }
    .top .searchbox .select{
      width: 108px;
    }
    .top .searchbox .search{
      width: 357px;
    }
    .tabletitles{
      background:#f7f8fa!important;
    }
    .el-table td, .el-table th{
      padding: 12px 0!important;
    }
    .el-button--small, .el-button--small.is-round{
      padding: 5px 0px;
    }
  }
</style>
