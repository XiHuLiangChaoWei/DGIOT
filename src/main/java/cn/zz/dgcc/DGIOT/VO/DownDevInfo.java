package cn.zz.dgcc.DGIOT.VO;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by: LT001
 * Date: 2020/5/9 8:45
 * ClassExplain :注册成功后下发三元组
 * ->
 */
public class DownDevInfo {
    String productKey;
    String deviceName;
    String deviceSecret;
    String DtuId;
    String devId;

    public DownDevInfo() {
    }

    public DownDevInfo(String productKey, String deviceName, String deviceSecret, String dtuId, String devId) {
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.deviceSecret = deviceSecret;
        this.DtuId = dtuId;
        this.devId = devId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"productKey\":\"" + productKey + '\"' +
                ",\"deviceName\":\"" + deviceName + '\"' +
                ", \"deviceSecret\":\"" + deviceSecret + '\"' +
                ",\"DtuId\":\"" + DtuId + '\"' +
                ", \"devId\":\"" + devId + '\"' +
                '}';
    }

}
