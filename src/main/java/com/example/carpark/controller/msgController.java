package com.example.carpark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/msg")
public class msgController {



    /**
//     * 路径跳转
//     *
//     * @param path
//     * @return
//     */
//    @RequestMapping("/path/{uri}")
//    public String redirect(@PathVariable(value = "uri") String path) {
//        return "demo/" + path;
//    }

    @RequestMapping("/getList")
    @ResponseBody
    public String getList() {

        return "{\n" +
                "  \"code\": 0\n" +
                "  ,\"msg\": \"\"\n" +
                "  ,\"data\": {\n" +
                "    \"mine\": {\n" +
                "      \"username\": \"纸飞机\"\n" +
                "      ,\"id\": \"100000\"\n" +
                "      ,\"status\": \"online\"\n" +
                "      ,\"sign\": \"在深邃的编码世界，做一枚轻盈的纸飞机\"\n" +
                "      ,\"avatar\": \"http://cdn.firstlinkapp.com/upload/2016_6/1465575923433_33812.jpg\"\n" +
                "    }\n" +
                "    ,\"friend\": [{\n" +
                "      \"groupname\": \"前端码屌\"\n" +
                "      ,\"id\": 1\n" +
                "      ,\"online\": 2\n" +
                "      ,\"list\": [{\n" +
                "        \"username\": \"贤心\"\n" +
                "        ,\"id\": \"100001\"\n" +
                "        ,\"avatar\": \"http://tp1.sinaimg.cn/1571889140/180/40030060651/1\"\n" +
                "        ,\"sign\": \"这些都是测试数据，实际使用请严格按照该格式返回\"\n" +
                "      },{\n" +
                "        \"username\": \"Z_子晴\"\n" +
                "        ,\"id\": \"108101\"\n" +
                "        ,\"avatar\": \"http://tva3.sinaimg.cn/crop.0.0.512.512.180/8693225ajw8f2rt20ptykj20e80e8weu.jpg\"\n" +
                "        ,\"sign\": \"微电商达人\"\n" +
                "      },{\n" +
                "        \"username\": \"Lemon_CC\"\n" +
                "        ,\"id\": \"102101\"\n" +
                "        ,\"avatar\": \"http://tp2.sinaimg.cn/1833062053/180/5643591594/0\"\n" +
                "        ,\"sign\": \"\"\n" +
                "      },{\n" +
                "        \"username\": \"马小云\"\n" +
                "        ,\"id\": \"168168\"\n" +
                "        ,\"avatar\": \"http://tp4.sinaimg.cn/2145291155/180/5601307179/1\"\n" +
                "        ,\"sign\": \"让天下没有难写的代码\"\n" +
                "        ,\"status\": \"offline\"\n" +
                "      },{\n" +
                "        \"username\": \"徐小峥\"\n" +
                "        ,\"id\": \"666666\"\n" +
                "        ,\"avatar\": \"http://tp2.sinaimg.cn/1783286485/180/5677568891/1\"\n" +
                "        ,\"sign\": \"代码在囧途，也要写到底\"\n" +
                "      }]\n" +
                "    },{\n" +
                "      \"groupname\": \"网红\"\n" +
                "      ,\"id\": 2\n" +
                "      ,\"online\": 3\n" +
                "      ,\"list\": [{\n" +
                "        \"username\": \"罗玉凤\"\n" +
                "        ,\"id\": \"121286\"\n" +
                "        ,\"avatar\": \"http://tp1.sinaimg.cn/1241679004/180/5743814375/0\"\n" +
                "        ,\"sign\": \"在自己实力不济的时候，不要去相信什么媒体和记者。他们不是善良的人，有时候候他们的采访对当事人而言就是陷阱\"\n" +
                "      },{\n" +
                "        \"username\": \"长泽梓Azusa\"\n" +
                "        ,\"id\": \"100001222\"\n" +
                "        ,\"sign\": \"我是日本女艺人长泽あずさ\"\n" +
                "        ,\"avatar\": \"http://tva1.sinaimg.cn/crop.0.0.180.180.180/86b15b6cjw1e8qgp5bmzyj2050050aa8.jpg\"\n" +
                "      },{\n" +
                "        \"username\": \"大鱼_MsYuyu\"\n" +
                "        ,\"id\": \"12123454\"\n" +
                "        ,\"avatar\": \"http://tp1.sinaimg.cn/5286730964/50/5745125631/0\"\n" +
                "        ,\"sign\": \"我瘋了！這也太準了吧  超級笑點低\"\n" +
                "      },{\n" +
                "        \"username\": \"谢楠\"\n" +
                "        ,\"id\": \"10034001\"\n" +
                "        ,\"avatar\": \"http://tp4.sinaimg.cn/1665074831/180/5617130952/0\"\n" +
                "        ,\"sign\": \"\"\n" +
                "      },{\n" +
                "        \"username\": \"柏雪近在它香\"\n" +
                "        ,\"id\": \"3435343\"\n" +
                "        ,\"avatar\": \"http://tp2.sinaimg.cn/2518326245/180/5636099025/0\"\n" +
                "        ,\"sign\": \"\"\n" +
                "      }]\n" +
                "    },{\n" +
                "      \"groupname\": \"我心中的女神\"\n" +
                "      ,\"id\": 3\n" +
                "      ,\"online\": 1\n" +
                "      ,\"list\": [{\n" +
                "        \"username\": \"林心如\"\n" +
                "        ,\"id\": \"76543\"\n" +
                "        ,\"avatar\": \"http://tp3.sinaimg.cn/1223762662/180/5741707953/0\"\n" +
                "        ,\"sign\": \"我爱贤心\"\n" +
                "      },{\n" +
                "        \"username\": \"佟丽娅\"\n" +
                "        ,\"id\": \"4803920\"\n" +
                "        ,\"avatar\": \"http://tp4.sinaimg.cn/1345566427/180/5730976522/0\"\n" +
                "        ,\"sign\": \"我也爱贤心吖吖啊\"\n" +
                "      }]\n" +
                "    }]\n" +
                "    ,\"group\": [{\n" +
                "      \"groupname\": \"前端群\"\n" +
                "      ,\"id\": \"101\"\n" +
                "      ,\"avatar\": \"http://tp2.sinaimg.cn/2211874245/180/40050524279/0\"\n" +
                "    },{\n" +
                "      \"groupname\": \"Fly社区官方群\"\n" +
                "      ,\"id\": \"102\"\n" +
                "      ,\"avatar\": \"http://tp2.sinaimg.cn/5488749285/50/5719808192/1\"\n" +
                "    }]\n" +
                "  }\n" +
                "}";
    }

