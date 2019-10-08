<template>
  <div id="formalHeadman" class="content_bgibox">
    <el-row>
      <el-col :span="11">
        <!--评审-->
        <div class="setup_bigbox">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">评审</span>
          </div>
          <!--磋商供应商选择-->
          <div class="type_selectbox">
            <div class="type_selecttitle">磋商供应商：</div>
            <el-select v-model="reviewSelId" placeholder="请选择" @change="reviewSelects">
              <el-option
                v-for="item in reviewSelect"
                :key="item.objectId"
                :label="item.userName"
                :value="item.objectId">
              </el-option>
            </el-select>
          </div>
          <!--磋商供应商选择-->
          <!--供应商选择数据-->
          <div class="tableDataSupplier_box">
            <el-table
              :data="tableDataSupplier"
              border
              style="width: 100%">
              <el-table-column
                prop="checkIndex"
                label="序号"
                width="70"
                align="center">
              </el-table-column>
              <el-table-column
                prop="itemName"
                label="评审因素"
                width="175"
                align="center"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="itemDesc"
                label="评审标准"
                width="175"
                align="center"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="itemScore"
                label="满分"
                width="80"
                align="center"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                label="得分"
                align="center">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.checkScore"
                    :ref="'input' + scope.row.objectId"
                    size="mini"
                    type="text"
                    @change="handleScore(scope.row.objectId, scope.row.checkScore, scope.row.itemScore)"
                    style="font-size: 15px;" :disabled="subStatus">
                  </el-input>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <!--供应商选择数据-->
        </div>
        <div class="review_final_offer">最终轮报价：{{finalOffer}}元</div>
        <!--评审-->
      </el-col>
      <el-col :span="13">
        <div class="pdf_bigbox">
          <div class="pdf_select_box">
            <el-tabs v-model="fileValueTender" @tab-click="filePdfSelect(fileValueTender)" style="width: 95%;">
              <el-tab-pane
                v-for="item in fileListTender"
                :key="item.objectId"
                :name="item.objectId"
                :label="item.label">
              </el-tab-pane>
            </el-tabs>
          </div>
          <div class="pdf_div">
            <iframe id="pdf_file1" :src="pdfUrl" frameborder="0" class="pdf_iframe"></iframe>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { consultingReview } from '@/api'
import {pdfUrl} from '@/assets/js/common'
import { handlePdfDisplay } from '@/utils/common'
export default {
  data () {
    return {
      pageSize: 1000, // 每页展示条数
      pageNo: 0,
      // 自定义序号
      dataIndex: 1,
      subStatus: false,
      // 磋商供应商选择
      reviewSelect: [],
      // 谈判商默认展示数据objectId
      reviewSelId: '',
      // 自定义objectId
      checkObjectId: '',
      // pdf地址
      basePdfUrl: `${pdfUrl}`,
      pdfUrl: '',
      // 供应商选择后数据
      tableDataSupplier: [
      ],
      // pdf列表数据
      fileListBid: [], // 招标
      fileListTender: [], // 投标
      fileValueTender: '',
      pdfWidth: '',
      pdfHeight: '',
      finalOffer: ''
    }
  },
  methods: {
    // 供应商数据
    consultingSuppliersSeleList () {
      let url = {
        checkType: 'HLXSH'
      }
      consultingReview.consultingSuppliersSele(url).then((res) => {
        this.reviewSelect = res.data.BidOpenUserList
        this.reviewSelId = this.reviewSelect[0].objectId
        this.consultingSuppliersTable()
      })
    },
    // 供应商表格数据
    consultingSuppliersTable () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        tenderId: this.reviewSelId,
        checkType: 'HLXSH'
      }
      consultingReview.consultingSuppliersList(url).then((res) => {
        this.tableDataSupplier = res.data.BidOpenCheckList
        this.finalOffer = res.data.price
        this.tableDataSupplier.map((its) => {
          if (its.checkStatus === 1) {
            this.subStatus = true
          }
        })
      })
    },
    // 选择切换数据
    reviewSelects (objectId) {
      this.reviewSelId = objectId
      this.consultingSuppliersTable()
      let tender
      this.reviewSelect.map((item) => {
        if (Object.is(item.objectId, objectId)) {
          tender = item.userName
        }
      })
      if (!this.fileListTender) {
        return
      }
      this.fileListTender.map((item) => {
        if (Object.is(item.label, tender)) {
          this.fileValueTender = item.objectId
          this.filePdfSelect(this.fileValueTender)
        }
      })
    },
    // pdf文件选择列表
    pdfSelectLists () {
      let url = {
        checkType: 'HLXSH'
      }
      consultingReview.pdfSelectList(url).then((res) => {
        this.fileListTender = res.data.FileList
        if (!this.fileListTender) {
          return
        }
        if (this.fileListTender.length > 1) {
          this.fileValueTender = this.fileListTender[1].objectId
        } else {
          this.fileValueTender = this.fileListTender[0].objectId
        }
        this.filePdfSelect(this.fileValueTender)
        this.fileListBid = this.fileListTender
      })
    },
    // 上边pdf列表选择
    filePdfSelect (doc) {
      this.pdfUrl = this.basePdfUrl + '?doc=' + doc + '&width=' + this.pdfWidth + '&height=' + this.pdfHeight
    },
    handleScore (id, score, totalScore) {
      var flagRex = /^0/
      let reg = /^0$|^[1-9]\d{0,15}$|^[1-9]\d{0,15}\.{1}\d{1,4}$|^0\.{1}\d{1,4}$/g
      if (score > 1) {
        if (flagRex.test(score)) {
          this.$message({
            type: 'warning',
            message: '不能输入以0开头的正数'
          })
          return false
        }
      }
      if (reg.test(score)) {
        if (score > totalScore) {
          this.$message({
            type: 'warning',
            message: '不能超过满分'
          })
          // let name = 'input' + id
          // this.$refs[name].value = ''
          return
        }
        let params = {
          checkScore: score,
          objectId: id
        }
        consultingReview.preservationUnqualified(params).then((res) => {
        })
      } else {
        this.$message({
          type: 'warning',
          message: '得分不能超过四位小数'
        })
      }
    }
  },
  watch: {
    dialogFormVisible: function (value) {
      handlePdfDisplay(value)
    }
  },
  mounted () {
    let o = document.getElementById('pdf_file1')
    if (o) {
      this.pdfWidth = (o.clientWidth || o.offsetWidth) - 20
      this.pdfHeight = (o.clientHeight || o.offsetHeight) - 10
    }
    this.consultingSuppliersSeleList()
    this.pdfSelectLists()
  }
}
</script>
<style lang="less">
  #formalHeadman{
    margin-top: 20px;
    .setup_bigbox{
      box-shadow: 0 2px 0px 0px rgba(0,0,0,0.08) inset;
    }
    .unqualifiedbox{
      text-align: center;
    }
  }
</style>
