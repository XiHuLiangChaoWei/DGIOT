package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Depot;
import cn.zz.dgcc.DGIOT.entity.DepotDev;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by: LT001
 * Date: 2020/5/21 17:02
 * ClassExplain :
 * ->
 */
public interface DepotDevMapper {
    Integer selectDepotIdByDevName(String devName);

    String selectDevNameByDepotIdAndType(@Param("depotId") int depotId, @Param("type") int type,@Param("companyId") int companyId);

    List<String> selectDevNamesByDepotId(int depotId,int companyId);

    void updateDevStatus(@Param("devName") String devName, @Param("status") String status);

    List<DepotDev> selectInfoByDepotId(int depotId,int companyId);

    DepotDev selectInfoByDevName(String devName);

}
