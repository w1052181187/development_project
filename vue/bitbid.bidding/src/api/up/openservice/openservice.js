import request from '@/utils/axios/up-axios'

export const opens = {
  openList (query) {
    return request({
      url: '/module-managements/enterprise',
      method: 'get',
      params: query
    })
  }
}
