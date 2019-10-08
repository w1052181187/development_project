<template>
  <div class="cloudcontent" id="archives-detail">
    <div class="main">
      <div class="detail-title">{{info.tenderProjectName ? info.tenderProjectName : ''}}</div>
      <el-tabs v-model="activeTab" >
        <el-tab-pane label="档案信息" name="1">
          <archives-info :info="info" :isShowTransferRecord="!isQuery"></archives-info>
        </el-tab-pane>
        <el-tab-pane :label="isQuery ? '标段信息' : '移交记录'" name="2">
          <section-info  v-if="isQuery" :archivesProject="info"></section-info>
          <transfer-record v-else :archivesProject="info" :isFormAdd="false"></transfer-record>
        </el-tab-pane>
        <el-tab-pane label="归档文件" :name="'3'">
          <file-pack-download :info="info" :isQuery="isQuery"></file-pack-download>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import {archivesProject} from '@/api/archives'
export default {
  components: {
    transferRecord: resolve => require(['./transferRecord'], resolve),
    archivesInfo: resolve => require(['./archivesInfo.vue'], resolve),
    sectionInfo: resolve => require(['./sectionInfo.vue'], resolve),
    filePackDownload: resolve => require(['./filePackDownload.vue'], resolve)
  },
  data () {
    return {
      isQuery: false,
      title: '',
      activeTab: '1',
      info: {}
    }
  },
  watch: {
    '$route': 'init'
  },
  methods: {
    /**
     * 标段切换
     * @param item
     * @param index
     */
    handleBidClick (item, index) {
      this.activeBid = index
    },
    init () {
      this.isQuery = this.$route.meta.type === 'query'
      // if (this.activeTab === '3') {
      //   this.activeTab = '2'
      // }
      this.activeTab = '1'
      this.getData()
    },
    getData () {
      let isSetRecord = 0
      if (Number(this.$route.query.status) === 1) {
        isSetRecord = 1
      }
      archivesProject.getArchivesInfo(this.$route.params.objectId, isSetRecord).then((res) => {
        if (res.data.resCode === '0000') {
          this.info = res.data.archivesProject
          this.info.archivesInfoList = this.info.archivesInfoList.filter(archivesInfo => Number(archivesInfo.transferStatus) !== 0)
          this.isShow = this.isShow && Number(this.info.status) === 1
        }
      })
    }
  },
  mounted () {
    this.init()
  }
}
</script>

<style lang="less">
  #archives-detail{
  }
</style>
