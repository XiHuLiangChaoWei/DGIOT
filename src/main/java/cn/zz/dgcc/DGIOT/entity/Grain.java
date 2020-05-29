package cn.zz.dgcc.DGIOT.entity;

import cn.zz.dgcc.DGIOT.utils.ContextUtil;

import java.util.Date;

/**
 * Created by: YYL
 * Date: 2020/5/21 8:39
 * ClassExplain :
 * ->
 */
public class Grain {
    int id;
    String devName; //使用云端devName
    Date recivedTime;
    String content;
    String msgId;
    String batchId;

    public Grain() {
    }

    public Grain(String devName, Date date, String msg, String msgId) {
        this.devName = devName;
        this.recivedTime = date;
        this.content = msg;
        this.msgId = msgId;
        this.batchId = ContextUtil.getTimeYMDHMM(date);
    }

    @Override
    public String toString() {
        return "Grain{" +
                "id=" + id +
                ", devName='" + devName + '\'' +
                ", recivedTime=" + recivedTime +
                ", content='" + content + '\'' +
                ", msgId='" + msgId + '\'' +
                ", batchId='" + batchId + '\'' +
                '}';
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

    public Date getRecivedTime() {
        return recivedTime;
    }

    public void setRecivedTime(Date recivedTime) {
        this.recivedTime = recivedTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
