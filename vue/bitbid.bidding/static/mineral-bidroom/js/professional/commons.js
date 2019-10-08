function formateDate(date){
  let mouth = date.getMonth()+1
  let day = date.getDate()
  let hour = date.getHours()
  let minute = date.getMinutes()
  let second = date.getSeconds()
  mouth = ('00' + mouth).substring((''+mouth).length)
  day = ('00' + day).substring((''+day).length)
  hour = ('00' + hour).substring((''+hour).length)
  minute = ('00' + minute).substring((''+minute).length)
  second = ('00' + second).substring((''+second).length)
  return date.getFullYear()+'-'+mouth+'-'+day+' '+hour+':'+minute+':'+second
}

function formatePrice(price){
  price = ''+price
  var integer = ''
  var decimals = ''
  var index = price.indexOf('.')
  if (index>-1) {
    integer = price.substring(0,index)
    decimals = price.substring(index+1)
  } else {
    integer = price
  }
  integer = integer.split('').reverse()
  var result = []
  for(var i=0;i<integer.length;i++){
    result.push(integer[i])
    if (i!=(integer.length-1)&&i%3==2) {
      result.push(',')
    }
  }
  integer = result.reverse().join('')
  return integer+(decimals.length>0?('.'+decimals):'')
}

var adfineObjectHandler = Object.defineProperty

var countdownHandler = function(){
  let timeEvents = []

  let stop = function () {
    if(window.parent.auctionRoom){
      window.parent.auctionRoom.stop()
    }
  }

  let start = function (date) {
    if(date.getTime()<new Date().getTime()){
      stop()
    }else{
      $('.time').countdown(date, new Date(), function(event) {
        $(this).html(event.strftime('<em>%D</em> 天 <em>%H</em> 时 <em>%M</em> 分 <em>%S</em> 秒 '));
        for(let key in timeEvents){
          let curEvent = timeEvents[key]
          if(!curEvent.taskRunning && curEvent.judge(event)){
            curEvent.taskRunning = true
            curEvent.call()
          }
        }
        if(event.type == 'finish'){
          stop()
        }
      });
    }
  }

  let putEvent = function (long,fun1,fun2) {
    if (long != null) {
      timeEvents.push({judge:function (event) {
        return long == event.totalSecsLeft
      },call:fun1})
    } else {
      timeEvents.push({judge:fun1,call:fun2})
    }
  }

  return {
    start:start,
    putEvent:putEvent
  }
}()

var parseDate = function(date){
  return new Date(date.substring(0, 4), parseInt(date.substring(5, 7)) - 1, date.substring(8, 10), date.substring(11, 13), date.substring(14, 16), date.substring(17))
}

var offerHandler = function () {
  var EMPTY_FUNC = function () {
    alert("empty func")
  }

  var manage = {}//运行期管理器

  adfineObjectHandler(manage,'using',{
    set:function (newVal) {
      this._using = newVal
      this.updateUsing()
    },
    get:function () {
      return this._using
    }
  })

  adfineObjectHandler(manage,'running',{
    set:function (newVal) {
      this._running = newVal
      this.updateUsing()
    },
    get:function () {
      return this._running
    }
  })

  /*更新运行状态*/
  manage.updateUsing = function () {
    if (this.running) {
      if (this.using) {
        $a.closest('p').removeClass("gray")
        $a.unbind().click(aClick)
        $button.removeClass("gray").unbind().click(inputClick)
        $text.removeAttr("disabled")
      } else {
        $a.closest('p').addClass("gray")
        $a.unbind()
        $button.addClass("gray").unbind()
        $text.attr("disabled","disabled")
      }
    }
  }

  var aClick = EMPTY_FUNC,inputClick = EMPTY_FUNC
  var $a, $text, $button, $hintF, $hintV, $hintM

  var setClick = function (func1,func2) {
    aClick = func1
    inputClick = func2
  }

  var start = function () {
    manage.using = true
  }

  var stop = function () {
    manage.using = false
  }

  var run = function(){
    manage.running = true
  }

  var showHint = function (val) {
    switch (val) {
      case 1:$hintF.show();break
      case 2:$hintV.show();break
      case 3:$hintM.show();break
    }
  }

  var init = function () {
    manage.using = true
    $(document).ready(function () {
      $a = $("#quickBidderPrice a")
      $text = $("#manualBidderPrice :text")
      $button = $("#manualBidderPrice :button")
      $hintF = $("#redTipFormat")
      $hintV = $("#redTipValue")
      $hintM = $("#redTipMul")

      $text.focus(function () {
        $(this).val('')
        $hintF.hide()
        $hintV.hide()
        $hintM.hide()
      })
    })
    return this
  }

  return {
    setClick:setClick,
    start:start,
    stop:stop,
    init:init,
    showHint:showHint,
    run:run
  }
}().init()

var unifyNow = function () {
  var now = new Date()

  var timer = setInterval(function () {
    var nowLong = now.getTime() + 1000
    var newNow = new Date()
    newNow.setTime(nowLong)
    now = newNow
  },1000)

  var setNow = function (timeLong) {
    if(timeLong){
      now.setTime(timeLong)
    }
  }

  var getNow = function () {
    return now
  }
  return {
    getNow:getNow,
    setNow:setNow
  }
}()

function floatAdd(num1,num2){
  var r1,r2,m,n;
  try{r1=num1.toString().split(".")[1].length}catch(e){r1=0}
  try{r2=num2.toString().split(".")[1].length}catch(e){r2=0}
  m = Math.pow(10,Math.max(r1,r2));
  n = (r1>=r2)?r1:r2;
  return ((num1*m + num2*m)/m).toFixed(n);
}
