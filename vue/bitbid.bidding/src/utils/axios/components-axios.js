import axios from './common/old-axios'

axios.defaults.baseURL = process.env.COMPONENTS_BASE_API

export default axios
