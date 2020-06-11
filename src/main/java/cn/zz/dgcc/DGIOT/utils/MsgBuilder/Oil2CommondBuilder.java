package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.BuildMessage;

/**
 * Created by: YYL
 * Date: 2020/6/11 10:33
 * ClassExplain :
 * ->
 */

public class Oil2CommondBuilder extends CommondBuild {
    private Oil2CommondBuilder() {

    }

    private final static Oil2CommondBuilder oil2CommondBuilder = new Oil2CommondBuilder();

    public static Oil2CommondBuilder getInstance() {
        return oil2CommondBuilder;
    }

    final String start = "AA";

    final String 校准命令 = "A9";

    final String 查询命令 = "AB";

    private String devAddress = "01";

    private String oilConf = "";

    public String getOilConf() {
        return oilConf;
    }

    public void setOilConf(String oilConf) {
        this.oilConf = oilConf;
    }

    public String getDevAddress() {
        return devAddress;
    }

    public void setDevAddress(String devAddress) {
        this.devAddress = devAddress;
    }

    /**
     *
     * @param type  1下发设置，2下发查询
     * @return
     */
    public BuildMessage build(int type) {
        BuildMessage buildMessage = new BuildMessage();
        buildMessage.setEnd(end);
        if (type == 1) {
            buildMessage.setStart(start + 校准命令);
            buildBody(buildMessage, type);
            buildMessageCRC16(buildMessage);
        } else if (type == 2) {
            buildMessage.setStart(start + 查询命令);
            buildBody(buildMessage, type);
            buildMessageCheckSum(buildMessage);
        }
        buildMessage(buildMessage);
        //TODO 重置配置
        return buildMessage;
    }

    public void buildBody(BuildMessage buildMessage, int type) {
        StringBuffer sb = new StringBuffer();
        //时间流水
        sb.append("FFFFFFFFFFFF");
        //循环码
        sb.append("FF");
        //分机地址
        sb.append(devAddress);
        //油情命令
        switch (type) {
            case 1:
                sb.append(校准命令);
                //f分级地址
                sb.append(devAddress);
                sb.append(oilConf);
                sb.append("FFFFFFFFFFFFFFFFFF");
                break;
            case 2:
                sb.append(查询命令);
                //f分级地址
                sb.append(devAddress);
                sb.append("FFFFFFFFFFFFFFFFFFFFFFFFFF");
                break;
        }
        buildMessage.setBodyContent(sb.toString());
    }
}
