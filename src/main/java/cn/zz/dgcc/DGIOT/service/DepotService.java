package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.Depot;

import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/5/21 9:55
 * ClassExplain :
 * ->
 */
public interface DepotService {
    Depot getDepotByDevName(String devName);

    Depot getDepotByDepotId(int depotId);

    String getDevNameByDepotIdAndType(int depotId,int type);

    List<Depot> getDepotList();

    List<String> getDevNamesByDepotId(int depotId);

    List<Depot> getDepotListOnCompanyId(int userId);

    int updateTempInfoById(double maxTemp, double minTemp, double avgTemp,double innH,double innT, int id);

    int updateQTStatusById(int clStatus, int id);

}
