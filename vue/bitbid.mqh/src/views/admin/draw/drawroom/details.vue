<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="droom-details-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawroom' }">抽签</el-breadcrumb-item>
        <el-breadcrumb-item>详情页</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="returnPreviousPage" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="dm-tetails-cont">
      <!-- 抽签项目信息start -->
      <div class="dproject-info">
        <div class="cont-tit">
          <span>抽签项目信息</span>
        </div>
        <div class="dpro-table">
          <el-table
            :data="projectTableData"
            border
            style="width: 100%">
            <el-table-column
              width="55"
              type="index"
              align="center"
              label="序号"
              :index="indexMethod">
            </el-table-column>
            <el-table-column
              align="center"
              prop="projectName"
              label="项目名称">
            </el-table-column>
            <el-table-column
              align="center"
              prop="enterpriseName"
              label="招标方">
            </el-table-column>
          </el-table>
        </div>
      </div>
      <!-- 抽签项目信息end -->

      <!-- 抽签信息start -->
      <div class="draw-info">
        <div class="cont-tit">
          <span>抽签信息</span>
        </div>
        <div class="draw-info-cont">
          <el-form ref="form" :model="drawInfo" label-width="130px">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="开始抽签业主:">
                  <span>{{drawInfo.startEnterpriseName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="拟抽签时间:">
                  <span>{{drawInfo.extractTime | filterDate}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="抽签机构类型:">
                  <span>{{drawInfo.type | filterType}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <!--<el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="开始抽签业主:">
                  <span>{{drawInfo.startEnterpriseName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="结束抽签业主:">
                  <span>{{drawInfo.endEnterpriseName}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="拟抽签时间:">
                  <span>{{drawInfo.extractTime | filterDate}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="抽签机构类型:">
                  <span>{{drawInfo.type | filterType}}</span>
                </el-form-item>
              </el-col>
            </el-row>-->
          </el-form>
        </div>
      </div>
      <!-- 抽签信息end -->

      <!-- 选聘中介机构start -->
      <div class="drawinter-info">
        <div class="cont-tit">
          <span>备选中介机构</span>
        </div>
        <div class="dpro-table">
          <el-table
            :data="enterpriseTableData"
            border
            style="width: 100%">
            <el-table-column
              width="55"
              type="index"
              label="序号"
              :index="indexMethod">
            </el-table-column>
            <el-table-column
              align="left"
              prop="name"
              label="机构名称">
            </el-table-column>
          </el-table>
        </div>
      </div>
      <!-- 选聘中介机构end -->
    </div>
  </div>
</template>
<script>
import {drawproject, enterprise} from '../../../../api/index'
import {formatDate} from '../../../../common/js/date.js'
// 实例
export default {
  data () {
    return {
      projectTableData: [],
      enterpriseTableData: [],
      drawInfo: {},
      pageNo: 1,
      selectEnterpriseIds: []
    }
  },
  created () {
  },
  computed: {
    getSelectEnterpriseIds () {
      return this.selectEnterpriseIds
    }
  },
  watch: {
    getSelectEnterpriseIds () {
      this.getIsOptionedEnterprise(this.selectEnterpriseIds)
    }
  },
  filters: {
    /** 格式化申请时间 */
    filterDate (extractTime) {
      if (extractTime) {
        let date = new Date(extractTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    /** 中介机构类型的选择 */
    filterType (type) {
      if (type === 1) {
        return '代理机构选聘'
      } else if (type === 2) {
        return '咨询造价机构'
      } else if (type === 3) {
        return '审计评估机构'
      } else if (type === 4) {
        return '法律服务机构'
      }
    }
  },
  methods: {
    returnPreviousPage () {
      if (!this.$route.query.btn) {
        this.$router.push({path: '/draw/drawproject', query: {activeName: this.$route.query.activeName}})
      } else {
        this.$router.push({path: '/draw/drawroom'})
      }
    },
    indexMethod (index) {
      return index + (this.pageNo - 1) * 10 + 1
    },
    getCurrentGroupInfo () {
      if (this.$route.query.name === 'details') {
        drawproject.getOne(this.$route.query.objectId).then((res) => {
          // 抽签项目信息
          this.projectTableData = res.data.projectGroup.selectApplyList
          this.selectEnterpriseIds = res.data.projectGroup.selectEnterpriseIds
          // this.getIsOptionedEnterprise(res.data.projectGroup.selectEnterpriseIds)
          // 抽签信息
          this.drawInfo = res.data.projectGroup
        })
      }
    },
    /** 得到已经选择的备选机构的名称 */
    getIsOptionedEnterprise (selectEnterpriseIds) {
      let includeIds = ''
      this.enterpriseTableData = []
      let enterpriseNameList = []
      if (selectEnterpriseIds.length > 0) {
        for (let i = 0; i < selectEnterpriseIds.length; i++) {
          if (i === selectEnterpriseIds.length - 1) {
            includeIds += selectEnterpriseIds[i]
          } else {
            includeIds += (selectEnterpriseIds[i] + ',')
          }
        }
      }
      enterprise.getAll({
        includeIds: includeIds
      }).then((res) => {
        if (res.data.enterprises.length > 0) {
          for (let j = 0; j < res.data.enterprises.length; j++) {
            let obj = {}
            obj.name = res.data.enterprises[j].name
            enterpriseNameList.push(obj)
          }
          this.enterpriseTableData = enterpriseNameList
        }
      })
    }
  },
  // 实例创建完成
  mounted () {
    this.getCurrentGroupInfo()
  }
}
</script>
<style lang="less">
#droom-details-page {
  /*详情页内容样式*/
  .dm-tetails-cont {
    margin: 20px 20px 130px;
    background-color: #EAEDF2;
    .dproject-info {
      background-color: #fff;
      min-height: 300px;
    }
    .draw-info {
      margin-top: 20px;
      background-color: #fff;
      height: 100px;
      .draw-info-cont {
        padding: 10px;
      }
    }
    .drawinter-info {
      margin-top: 20px;
      background-color: #fff;
      min-height: 300px;
    }
  }
  .cont-tit {
    text-align: left;
    background: #F7F6FB;
    font-size: 14px;
    font-weight: bold;
    padding: 10px 20px;
  }
  .el-form-item__content {
    text-align: left;
  }
  .dpro-table {
    padding: 20px;
  }
}
</style>
