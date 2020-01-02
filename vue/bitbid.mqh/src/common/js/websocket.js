import store from '@/store'
import SockJS from 'sockjs-client'
export const WebPushSocket = function () {
  let webPushSocket = {
    // collectionPath: 'zbxt.tcgc.com.cn',
    collectionPath: 'msop.bibenet.com',
    // collectionPath: '10.100.6.205:8091',
    // collectionPath: '192.168.1.14:8092',
    websocket: null,
    userId: '',
    enterpriseId: '',
    relatedId: '',
    heartCheck: null
  }
  let lockReconnect = false // 避免ws重复连接
  /**
   * 设置默认参数
   */
  webPushSocket.setSettings = function (userId, enterpriseId, relatedId) {
    this.userId = userId
    this.enterpriseId = enterpriseId
    this.relatedId = relatedId
  }

  webPushSocket.init = function () {
    connect()
    messageHandler()
    this.heartCheck = heartCheck
  }

  /**
   * websoket构造
   */
  let SocketAdapter = function (options) {
    if ('WebSocket' in window) {
      // websocket = new ReconnectingWebSocket(options.sokectConnectUrl,null,{debug: true, reconnectInterval: 3000});
      webPushSocket.websocket = new WebSocket(options.sokectConnectUrl)
      // } else if ('MozWebSocket' in window) {
      //   websocket = new MozWebSocket(options.sokectConnectUrl)
    } else {
      webPushSocket.websocket = new SockJS(options.sokectConnectHttp)
    }
  }

  /**
   * websoket链接
   */
  let connect = function () {
    let collectionParams = 'USERID=' + webPushSocket.userId + '&ENTID=' + webPushSocket.enterpriseId + '&DPID=' + '&RCID=' + webPushSocket.relatedId || ''
    let protocol = window.location.protocol.startsWith('https') ? 's' : ''
    SocketAdapter({
      sokectConnectUrl: 'ws' + protocol + '://' + webPushSocket.collectionPath + '/websocket.ws?' + collectionParams,
      sokectConnectHttp: 'http' + protocol + '://' + webPushSocket.collectionPath + '/websocket/sockjs.ws?' + collectionParams
    })
  }

  /**
   * websoket链接成功后的操作
   */
  let messageHandler = function () {
    // 连接成功
    webPushSocket.websocket.onopen = function (event) {
      // heartCheck.start() // 心跳检测重置
      console.log('成功' + event)
    }
    // 连接失败
    webPushSocket.websocket.onerror = function (event) {
      // reconnect()
      console.log('失败' + event)
    }
    // 连接关闭
    webPushSocket.websocket.onclose = function (event) {
      // reconnect()
      console.log('关闭' + event)
    }
    // 获取消息
    webPushSocket.websocket.onmessage = webPushSocket.websocket.onmessage || function (event) {
      // heartCheck.start() // 拿到任何消息都说明当前连接是正常的
      console.log('收到消息')
      console.log(JSON.parse(event.data))
      store.commit('SET_MESSAGES', JSON.parse(event.data))
    }
  }

  function reconnect () {
    if (lockReconnect) return
    lockReconnect = true
    setTimeout(function () { // 没连接上会一直重连，设置延迟避免请求过多
      webPushSocket.init()
      lockReconnect = false
    }, 2000)
  }

  /**
   * 心跳检测
   */
  let heartCheck = {
    timeout: 0, // 15s发一次心跳
    start: function () {
      setTimeout(function () {
        // 这里发送一个心跳，后端收到后，返回一个心跳消息，
        // onmessage拿到返回的心跳就说明连接正常
        webPushSocket.websocket.send(JSON.stringify({
          'eventName': 100000
        }))
      }, this.timeout)
    }
  }
  return webPushSocket
}
