<template>
  <el-table :data="data" :row-style="showRow" v-bind="$attrs" style="width: 100%" row-key="id">
    <el-table-column v-for="(column) in columns" :key="column.value" :label="column.text" :width="column.width" header-align="center" align="left">
      <template slot-scope="scope">
        {{ scope.row.orderNumber | filterNumber(scope.row) }}{{ scope.row[column.value] }}
      </template>
    </el-table-column>
    <el-table-column
      prop="suggestedPlan.content"
      label="项目主要内容"
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
      <template slot-scope="scope">
        <span>{{scope.row.suggestedPlan.budgetQuantities?scope.row.suggestedPlan.budgetQuantities:scope.row.suggestedPlan.currentPlanQuantities}}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="suggestedPlan.budgetAmount"
      label="计划金额（万元）"
      align="center">
      <template slot-scope="scope">
        <span>{{scope.row.suggestedPlan.budgetAmount?scope.row.suggestedPlan.budgetAmount:scope.row.suggestedPlan.currentPlanAmount}}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="suggestedPlan.sectionInfoNumber"
      label="中标数量"
      align="center">
      <template slot-scope="scope">
        <el-form :model="scope.row" :rules="rulesBidNum" :ref="'formBidNum'+scope.$index">
          <el-form-item prop="suggestedPlan.sectionInfoNumber">
            <el-input v-model="scope.row.suggestedPlan.sectionInfoNumber" :disabled="!scope.row.dialogEdit"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column
      prop="suggestedPlan.sectionInfoPrice"
      label="中标价（万元）"
      align="center">
      <template slot-scope="scope">
        <el-form :model="scope.row" :rules="rulesBidPrice" :ref="'formBidPrice'+scope.$index">
          <el-form-item prop="suggestedPlan.sectionInfoPrice">
            <el-input v-model="scope.row.suggestedPlan.sectionInfoPrice" :disabled="!scope.row.dialogEdit"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <!--<el-table-column
      label="操作"
      align="center">
      <template slot-scope="scope">
        <el-button @click="tableBtnClick(scope.row, scope.$index, 'edit')" type="text" size="small">编辑</el-button>
        <el-button @click="tableBtnClick(scope.row, scope.$index, 'save')" type="text" size="small">保存</el-button>
      </template>
    </el-table-column>-->
  </el-table>
  </el-form>
</template>
<script>
import treeToArray from './utils/eval'
export default {
  name: 'TreeTable',
  data () {
    var checkAmount = (rule, value, callback) => {
      if (value && (!(/^[1-9]\d{0,4}$/).test(value))) {
        callback(new Error('中标数量为不超过五位的数值'))
      } else {
        callback()
      }
    }
    var checkPrice = (rule, value, callback) => {
      if (value && (!(/^\d{1,18}(\.\d{1,4})?$/).test(value))) {
        callback(new Error('输入小数点前不超18位且小数点后不超4位的数字'))
      } else {
        callback()
      }
    }
    return {
      rulesBidNum: {
        'suggestedPlan.sectionInfoNumber' : [
          {required: true, message: '请输入中标数量', trigger: 'blur'},
          {validator: checkAmount}
        ]
      },
      rulesBidPrice: {
        'suggestedPlan.sectionInfoPrice': [
          {required: true, message: '请输入中标价', trigger: 'blur'},
          {validator: checkPrice}
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
    evalFunc: Function,
    evalArgs: Array,
    expandAll: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    // 格式化数据源
    // formatData: function() {
    //   let tmp
    //   if (!Array.isArray(this.data)) {
    //     tmp = [this.data]
    //   } else {
    //     tmp = this.data
    //   }
    //   const func = this.evalFunc || treeToArray
    //   const args = this.evalArgs ? Array.concat([tmp, this.expandAll], this.evalArgs) : [tmp, this.expandAll]
    //   return func.apply(null, args)
    // }
  },
  methods: {
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
    tableBtnClick (row, type) {
      this.$emit('operate', row, scope.$index, type)
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

