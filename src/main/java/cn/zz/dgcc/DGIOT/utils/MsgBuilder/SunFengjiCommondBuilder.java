package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.BuildMessage;

/**
 * Created by: LT001
 * Date: 2020/7/16 14:40
 * ClassExplain :
 * ->
 */
public class SunFengjiCommondBuilder extends CommondBuild {
    private SunFengjiCommondBuilder() {
    }

    private final static SunFengjiCommondBuilder sunFengjiCommondBuilder = new SunFengjiCommondBuilder();

    public static SunFengjiCommondBuilder getInstance() {
        return sunFengjiCommondBuilder;
    }

    final String start = "A530";
    final String end = "EFEF";

    private String address = "01";
    private String num = "01";
    private String action = "00";

    public BuildMessage build() {
        BuildMessage buildMessage = new BuildMessage();
        buildMessage.setStart(start);
        buildMessage.setEnd(end);
        buildBody(buildMessage);
        buildMessageCheckSum(buildMessage);
        buildMessage(buildMessage);
        return buildMessage;
    }

    @Override
    public void buildBody(BuildMessage message) {
        StringBuffer sb = new StringBuffer();
        //时间流水
        sb.append("FFFFFFFFFFFF");
        sb.append("00");
        //地址
        sb.append(address);
        sb.append("30");
        sb.append(address);
        //风机编号
        sb.append(num);
        //动作
        sb.append(action);
        //预留字段
        sb.append("FFFFFFFFFFFFFFFFFFFFFF");
        message.setBodyContent(sb.toString());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
