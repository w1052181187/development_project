import request from '@/utils/axios'

export const leftMenu = {
  // 获取菜单栏列表(根据用户id、项目id)
  getMenuList (userId, projectId) {
    return request({
      url: '/resource/by-user/' + userId + '/' + projectId,
      method: 'get'
    })
  }
}
