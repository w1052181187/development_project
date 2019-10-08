<template>
  <div class="cloudcontent" id="cloud_webconpany">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">公司概况</el-breadcrumb-item>
    </el-breadcrumb>
    <!--面包屑-->
    <div class="main">
      <el-table
        :data="tableData"
        border
        style="width: 100%" header-cell-class-name="tableheader">
        <el-table-column
          type="index"
          label="序号"
          width="60"
          :index='dataIndex'
          align="center">
        </el-table-column>
        <el-table-column
          prop="type"
          label="类型"
          show-overflow-tooltip>
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
</template>
<script>
export default {
  components: {
  },
  data () {
    return {
      tableData: [
        {
          type: '公司简介',
          time: '2019-1-2'
        },
        {
          type: '团队介绍',
          time: '2019-1-2'
        },
        {
          type: '资质荣誉',
          time: '2019-1-2'
        }
      ]
    }
  },
  methods: {
    //  修改
    updateBtn () {},
    // 查看
    lookBtn () {}
  }
}
</script>
<style lang="less">
  #cloud_webconpany {
  }
</style>
