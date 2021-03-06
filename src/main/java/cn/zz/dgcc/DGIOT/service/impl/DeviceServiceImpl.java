package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.mapper.DeviceMapper;
import cn.zz.dgcc.DGIOT.service.CompanyService;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.service.Exception.ISqlException;
import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPMessage;
import cn.zz.dgcc.DGIOT.utils.DeviceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/4/23 15:16
 * ClassExplain :
 * ->
 */
@Component
@Service
public class DeviceServiceImpl implements DeviceService {
    private final int 需要云端注册新设备 = 0;
    private final int 需要更新设备信息 = 1;
    private final int 设备匹配成功 = 2;
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    @Resource
    DeviceMapper deviceMapper;
    @Autowired
    CompanyService companyService;


    @Override
    public int saveDeviceList(List<Device> devices) {
        //插入数据库
        log.info("云端设备总表大小=" + devices.size());
        return deviceMapper.insertDeviceList(devices);
    }

    @Override
    public Device getDeivceByDevS(String deviceSecret) {
        Device rs = deviceMapper.selectDevByDS(deviceSecret);
        if (rs == null) {
            throw new ISqlException("未找到数据");
        }
        return rs;
    }

    /**
     * @param amqpMessage
     * @return
     */
    @Override
    public int registerDevice(AMQPMessage amqpMessage) {
        /*
            先解析提交的设备要素
         */
//        log.info(amqpMessage.getContent());
        Device device = DeviceUtil.parseDev(amqpMessage);
        //设备信息与云端存储设备信息匹配
        log.info("判断设备信息是否匹配云端数据");
        if (isExistInYun(device) == 2) {
            log.info("设备信息匹配云端设备成功");
            return 需要更新设备信息;
        }
        //设备信息匹配出错：重置相关设备信息，并重新进行注册
        //根据设备5元所对应的云端三元素 重新下发
//        resetDeviceByDevId(device.getDevId());
//        resetDeviceByDtuId(device.getDtuId());
//        boolean result = reg(device);
        else if (isExistInYun(device) == 0) {
            //需要进行设备注册，result=注册结果
            boolean result = reg(device);
            if (!result) {
                return 需要云端注册新设备;
            }
            return 需要更新设备信息;
        } else if (isExistInYun(device) == 1) {
            log.info("云端库中已存在设备，更新```");
            log.info("重新分配dtuId/更新设备信息");
            int reset = deviceMapper.updataDeviceInfoByDevInfo(device);
            if (reset != 1) {
                throw new ISqlException("重置dtu匹配失败");
            }
            return 需要更新设备信息;
        }
        return 需要更新设备信息;
//        //检查是否注册
//        if (isReged(device)) {
//            //检查已存在信息是否与上报信息保持一致
//            if (checkSame(device)) {
//                //检查通过是相同的设备信息
//                log.info("设备信息匹配云端设备成功");
//                return 设备匹配;
//            }
//            log.info("云端库中已存在设备，更新dtu");
//            //dtuId与已存在信息不一致时，更新dtuId
//            //重新分配dtuId
//            log.info("重新分配dtuId/更新设备信息");
//            //清除dtu设备原有匹配
//            int res = resetDtuIdByDtuId(device.getDtuId());
//            //更新设备信息
//            int reset = deviceMapper.updataDeviceInfoByDevId(device);
//            if (reset != 1) {
//                throw new ISqlException("重置dtu匹配失败");
//            }
//            log.info("注册成功");
//            return 需要更新dtu;
//
//
//        } else {
//
//            //查询云设备列表中与注册设备type相同且没有被使用的设备列表
//            List<Device> ls = getNoUsedDeviceListByType(device.getType());
//            if (ls == null | ls.isEmpty()) {
//                log.info("没有可分配的云端设备");
//                return 需要注册新设备;//注册失败，返回false调用刷新列表
//            }
//            //获取未使用的云设备
//            Device reg2Dev = ls.get(0);
//            //通过ds来更新设备信息
//            String dS = reg2Dev.getDeviceSecret();
//            device.setDeviceSecret(dS);
//            int res = resetDtuIdByDtuId(device.getDtuId());
//            int upRs = deviceMapper.updataDeviceInfoByDS(device);
//            if (upRs == 1) {
//                //注册成功
//                return 需要更新dtu;
//            }
//        }
//        return 需要注册新设备;
    }

    private void resetDeviceByDtuId(String dtuId) {
        deviceMapper.resetDevInfoByDtuId(dtuId);
    }

