package cn.zz.dgcc.DGIOT.entity;

import cn.zz.dgcc.DGIOT.utils.ContextUtil;

import java.util.Date;

/**
 * Created by: YYL
 * Date: 2020/6/10 9:34
 * ClassExplain :
 * ->
 */
public class Oil {
    int id;
    String devName;
    Date receivedTime;
    String batch;
    String content;

    public Oil() {
    }


    public Oil(String devName, Date date, String msg) {
        this.devName = devName;
        this.receivedTime = date;
        this.content = msg;
        this.batch = ContextUtil.getTimeYMDHMM(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
