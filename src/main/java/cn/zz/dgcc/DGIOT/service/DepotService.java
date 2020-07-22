package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.Depot;
import cn.zz.dgcc.DGIOT.entity.DepotDev;
import cn.zz.dgcc.DGIOT.entity.Device;

import java.util.Date;
import java.util.List;

/**
 * Created by: LT001
 * Date: 2020/5/21 9:55
 * ClassExplain :
 * ->
 */
public interface DepotService {
    Depot getDepotByDevName(String devName);

    Depot getDepotByDepotIdAndCompanyId(int depotId,int companyId);

    String getDevNameByDepotIdAndType(int depotId,int type,int companyId);

    List<Depot> getDepotList();

    List<String> getDevNamesByDepotId(int depotId,int companyId);

    List<Depot> getDepotListOnCompanyId(int userId);

    /**
     *
     * @param maxTemp
     * @param minTemp
     * @param avgTemp
     * @param innH
     * @param innT
     * @param id
     * @return
     */
    int updateTempInfoById(Date date, double maxTemp, double minTemp, double avgTemp, double innH, double innT, int id);

    int updateQTStatusById(int clStatus,int model, int id);

    void updateDevStatus(List<Device> devices);

    List<DepotDev> getDepotDevByDepotId(int depotId,int companyId);

    DepotDev getDepotDevByDevName(String devName);

    int updateDepotInfoByCompanyAndDepot(int companyId,String depotInfo);

    int addDepot(Depot depot);
}
