import checkauction from '../auction/check-auction-bids'
import biddingrecords from '../auction/auction-bidding-records'
export default [
  ...checkauction,
  ...biddingrecords
]
