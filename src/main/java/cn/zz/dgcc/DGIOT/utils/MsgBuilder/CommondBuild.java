package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

import cn.zz.dgcc.DGIOT.entity.BuildMessage;

/**
 * Created by: LT001
 * Date: 2020/5/6 9:23
 * ClassExplain :
 * ->
 */
public abstract class  CommondBuild {
    public static final String end = "EFEF";
    public static final String start = "AA55";

    public BuildMessage build() {
        BuildMessage buildMessage = new BuildMessage();
        buildMessage.setStart(start);
        buildMessage.setEnd(end);
        return buildMessage;
    }

    public void buildMessage(BuildMessage message) {
        String strMsg = message.getStart() + message.getBodyContent() + message.getEnd();
        message.setMsg(BytesUtil.hexStrToBytes(strMsg));
    }

//    public static void main(String[] args) {
//       byte[] a = BytesUtil.hexStrToBytes("AA55E1030101FFFFFFFFFFFF");
//       int crc16 = CRC16.calcCrc16(a);
//       byte[] crc16Hex = BytesUtil.shortToByte((short)crc16);
//       String crc16str = BytesUtil.bytesToString(crc16Hex);
//       log.info(crc16str);
//    }

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
        //将校验值 拼装到 body中
        message.setBodyContent(message.getBodyContent() + crc16Str);
    }

    /**
     * 计算前24的和%255
     *
     * @param message
     */
    public void buildMessageCheckSum(BuildMessage message) {
        String c = CRC16.makeChecksum(message.getStart() + message.getBodyContent());
        message.setBodyContent(message.getBodyContent() + c);
    }

    public void buildMessageId(BuildMessage message) {
    }

    public void buildBody(BuildMessage message) {

    }

}
