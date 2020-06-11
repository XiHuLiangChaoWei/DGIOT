package cn.zz.dgcc.DGIOT.entity;

/**
 * Created by: YYL
 * Date: 2020/6/11 11:10
 * ClassExplain :
 * ->
 */
public class OilConf {
    int id;
    String devName;
    String recTime;
    String content;

    public OilConf() {
    }

    public OilConf(String devName, String recTime, String content) {
        this.devName = devName;
        this.recTime = recTime;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getRecTime() {
        return recTime;
    }

    public void setRecTime(String recTime) {
        this.recTime = recTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
