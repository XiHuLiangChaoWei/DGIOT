package cn.zz.dgcc.DGIOT.entity;

import cn.zz.dgcc.DGIOT.utils.ContextUtil;

import java.util.Date;
import java.util.Objects;

/**
 * Created by: LT001
 * Date: 2020/4/26 8:51
 * ClassExplain :
 * ->
 */
public class N2 {
    int id;
    String devName;
    Date receivedTime;
    String content;
    String msgId;
    String batchId;
    String n2devAction;

    public N2() {

    }

    public N2(String devName, Date receivedTime, String content, String msgId) {
        this.devName = devName;
        this.receivedTime = receivedTime;
        this.content = content;
        this.msgId = msgId;
        this.batchId = ContextUtil.getTimeYMDHMM(receivedTime);
    }

    public N2(String devName, Date date, String msg, String msgId, String s) {
        this.devName = devName;
        this.receivedTime = date;
        this.content = msg;
        this.msgId = msgId;
        this.batchId = ContextUtil.getTimeYMDHMM(receivedTime);
        this.n2devAction = s;
    }

    @Override
    public String toString() {
        return "N2{" +
                "id=" + id +
                ", devName='" + devName + '\'' +
                ", receivedTime=" + receivedTime +
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

    public Date getreceivedTime() {
        return receivedTime;
    }

    public void setreceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
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
