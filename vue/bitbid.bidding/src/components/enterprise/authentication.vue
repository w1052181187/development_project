<template>
  <div id="autrtic">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>企业认证</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="penservicecontitle">企业认证</div>
          <div class="authenbigbox">
            <el-table
              :data="tableData"
              border
              style="width: 100%" :cell-style="cellStyle">
              <el-table-column
                prop="enterpriseName"
                label="名称"
                align="center"
                width="140">
              </el-table-column>
              <el-table-column
                prop="province"
                label="认证主体"
                align="center">
                <template slot-scope="scope">
                  <span class="jfbdbox" v-for="(item,index) in scope.row.province" :key="index">
                    <span class="statesbox_uncertified" v-show="item.state == 0"></span>
                    <span class="statesbox_lan" v-show="item.state == 1"></span>
                    <span class="statesbox_lvbtn" v-show="item.state == 2"></span>
                    <span class="statesbox_red" v-show="item.state == 3"></span>
                    <span class="xmbh_box" :title="item.subjectName">{{item.subjectName}}</span>
                  </span>
                </template>
              </el-table-column>
              <el-table-column
                prop="status"
                label="状态"
                width="120"
                align="center">
                <template slot-scope="scope">
                  <div class="statusbigbox" v-if="scope.row.status === 0">
                    <span class="statusbox">未认证</span>
                  </div>
                  <div class="statusbigbox" v-if="scope.row.status === 1">
                    <span class="statusbox">已提交</span>
                  </div>
                  <div class="statusbigbox" v-if="scope.row.status === 2">
                    <span class="statusbox">审核通过</span>
                  </div>
                  <div class="statusbigbox" v-if="scope.row.status === 3">
                    <span class="statusbox">审核不通过</span>
                  </div>
              </template>
              </el-table-column>
              <el-table-column
                fixed="right"
                label="操作"
                width="160"
                align="center">
                <template slot-scope="scope">
                  <div class="statusbigbox" v-if="scope.row.status === 0">
                    <el-button type="text" size="small" @click="immediatelyBtn(1)">立即认证</el-button>
                  </div>
                  <div class="statusbigbox" v-if="scope.row.status === 1">
                    <el-button type="text" size="small" @click="Lookdetails">查看详情</el-button>
                  </div>
                  <div class="statusbigbox" v-if="scope.row.status === 2">
                    <el-button type="text" size="small" @click="immediatelyBtn(2)">重新认证</el-button>
                    <el-button type="text" size="small" @click="Lookdetails">查看详情</el-button>
                  </div>
                  <div class="statusbigbox" v-if="scope.row.status === 3">
                    <el-button type="text" size="small" @click="immediatelyBtn(2)">重新认证</el-button>
                    <el-button type="text" size="small" @click="Lookdetails">查看详情</el-button>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
        <!--内容开始-->
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
import {enterprise} from '../../api/index'
export default {
  components: {
    publicside
  },
  data () {
    return {
      tableData: []
    }
  },
  methods: {
    enterpriseSubjectData () {
      let url = {
        isDelete: 0,
        userId: this.$store.getters.authUser.userId
      }
      enterprise.subjectList(url).then((item) => {
        this.tableData = item.data.item
      })
    },
    immediatelyBtn (obj) {
      this.$confirm('确认要去认证吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 第一次认证
        if (obj === 1) {
          this.$router.push({name: 'subject'})
        } else {
          // 重新认证
          let subjectType = ''
          this.tableData[0].province.map(item => {
            if (item.state == 3 || item.state == 2) {
              subjectType += (item.objectId + ',')
            }
          })
          // 去除末尾逗号
          let str = subjectType.substring(0, subjectType.length - 1)
          this.$router.push({name: 'subject', query: {subjectForm: str, flangshow: true, approveState: true}})
        }
      }).catch(() => {
        return false
      })
    },
    // 查看详情
    Lookdetails () {
      this.$router.push({name: 'authen-inform', params: {LookStatus: true}})
    },
    cellStyle ({row, column, rowIndex, columnIndex}) {
      if (columnIndex === 1) {
        return 'text-align: left'
      } else {
        return ''
      }
    }
  },
  mounted () {
    this.enterpriseSubjectData()
  }
}
</script>
<style lang="less">
  #autrtic{
    .authenbigbox{
      width: 100%;
      min-height: 50px;
      margin-top: 40px;
      box-sizing: border-box;
    }
    /*新写样式 9.12*/
    .jfbdbox{
      display:block;
      width: 33.33%;
      height: 35px;
      float: left;
      line-height: 35px;
      text-align: left;
      border-bottom: 1px solid #eee;
    }
    .el-table__body tr.hover-row>td{
      background:transparent!important;
    }
    .statusbigbox{
      width: 100%;
      height: 100%;
      text-align: center;
    }
    .statusbigbox button {
      float: inherit;
      margin-left: 10px;
    }
    .el-table .cell {
      height: auto!important;
      line-height: 23px;
      overflow: hidden;
      word-wrap: break-word
    }
  }
</style>
