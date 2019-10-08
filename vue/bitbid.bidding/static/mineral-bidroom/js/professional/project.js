var project = {}

Object.defineProperty(project,'auctionType',{
  set:function (newValue) {
    this._auctionType = parseInt(newValue)
    let $startTime = $("#startTime")
    let $planEndTime = $("#planEndTime")
    let $autoExtendTime = $("#autoExtendTime")
    if (this.auctionType <= 2) {
    } else if (this.auctionType <= 5) {
      $startTime.find('span').html('挂牌开始时间')
      $planEndTime.find('span').html('挂牌结束时间')
    } else {
      $startTime.find('span').html('限时竞价开始时间')
      $planEndTime.find('span').html('限时竞价结束时间')
    }
  },
  get: function () {
    if(!this._auctionType){
      this.auctionType = 1
    }
    return this._auctionType
  }
})

Object.defineProperty(project,'name',{
  set:function (newValue) {
    this._name = newValue
    if (this.auctionType <= 2) {
      $('#name').html('当前拍卖竞价室：'+this.name)
    } else if (this.auctionType <= 5) {
      $('#name').html(this.name + '  挂牌竞价室')
    } else {
      $('#name').html(this.name + '  限时竞价室')
    }
  },
  get: function () {
    return this._name
  }
})

Object.defineProperty(project,'startTime',{
  set:function (newValue) {
    this._startTime = newValue
    $('#startTime').find('em').html(formateDate(this.startTime))
  },
  get: function () {
    return this._startTime
  }
})

Object.defineProperty(project,'planEndTime',{
  set:function (newValue) {
    this._planEndTime = newValue
    $('#planEndTime').find('em').html((this.delayTime>0?'<font>延时'+this.delayTime+'次</font>':'')+formateDate(this.planEndTime))
    countdownHandler.start(this.planEndTime)
  },
  get: function () {
    return this._planEndTime
  }
})

Object.defineProperty(project,'delayTime',{
  set:function (newValue) {
    this._delayTime = newValue
    if(this.delayTime > 0 && this.planEndTime){
      let time = this.planEndTime.getTime()
      let newEndTime = time + this.autoExtendTime*60*1000
      let newEndDate = new Date()
      newEndDate.setTime(newEndTime)
      this.planEndTime = newEndDate
    }
  },
  get: function () {
    return this._delayTime
  }
})

Object.defineProperty(project,'autoExtendTime',{
  set:function (newValue) {
    this._autoExtendTime = newValue
    $('#autoExtendTime').find('em').html(this.autoExtendTime+'分钟')
  },
  get: function () {
    return this._autoExtendTime
  }
})

Object.defineProperty(project,'bidIncreame',{
  set:function (newValue) {
    this._bidIncreame = parseFloat(newValue)
    $('#bidIncreame').find('em').html(this.bidIncreame+'万元')
    $('#redTipMul').html('加价幅度为'+this.bidIncreame+'万元或'+this.bidIncreame+'万元的整数倍')
    initQuickBidderPrice(this.bidIncreame,this.floorPrice)
  },
  get: function () {
    return this._bidIncreame
  }
})

Object.defineProperty(project,'floorPrice',{
  set:function (newValue) {
    this._floorPrice = parseFloat(newValue)
    $('#floorPrice').find('em').html(this.floorPrice+'万元')
    initQuickBidderPrice(this.bidIncreame,this.floorPrice)
  },
  get: function () {
    return this._floorPrice
  }
})

Object.defineProperty(project,'relatedProject',{
  set:function (newValue) {
    this._relatedProject = newValue
    for(key in this.relatedProject){
      $("#relatedP_"+key).html(this.relatedProject[key])
    }
  },
  get: function () {
    return this._relatedProject
  }
})

var initQuickBidderPrice = function (bidIncreame,floorPrice) {
  if((!isNaN(bidIncreame))&&(!isNaN(floorPrice))){
    var $quickBidderPriceA = $('#quickBidderPrice a')
    for(var i = 0;i<$quickBidderPriceA.length;i++){
      var $curA = $quickBidderPriceA.eq(i);
      $curA.html(formatePrice(floorPrice+bidIncreame*(i+1)))
    }
  }
}
