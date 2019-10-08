import request from '@/utils/axios/components-axios'

export const billPushData = {
  // 项目
  programList (params) {
    return request({
      url: '/comptend/program',
      method: 'get',
      params: params
    })
  },
  // 保存
  save (program) {
    return request({
      url: '/comptend/program',
      method: 'post',
      data: program
    })
  },
  // 更新
  update (program) {
    return request({
      url: '/comptend/program',
      method: 'put',
      data: program
    })
  },
  // 查看
  detail (objectId) {
    return request({
      url: '/comptend/program/' + objectId,
      method: 'get'
    })
  },
  // 删除
  delete (objectId) {
    return request({
      url: '/comptend/program/' + objectId,
      method: 'delete'
    })
  },
  // 保存到本地
  saveFile (code) {
    return request({
      url: '/comptend/project/localFile/' + code,
      method: 'get'
    })
  },
  // 检查
  check (objectId) {
    return request({
      url: '/comptend/project/examine/' + objectId,
      method: 'get'
    })
  },
  // 获取招标信息
  getItemInfor (objectId) {
    return request({
      url: '/comptend/project/itemInfor/' + objectId,
      method: 'get'
    })
  },
  // 更新招标信息
  updateItemInfor (params) {
    return request({
      url: '/comptend/project/itemInfor',
      method: 'post',
      data: params
    })
  },
  // 查看项目详情
  projectDetail (objectId) {
    return request({
      url: '/comptend/project/' + objectId,
      method: 'get'
    })
  },
  // 更新招标信息
  updateprojectDetail (params) {
    return request({
      url: '/comptend/project',
      method: 'post',
      data: params
    })
  },
  // 批量下载Excels
  downloadAllExcels (params, id, type) {
    return request({
      url: 'comptend/project/download/store/' + type + '/' + id,
      method: 'post',
      data: params
    })
  }
}
