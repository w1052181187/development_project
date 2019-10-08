var AuctionRoom = function(options) {
  this.curUserId = options.roomInfor.userId
  this.curRoomId = options.roomInfor.roomId
  this.curRelatedCode = options.roomInfor.projectCode != null ? options.roomInfor.projectCode : '00000'
  this.collectionPath = options.roomInfor.roomPath.substring(options.roomInfor.roomPath.indexOf("://")+3)
  this.websocketOpenFlag = false

  let collectionParams = "/"+this.curRoomId+"/"+this.curUserId+"/"+this.curRelatedCode + "?_date=" + new Date().getTime()
  let protocol = (window.location.protocol.indexOf("https")>-1) ? 's' : ''
  let sokectConnectUrl = "ws"+ protocol + "://" + this.collectionPath + "websocket" + collectionParams
  let sokectConnectHttp = "http"+ protocol + "://" + this.collectionPath + "websocket/sockjs" +  collectionParams
  let auctionInfo = "http"+ protocol + "://" + this.collectionPath + "api/v1/room/info/" + this.curRoomId + "/" + this.curUserId
  let sendInfo = "http"+ protocol + "://" + this.collectionPath + "api/v1/room/bid"
  let timeInfo = "http"+ protocol + "://" + this.collectionPath + "api/v1/room/browserTime/" + this.curRoomId
  project.auctionType = options.roomInfor.auctionStatus
  //初始化数据
  $.ajax({
    url: auctionInfo + "?_date=" + new Date().getTime(),
    type: "GET",
    dataType: "json", //指定服务器返回的数据类型
    success: function (data) {
      if (data.resCode != '0000') {
        console.log(data.resMessage)
        window.parent.auctionRoom.quit()
        return;
      }
      let sectionInfo = eval("("+data.roomInfo.expand+")")
      let room = data.roomInfo
      project.name = sectionInfo.sectionName
      project.startTime = parseDate(room.beginTime)
      project.autoExtendTime = room.autoDelay
      project.bidIncreame = room.priceIncrease
      project.delayTime = data.systemRoom.extendTimes
      let endDate = new Date()

      if(room.auctionType == 1||room.auctionType == 3){
        let endTimeLong = project.startTime.getTime() + room.duration*60*1000
        endDate.setTime(endTimeLong)
      }else{
        endDate = parseDate(room.endTime)
      }
      if (project.delayTime > 0) {
        endDate.setTime(endDate.getTime() + project.delayTime * room.autoDelay * 60 * 1000)
      }
      project.planEndTime = endDate
      project.floorPrice = room.startingPrice

      project.relatedProject = {
        name: sectionInfo.sectionName,
        code: sectionInfo.sectionNumber,
        type: sectionInfo.type == 1?'探矿权':'采矿权',
        minerals: getSectionMinerals(sectionInfo),
        area: sectionInfo.area,
        year: sectionInfo.ageLimit,
        location: getSectionLocation(sectionInfo) + sectionInfo.miningLocation,
        coordinate: sectionInfo.areaCoordinates
      }

      if (data.currentMember) {
        user.name = data.currentMember.userName
        user.number = data.currentMember.userAuctionCode
        user.memberId = data.currentMember.memberId
        options.roomInfor.memberId = data.currentMember.memberId
      }

      pushRecords(data.recordList)

      if (options.callBack) {
        options.callBack({auctionType: room.auctionType})
      }
    }
  })

  var getSectionMinerals = function (sectionInfo) {
    for(let key in options.roomInfor.mineTypes){
      let curType = options.roomInfor.mineTypes[key]
      if(curType.value == sectionInfo.minerals){
        return curType.label
      }
    }
  }

  var getSectionLocation = function (sectionInfo) {
    let locationValue = ''
    for(let pKey in options.roomInfor.citydata.CityInfo){
      let curp = options.roomInfor.citydata.CityInfo[pKey]
      if(curp.value == sectionInfo.provinceId){
        locationValue = curp.label
        for(let cKey in curp.children){
          let curC = curp.children[cKey]
          if(curC.value == sectionInfo.cityId){
            locationValue += curC.label
            for(let coKey in curC.children){
              let curCo = curC.children[coKey]
              if(curCo.value == sectionInfo.countyId){
                locationValue += curCo.label
                return locationValue
              }
            }
          }
        }
      }
    }
    return locationValue
  }

  //设置webSocket
  let websocket = {}

  let that = this
  this.timer = setInterval(function(){
    if(that.websocketOpenFlag){
      clearInterval(that.timer)
    }else{
      try {
        if ('WebSocket' in window) {
          websocket = new WebSocket(sokectConnectUrl);
        } else if ('MozWebSocket' in window) {
          websocket = new MozWebSocket(sokectConnectUrl);
        } else {
          websocket = new SockJS(sokectConnectHttp);
        }
      } catch (error) {
        console.log(error)
        clearInterval(that.timer)
      }
      websocket.onopen = function (event) {}
      websocket.onerror = function (event) {}
      websocket.onclose = function (event) {}
      websocket.onmessage = function (event) {
        that.websocketParse(event)
      }
    }
  },100)

  this.websocketParse = function(event){
    let msgModel = JSON.parse(event.data);
    if(!this.websocketOpenFlag&&msgModel.onlineUsers){
      for(var key in msgModel.onlineUsers){
        if(options.roomInfor.userId == msgModel.onlineUsers[key]){
          this.websocketOpenFlag = true
          offerHandler.run()
          break
        }
      }
    }
    if(options.parse !=undefined && typeof (options.parse) == 'function') {
      options.parse(msgModel)
    }
  }

  //设置出价
  offerHandler.setClick(function () {
    let value = $(this).html()
    while (value.indexOf(',')>-1) {
      value = value.replace(",",'')
    }
    $.post(sendInfo,{
      roomId: options.roomInfor.roomId,
      memberId: user.memberId,
      userId: options.roomInfor.userId,
      userName: user.number,
      offer: value
    },function(data){
      if(data.resCode != "0000"){
        alert(data.resMessage)
      }
    })
  },
  function () {
    let value = $(this).prev().val()
    if(!/(^[1-9]\d*(\.\d{1,6})?$)|(^0(\.\d{1,6})?$)/.test(value)){
      offerHandler.showHint(1)
    }else if(parseFloat(value) <= highRecord.price){
      offerHandler.showHint(2)
    }else if(((parseFloat(value) - (highRecord.price?highRecord.price:0)) % project.bidIncreame) != 0){
      offerHandler.showHint(3)
    }else{
      $.post(sendInfo,{
        roomId: options.roomInfor.roomId,
        memberId: user.memberId,
        userId: options.roomInfor.userId,
        userName: user.number,
        offer: value
      },function(data){
        if(data.resCode != "0000"){
          alert(data.resMessage)
        }
      })
    }
  })

  //设置更新时间
  let timer = setInterval(function () {
    $.get(timeInfo + "?_date=" + new Date().getTime(),function (data) {
      if (data.resCode == '0000') {
        unifyNow.setNow(data.browserTime)
      } else {
        console.log(data.resMessage)
      }
    })
  },30000)
}
