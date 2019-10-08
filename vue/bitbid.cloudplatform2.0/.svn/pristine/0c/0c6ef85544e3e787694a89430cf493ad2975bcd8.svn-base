<template>
  <div class="cloudcontent">
    <div class="main">
      <el-row :gutter="20">
        <el-col :span="4">
          <div class="top_bg">业务模块：</div>
          <el-table
            ref="moduleTable"
            :data="moduleList"
            highlight-current-row
            border
            :row-class-name="tableRowClassName"
            :row-style="handleRowStyle"
            :cell-style="handleCellStyle"
            @current-change="handleCurrentChange"
            header-cell-class-name="tableheader">
            <el-table-column
              type="index"
              label="序号"
              width="60">
            </el-table-column>
            <el-table-column
              prop="name"
              label="名称">
            </el-table-column>
          </el-table>
        </el-col>
        <el-col :span="4">
          <div class="top_bg">字典目录：</div>
          <el-table
            ref="catalogTable"
            :data="catalogList"
            highlight-current-row
            border
            :row-class-name="tableRowClassName"
            :row-style="handleCatalogRowStyle"
            :cell-style="handleCatalogCellStyle"
            @current-change="handleCatalogCurChange">
            <el-table-column
              type="index"
              label="序号"
              width="60">
            </el-table-column>
            <el-table-column
              prop="name"
              label="名称">
            </el-table-column>
          </el-table>
        </el-col>
        <el-col :span="16">
          <div class="top_bg">字典详情：<span class="top_addbtn" @click="addBtn">+ 新增</span></div>
          <el-table
            ref="detailTable"
            :data="detailList"
            highlight-current-row
            border
            :row-class-name="tableRowClassName"
            :row-style="handleDetailRowStyle"
            :cell-style="handleDetailCellStyle"
            @current-change="handleDetailCurChange">
            <el-table-column
              type="index"
              label="序号"
              width="60">
            </el-table-column>
            <el-table-column
              prop="name"
              label="名称">
            </el-table-column>
            <el-table-column
              label="操作" align="center" width="260">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="updateBtn(scope)">修改</el-button>
                <el-button type="text" size="small" @click="delBtn(scope)">删除</el-button>
                <el-button type="text" size="small" @click="moveBtn(scope.$index, scope.row, 1)"
                           :disabled="scope.$index === 0"
                           :class="scope.$index === 0 ? 'move_btn' : ''">上移</el-button>
                <el-button type="text" size="small" @click="moveBtn(scope.$index, scope.row, 2)"
                           :disabled="scope.$index === detailList.length - 1"
                           :class="scope.$index === detailList.length - 1 ? 'move_btn' : ''">下移</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </div>
    <el-dialog title="新增" :visible.sync="dialogVisible" width="600px" :before-close="close">
      <el-form :model="form" :rules="rules" ref="form" :label-width="formLabelWidth">
        <el-form-item label="字典目录：">
          <span>{{dictionaryDirectoryName}}</span>
        </el-form-item>
        <el-form-item label="名称：" prop="name">
          <el-input v-model.trim="form.name"></el-input>
        </el-form-item>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submit" :loading="isSubmiting">提交</el-button>
          <el-button class="cancal" @click="close">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {dataDictionary} from '@/api/system'
