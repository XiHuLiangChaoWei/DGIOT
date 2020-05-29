package cn.zz.dgcc.DGIOT.entity;

import cn.zz.dgcc.DGIOT.utils.ContextUtil;

import java.util.Date;
import java.util.Objects;

/**
 * Created by: YYL
 * Date: 2020/4/26 8:51
 * ClassExplain :
 * ->
 */
public class N2 {
    int id;
    String devName;
    Date recivedTime;
    String content;
    String msgId;
    String batchId;
    String n2devAction;

    public N2() {

    }

    public N2(String devName, Date recivedTime, String content, String msgId) {
        this.devName = devName;
        this.recivedTime = recivedTime;
        this.content = content;
        this.msgId = msgId;
        this.batchId = ContextUtil.getTimeYMDHMM(recivedTime);
    }

    public N2(String devName, Date date, String msg, String msgId, String s) {
        this.devName = devName;
        this.recivedTime = date;
        this.content = msg;
        this.msgId = msgId;
        this.batchId = ContextUtil.getTimeYMDHMM(recivedTime);
        this.n2devAction = s;
    }

    @Override
    public String toString() {
        return "N2{" +
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

    public String getN2devAction() {
        return n2devAction;
    }

    public void setN2devAction(String n2devAction) {
        this.n2devAction = n2devAction;
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
