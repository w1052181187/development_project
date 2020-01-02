<template>
  <el-table :data="data" :row-style="showRow" v-bind="$attrs" style="width: 100%" class="eltable-tree" row-key="id">
    <el-table-column v-for="(column) in columns" :key="column.value" :label="column.text" :width="column.width" header-align="center" align="left" fixed>
      <template slot-scope="scope">
        {{ scope.row[column.value] }}
      </template>
    </el-table-column>
    <el-table-column
      prop="suggestedPlan.content"
      label="工程简要内容及设备型号"
      align="center"
      >
    </el-table-column>
    <el-table-column
      prop="suggestedPlan.quantitiesUnit"
      label="工程量单位"
      align="center"
      >
    </el-table-column>
    <el-table-column
      prop="suggestedPlan.budgetQuantities"
      label="工程量"
      align="center"
      >
      <template slot-scope="scope" v-if="scope.row.suggestedPlan">
        <span>{{scope.row.suggestedPlan.budgetQuantities?scope.row.suggestedPlan.budgetQuantities:scope.row.suggestedPlan.currentPlanQuantities}}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="suggestedPlan.budgetAmount"
      label="计划金额"
      align="center"
      >
      <template slot-scope="scope" v-if="scope.row.suggestedPlan">
        <span>{{scope.row.suggestedPlan.budgetAmount?scope.row.suggestedPlan.budgetAmount:scope.row.suggestedPlan.currentPlanAmount}}</span>
      </template>
    </el-table-column>
    <el-table-column
      v-if="months.includes(1)"
      label="1月"
      width="200"
      align="center">
      <el-table-column
        prop="januaryMonthStatistics.number"
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.januaryMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.januaryMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="januaryMonthStatistics.price"
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.januaryMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.januaryMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column
      v-if="months.includes(2)"
      label="2月"
      width="200"
      align="center">
      <el-table-column
        prop="februaryMonthStatistics.number"
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.februaryMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.februaryMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="februaryMonthStatistics.price"
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.februaryMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.februaryMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column
      v-if="months.includes(3)"
      label="3月"
      width="200"
      align="center">
      <el-table-column
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.marchMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.marchMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.marchMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.marchMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column
      v-if="months.includes(4)"
      label="4月"
      width="200"
      align="center">
      <el-table-column
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.aprilMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.aprilMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.aprilMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.aprilMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column
      v-if="months.includes(5)"
      label="5月"
      width="200"
      align="center">
      <el-table-column
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.mayMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.mayMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.mayMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.mayMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column
      v-if="months.includes(6)"
      label="6月"
      width="200"
      align="center">
      <el-table-column
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.juneMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.juneMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.juneMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.juneMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column
      v-if="months.includes(7)"
      label="7月"
      width="200"
      align="center">
      <el-table-column
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.julyMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.julyMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.julyMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.julyMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column
      v-if="months.includes(8)"
      label="8月"
      width="200"
      align="center">
      <el-table-column
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.augustMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.augustMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.augustMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.augustMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column
      v-if="months.includes(9)"
      label="9月"
      width="200"
      align="center">
      <el-table-column
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.septemberMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.septemberMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.septemberMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.septemberMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column
      v-if="months.includes(10)"
      label="10月"
      width="200"
      align="center">
      <el-table-column
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.octoberMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.octoberMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.octoberMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.octoberMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column
      v-if="months.includes(11)"
      label="11月"
      width="200"
      align="center">
      <el-table-column
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.novemberMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.novemberMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.novemberMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.novemberMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column
      v-if="months.includes(12)"
      label="12月"
      width="200"
      align="center">
      <el-table-column
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.decemberMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.decemberMonthStatistics.number}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.decemberMonthStatistics && scope.row.suggestedPlan">
          <span>{{scope.row.decemberMonthStatistics.price}}</span>
        </template>
      </el-table-column>
    </el-table-column>
    <!-- <el-table-column
      align="center"
      width="200"
      class="current-month">
      <template slot="header" slot-scope="scope">
        <el-button
        type="text"
        class="table-col-btn"
        size="mini"
        @click="openColumn(scope.row)"
        icon="el-icon-caret-right">
          {{hideColumn?'展开':'关闭'}}
        </el-button>
        <span></span>{{currentMonth + '月'}}
      </template>
      <el-table-column
        label="数量"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.aprilMonthStatistics">
          <el-input v-model="scope.row.aprilMonthStatistics.number"></el-input>
        </template>
      </el-table-column>
      <el-table-column
        label="金额"
        width="100"
        align="center">
        <template slot-scope="scope" v-if="scope.row.aprilMonthStatistics">
          <el-input v-model="scope.row.budgetAmount.price"></el-input>
        </template>
      </el-table-column>
    </el-table-column> -->
    <el-table-column
      label="累计完成"
      align="center">
      <el-table-column
        prop="suggestedPlanYearStatistics.cumulativeCompleteNumber"
        label="数量"
        width="100"
        align="center">
      </el-table-column>
      <el-table-column
        prop="suggestedPlanYearStatistics.cumulativeCompletePrice"
        label="金额"
        align="center"
        width="100">
      </el-table-column>
    </el-table-column>
    <el-table-column
      prop="suggestedPlanYearStatistics.expectedComplete"
      label="全年预计完成"
      align="center">
      <template slot-scope="scope" v-if="scope.row.suggestedPlanYearStatistics">
        <span>{{scope.row.suggestedPlanYearStatistics.expectedComplete}}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="suggestedPlanYearStatistics.monthCompleteSituation"
      label="招标情况"
      show-overflow-tooltip
      align="center">
      <template slot-scope="scope" v-if="scope.row.suggestedPlan">
        <span>{{scope.row.suggestedPlan.monthCompleteSituation}}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="suggestedPlan.isFinish"
      label="备注"
      align="center"
      width="100"
      fixed="right">
      <template  slot-scope="scope" v-if="scope.row.suggestedPlan && scope.row.suggestedPlan.objectId">
        {{scope.row.suggestedPlan.isFinish === 1? '已完成': '未完成'}}
        <el-button type="text" @click="tableBtnClick(scope.row.suggestedPlan)">备注</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>
