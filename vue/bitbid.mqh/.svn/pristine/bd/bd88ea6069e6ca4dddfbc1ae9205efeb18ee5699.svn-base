<template>
  <div class="news-details" id="news-details">
    <div class="workdesk-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">当前位置:首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/news-details' }">行业资讯详情</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="news-form">
      <h3 class="news-title">{{newsForm.title}}</h3>
      <div class="news-time">
        <span>发布时间：</span><span>{{newsForm.publishedTime | filterDate}}</span>
      </div>
      <div class="news-cont" v-html="newsForm.content">
      </div>
    </div>
  </div>
</template>
<script>
import { industry } from 'api/index'
import {formatDate} from 'common/js/date'
export default {
  name: 'news-list',
  data () {
    return {
      newsForm: {}
    }
  },
  filters: {
    filterDate (value) {
      if (value) {
        let date = new Date(value)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return null
      }
    }
  },
  // 实例创建
  created () {
    if (this.$route.query && this.$route.query.id) {
      industry.selectOne(this.$route.query.id).then((res) => {
        if (res.data.resCode === '0000') {
          this.newsForm = res.data.industryNews
        }
      })
    }
  },
  methods: {
  }
}
</script>
<style lang="less">
  #news-details {
    width: 1200px;
    margin: auto;
    margin-top: 80px;
    .workdesk-mqh-tit {
      padding: 10px 0;
    }
    .news-form {
      background: #fff;
      min-height: 500px;
      padding: 15px;
      .news-title {
        margin-top: 0;
      }
      .news-time {
        color: #999;
      }
      .news-cont {
        margin-top: 20px;
      }
    }
  }
</style>