    @RequestMapping("/getMembers")
    @ResponseBody
    public String getMembers(){
        return "{\n" +
                "  \"code\": 0\n" +
                "  ,\"msg\": \"\"\n" +
                "  ,\"data\": {\n" +
                "    \"owner\": {\n" +
                "      \"username\": \"贤心\"\n" +
                "      ,\"id\": \"100001\"\n" +
                "      ,\"avatar\": \"http://tp1.sinaimg.cn/1571889140/180/40030060651/1\"\n" +
                "      ,\"sign\": \"这些都是测试数据，实际使用请严格按照该格式返回\"\n" +
                "    }\n" +
                "    ,\"members\": 12\n" +
                "    ,\"list\": [{\n" +
                "      \"username\": \"贤心\"\n" +
                "      ,\"id\": \"100001\"\n" +
                "      ,\"avatar\": \"http://tp1.sinaimg.cn/1571889140/180/40030060651/1\"\n" +
                "      ,\"sign\": \"这些都是测试数据，实际使用请严格按照该格式返回\"\n" +
                "    },{\n" +
                "      \"username\": \"Z_子晴\"\n" +
                "      ,\"id\": \"108101\"\n" +
                "      ,\"avatar\": \"http://tva3.sinaimg.cn/crop.0.0.512.512.180/8693225ajw8f2rt20ptykj20e80e8weu.jpg\"\n" +
                "      ,\"sign\": \"微电商达人\"\n" +
                "    },{\n" +
                "      \"username\": \"Lemon_CC\"\n" +
                "      ,\"id\": \"102101\"\n" +
                "      ,\"avatar\": \"http://tp2.sinaimg.cn/1833062053/180/5643591594/0\"\n" +
                "      ,\"sign\": \"\"\n" +
                "    },{\n" +
                "      \"username\": \"马小云\"\n" +
                "      ,\"id\": \"168168\"\n" +
                "      ,\"avatar\": \"http://tp4.sinaimg.cn/2145291155/180/5601307179/1\"\n" +
                "      ,\"sign\": \"让天下没有难写的代码\"\n" +
                "    },{\n" +
                "      \"username\": \"徐小峥\"\n" +
                "      ,\"id\": \"666666\"\n" +
                "      ,\"avatar\": \"http://tp2.sinaimg.cn/1783286485/180/5677568891/1\"\n" +
                "      ,\"sign\": \"代码在囧途，也要写到底\"\n" +
                "    },{\n" +
                "      \"username\": \"罗玉凤\"\n" +
                "      ,\"id\": \"121286\"\n" +
                "      ,\"avatar\": \"http://tp1.sinaimg.cn/1241679004/180/5743814375/0\"\n" +
                "      ,\"sign\": \"在自己实力不济的时候，不要去相信什么媒体和记者。他们不是善良的人，有时候候他们的采访对当事人而言就是陷阱\"\n" +
                "    },{\n" +
                "      \"username\": \"长泽梓Azusa\"\n" +
                "      ,\"id\": \"100001222\"\n" +
                "      ,\"avatar\": \"http://tva1.sinaimg.cn/crop.0.0.180.180.180/86b15b6cjw1e8qgp5bmzyj2050050aa8.jpg\"\n" +
                "      ,\"sign\": \"我是日本女艺人长泽あずさ\"\n" +
                "    },{\n" +
                "      \"username\": \"大鱼_MsYuyu\"\n" +
                "      ,\"id\": \"12123454\"\n" +
                "      ,\"avatar\": \"http://tp1.sinaimg.cn/5286730964/50/5745125631/0\"\n" +
                "      ,\"sign\": \"我瘋了！這也太準了吧  超級笑點低\"\n" +
                "    },{\n" +
                "      \"username\": \"谢楠\"\n" +
                "      ,\"id\": \"10034001\"\n" +
                "      ,\"avatar\": \"http://tp4.sinaimg.cn/1665074831/180/5617130952/0\"\n" +
                "      ,\"sign\": \"\"\n" +
                "    },{\n" +
                "      \"username\": \"柏雪近在它香\"\n" +
                "      ,\"id\": \"3435343\"\n" +
                "      ,\"avatar\": \"http://tp2.sinaimg.cn/2518326245/180/5636099025/0\"\n" +
                "      ,\"sign\": \"\"\n" +
                "    },{\n" +
                "      \"username\": \"林心如\"\n" +
                "      ,\"id\": \"76543\"\n" +
                "      ,\"avatar\": \"http://tp3.sinaimg.cn/1223762662/180/5741707953/0\"\n" +
                "      ,\"sign\": \"我爱贤心\"\n" +
                "    },{\n" +
                "      \"username\": \"佟丽娅\"\n" +
                "      ,\"id\": \"4803920\"\n" +
                "      ,\"avatar\": \"http://tp4.sinaimg.cn/1345566427/180/5730976522/0\"\n" +
                "      ,\"sign\": \"我也爱贤心吖吖啊\"\n" +
                "    }]\n" +
                "  }\n" +
                "}";
    }
}
