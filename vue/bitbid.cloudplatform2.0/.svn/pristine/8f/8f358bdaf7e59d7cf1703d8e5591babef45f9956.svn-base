<template>
  <div>
    <template v-if="isFormAdd">
      <div class="basic-info-title">归档情况</div>
      <el-form class="viewdetails" label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="归档状态：">
              <span v-if="Number(info.archivesStatus) === 0">待归档</span>
              <span v-if="Number(info.archivesStatus) === 1">部分归档</span>
              <span v-if="Number(info.archivesStatus) === 2">全部归档</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="档案管理员：">
              <span>{{info.adminName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="归档进度：">
              <span>{{info.archivesSchedule}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归档时间：">
              <span>{{info.filingTime}}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </template>
    <div v-if="!isFormAdd" class="section_listbox">
      <div class="section_list">
        <ul class="section_ul">
          <li v-for="(item, index) in archivesProject.archivesInfoList" :key="item.index" :class="activeBid === index ? 'cur': ''" @click="handleBidClick(item, index)">
            <p :title="item.bidSectionName" class="bidSectionName">{{item.bidSectionName}}</p>
          </li>
        </ul>
      </div>
      <div class="btnMore" v-if="isMore">
        <i class="open" v-if="!isOpen" @click="handleIsOpen">展开</i>
        <i class="close" v-if="isOpen" @click="handleIsOpen">收起</i>
      </div>
    </div>
    <div class="basic-info-title">{{isFormAdd ? '移交情况' : '移交&归档记录'}}</div>
    <el-table
      :data="transferData"
      border
      style="width: 100%;margin-left: 32px" header-cell-class-name="tableheader">
      <el-table-column
        width="50"
        type="index"
        align="center"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="name"
        label="档案目录"
        align="center"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="transferStatus"
        label="移交状态"
        align="center"
        width="100"
        show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.transferStatus === 0">待移交</span>
          <span v-if="scope.row.transferStatus === 1" class="transferStatus-accept">已接收</span>
          <span v-if="scope.row.transferStatus === 2">已移交</span>
          <span v-if="scope.row.transferStatus === 3">已退回</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="lastTransferName"
        label="最新移交人"
        align="center"
        width="120"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="lastTransferTime"
        label="最新移交时间"
        align="center"
        width="160"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="status"
        label="归档状态"
        align="center"
        width="100"
        show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.transferStatus === 0"></span>
          <template v-else>
            <span v-if="scope.row.status === 0">未归档</span>
            <span v-if="scope.row.status === 1" class="transferStatus-accept">已归档</span>
          </template>
        </template>
      </el-table-column>
      <el-table-column
        prop="filingTime"
        label="归档时间"
        align="center"
        width="160"
        show-overflow-tooltip>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import {archivesClassify} from '@/api/archives'
export default {
  components: {
  },
  props: ['isFormAdd', 'info', 'allArchivesClassifyList', 'archivesProject'],
  data () {
    return {
      transferData: [
        {}
      ],
      objectId: '',
      bidList: [],
      activeBid: 0,
      isMore: false, // 判断标段是否需要多行显示
      isOpen: false // 判断标段是否展开收起
    }
  },
  watch: {
    info () {
      this.init()
    },
    archivesProject () {
      this.getTranferRecord()
    }
  },
  methods: {
    /**
     * 标段切换
     * @param item
     * @param index
     */
    handleBidClick (item, index) {
      this.activeBid = index
      this.objectId = item.objectId
      this.getInfo()
    },
    getInfo () {
      archivesClassify.getArchivesClassifyList({
        archivesInfoId: this.objectId
      }).then((res) => {
        if (res.data.resCode === '0000') {
          this.transferData = res.data.archivesClassifyList.list
        }
      })
    },
    init () {
      if (!this.info) {
        return
      }
      this.objectId = this.info.objectId
      this.getInfo()
    },
    initSectionStyle () {
      let sectionUlWrap = document.getElementsByClassName('section_ul')
      let height = sectionUlWrap[0].clientHeight
      if (height > 40) {
        this.isMore = true
      }
    },
    handleIsOpen () {
      this.isOpen = !this.isOpen
      let sectionListWrap = document.getElementsByClassName('section_list')
      if (this.isOpen) {
        sectionListWrap[0].style.height = 'auto'
      } else {
        sectionListWrap[0].style.height = 48 + 'px'
      }
    },
    getTranferRecord () {
      if (!this.archivesProject || !this.archivesProject.archivesInfoList) {
        return
      }
      this.objectId = this.archivesProject.archivesInfoList[0].objectId
      this.getInfo()
    }
  },
  mounted () {
    this.initSectionStyle()
  }
}
</script>
<style lang="less">
  .bidSectionName{
    line-height: 44px;
  }
</style>
