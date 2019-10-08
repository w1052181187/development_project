<template>
  <div id="index">
    <el-row v-if="!$route.meta.isshow">
      <el-col :span="3" class="side_nave_left">
        <leftNav v-if="!$route.meta.isShowLeft"></leftNav>
      </el-col>
      <el-col :span="21" class="side_nave_right">
        <commonhead v-if="!$route.meta.isShowLeft"></commonhead>
        <router-view/>
        <commonfoot v-if="!$route.meta.isShowLeft"></commonfoot>
      </el-col>
    </el-row>
    <div v-else>
      <router-view/>
    </div>
  </div>
</template>

<script>
// 引入头部
import commonhead from '../components/common/header.vue'
import commonfoot from '../components/common/footer.vue'
import leftNav from '../components/common/side-nav.vue'
export default {
  data () {
    return {}
  },
  methods: {
  },
  // 组件
  components: {
    // 头部
    commonhead,
    commonfoot,
    leftNav
  }
}
</script>