    private void resetDeviceByDevId(String devId) {
        deviceMapper.resetDevInfoByDevId(devId);
    }

    /**
     * 从设备上传信息中，解析拿到项目名，通过项目名拿到项目id，通过项目id在设备表中查找设备进行绑定
     *
     * @param device
     * @return
     */
    private boolean reg(Device device) {
        //查询云设备列表中与注册设备type相同且没有被使用的  同项目设备列表

        //获取devNote
        String devNote = device.getDevNote();
        String[] strs = devNote.split("-");
        String xiangMu = strs[0];
        //根据devNote获取companyId
        int companyId = companyService.getCIDByName(xiangMu);

        List<Device> ls = getNoUsedDeviceListByTypeAndProject(device.getType(), companyId);
        if (ls == null | ls.isEmpty()) {
            log.info("没有可分配的云端设备");
            return false;
        }
        //获取未使用的云设备
        Device reg2Dev = ls.get(0);
        //TODO 修改过的匹配机制 要求实现在云端分配好 编号 站号
        //如果设备类型是太阳能分机,通过获取未使用的云设备来进行ds分配,
//        if (device.getType() == 7) {
//            String dS = reg2Dev.getDeviceSecret();
//            device.setDeviceSecret(dS);
//            int upRs = deviceMapper.updataDeviceInfoByDS(device);
//            return upRs == 1;
//        }
        //非太阳能分机,通过设定好的编号站号来进行云端和实机设备的绑定;
        String ds = getPipeiDevDsByBhAndZh(device.getDevBH(), device.getDevZH(), companyId, device.getType());
        //通过ds来更新设备信息
        device.setDeviceSecret(ds);
        int res = resetDtuIdByDtuId(device.getDtuId());
        int upRs = deviceMapper.updataDeviceInfoByDS(device);
        //注册成功
        return upRs == 1;
    }

    private String getPipeiDevDsByBhAndZh(String devBH, String devZH, int companyId, int type) {
        return deviceMapper.selectQTDevByBHAndZHAndCompany(devBH, devZH, type, companyId);
    }

    /**
     * 重置dtuid
     *
     * @param dtuId
     * @return
     */
    private int resetDtuIdByDtuId(String dtuId) {
        return deviceMapper.resetDtuId(dtuId);
    }

    int isExistInYun(Device device) {
        //deviceMapper.selectCountDevByDevAndDtu(device.getDevId(), device.getDtuId());
        //修改 通过分机5元作为身份验证
        int rs = deviceMapper.selectCountDevByDevInfoWWithoutDevAndDtu(device);
//        if (device.getType() == 7) {
//            rs = deviceMapper.selectCountDevByDevInfoAndCCid(device);
//        }
        if (rs == 0) {
            log.info("云端不存在该设备");
            //TODO 注册设备
            return 0;
        } else if (rs == 1) {
            Device rs1 =
                    deviceMapper.selectDevByDevInfo(device);
            //云端存在该设备，判断存储的dev和dtu信息是否相符
            log.info("查询云端设备信息：" + rs1);
            log.info("更新云端信息····");
            int rs2 = deviceMapper.updataDeviceInfoByDevInfo(device);
            return 1;
        }
        return 2;
    }


    boolean checkSame(Device device) {
        //通过devId获取数据库中存储的设备信息。如果dtuId相同且deviceSecret相同，表明是同一个设备上线
        Device rs = getDeviceInfoByDev(device.getDevId());

        return device.getDtuId().equals(rs.getDtuId()) && rs.getDeviceSecret().equals(device.getDeviceSecret());
    }

    boolean isReged(Device device) {
        //查询dev和dtu绑定的设备
        Device rs = getDeviceInfoByDev(device.getDevId());
        if (rs == null) {
            log.info("!没有dev信息,可以将设备注册到列表");
            return false;
        } else {
            log.info("dev设备信息已存在");
            return true;
        }
    }

    @Override
    public Device getDeviceInfoByDev(String dev) {
        Device rs = deviceMapper.selectDevByDev(dev);
        return rs;
    }

    @Override
    public List<Device> getDeviceListByType(int type) {
        List<Device> rs = deviceMapper.selectDevByType(type);
        return rs;
    }

    @Override
    public Device getDeviceInfoByDevAndDtu(String devId, String dtuId) {
        Device rs = deviceMapper.selectDevByDevAndDtu(devId, dtuId);
        return rs;
    }

