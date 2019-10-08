<template>
  <div class="cloudcontent" id="cloud_ruleset">
    <p class="title">{{pageTitle}}规则设置</p>
    <set-number :detailInfo="(Number(this.$route.params.type) === 1 || Number(this.$route.params.type) === 2) ? simpleTypeDetailInfo : projectTypeDetailInfo" :numberType="this.$route.params.type"></set-number>
  </div>
</template>
<script>
import SetNumber from './setNumber'
export default {
  components: {
    SetNumber
  },
  data () {
    return {
      pageTitle: '',
      simpleTypeDetailInfo: [
        {
          name: '年度YYYY',
          example: '例如：2019'
        },
        {
          name: '月份MM',
          example: '例如：01'
        },
        {
          name: '日期DD',
          example: '例如：01'
        },
        {
          name: '流水号',
          example: '例如：5位流水号  00001'
        }
      ],
      projectTypeDetailInfo: [
        {
          name: '年度YYYY',
          example: '例如：2019'
        },
        {
          name: '月份MM',
          example: '例如：01'
        },
        {
          name: '日期DD',
          example: '例如：01'
        },
        {
          name: '资质主体文字',
          example: '例如：远东'
        },
        {
          name: '资质主体首字母',
          example: '例如：“远东”的首字母 YD'
        },
        {
          name: '流水号',
          example: '例如：5位流水号  00001'
        }
      ]
    }
  },
  methods: {
    setPageTitle () {
      switch (Number(this.$route.params.type)) {
        case 1 :
          this.pageTitle = '客户编号'
          break
        case 2 :
          this.pageTitle = '商机编号'
          break
        case 3 :
          this.pageTitle = '项目编号'
          break
      }
    }
  },
  mounted () {
    this.setPageTitle()
  }
}
</script>

<style lang="less">
  #cloud_ruleset{
    padding: 20px;
    p.title{
      padding-left: 12px;
      border-left: 4px solid #4a8cea;
      color: #000000;
      margin-bottom: 20px;
    }
  }
</style>
