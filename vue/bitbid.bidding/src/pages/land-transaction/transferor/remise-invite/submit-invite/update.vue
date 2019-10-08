<template>
  <div id="addbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/remise-invite/submit-invite?roledsType=1&showStatus=false' }">提交出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item>修改出让邀请书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" :rules="rules" ref="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="projectName">
                  <el-input v-model="sectionForm.projectName" placeholder="请输入项目名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="projectNumber">
                  <el-input v-model="sectionForm.projectNumber" placeholder="请输入项目编号"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出让文件获取方法：" prop="fileObtainStartMethod">
                  <el-input v-model="sectionForm.fileObtainStartMethod" placeholder="请输入出让文件获取方法"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--相关地块信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">相关地块信息</span>
          <div class="formmian">
            <div v-for="(relevantMassif,index) in sectionForm.landInformations" :key="index">
              <el-row>
                <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">块地{{index + 1}}</span>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块编号：" prop="contacts">
                    <span>{{relevantMassif.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="估价报告备案号：" prop="contacts">
                    <span>{{relevantMassif.valuationReport}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="竞买保证金(万元)：" prop="contacts">
                    <span>{{relevantMassif.bidBond}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="是否存在底价：" prop="contacts">
                    <span v-if="relevantMassif.FloorPrice === 0">是</span>
                    <span v-if="relevantMassif.FloorPrice === 1">否</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="土地用途：" prop="contacts">
                    <span>{{relevantMassif.landUse}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="地块估算价(元)：" prop="contacts">
                    <span>{{relevantMassif.conputedPrice}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="选择受让人：">
                    <el-button style="background: rgba(25, 158, 216, 1);margin-bottom: 25px" @click="choiceAssigneeBtn">
                      <span style="color:#fff">选择受让人</span>
                    </el-button>
                    <el-dialog title="选择受让人" :visible.sync="dialogTableVisible">
                      <el-table
                        :data="relevantMassif.assigneeData"
                        border
                        ref="multipleTable"
                        tooltip-effect="dark"
                        @select="handleSelectionChange"
                        @select-all="handleSelectionAll">
                        <el-table-column type="selection" width="55"  align="center"></el-table-column>
                        <el-table-column property="enterpersName" label="企业名称"></el-table-column>
                        <el-table-column property="Industry" label="所属行业"></el-table-column>
                        <el-table-column property="Contacts" label="联系人"></el-table-column>
                        <el-table-column property="contactWay" label="联系方式"></el-table-column>
                      </el-table>
                      <div class="button_box" style="text-align: center;margin-top: 30px">
                        <el-button type="primary" @click="determineBtn('relevantMassif')">确定</el-button>
                        <el-button class="cancel" @click="dialogCancelBtn">取消</el-button>
                      </div>
                    </el-dialog>
                    <el-table
                      :data="relevantMassif.assigneeTable"
                      border>
                      <el-table-column
                        property="enterpersName"
                        label="受让人名称">
                      </el-table-column>
                      <el-table-column
                        property="contactWay"
                        label="手机">
                      </el-table-column>
                      <el-table-column
                        property="Contacts"
                        label="邮箱">
                      </el-table-column>
                      <el-table-column
                        label="操作"
                        width="120" align="center">
                        <template slot-scope="scope">
                          <el-button type="text" size="small"
                          @click.native.prevent="deleteRow(scope.$index, assigneeTable)">
                            删除
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="受让人资格条件：">
                    <el-input type="textarea" v-model="relevantMassif.desc"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <!--相关地块信息-->
        <!--邀请书内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">邀请书内容</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="邀请书名称：" prop="annoNumber">
                  <el-input v-model="sectionForm.annoNumber" placeholder="请输入邀请书名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告类型：" prop="contacts">
                  <span v-if="sectionForm.type === 1">拍卖出让公告</span>
                  <span v-if="sectionForm.type === 2">挂牌出让公告</span>
                  <span v-if="sectionForm.type === 3">资格后审出让公告</span>
                  <span v-if="sectionForm.type === 4">资格预审出让公告</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="邀请书内容：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="false" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="附件：">
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--邀请书内容-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('sectionForm', 1)" :loading="subStatus">提交</el-button>
          <el-button type="primary" class="save" @click="submitForm('sectionForm', 0)" :loading="subStatus">保存</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import { landAnno } from '@/api'
export default {
  components: {
    commonFileUpload,
    editor
  },
  data () {
    return {
      content: '', // 富文本内容
      sectionForm: {},
      dialogTableVisible: false, // 受让人弹框
      subStatus: false,
      rules: {
        projectName: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { min: 1, max: 500, message: '长度在 1~500个字符', trigger: ['blur', 'change'] }
        ],
        projectNumber: [
          { required: true, message: '请输入项目编号', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1~100个字符', trigger: ['blur', 'change'] }
        ],
        fileObtainStartMethod: [
          { required: true, message: '请输入出让文件获取方法', trigger: 'blur' },
          { min: 1, max: 500, message: '长度在 1~500个字符', trigger: ['blur', 'change'] }
        ],
        annoNumber: [
          { required: true, message: '请输入邀请书名称', trigger: 'blur' },
          { min: 1, max: 500, message: '长度在 1~500个字符', trigger: ['blur', 'change'] }
        ]
      },
      // 业务类型
      fileType: 1,
      // 文件上传保存的数组
      fileArry: [],
      oldFileName: ''
    }
  },
  methods: {
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    // 提交保存
    submitForm (sectionForm, flag) {
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          this.sectionForm.status = flag
          this.sectionForm.content = this.$refs.ueditor.getContent()
          this.sectionForm.landInfoIds = this.$route.query.landInfoIds
          this.subStatus = true
          landAnno.update(this.sectionForm).then((res) => {
            this.subStatus = false
            this.$router.push({path: '/lt/transferor/remise-invite/submit-invite?roledsType=1&showStatus=false'})
          })
        } else {
          return false
        }
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/lt/transferor/remise-invite/submit-invite?roledsType=1&showStatus=false'})
    },
    // ***************************************************************自己方法**********************************************************************
    // ----------------------------------------------------选择项目--------------------------------------
    // 单独选择
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
    // 全部选择
    handleSelectionAll (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection
      }
    },
    // ------------------------------------------------选择受让人-------------------------------------------
    choiceAssigneeBtn () {
      this.dialogTableVisible = true
    },
    // ------------------------------------------------关闭弹框-------------------------------------------
    dialogCancelBtn () {
      this.dialogTableVisible = false
    },
    // ------------------------------------------------选择后确定-------------------------------------------
    determineBtn (relevantMassif) {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择缴费标段'
        })
        return false
      } else {
        this.dialogTableVisible = false
      }
    },
    // 删除选择后展示出来的数据
    deleteRow (index, rows) {
      rows.splice(index, 1)
    },
    landAnnoInfo () {
      landAnno.detail(this.$route.query.objectId).then((res) => {
        this.sectionForm = res.data.landAnno
        console.log(this.sectionForm)
        this.content = res.data.landAnno.content
        this.$refs.ueditor.setContent(this.content) // 设置富文本内容
      })
    }
  },
  mounted () {
    this.landAnnoInfo()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
