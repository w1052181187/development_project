var user = {}

adfineObjectHandler(user,'name',{
  set:function (newValue) {
    this._name = newValue
    $('#bidderName').html(this.name)
  },
  get:function () {
    return this._name
  }
})

adfineObjectHandler(user,'number',{
  set:function (newValue) {
    this._number = newValue
    $('#bidderNumber').html(this.number)
  },
  get:function () {
    return this._number
  }
})
