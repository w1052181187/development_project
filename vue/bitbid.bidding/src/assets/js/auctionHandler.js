import * as citydata from '@/assets/js/citydata'
import {mineTypes} from '@/assets/js/mineral/mineTypes'

const factory = {}

let UserJobType = {
  'auctor': 0,
  'bidder': 1
}

let AuctionType = {
  'auction': 1,
  'hang': 2,
  'limitAuction':3
}

let IsApplyType = {
  'null': 0,
  'no': 1,
  'yes': 2
}

/*显示参与者在当前项目中的状态*/
let AuctionStatusType = {
  'auctionReady': 0,
  'auctionGoing': 1,
  'auctionOver': 2,
  'hangReady': 3,
  'hangGoing': 4,
  'hangOver': 5,
  'limitAuctionReady': 6,
  'limitAuctionGoing': 7,
  'limitAuctionOver':8
}

let SectionPricestatusType = {
  'auctionReady': 0,
  'auctionGoing': 1,
  'auctionOver': 2,
  'hangReady': 0,
  'hangGoing': 1,
  'hangOver': 2,
  'limitAuctionReady': 3,
  'limitAuctionGoing': 4,
  'limitAuctionOver':5
}

const AuctionHandler = function (params) {
  this.auctionType = params.auctionType
  this.userJob = params.userJob
  this.isApply = params.isApply
  this.relatedCode = params.section.code
  this.roomId = params.section.roomId
  this.sectionPricestatus = params.section.pricestatus
  if (params.section.announcement) {
    this.startTime = params.section.announcement.hangStartTime
    this.endTime = params.section.announcement.hangEndTime
    this.limitStartTime = params.section.limitBiddingStartTime
  }
}

Object.assign(AuctionHandler.prototype,{
  getAuctionStatus () {
    if (this.auctionStatus) {
      return this.auctionStatus
    } else {
      let auctionStatus
      if (this.auctionType == AuctionType['auction']) {//拍卖
        auctionStatus =  this.sectionPricestatus
      } else if (this.auctionType == AuctionType['hang']) {//挂牌
        if (this.userJob == UserJobType['auctor']) {//拍卖人
          auctionStatus = this.sectionPricestatus + 3
        } else {//竞价人
          if (this.sectionPricestatus == SectionPricestatusType['hangReady']) {
            auctionStatus = this.sectionPricestatus + 3
          } else if (this.sectionPricestatus == SectionPricestatusType['hangGoing']) {
            if (this.isApply == IsApplyType['null']) {
              auctionStatus = AuctionStatusType['hangGoing']
            } else if (this.isApply == IsApplyType['no']) {
              auctionStatus = AuctionStatusType['hangOver']
            } else {
              auctionStatus = AuctionStatusType['limitAuctionReady']
            }
          } else {
            if (this.isApply <= IsApplyType['no']) {
              auctionStatus = AuctionStatusType['hangOver']
            } else {
              auctionStatus = this.sectionPricestatus + 3
            }
          }
        }
      }
      this.auctionStatus = auctionStatus
      return this.auctionStatus
    }
  },
  getStartTime () {
    if (this.auctionType == AuctionType['auction']) {
      return this.startTime
    } else {
      if (this.getAuctionStatus() > AuctionStatusType['hangGoing']) {
        return this.limitStartTime
      } else {
        return this.startTime
      }
    }
  },
  getAuctionStatusStr () {
    return ['未开始','竞拍中','已结束','挂牌未开始','挂牌竞价中','挂牌已结束','限时竞价未开始','限时竞价中','限时竞价已结束'][this.getAuctionStatus()]
  },
  getHangStartTime () {
    return this.startTime
  },
  getHangEndTime () {
    return this.endTime
  },
  getLimitStartTime () {
    return this.limitStartTime
  },
  getRoomId () {
    if (this.auctionType == AuctionType['auction']) {
      return this.roomId
    } else {
      let auctionStatus = this.getAuctionStatus()
      if (auctionStatus <= AuctionStatusType['hangGoing']) {
        return this.roomId.split(',')[0]
      } else {
        return this.roomId.split(',')[1]
      }
    }
  },
  initDataStore (options) {
    if (window.auctionRoom) {
      delete window.auctionRoom
    }
    let result = {}
    result.roomId = this.getRoomId()
    result.startTime = this.getStartTime()
    result.projectCode = this.relatedCode
    result.start = false
    result.mineTypes = mineTypes
    result.citydata = citydata
    result.auctionStatus = this.getAuctionStatus()
    Object.assign(result,options)
    window.auctionRoom = result
  }
})

factory.create = (params) => {
  return new AuctionHandler(params)
}

factory.UserJobType = UserJobType
factory.AuctionType = AuctionType

export{factory}
