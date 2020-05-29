package cn.zz.dgcc.DGIOT.entity;

import java.util.List;

/**
 * 命令封装类
 *
 * @author Andy
 */
public class BuildMessage {

    private String start;

    // 命令ID
    private int orderId;

    // 命令类型
    private String type;

    // 消息内容
    private String bodyContent;

    private String end;

    // 异常信息
    private String error;

    // 最终的命令信息
    private byte[] msg;

    // 可能多条命令
    private List<byte[]> listMsg;


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public void setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public byte[] getMsg() {
        return msg;
    }

    public void setMsg(byte[] msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<byte[]> getListMsg() {
        return listMsg;
    }

    public void setListMsg(List<byte[]> listMsg) {
        this.listMsg = listMsg;
    }

    @Override
    public String toString() {
        return start+ bodyContent + end;
    }

}
