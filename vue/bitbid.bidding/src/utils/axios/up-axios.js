import axios from './common/up-axios'

axios.defaults.baseURL = process.env.UP_BASE_API

export default axios
