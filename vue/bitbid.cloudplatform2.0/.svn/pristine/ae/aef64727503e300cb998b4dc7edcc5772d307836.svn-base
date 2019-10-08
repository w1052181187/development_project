<template>
  <div class="cloudcontent" id="archives-detail">
    <div class="main">
      <el-tabs class="detail_tab" v-model="activeTab" type="card">
        <el-tab-pane label="档案室" name="1">
          <archives-room></archives-room>
        </el-tab-pane>
        <el-tab-pane label="档案列表" name="2">
          <list></list>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
// import {archivesInfo} from '@/api/archives'
export default {
  components: {
    archivesRoom: resolve => require(['./archivesRoom.vue'], resolve),
    list: resolve => require(['./list'], resolve)
  },
  data () {
    return {
      activeTab: '1',
      info: {}
    }
  },
  watch: {
    '$route': 'init'
  },
  methods: {
    init () {
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
