import request from '@/utils/axios/projectflow-axios'

export const projectLeader = {
  /**
   * 新增项目负责人接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return request({
      url: '/projectLeader-infos',
      method: 'post',
      data: data
    })
  },
  /**
   * 根据企业id获取部门的树形结构信息接口
   */
  getDeptsByEnterpriesId (enterpriesId) {
    return request({
      url: '/projectLeader-infos/' + enterpriesId,
      method: 'get'
    })
  },
  /**
   * 根据部门信息查询所有员工信息
   */
  getUsersByDept (query) {
    return request({
      url: '/projectLeader-infos/queryUsersByDept',
      method: 'post',
      data: query
    })
  },
  /**
   * 项目全貌：项目分配
   */
  getProjectViewList (query) {
    return request({
      url: '/projectLeader-infos',
      method: 'get',
      params: query
    })
  }
}
