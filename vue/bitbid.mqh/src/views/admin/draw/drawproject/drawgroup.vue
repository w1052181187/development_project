<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="dgroup-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawproject' }">抽签项目管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawgroup' }">抽签分组</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="dgroup-cont">
      <el-row style="border-bottom: 1px solid #ddd;">
        <div class="draw-group draw-position">
          <el-form :model="ruleGroupForm" :rules="rules" ref="ruleGroupForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="中介机构类型:" prop="type">
              <el-select v-model="ruleGroupForm.type" placeholder="请选择中介机构类型" style="width: 260px;" @change="getProjectInfoList(ruleGroupForm.type)">
                <el-option
                  v-for="item in typeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="抽签项目分组:" prop="group">
              <el-transfer
                v-model="ruleGroupForm.group"
                :props="{
                    key: 'value',
                    label: 'desc'
                  }"
                :titles="['项目名称', '已选择项目组']"
                :data="transferData"
                @change="changeProjectList">
              </el-transfer>
            </el-form-item>
          </el-form>
          <div class="draw-inter">
            <el-form :model="formInline" ref="formInline" class="demo-form-inline">
              <el-form-item label="开始抽签单位:" prop="startEnterpriseId">
                <el-select v-model="formInline.startEnterpriseId" placeholder="抽签单位" style="width: 260px;">
                  <el-option
                    v-for="item in sunitList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <!--<el-form-item label="结束抽签单位:" prop="endEnterpriseId">
                <el-select v-model="formInline.endEnterpriseId" placeholder="抽签单位" style="width: 260px;">
                  <el-option
                    v-for="item in sunitList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>-->
              <el-form-item label="拟 抽 签 时 间:" prop="extractTime">
                <el-date-picker
                  style="width: 258px;"
                  v-model="formInline.extractTime"
                  type="datetime"
                  placeholder="选择日期时间">
                </el-date-picker>
              </el-form-item>
            </el-form>
          </div>
        </div>
        <div class="line-segment draw-position"></div>
        <div class="inter-table draw-position">
          <el-table
            :data="interTableData"
            :row-class-name="tableRowClassName"
            border
            height="600"
            style="width: 100%; overflow-y:auto;">
            <el-table-column
              type="index"
              label="序号"
              align="center"
              width="55"
              :index="indexMethod">
            </el-table-column>
            <el-table-column
              prop="interTableData.name"
              label="中介机构名称"
              align="center">
              <template slot-scope="scope">
                <!-- 未开标的悬浮显示拥有的项目 -->
                <el-tooltip class="item" effect="dark" :content="content" placement="top-start">
                  <span @mouseover="getAgancyTenderProject(scope.row)">{{scope.row.name}}</span>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="是否备选"
              align="center">
              <template slot-scope="scope">
                <el-switch
                  :width="40"
                  v-model="scope.row.status"
                  name="scope.row.name"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  :active-value="1"
                  :inactive-value="0"
                  @change="changeSwitch(scope.row)">
                </el-switch>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-row>
      <div class="inter-save-btn">
        <el-button type="primary" @click="saveClickBtn('formInline')" size="medium">保存</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {drawrecord, drawproject, enterprise} from '../../../../api/index'
