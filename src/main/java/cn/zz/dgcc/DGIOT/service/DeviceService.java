package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPMessage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by: LT001
 * Date: 2020/4/23 15:16
 * ClassExplain :
 * ->
 */
@Service
public interface DeviceService {
    int saveDeviceList(List<Device> devices);

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

    /**
     * 获取设备库中所有设备列表
     * @return List<Device>
     */
    List<Device> getAllDev();

    List<Device> getAllQTDev();

    void updateDevStatus(List<Device> devices);

    int getOnlineCount();

    int getOnlineCount(int i);

    List<Device> getDevByCompanyIdAndType(int companyId, int devType);

    void updateDevIotId(List<Device> devices);

    String getFirewareVerByDevName(String devName);

    List<Device> getDevListByDevNameInfo(String str);

    List<Device> getAllActiveDev();

    List<Device> getAllActiveDev(int page, int limit);

    int updateDevUidByDevNameInfo(int cid, String deviceNameSuf);
}
