package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.N2Configure;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.zz.dgcc.DGIOT.utils.Constant.DATE_FORMAT_YMDHMS;

/**
 * Created by: YYL
 * Date: 2020/5/28 8:49
 * ClassExplain :
 * ->
 */
public class ConfCommondBuilder extends CommondBuild{
    private final static ConfCommondBuilder instance = new ConfCommondBuilder();
    private ConfCommondBuilder() {
    }
    public static ConfCommondBuilder getInstance() {
        return instance;
    }

    private N2Configure n2Configure;

    public N2Configure getN2Configure() {
        return n2Configure;
    }

    public void setN2Configure(N2Configure n2Configure) {
        this.n2Configure = n2Configure;
    }

    public String n2ConfToCommond(){
        return new Gson().toJson(this.n2Configure);
    }

    public String setTimes(String devId){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        String d = sdf.format(date);
        return "{\"devId\":\""+devId+"\",\"command type\":\"TBSJ\",\"Sync time\":\""+d+"\"}";
    }

    public String getPZCXInfo(String devId){
        String rs = "{\"devId\":\""+devId+"\",\"command type\":\"CXPZ\"}";
        return rs;
    }

    public String downDate(String devId,String action){
        String rs = "{\"devId\":\""+devId+"\",\"command type\":\""+action+"\"}";
        return rs ;
    }


    public String modelDowndate(String devId,int a,int b){
        String str = "";
        switch (a){
            case 1 : str = "ddcq";break;
            case 2 : str = "scxp";break;
            case 3 : str = "xcsp";break;
            case 4 : str = "hlxz";break;
            case 5 : str = "fycd";break;
            case 6 : str = "qmjc";break;
        }
        String commod = "{\"devId\":\""+devId+"\",\"command type\":\"run->"+str+"\",\"act falg\":"+b+"}";
        return commod;
    }

//    public static void main(String[] args) {
//        log.info(getInstance().modelDowndate("111",1,1));
//    }
}
