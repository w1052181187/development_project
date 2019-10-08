<template>
  <div>
    <div class="file-type-name">
      <span>招标公告</span>
    </div>
    <el-table
      :data="item.noticeInfoList"
      style="width:100%"
      border
      header-cell-class-name="tableheader-filetype">
      <el-table-column
        type="index"
        :index="1"
        label="序号"
        width="80"
        align="center">
      </el-table-column>
      <el-table-column
        prop="title"
        label="公告名称"
        align="center">
      </el-table-column>
      <el-table-column
        prop="nature"
        label="公告性质"
        align="center">
        <template slot-scope="scope">
          {{scope.row.nature === 1 ? '正常公告' : '更正公告'}}
        </template>
      </el-table-column>
      <el-table-column
        prop="publishDate"
        label="发布时间"
        align="center"
        value-format="yyyy-MM-dd HH:mm">
      </el-table-column>
      <el-table-column
        prop="url"
        label="公告链接"
        align="center">
        <template  slot-scope="scope">
          <a :href="scope.row.url" target="_blank">{{scope.row.url}}</a>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        v-if="canEdit">
        <template slot-scope="scope">
          <el-button
            @click="handleEdit(scope)"
            type="text"
            size="small">
            修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--添加弹框-->
    <el-dialog :before-close='cancle' title="招标公告" :visible.sync="dialogFormVisible" width="50%">
      <el-form :model="form" :rules="rules" ref="form" :label-width="formLabelWidth">
        <el-form-item label="公告名称：" prop="title" >
          <el-input auto-complete="off" v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="公告性质：" prop="nature">
          <el-select v-model="form.nature" placeholder="请选择">
            <el-option
              v-for="item in annoNatureOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发布时间：" prop="publishDate">
          <el-date-picker
            v-model="form.publishDate"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="公告链接：" prop="url">
          <el-input v-model="form.url"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="cancal" @click="cancle">取消</el-button>
        <el-button type="primary" @click="addNotice('form')" :loading="isLoading">确认</el-button>
      </div>
    </el-dialog>
    <!--添加弹框-->
    <div class="file-type-add" @click="handleAdd" v-if="canEdit">
      <span>+ 添加招标公告</span>
    </div>
    <fileUpload class="file-type-enclosure" :canEdit="canEdit"
                :file-list="item.refArchivesFileInformationList"
                :object-id="item.objectId" :file-type='9' file-name='其他'></fileUpload>
  </div>
</template>
<script>
import {validLinks} from '@/assets/js/validate'
import {archivesFile} from '@/api/archives'
export default {
  props: ['item', 'canEdit'],
  components: {
    fileUpload: resolve => require(['./fileUpload'], resolve)
  },
  watch: {
  },
  data () {
    return {
      // 增加公告的提交
      form: {
        title: '',
        nature: '',
        publishDate: '',
        url: ''
      },
      // 公告性质
      annoNatureOptions: [{
        value: 1,
        label: '正常公告'
      }, {
        value: 2,
        label: '更正公告'
      }
      ],
      // 判断
      rules: {
        title: [
          {required: true, message: '公告名称不能为空', trigger: ['blur', 'change']},
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ],
        publishDate: [
          {required: true, message: '发布时间不能为空', trigger: ['blur', 'change']}
        ],
        nature: [
          {required: true, message: '公告性质不能为空', trigger: ['blur', 'change']}
        ],
        url: [
          {min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change']},
          {validator: validLinks, trigger: 'change'}
        ]
      },
      // 日期选择
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      formLabelWidth: '120px',
      dialogFormVisible: false,
      isLoading: false
    }
  },
  methods: {
    // 添加保存
    addNotice (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.isLoading = true
          this.form.archivesFileId = this.item.objectId
          archivesFile.savaOrUpdateNotice(this.form).then((res) => {
            this.isLoading = false
            if (res.data.resCode === '0000') {
              if (!this.isUpdate) {
                this.item.noticeInfoList.push(res.data.noticeInfo)
                this.isUpdate = false
              }
              this.cancle()
            }
          })
        } else {
          return false
        }
      })
    },
    handleAdd () {
      this.dialogFormVisible = true
    },
    handleEdit (scope) {
      this.form = Object.assign({}, scope.row)
      this.dialogFormVisible = true
      this.isUpdate = true
    },
    cancle () {
      this.form = {}
      this.$refs.form.resetFields()
      this.dialogFormVisible = false
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
</style>
