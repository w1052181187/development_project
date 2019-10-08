<template>
  <div id="process" class="process">
    <div class="top">
      <el-button type="text" @click="returnindex">&lt; 首页</el-button>
      <!--<el-button class="lookbtn" type="primary"><img src="../../../../static/images/system-quantybill/ico2.png"/> 查看标书</el-button>-->
    </div>
    <div class="processmain">
      <step :type="type" :currentActive="currentActive"></step>
      <!-- 招标第一步  导入招标数据 -->
      <treebidphase @updateProgram = "updateProgram" :program = "program"  @childValue = "childValue" :fileName = "fileName" :columns="columns" :tree-structure="true" :data-source="dataSource" v-if="type === 1 && currentActive === 1"></treebidphase>
      <!-- 招标第二步  检查数据-->
      <checkbid v-if="type === 1 && currentActive === 2" :projectId = "projectId" @IsNext = "IsNext"></checkbid>
      <!--  招标第三步  填写招标信息-->
       <bidinfo v-if="type === 1 && currentActive === 3" ref="bidinfo" v-on:childSubValue="childSubValue" @childFormData = "childFormData" :projectId = "projectId"></bidinfo>
      <!--  投标、招标控制价第一步 导入数据-->
      <tbtreebidphase  @updateProgram = "updateProgram" :program = "program" @childValue = "childValue" :fileName = "fileName" :columns="columns" :tree-structure="true" :data-source="dataSource" v-if="type !== 1 && currentActive === 1"></tbtreebidphase>
      <!--  投标、招标控制价导入数据符合性检查不合格之后的信息-->
      <el-dialog title="提示：导入数据与招标文件不对应，请检查后重新导入" :visible.sync="dialogTableVisible">
        <el-table :data="gridData" height="500">
          <el-table-column property="address" label="项目" show-overflow-tooltip></el-table-column>
          <el-table-column property="singleName" label="单项工程" show-overflow-tooltip></el-table-column>
          <el-table-column property="unitName" label="单位工程" show-overflow-tooltip></el-table-column>
          <el-table-column property="code" label="编码" show-overflow-tooltip></el-table-column>
          <el-table-column property="name" label="名称" show-overflow-tooltip></el-table-column>
          <el-table-column property="content" label="错误内容" show-overflow-tooltip></el-table-column>
        </el-table>
      </el-dialog>
      <!--  投标第二步  填写投标信息-->
      <tenderinfo v-if="type === 2 && currentActive === 3" ref="tenderinfo" :projectId = "projectId" v-on:childSubValue="childSubValue" @childFormData = "childFormData" ></tenderinfo>
      <!--  投标、招标控制价 第二步  标书检查-->
      <checkunbid v-if="type !== 1 && currentActive === 2" :projectId = "projectId" @IsNext = "IsNext"></checkunbid>
      <!--  招标控制价第三步  填写招标控制价信息-->
      <bidpriceinfo v-if="type === 3 && currentActive === 3" ref="bidpriceinfo" :projectId = "projectId" v-on:childSubValue="childSubValue" @childFormData = "childFormData"></bidpriceinfo>
      <!--<treeunitproject :columns="columns2" :tree-structure="true" :data-source="dataSource2" v-if="type !== 1 && currentActive === 2"></treeunitproject>-->
      <!--<tenderbook v-if="currentActive === 4" @childVal="childVal"></tenderbook>-->
    </div>
    <div class="process_button">
      <el-button type="primary" v-if="currentActive !== 1 " @click="prevStep">上一步</el-button>
      <el-button type="primary"  @click="nextStep">下一步</el-button>
      <!--<el-button type="primary" v-if="currentActive === 4" @click="_saveEndFile">生成标书</el-button>-->
      <!--<el-button type="primary"  v-if="currentActive === 4" @click="lookStatement">查看报表</el-button>-->
    </div>
  </div>
