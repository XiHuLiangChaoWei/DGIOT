package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.BuildMessage;

/**
 * Created by: YYL
 * Date: 2020/6/24 17:07
 * ClassExplain :
 * ->
 */
public class SumPowerCommondBuilder extends CommondBuild {
    private SumPowerCommondBuilder() {
    }

    private final static SumPowerCommondBuilder sumPowerCommondBuilder = new SumPowerCommondBuilder();

    public static SumPowerCommondBuilder getInstance() {
        return sumPowerCommondBuilder;
    }

    final String start = "AAA0";
    final String end = "EFEF";
    String devAddress;
    String ceng;
    String hang;
    String lie;
    String thNum;
    String ifOut;

    public BuildMessage build() {
        BuildMessage buildMessage = new BuildMessage();
        buildMessage.setStart(start);
        buildMessage.setEnd(end);
        buildBody(buildMessage);
        buildMessageCheckSum(buildMessage);
        buildMessage(buildMessage);
        return buildMessage;
    }

    public void buildBody(BuildMessage buildMessage) {
        StringBuffer sb = new StringBuffer();
        //时间流水
        sb.append("FFFFFFFFFFFF");
        //循环码
        sb.append("00");
        //分机地址
        sb.append(devAddress);
        //油情命令
        sb.append("A0");
        //分机地址
        sb.append(devAddress);
        //变长提取标记
        sb.append("11");
        sb.append("22");
        sb.append("33");
        //分机层
        sb.append(ceng);
        //分机行
        sb.append(hang);
        //分机列
        sb.append(lie);
        //温湿度数
        sb.append(thNum);
        //是否携带外温
        sb.append(ifOut);
        //预留
        sb.append("FFFFFFFFFF");
        buildMessage.setBodyContent(sb.toString());
    }

    public String getCeng() {
        return ceng;
    }

    public void setCeng(String ceng) {
        this.ceng = ceng;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getLie() {
        return lie;
    }

    public void setLie(String lie) {
        this.lie = lie;
    }

    public String getThNum() {
        return thNum;
    }

    public void setThNum(String thNum) {
        this.thNum = thNum;
    }

    public String getIfOut() {
        return ifOut;
    }

    public void setIfOut(String ifOut) {
        this.ifOut = ifOut;
    }

    public String getDevAddress() {
        return devAddress;
    }

    public void setDevAddress(String devAddress) {
        this.devAddress = devAddress;
    }
}
