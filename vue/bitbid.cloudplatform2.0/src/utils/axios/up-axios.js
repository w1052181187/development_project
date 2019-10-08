import axios from './common/userprofile-axios'

axios.defaults.baseURL = process.env.UP_BASE_API

export default axios
