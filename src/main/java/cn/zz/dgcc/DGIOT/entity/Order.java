package cn.zz.dgcc.DGIOT.entity;

import com.sun.org.apache.xpath.internal.operations.Or;

/**
 * Created by: YYL
 * Date: 2020/4/23 15:00
 * ClassExplain :
 * ->
 */
public class Order {
    int id;
    int userId;
    int isSuccess;
    String messageId;
    int msgType;
    String messageContent;
    String createTime;
    String devId;
    String result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Order(int userId, int isSuccess, String messageId, int msgType, String messageContent, String createTime, String devId, String result) {
        this.userId = userId;
        this.isSuccess = isSuccess;
        this.messageId = messageId;
        this.msgType = msgType;
        this.messageContent = messageContent;
        this.createTime = createTime;
        this.devId = devId;
        this.result = result;
    }
    public Order(){}

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + userId + '\'' +
                ", isSuccess=" + isSuccess +
                ", messageId='" + messageId + '\'' +
                ", msgType=" + msgType +
                ", messageContent='" + messageContent + '\'' +
                ", createTime='" + createTime + '\'' +
                ", devId='" + devId + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
