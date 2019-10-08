<template>
  <div id="addbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/remise-invite/submit-invite?roledsType=1&showStatus=false' }">提交出让邀请</el-breadcrumb-item>
        <el-breadcrumb-item>添加出让邀请书</el-breadcrumb-item>
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
            <div v-for="(info,index) in landInformation" :key="index">
              <el-row>
                <span style="margin-left: 80px;margin-bottom: 22px;color: #000;font-weight: 800">地块{{index + 1}}</span>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地块编号：" prop="contacts">
                    <span>{{info.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="估价报告备案号：" prop="contacts">
                    <span>{{info.valuationReport}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="竞买保证金(万元)：" prop="contacts">
                    <span>{{info.bidBond}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="是否存在底价：" prop="contacts">
                    <span v-if="info.isFloorPrice === 1">是</span>
                    <span v-if="info.isFloorPrice === 0">否</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="土地用途：" prop="contacts" class="bitianicon">
                    <span>{{info.landUse}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="地块估算价(元)：" prop="contacts" class="bitianicon">
                    <span>{{info.conputedPrice}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="选择受让人：" class="bitianicon">
                    <el-button style="background: rgba(25, 158, 216, 1);margin-bottom: 25px" @click="choiceAssigneeBtn">
                      <span style="color:#fff">选择受让人</span>
                    </el-button>
                    <el-dialog title="选择受让人" :visible.sync="dialogTableVisible">
                      <el-table
                        :data="landAssignee"
                        border
                        ref="multipleTable"
                        tooltip-effect="dark"
                        @select="handleSelectionChange"
                        @select-all="handleSelectionAll">
                        <el-table-column type="selection" width="55"  align="center"></el-table-column>
                        <el-table-column property="enterpriseName" label="企业名称"></el-table-column>
                        <el-table-column property="industryName" label="所属行业"></el-table-column>
                        <el-table-column property="contactName" label="联系人"></el-table-column>
                        <el-table-column property="contactCellphone" label="联系方式"></el-table-column>
                      </el-table>
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
                      <div class="button_box" style="text-align: center;margin-top: 30px">
                        <el-button type="primary" @click="determineBtn(info.objectId)">确定</el-button>
                        <el-button class="cancel" @click="dialogCancelBtn">取消</el-button>
                      </div>
                    </el-dialog>
                    <el-table
                      :data="assigneeArr"
                      border>
                      <el-table-column
                        property="enterpriseName"
                        label="受让人名称">
                      </el-table-column>
                      <el-table-column
                        property="contactCellphone"
                        label="联系方式">
                      </el-table-column>
                      <el-table-column
                        property="contactCellemail"
                        label="邮箱">
                      </el-table-column>
                      <el-table-column
                        label="操作"
                        width="120" align="center">
                        <template slot-scope="scope">
                          <el-button type="text" size="small"
                          @click.native.prevent="deleteRow(scope.row.objectId, info.objectId)">
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
                    <span>{{info.prerequisites}}</span>
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
                <el-form-item label="公告类型：" prop="type">
                  <el-select v-model="sectionForm.type">
                    <el-option
                      v-for="item in annoType"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="邀请书内容：" prop="content" class="bitianicon">
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
import { landProject, landAnno } from '@/api'
import * as industry from '@/assets/js/industry'
export default {
  components: {
    commonFileUpload,
    editor
  },
  data () {
    return {
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      subStatus: false,
      content: '', // 富文本内容
      sectionForm: {},
      dialogTableVisible: false, // 受让人弹框
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
      oldFileName: '',
      // 公告类型
      annoType: [
        {
          label: '拍卖出让公告',
          value: 1
        },
        {
          label: '挂牌出让公告',
          value: 2
        },
        {
          label: '资格后审出让公告',
          value: 3
        },
        {
          label: '资格预审出让公告',
          value: 4
        }
      ],
      // 相关地块信息
      landInformation: [],
      // 受让人信息
      landAssignee: [],
      industryData: industry.industry,
      multipleSelection: [],
      assigneeArr: []
    }
  },
  methods: {
    // 相关地块信息
    landInfo () {
      let arrLength = this.$route.query.landInfoIds.length
      for (var i = 0; i < arrLength; i++) {
        landProject.detail(this.$route.query.landInfoIds[i]).then(res => {
          this.landInformation.push(res.data.landInformation)
        })
      }
    },
    // 受让人信息
    assignee () {
      let url = {
        pageNo: 0,
        pageSize: 5
      }
      landAnno.queryAssignee(url).then((res) => {
        this.landAssignee = res.data.enterprisePageInfo.list
        this.total = res.data.enterprisePageInfo.total
        this.landAssignee.map((ite) => {
          let industryStr = ''
          this.industryData.map((item) => {
            if (ite.industry === item.value) {
              industryStr = item.label
              item.children.map((item1) => {
                if (ite.industrySecond === item1.value) {
                  industryStr += item1.label
                }
              })
            }
          })
          ite.industryName = industryStr
        })
      })
    },
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    // 提交保存
    submitForm (sectionForm, flag) {
      if (this.$refs.ueditor.getContent() === '' || this.$refs.ueditor.getContent() === null || this.$refs.ueditor.getContent() === undefined) {
        this.$message({
          type: 'warning',
          message: '请填写内容'
        })
        return false
      }
      if (this.assigneeArr.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择受让人'
        })
        return false
      }
      this.$refs[sectionForm].validate((valid) => {
        if (valid) {
          this.sectionForm.status = flag
          this.sectionForm.content = this.$refs.ueditor.getContent()
          this.sectionForm.landInfoIds = this.$route.query.landInfoIds
          this.subStatus = true
          landAnno.save(this.sectionForm).then((res) => {
            this.subStatus = false
            this.$router.push({path: '/lt/transferor/remise-invite/submit-invite?roledsType=1&showStatus=false'})
          })
        } else {
          return false
        }
      })
    },
    // ----------------------------------------------------分页--------------------------------------
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.assignee()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.assignee()
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
    // ------------------------------------------------选择受让人弹框-------------------------------------------
    choiceAssigneeBtn () {
      this.dialogTableVisible = true
    },
    // ------------------------------------------------关闭弹框-------------------------------------------
    dialogCancelBtn () {
      this.dialogTableVisible = false
    },
    // ------------------------------------------------选择后确定-------------------------------------------
    determineBtn (objectId) {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择受让人'
        })
        return false
      }
      let userIdsArr = []
      this.multipleSelection.map((item) => {
        userIdsArr.push(item.enterpriseId)
      })
      let param = {
        objectId: objectId,
        userIds: userIdsArr
      }
      landProject.saveRefAssignee(param).then((res) => {
        landProject.queryUserIdsById(objectId).then((resp) => {
          resp.data.userIds.map((item) => {
            landProject.queryAssigneeById(item).then((respo) => {
              this.assigneeArr.push(respo.data.enterprise)
            })
          })
        })
        this.dialogTableVisible = false
      })
    },
    // 删除选择后展示出来的数据
    deleteRow (userId, objectId) {
      this.assigneeArr = []
      landProject.delRefAssignee(userId, objectId).then((res) => {
        landProject.queryUserIdsById(objectId).then((resp) => {
          resp.data.userIds.map((item) => {
            landProject.queryAssigneeById(item).then((respo) => {
              this.assigneeArr.push(respo.data.enterprise)
            })
          })
        })
      })
    }
  },
  mounted () {
    this.landInfo()
    this.assignee()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
