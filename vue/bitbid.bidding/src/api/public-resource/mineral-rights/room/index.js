import roomInfo from '@/utils/axios/room-axios'

export const room = {
  open (roomId) {
    return roomInfo.request({
      url: '/api/v1/room/open/' + roomId,
      method: 'post'
    })
  },
  getBase () {
    return roomInfo.baseURL
  },
  queryList (params) {
    return roomInfo.request({
      url: '/api/v1/syncRoomData/record/' + params.roomId,
      method: 'get',
      params: params
    })
  }
}
