// 标段类型js
const bidsType = [
  {
    "value": "A",
    "label": "工程",
    "children": [
      {
        "value": "A01",
        "label": "规划",
        "children": [
          {
            "value": "A0101",
            "label": "国民经济和社会发展规划"
          },
          {
            "value": "A0102",
            "label": "城乡规划"
          },
          {
            "value": "A0103",
            "label": "土地利用总体规划"
          },
          {
            "value": "A0104",
            "label": "国土规划"
          },
          {
            "value": "A0105",
            "label": "矿产资源规划"
          }
        ]
      },
      {
        "value": "A02",
        "label": "投资策划与决策",
        "children": [
          {
            "value": "A0201",
            "label": "项目建议、可行性研究、评估及后评价"
          }
        ]
      },
      {
        "value": "A03",
        "label": "勘察",
        "children": [
          {
            "value": "A0301",
            "label": "岩土工程"
          },
          {
            "value": "A0302",
            "label": "地质勘察"
          },
          {
            "value": "A0303",
            "label": "测绘工程"
          },
          {
            "value": "A0304",
            "label": "水文气象勘察"
          },
          {
            "value": "A0305",
            "label": "水资源调查评价"
          },
          {
            "value": "A0306",
            "label": "地震勘察"
          },
          {
            "value": "A0307",
            "label": "文物勘察"
          }
        ]
      },
      {
        "value": "A04",
        "label": "设计",
        "children": [
          {
            "value": "A0401",
            "label": "建筑工程"
          },
          {
            "value": "A0402",
            "label": "市政工程"
          },
          {
            "value": "A0403",
            "label": "公路工程"
          },
          {
            "value": "A0404",
            "label": "铁路工程"
          },
          {
            "value": "A0405",
            "label": "城市轨道交通工程"
          },
          {
            "value": "A0406",
            "label": "民航工程"
          },
          {
            "value": "A0407",
            "label": "水运工程"
          },
          {
            "value": "A0408",
            "label": "水利工程"
          },
          {
            "value": "A0409",
            "label": "水电工程"
          },
          {
            "value": "A0410",
            "label": "电力工程"
          },
          {
            "value": "A0411",
            "label": "新能源工程"
          },
          {
            "value": "A0412",
            "label": "矿山工程"
          },
          {
            "value": "A0413",
            "label": "石油天然气工程"
          },
          {
            "value": "A0414",
            "label": "机械工程"
          },
          {
            "value": "A0415",
            "label": "冶金工程"
          },
          {
            "value": "A0416",
            "label": "有色工程"
          },
          {
            "value": "A0417",
            "label": "化工工程"
          },
          {
            "value": "A0418",
            "label": "石油化工工程"
          },
          {
            "value": "A0419",
            "label": "煤化工工程"
          },
          {
            "value": "A0420",
            "label": "轻工业工程"
          },
          {
            "value": "A0421",
            "label": "农业工程"
          },
          {
            "value": "A0422",
            "label": "林业工程"
          },
          {
            "value": "A0423",
            "label": "商物粮工程"
          },
          {
            "value": "A0424",
            "label": "电子工程"
          },
          {
            "value": "A0425",
            "label": "通信工程"
          },
          {
            "value": "A0426",
            "label": "广播电影电视工程"
          },
          {
            "value": "A0427",
            "label": "海洋工程"
          },
          {
            "value": "A0428",
            "label": "建材工业工程"
          },
          {
            "value": "A0429",
            "label": "军工工程"
          },
          {
            "value": "A0430",
            "label": "土地整治工程"
          }
        ]
      },
      {
        "value": "A05",
        "label": "监理",
        "children": [
          {
            "value": "A0501",
            "label": "建筑工程"
          },
          {
            "value": "A0502",
            "label": "市政工程"
          },
          {
            "value": "A0503",
            "label": "公路工程"
          },
          {
            "value": "A0504",
            "label": "铁路工程"
          },
          {
            "value": "A0505",
            "label": "城市轨道交通"
          },
          {
            "value": "A0506",
            "label": "民航工程"
          },
          {
            "value": "A0507",
            "label": "水运工程"
          },
          {
            "value": "A0508",
            "label": "水利工程"
          },
          {
            "value": "A0509",
            "label": "水电工程"
          },
          {
            "value": "A0510",
            "label": "电力工程"
          },
          {
            "value": "A0511",
            "label": "新能源工程"
          },
          {
            "value": "A0512",
            "label": "矿山工程"
          },
          {
            "value": "A0513",
            "label": "石油天然气及石油化工工程"
          },
          {
            "value": "A0514",
            "label": "冶金工程"
          },
          {
            "value": "A0515",
            "label": "农林工程"
          },
          {
            "value": "A0516",
            "label": "电子工程"
          },
          {
            "value": "A0517",
            "label": "通信工程"
          },
          {
            "value": "A0518",
            "label": "广播电影电视工程"
          },
          {
            "value": "A0519",
            "label": "地质环境"
          },
          {
            "value": "A0520",
            "label": "机电安装工程"
          },
          {
            "value": "A0521",
            "label": "设备监造"
          },
          {
            "value": "A0522",
            "label": "土地整治工程"
          }
        ]
      },
      {
        "value": "A06",
        "label": "工程造价",
        "children": [
          {
            "value": "A0601",
            "label": "土建工程"
          },
          {
            "value": "A0602",
            "label": "安装工程"
          },
          {
            "value": "A0603",
            "label": "概预算"
          }
        ]
      },
      {
        "value": "A07",
        "label": "项目管理(含代建)",
        "children": [
          {
            "value": "A0701",
            "label": "建设项目管理"
          }
        ]
      },
      {
        "value": "A08",
        "label": "工程施工",
        "children": [
          {
            "value": "A0801",
            "label": "建筑工程"
          },
          {
            "value": "A0802",
            "label": "市政工程"
          },
          {
            "value": "A0803",
            "label": "公路工程"
          },
          {
            "value": "A0804",
            "label": "铁路工程"
          },
          {
            "value": "A0805",
            "label": "城市轨道交通工程"
          },
          {
            "value": "A0806",
            "label": "民航工程"
          },
          {
            "value": "A0807",
            "label": "水运工程"
          },
          {
            "value": "A0808",
            "label": "水利工程"
          },
          {
            "value": "A0809",
            "label": "水电工程"
          },
          {
            "value": "A0810",
            "label": "电力工程"
          },
          {
            "value": "A0811",
            "label": "新能源工程"
          },
          {
            "value": "A0812",
            "label": "矿山工程"
          },
          {
            "value": "A0813",
            "label": "石油天然气工程"
          },
          {
            "value": "A0814",
            "label": "机械工程"
          },
          {
            "value": "A0815",
            "label": "冶金工程"
          },
          {
            "value": "A0816",
            "label": "化工、医药工程"
          },
          {
            "value": "A0817",
            "label": "轻工业工程"
          },
          {
            "value": "A0818",
            "label": "农业工程"
          },
          {
            "value": "A0819",
            "label": "林业工程"
          },
          {
            "value": "A0820",
            "label": "商物粮工程"
          },
          {
            "value": "A0821",
            "label": "电子工程"
          },
          {
            "value": "A0822",
            "label": "通信工程"
          },
          {
            "value": "A0823",
            "label": "广播电影电视工程"
          },
          {
            "value": "A0824",
            "label": "海洋工程"
          },
          {
            "value": "A0825",
            "label": "建材工业工程"
          },
          {
            "value": "A0826",
            "label": "军工工程"
          },
          {
            "value": "A0827",
            "label": "生物工程"
          },
          {
            "value": "A0828",
            "label": "航空航天工程"
          },
          {
            "value": "A0829",
            "label": "地质环境"
          },
          {
            "value": "A0830",
            "label": "园林绿化工程"
          },
          {
            "value": "A0831",
            "label": "核工程"
          },
          {
            "value": "A0832",
            "label": "信息工程"
          },
          {
            "value": "A0833",
            "label": "文物保护工程"
          },
          {
            "value": "A0834",
            "label": "土地整治工程"
          }
        ]
      },
      {
        "value": "A09",
        "label": "其他工程",
        "children": [
          {
            "value": "A0901",
            "label": "环境工程"
          },
          {
            "value": "A0902",
            "label": "节能工程"
          },
          {
            "value": "A0903",
            "label": "气象工程"
          },
          {
            "value": "A0904",
            "label": "地震工程"
          },
          {
            "value": "A0905",
            "label": "水文工程"
          },
          {
            "value": "A0906",
            "label": "储（蓄）能工程"
          },
          {
            "value": "A0907",
            "label": "安全防范工程"
          },
          {
            "value": "A0908",
            "label": "展陈工程"
          },
          {
            "value": "A0909",
            "label": "土地整治工程"
          },
          {
            "value": "A0910",
            "label": "沙漠化治理工程"
          }
        ]
      }
    ]
  },
  {
    "value": "B",
    "label": "货物",
    "children": [
      {
        "value": "B01",
        "label": "机电产品类",
        "children": [
          {
            "value": "B0101",
            "label": "建筑机械"
          },
          {
            "value": "B0102",
            "label": "城市轨道工程设备"
          },
          {
            "value": "B0103",
            "label": "民航设备"
          },
          {
            "value": "B0104",
            "label": "矿山机械"
          },
          {
            "value": "B0105",
            "label": "石油、天然气设备"
          },
          {
            "value": "B0106",
            "label": "冶金机械"
          },
          {
            "value": "B0107",
            "label": "化工机械"
          },
          {
            "value": "B0108",
            "label": "煤化工设备"
          },
          {
            "value": "B0109",
            "label": "轻工机械"
          },
          {
            "value": "B0110",
            "label": "农业机械"
          },
          {
            "value": "B0111",
            "label": "林业机械"
          },
          {
            "value": "B0112",
            "label": "电子元器件"
          },
          {
            "value": "B0113",
            "label": "通信设备"
          },
          {
            "value": "B0114",
            "label": "广播、影视、舞台设备"
          },
          {
            "value": "B0115",
            "label": "海洋设备"
          },
          {
            "value": "B0116",
            "label": "金属加工机械"
          },
          {
            "value": "B0117",
            "label": "锅炉及原动机设备"
          },
          {
            "value": "B0118",
            "label": "造纸机械"
          },
          {
            "value": "B0119",
            "label": "起重运输机械"
          },
          {
            "value": "B0120",
            "label": "纺织机械"
          },
          {
            "value": "B0121",
            "label": "包装设备"
          },
          {
            "value": "B0122",
            "label": "生物制造设备"
          },
          {
            "value": "B0123",
            "label": "粮油食品加工机械"
          },
          {
            "value": "B0124",
            "label": "非金属矿物制品工业专用设备"
          },
          {
            "value": "B0125",
            "label": "车辆"
          },
          {
            "value": "B0126",
            "label": "船舶设备"
          },
          {
            "value": "B0127",
            "label": "航空航天设备"
          },
          {
            "value": "B0128",
            "label": "兵器装备"
          },
          {
            "value": "B0129",
            "label": "核设备"
          },
          {
            "value": "B0130",
            "label": "电机"
          },
          {
            "value": "B0131",
            "label": "输变电设备、设施"
          },
          {
            "value": "B0132",
            "label": "计算机及网络设备"
          },
          {
            "value": "B0133",
            "label": "计算机系统软件"
          },
          {
            "value": "B0134",
            "label": "环保设备"
          },
          {
            "value": "B0135",
            "label": "气象设备"
          },
          {
            "value": "B0136",
            "label": "交通信号专用设备"
          },
          {
            "value": "B0137",
            "label": "地震监测设备"
          },
          {
            "value": "B0138",
            "label": "节能设备"
          },
          {
            "value": "B0139",
            "label": "科学、教育设备"
          },
          {
            "value": "B0140",
            "label": "安全技术防范设备"
          },
          {
            "value": "B0141",
            "label": "其他专用机械"
          },
          {
            "value": "B0142",
            "label": "电子专用设备"
          },
          {
            "value": "B0143",
            "label": "水运工程设备"
          },
          {
            "value": "B0144",
            "label": "新能源机械设备"
          },
          {
            "value": "B0145",
            "label": "轨道铺架设备"
          },
          {
            "value": "B0146",
            "label": "文物保护设备"
          },
          {
            "value": "B0147",
            "label": "测绘地理信息设备"
          },
          {
            "value": "B0148",
            "label": "医疗设备"
          }
        ]
      },
      {
        "value": "B02",
        "label": "医疗器材",
        "children": [
          {
            "value": "B0201",
            "label": "医疗器具"
          },
          {
            "value": "B0202",
            "label": "材料"
          }
        ]
      },
      {
        "value": "B03",
        "label": "金属材料",
        "children": [
          {
            "value": "B0301",
            "label": "黑色金属及产品"
          },
          {
            "value": "B0302",
            "label": "有色金属及产品"
          },
          {
            "value": "B0303",
            "label": "合金及产品"
          },
          {
            "value": "B0304",
            "label": "石油专用管材"
          }
        ]
      },
      {
        "value": "B04",
        "label": "石油及其制品",
        "children": [
          {
            "value": "B0401",
            "label": "原油"
          },
          {
            "value": "B0402",
            "label": "天然气"
          },
          {
            "value": "B0403",
            "label": "石油制品"
          }
        ]
      },
      {
        "value": "B05",
        "label": "煤炭煤层气及其制品",
        "children": [
          {
            "value": "B0501",
            "label": "煤炭"
          },
          {
            "value": "B0502",
            "label": "煤炭制品"
          },
          {
            "value": "B0503",
            "label": "煤层气"
          }
        ]
      },
      {
        "value": "B06",
        "label": "化工材料及其制品",
        "children": [
          {
            "value": "B0601",
            "label": "无机化学原料"
          },
          {
            "value": "B0602",
            "label": "有机化工材料"
          },
          {
            "value": "B0603",
            "label": "合成材料及专用化工品"
          },
          {
            "value": "B0604",
            "label": "石油专用化工产品"
          },
          {
            "value": "B0605",
            "label": "催化剂、助剂、添加剂"
          },
          {
            "value": "B0606",
            "label": "其他化工"
          }
        ]
      },
      {
        "value": "B07",
        "label": "建筑材料",
        "children": [
          {
            "value": "B0701",
            "label": "水泥及水泥制品"
          },
          {
            "value": "B0702",
            "label": "木材"
          },
          {
            "value": "B0703",
            "label": "石材"
          },
          {
            "value": "B0704",
            "label": "陶瓷制品"
          },
          {
            "value": "B0705",
            "label": "其他建筑材料"
          },
          {
            "value": "B0706",
            "label": "新型材料"
          }
        ]
      },
      {
        "value": "B08",
        "label": "医药",
        "children": [
          {
            "value": "B0801",
            "label": "内科"
          },
          {
            "value": "B0802",
            "label": "外科"
          },
          {
            "value": "B0803",
            "label": "妇产科"
          },
          {
            "value": "B0804",
            "label": "儿科"
          },
          {
            "value": "B0805",
            "label": "口腔科"
          },
          {
            "value": "B0806",
            "label": "眼科"
          },
          {
            "value": "B0807",
            "label": "耳鼻咽喉科"
          },
          {
            "value": "B0808",
            "label": "皮肤科"
          },
          {
            "value": "B0809",
            "label": "精神科"
          },
          {
            "value": "B0810",
            "label": "肿瘤科"
          },
          {
            "value": "B0811",
            "label": "中医"
          },
          {
            "value": "B0812",
            "label": "民族医学"
          },
          {
            "value": "B0813",
            "label": "麻醉科"
          },
          {
            "value": "B0814",
            "label": "中西医结合科"
          },
          {
            "value": "B0815",
            "label": "采供血"
          },
          {
            "value": "B0816",
            "label": "医技"
          },
          {
            "value": "B0817",
            "label": "疾控与公共卫生"
          },
          {
            "value": "B0818",
            "label": "护理"
          },
          {
            "value": "B0819",
            "label": "药学"
          },
          {
            "value": "B0820",
            "label": "法医学"
          },
          {
            "value": "B0821",
            "label": "戒毒医学"
          },
          {
            "value": "B0822",
            "label": "环化药剂"
          },
          {
            "value": "B0823",
            "label": "药品注册"
          },
          {
            "value": "B0824",
            "label": "兽用疫苗药品"
          },
          {
            "value": "B0825",
            "label": "疫苗"
          }
        ]
      },
      {
        "value": "B09",
        "label": "其他类",
        "children": [
          {
            "value": "B0901",
            "label": "其他货物"
          }
        ]
      }
    ]
  },
  {
    "value": "C",
    "label": "服务",
    "children": [
      {
        "value": "C01",
        "label": "勘查与调查",
        "children": [
          {
            "value": "C0101",
            "label": "地球勘查"
          },
          {
            "value": "C0102",
            "label": "地质调查"
          },
          {
            "value": "C0103",
            "label": "生态与资源调查"
          },
          {
            "value": "C0104",
            "label": "矿产勘查"
          },
          {
            "value": "C0105",
            "label": "水文监测与调查"
          },
          {
            "value": "C0106",
            "label": "海洋调查与监测"
          },
          {
            "value": "C0107",
            "label": "水土保持调查与监测"
          },
          {
            "value": "C0108",
            "label": "地质环境调查"
          },
          {
            "value": "C0109",
            "label": "土地利用及变更调查"
          },
          {
            "value": "C0110",
            "label": "地理国情调查与监测"
          },
          {
            "value": "C0111",
            "label": "环境电磁频谱监测与调查"
          },
          {
            "value": "C0112",
            "label": "水文、水资源调查评价"
          },
          {
            "value": "C0113",
            "label": "环境质量调查"
          }
        ]
      },
      {
        "value": "C02",
        "label": "公共咨询",
        "children": [
          {
            "value": "C0201",
            "label": "咨询服务"
          }
        ]
      },
      {
        "value": "C03",
        "label": "经济管理",
        "children": [
          {
            "value": "C0301",
            "label": "国民经济管理"
          },
          {
            "value": "C0302",
            "label": "农业经济管理"
          },
          {
            "value": "C0303",
            "label": "工业经济管理"
          },
          {
            "value": "C0304",
            "label": "建筑经济管理"
          },
          {
            "value": "C0305",
            "label": "投资经济管理"
          },
          {
            "value": "C0306",
            "label": "循环经济管理"
          },
          {
            "value": "C0307",
            "label": "国有资产管理"
          },
          {
            "value": "C0308",
            "label": "土地管理"
          },
          {
            "value": "C0309",
            "label": "财税管理"
          },
          {
            "value": "C0310",
            "label": "审计管理"
          },
          {
            "value": "C0311",
            "label": "统计管理"
          },
          {
            "value": "C0312",
            "label": "价格管理"
          },
          {
            "value": "C0313",
            "label": "合同管理"
          },
          {
            "value": "C0314",
            "label": "地质矿产经济管理"
          },
          {
            "value": "C0315",
            "label": "海洋经济管理"
          }
        ]
      },
      {
        "value": "C04",
        "label": "工商管理",
        "children": [
          {
            "value": "C0401",
            "label": "企业管理"
          },
          {
            "value": "C0402",
            "label": "财务管理"
          },
          {
            "value": "C0403",
            "label": "资产评估"
          },
          {
            "value": "C0404",
            "label": "人力资源管理"
          },
          {
            "value": "C0405",
            "label": "市场营销"
          },
          {
            "value": "C0406",
            "label": "房地产管理"
          },
          {
            "value": "C0407",
            "label": "旅游管理"
          },
          {
            "value": "C0408",
            "label": "宾馆管理"
          },
          {
            "value": "C0409",
            "label": "餐饮管理"
          },
          {
            "value": "C0410",
            "label": "物业管理"
          },
          {
            "value": "C0411",
            "label": "国际贸易"
          },
          {
            "value": "C0412",
            "label": "商贸经纪与代理"
          },
          {
            "value": "C0413",
            "label": "城乡公共服务"
          },
          {
            "value": "C0414",
            "label": "出版物管理"
          }
        ]
      },
      {
        "value": "C05",
        "label": "金融",
        "children": [
          {
            "value": "C0501",
            "label": "银行"
          },
          {
            "value": "C0502",
            "label": "会计"
          },
          {
            "value": "C0503",
            "label": "保险"
          },
          {
            "value": "C0504",
            "label": "证券"
          },
          {
            "value": "C0505",
            "label": "投行"
          },
          {
            "value": "C0506",
            "label": "融资租赁"
          }
        ]
      },
      {
        "value": "C06",
        "label": "法律",
        "children": [
          {
            "value": "C0601",
            "label": "宪法"
          },
          {
            "value": "C0602",
            "label": "民商法"
          },
          {
            "value": "C0603",
            "label": "行政法"
          },
          {
            "value": "C0604",
            "label": "经济法"
          },
          {
            "value": "C0605",
            "label": "刑法"
          },
          {
            "value": "C0606",
            "label": "诉讼法"
          },
          {
            "value": "C0607",
            "label": "国际法"
          }
        ]
      },
      {
        "value": "C07",
        "label": "运维、评估与修理",
        "children": [
          {
            "value": "C0701",
            "label": "机械设备修理"
          },
          {
            "value": "C0702",
            "label": "电子通信产品维护与修理"
          },
          {
            "value": "C0703",
            "label": "油气开发评估与运维"
          },
          {
            "value": "C0704",
            "label": "石油化工生产运维"
          },
          {
            "value": "C0705",
            "label": "石油化工生产运维"
          },
          {
            "value": "C0706",
            "label": "污染治理设施运维与修理"
          }
        ]
      },
      {
        "value": "C08",
        "label": "租赁",
        "children": [
          {
            "value": "C0801",
            "label": "货物租赁"
          },
          {
            "value": "C0802",
            "label": "汽车租赁"
          },
          {
            "value": "C0803",
            "label": "飞机租赁"
          },
          {
            "value": "C0804",
            "label": "船舶租赁"
          },
          {
            "value": "C0805",
            "label": "不动产租赁"
          },
          {
            "value": "C0806",
            "label": "其他租赁"
          }
        ]
      },
      {
        "value": "C09",
        "label": "交通运输与物流",
        "children": [
          {
            "value": "C0901",
            "label": "铁路"
          },
          {
            "value": "C0902",
            "label": "城市轨道交通"
          },
          {
            "value": "C0903",
            "label": "道路运输与城市客运"
          },
          {
            "value": "C0904",
            "label": "水路"
          },
          {
            "value": "C0905",
            "label": "航空"
          },
          {
            "value": "C0906",
            "label": "管道"
          },
          {
            "value": "C0907",
            "label": "物流"
          },
          {
            "value": "C0908",
            "label": "危险货物运输"
          },
          {
            "value": "C0909",
            "label": "城市停车"
          }
        ]
      },
      {
        "value": "C10",
        "label": "节能服务",
        "children": [
          {
            "value": "C1001",
            "label": "合同能源管理"
          },
          {
            "value": "C1002",
            "label": "节能评估"
          },
          {
            "value": "C1003",
            "label": "节能监测"
          },
          {
            "value": "C1004",
            "label": "能源审计"
          }
        ]
      },
      {
        "value": "C11",
        "label": "高新技术服务",
        "children": [
          {
            "value": "C1101",
            "label": "技术研发"
          },
          {
            "value": "C1102",
            "label": "技术引进"
          },
          {
            "value": "C1103",
            "label": "技术评估与转移"
          }
        ]
      },
      {
        "value": "C12",
        "label": "其他服务",
        "children": [
          {
            "value": "C1201",
            "label": "社会公共安全服务"
          },
          {
            "value": "C1202",
            "label": "艺术"
          },
          {
            "value": "C1203",
            "label": "广告"
          },
          {
            "value": "C1204",
            "label": "会议服务"
          },
          {
            "value": "C1205",
            "label": "培训"
          },
          {
            "value": "C1206",
            "label": "印刷、出版、发行"
          },
          {
            "value": "C1207",
            "label": "体育产业与管理"
          },
          {
            "value": "C1208",
            "label": "广播、电视、电影和音像服务"
          },
          {
            "value": "C1209",
            "label": "厨房垃圾处理"
          },
          {
            "value": "C1210",
            "label": "物业服务"
          },
          {
            "value": "C1211",
            "label": "旅游服务"
          },
          {
            "value": "C1212",
            "label": "档案管理"
          },
          {
            "value": "C1213",
            "label": "检验检测"
          },
          {
            "value": "C1214",
            "label": "营销"
          },
          {
            "value": "C1215",
            "label": "劳务输出"
          }
        ]
      },
      {
        "value": "C13",
        "label": "应对气候变化服务",
        "children": [
          {
            "value": "C1301",
            "label": "温室气体排放核算"
          },
          {
            "value": "C1302",
            "label": "碳排放权交易"
          },
          {
            "value": "C1303",
            "label": "气候变化影响评估"
          },
          {
            "value": "C1304",
            "label": "林业碳汇交易"
          }
        ]
      },
      {
        "value": "C14",
        "label": "网络、电磁空间安全服务",
        "children": [
          {
            "value": "C1400",
            "label": "网络、电磁空间安全服务"
          }
        ]
      }
    ]
  }
]
export {
  bidsType
}
