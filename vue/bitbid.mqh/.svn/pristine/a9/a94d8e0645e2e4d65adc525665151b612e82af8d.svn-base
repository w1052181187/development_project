import ajax from '@/common/js/ajax'

export const systemInfo = {
  query: () => {
    return ajax({
      url: '/system-infos',
      method: 'get'
    })
  },
  update: (data) => {
    return ajax({
      url: '/system-infos',
      method: 'post',
      data: data
    })
  }
}
