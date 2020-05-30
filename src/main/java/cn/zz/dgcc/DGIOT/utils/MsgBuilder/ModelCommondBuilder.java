package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.BuildMessage;

/**
 * Created by: LT001
 * Date: 2020/5/12 10:56
 * ClassExplain : 控制柜 模式命令拼装工具
 * ->
 */
public class ModelCommondBuilder extends CommondBuild {
    public static final String end = "EFEF";
    public static final String start = "AA55";
    String model;//工作命令
    String devNote;//
    String devBH;
    String devZH;
    String action = "11";
    String targetCC = "63";

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDevNote() {
        return devNote;
    }

    public void setDevNote(String devNote) {
        this.devNote = devNote;
    }

    public String getDevBH() {
        return devBH;
    }

    public void setDevBH(String devBH) {
        this.devBH = devBH;
    }

    public String getDevZH() {
        return devZH;
    }

    public void setDevZH(String devZH) {
        this.devZH = devZH;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTargetCC() {
        return targetCC;
    }

    public void setTargetCC(String targetCC) {
        this.targetCC = targetCC;
    }

    private final static ModelCommondBuilder modelCommonBuilder = new ModelCommondBuilder();

    private ModelCommondBuilder() {
    }

    public static ModelCommondBuilder getModelCommonBuilder() {
        return modelCommonBuilder;
    }

    //调用build 完成一个命令的拼装
    public BuildMessage build() {
        BuildMessage buildMessage = new BuildMessage();
        buildMessage.setStart(start);
        buildMessage.setEnd(end);
        //设置默认的设备编号站号，防止出现空指针
        if (this.devBH == null | this.devZH == null) {
            devBH = "01";
            devZH = "01";
        }


        //这是消息主体
        buildBody(buildMessage, model);
        //向主体中 追加crc16校验位
        buildMessageCRC16(buildMessage);
        //向消息主体中拼装end和start，并将实际生成的命令存放在buildMessage的Msg属性中
        buildMessage(buildMessage);

        return buildMessage;
    }


    public void buildBody(BuildMessage message, String model) {
        StringBuffer sb = new StringBuffer();
        //控制类别
        sb.append("FA");
        //控制标记
        sb.append("06");
        //主控柜编号
        sb.append(devBH);
        //主控柜站号
        sb.append(devZH);
        //操作模式
        sb.append(model);
        //预留位
        sb.append("FF");
        //开关动作
        if(model.equals("00")) action="00";
        sb.append(action);
        //氮气目标浓度
        sb.append(targetCC);
        //
        sb.append("FF");
        sb.append("FF");

        message.setBodyContent(sb.toString());
    }
}
