<template>
  <div id="checkbid" class="checkbid" v-loading="loading">
    <div class="bidtop">
      <el-button class="uploadbtn" type="primary" @click="exportExamine">导出检查结果</el-button>
    </div>
    <div class="tab">
      <el-tabs v-model="activeName2" type="card" @tab-click="handleClick">
        <el-tab-pane label="检查结果汇总" name="first">
          <p>提示：请根据检查结果及时修改不合格项，修改后需重新导入招标数据。</p>
          <el-table
            :data="tableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              type="index"
              width="50"
              label="序号"
              align="center">
            </el-table-column>
            <el-table-column
              prop="checktype"
              label="检查内容"
              align="left">
            </el-table-column>
            <el-table-column
              prop="checknum"
              label="问题数量"
              align="left"
              width="200">
            </el-table-column>
            <el-table-column
              prop="checkresult"
              label="结论"
              align="left"
              width="200">
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="工程量检查" name="second">
          <p>检查规则：检查清单的工程量是否有为0、为空或者为负数的情况。</p>
          <el-table
            :data="tab2tableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="singleName"
              label="单项工程"
              align="left">
            </el-table-column>
            <el-table-column
              prop="unitName"
              label="单位工程"
              align="left">
            </el-table-column>
            <el-table-column
              prop="address"
              label="位置"
              align="left">
            </el-table-column>
            <el-table-column
              prop="code"
              label="清单编码"
              align="left">
            </el-table-column>
            <el-table-column
              prop="name"
              label="清单名称"
              align="left">
            </el-table-column>
            <el-table-column
              prop="unit"
              label="清单单位"
              align="left">
            </el-table-column>
            <el-table-column
              prop="workAmount"
              label="清单工程量"
              align="left">
            </el-table-column>
            <el-table-column
              prop="content"
              label="问题描述"
              align="left">
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="招标清单检查" name="third">
          <p>检查规则：检查分部分项清单、单价措施项目清单和总价措施项目名称、编码和单位是否为空、有无编码重复的情况。</p>
          <el-table
            :data="tab3tableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="singleName"
              label="单项工程"
              align="left">
            </el-table-column>
            <el-table-column
              prop="unitName"
              label="单位工程"
              align="left">
            </el-table-column>
            <el-table-column
              prop="address"
              label="位置"
              align="left">
            </el-table-column>
            <el-table-column
              prop="listCode"
              label="清单编码"
              align="left">
            </el-table-column>
            <el-table-column
              prop="listName"
              label="清单名称"
              align="left">
            </el-table-column>
            <el-table-column
              prop="message"
              label="问题描述"
              align="left">
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import { billPushData } from '@/api'
export default {
  props: ['projectId'],
  data () {
    return {
      activeName2: 'first',
      tableData: [],
      tab2tableData: [],
      tab3tableData: [],
      loading: true
    }
  },
  methods: {
    handleClick (tab, event) {
    },
    // 检查数据
    _getTableData () {
      billPushData.check(this.projectId).then((res) => {
        let flag = true
        if (res.data.errors['14']) {
          this.tab2tableData = res.data.errors['14'].errorList
          let obj = {checktype: '工程量检查'}
          obj.checknum = res.data.errors['14'].errorList.length
          if (obj.checknum > 0) {
            obj.checkresult = '不合格'
            if (flag) {
              flag = false
            }
          } else {
            obj.checkresult = '合格'
          }
          this.tableData.push(obj)
        }
        if (res.data.errors['1']) {
          this.tab3tableData = res.data.errors['1'].errorList
          let obj = {checktype: '招标清单检查'}
          obj.checknum = res.data.errors['1'].errorList.length
          if (obj.checknum > 0) {
            obj.checkresult = '不合格'
            if (flag) {
              flag = false
            }
          } else {
            obj.checkresult = '合格'
          }
          this.tableData.push(obj)
          this.loading = false
        }
        if (flag) {
          this.$emit('IsNext', true)
        } else {
          this.$emit('IsNext', false)
        }
      })
    },
    // 导出检查结果
    exportExamine () {
      let comurl = process.env.COMPONENTS_BASE_API
      if (this.$route.query.projectId) {
        let url = comurl + 'comptend/project/download/examine/' + this.$route.query.projectId + '/' + this.$route.query.type
        window.open(url)
      } else {
        let url = comurl + 'comptend/project/download/examine/' + this.projectId + '/' + this.$route.query.type
        window.open(url)
      }
    }
  },
  mounted () {
    this._getTableData()
  }
}
</script>
<style lang="less">
  #checkbid{
    width: 98%;
    padding: 0 1%;
    overflow: hidden;
    margin: 30px auto;
    .bidtop{
      width: 100%;
      margin: 0 auto;
      height: 62px;
      line-height: 62px;
    }
    .bidtop .el-button--primary{
      margin-top: 11px;
      float: right;
      background-color: #00aaff;
      border-color: #00aaff;
    }
    .tab .el-tabs__header{
      margin: 0 0 30px;
    }
    .tab .el-tabs__nav-scroll{
      padding-top: 10px;
      background: #ebeffe;
    }
    .tab .el-tabs--card>.el-tabs__header .el-tabs__nav{
      border: 0px;
      margin-left: 10px;
    }
    .tab .el-tabs--card>.el-tabs__header .el-tabs__item.is-active{
      background: #ffffff;
    }
    #pane-first p{
      color: #fe4848;
      font-size: 12px;
      line-height: 26px;
    }
    #pane-second p,#pane-third p,#pane-fourth p{
      color: #111111;
      font-size: 12px;
      line-height: 26px;
    }
    .tab .el-table{
      margin-bottom: 30px;
    }
  }
</style>
