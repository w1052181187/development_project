/* eslint-disable */
/**
 * 浏览器存储实用工具类，用来保留一些状态数据，在浏览器刷新时不清空
 * 支持ie6 7 8 9 10 11
 */
let storage

const isUnderIE = (version) => {
  version = version ? parseFloat(version) : 8.0
  //不是IE
  if (navigator.appName.indexOf('Microsoft Internet Explorer') === -1) {
    return false
  }
  const ver = navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1]
  return parseFloat(ver) < version
};

//确定存储内核
if (isUnderIE(8)) {
  const UserData = (() => {
    let data, name
    const init = (n) => {
        if (!data) {
          name = n ? n : window.location.hostname;
          try {
            data = document.createElement('input')
            data.type = "hidden"
            data.style.display = "none"
            data.addBehavior("#default#userData")

            document.appendChild(data)
            const expires = new Date()
            expires.setDate(expires.getDate() + 365)
            data.expires = expires.toUTCString()
          } catch (e) {
            console.assert('userdata创建失败')
            return null
          }
          return this
        }
      },
      setItem = function (key, value) {
        data.load(name)
        data.setAttribute(key, value)
        data.save(name)
      },
      getItem = function (key) {
        data.load(name)
        return data.getAttribute(key)
      },
      removeItem = function (key) {
        data.load(name)
        data.removeAttribute(key)
        data.save(name)
      }

    return {
      init, setItem, getItem, removeItem
    }
  })()
  storage = UserData.init()
} else {
  storage = window.sessionStorage
}

//调用本地存储
const save = function (name, data) {
  name = String(name)
  console.assert(storage, '浏览器不支持本地存储!');
  if (arguments.length !== 2 || typeof name !== 'string' || data == null) {
    if (arguments.length === 1 && typeof name === 'object') {
      let key
      for (key in name) {
        if (key && name.hasOwnProperty(key)) {
          storage.setItem(key, name[key])
        }
      }
    }
    return;
  }
  storage.setItem(name, data)
},
get = function (name, handler) {
  let data = storage.getItem(name);
  handler = typeof handler === 'undefined' ? function () {
    data = data === 'true' ? true : (data === 'false' ? false : data)
  } : handler
  handler(data)
  return data
},
remove = function (name) {
  return this.get(name, function () {
    storage.removeItem(name)
  })
},
getAll = function () {

},
clear = function () {
  storage.clear()
}

export default {
  has: function (name) {
    return !!this.get(name)
  },
  //绑定对象
  save: save,
  //取出对象
  get: get,
  //取出js对象
  getObject: function (name, handler) {
    return typeof this.get(name, handler) === 'string' ? JSON.parse(this.get(name, handler)) : this.get(name, handler)
  },
  saveObject: function (name, data) {
    this.save(name, JSON.stringify(data))
  },
  //解绑对象（返回解绑的对象）
  remove: remove,
  //获取所有对象
  getAll: getAll,
  //清空所有对象
  clear: clear
}
