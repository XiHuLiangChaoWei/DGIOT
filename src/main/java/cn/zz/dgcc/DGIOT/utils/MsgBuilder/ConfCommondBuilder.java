package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.QTConfigure;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.zz.dgcc.DGIOT.utils.Constant.DATE_FORMAT_YMDHMS;

/**
 * Created by: LT001
 * Date: 2020/5/28 8:49
 * ClassExplain :
 * ->
 */
public class ConfCommondBuilder extends CommondBuild {
    private final static ConfCommondBuilder instance = new ConfCommondBuilder();

    private ConfCommondBuilder() {
    }

    public static ConfCommondBuilder getInstance() {
        return instance;
    }

    private QTConfigure qTConfigure;

    public QTConfigure getQTConfigure() {
        return qTConfigure;
    }

    public void setQTConfigure(QTConfigure QTConfigure) {
        this.qTConfigure = QTConfigure;
    }


//    public static void main(String[] args) throws IllegalAccessException {
//        getInstance().qTConfigure = new QTConfigure("null","1","1",1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1);
//        getInstance().qTConfigure.setDevId(null);
//        getInstance().qTConfigure.setCqTime(0);
//        getInstance().qTConfigure.setCqTime(0);
//        System.err.println(getInstance().qTConfigure.toString());
//        JSONObject jo = JSONObject.parseObject(getInstance().qTConfigure.toString());
//        System.err.println(jo);
////        jo = (JSONObject) JSON.toJSON(getInstance().qTConfigure);
////        System.err.println(jo);
//
//    }


    public String n2ConfToCommond(String devId) {
        JSONObject jo = JSONObject.parseObject(getInstance().qTConfigure.toString());
        jo.put("devId", devId);
        jo.put("commond type", "SZCS");
        jo.put("Set Dev config", "CDQT");
        return jo.toJSONString();
    }

    public String setTimes(String devId) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        String d = sdf.format(date);
        return "{\"devId\":\"" + devId + "\",\"command type\":\"TBSJ\",\"Sync time\":\"" + d + "\"}";
    }

    public String getPZCXInfo(String devId) {
        String rs = "{\"devId\":\"" + devId + "\",\"command type\":\"CXPZ\"}";
        return rs;
    }

    public static void main(String[] args) {
      String s=  getInstance().downDate("1","RESET DTU");
        System.err.println(s);
    }

    public String downDate(String devId, String action) {
        String rs = "{\"devId\":\"" + devId + "\",\"command type\":\"" + action + "\"}";
        return rs;
    }


    public String modelDowndate(String devId, int a, int b) {
        String str = "";
        switch (a) {
            case 1:
                str = "ddcq";
                break;
            case 2:
                str = "scxp";
                break;
            case 3:
                str = "xcsp";
                break;
            case 4:
                str = "hlxz";
                break;
            case 5:
                str = "fycd";
                break;
            case 6:
                str = "qmjc";
                break;
            case 7:
                str = "n2pk";
                break;
            case 8:
                str = "n2hs";
                break;
            case 9:
                str = "ksty";
                break;
        }
        String commod = "{\"devId\":\"" + devId + "\",\"command type\":\"run->" + str + "\",\"act falg\":" + b + "}";
        return commod;
    }
}
