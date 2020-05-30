package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.*;
import cn.zz.dgcc.DGIOT.service.*;
import cn.zz.dgcc.DGIOT.service.Exception.ISqlException;
import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPMessage;
import cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg3AnalysisGrain;
import cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg4AnalysisN2;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/5/18 10:34
 * ClassExplain :
 * ->
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {
    private final Logger log = Logger.getLogger("AnalysisService.class");
    @Autowired
    DepotService depotService;
    @Autowired
    N2Service n2Service;
    @Autowired
    GrainService grainService;
    @Autowired
    OrderService orderService;
    @Autowired
    N2ConfService n2ConfService;

    private static String 开关动作 = "1";
    private static String 查询动作 = "2";
    private static String 气调信息 = "0";

    public void readFromMyql(String msgId) {
        N2 rs = n2Service.getInfo(msgId);
        String content = rs.getContent();
    }


    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

//    public static void main(String[] args) {
//        String a = "{\"a\":\"1\"}";
//        jsonParse(a);
//    }

    public static JSONObject jsonParse(String msg) {
        JSONObject jo = JSONObject.parseObject(msg);
        System.err.println(jo);
        return jo;
    }

    @Override
    public void analysisInfo(AMQPMessage amqpMessage) {
        log.info("解析开始~~~~~~~~~~~~~~~~~~~~~~~~");
        byte[] bytes = amqpMessage.getBody();
        String msg = bytesToHexString(bytes).toUpperCase();
        ;
        String topic = amqpMessage.getTopic();
        String[] topicSplit = topic.split("/");
        String pk = topicSplit[1];
        String devName = topicSplit[2];
        String msgId = amqpMessage.getMessageId();
        log.info("接收到" + pk + "-" + devName + "的消息:" + msg);
        log.info("解析完成························");
        Date date = new Date();

        String content = amqpMessage.getContent();
        if (content.startsWith("{")) {
            JSONObject jo = jsonParse(content);
            if ("CXPZ".equals(jo.getString("response"))) {
                Gson g = new Gson();
                QTConfigure n2conf = g.fromJson(jo.toJSONString(), QTConfigure.class);
                int rs = n2ConfService.saveConf(n2conf);
            }
        }


        if (msg.startsWith("0306")) {
            //代表制氮机控制命令返回
            N2 n2 = new N2(devName, date, msg, msgId, 开关动作);
            int rs = n2Service.saveN2(n2);
            if (rs != 1) {
                throw new ISqlException("持久化制氮机信息到本地出错");
            }
        }
        if (msg.startsWith("0303")) {
            //代表制氮机查询命令返回
            N2 n2 = new N2(devName, date, msg, msgId, 查询动作);
            int rs = n2Service.saveN2(n2);
            if (rs != 1) {
                throw new ISqlException("持久化制氮机信息到本地出错");
            }
        }

        if (msg.startsWith("AA55E1")) {
            //代表控制类命令返回，无需解析
        }
        if (msg.startsWith("AA55E2")) {
            //代表查询类命令
        }
        if (msg.startsWith("B5")) {
            //代表为气体或者虫情
        }
        if (msg.startsWith("AA55E4")) {
            //气调箱回复数据
            N2 n2 = new N2(devName, date, msg, msgId, 气调信息);
            Depot depot = depotService.getDepotByDevName(devName);
            int rs = n2Service.saveN2(n2);
            if (rs == 1) {
                Dg4AnalysisN2 dg4AnalysisN2 = Dg4AnalysisN2.newInstance();
                dg4AnalysisN2.analysisN2Info(n2, devName, depot);
            }
        }
        if (msg.startsWith("AAB0") || msg.startsWith("AAB1")) {
            //代表为粮情数据，第一包和第二包 TODO
            //存储原始数据
            Grain grain = new Grain(devName, date, msg, msgId);
            int rs = grainService.saveGrain(grain);
            if (rs != 1) {
                throw new ISqlException("持久化粮情信息到本地出错");
            }
            //持久化成功 执行解析操作返回json对象
            Depot depot = depotService.getDepotByDevName(devName);
            int id = depot.getId();
            Dg3AnalysisGrain dg3AnalysisGrain = Dg3AnalysisGrain.newInstance();
            try {
                JSONObject j = dg3AnalysisGrain.analysis(grain, depot);
                double maxTemp = j.getDouble("maxTemp");
                double minTemp = j.getDouble("minTemp");
                double avgTemp = j.getDouble("avgTemp");
                int r = depotService.updateTempInfoById(maxTemp, minTemp, avgTemp, id);
                if (r != 1) {
                    throw new ISqlException("更新失败");
                }
                log.info("更新仓库信息成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
