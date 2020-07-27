package cn.zz.dgcc.DGIOT.service;

/**
 * Created by: LT001
 * Date: 2020/5/6 14:36
 * ClassExplain :批量申请阿里云设备，并保存到数据库
 * ->
 */
public interface IotDeviceService {
    /**
     *
     * @param pk 阿里云产品pk
     * @param deviceName 要注册的设备名 XX-XX-XX格式
     * @param deviceNameFix 从X开始 默认为0，如果为已存在项目添加设备 TODO 该参数为最初版本遗留,最初实现的功能是根据上线物联网设备进行自动注册,后续发现单项目设备数量是固定的
     * @param num
     */
    void batchAddDevices(String pk,String deviceName,int deviceNameFix,int num);
}
