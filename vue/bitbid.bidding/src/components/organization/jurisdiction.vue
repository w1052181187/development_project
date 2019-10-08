<template>
  <div id="homejuris">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/organ/department' }">组织结构</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/organ/role' }">角色管理</el-breadcrumb-item>
          <el-breadcrumb-item>分配权限</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="penservicecontitle">分配权限</div>
          <div class="penserviceconconbox">
            <!--模块内容-->
            <div class="typebigbox">
              <el-row class="tac">
                <el-col :span="24">
                  <el-menu
                    :default-active="activeIndex"
                    class="el-menu-vertical-demo typebox">
                    <el-menu-item :index="activeIndex+''" @click="changeModule(management.objectId,activeIndex)" v-for="(management,activeIndex) in managementList" :key="activeIndex">
                      <span slot="title">{{management.serviceName}}</span>
                    </el-menu-item>
                  </el-menu>
                </el-col>
              </el-row>
            </div>
            <!--模块内容-->
            <publicjuris :moduleId="clickObjectid" @changeResultDepIds="changeResultDepIds" @changeResultPerIds="changeResultPerIds" @clearModulePerAndDepIds="clearModulePerAndDepIds"
            :treeNodes="moduleDepTreeDatas" :permissionIds="modulePermissions"></publicjuris>
            <!--按钮-->
            <div class="jursdbtn">
              <el-button @click="submitData" type="primary">保 存</el-button>
              <el-button @click="cancel">取 消</el-button>
            </div>
            <!--按钮-->
          </div>
        </div>
        <!--内容开始-->
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
import publicjuris from '../organization/public-juris.vue'
import {rolead} from '../../api/index'

