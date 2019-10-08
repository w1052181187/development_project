var highRecord = {};

var recordTool = {}
recordTool.orderList = function (recordList) {
  for (var i=0;i<recordList.length;i++) {
    for (var j=i+1;j<recordList.length;j++) {
      if (recordList[i].recordId > recordList[j].recordId) {
        let a = recordList[j]
        recordList[j] = recordList[i]
        recordList[i] = a
      }
    }
  }
}

adfineObjectHandler(highRecord,'price',{
  set: function (newValue) {
    this._price = parseFloat(newValue)
    //修改快速报价信息
    var $quickBidderPriceA = $('#quickBidderPrice a')
    for(var i = 0;i<$quickBidderPriceA.length;i++){
      var $curA = $quickBidderPriceA.eq(i);
      $curA.html(formatePrice(floatAdd(this.price,project.bidIncreame*(i+1))))
    }
    //修改最高价展示信息
    $('#highRecord').find('.money').html('<font>'+formatePrice(this.price)+'</font>万元')
  },
  get: function () {
    return this._price
  }
})

adfineObjectHandler(highRecord,'persion',{
  set: function (newValue) {
    this._persion = newValue
    $('#highRecord').find('i').html(this.persion)
    if (this.persion == user.number) {
      offerHandler.stop()
      systemHintHandler.offer(true,formatePrice(this.price))
    } else {
      offerHandler.start()
      systemHintHandler.offer(false,formatePrice(this.price))
    }
  },
  get: function () {
    return this._persion
  }
})

var pushRecords = function (recordList) {
  recordTool.orderList(recordList)
  for(var i=0;i<recordList.length;i++){
    let cur = recordList[i]
    pushRecord({persion:cur.userName,price:cur.offer,date: cur.createDate})
  }
}

var pushRecord = function (record) {
  record.price = parseFloat(record.price)
  let curDate = typeof record.date == 'string'?record.date:formateDate(record.date)
  highRecord.price = record.price
  highRecord.persion = record.persion
  $('#recordList').find('li').eq(0).find('b').remove()
  $('#recordList ul').prepend('<li>\n' +
    '              <p><span>'+record.persion+'</span>进行了一次举牌   <em>'+curDate+'</em> <b>领先</b><i>￥'+formatePrice(record.price)+'</i></p>\n' +
    '            </li>')
  if (record.persion==user.number) {
    $('#myRecordList').prepend('<tr>\n' +
      '            <td>'+formatePrice(record.price)+'</td>\n' +
      '            <td>'+curDate+'</td>\n' +
      '          </tr>')
  }
}
