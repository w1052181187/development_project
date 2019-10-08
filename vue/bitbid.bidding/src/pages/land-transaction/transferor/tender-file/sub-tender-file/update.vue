<template>
  <div id="tranaddindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标、投标文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/tender-file/sub-tender-file/index?roledsType=1&showStatus=false' }">提交招标文件</el-breadcrumb-item>
        <el-breadcrumb-item>修改招标文件</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="160px" class="demo-ruleForm">
        <!--出让文件信息-->
        <div class="proinfobox">
          <span class="title">出让文件信息</span>
          <el-col :span="24">
            <el-form-item label="招标文件：" prop="name" class="filetitle bitianicon">
              <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName" :type="typeFile"></commonFileUpload>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注：" prop="remark" class="filetitle">
              <el-input
                type="textarea"
                class="remarksbox"
                v-model="ruleForm.remark">
              </el-input>
            </el-form-item>
          </el-col>
        </div>
        <!--出让文件信息-->
        <!--文件信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">文件信息</span>
          <el-row>
            <el-col :span="12">
              <el-form-item label="答疑澄清时间：" class="bitianicon timesbox">
                <el-date-picker
                  v-model="answerDataRange"
                  type="datetimerange"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  range-separator="至"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"
                  align="right">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="投标有效期（天）：" prop="validityOfBid">
                <el-input v-model="ruleForm.validityOfBid" placeholder="请输入投标有效期"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="评标办法：" prop="bidEvaluationMethod">
                <el-select v-model="ruleForm.bidEvaluationMethod">
                  <el-option
                    v-for="item in bidEvaluationData"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8" class="hourbox">
              <el-form-item label="开标时间：" prop="bidOpeningTime">
                <el-date-picker
                  v-model="ruleForm.bidOpeningTime"
                  type="datetime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  placeholder="请输入开标时间">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="开标地点：" prop="placeOfBidOpening">
                <el-input v-model="ruleForm.placeOfBidOpening" placeholder="请输入开标地点"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="开标方式：" prop="bidOpeningWay">
                <el-select v-model="ruleForm.bidOpeningWay">
                  <el-option
                    v-for="item in openingData"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="评标方式：" prop="bidEvaluationMethods">
                <el-select v-model="ruleForm.bidEvaluationMethods">
                  <el-option
                    v-for="item in evaluationMethodData"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--文件信息-->
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">关联项目信息</span>
          <el-table
            ref="multipleTable"
            :data="tableDataSeletion"
            tooltip-effect="dark"
            style="width: 100%"
            border
            header-cell-class-name="tabletitles"
            @selection-change="handleSelectionChange"
            @select-all="handleSelectionAll">
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column
              prop="landInformation.sectionNumber"
              align="left"
              label="地块编号">
            </el-table-column>
            <el-table-column
              prop="methodOfSupply"
              align="left"
              label="供应方式"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox">
                  <span class="statusbox">招拍挂出让</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="landInformation.methodOfTransfer"
              align="left"
              label="出让方式"
              width="200"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 3">
                  <span class="statusbox">资格后审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.landInformation.methodOfTransfer === 4">
                  <span class="statusbox">资格预审</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--关联项目信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('ruleForm', 1)" :loading="subStatus">提交</el-button>
          <el-button type="primary" class="save" @click="submitForm('ruleForm', 0)" :loading="subStatus">保存</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import {tdocuments, fileInfo} from '@/api'
