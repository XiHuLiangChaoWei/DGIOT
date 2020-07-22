package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.SunConf;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by: LT001
 * Date: 2020/7/13 8:12
 * ClassExplain :
 * ->
 */
public class SunPowerJsonCommondBuilder extends CommondBuild {
    private final static SunPowerJsonCommondBuilder instance = new SunPowerJsonCommondBuilder();

    private SunPowerJsonCommondBuilder() {
    }

    public static SunPowerJsonCommondBuilder getInstance() {
        return instance;
    }

    SunConf sunConf;
    int oldAdd;

    public void setOldAdd(int oldAdd) {
        this.oldAdd = oldAdd;
    }

    public SunConf getSunConf() {
        return sunConf;
    }

    public void setSunConf(SunConf sunConf) {
        this.sunConf = sunConf;
    }

    public String setConf() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command type", "SET_FJ_PAR");
        jsonObject.put("LQ_ADD", oldAdd);
        jsonObject.put("LQ_NEW_ADD", sunConf.getAddress());
        jsonObject.put("LQ_CS", sunConf.getCeng());
        jsonObject.put("LQ_HS", sunConf.getHang());
        jsonObject.put("LQ_LS", sunConf.getLie());
        jsonObject.put("LQ_NWSD", sunConf.getInnerTH());
        jsonObject.put("LQ_WWSD", sunConf.getOutTH());     System.err.println(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }


    public String setConf(SunConf sunConf) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command type", "SET_FJ_PAR");
        jsonObject.put("LQ_ADD", oldAdd);
        jsonObject.put("LQ_NEW_ADD", sunConf.getAddress());
        jsonObject.put("LQ_CS", sunConf.getCeng());
        jsonObject.put("LQ_HS", sunConf.getHang());
        jsonObject.put("LQ_LS", sunConf.getLie());
        jsonObject.put("LQ_NWSD", sunConf.getInnerTH());
        jsonObject.put("LQ_WWSD", sunConf.getOutTH());
        System.err.println(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    public String setConf(int oldAdd, int newAdd, int ceng, int hang, int lie, int inTH, int outTH) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command type", "SET_FJ_PAR");
        jsonObject.put("LQ_ADD", oldAdd);
        jsonObject.put("LQ_NEW_ADD", newAdd);
        jsonObject.put("LQ_CS", ceng);
        jsonObject.put("LQ_HS", hang);
        jsonObject.put("LQ_LS", lie);
        jsonObject.put("LQ_NWSD", inTH);
        jsonObject.put("LQ_WWSD", outTH);     System.err.println(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    public String getConf() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command type", "LOOK_FJ_PAR");     System.err.println(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}
