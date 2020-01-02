const supplierCondition = {
  quoteRequirement : [
    {
      value: '1',
      label: '报价含税'
    },
    {
      value: '2',
      label: '报价包含运费'
    }
  ],
  paymentMethod : [
    {
      value: '1',
      label: '银行转账'
    },
    {
      value: '2',
      label: '电子银行承兑汇票'
    },
    {
      value: '3',
      label: '其他'
    }
  ],
  transactionMode : [
    {
      value: '1',
      label: '货到付款'
    },
    {
      value: '2',
      label: '款到发货'
    },
    {
      value: '3',
      label: '账期支付'
    },
    {
      value: '4',
      label: '其他'
    }
  ],
  settlementDayType : [
    {
      value: '1',
      label: '指定日期'
    },
    {
      value: '2',
      label: '交付日期'
    }
  ],
  invoiceRequirement : [
    {
      value: '1',
      label: '增值税专用发票自行开'
    },
    {
      value: '2',
      label: '增值税专用发票或税务代开'
    },
    {
      value: '3',
      label: '增值税普通发票'
    },
    {
      value: '4',
      label: '不用发票'
    }
  ]
}
export {
  supplierCondition
}
