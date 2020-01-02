<template>
  <div class="enterInfo" id="honerInfo">
    <p class="tips" v-if="isEdit">
      <span>编辑状态下，离开此标签未保存的信息将不会被保存</span>
      <el-button class="handleBtn" type="primary" @click="save('dataForm')">保存</el-button>
    </p>
    <p class="tips" v-else>
      <span>完善企业业绩信息，获得更多合作机会</span>
      <el-button class="handleBtn" type="primary"  @click="isEdit = true">编辑</el-button>
    </p>
    <el-form :model="dataForm" :rules="rules" ref="dataForm" label-width="120px" :validate-on-rule-change="true">
      <div class="mainContent">
        <h3 class="title">获奖情况</h3>
        <ul class="honorList">
          <li v-if="dataForm.honorsAndAwardsList && dataForm.honorsAndAwardsList.length !== 0"
              v-for="(item, index) in dataForm.honorsAndAwardsList" :key="item.index">
            <div class="img">
              <img :src="fileReviewUrl + fileInformations[index].relativePath"/>
              <span v-if="isEdit" class="del" @click="handleDel(1, index)"></span>
            </div>
            <el-form-item :prop="'honorsAndAwardsList.'+index+'.honorName'" :rules="rules.honorName" class="itemRule">
              <el-input v-if="isEdit" v-model="item.honorName" placeholder="获奖名称"></el-input>
              <p v-else :title="item.honorName">{{item.honorName}}</p>
            </el-form-item>
          </li>
          <li class="addData" v-if="isEdit">
            <div class="img">
              <p>资质信息</p>
              <i>建议分辨率140*200</i>
              <i>支持jpg、jpeg格式</i>
              <uploadFile @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" :fileType=dataForm.honorsAndAwardsList?dataForm.honorsAndAwardsList.length:0 :fileArrays="fileInformations" isImage="1"></uploadFile>
            </div>
          </li>
        </ul>
        <div class="nodata" v-if="!dataForm.honorsAndAwardsList || dataForm.honorsAndAwardsList.length === 0">
          <img src="../../../static/images/enterprise/nohoner.png"/>
          <p>暂无获奖情况</p>
        </div>
      </div>
      <div class="mainContent">
        <h3 class="title">主要客户介绍</h3>
        <div class="handle" v-if="isEdit"><el-button class="handleBtn" type="primary" @click="addTable(2)">+ 添加客户情况</el-button></div>
        <div class="tablemain">
          <el-table
            :data="dataForm.majorClientList"
            style="width: 100%">
            <el-table-column
              v-if="isEdit"
              class-name="del"
              width="30">
              <template slot-scope="scope">
                <span v-if="scope.$index !== 0" @click="handleDel(2, scope.$index)"></span>
              </template>
            </el-table-column>
            <el-table-column
              prop="clientName"
              width="260px"
              label="客户名称">
              <template slot-scope="scope">
                <el-form-item :prop="'majorClientList.'+scope.$index+'.clientName'" :rules="rules.clientName" class="itemRule">
                  <el-input  v-if="isEdit" v-model="scope.row.clientName" placeholder="请输入客户名称"></el-input>
                  <span v-else>{{scope.row.clientName}}</span>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              prop="projectName"
              label="项目或主要产品/服务">
              <template slot-scope="scope">
                <el-form-item :prop="'majorClientList.'+scope.$index+'.projectName'" :rules="rules.projectName" class="itemRule">
                  <el-input  v-if="isEdit" v-model="scope.row.projectName" placeholder="请输入项目或主要产品/服务"></el-input>
                  <span v-else>{{scope.row.projectName}}</span>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              prop="implementDate"
              width="280px"
              label="实施时间">
              <template slot-scope="scope">
                <el-form-item :prop="'majorClientList.'+scope.$index+'.implementDate'" :rules="rules.implementDate" class="itemRule">
                  <el-date-picker v-if="isEdit" v-model="scope.row.implementDate" value-format="yyyy-MM-dd" type="date" placeholder="选择日期">
                  </el-date-picker>
                  <span v-if="!isEdit && dataForm.majorClientList">{{dateFormat(scope.row.implementDate, 'yyyy-MM-dd')}}</span>
                </el-form-item>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="mainContent">
        <h3 class="title">与银行或金融机构合作情况</h3>
        <div class="handle" v-if="isEdit"><el-button class="handleBtn" type="primary" @click="addTable(3)">+ 添加合作情况</el-button></div>
        <div class="tablemain">
          <el-table
            :data="dataForm.financialCooperationList"
            style="width: 100%">
            <el-table-column
              v-if="isEdit"
              class-name="del"
              width="30">
              <template slot-scope="scope">
                <span v-if="scope.$index !== 0" @click="handleDel(3, scope.$index)"></span>
              </template>
            </el-table-column>
            <el-table-column prop="date" label="机构名称（总分支级别）" width="260px">
              <template slot-scope="scope">
                <el-form-item :prop="'financialCooperationList.'+scope.$index+'.bankName'" :rules="rules.bankName" class="itemRule">
                  <el-input v-if="isEdit" v-model="scope.row.bankName" placeholder="请输入机构名称"></el-input>
                  <span v-else>{{scope.row.bankName}}</span>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              prop="projectName"
              label="项目或主要产品/服务">
              <template slot-scope="scope">
                <el-form-item :prop="'financialCooperationList.'+scope.$index+'.projectName'" :rules="rules.projectName" class="itemRule">
                  <el-input v-if="isEdit" v-model="scope.row.projectName" placeholder="请输入项目或主要产品/服务"></el-input>
                  <span v-else>{{scope.row.projectName}}</span>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              prop="implementDate"
              label="实施时间"
              width="280px">
              <template slot-scope="scope">
                <el-form-item :prop="'financialCooperationList.'+scope.$index+'.implementDate'" :rules="rules.implementDate" class="itemRule">
                  <el-date-picker v-if="isEdit" v-model="scope.row.implementDate" value-format="yyyy-MM-dd" type="date" placeholder="选择日期">
                  </el-date-picker>
                  <span v-if="!isEdit && dataForm.financialCooperationList">{{dateFormat(scope.row.implementDate, 'yyyy-MM-dd')}}</span>
                </el-form-item>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import uploadFile from '@/components/upload/publicFileUpload'
