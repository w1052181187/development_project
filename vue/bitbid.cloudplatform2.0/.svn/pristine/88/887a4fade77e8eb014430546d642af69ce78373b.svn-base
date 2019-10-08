import request from '@/utils/axios/components-axios'

export const comment = {
  /**
   * 查询评论列表接口
   */
  getList (query) {
    return request({
      url: '/comment',
      method: 'get',
      params: query
    })
  },
  /**
   * 新增/修改评论接口
   */
  update (data) {
    return request({
      url: '/comment',
      method: 'put',
      data: data
    })
  }
}
