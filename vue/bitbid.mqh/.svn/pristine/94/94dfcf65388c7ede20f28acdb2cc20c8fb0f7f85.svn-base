export const WebRTC = function () {
  var getUserMedia = (navigator.mediaDevices.getUserMedia || navigator.getUserMedia)
  // const servers = {iceServers: [{urls: 'stun:10.100.6.205:3478', username: 'bitbid', credential: '0xe1e4012c45245edeee9db7eeb283444d'}]}
  const servers = {iceServers: [{urls: 'stun:192.168.1.14:3478', username: 'bitbid', credential: '0xe1e4012c45245edeee9db7eeb283444d'}]}
  /**********************************************************/
  /*                                                        */
  /*                       事件处理器                       */
  /*                                                        */
  /**********************************************************/
  function EventEmitter () {
    this.events = {}
  }
  // 绑定事件函数
  EventEmitter.prototype.on = function (eventName, callback) {
    this.events[eventName] = this.events[eventName] || []
    this.events[eventName].push(callback)
  }
  // 触发事件函数
  EventEmitter.prototype.emit = function (eventName, _) {
    var events = this.events[eventName],
      args = Array.prototype.slice.call(arguments, 1),
      i, m
    if (!events) {
      return
    }
    for (i = 0, m = events.length; i < m; i++) {
      events[i].apply(null, args)
    }
  }
  /** *****************基础部分*********************/
  function webrtc () {
    // 本地media stream
    this.localMediaStream = null
    // 所在房间
    this.roomId = null
    // 本地WebSocket连接
    this.websocket = null
    // 本地用户id
    this.userId = null

    this.enterpriseId = null
    // 保存所有与本地相连的peer connection， 键为socket id，值为PeerConnection类型
    this.peerConnections = {}
    // 初始时需要构建链接的数目
    this.numStreams = 0
    // 初始时已经连接的数目
    this.initializedStreams = 0
    // 保存所有的data channel，键为socket id，值通过PeerConnection实例的createChannel创建
    this.dataChannels = {}
    // room中抽签企业信息
    this.enterprises = []
    // 登陆者自己的peerConnection
    this.peer = null
    this.userType = null
  }
  // 继承自事件处理器，提供绑定事件和触发事件的功能
  webrtc.prototype = new EventEmitter()
  /**********************************************************/
  /*                                                        */
  /*                       信息初始化                       */
  /*                                                        */
  /**********************************************************/
  webrtc.prototype.setSettings = function (userId, enterpriseId, roomId, websocket, userType, enterprises) {
    this.userId = userId
    this.userType = userType
    this.enterpriseId = enterpriseId
    this.roomId = roomId
    this.websocket = websocket
    this.enterprises = enterprises
  }
  webrtc.prototype.init = function () {
    var socket = this.websocket,
      that = this
    this.on('_ice_candidate', function (data) {
      var candidate = new RTCIceCandidate(data.candidate)
      if (that.peerConnections != {} && that.peerConnections[data.socketId] != 'undefined' && that.peerConnections[data.socketId] != undefined) {
        var pc = that.peerConnections[data.socketId]
        pc.addIceCandidate(candidate).then(() => { handleConnectionSuccess(pc) })
          .catch((error) => { handleConnectionFailure(pc, error) })
      }
    })

    this.on('_new_peer', function (data) {
      if (that.userId != data) {
        var pc = that.createPeerConnection(data)
        if (that.localMediaStream) {
          pc.addStream(that.localMediaStream)
        }
      }
    })
    this.on('_offer', function (data) {
      if (that.peerConnections != {} && that.peerConnections[data.socketId] != 'undefined' && that.peerConnections[data.socketId] != undefined) {
        that.receiveOffer(data.socketId, data.sdp)
      }
    })
    this.on('_answer', function (data) {
      if (that.peerConnections != {} && that.peerConnections[data.socketId] != 'undefined' && that.peerConnections[data.socketId] != undefined) {
        that.receiveAnswer(data.socketId, data.sdp)
      }
    })
    this.on('ready', function() {
      that.createPeerConnections()
      that.addStreams()
      that.sendOffers()
    })
  }
  /** ***********************服务器连接部分***************************/
  /** ***********************流处理部分*******************************/
  // 创建本地流
  webrtc.prototype.createStream = function (options) {
    var that = this

    options.video = !!options.video
    options.audio = !!options.audio

    if (getUserMedia) {
      this.numStreams++
      navigator.mediaDevices.getUserMedia(options).then(
        function (stream) {
          that.localMediaStream = stream
          that.initializedStreams++
          that.emit('stream_created', stream)
          if (that.initializedStreams == that.numStreams) {
            that.emit('ready')
          }
        }
      ).catch(
        function (error) {
          console.info(error)
          that.emit('stream_create_error', error)
        }
      )
    } else {
      that.emit('stream_create_error', new Error('WebRTC is not yet supported in this browser.'))
    }
  }
  // 将本地流添加到所有的PeerConnection实例中
  webrtc.prototype.addStreams = function() {
    var i, m,
      stream,
      connection,
      that = this
    if (that.localMediaStream) {
      for (connection in that.peerConnections) {
        that.peerConnections[connection].addStream(that.localMediaStream)
      }
    }
  }

  // 将流绑定到video标签上用于输出
  webrtc.prototype.attachStream = function (stream, domId) {
    var element = document.getElementById(domId)
    element.srcObject = stream
  }
  /** *********************信令交换部分*******************************/
  // 向所有PeerConnection发送Offer类型信令
  webrtc.prototype.sendOffers = function () {
    var i, m,
      pc,
      that = this,
      pcCreateOfferCbGen = function (pc, socketId) {
        return function (session_desc) {
          pc.setLocalDescription(session_desc).then(() => { setLocalDescriptionSuccess(pc) }).catch(setSessionDescriptionError)
          that.websocket.websocket.send(JSON.stringify({
            'eventName': '_offer',
            'receiverId': socketId,
            'data': {
              'sdp': session_desc,
              'socketId': that.userId
            }
          }))
        }
      },
      pcCreateOfferErrorCb = function (error) {
        console.log(error)
      }
    for (i = 0, m = that.enterprises.length; i < m; i++) {
      pc = that.peerConnections[that.enterprises[i].userId]
      if (that.userId != that.enterprises[i].userId) {
        pc.createOffer(pcCreateOfferCbGen(pc, that.enterprises[i].userId), pcCreateOfferErrorCb)
      }
    }
  }
  // 接收到Offer类型信令后作为回应返回answer类型信令
  webrtc.prototype.receiveOffer = function (socketId, sdp) {
    var pc = this.peerConnections[socketId]
    this.sendAnswer(socketId, sdp)
  }

  // 发送answer类型信令
  webrtc.prototype.sendAnswer = function (socketId, sdp) {
    var that = this
    var pc = that.peerConnections[socketId]
    pc.setRemoteDescription(new RTCSessionDescription(sdp)).then(() => {setRemoteDescriptionSuccess(pc)}).catch(setSessionDescriptionError)
    pc.createAnswer(function (session_desc) {
      pc.setLocalDescription(session_desc).then(() => { setLocalDescriptionSuccess(pc) }).catch(setSessionDescriptionError)
      that.websocket.websocket.send(JSON.stringify({
        'eventName': '_answer',
        'receiverId': socketId,
        'data': {
          'socketId': that.userId,
          'sdp': session_desc
        }
      }))
    }, function(error) {
      console.info('接受offer信令报错')
      console.log(error)
    })
  }

  // 接收到answer类型信令后将对方的session描述写入PeerConnection中
  webrtc.prototype.receiveAnswer = function (socketId, sdp) {
    console.info("接受offer"+sdp)
    var that = this
    var pc = that.peerConnections[socketId]
    pc.setRemoteDescription(new RTCSessionDescription(sdp)).then(() => { setRemoteDescriptionSuccess(pc) }).catch(setSessionDescriptionError)
  }

  /** *********************点对点连接部分*****************************/

  // 创建与其他用户连接的PeerConnections
  webrtc.prototype.createPeerConnections = function () {
    var i, m,
      that = this
    for (i = 0, m = that.enterprises.length; i < m; i++) {
      if (that.userId != that.enterprises[i].userId) {
        that.createPeerConnection(that.enterprises[i].userId)
      }
    }
  }

  // 创建单个PeerConnection
  webrtc.prototype.createPeerConnection = function (socketId) {
    var that = this
    var pc = new RTCPeerConnection(servers)
    that.peerConnections[socketId] = pc
    pc.onicecandidate = function (evt) {
      if (evt.candidate) {
        that.websocket.websocket.send(JSON.stringify({
          'eventName': '_ice_candidate',
          'receiverId': socketId,
          'data': {
            'label': evt.candidate.sdpMLineIndex,
            'candidate': evt.candidate,
            'socketId': that.userId // 企业id作为主键

          }
        }))
      }
    }
    pc.onopen = function () {
      that.emit('pc_opened', socketId, pc)
    }
    pc.oniceconnectionstatechange = handleConnectionChange
    pc.onaddstream = function (evt) {
      that.emit('pc_add_stream', evt.stream, socketId, pc)
      let vieWarp = document.getElementById('videoCont')
      let div = document.createElement('div')
      div.setAttribute('class', 'el-row')
      div.setAttribute('id', 'row_' + socketId)
      div.innerHTML = '<div class="el-col el-col-24"><div class="video-img"><video id="other_' + socketId + '"  autoplay="autoplay" width="317" height="200"></video></div></div>'
      for (var i = 0; i < that.enterprises.length; i++) {
        var en = that.enterprises[i]
        if (en.userId == socketId && en.enterpriseType == 1) {
          let first = vieWarp.firstChild
          first.before(div)
        } else {
          vieWarp.appendChild(div)
        }
      }
      document.getElementById('other_' + socketId).srcObject = evt.stream
    }
    return pc
  }
  // 更改连接状态日志.
  var handleConnectionChange = function (event) {
    const peerConnection = event.target
    console.log('ICE state change event:', event)
  }
  // 连接成功日志
  var handleConnectionSuccess = function (peerConnection) {
    console.info(peerConnection)
  }
  // 连接失败日志
  var handleConnectionFailure = function (peerConnection, error) {
    console.info(error)
  }
  // 会话描述成功日志
  var setDescriptionSuccess = function (peerConnection, functionName) {
    console.info('会话连接成功' + peerConnection)
  }
  // 会话描述失败日志
  var setSessionDescriptionError = function (error) {
    console.info(error.toString())
  }
  // 本地sdp成功日志
  var setLocalDescriptionSuccess = function (peerConnection) {
    setDescriptionSuccess(peerConnection, 'setLocalDescription')
  }
  // 远程sdp成功日志
  var setRemoteDescriptionSuccess = function (peerConnection) {
    setDescriptionSuccess(peerConnection, 'setRemoteDescription')
  }
  // 关闭PeerConnection连接
  webrtc.prototype.closePeerConnection = function (pc) {
    if (!pc) return
    pc.close()
  }
  return new webrtc()
}
