package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by: LT001
 * Date: 2020/4/23 15:16
 * ClassExplain :
 * ->
 */
@Mapper
public interface DeviceMapper {
    int insertDeviceList(List<Device> list);

    Device selectDevByDev(String devId);

    Device selectDevByDtu(String dtuId);

    Device selectDevByDS(String devSecret);

    List<Device> selectDevByType(int type);

    List<Device> selectNoUsedDevByType(int type);

    int updataDeviceInfoByDS(Device device);

    int resetDtuId(String dtuId);


    int updataDeviceInfoByDevInfo(Device device);

    Device selectDevByDevAndDtu(@Param("devId") String devId,
                                @Param("dtuId") String dtuId);

    int selectX();

    int selectCountByType(String pk);

    void resetDevInfoByDtuId(String dtuId);

    void resetDevInfoByDevId(String devId);

    int selectCountDevByDevAndDtu(String devId, String dtuId);

    int selectCountDevByDevInfo(Device device);

    Device selectDevByDevInfo(Device device);

    int selectCountDevByDevInfoWWithoutDevAndDtu(Device device);

    Device selectDevByDevName(String devName);

    Device selectDevByUpInfo(Device logDevice);

    Device selectDevByBHAndZH(String devBH, String devZH, int type);

    Device selectN2Dev();

    Device selectN2DevByUserId(int userId);

    List<Device> selectAll();

    List<Device> selectAllQT();

    void updataDevVersion(Device logDevice);

    void updataDtuVersion(Device logDevice);

    List<Device> selectNoUsedDevByTypeAndProject(@Param("type") int type, @Param("userId") int company);

    void updateDevStatus(Device device);

    int selectCountByStatus();

    int selectCountByStatusAndType(int type);

    void updateCC(Device logDevice);

    List<Device> selectDevByTypeAndProject(@Param("userId") int companyId, @Param("type") int devType);

    void updateIotId(Device device);

    String selectQTDevByBHAndZHAndCompany(@Param("devBH") String devBH, @Param("devZH") String devZH, @Param("type") int i,@Param("companyId") int companyId);

    int selectCountDevByDevInfoAndCCid(Device device);

    String selectFirewareByDevName(String devName);

    List<Device> selectDevByDevNameInfo(String str);

    List<Device> selectAllActive();

    List<Device> selectAllActiveWithLimit(int page, int limit);

    /**
     * 通过模糊查询，找到设备名符合条件的 然后进行更新companyId
     * @param cid
     * @param deviceNameSuf
     * @return
     */
    int updateCidByDevInfo(int cid, String deviceNameSuf);
}
