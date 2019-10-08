import axios from './common/rp-axios'

axios.defaults.baseURL = process.env.COMPONENTS_BASE_API

export default axios
