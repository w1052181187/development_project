$(document).ready(function () {
  jQuery(".promain").slide({});
  let timer = setInterval(function () {
    let roomInfor = window.parent.auctionRoom
    if(roomInfor&&roomInfor.start){
      clearInterval(timer)
      new AuctionRoom({
        roomInfor:roomInfor,
        parse: function(msgModel){
          let record = msgModel.record
          if (record) {
            pushRecord({persion:record.userName,price:record.offer,date: record.createDate})
          }
          let room = eval("("+msgModel.expands+")")
          if(room && room.extendTimes > project.delayTime){
            project.delayTime = room.extendTimes
          }
        }
      })
      $("#returnButton").click(roomInfor.quit)
      countdownHandler.putEvent(180,function () {
        systemHintHandler.pushOne("系统通知：距离本次拍卖结束时间还有3分钟，请大家抓紧时间。")
      })
      clearInterval(timer)
    }
  },100)
})
