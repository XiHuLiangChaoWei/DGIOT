package cn.zz.dgcc.DGIOT.utils;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPMessage;
import com.alibaba.fastjson.JSONObject;

import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/5/8 11:21
 * ClassExplain :
 * ->
 */
public class DeviceUtil {
    private final static Logger log = Logger.getLogger(DeviceUtil.class.getSimpleName());

    public static Device parseDev(AMQPMessage amqpMessage) {
//        log.info("amqpMsg="+amqpMessage);
//        log.info("device:log="+amqpMessage.getContent());

        //解析content
        JSONObject content = JSONObject.parseObject(amqpMessage.getContent());
        log.info("device:LOG=" + content);
        Device device = new Device();
        String DtuId = content.getString("DtuId");
        String devId = content.getString("devId");
        String devNote = content.getString("devNote");
        String devBH = content.getString("devBH");
        String devZH = content.getString("devZH");
        int type = content.getInteger("type");
        String busType = content.getString("busType");
        String ccId = content.getString("CCID");
        String devVersion = content.getString("DevVer");
        String dtuVersion = content.getString("DtuVer");
        if (devNote.equals(null)) {
            devNote = "";
        }
        if (devId.equals(null)) {
            devId = "";
        }
        if (devBH.equals(null)) {
            devBH = "0";
        }
        if (devZH.equals(null)) {
            devZH = "0";
        }
        if (busType.equals(null)) {
            busType = "0";
        }


        device.setDtuId(DtuId);
        device.setDevId(devId);
        device.setDevNote(devNote);
        device.setDevBH(devBH);
        device.setDevZH(devZH);
        device.setType(type);
        device.setBusType(busType);
        device.setCcId(ccId);
        device.setDevVersion(devVersion);
        device.setDtuVersion(dtuVersion);
//        System.out.println("解析本地设备信息：" + device.toString());

        //解析Topic 获取pk和deviceName
        String topic = amqpMessage.getTopic();
        String[] topicSplit = topic.split("/");
        String pk = topicSplit[1];
        String devName = topicSplit[2];
        device.setProductKey(pk);
        device.setDeviceName(devName);

        return device;
    }

}
