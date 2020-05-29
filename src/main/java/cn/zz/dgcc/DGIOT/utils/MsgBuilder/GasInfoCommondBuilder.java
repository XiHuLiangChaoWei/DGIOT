package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.BuildMessage;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;

/**
 * Created by: LT001
 * Date: 2020/5/14 10:35
 * ClassExplain : 查询气调信息
 * ->
 */
public class GasInfoCommondBuilder extends CommondBuild {
    private final static GasInfoCommondBuilder instance = new GasInfoCommondBuilder();

    private GasInfoCommondBuilder() {
    }

    public static GasInfoCommondBuilder getInstance() {
        return instance;
    }

    private String devBH = "01";
    private String devZH = "01";
    private String devDZ = "01";
    private String devBHDZ = "04";

    public void setDevBH(String devBH) {
        this.devBH = ContextUtil.FormatNum(Integer.parseInt(devBH), 2);
    }

    public void setDevZH(String devZH) {
        this.devZH = ContextUtil.FormatNum(Integer.parseInt(devZH), 2);
    }

    public void setDevDZ(String devDZ) {
        this.devDZ = devDZ;
    }

    public void setDevBHDZ(String devBHDZ) {
        this.devBHDZ = devBHDZ;
    }

    public BuildMessage build() {
        BuildMessage buildMessage = new BuildMessage();
        buildMessage.setStart(start);
        buildMessage.setEnd(end);
        buildBody(buildMessage);
        buildMessageCRC16(buildMessage);
        buildMessage(buildMessage);
        return buildMessage;
    }

//    public static void main(String[] args) {
//        log.info(instance.build().toString());
//    }

    public void buildBody(BuildMessage buildMessage) {
        StringBuffer sb = new StringBuffer();

        sb.append("E1");
        sb.append("03");
        sb.append(devBH);
        sb.append(devZH);
        sb.append("FFFFFFFFFFFF");
        buildMessage.setBodyContent(sb.toString());
    }
}