// 实例
export default {
  data () {
    return {
      rules: {},
      ruleGroupForm: {},
      formInline: {
        startEnterpriseId: '',
        endEnterpriseId: ''
      },
      transferData: [],
      currentPage: 1,
      interTableData: [],
      typeList: [
        {
          label: '代理机构选聘',
          value: 1
        },
        {
          label: '咨询造价机构',
          value: 2
        },
        {
          label: '审计评估机构',
          value: 3
        },
        {
          label: '法律服务机构',
          value: 4
        }],
      sunitList: [], // 选中项目对应的企业信息
      projectData: [],
      projectTotal: 0,
      optionalEnterprise: [], // 当前分组中所选择的备选企业id信息
      projectGroupCodesInfo: [], // 已选项目的code集合
      selectEnterpriseIds: [], // 当前分组中所选择的备选企业信息
      applyEnterpriseList: [],
      agancyMap: [], // 招标代理机构下未开标项目的数量
      content: '未开标的招标项目有'
    }
  },
  created () {
    this.getCurrentGroupInfo()
    this.getAllEnterpriseList()
  },
  methods: {
    /** 获取选中的代理机构的招标项目信息 */
    getAgancyTenderProject (row) {
      let flag = true
      this.content = '未开标的招标项目有'
      let str = ' '
      if (this.agancyMap.length > 0) {
        for (let i = 0; i < this.agancyMap.length; i++) {
          if (this.agancyMap[i].agancyId === row.id) {
            flag = false
            str = ': ' + this.agancyMap[i].projectCount + '个'
            this.content += str
            break
          }
        }
      }
      if (flag) {
        drawproject.getByEntrustEnterpriseId(row.id).then((res) => {
          this.content = '未开标的招标项目有'
          str = ': ' + res.data.count + '个'
          this.content += str
          let obj = {
            agancyId: row.id,
            projectCount: res.data.count
          }
          this.agancyMap.push(obj)
        })
      }
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 备选开关 */
    changeSwitch (data) {
      let flag = false
      let index
      for (let i = 0; i < this.optionalEnterprise.length; i++) {
        //  对已选中的备选企业做操作
        if (this.optionalEnterprise[i] === data.id) {
          flag = true
          index = i
          break
        }
      }
      if (flag) {
        if (data.status === 0) {
          this.optionalEnterprise.splice(index, 1)
          flag = false
        }
      } else {
        if (data.status === 1) {
          this.optionalEnterprise.push(data.id)
        }
      }
    },
    /** 保存 */
    saveClickBtn (formName) {
      // 判断选中的待抽取中介数量
      if (this.optionalEnterprise.length < 5) {
        this.$confirm('备选的中介机构数量不能少于五家', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {}).catch(() => {
          return false
        })
      } else {
        // 选择的待抽取中介
        this.formInline.selectEnterpriseIds = this.optionalEnterprise
        // 选择的申请表编号集合
        this.formInline.projectGroupCodes = this.projectGroupCodesInfo
        this.formInline.type = this.ruleGroupForm.type
        this.$refs[formName].validate((valid) => {
          if (valid) {
            drawproject.save(this.formInline).then((res) => {
              this.$router.push({path: './drawproject'})
            })
          } else {
            return false
          }
        })
      }
    },
    /** 得到对应中介机构下所有的项目信息 */
    getProjectInfoList (type) {
      this.ruleGroupForm.group = [] // 清空上次的数据
      this.transferData = [] // 清空上次的数据
      this.ruleGroupForm.type = type // 获取当前对应的中介机构类型
      drawrecord.getList({
        pageNo: 1,
        pageSize: 0,
        type: type,
        approveStatus: 5,
        isGrouped: 0
      }).then((res) => {
        this.projectData = res.data.selectApplyList.list
        this.projectTotal = res.data.selectApplyList.total
        if (this.projectData.length > 0) {
          for (let i = 0; i < this.projectData.length; i++) {
            this.transferData.push({
              value: this.projectData[i].objectId,
              desc: this.projectData[i].projectName
            })
          }
        }
      })
    },
    changeProjectList () {
      this.sunitList = [] // 清空已有信息
      this.projectGroupCodesInfo = [] // 清空已有信息
      this.formInline.startEnterpriseId = ''
      this.formInline.endEnterpriseId = ''
      if (this.ruleGroupForm.group.length > 0) {
        let includeIds = ''
        for (let i = 0; i < this.ruleGroupForm.group.length; i++) {
          if (i === this.ruleGroupForm.group.length - 1) {
            includeIds += this.ruleGroupForm.group[i]
          } else {
            includeIds += (this.ruleGroupForm.group[i] + ',')
          }
        }
        drawrecord.getList({
          includeIds: includeIds
        }).then((res) => {
          // 获取项目的code
          res.data.selectApplyList.list.forEach(item => this.projectGroupCodesInfo.push(item.code))
          this.getApplyEnterprise(res.data.selectApplyList.list)
        })
      }
    },
    /** 获取项目对应的企业信息 */
    getApplyEnterprise (selectApplyList) {
      this.sunitList = []
      for (let i = 0; i < selectApplyList.length; i++) {
        if (this.sunitList.length > 0) {
          let flag = false
          for (let j = 0; j < this.sunitList.length; j++) {
            if (this.sunitList[j].value !== selectApplyList[i].enterpriseId && this.sunitList[j].label !== selectApplyList[i].enterpriseName) {
              flag = true
            } else {
              flag = false
              break
            }
          }
          if (flag) {
            this.sunitList.push({
              value: selectApplyList[i].enterpriseId,
              label: selectApplyList[i].enterpriseName
            })
          }
        } else {
          this.sunitList.push({
            value: selectApplyList[i].enterpriseId,
            label: selectApplyList[i].enterpriseName
          })
        }
      }
    },
    /** 获取所有备选机构的信息 */
    getAllEnterpriseList () {
      enterprise.getList({
        pageNo: 1,
        pageSize: 0,
        type: 4
      }).then((res) => {
        // this.interTableData = res.data.enterprises
        if (res.data.enterprises.length > 0) {
          let info = []
          for (let i = 0; i < res.data.enterprises.length; i++) {
            var obj = {}
            // 用于选出备选企业信息
            if (this.selectEnterpriseIds.length > 0) {
              for (let j = 0; j < this.selectEnterpriseIds.length; j++) {
                if (this.selectEnterpriseIds[j].id === res.data.enterprises[i].id) {
                  obj.status = 1
                }
              }
            }
            obj.id = res.data.enterprises[i].id
            obj.name = res.data.enterprises[i].label
            info[i] = obj
          }
          this.interTableData = info
        }
      })
    },
    getCurrentGroupInfo () {
      if (this.$route.query.name === 'edit') {
        drawproject.getOne(this.$route.query.objectId).then((res) => {
          if (res) {
            if (res.data.projectGroup.selectApplyList.length > 0) {
              // 获取当前对应的中介类型中未分组的项目信息
              this.getProjectInfoList(res.data.projectGroup.selectApplyList[0].type)
              // 展示当前对应分组中的项目信息
              let info = []
              let projectGroupCodes = []
              for (let i = 0; i < res.data.projectGroup.selectApplyList.length; i++) {
                info[i] = res.data.projectGroup.selectApplyList[i].objectId
                projectGroupCodes[i] = res.data.projectGroup.selectApplyList[i].code
                // 编辑操作时将已选择的项目信息进行展示
                this.transferData.push({
                  value: res.data.projectGroup.selectApplyList[i].objectId,
                  desc: res.data.projectGroup.selectApplyList[i].projectName
                })
              }
              this.ruleGroupForm.group = info
              this.projectGroupCodesInfo = projectGroupCodes
              // 获取当前指定的开始抽签单位和结束抽签单位
              this.formInline = res.data.projectGroup
              this.getApplyEnterprise(res.data.projectGroup.selectApplyList)
              // 获取备选机构的信息
              let enterpriseIds = []
              let optionalEnterpriseIds = []
              for (let i = 0; i < res.data.projectGroup.selectEnterpriseIds.length; i++) {
                let obj = {}
                optionalEnterpriseIds[i] = res.data.projectGroup.selectEnterpriseIds[i]
                obj.id = res.data.projectGroup.selectEnterpriseIds[i]
                obj.status = 1
                enterpriseIds.push(obj)
              }
              this.selectEnterpriseIds = enterpriseIds
              this.optionalEnterprise = optionalEnterpriseIds
              this.getAllEnterpriseList()
            }
          } else {
            return false
          }
        })
      }
    }
  },
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
#dgroup-page {
  .dgroup-cont {
    margin: 20px 20px 130px;
    background-color: #fff;
    .draw-group{
      width: 65%;
      background-color: #fff;
      .demo-ruleForm {
        padding: 20px;
      }
    }
    .inter-table {
      width: 28%;
      padding: 20px 10px;
    }
    .draw-inter {
      border-top: 1px solid #ddd;
      text-align: left;
      .demo-form-inline {
        padding: 20px;
      }
    }
    .line-segment {
      width: 3%;
      height: 640px;
      border-left: 1px solid #ddd;
    }
    .draw-position {
      float: left;
    }
  }
  .el-form-item__content {
    text-align: left;
  }
  .inter-save-btn {
    text-align: center;
    padding: 20px;
  }
  // 穿梭样式
  .el-transfer {
    text-align: left;
    .el-transfer-panel {
      width: 260px;
    }
  }

}
</style>