</template>
<script>
import { billPushData } from '@/api'
export default {
  data () {
    return {
      currentActive: this.$route.query.operationStatus ? Number(this.$route.query.operationStatus) : 1,
      // 步骤条
      type: Number(this.$route.query.type),
      columns: [
        {
          text: '工程编码',
          dataIndex: 'number'
        }, {
          text: '工程名称',
          dataIndex: 'name'
        }, {
          text: '工程级别',
          dataIndex: 'rank'
        }
      ],
      dataSource: [],
      columns2: [
        {
          text: '工程名称',
          dataIndex: 'name'
        }, {
          text: '状态',
          dataIndex: 'age'
        }, {
          text: '文件名称',
          dataIndex: 'sex'
        }
      ],
      dataSource2: [],
      isSubmit: '',
      childV: null,
      projectId: this.$route.query.projectId ? this.$route.query.projectId : -1,
      fileName: '',
      IsNextStep: false,
      bidFormInfo: {},
      endFileName: '',
      gridData: [],
      dialogTableVisible: false,
      program: {
        programId: this.$route.query.programId,
        projectId: this.$route.query.projectId ? this.$route.query.projectId : null
      }
      // childTenderSubVal: null,
      // tenderFormData: ''
    }
  },
  /** 注册组件 */
  components: {
    // 步骤条
    step: resolve => require(['./step-menu'], resolve),
    // 填写招标信息
    bidinfo: resolve => require(['./bid-info'], resolve),
    // 填写投标信息
    tenderinfo: resolve => require(['./tender-info'], resolve),
    // 填写招标控制价信息
    bidpriceinfo: resolve => require(['./bidprice-info'], resolve),
    // 招标标书检查
    checkbid: resolve => require(['./check-bid'], resolve),
    // 非招标标书检查
    checkunbid: resolve => require(['./check-unbid'], resolve),
    // 招标导入文件
    treebidphase: resolve => require(['./tree-bidphase'], resolve),
    // 招标导入文件
    tbtreebidphase: resolve => require(['./tbtree-bidphase.vue'], resolve),
    // 单位工程导入方式
    treeunitproject: resolve => require(['./tree-unitproject'], resolve)
    // 生成标书
    // tenderbook: resolve => require(['./tender-book'], resolve)
  },
  methods: {
    returnindex () {
      this.$router.push({path: `/system-quantybill`})
    },
    IsNext (val) {
      this.IsNextStep = val
    },
    // 招标 导入招标数据 上传之后返回树形 数据
    childValue (val) {
      if (val) {
        this.childV = val
        if (this.childV.error === 1) {
          this.gridData = []
          this.dialogTableVisible = true
          this.fileName = ''
          let obj = this.childV
          for (let i in obj) {
            if (obj[i].errorList) {
              for (var index = 0; index < obj[i].errorList.length; index++) {
                this.gridData.push(obj[i].errorList[index])
              }
            }
          }
        } else {
          this.dataSource = []
          this.dataSource.push(this.childV.treeModel)
          console.log(this.dataSource)
          this.fileName = this.childV.fileInformation.fileName
          // 招标  下一步时传objectId 给 标书检查子组件
          this.projectId = this.childV.objectId
          let sectionNum = 1
          for (let i in this.dataSource) {
            let curSection = this.dataSource[i]
            curSection.number = sectionNum > 9 ? '' + sectionNum : '0' + sectionNum
            let singleNum = 1
            for (let j in curSection.children) {
              let curSingle = curSection.children[j]
              curSingle.number = curSection.number + (singleNum > 9 ? '' + singleNum : '0' + singleNum)
              let unitNum = 1
              for (let k in curSingle.children) {
                let curUnit = curSingle.children[k]
                curUnit.number = curSingle.number + (unitNum > 9 ? '' + unitNum : '0' + unitNum)
                unitNum++
              }
              singleNum++
            }
            sectionNum++
          }
        }
      }
    },
    // 填写招标信息子组件传给父组件的值,判断验证是否通过
    childSubValue (childSubValue) {
      this.isSubmit = childSubValue
    },
    // 填写招标信息接收子组件的form表单数据
    childFormData (val) {
      this.bidFormInfo = val
    },
    // 生成标书接收filename
    // childVal (val) {
    //   this.endFileName = val
    // },
    IEVersion () {
      let browser = navigator.appName
      let bversion = navigator.appVersion
      let version = bversion.split(';')
      if (version[1] !== undefined) { // ie浏览器上使用
        let trimVersion = version[1].replace(/[ ]/g, '')
        if (browser === 'Microsoft Internet Explorer' && trimVersion === 'MSIE9.0') {
          return 1
        } else if (browser === 'Microsoft Internet Explorer' && trimVersion === 'MSIE10.0') {
          return 2
        } else if (browser === 'Microsoft Internet Explorer' && trimVersion === 'MSIE11.0') {
          return 3
        }
      }
    },
    // 下一步
    nextStep () {
      if (this.type === 1) {
        if (this.currentActive === 1) {
          if (!this.dataSource.length) {
            this.$message({
              type: 'warning',
              message: '请导入招标数据'
            })
          } else if (this.projectId !== -1) {
            let params = {
              objectId: this.projectId,
              operationStatus: this.currentActive
            }
            billPushData.updateprojectDetail(params).then((res) => {
            })
            this.currentActive++
          }
        } else if (this.currentActive === 2) {
          if (this.IsNextStep === true) {
            let params = {
              objectId: this.projectId,
              operationStatus: this.currentActive
            }
            billPushData.updateprojectDetail(params).then((res) => {
            })
            this.currentActive++
          } else {
            this.$message({
              type: 'warning',
              message: '标书检查不合格，请修改后重新导入数据'
            })
          }
        } else if (this.currentActive === 3) {
          this.$refs['bidinfo'].validateForm()
          if (this.isSubmit === 'true') {
            this.$confirm('请确认信息是否正确，确定后方案将不能修改!', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              billPushData.updateItemInfor(this.bidFormInfo).then((res) => {
                if (res.data.resCode == '0000') {
                  let params = {
                    objectId: this.projectId,
                    operationStatus: 4
                  }
                  billPushData.updateprojectDetail(params).then((res) => {
                    this.$router.push({path: `/system-quantybill/statement`, query: {type: this.$route.query.type, projectId: this.projectId, turn: 1}})
                  })
                } else {
                  alert(res.data.resMessage)
                }
              })
            }).catch(() => {
              return false
            })
          }
        } else {
          // this.currentActive++
          this.$router.push({path: `/system-quantybill/statement`, query: {type: this.$route.query.type, projectId: this.projectId, turn: 1}})
        }
      } else if (this.type === 2) {
        if (this.currentActive === 1) {
          if (!this.dataSource.length) {
            this.$message({
              type: 'warning',
              message: '请导入投标数据'
            })
          } else if (this.projectId !== -1) {
            let params = {
              objectId: this.projectId,
              operationStatus: this.currentActive
            }
            billPushData.updateprojectDetail(params).then((res) => {
            })
            this.currentActive++
          }
        } else if (this.currentActive === 2) {
          if (this.IsNextStep === true) {
            let params = {
              objectId: this.projectId,
              operationStatus: this.currentActive
            }
            billPushData.updateprojectDetail(params).then((res) => {
            })
            this.currentActive++
          } else {
            this.$message({
              type: 'warning',
              message: '标书检查不合格，请修改后重新导入数据'
            })
          }
        } else if (this.currentActive === 3) {
          this.$refs['tenderinfo'].validateForm()
          if (this.isSubmit === 'true') {
            this.$confirm('请确认信息是否正确，确定后方案将不能修改!', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              billPushData.updateItemInfor(this.bidFormInfo).then((res) => {
                if (res.data.resCode == '0000') {
                  let params = {
                    objectId: this.projectId,
                    operationStatus: 4
                  }
                  billPushData.updateprojectDetail(params).then((res) => {
                    this.$router.push({path: `/system-quantybill/statement`, query: {type: this.$route.query.type, projectId: this.projectId, turn: 1}})
                  })
                } else {
                  alert(res.data.resMessage)
                }
              })
            }).catch(() => {
              return false
            })
          }
        } else {
          // this.currentActive++
          this.$router.push({path: `/system-quantybill/statement`, query: {type: this.$route.query.type, projectId: this.projectId, turn: 1}})
        }
      } else if (this.type === 3) {
        if (this.currentActive === 1) {
          if (!this.dataSource.length) {
            this.$message({
              type: 'warning',
              message: '请导入招标控制价数据'
            })
          } else if (this.projectId !== -1) {
            let params = {
              objectId: this.projectId,
              operationStatus: this.currentActive
            }
            billPushData.updateprojectDetail(params).then((res) => {
            })
            this.currentActive++
          }
        } else if (this.currentActive === 2) {
          if (this.IsNextStep === true) {
            let params = {
              objectId: this.projectId,
              operationStatus: this.currentActive
            }
            billPushData.updateprojectDetail(params).then((res) => {
            })
            this.currentActive++
          } else {
            this.$message({
              type: 'warning',
              message: '标书检查不合格，请修改后重新导入数据'
            })
          }
        } else if (this.currentActive === 3) {
          this.$refs['bidpriceinfo'].validateForm()
          if (this.isSubmit === 'true') {
            this.$confirm('请确认信息是否正确，确定后方案将不能修改!', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              billPushData.updateItemInfor(this.bidFormInfo).then((res) => {
                if (res.data.resCode == '0000') {
                  let params = {
                    objectId: this.projectId,
                    operationStatus: 4
                  }
                  billPushData.updateprojectDetail(params).then((res) => {
                    this.$router.push({path: `/system-quantybill/statement`, query: {type: this.$route.query.type, projectId: this.projectId, turn: 1}})
                  })
                } else {
                  alert(res.data.resMessage)
                }
              })
            }).catch(() => {
              return false
            })
          }
        } else {
          let params = {
            objectId: this.projectId,
            operationStatus: this.currentActive
          }
          billPushData.updateprojectDetail(params).then((res) => {
          })
          // this.currentActive++
          this.$router.push({path: `/system-quantybill/statement`, query: {type: this.$route.query.type, projectId: this.projectId, turn: 1}})
        }
      } else {
        let params = {
          objectId: this.projectId,
          operationStatus: this.currentActive
        }
        billPushData.updateprojectDetail(params).then((res) => {
        })
        // this.currentActive++
        this.$router.push({path: `/system-quantybill/statement`, query: {type: this.$route.query.type, projectId: this.projectId, turn: 1}})
      }
    },
    // 上一步
    prevStep () {
      this.isSubmit = ''
      this.currentActive--
      if (this.currentActive === 1) {
        this.dataSource = []
      }
    },
    // 生成标书
    // _saveEndFile () {
    //   if (this.endFileName) {
    //     let params = {
    //       objectId: this.projectId,
    //       operationStatus: this.currentActive
    //     }
    //     billPushData.updateprojectDetail(params).then((res) => {
    //     })
    //     let comurl = process.env.COMPONENTS_BASE_API
    //     let url = comurl + 'comptend/project/download/localFile/end/' + this.projectId + '?fileName=' + encodeURI(this.endFileName)
    //     window.open(url)
    //   } else {
    //     this.$message({
    //       type: 'warning',
    //       message: '请输入文件名称'
    //     })
    //   }
    // },
    // 查看报表
    // lookStatement () {
    //   let routeUrl = this.$router.resolve({
    //     path: '/system-quantybill/statement',
    //     query: {type: this.$route.query.type, projectId: this.projectId}
    //   })
    //   window.open(routeUrl.href, '_blank')
    //   // this.$router.push({path: `/system-quantybill/statement`, query: {type: this.$route.query.type, projectId: this.projectId}})
    // }
    updateProgram (program) {
      this.program = program
    }
  },
  watch: {
  },
  mounted () {
  }
}
</script>
<style lang="less">
  #process{
    width: 1200px;
    min-height: 300px;
    background: #ffffff;
    margin: 20px auto;
    padding-bottom: 50px;
    .top{
      width: 98%;
      padding: 0 1%;
      height: 62px;
      line-height: 62px;
      border-bottom: 1px solid #eeeeee;
    }
    .top .el-button--text span{
      font-size: 16px;
    }
    .top .el-button--primary{
      margin-top: 11px;
      float: right;
      background-color: #ff8a00;
      border-color: #ff8a00;
    }
    .process_button{
      text-align: center;
    }
    .el-dialog__body{
      height: 70%;
      overflow-y: auto;
    }
  }
</style>
