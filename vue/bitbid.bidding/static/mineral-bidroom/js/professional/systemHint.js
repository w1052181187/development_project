var systemHintHandler = function () {
  var systemHints = [],$systemHint,$offerPrice

  var setSystemHint = function(newValue){
    systemHints = newValue
    write()
  }

  var pushOne = function(systemHint){
    systemHints.push(systemHint)
    write(systemHint)
  }

  var offer = function (isMe,price) {
    let message
    if(isMe){
      message = '系统提示：您现在是最高报价人'
    }else{
      message = '系统提示：您的出价已被超越，当前最高价为'+price+'万元。'
    }
    $offerPrice.html(message)
    $offerPrice.attr('title',message)
  }

  var write = function(systemHint){
    if(systemHint){
      writeOne(systemHint)
    }else{
      for(var i = 0;i<systemHints.length;i++){
        writeOne(systemHints[i])
      }
    }
  }

  var writeOne = function (systemHint) {
    $systemHint.append('<li title="'+systemHint+'">'+systemHint+'</li>')
  }

  $(document).ready(function () {
    $systemHint = $('#systemHint ul')
    $offerPrice = $('#offerPrice')
  })

  return{
    setSystemHint:setSystemHint,
    pushOne:pushOne,
    offer:offer
  }
}()
