package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.BuildMessage;
import cn.zz.dgcc.DGIOT.entity.Order;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;

/**
 * Created by: LT001
 * Date: 2020/5/6 10:12
 * ClassExplain : 控制柜 测控命令拼装工具
 * ->
 */
public class ControlOrderCommondBuilder extends CommondBuild {
    public static final String end = "EFEF";
    public static final String start = "AA55";
    private final static ControlOrderCommondBuilder instance = new ControlOrderCommondBuilder();

    private ControlOrderCommondBuilder() {
    }

    public static ControlOrderCommondBuilder getInstance() {
        return instance;
    }


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

    public void set空调温度(String 空调温度) {
        this.空调温度 = 空调温度;
    }

    public void set开关动作(String 开关动作) {
        this.开关动作 = 开关动作;
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

    private String devBH = "01";
    private String devZH = "01";
    private String devDZ = "01";
    private String devBHDZ = "04";
    private String 空调温度 = "1A";
    private String 开关动作 = "11";

    public void buildBody(BuildMessage buildMessage) {
        StringBuffer sb = new StringBuffer();
        // 控制类别
        sb.append("F7");
        // 控制标记
        sb.append("06");

        // 主控柜编号
//        sb.append(BytesUtil.getTargetId(order.getSerId(), false));
        sb.append(devBH);
        // 主控柜站号 TODO 待确定
        sb.append(devZH);

        // 设备类地址
//        sb.append(MsgUtil.getDeviceType(device.getType()));
        sb.append(devDZ);

        // 设备编号
//        sb.append(BytesUtil.toHexString(Integer.valueOf(device.getId())));
        sb.append(devBHDZ);
        //动作
//        sb.append(MsgUtil.getStatus(device.getTargetStatus()));
        sb.append(开关动作);
        //空调
        sb.append(空调温度);

        //命令ID
//        sb.append(BytesUtil.getOrderId(buildMessage.getOrderId()));
        sb.append("FF");
        sb.append("FF");

        //验证信息 TODO


        //最后
        buildMessage.setBodyContent(sb.toString());
    }
}
