<template>
  <div id="homeopenservice">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
          <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>开通服务</el-breadcrumb-item>
          </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="penservicecontitle">开通服务</div>
          <div class="penserviceconconbox">
            <template>
              <el-table
                :data="refMouEntList"
                border
                style="width:100%" header-cell-class-name="tableheader">
                <el-table-column
                  type="index"
                  :index="1"
                  label="序号"
                  width="60" align="center">
                </el-table-column>
                <el-table-column
                  prop="serviceName"
                  label="服务名称"
                  width="150">
                </el-table-column>
                <el-table-column
                  prop="serviceDescription"
                  label="服务简介"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="serviceStatus"
                  label="状态"
                  width="120">
                  <template slot-scope="scope">
                    {{scope.row.serviceStatus==null?'未开通':scope.row.serviceEnabledStatus=='0'?'启用中':'已开通'}}
                  </template>
                </el-table-column>
                <el-table-column
                  prop="serviceEndDate"
                  label="服务截止时间"
                  width="180">
                </el-table-column>
                <el-table-column
                  label="操作"
                  width="180" align="center">
                  <template slot-scope="scope">
                    <el-button type="danger" size="small" v-if="scope.row.isChecked === 1 && scope.row.serviceEnabledStatus === 1 || scope.row.serviceEnabledStatus === 0" @click="renewBtn">续费</el-button>
                    <el-button type="success" v-if="scope.row.isChecked === null"  @click="Openup">立即开通</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </div>
        </div>
        <!--内容开始-->
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
import {opens} from '../../api/index'

export default {
  components: {
    publicside
  },
  data () {
    return {
      refMouEntList: []
    }
  },
  methods: {
    openServiceList () {
      let url = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        isDelete: 0
      }
      opens.openList(url).then((res) => {
        this.refMouEntList = res.data.managementList
        this.refMouEntList.map((item) => {
          if (item.serviceEndDate === null) {
            item.serviceEndDate = '--------------------------'
          }
        })
      })
    },
    Openup () {
      this.$router.push({path: '/notopen'})
    },
    renewBtn () {
      this.$router.push({path: '/renewal'})
    }
  },
  mounted () {
    this.openServiceList()
  }
}
</script>
<style lang="less">
  #homeopenservice{
    .el-table .cell {
      height: auto!important;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .el-button {
      padding: 5px 10px;
      border-radius: 0;
      margin-left: 0;
    }
    .el-button--primary {
      color: #fff;
      background-color: #3f63f6;
      border-color: #3f63f6;
      width: 80px;
      font-size: 12px;
    }
    .el-button--danger {
      color: #fff;
      background-color: #ff8801;
      border-color: #ff8801;
      width: 80px;
      font-size: 12px;
    }
  }
</style>
