package cn.zz.dgcc.DGIOT.entity;

/**
 * Created by: YYL
 * Date: 2020/7/29 16:13
 * ClassExplain :
 * ->
 */
public class Iproduct {
    int id;
    String productName;
    String productKey;

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "Iproduct{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productKey='" + productKey + '\'' +
                '}';
    }
}
