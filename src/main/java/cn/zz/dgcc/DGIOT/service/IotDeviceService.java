package cn.zz.dgcc.DGIOT.service;

/**
 * Created by: LT001
 * Date: 2020/5/6 14:36
 * ClassExplain :批量申请阿里云设备，并保存到数据库
 * ->
 */
public interface IotDeviceService {
    void batchAddDevices(String pk,String deviceName,int deviceNameFix,int num);
}