import {fileReviewUrl, dateFormat} from '@/assets/js/common'
import {attachinfo} from '@/api/system'
export default {
  name: 'honerInfo',
  components: {
    uploadFile
  },
  data () {
    return {
      // 判断编辑、查看状态 ；默认应为查看状态
      isEdit: false,
      fileReviewUrl: fileReviewUrl, // 图片展示路径
      dataForm: {
        enterpriseId: this.$store.getters.authUser.enterpriseId
      },
      fileInformations: [],
      rules: {
        honorName: [
          { message: '请输入获奖名称', required: true, trigger: 'blur' }
        ],
        clientName: [
          { message: '请输入客户名称', required: true, trigger: 'blur' }
        ],
        projectName: [
          { message: '请输入项目或主要产品/服务', required: true, trigger: 'blur' }
        ],
        implementDate: [
          { message: '请输入实施时间', required: true, trigger: 'blur' }
        ],
        bankName: [
          { message: '请输入机构名称', required: true, trigger: 'blur' }
        ]
      },
      tableData: [],
      dateFormat: dateFormat
    }
  },
  methods: {
    // 上传文件
    uploadSuccess (file, fileOrder) {
      if (!this.dataForm.honorsAndAwardsList) {
        this.dataForm.honorsAndAwardsList = []
      }
      if (this.fileInformations[fileOrder]) {
        this.fileInformations.splice(fileOrder, 1, file)
        this.dataForm.honorsAndAwardsList[fileOrder].fileInformation = file
      } else {
        this.dataForm.honorsAndAwardsList.push({
          honorName: '',
          fileInformation: file
        })
        this.fileInformations.push(file)
      }
    },
    // 删除文件
    deleteSuccess (fileId, fileType) {
      this.fileInformations = this.fileInformations.filter(item => item.relativePath !== fileId)
    },
    // 初始化数据
    detail () {
      let url = {
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }
      attachinfo.queryOne(url).then((res) => {
        this.$set(this.dataForm, 'objectId', res.data.enterpriseExpand.objectId)
        this.$set(this.dataForm, 'honorsAndAwardsList', res.data.enterpriseExpand.honorsAndAwardsList)
        this.$set(this.dataForm, 'majorClientList', res.data.enterpriseExpand.majorClientList)
        this.$set(this.dataForm, 'financialCooperationList', res.data.enterpriseExpand.financialCooperationList)
        // 提取文件信息
        if (this.dataForm.honorsAndAwardsList) {
          this.dataForm.honorsAndAwardsList.forEach(item => {
            let file = item.fileInformation === null ? {} : item.fileInformation
            this.fileInformations.push(file)
          })
        }
      })
    },
    // 保存数据
    save (formName) {
      this.removeEmptyInfo()
      setTimeout(() => {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            attachinfo.update(this.dataForm).then((res) => {
              if (res.data.resCode === '0000') {
                this.isEdit = false
                this.$store.commit('SET_ENTERPRISE_SAVE', new Date().getTime())
              }
            })
          }
        })
      }, 100)
    },
    // 去除空信息
    removeEmptyInfo () {
      if (this.dataForm.majorClientList) {
        let transit = []
        this.dataForm.majorClientList.forEach((item) => {
          if (item['clientName'] || item['projectName'] || item['implementDate']) {
            transit.push(item)
          }
        })
        this.dataForm.majorClientList = transit
      }
      if (this.dataForm.financialCooperationList) {
        let transit = []
        this.dataForm.financialCooperationList.forEach((item) => {
          if (item['bankName'] || item['projectName'] || item['implementDate']) {
            transit.push(item)
          }
        })
        this.dataForm.financialCooperationList = transit
      }
    },
    addTable (tableNumber) {
      // 客户信息
      if (tableNumber === 2) {
        let newline = {}
        if (!this.dataForm.majorClientList) {
          this.dataForm.majorClientList = []
        }
        this.dataForm.majorClientList.push(newline)
        return
      }
      // 金融合作
      if (tableNumber === 3) {
        let newline = {}
        if (!this.dataForm.financialCooperationList) {
          this.dataForm.financialCooperationList = []
        }
        this.dataForm.financialCooperationList.push(newline)
      }
    },
    handleDel (tableNumber, index) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        // 获奖情况
        if (tableNumber === 1) {
          this.dataForm.honorsAndAwardsList.splice(index, 1)
          this.fileInformations.splice(index, 1)
          return
        }
        // 主要客户
        if (tableNumber === 2) {
          this.dataForm.majorClientList.splice(index, 1)
          return
        }
        // 金融合作
        if (tableNumber === 3) {
          this.dataForm.financialCooperationList.splice(index, 1)
        }
      }).catch(() => {
        return false
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>

<style lang="less">
  #honerInfo{
    .honorList{
      overflow: hidden;
      margin: 10px auto;
    }
    .honorList li{
      float: left;
      width: 190px;
      overflow: hidden;
      margin: 10px 20px;
    }
    .honorList li .img{
      width: 149px;
      height: 212px;
      margin: 10px 20px;
      border: 1px solid #dddddd;
      position: relative;
    }
    .honorList li .img img{
      max-width: 100%;
    }
    .honorList li .img span.del{
      width: 16px;
      height: 16px;
      display: inline-block;
      background: url("../../../static/images/enterprise/close.png") no-repeat;
      position: absolute;
      right: -8px;
      top: -8px;
      cursor: pointer;
    }
    .honorList li input{
      width: 100%;
      height: 30px;
      line-height: 30px;
      padding: 0 5%;
    }
    .honorList li p{
      width: 100%;
      height:30px;
      line-height: 30px;
      text-align: center;
      overflow: hidden;
      white-space: nowrap;
      -ms-text-overflow: ellipsis;
      text-overflow: ellipsis;
    }
    .honorList li.addData{
      cursor: pointer;
    }
    .honorList li.addData .img{
      background: #f5f7fa url("../../../static/images/enterprise/plus.png") top 10px center no-repeat;
    }
    .honorList li.addData p{
      margin-top: 70px;
      line-height: 28px;
      font-weight: bold;
    }
    .honorList li.addData i{
      font-style: normal;
      font-size: 12px;
      color: #999999;
      text-align: center;
      padding: 0 20px;
    }
    .honorList li.addData .webuploader-container{
      margin: 16px 0 0 20px;
    }
    .honorList li.addData span{
      float: left;
      margin: 100px 10px 0 0;
      color: #999999;
    }
    .honorList li.addData .webuploader-pick{
      width: 72px;
      height: 32px;
      line-height: 32px;
      padding-left: 20px;
    }
    .itemRule .el-form-item__content{
      margin-left: 0!important;
    }
  }
</style>
