import axios from './common/up-axios'

axios.defaults.baseURL = process.env.ASSETS_API

export default axios
