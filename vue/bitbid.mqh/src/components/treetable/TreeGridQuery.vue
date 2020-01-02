<template>
  <el-table :data="data" :row-style="showRow" v-bind="$attrs" row-key="id">
    <el-table-column v-for="(column) in columns" :key="column.value" :label="column.text" :width="column.width" header-align="center" align="left">
      <template slot-scope="scope">
        <!-- Todo -->
        <!-- eslint-disable-next-line vue/no-confusing-v-for-v-if -->
        <!-- <label v-if="index === 0">
          <span v-for="space in scope.row._level" :key="space" class="ms-tree-space"></span>
        </label>
        <span v-if="iconShow(index,scope.row)" class="tree-ctrl" @click="toggleExpanded(scope.$index)">
          <i v-if="!scope.row._expanded" class="el-icon-caret-right"/>
          <i v-else class="el-icon-caret-bottom"/>
        </span> -->
        {{ scope.row.orderNumber | filterNumber(scope.row) }}
        {{ scope.row[column.value] }}
      </template>
    </el-table-column>
    <el-table-column
      prop="content"
      label="工程简要内容及设备型号"
      align="center">
    </el-table-column>
    <el-table-column
      prop="quantitiesUnit"
      label="工程量单位"
      align="center">
    </el-table-column>
    <el-table-column
      label="概算"
      align="center">
      <el-table-column
        prop="budgetQuantities"
        label="工程量"
        align="center">
      </el-table-column>
      <el-table-column
        prop="budgetAmount"
        label="金额"
        align="center">
      </el-table-column>
    </el-table-column>
    <el-table-column
      label="本年计划"
      align="center">
      <el-table-column
        prop="currentPlanQuantities"
        label="工程量"
        align="center">
      </el-table-column>
      <el-table-column
        prop="currentPlanAmount"
        label="金额"
        align="center">
      </el-table-column>
    </el-table-column>
    <el-table-column
      label="操作"
      align="center">
      <template slot-scope="scope">
        <el-button
          @click="tableBtnClick(scope.row)"
          type="text" size="small"
          v-if="scope.row.children ==undefined">
          查看申报表
        </el-button>
      </template>
    </el-table-column>
    <slot/>
  </el-table>
</template>

<script>
import treeToArray from './utils/eval'
export default {
  name: 'TreeTable',
  data () {
    return {
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
    tableBtnClick (row) {
      this.$emit('operate', row)
    }
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

  .tree-ctrl{
    position: relative;
    cursor: pointer;
    color: @color-blue;
    margin-left: -@space-width;
  }
</style>
