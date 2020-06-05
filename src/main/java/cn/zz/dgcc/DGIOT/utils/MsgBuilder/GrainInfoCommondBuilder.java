package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.BuildMessage;

/**
 * Created by: YYL
 * Date: 2020/5/23 9:46
 * ClassExplain :
 * ->
 */
public class GrainInfoCommondBuilder extends CommondBuild {
    public static final String end = "EFEF";
    public static final String start = "AAA0";
    private final static GrainInfoCommondBuilder grainInfoCommondBuilder = new GrainInfoCommondBuilder();

    private GrainInfoCommondBuilder() {
    }

    public static GrainInfoCommondBuilder getGrainInfoCommondBuilder() {
        return grainInfoCommondBuilder;
    }

    String year = "FF";
    String month = "FF";
    String day = "FF";
    String hour = "FF";
    String min = "FF";
    String s = "FF";
    String devBH = "01";
    String devZH = "01";

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setS(String s) {
        this.s = s;
    }

    public void setDevBH(String devBH) {
        this.devBH = devBH;
    }

    public void setDevZH(String devZH) {
        this.devZH = devZH;
    }

    public BuildMessage build() {
        BuildMessage build = new BuildMessage();

        build.setStart(start);
        build.setEnd(end);
        buildBody(build);
        buildMessageCheckSum(build);
        buildMessage(build);
        return build;
    }

    public void buildMessageCRC16(BuildMessage message) {
        //获得 start+body内容拼装的byte型数组
        byte[] cmdData = BytesUtil.hexStrToBytes(message.getStart() + message.getBodyContent());
        //获得crc16的校验值
        int crc16Hex2 = CRC16.calcCrc16(cmdData);
        //校验值转换成byte数组
        byte[] crc16Hex2Bytes = BytesUtil.shortToByte((short) crc16Hex2);
        // 调高低位：shortToByte中已调整高低位，这里不需要再调整
        //cmdData[cmdData.length - 4] = crc16Hex2Bytes[0];
        //cmdData[cmdData.length - 3] = crc16Hex2Bytes[1];
        String crc16Str = BytesUtil.bytesToString(crc16Hex2Bytes);
        String rCrc16Str = crc16Str.substring(2, 4);
        //将校验值 拼装到 body中
        message.setBodyContent(message.getBodyContent() + rCrc16Str);
    }

    public void buildBody(BuildMessage buildMessage) {
        StringBuffer sb = new StringBuffer();
        sb.append(year);
        sb.append(month);
        sb.append(day);
        sb.append(hour);
        sb.append(min);
        sb.append(s);
        sb.append("00");
        sb.append(devBH);
        sb.append("A0");
        sb.append(devZH);
        sb.append("FFFFFFFFFFFFFFFFFFFFFFFFFF");
        buildMessage.setBodyContent(sb.toString());
    }
}
