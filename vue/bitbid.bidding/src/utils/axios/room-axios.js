import axios from './common/room-axios'

let path = process.env['ROOM_BASE_API']

axios.defaults.baseURL = process.env['ROOM_BASE_API']

export default {baseURL: path, request: axios}