import {sumType} from '@/assets/js/validate'
export default {
  components: {
    commonFileUpload
  },
  data () {
    // 数字
    var sumTypes = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入数字'))
      } else if (!sumType(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {},
      // 业务类型
      fileType: 3,
      // 文件类型：土地
      typeFile: 2,
      // 文件上传保存的数组
      fileArry: [],
      // 关联相关信息
      tableDataSeletion: [],
      // 评标办法
      bidEvaluationData: [
        {
          value: 1,
          label: '综合评估法'
        }, {
          value: 2,
          label: '经评审最高价中标法'
        }
      ],
      // 开标方式
      openingData: [
        {
          value: 1,
          label: '在线开标'
        }, {
          value: 2,
          label: '线下开标'
        }
      ],
      // 评标方式
      evaluationMethodData: [
        {
          value: 1,
          label: '在线评标'
        }, {
          value: 2,
          label: '线下评标'
        }
      ],
      rules: {
        validityOfBid: [
          { required: true, message: '请输入投标有效期', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur'] },
          { validator: sumTypes, trigger: ['blur', 'change'] }
        ],
        bidEvaluationMethod: [
          {required: true, message: '请选择评标办法', trigger: ['blur', 'change']}
        ],
        bidOpeningTime: [
          {required: true, message: '请选择开标时间', trigger: ['blur', 'change']}
        ],
        placeOfBidOpening: [
          { required: true, message: '请输入开标地点', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1~200个字符', trigger: ['blur', 'change'] }
        ],
        bidOpeningWay: [
          {required: true, message: '请选择开标方式', trigger: ['blur', 'change']}
        ],
        bidEvaluationMethods: [
          {required: true, message: '请选择评标方式', trigger: ['blur', 'change']}
        ]
      },
      answerDataRange: [], // 答疑澄清时间
      multipleSelection: [], // 关联信息提交时的数组
      // 提交保存状态
      subStatus: false,
      updateStatus: false,
      oldFileName: '',
      fileObjectId: ''
    }
  },
  methods: {
    submitForm (ruleForm, flag) {
      if (this.updateStatus) {
        if (this.fileArry.length === 0) {
          this.$message({
            type: 'warning',
            message: '请上传文件'
          })
          return false
        }
      }
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择关联项目'
        })
        return false
      }
      this.subStatus = true
      this.ruleForm.status = flag
      this.ruleForm.answerStartTime = this.answerDataRange[0]
      this.ruleForm.answerEndTime = this.answerDataRange[1]
      tdocuments.update(this.ruleForm).then((res) => {
        if (this.fileArry.length !== 0) {
          this.fileArry[0].relatedCode = res.data.transDocuments.code
          this.fileArry[0].objectId = this.fileObjectId
          // 将附件信息入库
          fileInfo.saveFile(this.fileArry).then((res) => {
            this.fileArry = []
          })
        }
        this.multipleSelection = this.multipleSelection.map(function (item) {
          delete item.methodOfTransfer
          item.transDocId = res.data.transDocuments.objectId
          item.isChecked = 1
          return item
        })
        tdocuments.saveRelation(this.multipleSelection).then((res) => {
          // 跳转到出让文件列表页
          this.$router.push({path: '/lt/transferor/tender-file/sub-tender-file/index?roledsType=1&showStatus=false'})
        })
      })
    },
    // ***************************************************************自己方法**********************************************************************
    // 关联项目信息选择
    handleSelectionChange (selection, row) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        let temp = []
        for (let i = 0; i < selection.length; i++) {
          if (!temp.includes(selection[i].objectId)) {
            temp.push(selection[i])
          }
        }
        this.multipleSelection = temp
      }
    },
    handleSelectionAll (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection
      }
    },
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.updateStatus = true
      this.fileArry = file
    },
    // 查看详情
    detail () {
      // 查询关联的项目信息
      let url = {
        annoId: this.$route.query.annoId,
        flag: 1,
        transDocId: this.$route.query.objectId,
        type: 2
      }
      tdocuments.selectRelation(url).then((res) => {
        this.tableDataSeletion = res.data.refTrandocSectionList
        tdocuments.detail(this.$route.query.objectId).then((res) => {
          this.ruleForm = res.data.transferDocuments
          this.answerDataRange = [this.ruleForm.answerStartTime, this.ruleForm.answerEndTime]
          if (res.data.transferDocuments.fileInformations) {
            this.oldFileName = res.data.transferDocuments.fileInformations.fileName
            this.fileObjectId = res.data.transferDocuments.fileInformations.objectId
          }
          let url = {
            transDocId: res.data.transferDocuments.objectId
          }
          tdocuments.detailRelation(url).then((res) => {
            this.tableDataSeletion.map((tableItem, index) => {
              if (tableItem.isChecked === 1) {
                this.$refs.multipleTable.toggleRowSelection(this.tableDataSeletion[index], true)
              } else {
                this.$refs.multipleTable.toggleRowSelection(this.tableDataSeletion[index], false)
              }
            })
          })
        })
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
