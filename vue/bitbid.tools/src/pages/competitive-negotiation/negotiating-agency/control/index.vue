<template>
  <div id="control_box" class="content_bgibox">
    <!--项目信息-->
    <commonProject></commonProject>
    <!--项目信息-->
    <!--组建资审小组-->
    <div class="setup_bigbox" style="padding-bottom: 70px">
      <div>
        <span class="line_bigbox"></span>
        <span class="title_bigbox">组建谈判小组</span>
      </div>
      <!--谈判组数据-->
      <div class="tableData_box">
        <template>
          <el-table
            :data="tableDataExpert"
            border
            style="width: 100%">
            <el-table-column
              type="index"
              label="序号"
              width="80"
              align="center"
              :index='dataIndex'>
            </el-table-column>
            <el-table-column
              prop="userName"
              label="谈判流程"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="userName"
              label="状态"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              label="操作"
              width="100"
              align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="editBtn(scope)">回退</el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </div>
      <!--谈判组数据-->
    </div>
  </div>
</template>
<script>
import commonProject from '@/components/common/competitive-negotiation/project'
export default {
  components: {
    commonProject
  },
  data () {
    return {
      pageSize: 1000, // 每页展示条数
      pageNo: 0,
      // 自定义序号
      dataIndex: 1,
      tableDataExpert: []
    }
  },
  methods: {},
  mounted () {}
}
</script>
<style lang="less">
  #control_box{
    margin-top: 40px;
    .tableData_box{
      width: 60%;
      margin-top: 20px;
    }
  }
</style>
