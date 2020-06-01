package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.Depot;
import cn.zz.dgcc.DGIOT.mapper.DepotDevMapper;
import cn.zz.dgcc.DGIOT.mapper.DepotMapper;
import cn.zz.dgcc.DGIOT.service.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/5/21 9:55
 * ClassExplain :
 * ->
 */
@Service
public class DepotServiceImpl implements DepotService {
    @Resource
    DepotMapper depotMapper;
    @Resource
    DepotDevMapper depotDevMapper;

    int getDeoptIdByDevName(String devName) {
        int rs = depotDevMapper.selectDepotIdByDevName(devName);
        return rs;
    }

    @Override
    public Depot getDepotByDevName(String devName) {
        int a = getDeoptIdByDevName(devName);
        Depot rs = depotMapper.selectDepotByDepotId(a);
        return rs;
    }

    @Override
    public Depot getDepotByDepotId(int depotId) {
        Depot rs = depotMapper.selectDepotByDepotId(depotId);
        return rs;
    }


    @Override
    public String getDevNameByDepotIdAndType(int depotId, int type) {
        String devName = depotDevMapper.selectDevNameByDepotIdAndType(depotId, type);
        return devName;
    }

    @Override
    public List<Depot> getDepotList() {
        List<Depot> depots = depotMapper.selectAll();
        return depots;
    }

    @Override
    public List<String> getDevNamesByDepotId(int depotId) {
        List<String> devNames = depotDevMapper.selectDevNamesByDepotId(depotId);
        return devNames;
    }

    @Override
    public List<Depot> getDepotListOnCompanyId(int CompanyId) {
        List<Depot> depots = depotMapper.selectDepotsOnCompany(CompanyId);
        return depots;
    }

    @Override
    public int updateTempInfoById(double maxTemp, double minTemp, double avgTemp, double innH, double innT, int id) {
        int rs = depotMapper.upTempDateById(id, maxTemp, minTemp,innH,innT, avgTemp);
        return rs;
    }

    @Override
    public int updateQTStatusById(int clStatus, int id) {
        return depotMapper.upQTStatusById(id,clStatus);
    }
}
