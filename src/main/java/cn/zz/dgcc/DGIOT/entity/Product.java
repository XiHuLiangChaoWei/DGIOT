package cn.zz.dgcc.DGIOT.entity;

/**
 * Created by: LT001
 * Date: 2020/5/6 16:52
 * ClassExplain :
 * ->
 */
public class Product {
    String desc;
    String productName;
    String productKey;
    int deviceCount;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }
}
