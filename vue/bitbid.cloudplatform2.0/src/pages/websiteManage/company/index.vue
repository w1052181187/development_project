<template>
  <div class="cloudcontent" id="cloud_webconpany">
    <div class="main">
      <div class="part">
        <h3><span>公司概况</span></h3>
        <el-table
          :data="tableData"
          border
          style="width: 100%" header-cell-class-name="tableheader">
          <el-table-column
            type="index"
            label="序号"
            width="60"
            align="center">
          </el-table-column>
          <el-table-column
            prop="type"
            label="类型"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span v-if="scope.row.type === '1' ">公司简介</span>
              <span v-if="scope.row.type === '2' ">团队介绍</span>
              <span v-if="scope.row.type === '3' ">资质荣誉</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="tenderProjectName"
            label="发布时间"
            width="200"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作" align="center" width="200">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="updateBtn(scope)">修改</el-button>
              <el-button type="text" size="small" @click="lookBtn(scope)">查看</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="part">
        <h3><span>联系方式</span></h3>
        <el-table
          :data="contactTableData"
          border
          style="width: 100%" header-cell-class-name="tableheader">
          <el-table-column
            type="index"
            label="序号"
            width="60"
            align="center">
          </el-table-column>
          <el-table-column
            prop="type"
            label="类型"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span v-if="scope.row.type === '4' ">公司地址</span>
              <span v-if="scope.row.type === '5' ">联系方式</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="tenderProjectName"
            label="发布时间"
            width="200"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="操作" align="center" width="200">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="updateBtn(scope)">修改</el-button>
              <el-button type="text" size="small" @click="lookBtn(scope)">查看</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  components: {
  },
  data () {
    return {
      tableData: [
        {
          type: '1',
          time: '2019-1-2'
        },
        {
          type: '2',
          time: '2019-1-2'
        },
        {
          type: '3',
          time: '2019-1-2'
        }
      ],
      contactTableData: [
        {
          type: '4',
          time: '2019-1-2'
        },
        {
          type: '5',
          time: '2019-1-2'
        }
      ]
    }
  },
  methods: {
    //  修改
    updateBtn (scope) {
      if (scope.row.type === '1') {
        this.$router.push({path: `/websiteManage/company/updateProfile`})
      } else if (scope.row.type === '2') {
        this.$router.push({path: `/websiteManage/company/updateTeam`})
      } else if (scope.row.type === '3') {
        this.$router.push({path: `/websiteManage/company/updateHonor`})
      } else if (scope.row.type === '4') {
        this.$router.push({path: `/websiteManage/company/updateAddress`})
      } else {
        this.$router.push({path: `/websiteManage/company/updateContact`})
      }
    },
    // 查看
    lookBtn (scope) {
      if (scope.row.type === '1') {
        this.$router.push({path: `/websiteManage/company/detailProfile`})
      } else if (scope.row.type === '2') {
        this.$router.push({path: `/websiteManage/company/detailTeam`})
      } else if (scope.row.type === '3') {
        this.$router.push({path: `/websiteManage/company/detailHonor`})
      } else if (scope.row.type === '4') {
        this.$router.push({path: `/websiteManage/company/detailAddress`})
      } else {
        this.$router.push({path: `/websiteManage/company/detailContact`})
      }
    }
  }
}
</script>
<style lang="less">
  #cloud_webconpany {
    .main{
      margin-top: 0px;
    }
    .part{
      overflow: hidden;
      margin: 12px auto;
    }
    .part h3{
      height: 36px;
      line-height: 36px;
      color: #111111;
      font-size: 14px;
      border-bottom: 1px solid #dcdfe6;
    }
    .part h3 span{
      margin-top: 18px;
      display: inline-block;
      background: #ffffff;
      padding: 0 10px;
      float: left;
    }
    .el-table{
      margin-top: 30px;
    }
  }
</style>
