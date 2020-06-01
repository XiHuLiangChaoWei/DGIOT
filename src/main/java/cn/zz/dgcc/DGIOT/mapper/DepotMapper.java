package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Depot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/5/14 15:41
 * ClassExplain :
 * ->
 */
@Mapper
public interface DepotMapper {
    List<Depot> selectAll();

    int selectDepotIdByDevName(String devName);

    Depot selectDepotByDepotId(int a);

    Depot selectDepotByDevName(String devName);

    List<Depot> selectDepotsOnCompany(int companyId);

    int upTempDateById(@Param("id") int id,
                       @Param("maxTemp") double maxTemp,
                       @Param("minTemp") double minTemp,
                       @Param("avgTemp") double avgTemp,
                       @Param("innH") double innH,
                       @Param("innT") double innT);
}
