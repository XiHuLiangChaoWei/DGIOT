package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.Depot;
import cn.zz.dgcc.DGIOT.entity.DepotDev;
import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.mapper.DepotDevMapper;
import cn.zz.dgcc.DGIOT.mapper.DepotMapper;
import cn.zz.dgcc.DGIOT.service.DepotService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by: LT001
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
    public Depot getDepotByDepotIdAndCompanyId(int depotId, int companyId) {
        Depot rs = depotMapper.selectDepotByDepotIdAndCompanyId(depotId, companyId);
        return rs;
    }


    @Override
    public String getDevNameByDepotIdAndType(int depotId, int type, int companyId) {
        String devName = depotDevMapper.selectDevNameByDepotIdAndType(depotId, type, companyId);
        return devName;
    }

    @Override
    public List<Depot> getDepotList() {
        List<Depot> depots = depotMapper.selectAll();
        return depots;
    }

    @Override
    public List<String> getDevNamesByDepotId(int depotId, int companyId) {
        List<String> devNames = depotDevMapper.selectDevNamesByDepotId(depotId, companyId);
        return devNames;
    }

    @Override
    public List<Depot> getDepotListOnCompanyId(int CompanyId) {
        List<Depot> depots = depotMapper.selectDepotsOnCompany(CompanyId);
        return depots;
    }

    /**
     * @param maxTemp
     * @param minTemp
     * @param avgTemp
     * @param innH
     * @param innT
     * @param id
     * @return
     */
    @Override
    public int updateTempInfoById(Date date, double maxTemp, double minTemp, double avgTemp, double innH, double innT, int id) {
        int rs = depotMapper.upTempDateById(id, date, maxTemp, minTemp, innH, innT, avgTemp);
        return rs;
    }

    @Override
    public int updateQTStatusById(int clStatus, int model, int id) {
        return depotMapper.upQTStatusById(id, clStatus, model);
    }

    @Override
    public void updateDevStatus(List<Device> devices) {
        for (Device d : devices
        ) {
            String devName = d.getDeviceName();
            String status = d.getDeviceStatus();
            depotDevMapper.updateDevStatus(devName, status);
        }
    }

    @Override
    public List<DepotDev> getDepotDevByDepotId(int depotId, int companyId) {
        return depotDevMapper.selectInfoByDepotId(depotId, companyId);
    }

    @Override
    public DepotDev getDepotDevByDevName(String devName) {
        return depotDevMapper.selectInfoByDevName(devName);
    }

    @Override
    public int updateDepotInfoByCompanyAndDepot(int companyId, String depotInfo) {
        JSONObject jsonObject = JSON.parseObject(depotInfo);
        int depotId = jsonObject.getInteger("depotId");
        String depotType = jsonObject.getString("depot_type");
        Date inTime = jsonObject.getDate("in_time");
        String grainType = jsonObject.getString("grain_type");
        String grainNum = jsonObject.getString("grain_num");
        String inHumidity = jsonObject.getString("in_humidity");
        String nowHumidity = jsonObject.getString("now_humidity");
        String tester = jsonObject.getString("tester");

        return depotMapper.updateDepotInfoByCompanyAndDepot(companyId,depotId,depotType,inTime,grainType,grainNum,inHumidity,nowHumidity,tester);
    }

    @Override
    public int addDepot(Depot depot) {
        return depotMapper.insert(depot);
    }

}