    @Override
    public boolean loginDevice(AMQPMessage amqpMessage) {
        //获取设备登陆信息
        log.info("获取设备登陆信息");
        Device logDevice = DeviceUtil.parseDev(amqpMessage);
//        String topic = amqpMessage.getTopic();
//        String[] topicSplit = topic.split("/");
//        String pk = topicSplit[1];
//        String devName = topicSplit[2];
//        logDevice.setProductKey(pk);
//        logDevice.setDeviceName(devName);
        log.info("判断数据库中是否存在与登陆信息相同的数据···");
        //登陆时，取出分机5元进行设备验证，即 devNote,devBH,devZH,type,busType
//        Device dR = deviceMapper.selectDevByDevAndDtu(logDevice.getDevId(), logDevice.getDtuId());
        int dR = deviceMapper.selectCountDevByDevInfo(logDevice);
//        Device rs = deviceMapper.selectDevByUpInfo(logDevice);
        if (dR == 0) {
            log.info("库中不存在该数据，恢复出厂设置");
            return false;
        }
        log.info("信息匹配成功");
        deviceMapper.updataDevVersion(logDevice);
        deviceMapper.updataDtuVersion(logDevice);
        deviceMapper.updateCC(logDevice);
        return true;
    }

    @Override
    public int getCount() {
        int rs = deviceMapper.selectX();
        return rs;
    }

    @Override
    public int getCountByType(String pk) {
        int rs = deviceMapper.selectCountByType(pk);
        return rs;
    }

    @Override
    public Device getDevByRegDevInfo(Device regSuc) {
        Device rs = deviceMapper.selectDevByDevInfo(regSuc);
        return rs;
    }

    @Override
    public Device getDevInfoByDetail(String devNote, int type, String devBH, String devZH, String busType) {
        return null;
    }

    @Override
    public Device getDevByDevName(String dev) {
        Device rs = deviceMapper.selectDevByDevName(dev);
        return rs;
    }

    @Override
    public Device getDevByBHAndZH(String devBH, String devZH, int type) {

        return deviceMapper.selectDevByBHAndZH(devBH, devZH, type);
    }

    @Override
    public Device getN2Dev() {
        return deviceMapper.selectN2Dev();
    }

    @Override
    public Device getN2DevByUser(int userId) {
        return deviceMapper.selectN2DevByUserId(userId);
    }

    @Override
    public List<Device> getAllDev() {
        List<Device> rs = deviceMapper.selectAll();
        return rs;
    }

    @Override
    public List<Device> getAllQTDev() {
        return deviceMapper.selectAllQT();
    }

    @Override
    public void updateDevStatus(List<Device> devices) {
        for (Device device : devices
        ) {
            deviceMapper.updateDevStatus(device);
        }

    }

    @Override
    public int getOnlineCount() {
        return deviceMapper.selectCountByStatus();
    }

    @Override
    public int getOnlineCount(int i) {
        return deviceMapper.selectCountByStatusAndType(i);
    }

    @Override
    public List<Device> getDevByCompanyIdAndType(int companyId, int devType) {
        return deviceMapper.selectDevByTypeAndProject(companyId, devType);
    }

    @Override
    public void updateDevIotId(List<Device> devices) {
        for (Device device : devices
        ) {
            deviceMapper.updateIotId(device);
        }
    }

    @Override
    public String getFirewareVerByDevName(String devName) {
        return deviceMapper.selectFirewareByDevName(devName);
    }

    @Override
    public List<Device> getDevListByDevNameInfo(String str) {
        List<Device> list = deviceMapper.selectDevByDevNameInfo(str);
        if (list == null) {
            throw new ISqlException("没有设备");
        }
        return list;
    }

    @Override
    public List<Device> getAllActiveDev() {
        return deviceMapper.selectAllActive();
    }

    @Override
    public List<Device> getAllActiveDev(int page, int limit) {
        page = (page - 1) * limit;
        return deviceMapper.selectAllActiveWithLimit(page, limit);
    }

    @Override
    public int updateDevUidByDevNameInfo(int cid, String deviceNameSuf) {
        return deviceMapper.updateCidByDevInfo(cid, deviceNameSuf);
    }

    public List<Device> getNoUsedDeviceListByTypeAndProject(int type, int companyId) {
        List<Device> rs = deviceMapper.selectNoUsedDevByTypeAndProject(type, companyId);
        return rs;
    }

}
