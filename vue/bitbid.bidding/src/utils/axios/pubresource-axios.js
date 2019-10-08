import axios from './common/rp-axios'

axios.defaults.baseURL = process.env.PUBRESOURCE_BASE_API

export default axios
