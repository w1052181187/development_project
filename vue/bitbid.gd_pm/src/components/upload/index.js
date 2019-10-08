import vueUploadWeb from './uploadWeb'

const uploadWeb = {
  install: Vue => Vue.component(vueUploadWeb.name, vueUploadWeb)
}

if (typeof window !== 'undefined' && window.Vue) {
  window.Vue.use(uploadWeb)
}

export default uploadWeb
