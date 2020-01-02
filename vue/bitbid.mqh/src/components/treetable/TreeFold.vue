<template>
  <!-- <el-form :model="ruleForm" ref="form"> -->
    <el-table :data="data" :row-style="showRow" v-bind="$attrs" style="width: 100%" class="eltable-tree" row-key="id">
      <el-table-column v-for="(column) in columns" :key="column.value" :label="column.text" :width="column.width" header-align="center" align="left" fixed>
        <template slot-scope="scope">
          {{ scope.row[column.value] }}
        </template>
      </el-table-column>
      <el-table-column
        prop="suggestedPlan.content"
        label="工程简要内容及设备型号"
        align="center">
      </el-table-column>
      <el-table-column
        prop="suggestedPlan.quantitiesUnit"
        label="工程量单位"
        align="center">
      </el-table-column>
      <el-table-column
        prop="suggestedPlan.budgetQuantities"
        label="工程量"
        align="center">
        <template slot-scope="scope" v-if="scope.row.suggestedPlan">
          <span>{{scope.row.suggestedPlan.budgetQuantities?scope.row.suggestedPlan.budgetQuantities:scope.row.suggestedPlan.currentPlanQuantities}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="suggestedPlan.budgetAmount"
        label="计划金额"
        align="center">
        <template slot-scope="scope" v-if="scope.row.suggestedPlan">
          <span>{{scope.row.suggestedPlan.budgetAmount?scope.row.suggestedPlan.budgetAmount:scope.row.suggestedPlan.currentPlanAmount}}</span>
        </template>
      </el-table-column>
      <el-table-column
        v-if="months.includes(1)"
        label="1月"
        width="300"
        align="center">
        <el-table-column
          prop="januaryMonthStatistics.number"
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.januaryMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item :prop="'data.'+scope.$index+'.januaryMonthStatistics'+ '.number'" :rules="rules.januaryMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.januaryMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.januaryMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.januaryMonthStatistics.number}}</span>
            <!-- </el-form-item> -->
          </template>
        </el-table-column>
        <el-table-column
          prop="januaryMonthStatistics.price"
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.januaryMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item :prop="'data'+scope.$index+['januaryMonthStatistics.price']" :rules="rules['januaryMonthStatistics.price']"> -->
              <el-input v-model="scope.row.januaryMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.januaryMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.januaryMonthStatistics.price}}</span>
            <!-- </el-form-item> -->
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        v-if="months.includes(2)"
        label="2月"
        width="300"
        align="center">
        <el-table-column
          prop="februaryMonthStatistics.number"
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.februaryMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="februaryMonthStatisticsNumber" :rules="rules.februaryMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.februaryMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.februaryMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.februaryMonthStatistics.number}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
        <el-table-column
          prop="februaryMonthStatistics.price"
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.februaryMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="februaryMonthStatisticsPrice" :rules="rules.februaryMonthStatisticsPrice"> -->
              <el-input v-model="scope.row.februaryMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.februaryMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.februaryMonthStatistics.price}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        v-if="months.includes(3)"
        label="3月"
        width="300"
        align="center">
        <el-table-column
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.marchMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="marchMonthStatisticsNumber" :rules="rules.marchMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.marchMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.marchMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.marchMonthStatistics.number}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
        <el-table-column
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.marchMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="marchMonthStatisticsPrice" :rules="rules.marchMonthStatisticsPrice"> -->
              <el-input v-model="scope.row.marchMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.marchMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.marchMonthStatistics.price}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        v-if="months.includes(4)"
        label="4月"
        width="300"
        align="center">
        <el-table-column
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.aprilMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="aprilMonthStatisticsNumber" :rules="rules.aprilMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.aprilMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.aprilMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.aprilMonthStatistics.number}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
        <el-table-column
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.aprilMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="aprilMonthStatisticsPrice" :rules="rules.aprilMonthStatisticsPrice"> -->
              <el-input v-model="scope.row.aprilMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.aprilMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.aprilMonthStatistics.price}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        v-if="months.includes(5)"
        label="5月"
        width="300"
        align="center">
        <el-table-column
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.mayMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="mayMonthStatisticsNumber" :rules="rules.mayMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.mayMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.mayMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.mayMonthStatistics.number}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
        <el-table-column
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.mayMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="mayMonthStatisticsPrice" :rules="rules.mayMonthStatisticsPrice"> -->
              <el-input v-model="scope.row.mayMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.mayMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.mayMonthStatistics.price}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        v-if="months.includes(6)"
        label="6月"
        width="300"
        align="center">
        <el-table-column
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.juneMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="juneMonthStatisticsNumber" :rules="rules.juneMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.juneMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.juneMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.juneMonthStatistics.number}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
        <el-table-column
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.juneMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="juneMonthStatisticsPrice" :rules="rules.juneMonthStatisticsPrice"> -->
              <el-input v-model="scope.row.juneMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.juneMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.juneMonthStatistics.price}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        v-if="months.includes(7)"
        label="7月"
        width="300"
        align="center">
        <el-table-column
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.julyMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="julyMonthStatisticsNumber" :rules="rules.julyMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.julyMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.julyMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.julyMonthStatistics.number}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
        <el-table-column
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.julyMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="julyMonthStatisticsPrice" :rules="rules.julyMonthStatisticsPrice"> -->
              <el-input v-model="scope.row.julyMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.julyMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.julyMonthStatistics.price}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        v-if="months.includes(8)"
        label="8月"
        width="300"
        align="center">
        <el-table-column
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.augustMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="augustMonthStatisticsNumber" :rules="rules.augustMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.augustMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.augustMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.augustMonthStatistics.number}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
        <el-table-column
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.augustMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="augustMonthStatisticsPrice" :rules="rules.augustMonthStatisticsPrice"> -->
              <el-input v-model="scope.row.augustMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.augustMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.augustMonthStatistics.price}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        v-if="months.includes(9)"
        label="9月"
        width="300"
        align="center">
        <el-table-column
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.septemberMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="septemberMonthStatisticsNumber" :rules="rules.septemberMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.septemberMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.septemberMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.septemberMonthStatistics.number}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
        <el-table-column
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.septemberMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="septemberMonthStatisticsPrice" :rules="rules.septemberMonthStatisticsPrice"> -->
              <el-input v-model="scope.row.septemberMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.septemberMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.septemberMonthStatistics.price}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        v-if="months.includes(10)"
        label="10月"
        width="300"
        align="center">
        <el-table-column
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.octoberMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="octoberMonthStatisticsNumber" :rules="rules.octoberMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.octoberMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.octoberMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.octoberMonthStatistics.number}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
        <el-table-column
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.octoberMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="octoberMonthStatisticsPrice" :rules="rules.octoberMonthStatisticsPrice"> -->
              <el-input v-model="scope.row.octoberMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.octoberMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.octoberMonthStatistics.price}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        v-if="months.includes(11)"
        label="11月"
        width="300"
        align="center">
        <el-table-column
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.novemberMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="novemberMonthStatisticsNumber" :rules="rules.novemberMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.novemberMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.novemberMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.novemberMonthStatistics.number}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
        <el-table-column
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.novemberMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="novemberMonthStatisticsPrice" :rules="rules.novemberMonthStatisticsPrice"> -->
              <el-input v-model="scope.row.novemberMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.novemberMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.novemberMonthStatistics.price}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column
        v-if="months.includes(12)"
        label="12月"
        width="300"
        align="center">
        <el-table-column
          label="数量"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.decemberMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="decemberMonthStatisticsNumber" :rules="rules.decemberMonthStatisticsNumber"> -->
              <el-input v-model="scope.row.decemberMonthStatistics.number" v-if="scope.row.suggestedPlan.objectId && scope.row.decemberMonthStatistics.numberIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.decemberMonthStatistics.number}}</span>
            <!-- </el-form-item>   -->
          </template>
        </el-table-column>
        <el-table-column
          label="金额"
          width="150"
          align="center">
          <template slot-scope="scope" v-if="scope.row.decemberMonthStatistics && scope.row.suggestedPlan">
            <!-- <el-form-item prop="decemberMonthStatisticsPrice" :rules="rules.decemberMonthStatisticsPrice"> -->
              <el-input v-model="scope.row.decemberMonthStatistics.price" v-if="scope.row.suggestedPlan.objectId && scope.row.decemberMonthStatistics.priceIsSubmitted === 0" class="history-input"></el-input>
              <span v-else>{{scope.row.decemberMonthStatistics.price}}</span>
            <!-- </el-form-item>   -->
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
        align="center"
        width="200">
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
          <!-- <el-form-item prop="suggestedPlanYearStatistics" :rules="rules.suggestedPlanYearStatistics"> -->
            <el-input v-model="scope.row.suggestedPlanYearStatistics.expectedComplete" v-if="scope.row.suggestedPlan.objectId &&
            scope.row.suggestedPlanYearStatistics.isSubmitted === 0"></el-input>
            <span v-else>{{scope.row.suggestedPlanYearStatistics.expectedComplete}}</span>
          <!-- </el-form-item>   -->
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
        width="200"
        fixed="right">
        <template  slot-scope="scope" v-if="scope.row.suggestedPlan && scope.row.suggestedPlan.objectId">
          <el-select v-model="scope.row.suggestedPlan.isFinish" placeholder="请选择" style="float:left;width:100px;">
            <el-option label="未完成" :value="0"></el-option>
            <el-option label="已完成" :value="1"></el-option>
          </el-select>
          <el-button type="text" @click="tableBtnClick(scope.row.suggestedPlan, scope.$index)">备注</el-button>
        </template>
      </el-table-column>
    </el-table>
  <!-- </el-form>   -->
</template>
<script>
import treeToArray from './utils/eval'
export default {
  name: 'TreeTable',
  data () {
    let checkAmount = (rule, value, callback) => {
      if (value && (!(/^\d{1,14}(\.\d{1,2})?$/).test(value))) {
        callback(new Error('小数点前不超过十四位小数点后不超过两位的数值'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {},
      rules: {
        januaryMonthStatisticsNumber: [
          {validator: checkAmount}
        ]
      },
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
      tableBtnClick (row, index) {
        this.$emit('operate', row, index)
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
<style lang="less">
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
    .history-num {
      display: block;
      float: left;
      line-height: 40px;
    }
    .history-input {
      width: 90px;
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

