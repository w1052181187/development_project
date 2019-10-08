<template>
  <div>
    <!--弹窗  竞争对手分析 查看-->
    <el-dialog title="查看竞争对手" :visible.sync="showVisible" :before-close="cancel">
      <el-form :model="submitForm" :label-width="formLabelWidth" v-loading="loading">
        <el-row>
          <el-col>
            <el-form-item label="企业名称：">
              <span>{{submitForm.name || '---'}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="竞争力：">
              <el-rate v-model="submitForm.level" disabled></el-rate>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="工商信息：">
              <el-input v-model="submitForm.businessInfo" type="textarea" placeholder="暂无数据" readonly></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="分支机构：">
              <el-input v-model="submitForm.branch" type="textarea" placeholder="暂无数据" readonly></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="主要成员及股东名单：">
              <el-input v-model="submitForm.mainMembers" type="textarea" placeholder="暂无数据" readonly></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="企业资质等级：">
              <span>{{submitForm.categoryInfo}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="近一年承接项目情况：">
              <el-table
                :data="submitForm.yearProjectSituationList"
                border
                style="width: 100%" header-cell-class-name="tableheader">
                <el-table-column
                  type="index"
                  label="序号"
                  width="60"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="项目名称"
                  :formatter="simpleFormatData"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="amount"
                  label="项目金额"
                  :formatter="simpleFormatData"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="承接本招标人项目情况：">
              <el-table
                :data="submitForm.ownerProjectSituationList"
                border
                style="width: 100%" header-cell-class-name="tableheader">
                <el-table-column
                  type="index"
                  label="序号"
                  width="60"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="项目名称"
                  :formatter="simpleFormatData"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="amount"
                  label="项目金额"
                  :formatter="simpleFormatData"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--弹窗  竞争对手分析 查看-->
  </div>
</template>
<script>
import { category } from '@/api/resource'
import { competitor } from '@/api/customer'
export default {
  data () {
    return {
      loading: false,
      submitForm: {
        analysisCode: this.relatedCode,
        level: 0,
        categoryId: null,
        gradeId: null,
        categoryInfo: '',
        yearProjectSituationList: [],
        ownerProjectSituationList: []
      },
      formLabelWidth: '170px',
      // 资质等级联动一级
      categoryFirstOptions: [],
      // 资质等级联动二级
      categorySecondOptions: []
    }
  },
  props: ['showVisible', 'objectId'],
  watch: {
    objectId (value) {
      if (value) {
        this.initData()
      } else {
        // 清除所有数据
        let newSubmitForm = {
          analysisCode: this.relatedCode,
          categoryId: null,
          gradeId: null,
          yearProjectSituationList: [],
          ownerProjectSituationList: []
        }
        this.submitForm = newSubmitForm
      }
    }
  },
  methods: {
    // 获取列表数据
    initData () {
      this.loading = true
      competitor.queryOne(this.objectId).then(res => {
        this.loading = false
        let obj = res.data.data
        // 包装资质等级
        if (obj.categoryId) {
          obj.categoryInfo = this.categoryFirstOptions.find(item => item.objectId === obj.categoryId).name || ''
          category.getCategoryLevelList(obj.categoryId).then(res => {
            this.categorySecondOptions = res.data.categoryList
            if (obj.gradeId) {
              let gradeName = this.categorySecondOptions.find(item => item.objectId === obj.gradeId).name || ''
              obj.categoryInfo = obj.categoryInfo + '-' + gradeName
              if (obj.categoryInfo === '-') {
                obj.categoryInfo = '暂无数据'
              }
            }
          })
        }
        this.submitForm = obj
        this.submitForm.level = Number(this.submitForm.level)
      })
    },
    // 初始化资质信息
    initCategoryFirst () {
      category.getCategoryList().then((res) => {
        this.categoryFirstOptions = res.data.categoryList
      })
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    },
    // 关闭
    cancel () {
      this.$emit('showDialog', false)
    }
  },
  mounted () {
    // 初始化资质信息
    this.initCategoryFirst()
  }
}
</script>
<style scoped>
  .selectbox{
    background: #f7f8f9;
    padding: 0px 0;
    margin-bottom: 0px;
  }
</style>
