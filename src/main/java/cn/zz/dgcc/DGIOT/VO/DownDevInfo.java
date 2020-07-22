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

    //{"productKey":"a1NXIYnqFcv","deviceName":"QT000001","deviceSecret":"NvRGnDGttpHquSzCiOuXZ264hZ17SuvV","DtuId":"30FF6B064241343617181857","devId":"39FFD7054E42343343782543"}

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
