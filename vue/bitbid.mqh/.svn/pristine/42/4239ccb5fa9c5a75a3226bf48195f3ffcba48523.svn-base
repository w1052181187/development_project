<template>
  <!-- 招标控制价 -->
  <div class="admin-layout" id="bidprice-page">
    <div class="bidprice-cont">
      <div class="bidprice-tit">
        <span>招标控制价</span>
      </div>
      <div class="bidprice-form">
        <el-form :model="ruleForm" :rules="ruleForm.rules" ref="ruleForm" label-width="140px">
          <el-form-item label="控制价信息:" prop="bidpriceInfo">
            <el-table
              class="bidprice-tab"
              border
              :data="ruleForm.tableData"
              :row-class-name="tableRowClassName"
              style="width: 100%">
              <el-table-column
                type="index"
                label="序号"
                :index="indexMethod"
                width="55"
                align="center">
              </el-table-column>
              <el-table-column
                label="标段名称"
                prop="name"
                align="center">
              </el-table-column>
              <el-table-column
                prop="controlPrice"
                label="控制价(万元)"
                align="center">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'tableData.' +scope.$index+ '.controlPrice'"
                    :rules="ruleForm.rules.controlPrice">
                    <el-input v-model="scope.row.controlPrice" placeholder="请输入内容" v-if="scope.row.flag===1"></el-input>
                    <span v-if="!scope.row.flag">{{scope.row.controlPrice}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                v-if="dialogEdit"
                label="操作"
                align="center"
                width="100">
                <template slot-scope="scope">
                  <el-button @click="handleTableClick(scope.row,'edit', scope.$index)" type="text" size="medium" v-if="!scope.row.flag">编辑</el-button>
                  <el-button @click="handleTableClick(scope.row,'confirm', scope.$index)" type="text" size="medium" v-if="scope.row.flag===1">确定</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
          <el-form-item label="控制价附件:" prop="bidPriceFile" ref="bidPriceFile">
            <el-upload
              class="upload-demo"
              :action="upLoad"
              :on-success="uploadSuccess"
              :file-list="fileList"
              :show-file-list="false"
              v-if="dialogEdit">
              <el-button size="small" type="success" class="upload-btn">上传文件</el-button>
            </el-upload>
            <!-- 上传附件列表 -->
            <div class="apply-upload">
              <el-row>
                <el-col :span="12">
                  <span class="file-list" v-for="(item, index) in fileList" :key="index">
                    <el-button type="text" style="padding-right: 20px;" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button>
                    <i class="el-icon-error upload-file" @click="clickDel(index)" v-if="dialogEdit"></i>
                  </span>
                </el-col>
              </el-row>
            </div>
          </el-form-item>
          <el-form-item class="form-last">
            <el-button type="primary" @click="onSubmit('ruleForm')" v-if="dialogEdit">确 定</el-button>
            <!-- <el-button @click="$router.go(-1)">取消</el-button> -->
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
import {bidfile, bidprice, openbid, section} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
export default {
  data () {
    var validPrice = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.\d{1,6})?$/).test(value))) {
        callback(new Error('控制价只能输入小数点前不超过14位且小数点后不超过6位的数字'))
      } else {
        callback()
      }
    }
    return {
      upLoad: commonJs.fileUploadUrl,
      dialogEdit: false,
      ruleForm: {
        rules: {
          bidPriceFile: [
            {required: true, message: '请上传控制价附件', trigger: 'blur'}
          ],
          controlPrice: [
            {required: true, message: '请填写控制价', trigger: 'blur'},
            {validator: validPrice}
          ]
        },
        tableData: [
          {controlPrice: ''}
        ]
      },
      // tableData: [],
      flag: 0, // 0-新增 1-修改
      bidPriceFrom: {
        controlPriceSublists: []
      },
      currentPage: 1,
      fileList: [] // 上传文件列表
    }
  },
  props: {
    noticeCode: String,
    operationCheckFlag: Boolean
  },
  computed: {
  },
  created () {
  },

  /** 方法集 */
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /**
     * 删除上传附件
     */
    clickDel (index) {
      this.fileList.splice(index, 1)
      this.validFile()
    },
    validFile () {
      if (this.fileList.length > 0) {
        this.$set(this.ruleForm, 'bidPriceFile', 'bidPriceFile')
      } else {
        this.$set(this.ruleForm, 'bidPriceFile', '')
      }
      if (this.ruleForm.bidPriceFile) {
        this.$refs['bidPriceFile'].clearValidate()
      }
    },
    /*
     * 下载
     */
    downloadFile,
    /**
     * 上传附件
     */
    uploadSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        let fileInformations = []
        let existFileSize = 0
        //  得到已经保存的附件信息
        for (var i = 0; i < fileList.length; i++) {
          if (!fileList[i].response) {
            existFileSize++
            fileInformations[i] = fileList[i]
          }
        }
        // 添加新的附件信息
        if (existFileSize < fileList.length) {
          for (var j = existFileSize; j < fileList.length; j++) {
            if (fileList[j].response) {
              fileInformations[j] = fileList[j].response.fileInformation
            }
          }
        }
        this.fileList = fileInformations // 获得目前项目中所有的附件信息
      }
      this.validFile()
    },
    // 表格操作
    handleTableClick (row, type, index) {
      if (Object.is(type, 'edit')) {
        // 设置按钮切换（编辑-确定）
        this.ruleForm.tableData = this.setTogglebutton(this.ruleForm.tableData, index, type)
      } else if (Object.is(type, 'confirm')) {
        // 设置按钮切换（确定-编辑）
        if (row.controlPrice && ((/^\d{1,14}(\.\d{1,6})?$/).test(row.controlPrice))) {
          this.ruleForm.tableData = this.setTogglebutton(this.ruleForm.tableData, index, type)
        }
      }
    },
    /** 确定 */
    onSubmit (val) {
      this.validFile()
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.bidPriceFrom.fileInformationList = this.fileList
          if (this.flag === 0) {
            this.bidPriceFrom.noticeCode = this.noticeCode
            if (this.ruleForm.tableData.length > 0) {
              for (let i = 0; i < this.ruleForm.tableData.length; i++) {
                this.ruleForm.tableData[i].sectionCode = this.ruleForm.tableData[i].code
              }
            }
            this.bidPriceFrom.controlPriceSublists = this.ruleForm.tableData
            bidprice.save(this.bidPriceFrom).then(() => {
              this.getControlPrice()
            })
          } else {
            this.bidPriceFrom.controlPriceSublists = this.ruleForm.tableData
            bidprice.update(this.bidPriceFrom).then(() => {
              this.getControlPrice()
            })
          }
        } else {
          return false
        }
      })
    },
    /** 设置按钮切换 */
    setTogglebutton (arr, index, type) {
      let newArr = []
      // 将设置好的数据拷贝一份
      newArr = arr.map((item, current) => {
        if (index === current) {
          if (Object.is(type, 'edit')) {
            item.flag = 1
          } else if (Object.is(type, 'confirm')) {
            item.flag = 0
          }
          return item
        } else {
          return item
        }
      })
      return newArr
    },
    getControlPrice () {
      let obj = {
        noticeCode: this.noticeCode
      }
      bidprice.getOne(obj).then((res) => {
        let controlPrice = res.data.controlPrice
        return controlPrice
      }).then((controlPrice) => {
        if (controlPrice) {
          if (controlPrice.controlPriceSublists.length > 0) {
            this.flag = 1
            this.bidPriceFrom = controlPrice
            this.ruleForm.tableData = this.bidPriceFrom.controlPriceSublists
            this.fileList = this.bidPriceFrom.fileInformationList
          } else {
            this.flag = 0
            this.bidPriceFrom = controlPrice
            this.getSections()
          }
        } else {
          return false
        }
      })
    },
    /** 查询标段信息 */
    getSections () {
      this.ruleForm.tableData = []
      let obj = {
        noticeCode: this.noticeCode,
        tenderDocStatus: 4
      }
      bidfile.getList(obj).then((res) => {
        let tenderDocList = res.data.tenderDocList
        if (tenderDocList.length > 0) {
          tenderDocList.map((tenderDoc) => {
            tenderDoc.sectionList.map((section) => {
              this.ruleForm.tableData.push(section)
            })
          })
        }
      })
    },
    /** 招标控制价只可在招标文件发布之后，产生开评标数据之前编辑，否则只可查看 */
    getTenderDocList () {
      let obj = {
        noticeCode: this.noticeCode,
        tenderDocStatus: 4
      }
      /** 查询招标文件信息 */
      bidfile.getList(obj).then((res) => {
        let tenderDocList = res.data.tenderDocList
        if (tenderDocList.length > 0) {
          this.dialogEdit = true
        } else {
          this.dialogEdit = false
        }
      }).then((res) => {
        if (this.dialogEdit === true) {
          this.getOpenEvalResult()
        }
      })
    },
    /** 查询该公告下是否存在开评标数据，若存在，招标控制价只可查看，否则可编辑 */
    getOpenEvalResult () {
      let openResult = null
      let obj = {
        noticeCode: this.noticeCode
      }
      openbid.getOpenRecord(obj).then((res) => {
        openResult = res.data.openingRecord
      }).then((res) => {
        let evalResult = null
        openbid.getEvaluationResult(obj).then((res) => {
          evalResult = res.data.evaluationResult
        })
        if (openResult || evalResult) {
          this.dialogEdit = false
        } else {
          this.dialogEdit = true
        }
      })
    },
    /** 查询未锁定的标段，若标段均被锁定，招标控制价只可查看 */
    getNotLockedSections () {
      let sectionList = []
      section.getNotLockedSections(this.noticeCode).then((res) => {
        sectionList = res.data.sectionList
      }).then((res) => {
        if (sectionList) {
          if (sectionList.length < 1) {
            this.dialogEdit = false
          } else {
            this.getTenderDocList()
          }
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getControlPrice()
    let enterpriseType = this.$store.getters.authUser.enterpriseType
    if (enterpriseType === 4) {
      this.getNotLockedSections()
    } else {
      this.dialogEdit = false
    }
    // 招标变更查询（只可查看）
    if (!this.operationCheckFlag) {
      this.dialogEdit = false
    }
  }
}
</script>
<style lang="less">
#bidprice-page {
  .bidprice-cont {
    .bidprice-tit {
      height: 50px;
      line-height: 50px;
      text-align:left;
      padding: 0 20px;
      background:#F7F6FB;
      span {
        font-weight: 800;
      }
    }
    .bidprice-form {
      padding: 20px;
      .bidprice-tab {
        .el-form-item__content {
          text-align: center;
        }
      }
      .el-form-item__content {
        text-align: left;
      }
      .form-last {
        .el-form-item__content {
          text-align: center;
        }
      }
    }
  }
}
</style>
