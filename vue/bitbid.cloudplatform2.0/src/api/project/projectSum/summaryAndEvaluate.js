import request from '@/utils/axios/projectflow-axios'

export const summaryAndEvaluate = {
  /**
   * 新增项目总结和评价信息接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return request({
      url: '/summaryAndEvaluate-infos',
      method: 'post',
      data: data
    })
  }
}
