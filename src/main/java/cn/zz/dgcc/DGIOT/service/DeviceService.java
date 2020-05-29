package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPMessage;

import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/4/23 15:16
 * ClassExplain :
 * ->
 */
public interface DeviceService {
    void saveDeviceList(List<Device> devices);

    Device getDeivceByDevS(String deviceSecret);

    int registerDevice(AMQPMessage amqpMessage);

    Device getDeviceInfoByDev(String dev);

    List<Device> getDeviceListByType(int type);

    Device getDeviceInfoByDevAndDtu(String devId, String dtuId);

    boolean loginDevice(AMQPMessage amqpMessage);

    int getCount();

    int getCountByType(String pk);

    Device getDevByRegDevInfo(Device regSuc);

    Device getDevInfoByDetail(String devNote, int type, String devBH, String devZH, String busType);

    Device getDevByDevName(String dev);

    Device getDevByBHAndZH(String devBH, String devZH,int type);

    Device getN2Dev();

    Device getN2DevByUser(int userId);
}
