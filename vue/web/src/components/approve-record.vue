<template>
  <tr>
    <td>审批记录：</td>
    <td  colspan="5" class="fivecolumn">
      <template>
        <el-table
          :data="approveRecord"
          border
          header-cell-class-name="tabletitles"
          class="apprecordtable">
          <el-table-column
            type="index"
            width="50"
            label="序号"
            align="center">
          </el-table-column>
          <el-table-column
            prop="auditStatus"
            label="审批状态"
            align="left"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="statusbigbox">
                <span v-if="scope.row.auditStatus === 0">
                  不通过
                </span>
                <span v-if="scope.row.auditStatus === 1">
                  通过
                </span>
                <span v-if="scope.row.auditStatus === 2">
                  已撤回
                </span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="createDate"
            label="审批时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="creator"
            label="审批人"
            align="left"
            :formatter="getUserName"
            show-overflow-tooltip
            v-if="$store.getters.authUser.enterpriseId === 1">
          </el-table-column>
          <el-table-column
            prop="opinion"
            label="审批意见"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
        </el-table>
      </template>
    </td>
  </tr>
</template>

<script>
import {approve} from '@/api'
export default {
  props: ['code'],
  data () {
    return {
      approveRecord: []
    }
  },
  methods: {
    list () {
      let parmas = {
        relatedCode: this.code
      }
      approve.queryList(parmas).then((res) => {
        this.approveRecord = res.data.recordOfApprovals
      })
    },
    // 时间戳转换
    timestampToTime (row, column) {
      var date = new Date(row.createDate) // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
      var Y = date.getFullYear() + '-'
      var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
      var D = date.getDate() + ' '
      var h = date.getHours() + ':'
      var m = date.getMinutes() + ':'
      var s = date.getSeconds()
      return Y + M + D + h + m + s
    },
    getUserName (row, column) {
      if (row.user) {
        return row.user.name
      } else {
        return '-----'
      }
    }
  },
  mounted () {
    this.list()
  }
}
</script>

<style lang="less">
  .apprecordtable{
    width: 100%;
    max-width: 100%;
    table-layout: fixed
  }
</style>
