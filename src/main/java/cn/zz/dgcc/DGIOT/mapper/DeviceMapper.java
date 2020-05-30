package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by: YYL
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

    Device selectDevByBHAndZH(String devBH, String devZH,int type);

    Device selectN2Dev();

    Device selectN2DevByUserId(int userId);

    List<Device> selectAll();
}