export default {
  components: {
    publicside,
    publicjuris
  },
  data () {
    return {
      activeIndex: '0',
      // 操作权限多选
      clickObjectid: '',
      isIndeterminate: true,
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      managementList: [],
      limitsJurs: [],
      flag: false,
      // 角色权限分配中的启用禁用参数 userId
      userIds: this.$store.getters.authUser.userId,
      // 定义启用禁用初始状态
      dishadeStaus: true,
      dishadnames: '启用',
      roleId: '',
      result: {},
      // 是否填写数据
      fillInNumber: 0,
      // 是否选择权限
      fillInJurisdiction: 0
    }
  },
  computed: {
    moduleDepTreeDatas () {
      this.result[this.clickObjectid] = this.result[this.clickObjectid] || {}
      return this.result[this.clickObjectid]['depIds'] || []
    },
    modulePermissions () {
      this.result[this.clickObjectid] = this.result[this.clickObjectid] || {}
      return this.result[this.clickObjectid]['permissionIds'] || []
    }
  },
  methods: {
    changeModule (moduleId, activeIndex) {
      this.activeIndex = activeIndex + ''
      this.clickObjectid = moduleId
    },
    changeResultDepIds (evtValue, moduleId) {
      this.result[moduleId] = this.result[moduleId] || {}
      this.result[moduleId]['depIds'] = evtValue
      if (evtValue.length != 0) {
        this.fillInNumber = 0
      }
    },
    changeResultPerIds (evtValue, moduleId) {
      this.result[moduleId] = this.result[moduleId] || {}
      this.result[moduleId]['permissionIds'] = evtValue
      if (evtValue.length > 0) {
        this.fillInJurisdiction = 0
      }
    },
    clearModulePerAndDepIds (moduleId) {
      this.result[moduleId] = this.result[moduleId] || {}
      this.result[moduleId]['permissionIds'] = []
      this.result[moduleId]['depIds'] = []
    },
    submitData () {
      this.$confirm('确认保存吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        let resultMap = this.result
        let resultRoleId = this.roleId
        let resultData = []
        for (var key in resultMap) {
          if (resultMap[key].permissionIds.length) {
            let jurisdiction = resultMap[key].depIds.length ? resultMap[key].depIds.reduce((a, b) => { return a + ',' + b }) : ''
            resultMap[key].permissionIds.forEach(element => {
              if (element) {
                let acrObject = {
                  roleId: resultRoleId,
                  jurisdiction: jurisdiction,
                  moduleMark: element,
                  moduleId: key
                }
                resultData.push(acrObject)
              }
            })
          }
        }
        // 权限判断
        if (resultData.length === 0) {
          this.fillInJurisdiction = 1
        }
        if (this.fillInJurisdiction === 1) {
          this.$message({
            type: 'warning',
            message: '权限不能为空'
          })
          return false
        }
        if (!resultData.length) {
          resultData[0] = {'roleId': resultRoleId}
        }
        if (this.clickObjectid === 10001 || this.clickObjectid === 10005) {
          // 数据范围判断
          resultData.map((ite) => {
            if (ite.jurisdiction === '' && ite.moduleId === '10001') {
              this.fillInNumber = 1
            }
            if (ite.jurisdiction === '' && ite.moduleId === '10005') {
              this.fillInNumber = 1
            }
          })
          if (this.fillInNumber === 1) {
            this.$message({
              type: 'warning',
              message: '数据范围不能为空'
            })
            return false
          }
        }
       rolead.addjuris(resultData).then((res) => {
         this.$router.push({path: '/organ/role'})
       })
      }).catch(() => {
        return false
      })
    },
    // 取消
    cancel () {
      this.$router.push({path: '/organ/role'})
    },
    // 初始化列表展示
    getData () {
      this.roleId = this.$route.params.objectId
      this.$nextTick(function () {
        rolead.jurisList({
          roleId: this.roleId,
          isDelete: 0,
          enterpriseId: this.$store.getters.authUser.enterpriseId,
          _t: new Date().getTime()
        }).then((res) => {
          // 侧边列表数据
          this.managementList = res.data.managementList
          // 已经选中的数据
          this.result = res.data.resultMap
          // 第一个选择的模块
          this.clickObjectid = res.data.firstModuleId
          this.changeModule(this.clickObjectid, 0)
        })
      })
    }
  },
  mounted () {
    this.getData()
  }
}
</script>
<style lang="less">
  #homejuris{
    .typebigbox{
      display: inline-block;
      width: 150px;
      border: 1px solid #f4f4f4;
      vertical-align: top;
    }
    .typebox{
      text-align: center;
    }
    .operation_jurisdiction{
      display: inline-block;
      vertical-align: top;
      margin-left: 20px;
      border: 1px solid #eeeeee;
      width: 450px;
    }
    .operation_juristitle{
      height: 57px;
      background: #f5f7f8;
      font-size: 14px;
      line-height: 57px;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
      color:#333333;
    }
    .operation_juriscon{
      padding:20px;
    }
    .datarange_bigbox{
      display: inline-block;
      vertical-align: top;
      margin-left: 20px;
      border: 1px solid #eeeeee;
      width: 300px;
    }
    .operation_datarangetitle{
      min-width:300px;
      height: 57px;
      background: #f5f7f8;
      font-size: 14px;
      line-height: 57px;
      padding: 0 20px;
      border-bottom: 1px solid #eeeeee;
      box-sizing: border-box;
      color:#333333;
    }
    .operation_datarangecon{
      padding:20px;
    }
    .jursdbtn{
      text-align: center;
      margin-top: 40px;
    }
    .el-button--primary {
      color: #fff;
      background-color: #3f63f6;
      border-color: #3f63f6;
    }
    .disableenable{
      display: inline-block;
      vertical-align: top;
      float: right;
      cursor: pointer;
    }
    .el-tree-node__label {
      font-size: 14px;
      display: inline-block;
      width: 240px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .el-checkbox{
      margin-left: 0;
      margin-right: 30px;
    }
    .checkedbox{
      margin-top: 10px;
    }
    .el-menu-item.is-active {
      background: #3f63f6 !important;
      border-left: 0px solid transparent;
    }
  }
</style>
