<template>
  <div id="checkunbid" class="checkunbid" v-loading="loading">
    <div class="bidtop">
      <el-button class="uploadbtn" type="primary" @click="exportExamine">导出检查结果</el-button>
    </div>
    <div class="tab">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="检查结果汇总" name="first">
          <p v-if="Number(this.$route.query.type) === 2">提示：请根据检查结果及时修改不合格项，修改后需重新导入投标数据。<span class="totalprice">投标总价：{{allTotalPrice}}元</span></p>
          <p v-if="Number(this.$route.query.type) === 3">提示：请根据检查结果及时修改不合格项，修改后需重新导入招标控制价数据。<span class="totalprice">招标控制价总价：{{allTotalPrice}}元</span></p>
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
              width="100">
            </el-table-column>
            <el-table-column
              prop="checkresult"
              label="结论"
              align="left"
              width="300">
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="清单单价遗漏" name="second">
          <p>检查规则：检查分部分项清单及措施项目单价、合价是否有报价为0或负数的情况。</p>
          <el-table
            :data="tab2tableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="singleName"
              label="单项工程"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="unitName"
              label="单位工程"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="address"
              label="位置"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="code"
              label="清单编码"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="name"
              label="清单名称"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="unit"
              label="单位"
              align="left">
            </el-table-column>
            <el-table-column
              prop="workAmount"
              label="工程量"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="price"
              label="单价"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="totalPrice"
              label="合价"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="清单单价算术性" name="third">
          <p>检查规则：检查分部分项清单单价与合价是否有计算错误的情况。</p>
          <el-table
            :data="tab3tableData"
            border
            header-cell-class-name="tabletitles"
            :show-overflow-tooltip="true">
            <el-table-column
              prop="singleName"
              label="单项工程"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="unitName"
              label="单位工程"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="address"
              label="位置"
              align="left"
              width="80"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="code"
              label="清单编码"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="name"
              label="清单名称"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="unit"
              label="单位"
              align="left"
              width="60">
            </el-table-column>
            <el-table-column
              prop="workAmount"
              label="工程量"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="price"
              label="单价"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="calPrice"
              label="计算单价"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="totalPrice"
              label="合价"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="calTotalPrice"
              label="计算合价"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="content"
              label="问题描述"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="组织措施项目算术性" name="fourth">
          <p>检查规则：检查措施项目单价是否有计算错误的情况。</p>
          <el-table
            :data="tab4tableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="singleName"
              label="单项工程"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="unitName"
              label="单位工程"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="address"
              label="位置"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="code"
              label="清单编码"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="name"
              label="清单名称"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="unit"
              label="单位"
              align="left">
            </el-table-column>
            <el-table-column
              prop="workAmount"
              label="工程量"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="price"
              label="单价"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="calPrice"
              label="计算单价"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="其他项目、税金算术性" name="fifth">
          <p>检查规则：检查其他项目与税金金额是否有计算错误的情况。</p>
          <el-table
            :data="tab5tableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="singleName"
              label="单项工程"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="unitName"
              label="单位工程"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="address"
              label="位置"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="price"
              label="金额"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="calPrice"
              label="计算金额"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="content"
              label="问题描述"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="合价算术性" name="sixth">
          <p>检查规则：检查标段工程、各单项工程、各单位工程报价及投标总价是否有计算错误的情况。</p>
          <el-table
            :data="tab6tableData"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="type"
              label="项目类型"
              align="left">
              <template slot-scope="scope">
                <span v-if="scope.row.type === '1'">标段</span>
                <span v-if="scope.row.type === '2'">单项工程</span>
                <span v-if="scope.row.type === '3'">单位工程</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="项目名称"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="unitPartial"
              label="分部分项合价"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="unitKill"
              label="施工技术"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="unitOm"
              label="施工组织"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="unitOther"
              label="其他项目"
              align="left">
            </el-table-column>
            <el-table-column
              prop="unitTax"
              label="税金"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="totalPrice"
              label="合价"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="calTotal"
              label="计算合价"
              align="left"
              show-overflow-tooltip>
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
      activeName: 'first',
      tableData: [],
      tab2tableData: [],
      tab3tableData: [],
      tab4tableData: [],
      tab5tableData: [],
      tab6tableData: [],
      loading: true,
      allTotalPrice: ''
    }
  },
  methods: {
    handleClick (tab, event) {
    },
    // 检查数据
    _getTableData () {
      billPushData.check(this.projectId).then((res) => {
        let flag = true
        if (res.data.errors['9']) {
          this.tab2tableData = res.data.errors['9'].errorList
          let obj = {checktype: '清单单价遗漏'}
          obj.checknum = res.data.errors['9'].errorList.length
          if (obj.checknum > 0) {
            obj.checkresult = '不合格'
          } else {
            obj.checkresult = '合格'
          }
          this.tableData.push(obj)
        }
        if (res.data.errors['10']) {
          this.tab3tableData = res.data.errors['10'].errorList
          let obj = {checktype: '清单单价算术性'}
          obj.checknum = res.data.errors['10'].errorList.length
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
        if (res.data.errors['11']) {
          this.tab4tableData = res.data.errors['11'].errorList
          let obj = {checktype: '组织措施项目算术性'}
          obj.checknum = res.data.errors['11'].errorList.length
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
        if (res.data.errors['12']) {
          this.tab5tableData = res.data.errors['12'].errorList
          let obj = {checktype: '其他项目、税金算术性'}
          obj.checknum = res.data.errors['12'].errorList.length
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
        if (res.data.errors['13']) {
          this.tab6tableData = res.data.errors['13'].errorList
          let obj = {checktype: '合价算术性'}
          obj.checknum = res.data.errors['13'].errorList.length
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
        if (res.data.errors['16']) {
          this.allTotalPrice = res.data.errors['16'].errorList[0].price
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
  #checkunbid{
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
    #pane-second p,#pane-third p,#pane-fourth p,#pane-fifth p,#pane-sixth p{
      color: #111111;
      font-size: 12px;
      line-height: 26px;
    }
    span.totalprice{
      float: right;
      font-size: 16px;
      color: #333333;
      margin-right: 20px;
    }
    .tab .el-table{
      margin-bottom: 30px;
    }
  }
</style>
