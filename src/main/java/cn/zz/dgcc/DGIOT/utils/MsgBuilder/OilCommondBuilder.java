package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.BuildMessage;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;

import java.util.Date;

/**
 * Created by: YYL
 * Date: 2020/6/10 14:48
 * ClassExplain :
 * ->
 */
public class OilCommondBuilder extends CommondBuild {
    private OilCommondBuilder() {
    }

    private final static OilCommondBuilder oilCommondBuilder = new OilCommondBuilder();

    public static OilCommondBuilder getInstance() {
        return oilCommondBuilder;
    }

    private String devAddress = "01";

    public String getDevAddress() {
        return devAddress;
    }

    public void setDevAddress(String devAddress) {
        this.devAddress = devAddress;
    }

    final String start = "AAA8";
    final String end = "EFEF";
    String time = ContextUtil.getTimeYMDHMM(new Date());


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
        sb.append("FF");
        //分机地址
        sb.append(devAddress);
        //油情命令
        sb.append("A8");
        //f分级地址
        sb.append(devAddress);
        //预留扩展
        sb.append("FFFFFFFFFFFFFFFFFFFFFFFFFF");
        buildMessage.setBodyContent(sb.toString());
    }
}