<script>
import treeToArray from './utils/eval'
export default {
  name: 'TreeTable',
  data () {
    return {
      monthList: [],
      hideColumn: true, // 隐藏列
      currentMonth: null
    }
  },
  props: {
    /* eslint-disable */
    data: {
      type: [Array, Object],
      required: true
    },
    columns: {
      type: Array,
      default: () => []
    },
    months: {
      type: Array,
      default: () => []
    },
    evalFunc: Function,
    evalArgs: Array,
    expandAll: {
      type: Boolean,
      default: false
    }
  },
    computed: {
    },
    methods: {
      /* 增加-非公开行颜色 */
      // tableRowClassName ({row, rowIndex}) {
      //   if (row.suggestedPlan) {
      //     if (row.suggestedPlan.bidType !== 1 && row.suggestedPlan.objectId) {
      //       return 'noopen_row'
      //     }
      //   }
      // },
      showRow: function(row) {
        const show = (row.row.parent ? (row.row.parent._expanded && row.row.parent._show) : true)
        row.row._show = show
        return show ? 'animation:treeTableShow 1s;-webkit-animation:treeTableShow 1s;' : 'display:none;'
      },
      // 切换下级是否展开
      toggleExpanded: function(trIndex) {
        const record = this.formatData[trIndex]
        record._expanded = !record._expanded
      },
      // 图标显示
      iconShow(index, record) {
        return (index === 0 && record.children && record.children.length > 0)
      },
      tableBtnClick (row) {
        this.$emit('operate', row)
      },
      openColumn (row) {
        this.hideColumn = !this.hideColumn
      },
      // 获取当前月份
      getCurrentMonth () {
        let date = new Date()
        let month = date.getMonth() + 1
        this.currentMonth = month
        this.getMonthList(month)
      },
      // 根据当前月份-生成月份list
      getMonthList (month) {
        if (!month) {return}
        let result = []
        for (let i = 1; i <= month; i++) {
          // let obj = {
          //   month: i,
          //   value:
          // }
          result.push(i)
        }
        this.monthList = result
      }
    },
    mounted () {
      this.getCurrentMonth()
    },
    filters: {
      filterNumber (num, row) {
        num = Number(num) - 1
        var upperCaseNumber = ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十']
        var length = String(num).length
        if (length === 1 && row._level === 1) {
          return `${upperCaseNumber[num]}、`
        } else if (length === 1 && row._level === 2 && row.children !== null) {
          return `(${upperCaseNumber[num]})`
        } else if (row.children === null) {
          let levelT = num + 1
          return `${levelT++}、`
        }
      }
    }
  }
</script>
<style lang="less" scoped>
  @keyframes treeTableShow {
    from {opacity: 0;}
    to {opacity: 1;}
  }
  @-webkit-keyframes treeTableShow {
    from {opacity: 0;}
    to {opacity: 1;}
  }
  @color-blue: #2196F3;
  @space-width: 18px;
  .ms-tree-space {
    position: relative;
    top: 1px;
    display: inline-block;
    font-style: normal;
    font-weight: 400;
    line-height: 1;
    width: @space-width;
    height: 14px;
    &::before {
      content: ""
    }
  }
  .eltable-tree {
    .el-table__fixed,
    .el-table__fixed-right {
      z-index: 2 !important;
    }
    .noopen_row {
      color: #00ba39 !important;
    }
  }
  .el-icon-caret-bottom,
  .el-icon-caret-right {
    font-size: 18px;
  }
  .processContainer{
    width: 100%;
    height: 100%;
  }
  table td {
    line-height: 26px;
  }
  .save-boxs {
    padding: 10px;
  }
  .el-table__header-wrapper  .table-col-btn {
    position: absolute;
    display: inline-block;
    left: -5px;
    top: 0;
  }
  .tree-ctrl{
    position: relative;
    cursor: pointer;
    color: @color-blue;
    margin-left: -@space-width;
  }
</style>