export default {
  name: '',
  data () {
    return {
      moduleList: [],
      catalogList: [
        {name: ''}
      ],
      detailList: [],
      orderList: [],
      curIndex: 0,
      curCatalogIndex: 0,
      curDetailIndex: 0,
      isSubmiting: false,
      dialogVisible: false,
      form: {},
      formLabelWidth: '120px',
      rules: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'}
        ]
      },
      businessModuleId: null,
      dictionaryDirectoryId: null,
      dictionaryDirectoryName: '',
      isNeedAdd: false, // 当前企业是否需要新增字典详情
      oldName: '' // 存修改之前的字典详情名称
    }
  },
  methods: {
    init () {
      this.getData()
    },
    getData () {
      dataDictionary.getModuleList({}).then(res => {
        this.moduleList = res.data.businessModules
        this.businessModuleId = this.businessModuleId ? this.businessModuleId : this.moduleList[0].objectId
        this.$refs.moduleTable.setCurrentRow(this.moduleList[0])
      }).then(res => {
        this.getDirectoryList()
      })
    },
    getDirectoryList () {
      let query = {
        businessModuleId: this.businessModuleId
      }
      dataDictionary.getDirectoryList(query).then(res => {
        this.catalogList = res.data.dictionaryDirectories
        this.dictionaryDirectoryId = this.dictionaryDirectoryId ? this.dictionaryDirectoryId : this.catalogList[0].objectId
        this.dictionaryDirectoryName = this.dictionaryDirectoryName ? this.dictionaryDirectoryName : this.catalogList[0].name
        this.$refs.catalogTable.setCurrentRow(this.catalogList[0])
      }).then(res => {
        let query = {
          enterpriseId: this.$store.getters.authUser.enterpriseId,
          dictionaryDirectoryId: this.dictionaryDirectoryId,
          queryDefaultFlag: false
        }
        dataDictionary.getDetailList(query).then(res => {
          this.detailList = res.data.dictionaryDetails
          if (this.detailList.length) {
            this.isNeedAdd = false
            this.$refs.detailTable.setCurrentRow(this.detailList[0])
          } else {
            query.isDelete = 1
            dataDictionary.getDetailList(query).then(res => {
              let dictionaryDetails = res.data.dictionaryDetails
              if (dictionaryDetails.length) {
                this.isNeedAdd = false
              } else {
                this.isNeedAdd = true
                this.getDataTemplate()
              }
            })
          }
        })
      })
    },
    getDetailList () {
      let query = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        dictionaryDirectoryId: this.dictionaryDirectoryId,
        queryDefaultFlag: false
      }
      dataDictionary.getDetailList(query).then(res => {
        this.detailList = res.data.dictionaryDetails
        if (this.detailList.length) {
          this.isNeedAdd = false
          this.$refs.detailTable.setCurrentRow(this.detailList[0])
        } else {
          query.isDelete = 1
          dataDictionary.getDetailList(query).then(res => {
            let dictionaryDetails = res.data.dictionaryDetails
            if (dictionaryDetails.length) {
              this.isNeedAdd = false
            } else {
              this.isNeedAdd = true
              this.getDataTemplate()
            }
          })
        }
      })
    },
    getDetails () {
      let query = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        dictionaryDirectoryId: this.dictionaryDirectoryId,
        queryDefaultFlag: false
      }
      dataDictionary.getDetailList(query).then(res => {
        this.detailList = res.data.dictionaryDetails
        this.$refs.detailTable.setCurrentRow(this.detailList[0])
        this.isNeedAdd = false
      })
    },
    getDataTemplate () {
      let query = {
        dictionaryDirectoryId: this.dictionaryDirectoryId
      }
      dataDictionary.getDataTemplate(query).then(res => {
        this.detailList = res.data.dictionaryDetails
        this.$refs.detailTable.setCurrentRow(this.detailList[0])
      })
    },
    tableRowClassName ({row, rowIndex}) {
      row.index = rowIndex
    },
    handleRowStyle ({row, rowIndex}) {
      if (this.curIndex === rowIndex) {
        return 'background-color: #ecf3fd'
      }
    },
    handleCellStyle ({row, column, rowIndex, columnIndex}) {
      if (this.curIndex === rowIndex && columnIndex === 1) {
        return 'color: #5592eb'
      }
    },
    handleCurrentChange (row) {
      // 切换模块
      this.curIndex = row.index
      this.businessModuleId = row.objectId
      let query = {
        businessModuleId: this.businessModuleId
      }
      dataDictionary.getDirectoryList(query).then(res => {
        this.catalogList = res.data.dictionaryDirectories
        this.dictionaryDirectoryId = this.dictionaryDirectoryId ? this.dictionaryDirectoryId : this.catalogList[0].objectId
        this.dictionaryDirectoryName = this.dictionaryDirectoryName ? this.dictionaryDirectoryName : this.catalogList[0].name
        this.$refs.catalogTable.setCurrentRow(this.catalogList[0])
      })
    },
    handleCatalogRowStyle ({row, rowIndex}) {
      if (this.curCatalogIndex === rowIndex) {
        return 'background-color: #ecf3fd'
      }
    },
    handleCatalogCellStyle ({row, column, rowIndex, columnIndex}) {
      if (this.curCatalogIndex === rowIndex && columnIndex === 1) {
        return 'color: #5592eb'
      }
    },
    handleCatalogCurChange (row) {
      // 切换目录
      this.curCatalogIndex = row.index
      this.dictionaryDirectoryId = row.objectId
      this.dictionaryDirectoryName = row.name
      this.getDetailList()
    },
    handleDetailRowStyle ({row, rowIndex}) {
      if (this.curDetailIndex === rowIndex) {
        return 'background-color: #ecf3fd'
      }
    },
    handleDetailCellStyle ({row, column, rowIndex, columnIndex}) {
      if (this.curDetailIndex === rowIndex && columnIndex === 1) {
        return 'color: #5592eb'
      }
    },
    handleDetailCurChange (row) {
      // 切换详情
      // this.curDetailIndex = row.index
    },
    addBtn () {
      this.form = {}
      this.dialogVisible = true
    },
    updateBtn (scope) {
      this.form = scope.row
      this.oldName = this.form.name
      this.dialogVisible = true
    },
    // 删除
    delBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        if (this.isNeedAdd) {
          let query = {
            excludeDirectoryId: this.dictionaryDirectoryId
          }
          dataDictionary.getDataTemplate(query).then(res => {
            let data = res.data.dictionaryDetails
            let dictionaryDetails = data.concat(this.detailList)
            dictionaryDetails = dictionaryDetails.map(item => {
              if (Object.is(scope.row.objectId, item.objectId)) {
                item.isDelete = 1
              }
              item.objectId = null
              return item
            })
            dataDictionary.saveBatchDetail(dictionaryDetails).then(() => {
              this.getDetails()
            })
          })
        } else {
          dataDictionary.deleteDetailById(scope.row.objectId).then(res => {
            this.getDetails()
          })
        }
      }).catch(() => {
        return false
      })
    },
    moveBtn (index, row, status) {
      this.orderList = []
      this.detailList.map((item) => {
        this.orderList.push(item)
      })
      switch (status) {
        case 1:
          // 上移
          let lastRank = this.orderList[index - 1].rank
          this.orderList[index - 1].rank = this.orderList[index].rank
          this.orderList[index].rank = lastRank
          break
        case 2:
          // 下移
          let nextRank = this.orderList[index + 1].rank
          this.orderList[index + 1].rank = this.orderList[index].rank
          this.orderList[index].rank = nextRank
          break
      }
      if (this.isNeedAdd) {
        let query = {
          excludeDirectoryId: this.dictionaryDirectoryId
        }
        dataDictionary.getDataTemplate(query).then(res => {
          let data = res.data.dictionaryDetails
          let dictionaryDetails = data.concat(this.orderList)
          dictionaryDetails = dictionaryDetails.map(item => {
            item.objectId = null
            return item
          })
          dataDictionary.saveBatchDetail(dictionaryDetails).then(() => {
            this.getDetails()
          })
        })
      } else {
        dataDictionary.updateBatchDetail(this.orderList).then(() => {
          this.getDetails()
        })
      }
    },
    close () {
      this.$refs['form'].resetFields()
      this.dialogVisible = false
      this.form.name = this.oldName
    },
    submit () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.isSubmiting = true
          this.form.dictionaryDirectoryId = this.dictionaryDirectoryId
          if (Object.is(this.detailList.length, 0)) {
            this.form.rank = this.form.rank ? this.form.rank : 1
          } else {
            this.form.rank = this.form.rank ? this.form.rank : this.detailList[this.detailList.length - 1].rank + 1
          }
          if (this.isNeedAdd) {
            dataDictionary.getDataTemplate({}).then(res => {
              let dictionaryDetails = res.data.dictionaryDetails
              dictionaryDetails = dictionaryDetails.filter(item => {
                if (!Object.is(this.form.objectId, item.objectId)) {
                  item.objectId = null
                  return item
                }
              })
              this.form.objectId = null
              dictionaryDetails = dictionaryDetails.concat([this.form])
              dataDictionary.saveBatchDetail(dictionaryDetails).then(() => {
                this.getDetails()
                this.dialogVisible = false
                setTimeout(() => {
                  this.isSubmiting = false
                }, 200)
              })
            })
          } else {
            dataDictionary.updateDetail(this.form).then(() => {
              this.getDetails()
              this.dialogVisible = false
              setTimeout(() => {
                this.isSubmiting = false
              }, 200)
            })
          }
        } else {
          return false
        }
      })
    }
  },
  mounted () {
    this.init()
  }
}
</script>

<style scoped>
  .top_bg {
    background-color: #4a8cea;
    font-size: 14px;
    color: white;
    height: 38px;
    line-height: 38px;
    box-sizing: border-box;
    padding: 0 10px;
    font-weight: 800;
  }
  .top_addbtn{
    float: right;
    height: 26px;
    line-height: 26px;
    font-size: 14px;
    color: #4a8cea;
    text-align: center;
    box-sizing: border-box;
    padding: 0 10px;
    border-radius: 3px;
    background-color: white;
    margin-top: 6px;
  }
  .submit-radio{
    text-align: center;
  }
  .move_btn {
    color: #999!important;
  }
</style>
