package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Depot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by: LT001
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
                       @Param("upTime") Date date,
                       @Param("maxTemp") double maxTemp,
                       @Param("minTemp") double minTemp,
                       @Param("innH") double innH,
                       @Param("innT") double innT,
                       @Param("avgTemp") double avgTemp);

    int upQTStatusById(@Param("id") int id, @Param("clStatus") int clStatus, @Param("model") int model);

    Depot selectDepotByDepotIdAndCompanyId(@Param("depotId") int depotId, @Param("companyId") int companyId);

    int updateDepotInfoByCompanyAndDepot(@Param("companyId") int companyId,
                                         @Param("depotId") int depotId,
                                         @Param("depotType") String depotType,
                                         @Param("inTime") Date inTime,
                                         @Param("grainType") String grainType,
                                         @Param("grainNum") String grainNum,
                                         @Param("inHumidity") String inHumidity,
                                         @Param("nowHumidity") String nowHumidity,
                                         @Param("tester") String tester);

    int insert(Depot depot);
}
