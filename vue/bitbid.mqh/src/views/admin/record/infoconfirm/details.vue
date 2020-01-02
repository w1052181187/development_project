<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="confirm-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/record/infoconfirm' }">招标信息确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/record/infoconfirm/details' }">查看</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="goBack" class="go-back-btn">
        返回
      </el-button>
    </div>

    <!-- 详情表格 start -->
    <div class="table-box">
      <div class="title-box">
        <h2>太原煤炭气化（集团）有限责任公司合同备案表</h2>
      </div>
      <div class="record-info">
        <table class="table-cont">
          <tbody>
            <tr class="fisrt-tr">
              <td colspan="4"><b>项目信息</b></td>
            </tr>
            <tr>
              <td>项目名称</td>
              <td colspan="3">{{bidInfoConfirm.tenderProjectName}}</td>
            </tr>
            <tr>
              <td>项目编号</td>
              <td>{{bidInfoConfirm.codeUser}}</td>
              <td>项目类型</td>
              <td>{{bidInfoConfirm.tenderProjectType | filterType}}</td>
            </tr>
            <tr>
              <td>标段名称</td>
              <td colspan="3">{{bidInfoConfirm.sectionName}}</td>
            </tr>
             <tr>
              <td>项目单位</td>
              <td colspan="3">{{bidInfoConfirm.projectEnterpriseName}}</td>
            </tr>
             <tr>
              <td>需求方</td>
              <td colspan="3">{{bidInfoConfirm.requiresEnterpriseName}}</td>
            </tr>
             <tr>
              <td>招标代理机构</td>
              <td>{{bidInfoConfirm.agentEnterpriseName}}</td>
              <td>公告时间</td>
              <td>{{bidInfoConfirm.noticeTime | filterDate}}</td>
            </tr>
            <tr>
              <td>中标单位</td>
              <td>{{bidInfoConfirm.winnerBidderName}}</td>
              <td>中标金额(万元)</td>
              <td>{{bidInfoConfirm.winPrice}}</td>
            </tr>
            <tr>
              <td>开标时间</td>
              <td>{{bidInfoConfirm.bidOpenTime | filterDate}}</td>
              <td>公示时间</td>
              <td>{{bidInfoConfirm.publicityTime | filterDate}}</td>
            </tr>
          </tbody>
        </table>
        <div class="record-btn">
          <el-button type="primary" @click="submitBtnClick" v-if="bidInfoConfirm.isConfirm === 0">确 认</el-button>
          <el-button type="info" @click="$router.go(-1)" v-if="bidInfoConfirm.isConfirm === 0">取 消</el-button>
        </div>
      </div>
    </div>
    <!-- 详情表格 end -->
  </div>
</template>
<script>
import {bidInfoConfirm} from 'api/index'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      bidInfoConfirm: {}
    }
  },
  computed: {},
  created () {
  },
  /** 方法集 */
  methods: {
    goBack () {
      this.$router.push({path: '/admin/record/infoconfirm', query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
    },
    /** 提交 */
    submitBtnClick () {
      this.$set(this.bidInfoConfirm, 'isConfirm', 1)
      this.$set(this.bidInfoConfirm, 'confirmTime', new Date())
      bidInfoConfirm.update(this.bidInfoConfirm).then(res => {
        this.getById()
      })
    },
    getById () {
      bidInfoConfirm.getById(this.$route.query.objectId).then(res => {
        this.bidInfoConfirm = res.data.bidInfoConfirm
        if (this.bidInfoConfirm) {
          if (this.bidInfoConfirm.tenderProject) {
            let tenderProject = this.bidInfoConfirm.tenderProject
            this.bidInfoConfirm.tenderProjectName = tenderProject.tenderProjectName
            this.bidInfoConfirm.codeUser = tenderProject.codeUser
            this.bidInfoConfirm.tenderProjectType = tenderProject.tenderProjectType
          }
        }
      })
    }
  },
  /** 实例创建完成 */
  mounted () {
    this.getById()
  },
  /** 过滤 */
  filters: {
    filterDate (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    },
    filterType (val) {
      if (val === 1) {
        return '工程'
      } else if (val === 2) {
        return '货物'
      } else if (val === 3) {
        return '服务'
      }
    }
  }
}
</script>
<style lang="less">
#confirm-page {
  .table-box {
    background: #fff;
    margin: 0 20px;
    margin-bottom: 130px;
    padding: 30px 15px;
    .record-info {
      width: 1200px;
      margin: 0 auto;
      .table-cont {
        width: 100%;
        margin-bottom: 30px;
        border-collapse: collapse;
        border: 1px solid #ddd;
        tr, td {
          border: 1px solid #ddd;
        }
        tr {
          height: 40px;
          line-height: 40px;
        }
        td:nth-child(odd) {
          width: 20%;
          background: #eee;
        }
        .fisrt-tr td{
          background: #fff;
        }
        .fisrt-tr b {
          font-size: 18px;
        }
      }
    }
  }
}
</style>
